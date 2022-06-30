<template>
	<!-- 커핏됐나요? -->
	<div class="back">
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
		<h1>
			{{ this.$store.state.planCode.address }}
			<h4>{{ this.$store.state.planCode.detailAddress }}</h4>
		</h1>

		<h2>
			{{ this.$store.state.planCode.planTotalDate - 1 }}박{{
				this.$store.state.planCode.planTotalDate
			}}일
		</h2>
		<div class="frame">
			<div class="dateWrap">
				<div
					class="dates"
					v-for="index in this.$store.state.planCode.planTotalDate"
					:key="index"
				>
					<button
						class="w-btn w-btn-blue8"
						@click="datesButton(index)"
					>
						{{ index }}일차
					</button>
				</div>
			</div>
			<hr />

			<div class="detailPlan">
				<h1>{{ dateIndex }}일차 계획</h1>
				<p>시작 <input type="time" v-model="detailStart" /><br /></p>
				<p>종료<input type="time" v-model="detailEnd" /></p>
				<p>
					Name
					<input
						type="text"
						v-model="detailName"
						placeholder="일정 이름을 입력하세요"
					/>
				</p>

				Memo<input
					type="text"
					v-model="detailMemo"
					placeholder="메모를 입력하세요"
				/>
				<div v-for="(value, index) in showChecklist" :key="index">
					<h2>{{ value }}</h2>
				</div>
				<button class="noBorder" @click="insert">삽입</button>
			</div>
			<br />
			<hr />

			<div class="planTable">
				<table align="center">
					<thead>
						<th bgcolor="skybule">{{ dateIndex }}일차</th>
						<th>시작</th>
						<th>종료</th>
						<th>이름</th>
						<th>메모</th>
						<th colspan="2">체크리스트</th>
					</thead>
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
							<span
								v-for="(value1, index) in allCheckList"
								:key="index"
							>
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
							<div class="twoBtn">
								<button
									@click="insertChecklist(value.detailCode)"
								>
									checkList
								</button>
								<button @click="deleteDetailPlan(value)">
									일정 삭제
								</button>
							</div>
						</td>
					</tr>
					<tr class="a">
						<td style="border-bottom: none" colspan="7">
							<button
								class="w-btn-outline w-btn-red-outline8"
								@click="savePlan"
							>
								플랜저장
							</button>
							<button
								class="w-btn-outline w-btn-red-outline8"
								@click="editPlan"
							>
								플랜수정
							</button>
						</td>
					</tr>
				</table>
			</div>
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
			this.allCheckList.length = 0;
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
						'background-color: rgba(80, 80, 80, 80) ; color: skyblue;border:none; border-radius: 20px;';
				} else {
					ele.style =
						'color: white; background-color: skyblue; border:none; border-radius: 20px;';
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
					this.loadAllCheckList();
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
		editPlan: function () {
			this.$router.push({ name: 'editBasicPlan' });
			console.log(this.$store.state.planCode);
		},
	},
};
</script>

<style>
.color_n {
	background-color: rgba(224, 76, 76, 0);
	color: skyblue;
	border-radius: 4px;
	margin: 5px;
	box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
	max-width: 100px;
	min-width: 80px;
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
}
.twoBtn {
	background-size: 80px;
}
.sidebar {
	color: black;
	background-color: var(--sidebar-bg-color);
	float: left;
	z-index: 1;
	height: auto;
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
.w-btn-blue8 {
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
.w-btn-red-outline8 {
	border: 3px solid #ff5f2e;
	color: #ff5f2e;
	background-color: #f3f3f3;

}
.w-btn-red-outline8:hover {
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
</style>
