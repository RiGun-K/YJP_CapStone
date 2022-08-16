<template>
  <svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
    <symbol id="enter" viewBox="0 0 16 16">
      <path fill-rule="evenodd" d="M6 3.5a.5.5 0 0 1 .5-.5h8a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-.5.5h-8a.5.5 0 0 1-.5-.5v-2a.5.5 0 0 0-1 0v2A1.5 1.5 0 0 0 6.5 14h8a1.5 1.5 0 0 0 1.5-1.5v-9A1.5 1.5 0 0 0 14.5 2h-8A1.5 1.5 0 0 0 5 3.5v2a.5.5 0 0 0 1 0v-2z"/>
      <path fill-rule="evenodd" d="M11.854 8.354a.5.5 0 0 0 0-.708l-3-3a.5.5 0 1 0-.708.708L10.293 7.5H1.5a.5.5 0 0 0 0 1h8.793l-2.147 2.146a.5.5 0 0 0 .708.708l3-3z"/>
    </symbol>
  </svg>

  <div class="container">
    <div class="myTeam" style="display: flex; width:200px; justify-content: center; align-items: center">
      <h3 style="margin-top: 4%;">MY TEAM</h3>
      <a title="Button push blue/green" class="buttons btnPush btnBlueGreen" @Click="showingAddTeamForm">+</a>
    </div>
    <div v-if="addTeamForm" class="newTeam" style="display:flex; width: 500px; margin-left: 50px; margin-top: 30px; margin-bottom: 50px">
      <select class="form-select" aria-label="Default select example" v-model="open" style="width: 120px; margin-right: 10px">
        <option selected>전체공개</option>
        <option value="2">비공개</option>
      </select>
      <input class="form-control" v-model="$store.state.insertName" type="text" placeholder="Insert Team Name" aria-label="default input example"
      style="width: 300px; height: 45px">
      <a title="Button push blue/green" class="buttons2 btnPush btnBlueGreen" @click="teamSave">생성</a>
    </div>
    <div v-if="showingTeamList">
      <p v-html="noTeam" style="margin-top: 10px; margin-left: 35px; font-style: italic;"></p>
      <div style="display: flex">
        <div
            v-for="(value, index) in teamList"
            :key="index"
            class="teamList"
        >
          <button
              class="w-btn-gray"
              type="button"
              @click="loadTeamMemberList(value.teamCode)"
              :title="value.teamCode.teamName"
          >
            {{ value.teamCode.teamName }}
          </button>
        </div>
      </div>
      <div style="display: flex">
        <div
            v-for="(value, index) in unacceptedTeamCode"
            :key="index"
            style="display: inline-block"
        >
          <button
              class="w-btn-gra2 w-btn-gra-anim"
              type="button"
              @click="selectUnacceptedTeam(value.teamCode)"
              :title="value.teamCode.teamName"
          >
            {{ value.teamCode.teamName }}
          </button>
        </div>
        <div v-if="showingRequest" class="request">
          <div class="card" style="width: 600px; margin-left: 30px; border-radius: 1em; margin-top: 10px">
            <div class="card-body" style=" height: 100px; margin-top: 15px">
              <div style="display: flex; justify-content: center; align-items: center; margin-bottom: 10px">
                <h5 class="teamNameH5" style="margin-right: 10px">{{ this.requestTeamCode.teamName }}</h5>
                <h5 class="card-title">팀에서 요청이 왔습니다</h5>
              </div>
              <div style="margin-left: 460px">
                <a href="#" class="card-link" @click="accept(requestTeamCode.teamCode)" >수락</a>
                <a href="#" class="card-link" @click="refuse(requestTeamCode.teamCode)" style="color: #c02727">거절</a>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div v-if="showingTeamMember" class="teamMemberList" style="margin-top: 20px; margin-left: 15px">
        <div style="display: flex" class="teamNameStyle">
          <h3 class="teamNameH3">
            {{ $store.state.teamCode.teamCode.teamName }}
          </h3>
          <div class="aTag">
            <a :href="$store.state.teamURL" style="text-decoration: none; color: #989898">
              <svg class="bi d-block mx-auto mb-1" width="30" height="30"><use xlink:href="#enter"/></svg>
            </a>
          </div>
        </div>
          <div class="container px-4 py-5" id="featured-5" style="margin-top: -80px">
            <div class="row g-4 py-5 row-cols-1 row-cols-lg-5">
              <div class="feature col" style="border: 1px solid #e3e3e3; border-radius: 2em; padding: 20px; margin-right: 10px; height: 180px"
                   v-for="(value, index) in $store.state
								.teamMemberList"
                   :key="index">
                <div style="display: flex;">
                  <div class="feature-icon d-inline-flex align-items-center justify-content-center bg-gradient text-white fs-2 mb-3" style="background-color: #456b49">
                    <svg class="bi" width="1em" height="1em"><use xlink:href="#people-circle"/></svg>
                  </div>
                  <h3 style="margin-top: 15px; margin-left: 10px">{{ value.mcode.mname }}</h3>
                </div>
                <div style="margin-left: 10px">
                  <p>{{ value.mcode.mmail }}</p>
                  <p>{{ value.mcode.mph }}</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
</template>

<script>
import axios from 'axios';
import store from '@/store';

export default {
	name: 'TeamManagement',
	data() {
		return {
			addTeamForm: false,
			showingTeamMember: false,
			showingTeamList: false,
			noTeam: '',
			unacceptedTeamCode: [],
			acceptedTeamList: [],
			teamCodeList: [],
			teamList: [],
			mcode: '',
			open: '전체공개',
			requestTeamCode: '',
			showingRequest: false,
		};
	},
	created() {
		this.TeamManage(this.$store.getters.getLoginState.mcode);
	},
	methods: {
		selectUnacceptedTeam: function (teamCode) {
			this.showingTeamMember = false;
			this.requestTeamCode = teamCode;
			this.showingRequest = true;
		},
		teamSave: function () {
			if (
				this.$store.state.open === '' ||
				this.$store.state.insertName === ''
			) {
				alert('값을 입력하세요');
			} else {
				if (this.$store.state.open === '전체공개') {
					this.$store.state.open = 'y';
				} else this.$store.state.open = 'n';
				const url = '/api/addTeam';
				axios
					.put(url, {
						teamName: this.$store.state.insertName,
						teamState: this.$store.state.open,
						teamMaster: this.mcode,
					})
					.then((response) => {
						if (response.data !== 'n') {
							alert(
								this.$store.state.insertName +
									'팀이 생성되었습니다',
							);

							this.TeamManage(
								this.$store.getters.getLoginState.mcode,
							);

							this.$store.state.insertName = '';
							this.addTeamForm = false;
						} else {
							alert('이미 존재하는 팀 이름입니다');
						}
					})
					.catch((error) => {
						console.log(error);
					});
			}
		},
		// -----------------------------------------------------
		TeamManage: function (mcode) {
			this.mcode = mcode;
			this.showingTeamList = true;
			this.teamList.length = 0;
			this.unacceptedTeamCode.length = 0;
			const url = 'http://localhost:9002';
			axios
				.post(`${url}/api/TeamManagementPage/` + this.mcode)
				.then((response) => {
					this.noTeam = '';
					response.data.map((item) => {
						this.$store.commit('updateMyTeam', item.mcode);
						this.teamCodeList = item;
						if (item.acception === 'y') {
							this.teamList.push(item);
						} else {
							this.unacceptedTeamCode.push(item);
							console.log(item);
						}
					});
				})
				.catch((error) => {
					this.noTeam = '<h2>소속 팀이 없습니다</h2>';
					console.log(error);
				});
		},
		// -----------------------------------------------------

		accept: function (teamCode) {
			const url = 'http://localhost:9002/api/acceptTeam';
			const member = { mcode: this.$store.state.member.mcode };
			const tc = { teamCode: teamCode };

			axios
				.post(url, { mcode: member, teamCode: tc })
				.then((response) => {
					alert('수락완료!');
					this.TeamManage(this.$store.state.member.mcode);
          this.$router.go();
				})
				.catch((error) => {
					console.log(error);
				});
		},
		// -----------------------------------------------------
		refuse: function (teamCode) {
			const delConfirm = confirm('정말로 거절 하시겠습니까?');
			if (delConfirm) {
				const url = 'http://localhost:9002/api/refuseTeam';
				const member = { mcode: this.$store.state.member.mcode };
				const tc = { teamCode: teamCode };
				axios
					.post(url, { mcode: member, teamCode: tc })
					.then((response) => {
						alert('거절성공');
						this.TeamManage(this.$store.state.member.mcode);
					})
					.catch((error) => {
						console.log(error);
					});
			}
		},
		// -----------------------------------------------------

		loadTeamMemberList: function (teamCode) {
			this.showingRequest = false;
			this.$store.commit('updateTeamName', teamCode.teamCode.teamName);
			this.$store.state.teamMemberList.length = 0;
			this.showingTeamMember = true;
			this.$store.state.teamURL = '/TeamMember';
			const url =
				'http://localhost:9002/api/loadTeamMemberList/' +
				teamCode.teamName;
			axios
				.get(url)
				.then((response) => {
					response.data.map((item) => {
						this.$store.commit('updateTeamCode', item);
						if (item.acception === 'y') {
							this.$store.commit('updateTeamMemberList', item);
						}
					});
				})
				.catch((error) => {
					console.log(error);
				});
		},
		// -----------------------------------------------------

		showingAddTeamForm: function () {
			this.addTeamForm = true;
		},
		// -----------------------------------------------------
	},
	loginedTeamCode: function () {},
};
</script>

<style>
@import url('https://fonts.googleapis.com/css?family=Poppins:200,300,400,500,600,700,800,900&display=swap');

.teamNameH5 {
  font-family: '휴먼둥근헤드라인', cursive;
  font-size: 1.5em;
  color: #4f9f34;
}

a,
a:visited {
  text-decoration: none;
}

a.buttons {
  width: 30px;
  height: 25px;
  padding: 2px;
  margin-left: 12px;
  text-align: center;
  color: #FFF;
  border-radius: 5px;
  transition: all 0.2s ;
}

a.buttons2{
  width: 70px;
  height: 30px;
  padding: 2px;
  margin-top: 5px;
  margin-left: 12px;
  text-align: center;
  color: #FFF;
  border-radius: 5px;
  transition: all 0.2s ;
  margin-bottom: 0;
}

.btnBlueGreen {
  background: #00AE68;
}

.btnBlueGreen.btnPush {
  box-shadow: 0px 5px 0px 0px #007144;
}

.btnPush:hover {
  margin-top: 10px;
  margin-bottom: 5px;
}

.btnBlueGreen.btnPush:hover {
  box-shadow: 0px 0px 0px 0px #007144;
}

.bi {
  vertical-align: -.125em;
  fill: currentColor;
}

.feature-icon {
  width: 4rem;
  height: 4rem;
  border-radius: .75rem;
}

.icon-link > .bi {
  margin-top: .125rem;
  margin-left: .125rem;
  fill: currentcolor;
  transition: transform .25s ease-in-out;
}
.icon-link:hover > .bi {
  transform: translate(.25rem);
}

.teamList {
	float: left;
}
.w-btn-gray {
	background-color: #31722b;
	color: #ffffff;
}
.teamList button {
	position: relative;
	overflow: hidden;
	width: 50px;
	height: 50px;
	border-radius: 10px;
	border: none;
	margin: 12px;
}
.teamList button:hover {
	position: relative;
	overflow: visible;
	width: 100%;
	min-width: 40px;
}

.w-btn-gra2 {
	background: linear-gradient(-45deg, #ee7752, #e73c7e, #23a6d5, #23d5ab);
	color: white;
	position: relative;
	overflow: hidden;
	width: 50px;
	height: 50px;
	border-radius: 10px;
	border: none;
	margin: 12px;
}

.w-btn-gra-anim {
	background-size: 400% 400%;
	animation: gradient1 0.5s ease infinite;
	border-radius: 10px;
	/*box-shadow: 0 15px 35px rgba(0, 0, 0, 0.3);*/
}
.w-btn-gra2:hover {
	width: 100%;
	transform: scale(1.1);
}

@keyframes gradient1 {
	0% {
		background-position: 0% 50%;
	}
	50% {
		background-position: 100% 50%;
	}
	100% {
		background-position: 0% 50%;
	}
}

.aTag{
  margin-top: 10px;
  margin-left: 14px;
}

.teamNameStyle{
  margin-bottom: 15px;
}

.teamNameH3{
  color: #333;
  border-bottom: 4px solid #3c5d3f;
  padding-bottom: 5px;
  position: relative;
}


.teamNameH3:before{
  /*     content: '';
      position: absolute;
      bottom: -20px;
      left: 50%;
      width: 20px;
      height: 20px;
      background: white;
      border-left: 4px solid #a79600;
     */
  content: ' ';
  position: absolute;
  width: 0;
  height: 0;
  left: 30px;
  bottom: -30px;
  border: 15px solid;
  border-color: #3c5d3f transparent transparent #3c5d3f;
}

.teamNameH3:after{
  /*     content: '';
      position: absolute;
      bottom: -20px;
      left: 49%;
      width: 15px;
      height: 31px;
      transform: rotate(51deg);
      border-right: 4px solid #a79600; */

  content: ' ';
  position: absolute;
  width: 0;
  height: 0;
  left: 34px;
  bottom: -20px;
  border: 15px solid;
  border-color: #fff transparent transparent #fff;
}
</style>
