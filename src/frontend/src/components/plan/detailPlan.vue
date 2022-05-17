<template>
	<h2>일정작성</h2>
	<h2>
		{{ this.$store.state.planCode.planTotalDate - 1 }}박{{
			this.$store.state.planCode.planTotalDate
		}}일
	</h2>

	<div v-for="index in this.$store.state.planCode.planTotalDate" :key="index">
		<button @click="datesButton(index)">{{ index }}</button>일차
		<br />
	</div>
	<div>
		<button
			v-for="(value, index) in allCheckList"
			:key="index"
			:class="[
				{ color_n: value.checkState == 'n' },
				{ color_y: value.checkState == 'y' },
			]"
			@click="updateState(value, $event)"
			@dblclick="deleteChecklist(value)"
		>
			{{ value.checkContent }}
		</button>
	</div>

	<hr />
	<h1>{{ dateIndex }}일차 계획</h1>
	시작
	<input type="time" v-model="detailStart" /><br />
	종료
	<input type="time" v-model="detailEnd" />
	<br />
	Name<input
		type="text"
		v-model="detailName"
		placeholder="일정 이름을 입력하세요"
	/>
	<br />

	Memo<input
		type="text"
		v-model="detailMemo"
		placeholder="메모를 입력하세요"
	/>
	<br />

	<div v-for="(value, index) in showChecklist" :key="index">
		<h2>{{ value }}</h2>
	</div>
	<button @click="insert">삽입</button>
	<hr />

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
							title="더블클릭하면 삭제됩니다"
							:class="[
								{ color_n: value1.checkState == 'n' },
								{ color_y: value1.checkState == 'y' },
							]"
							@click="updateState(value1, $event)"
							@dblclick="deleteChecklist(value1)"
						>
							{{ value1.checkContent }}
						</button>
					</div>
				</td>
				<td>
					<button @click="insertChecklist(value.detailCode)">
						checkList
					</button>
					<button @click="deleteDetailPlan()">일정 삭제</button>
				</td>
			</tr>
		</table>

		<div>
			<button @click="savePlan">플랜저장</button>
		</div>
	</div>
</template>

<script>
import axios from 'axios';
export default {
	created() {
		this.loadDetailPlanOfDay(1);
		this.loadAllCheckList();
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
			allCheckList: [],
		};
	},
	mounted() {},
	methods: {
		loadAllCheckList: function () {
			const url = 'api/loadAllCheckList';
			axios
				.get(url, {
					params: { planCode: this.$store.state.planCode.planCode },
				})
				.then((response) => {
					response.data.map((item) => {
						this.allCheckList.push(item);
						console.log(this.allCheckList);
					});
				})
				.catch((error) => {});
		},
		colorSetting: function () {
			let a = document.body.getElementsByClassName('checkList');
			Array.from(a).forEach(function (ele) {
				console.log('hi there');
				if (ele.checkState === 'n') {
					ele.style =
						'background-color: rgba(0, 0, 0, 0) ; color: skyblue;';
				} else {
					ele.style = 'color: white; background-color: skyblue;';
				}
			});
			console.log(a);
		},
		deleteChecklist: function (checkListCode) {
			const url = '/api/deleteChecklist';
			axios
				.delete(url, { data: checkListCode })
				.then((response) => {
					this.loadDetailPlanOfDay(this.dateIndex);
				})
				.catch((error) => {});
		},

		updateState(checkListCode, event) {
			console.log(event.target);
			this.test = checkListCode;
			const url = '/api/updateState';
			axios
				.put(url, checkListCode)
				.then((response) => {
					console.log(response.data);
					if (response.data.checkState === 'n') {
						event.target.style =
							'background-color: rgba(0, 0, 0, 0) ; color: skyblue;';
					} else {
						event.target.style =
							'color: white; background-color: skyblue;';
					}
				})
				.catch((error) => {
					console.log(error);
				});
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
		insertChecklist: function (detailCode) {
			const planDetail = { detailCode: detailCode };
			console.log(planDetail);
			const checkContent = prompt('체크리스트를 추가하세요!', 'ex) 휴지');
			const url = '/api/insertChecklist';

			axios
				.put(url, {
					detailCode: planDetail,
					checkContent: checkContent,
				})
				.then((response) => {
					console.log('성공');
					this.loadDetailPlanOfDay(this.dateIndex);
				})
				.catch((error) => {
					console.log(error);
				});
		},

		insert: function () {
			const url = '/api/insertDetailPlan';
			axios
				.put(url, {
					detailStart: this.detailStart,
					detailEnd: this.detailEnd,
					detailMemo: this.detailMemo,
					detailDay: this.dateIndex,
					planCode: this.$store.state.planCode,
					detailName: this.detailName,
				})
				.then((response) => {
					this.loadDetailPlanOfDay(this.dateIndex);
					console.log('success');
				})
				.catch((error) => {
					console.log(error);
				});
		},
		savePlan: function () {
			this.$router.push({ name: 'teamMember' });
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
.checkList {
}
table,
th {
	border: 3px solid black;
}
td {
	border: 2px solid black;
}
</style>
