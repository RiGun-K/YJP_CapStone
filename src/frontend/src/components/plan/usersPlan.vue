<template>
	<div class="back">
		<div class="frame">
			<h1>
				{{ this.$store.state.teamCode.teamCode.teamName }}팀의
				{{ this.$store.state.planCode.planName }}플랜입니다
			</h1>

			<div class="count">
				<p>조회수: {{ this.$store.state.planCode.planViews }}</p>
				<p>사용 수: {{ this.$store.state.planCode.planUsedCount }}회</p>
			</div>
			<div class="basicInfo">
				<h2>
					예상 비용: {{ this.$store.state.planCode.planBudget }}원
				</h2>
				<h2>인원: {{ this.$store.state.planCode.planNumber }}명</h2>
				<h2>지역: {{ this.$store.state.planCode.planDestination }}</h2>
			</div>
			<h2>
				{{ this.$store.state.planCode.planTotalDate - 1 }}박{{
					this.$store.state.planCode.planTotalDate
				}}일
			</h2>

			<div
				v-for="index in this.$store.state.planCode.planTotalDate"
				:key="index"
			>
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
					<tr
						v-for="(value, index) in detailPlanOfDayList"
						:key="index"
					>
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
.color_n {
	background-color: rgba(224, 76, 76, 0);
	color: skyblue;
	border-radius: 4px;
	margin: 5px;
	box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
	max-width: 100px;
	min-width: 80px;
	transition: 0.1s;
	overflow: hidden;

	height: 40px;
}
.color_y {
	color: white;
	background-color: skyblue;
	border-radius: 4px;
	margin: 5px;
	box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
	overflow: hidden;
	max-width: 100px;
	min-width: 80px;
	height: 40px;
}
.color_n:hover {
	transform: scale(1.2);
	overflow: visible;
	border-radius: 14px;
}
.color_y:hover {
	transform: scale(1.2);
	overflow: visible;
	border-radius: 14px;
}
.checkList {
}
td {
	border-top: 1px solid black;
	height: 90px;
}
:root {
	--sidebar-bg-color: #e6f4ff;
	--sidebar-item-hover: #b2e2fd;
}
</style>

<style scoped>
.frame {
	display: flex;
	background-color: rgb(247, 246, 230);
	align-content: stretch;
	width: 70%;
	height: 100%;
	flex-wrap: wrap;
	padding-bottom: auto;
	margin: auto;
	border-radius: 20px;
	padding-top: 50px;
	padding-bottom: 50px;
	flex-direction: column;
}

.w-btn {
	position: relative;
	border: none;
	display: inline-block;
	border-radius: 5px;
	text-align: center;
	font-family: 'paybooc-Light', sans-serif;
	box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
	text-decoration: none;
	font-weight: 600;
	transition: 0.25s;
	margin-left: 10px;
	margin-right: 5px;
	width: 60px;
	height: 60px;
}
.w-btn-blue {
	background-color: #6aafe6;
	color: #d4dfe6;
}
.w-btn:hover {
	letter-spacing: 2px;
	transform: scale(1.2);
	cursor: pointer;
}
.w-btn:active {
	transform: scale(1.5);
}
.dates {
	display: inline-block;
	float: left;
	padding-bottom: 30px;
}
.dateWrap {
	display: block;
	width: 100%;
}
.detailPlan {
	display: block;
	width: 100%;
}
.planTable {
	padding-top: 30px;
	margin: auto;
	display: block;
	width: 90%;
}
.buttonDiv {
	background: red;
}
.buttonDiv button {
	float: right;
}
.detailPlan input {
	border-radius: 7px;
}

.w-btn-outline {
	padding: 10px 20px;
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
.noBorder {
	background-color: rgba(0, 0, 0, 0);
	margin-left: 15px;
	border: none;
	font-weight: 800;
}
button {
	border: none;
}
.back {
	text-align: center;
	background-image: url(@/assets/campwall2.webp);
	background-size: 100%;
	background-repeat: repeat-y;
	padding-bottom: 50px;
	height: 100%;
}
.a {
	border: none;
}
.a button {
	margin-left: 20px;
}
.basicInfo {
	margin-top: auto;
}
.count {
	float: left;
}
</style>
