<template>
	<div class="search">
		<select
			class="optionTag"
			v-model="planDestination"
			@click="placeFilter()"
		>
			<option>전체</option>
			<option>강원도</option>
			<option>경기도</option>
			<option>경상도</option>
			<option>대구시</option>
			<option>부산시</option>
			<option>서울시</option>
			<option>인천시</option>
			<option>전라도</option>
			<option>제주도</option>
			<option>충청도</option>
			<option>울산시</option></select
		><input
			type="text"
			v-model="searchPlan"
			placeholder="키워드를 입력하세요"
		/>
		<button class="w-btn1 w-btn-indigo" @click="tagFilter(this.searchPlan)">
			검색
		</button>
	</div>
	<hr />

	<div class="sort">
		<button class="w-btn1 w-btn-indigo" @click="orderBy('planViews')">
			조회순
		</button>
		<button class="w-btn1 w-btn-indigo" @click="orderBy('planUsedCount')">
			카피순
		</button>
	</div>
	<div>
		<h1>지역: {{ planDestination }}</h1>
		<h1>검색: {{ searchPlan }}</h1>

		<div
			class="w-btn-outline w-btn-red-outline"
			@click="intoPlan(value)"
			v-for="(value, index) in filteredPlanList"
			:key="index"
		>
			{{ value.teamCode.teamName }}팀의{{ value.planName }} 플랜
			<br />조회수:
			{{ value.planViews }}
			<br />
			카피수:
			{{ value.planUsedCount }}
			<br />
			지역:{{ value.planDestination }}
		</div>
	</div>
</template>

<script>
import axios from 'axios';
export default {
	created() {
		this.loadAllPlans();
	},
	data() {
		return {
			searchPlan: '',
			planDestination: '전체',
			allPlanList: [],
			placeFiltered: [],
			tagFiltered: [],
			filteredPlanList: [],
		};
	},
	methods: {
		intoPlan: function (value) {
			this.$store.commit('updatePlanCode', value);
			this.$router.push({
				name: 'usersPlan',
			});
			const url = '/api/countView';

			axios
				.put(url, value)
				.then((response) => {})
				.catch((error) => {});
		},
		placeFilter: function () {
			if (this.planDestination === '전체') {
			} else {
				const placeFiltered = this.tagFiltered.filter(
					(a) => a.planDestination === this.planDestination,
				);
				this.filteredPlanList.length = '';
				this.filteredPlanList = placeFiltered;
			}
		},
		tagFilter: function (tag) {
			if (this.searchPlan === '' && this.planDestination === '전체') {
				this.loadAllPlans();
			} else {
				const url = 'api/tagFilter';
				this.filteredPlanList.length = 0;
				this.tagFiltered.length = 0;
				axios
					.get(url, { params: { tagContent: tag } })
					.then((response) => {
						response.data.map((item) => {
							this.filteredPlanList.push(item);
							this.tagFiltered.push(item);
						});
						if (this.planDestination === '전체') {
						} else {
							const placeFiltered = this.filteredPlanList.filter(
								(a) =>
									a.planDestination === this.planDestination,
							);
							this.filteredPlanList = placeFiltered;
						}
					})
					.catch((error) => {});
			}
		},
		orderBy: function (orderBy) {
			if (orderBy == 'planViews') {
				this.filteredPlanList.sort(function (a, b) {
					return b.planViews - a.planViews;
				});
			} else if (orderBy == 'planUsedCount') {
				this.filteredPlanList.sort(function (a, b) {
					return b.planUsedCount - a.planUsedCount;
				});
			}
		},
		loadAllPlans: function () {
			const url = '/api/loadAllPlans';
			this.tagFiltered.length = 0;
			this.allPlanList.length = 0;
			this.placeFiltered.length = 0;
			axios
				.get(url)
				.then((response) => {
					response.data.map((item) => {
						this.allPlanList.push(item);
						this.filteredPlanList.push(item);
						this.tagFiltered.push(item);
						return console.log(item);
					});
				})
				.catch((error) => {
					console.log(error);
				});
		},
	},
};
</script>

<style>
.w-btn-outline {
	position: relative;
	padding: 15px 30px;
	border-radius: 15px;
	font-family: 'paybooc-Light', sans-serif;
	box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
	text-decoration: none;
	font-weight: 600;
	transition: 0.25s;
}
.w-btn-red-outline {
	border: 3px solid #ff5f2e;
	color: #6e6e6e;
	width: 300px;
	height: 270px;
	margin: 20px;
	font-size: 25px;
	float: left;
}
.w-btn-red-outline:hover {
	background-color: #ff5f2e;
	color: #e1eef6;
}
.w-btn-outline:hover {
	letter-spacing: 2px;
	transform: scale(1.2);
	cursor: pointer;
}
.w-btn-outline:active {
	transform: scale(1.5);
}

.optionTag {
	text-align: center;
	width: 100px;
	font-weight: 600;
}
.sort {
	float: right;
	margin: 10px;
}
.w-btn1 {
	position: relative;
	border: none;
	display: inline-block;
	padding: 15px 30px;
	border-radius: 15px;
	font-family: 'paybooc-Light', sans-serif;
	box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
	text-decoration: none;
	font-weight: 600;
	transition: 0.25s;
	margin-right: 50px;
}
.w-btn1:hover {
	letter-spacing: 2px;
	transform: scale(1.2);
	cursor: pointer;
}
.w-btn1:active {
	transform: scale(1.5);
}
.w-btn {
	position: relative;
	border: none;
	display: inline-block;
	padding: 15px 30px;
	border-radius: 15px;
	font-family: 'paybooc-Light', sans-serif;
	box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
	text-decoration: none;
	font-weight: 600;
	transition: 0.25s;
}
.w-btn:hover {
	letter-spacing: 2px;
	transform: scale(1.2);
	cursor: pointer;
}
.w-btn-indigo {
	background-color: aliceblue;
	color: #1e6b7b;
	margin-bottom: 30px;
}
.search {
	height: 100px;
	background-color: #e1eef6;
	text-align: center;
}
.search input {
	width: 500px;
	height: 40px;
	margin-top: 30px;
}
.search select {
	height: 40px;
	margin-top: 30px;
}

.search button {
	position: relative;
	display: inline-block;
	margin-top: 30px;
}
</style>
