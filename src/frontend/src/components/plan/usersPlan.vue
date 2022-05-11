<template>
	<h1>
		{{ this.$store.state.teamCode.teamCode.teamName }}팀의
		{{ this.$store.state.planCode.planName }}플랜입니다
	</h1>
	<div>
		<h2>조회수: {{ this.$store.state.planCode.planViews }}</h2>
		<h2>사용 수: {{ this.$store.state.planCode.planUsedCount }}회</h2>
		<h2>예상 비용: {{ this.$store.state.planCode.planBudget }}원</h2>
		<h2>인원: {{ this.$store.state.planCode.planNumber }}명</h2>
		<h2>지역: {{ this.$store.state.planCode.planDestination }}</h2>
	</div>
	<h2>
		{{ this.$store.state.planCode.planTotalDate - 1 }}박{{
			this.$store.state.planCode.planTotalDate
		}}일
	</h2>

	<div v-for="index in this.$store.state.planCode.planTotalDate" :key="index">
		<button @click="datesButton(index)">{{ index }}</button>일차
		<br />
	</div>

	<hr />
	<h1>{{ dateIndex }}일차 계획</h1>

	<div v-for="(value, index) in showChecklist" :key="index">
		<h2>{{ value }}</h2>
	</div>
	<div>
		<table border="1" bordercolor="blue" align="center">
			<th bgcolor="skybule">{{ dateIndex }}일차</th>
			<th>시작</th>
			<th>종료</th>
			<th>이름</th>
			<th>메모</th>
			<th>체크리스트</th>
			<tr v-for="(value, index) in detailPlanOfDayList" :key="index">
				<td></td>
				<td>{{ value.detailStart }}</td>
				<td>{{ value.detailEnd }}</td>
				<td>{{ value.detailName }}</td>
				<td>{{ value.detailMemo }}</td>
				<td>
					<div>
						<button
							v-for="(value1, index) in value.checklists"
							:key="index"
							:class="[
								{ color_n: value1.checkState == 'n' },
								{ color_y: value1.checkState == 'y' },
							]"
						>
							{{ value1.checkContent }}
						</button>
					</div>
				</td>
			</tr>
		</table>
		<div>
			<button @click="copyPlan">내 플랜에 적용</button>
		</div>
	</div>
</template>

<script>
import axios from 'axios';
export default {
	created() {
		this.loadDetailPlanOfDay(1);
	},
	data() {
		return {
			detailStart: '',
			detailEnd: '',
			planList: '',
			detailName: '',
			detailMemo: '',
			detailDay: '',
			dateIndex: '',
			putChecklist: '',
			showChecklist: [],
			detailPlanOfDayList: [],
			isUnclicked: true,
			isClicked: false,
			planCode: '',
		};
	},
	mounted() {},
	methods: {
		colorSetting: function () {
			let a = document.body.getElementsByClassName('checkList');
			Array.from(a).forEach(function (ele) {
				if (ele.checkState === 'n') {
					ele.style =
						'background-color: rgba(0, 0, 0, 0) ; color: skyblue;';
				} else {
					ele.style = 'color: white; background-color: skyblue;';
				}
			});
			console.log(a);
		},

		datesButton: function (index) {
			this.detailPlanOfDayList.length = 0;
			this.dateIndex = index;
			this.loadDetailPlanOfDay(index);
		},
		loadDetailPlanOfDay: function (index) {
			this.dateIndex = index;
			const url = '/api/loadDetailPlanOfDay';
			this.detailPlanOfDayList.length = 0;
			axios
				.post(url, {
					detailDay: index,
					planCode: this.$store.state.planCode,
				})
				.then((response) => {
					response.data.map((item) => {
						console.log(item.checklists);
						this.detailPlanOfDayList.push(item);
						return console.log(item);
					});
				});
			this.colorSetting();
		},
		copyPlan: function () {
			const url = 'api/countUsed';
			axios
				.put(url, this.$store.state.planCode)
				.then((response) => {
					this.$router.push({ name: 'selectTeam' });
					console.log(this.$store.state.planCode);
				})
				.catch((error) => {});
		},
	},
};
</script>

<style>
.color_n {
	background-color: rgba(0, 0, 0, 0);
	color: skyblue;
}
.color_y {
	color: white;
	background-color: skyblue;
}

table,
th {
	border: 3px solid black;
}
td {
	border: 2px solid black;
}
</style>
