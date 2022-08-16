<template>
	<!-- 커핏됐나요? -->
	<div class="container">
    <div style="display: flex; margin-left: 20px; margin-bottom: 30px">
      <div style="border-radius: 15px 15px 15px 0;border: 3px solid #154416; padding: 0.5em 0.5em; display: flex">
        <h3 class="teamNameH3">{{this.$store.state.teamCode.teamCode.teamName}} </h3>
      </div>
      <h4 class="teamNameH4">팀의</h4>
      <div style="border-radius: 15px 15px 15px 0;border: 3px solid #154416; padding: 0.5em 0.5em; display: flex; margin-right: 10px">
        <h4 class="teamNameH3" style="margin-top: 10px; color: #e87c26">{{ this.$store.state.planCode.planTotalDate - 1 }}박{{
            this.$store.state.planCode.planTotalDate
          }}일</h4>
      </div>
      <div style="border-radius: 15px 15px 15px 0;border: 3px solid #154416; padding: 0.5em 0.5em; display: flex">
        <h3 class="teamNameH3">{{this.$store.state.planCode.planName}}</h3>
      </div>
      <h4 class="teamNameH4">플랜</h4>
    </div>

    <div style="display: flex; width: 1200px; height: 500px">
  <!--		<div class="sidebar" :style="{ width: sidebarWidth }">-->
  <!--			<h2>-->
  <!--				<span v-if="collapsed">-->
  <!--					<div>C</div>-->
  <!--				</span>-->
  <!--				<span v-else>체크리스트</span>-->
  <!--			</h2>-->
  <!--			<div>-->
  <!--				<button-->
  <!--					v-for="(value, index) in allCheckList"-->
  <!--					:key="index"-->
  <!--					title="더블클릭하면 삭제됩니다"-->
  <!--					:class="[-->
  <!--						{ color_n: value.checkState == 'n' },-->
  <!--						{ color_y: value.checkState == 'y' },-->
  <!--					]"-->
  <!--					@click="updateState(index)"-->
  <!--					@dblclick="deleteChecklist(value)"-->
  <!--				>-->
  <!--					{{ value.checkContent }}-->
  <!--				</button>-->
  <!--			</div>-->
  <!--			<span-->
  <!--				class="collapse-icon"-->
  <!--				:class="{ 'rotate-180': collapsed }"-->
  <!--				@click="toggleSidebar"-->
  <!--			>-->
  <!--				<i class="fas fa-angle-double-left"> 〈〈 </i>-->
  <!--			</span>-->
  <!--		</div>-->
  <!--		<h1>-->
  <!--			{{ this.$store.state.planCode.address }}-->
  <!--			<h4>{{ this.$store.state.planCode.detailAddress }}</h4>-->
  <!--		</h1>-->

  <!--		<h2>-->
  <!--			{{ this.$store.state.planCode.planTotalDate - 1 }}박{{-->
  <!--				this.$store.state.planCode.planTotalDate-->
  <!--			}}일-->
  <!--		</h2>-->
      <div>
      <div style="width: 200px; margin-left: 60px; margin-top: 10px; display: flex">
        <div
            v-for="index in this.$store.state.planCode.planTotalDate"
            :key="index"
            style="display: flex; width: 100px; height: 40px; margin-bottom: 10px"
        >
          <button
              class="btn btn-outline-success"
                @click="datesButton(index)"
            >
              {{ index }}
            </button>
            <p style="margin-top: 7px; width: 50px; margin-left: 5px">일차</p>
          </div>
        </div>
    <!--		<div class="frame">-->
    <!--			<div class="dateWrap">-->
    <!--				<div-->
    <!--					class="dates"-->
    <!--					v-for="index in this.$store.state.planCode.planTotalDate"-->
    <!--					:key="index"-->
    <!--				>-->
    <!--					<button-->
    <!--						class="w-btn w-btn-blue8"-->
    <!--						@click="datesButton(index)"-->
    <!--					>-->
    <!--						{{ index }}일차-->
    <!--					</button>-->
    <!--				</div>-->
    <!--			</div>-->
    <!--			<hr />-->
        <div>
          <div style="width: 400px; height: 300px">
            <table style="margin-left: 40px; text-align: center">
              <tr>
                <th>시작</th>
                <td><input type="time" v-model="detailStart" class="form-control"/></td>
              </tr>
              <tr>
                <th>종료</th>
                <td><input type="time" v-model="detailEnd" class="form-control"/></td>
              </tr>
              <tr>
                <th>일정 이름</th>
                <td><input
                    type="text"
                    v-model="detailName"
                    placeholder="일정 이름을 입력하세요"
                    class="form-control"
                /></td>
              </tr>
              <tr>
                <th>메모</th>
                <td><input
                    type="text"
                    v-model="detailMemo"
                    placeholder="메모를 입력하세요"
                    class="form-control"
                /></td>
              </tr>
            </table>
            <div style="margin-left: 450px; margin-top: -50px; width: 100px">
              <button class="btn btn-success" @click="insert">삽입</button>
            </div>
          </div>
        </div>
      </div>


      <div class="d-flex flex-column align-items-stretch flex-shrink-0 bg-white" style="width: 505px; border-radius: 0.6em; margin-left: 250px; height: 350px; border: 1px solid #ffb059">
        <a class="d-flex align-items-center flex-shrink-0 p-3 link-dark text-decoration-none" style="border-bottom: 1px solid #ffb059;  background-color: #ffa824; border-radius: 0.6em;" >
          <svg class="bi pe-none me-2" width="30" height="24"><use xlink:href="#people"/></svg>
          <span class="fs-5 fw-semibold" style="color:white;">Checklist</span>
        </a>
        <div class="list-group list-group-flush scrollarea"  style="overflow: scroll; width: 500px; height: 350px; margin-top: 10px; margin-bottom: 5px">
          <div class="list-group-item py-3 lh-sm" aria-current="true" style="border: none">
            <div >
              <button
                					v-for="(value, index) in allCheckList"
                					:key="index"
                					title="더블클릭하면 삭제됩니다"
                					:class="[
                						{ color_n2: value.checkState == 'n' },
                						{ color_y2: value.checkState == 'y' },
                					]"
                					@click="updateState(index)"
                					@dblclick="deleteChecklist(value)"
                				>
                					{{ value.checkContent }}
                				</button>
            </div>
          </div>
        </div>
      </div>
    </div>

<!--			<div class="detailPlan">-->
<!--				<h1>{{ dateIndex }}일차 계획</h1>-->
<!--				<p>시작 <input type="time" v-model="detailStart" /><br /></p>-->
<!--				<p>종료<input type="time" v-model="detailEnd" /></p>-->
<!--				<p>-->
<!--					Name-->
<!--					<input-->
<!--						type="text"-->
<!--						v-model="detailName"-->
<!--						placeholder="일정 이름을 입력하세요"-->
<!--					/>-->
<!--				</p>-->

<!--				Memo<input-->
<!--					type="text"-->
<!--					v-model="detailMemo"-->
<!--					placeholder="메모를 입력하세요"-->
<!--				/>-->
<!--				<div v-for="(value, index) in showChecklist" :key="index">-->
<!--					<h2>{{ value }}</h2>-->
<!--				</div>-->
<!--				<button class="noBorder" @click="insert">삽입</button>-->
<!--			</div>-->
<!--			<br />-->
<!--			<hr />-->

			<div>
        <div style="border-radius: 15px 15px 15px 0; border: 3px solid #154416; background-color: #154416; margin-bottom: 20px; padding: 0.5em 0.5em; width: 200px; margin-left: 30px">
          <h3 class="teamNameH3" style="color: white">{{ dateIndex }}일차 계획 </h3>
        </div>
<!--        <h1>{{ dateIndex }}일차 계획</h1>-->
				<table style="margin-left: 30px; margin-bottom: 50px">
					<thead>
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
						<td>{{ value.detailStart }}</td>
						<td>{{ value.detailEnd }}</td>
						<td>{{ value.detailName }}</td>
						<td>{{ value.detailMemo }}</td>
						<td style="width: 550px">
							<span
								v-for="(value1, index) in allCheckList"
								:key="index"
							>
								<button
									v-if="value1.detailCode == value.detailCode"
                  style="margin: 2px"
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
                  class="btn btn-outline-warning"
                  style="padding: 2px; width: 80px; margin-bottom: 2px"
								>
									checkList
								</button>
								<button @click="deleteDetailPlan(value)"
                        class="btn btn-outline-warning"
                        style="padding: 2px; width: 80px">
									일정 삭제
								</button>
							</div>
						</td>
					</tr>
<!--					<tr>-->
<!--						<td style="border-bottom: none" colspan="7">-->
<!--							<button-->
<!--								class="w-btn-outline w-btn-red-outline8"-->
<!--								@click="savePlan"-->
<!--							>-->
<!--								플랜저장-->
<!--							</button>-->
<!--							<button-->
<!--								class="w-btn-outline w-btn-red-outline8"-->
<!--								@click="editPlan"-->
<!--							>-->
<!--								플랜수정-->
<!--							</button>-->
<!--						</td>-->
<!--					</tr>-->
				</table>
        <div style="margin-left: 1100px">
          <button
              class="btn btn-warning"
              @click="editPlan"

          >
            플랜수정
          </button>
          <button
              class="btn btn-warning"
              @click="savePlan"
              style="margin-left: 20px"
          >
            플랜저장
          </button>
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
.teamNameH3 {
  font-family: '휴먼둥근헤드라인', cursive;
  color: #218633;
  margin-top: 5px;
  margin-left: 10px;
  margin-right: 10px;
}

.teamNameH4{
  margin-top: 30px;
  margin-left: 10px;
  margin-right: 20px;
  font-weight: bold;
}

table {
  border: 1px #336434 solid;
  font-size: .9em;
  box-shadow: 0 2px 5px rgba(0,0,0,.25);
  width: 100%;
  border-collapse: collapse;
  border-radius: 5px;
  overflow: hidden;
}

th {
  width: 150px;
  background: #336434;
  font-weight: bold;
  color: #fff;
}


td, th {
  padding: 1em .5em;
  vertical-align: middle;
}

td {
  border-bottom: 1px solid rgba(0,0,0,.1);
  background: #fff;
}

/*table {*/
/*  border: 1px #336434 solid;*/
/*  font-size: .9em;*/
/*  box-shadow: 0 2px 5px rgba(0,0,0,.25);*/
/*  width: 100%;*/
/*  border-collapse: collapse;*/
/*  border-radius: 5px;*/
/*  overflow: hidden;*/
/*}*/




.color_n {
	background-color: white;
	color: #33831c;
	border-radius: 4px;
	margin: 2px;
	max-width: 120px;
	min-width: 80px;
	overflow: hidden;
	height: 40px;
}
.color_y {
	color: white;
	background-color: #33831c;
	border-radius: 4px;
	margin: 2px;
	overflow: hidden;
	max-width: 120px;
	min-width: 80px;
	height: 40px;
}

.color_n2 {
  background-color: white;
  color: #fda14a;
  border-radius: 4px;
  margin: 2px;
  max-width: 120px;
  min-width: 80px;
  overflow: hidden;
  height: 40px;
}
.color_y2 {
  color: black;
  background-color: #ffbd37;
  border-radius: 4px;
  margin: 2px;
  overflow: hidden;
  max-width: 120px;
  min-width: 80px;
  height: 40px;
}


.bi {
  vertical-align: -.125em;
  fill: currentColor;
}

.btn-toggle-nav a {
  padding: .1875rem .5rem;
  margin-top: .125rem;
  margin-left: 1.25rem;
}
.btn-toggle-nav a:hover,
.btn-toggle-nav a:focus {
  background-color: #d2f4ea;
}

.scrollarea {
  overflow-y: auto;
}


/*td {*/
/*	border-top: 1px solid black;*/
/*	height: 90px;*/
/*}*/
/*:root {*/
/*	--sidebar-bg-color: #e6f4ff;*/
/*	--sidebar-item-hover: #b2e2fd;*/
/*}*/
</style>

<!--<style scoped>-->
<!--.frame {-->
<!--	display: flex;-->
<!--	background-color: rgb(247, 246, 230);-->
<!--	align-content: stretch;-->
<!--	width: 70%;-->
<!--	height: 100%;-->
<!--	flex-wrap: wrap;-->
<!--	padding-bottom: auto;-->
<!--	margin: auto;-->
<!--	border-radius: 20px;-->
<!--	padding-top: 50px;-->
<!--	padding-bottom: 50px;-->
<!--}-->
<!--.twoBtn {-->
<!--	background-size: 80px;-->
<!--}-->
<!--.sidebar {-->
<!--	color: black;-->
<!--	background-color: var(&#45;&#45;sidebar-bg-color);-->
<!--	float: left;-->
<!--	z-index: 1;-->
<!--	height: auto;-->
<!--	left: 0;-->
<!--	bottom: 0;-->
<!--	padding-bottom: 50%;-->
<!--	margin-right: 3%;-->
<!--	transition: 0.3s ease;-->
<!--	display: flex;-->
<!--	flex-direction: column;-->
<!--}-->
<!--.sidebar h5 {-->
<!--	margin-left: 10%;-->
<!--	margin-top: 15%;-->
<!--	margin-bottom: 3%;-->
<!--}-->

<!--.collapse-icon {-->
<!--	position: absolute;-->
<!--	top: 80%;-->
<!--	padding: 0.75em;-->
<!--	color: black;-->
<!--	transition: 0.2s linear;-->
<!--}-->
<!--.rotate-180 {-->
<!--	transform: rotate(180deg);-->
<!--	transition: 0.2s linear;-->
<!--}-->
<!--.w-btn {-->
<!--	position: relative;-->
<!--	border: none;-->
<!--	display: inline-block;-->
<!--	border-radius: 5px;-->
<!--	text-align: center;-->
<!--	font-family: 'paybooc-Light', sans-serif;-->
<!--	box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);-->
<!--	text-decoration: none;-->
<!--	font-weight: 600;-->
<!--	transition: 0.25s;-->
<!--	margin-left: 10px;-->
<!--	margin-right: 5px;-->
<!--	width: 60px;-->
<!--	height: 60px;-->
<!--}-->
<!--.w-btn-blue8 {-->
<!--	background-color: #6aafe6;-->
<!--	color: #d4dfe6;-->
<!--}-->
<!--.w-btn:hover {-->
<!--	letter-spacing: 2px;-->
<!--	transform: scale(1.2);-->
<!--	cursor: pointer;-->
<!--}-->
<!--.w-btn:active {-->
<!--	transform: scale(1.5);-->
<!--}-->
<!--.dates {-->
<!--	display: inline-block;-->
<!--	float: left;-->
<!--	padding-bottom: 30px;-->
<!--}-->
<!--.dateWrap {-->
<!--	display: block;-->
<!--	width: 100%;-->
<!--}-->
<!--.detailPlan {-->
<!--	display: block;-->
<!--	width: 100%;-->
<!--}-->
<!--.planTable {-->
<!--	padding-top: 30px;-->
<!--	margin: auto;-->
<!--	display: block;-->
<!--	width: 90%;-->
<!--}-->

<!--.detailPlan input {-->
<!--	border-radius: 7px;-->
<!--}-->

<!--.w-btn-outline {-->
<!--	padding: 10px 20px;-->
<!--	border-radius: 15px;-->
<!--	font-family: 'paybooc-Light', sans-serif;-->
<!--	box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);-->
<!--	text-decoration: none;-->
<!--	font-weight: 600;-->
<!--	transition: 0.25s;-->
<!--}-->
<!--.w-btn-red-outline8 {-->
<!--	border: 3px solid #ff5f2e;-->
<!--	color: #ff5f2e;-->
<!--	background-color: #f3f3f3;-->

<!--}-->
<!--.w-btn-red-outline8:hover {-->
<!--	background-color: #ff5f2e;-->
<!--	color: #e1eef6;-->
<!--}-->
<!--.w-btn-outline:hover {-->
<!--	letter-spacing: 2px;-->
<!--	transform: scale(1.2);-->
<!--	cursor: pointer;-->
<!--}-->
<!--.w-btn-outline:active {-->
<!--	transform: scale(1.5);-->
<!--}-->
<!--.noBorder {-->
<!--	background-color: rgba(0, 0, 0, 0);-->
<!--	margin-left: 15px;-->
<!--	border: none;-->
<!--	font-weight: 800;-->
<!--}-->
<!--button {-->
<!--	border: none;-->
<!--}-->
<!--.back {-->
<!--	text-align: center;-->
<!--	background-image: url(@/assets/campwall2.webp);-->
<!--	background-size: 100%;-->
<!--	background-repeat: repeat-y;-->
<!--	padding-bottom: 50px;-->
<!--	height: 100%;-->
<!--}-->
<!--.a {-->
<!--	border: none;-->
<!--}-->
<!--.a button {-->
<!--	margin-left: 20px;-->
<!--}-->
<!--</style>-->
