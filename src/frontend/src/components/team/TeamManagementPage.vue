<template>
	<div class="back">
		<div class="frame">
			<div class="content">
				<div class="myTeam">
					<p>MY TEAM</p>
					<button
						@Click="showingAddTeamForm"
						class="w-btn-outline w-btn-blue-outline"
						type="button"
					>
						+
					</button>
				</div>
				<div v-if="addTeamForm" class="newTeam">
					<select v-model="open">
						<option>전체공개</option>
						<option>비공개</option>
					</select>
					<input
						v-model="$store.state.insertName"
						placeholder="insert team name"
					/>

					<button
						@click="teamSave"
						class="w-btn w-btn-indigo"
						type="button"
					>
						생성
					</button>
				</div>
				<div v-if="showingTeamList">
					<p v-html="noTeam"></p>
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
				</div>
				<div>
					<hr />
					<br />
				</div>
				<div class="requestOuter">
					<div v-if="showingRequest" class="request">
						<div class="requestP">
							<p>
								팀{{ this.requestTeamCode.teamName }} 에서
								요청이 왔습니다
							</p>
							<div class="requestButton">
								<button
									@click="accept(requestTeamCode.teamCode)"
								>
									수락하기
								</button>
								<button
									@click="refuse(requestTeamCode.teamCode)"
								>
									거절하기
								</button>
							</div>
						</div>
					</div>
					<div v-if="showingTeamMember" class="teamMemberList">
						<p>
							{{ $store.state.teamCode.teamCode.teamName }}
							MEMBERS
						</p>

						<button
							id="teamMembers"
							v-for="(value, index) in $store.state
								.teamMemberList"
							:key="index"
						>
							이름:{{ value.mcode.mname }}
							<br />
							이메일:{{ value.mcode.mmail }} <br />
							나이:{{ value.mcode.mph }}
						</button>
						<a :href="$store.state.teamURL"
							>팀{{
								$store.state.teamCode.teamCode.teamName
							}}상세보기</a
						>
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
					this.noTeam = '<h1>소속 팀이 없습니다</h1>';
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

#teamMembers {
	width: 200px;
	height: 100px;
	background-color: #f5d682;
	border: none;
}

.teamList {
	float: left;
	/* width: 200px; */
	/* height: 120px; */
	/* border: 1px solid red; */
}
.w-btn-gray {
	background-color: #a3a1a1;
	color: #e3dede;
}
.teamList button {
	position: relative;
	overflow: hidden;
	width: 50px;
	height: 50px;
	border-radius: 10px;
	border: none;
	margin: 12px;
	box-shadow: 0 15px 35px rgba(0, 0, 0, 0.3);
}
.teamList button:hover {
	position: relative;
	overflow: visible;
	width: 100%;
	min-width: 40px;
}

.w-btn:hover {
	letter-spacing: 2px;
	transform: scale(1.5);
	cursor: pointer;
	position: relative;
	overflow: visible;
	min-width: 40px;
}

.w-btn-outline:active {
	transform: scale(1.5);
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
	box-shadow: 0 15px 35px rgba(0, 0, 0, 0.3);
}
.w-btn-gra2:hover {
	width: 100%;
	transform: scale(1.1);
}
.w-btn-blue-outline {
	border: 3px solid #6aafe6;
	color: #6e6e6e;
	border-radius: 10px;
	width: 35px;
	height: 35px;
	border: none;
	box-shadow: 0 15px 35px rgba(0, 0, 0, 0.3);
}
.w-btn-blue-outline:hover {
	background-color: #6aafe6;
	color: #d4dfe6;
}
.w-btn {
	position: relative;
	border: none;
	display: inline-block;
	padding: 15px 30px;
	border-radius: 15px;
	font-family: 'paybooc-Light', sans-serif;
	box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
	text-decoration: none;
	font-weight: 600;
	transition: 0.25s;
}
.w-btn-blue-outline {
	border: 3px solid #6aafe6;
	color: #6e6e6e;
}

.myTeam {
	width: 100%;
	margin: 20px;
	float: left;
}
.myTeam p {
	font-size: 30px;
	margin: 10px;
	float: left;
}
.myTeam button {
	margin-top: 15px;
}
.content {
	float: left;
}
.teamMemberList p {
	font-size: 20px;
}

hr {
	width: 2001px;
}
.teamMemberList button {
	border-radius: 20px;
	margin: 20px;
	border: none;
}
.request button {
	margin: 40px;
	width: 100px;
	height: 60px;
	background-color: #23a6d5;
	border: none;
	border-radius: 10px;
	color: white;
}

.request {
	margin-left: 450px;
}
.request p {
	font-size: 50px;
	display: flex;
}
.requestOuter {
	display: flex;
}

.requestButton {
	margin-left: 100px;
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
.back {
	position: absolute;
	background-image: url(@/assets/campwall2.webp);
	background-size: 100%;
	width: 100%;
	background-repeat: repeat-y;
	padding: auto;
}
.frame {
	border: none;
	border-radius: 50px;
	background-color: rgb(247, 246, 230);
	width: 86%;
	height: 800px;
	margin: auto;
}
.newTeam {
	padding: 40px;
}

.newTeam select {
	height: 40px;
	margin-right: 10px;
	border-radius: 10px;
}

.newTeam input {
	height: 40px;
	width: 300px;
	margin-right: 10px;
	border-radius: 10px;
}
.w-btn-indigo {
	background-color: aliceblue;
	color: #1e6b7b;
}
</style>
