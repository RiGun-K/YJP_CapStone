<template>
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

    <div style="display: flex">
      <div style="width: 400px; height: 500px">
        <table style="margin-left: 40px; text-align: center">
          <tr>
            <th>예상비용</th>
            <td>{{ this.$store.state.planCode.planBudget }}원</td>
          </tr>
          <tr>
            <th>인원</th>
            <td>{{ this.$store.state.planCode.planNumber }}명</td>
          </tr>
          <tr>
            <th>지역</th>
            <td>{{ this.$store.state.planCode.planDestination }}</td>
          </tr>
          <tr>
            <th>조회수 / 카피수</th>
            <td>{{ this.$store.state.planCode.planViews }} / {{ this.$store.state.planCode.planUsedCount }}회</td>
          </tr>
        </table>
      </div>

      <div style="width: 200px; margin-left: 60px; margin-top: 10px;">
        <div v-for="index in this.$store.state.planCode.planTotalDate" :key="index" style="display: flex; width: 100px; height: 40px; margin-bottom: 10px">
          <button class="btn btn-outline-success"  @click="datesButton(index)" >{{ index }}</button>
          <p style="margin-top: 7px; margin-left: 5px">일차</p>
        </div>
      </div>
    </div>

<!--			<div-->
<!--				v-for="index in this.$store.state.planCode.planTotalDate"-->
<!--				:key="index"-->
<!--			>-->
<!--				<button @click="datesButton(index)">{{ index }}</button>일차-->
<!--				<br />-->
<!--			</div>-->

    <div v-show="is_show" style="position: fixed; z-index: 4;">
      <div class="modal modal-signin d-block py-5" tabindex="-1" role="dialog" >
        <div class="modal-dialog" role="document" style="margin-left: 870px; margin-top: 200px;">
          <div class="modal-content rounded-4 shadow" style="width: 700px">
            <div class="modal-header p-5 pb-4 border-bottom-0">
              <h2 class="fw-bold mb-0">{{ dateIndex }}일차 계획</h2>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" @click="isShowBtn"></button>
            </div>

            <div class="modal-body p-5 pt-0">
              <form class="">
                <div class="form-floating mb-3">
                  <table>
                    <thead>
                    <tr>
                      <th style="width: 60px">시간</th>
                      <th style="width: 70px">이름</th>
                      <th style="width: 100px">메모</th>
                      <th>체크리스트</th>
                    </tr>
                    </thead>
                    <tbody>
                      <tr v-for="(value, index) in detailPlanOfDayList" :key="index">
                        <td>{{ value.detailStart }} ~ {{ value.detailEnd }}</td>
                        <td>{{ value.detailName }}</td>
                        <td>{{ value.detailMemo }}</td>
                        <td>
                          <div>
                            <button v-for="(value1, index) in value.checklists" type="button" class="btn btn-success btn-sm"
                            style="margin-right: 2px; margin-bottom: 2px">{{ value1.checkContent }}</button>
                          </div>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>

                <small class="text-muted">By clicking Button, you agree to the terms of use.</small>
                <button class="w-100 mb-2 btn btn-lg rounded-3 btn-outline-warning" id="button-addon2" type="button" @click="copyPlan">내 플랜에 적용</button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>

<!--			<h1>{{ dateIndex }}일차 계획</h1>-->

<!--			<div v-for="(value, index) in showChecklist" :key="index">-->
<!--				<h2>{{ value }}</h2>-->
<!--			</div>-->
<!--			<div>-->
<!--				<table border="1" bordercolor="blue" align="center">-->
<!--					<th bgcolor="skybule">{{ dateIndex }}일차</th>-->
<!--					<th>시작</th>-->
<!--					<th>종료</th>-->
<!--					<th>이름</th>-->
<!--					<th>메모</th>-->
<!--					<th>체크리스트</th>-->
<!--					<tr-->
<!--						v-for="(value, index) in detailPlanOfDayList"-->
<!--						:key="index"-->
<!--					>-->
<!--						<td></td>-->
<!--						<td>{{ value.detailStart }}</td>-->
<!--						<td>{{ value.detailEnd }}</td>-->
<!--						<td>{{ value.detailName }}</td>-->
<!--						<td>{{ value.detailMemo }}</td>-->
<!--						<td>-->
<!--							<div>-->
<!--								<button-->
<!--									v-for="(value1, index) in value.checklists"-->
<!--									:key="index"-->
<!--									:class="[-->
<!--										{ color_n: value1.checkState == 'n' },-->
<!--										{ color_y: value1.checkState == 'y' },-->
<!--									]"-->
<!--								>-->
<!--									{{ value1.checkContent }}-->
<!--								</button>-->
<!--							</div>-->
<!--						</td>-->
<!--					</tr>-->
<!--				</table>-->
<!--				<div>-->
<!--					<button @click="copyPlan">내 플랜에 적용</button>-->
<!--				</div>-->
<!--		</div>-->
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
      is_show: false,
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
    isShowBtn: function (){
      this.is_show = !this.is_show;
    },
		datesButton: function (index) {
			this.detailPlanOfDayList.length = 0;
			this.dateIndex = index;
			this.loadDetailPlanOfDay(index);
      this.isShowBtn();
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

<style scoped>
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

a {
  color: #73685d;
}

@media all and (max-width: 768px) {

  table, thead, tbody, th, td, tr {
    display: block;
  }

  th {
    text-align: right;
  }

  table {
    position: relative;
    padding-bottom: 0;
    border: none;
    box-shadow: 0 0 10px rgba(0,0,0,.2);
  }

  thead {
    float: left;
    white-space: nowrap;
  }

  tbody {
    overflow-x: auto;
    overflow-y: hidden;
    position: relative;
    white-space: nowrap;
  }

  tr {
    display: inline-block;
    vertical-align: top;
  }

  th {
    border-bottom: 1px solid #a39485;
  }

  td {
    border-bottom: 1px solid #e5e5e5;
  }
}


/*.color_n {*/
/*	background-color: rgba(0, 0, 0, 0);*/
/*	color: skyblue;*/
/*}*/
/*.color_y {*/
/*	color: white;*/
/*	background-color: skyblue;*/
/*}*/

/*table,*/
/*th {*/
/*	border: 3px solid black;*/
/*}*/
/*td {*/
/*	border: 2px solid black;*/
/*}*/
/*.color_n {*/
/*	background-color: rgba(224, 76, 76, 0);*/
/*	color: skyblue;*/
/*	border-radius: 4px;*/
/*	margin: 5px;*/
/*	box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);*/
/*	max-width: 100px;*/
/*	min-width: 80px;*/
/*	transition: 0.1s;*/
/*	overflow: hidden;*/

/*	height: 40px;*/
/*}*/
/*.color_y {*/
/*	color: white;*/
/*	background-color: skyblue;*/
/*	border-radius: 4px;*/
/*	margin: 5px;*/
/*	box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);*/
/*	overflow: hidden;*/
/*	max-width: 100px;*/
/*	min-width: 80px;*/
/*	height: 40px;*/
/*}*/
/*.color_n:hover {*/
/*	transform: scale(1.2);*/
/*	overflow: visible;*/
/*	border-radius: 14px;*/
/*}*/
/*.color_y:hover {*/
/*	transform: scale(1.2);*/
/*	overflow: visible;*/
/*	border-radius: 14px;*/
/*}*/
/*.checkList {*/
/*}*/
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
<!--	flex-direction: column;-->
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
<!--.w-btn-blue {-->
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
<!--.buttonDiv {-->
<!--	background: red;-->
<!--}-->
<!--.buttonDiv button {-->
<!--	float: right;-->
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
<!--.w-btn-red-outline {-->
<!--	border: 3px solid #ff5f2e;-->
<!--	color: #6e6e6e;-->
<!--}-->
<!--.w-btn-red-outline:hover {-->
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
<!--.basicInfo {-->
<!--	margin-top: auto;-->
<!--}-->
<!--.count {-->
<!--	float: left;-->
<!--}-->
<!--</style>-->
