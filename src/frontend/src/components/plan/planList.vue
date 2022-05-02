<template>
	<h2>검색</h2>
	<input type="text" v-model="searchPlan" placeholder="키워드를 입력하세요" />
	<h2>지역설정</h2>
	<select v-model="planDestination">
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

	<div id="plans" v-for="(value, index) in planList" :key="index">
		플랜이름: {{ value.planName }} 조회수: {{ value.planViews }} 카피수:
		{{ value.planUsedCount }}
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
			planList: [],
		};
	},
	methods: {
		loadAllPlans: function () {
			const url = '/api/loadAllPlans';

			axios
				.get(url)
				.then((response) => {
					response.data.map((item) => {
						console.log(item);
						this.planList.push(item);
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
	width: 100px;
	height: 100px;
	background-color: #f5d682;
	border: 1px solid red;
}
</style>
