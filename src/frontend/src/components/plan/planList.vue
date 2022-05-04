<template>
	<input type="text" v-model="searchPlan" placeholder="키워드를 입력하세요" />
	<button @click="tagFilter(this.searchPlan)">검색</button>
	<h2>지역설정</h2>
	<select v-model="planDestination" @click="placeFilter()">
		<option>전체</option>
		<option>강원</option>
		<option>경기</option>
		<option>경남</option>
		<option>경북</option>
		<option>광주</option>
		<option>대구</option>
		<option>대전</option>
		<option>부산</option>
		<option>서울</option>
		<option>인천</option>
		<option>전남</option>
		<option>전북</option>
		<option>제주</option>
		<option>충북</option>
	</select>

	<button @click="orderBy('planViews')">조회순</button>
	<button @click="orderBy('planUsedCount')">카피순</button>
	<hr />
	<h1>지역: {{ planDestination }}</h1>
	<h1>검색: {{ searchPlan }}</h1>
	<div
		@click="intoPlan(value)"
		id="plans"
		v-for="(value, index) in filteredPlanList"
		:key="index"
	>
		{{ value.teamCode.teamName }}팀의{{ value.planName }} 플랜 <br />조회수:
		{{ value.planViews }}
		<br />
		카피수:
		{{ value.planUsedCount }}
		<br />
		지역:{{ value.planDestination }}
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
#plans {
	width: 200px;
	height: 120px;
	background-color: #f5d682;
	border: 1px solid red;
}
</style>
