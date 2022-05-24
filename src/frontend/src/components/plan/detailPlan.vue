<template>
	<div class="sidebar" :style="{ width: sidebarWidth }">
		<h2>
			<span v-if="collapsed">
				<div>C</div>
			</span>
			<span v-else>체크리스트</span>
		</h2>
		<div>
			<button
				v-for="(value, index) in allCheckList"
				:key="index"
				title="더블클릭하면 삭제됩니다"
				:class="[
					{ color_n: value.checkState == 'n' },
					{ color_y: value.checkState == 'y' },
				]"
				@click="updateState(index)"
				@dblclick="deleteChecklist(value)"
			>
				{{ value.checkContent }}
			</button>
		</div>
		<span
			class="collapse-icon"
			:class="{ 'rotate-180': collapsed }"
			@click="toggleSidebar"
		>
			<i class="fas fa-angle-double-left"> 〈〈 </i>
		</span>
	</div>
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
				<td>{{ value.detailCode }}</td>
				<td>{{ value.detailStart }}</td>
				<td>{{ value.detailEnd }}</td>
				<td>{{ value.detailName }}</td>
				<td>{{ value.detailMemo }}</td>
				<td>
					<span v-for="(value1, index) in allCheckList" :key="index">
						<button
							v-if="value1.detailCode == value.detailCode"
							title="더블클릭하면 삭제됩니다"
							:class="[
								{ color_n: value1.checkState == 'n' },
								{ color_y: value1.checkState == 'y' },
							]"
							@click="updateState(index)"
							@dblclick="deleteChecklist(value1)"
						>
							{{ value1.checkContent }}
						</button>
					</span>
				</td>
				<td>
					<button @click="insertChecklist(value.detailCode)">
						checkList
					</button>
					<button @click="deleteDetailPlan(value)">일정 삭제</button>
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
import {
	collapsed,
	toggleSidebar,
	sidebarWidth,
} from '@/components/cart/Sidebar/state';
import SidebarLink from '@/components/cart/Sidebar/SidebarLink';
export default {
	components: { SidebarLink },
	setup() {
		return { collapsed, toggleSidebar, sidebarWidth };
	},
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
	watch: {
		allCheckList: {
			deep: true,
			handler() {
				this.colorSetting();
				console.log(this.allCheckList);
			},
		},
	},
	mounted() {},
	methods: {
		deleteDetailPlan: function (detailCode) {
			const url = 'api/deleteDetailPlan';
			const delConfirm = confirm('정말로 삭제 하시겠습니까?');
			if (delConfirm) {
				axios
					.delete(url, {
						params: { planDetailCode: detailCode.detailCode },
					})
					.then((response) => {
						this.loadDetailPlanOfDay(this.dateIndex);
					})
					.catch((error) => {
						console.log(error);
					});
			}
		},
		loadAllCheckList: function () {
			const url = 'api/loadAllCheckList';
			axios
				.get(url, {
					params: { planCode: this.$store.state.planCode.planCode },
				})
				.then((response) => {
					console.log(response);
					response.data.map((item) => {
						console.log(item);
						this.allCheckList.push(item);
					});
				})
				.catch((error) => {});
			this.colorSetting();
		},
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

		updateState(index) {
			const temp = this.allCheckList[index].detailCode;
			delete this.allCheckList[index].detailCode;
			const url = '/api/updateState';
			axios
				.put(url, this.allCheckList[index])
				.then((response) => {
					console.log(response);
					this.allCheckList[index] = response.data;
					console.log(response.data);
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
						this.detailPlanOfDayList.push(item);
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
:root {
	--sidebar-bg-color: #e6f4ff;
	--sidebar-item-hover: #b2e2fd;
}
</style>

<style scoped>
.sidebar {
	color: black;
	background-color: var(--sidebar-bg-color);
	float: left;
	z-index: 1;
	height: 100%;
	left: 0;
	bottom: 0;
	padding-bottom: 50%;
	margin-right: 3%;
	transition: 0.3s ease;
	display: flex;
	flex-direction: column;
}
.sidebar h5 {
	margin-left: 10%;
	margin-top: 15%;
	margin-bottom: 3%;
}
.sidebar-link {
	color: black;
}
.collapse-icon {
	position: absolute;
	top: 80%;
	padding: 0.75em;
	color: black;
	transition: 0.2s linear;
}
.rotate-180 {
	transform: rotate(180deg);
	transition: 0.2s linear;
}
</style>
