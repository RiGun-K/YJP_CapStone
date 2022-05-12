/* eslint-disable */
<!------------------------------------------------------------------------- -->

<template>
	<button @Click="showingAddTeamForm">팀 만들기</button>
	<div v-if="addTeamForm">
		<input
			v-model="$store.state.insertName"
			placeholder="insert team name"
		/>
		<select v-model="$store.state.open">
			<option>전체공개</option>
			<option>비공개</option>
		</select>
		<button @click="teamSave">제출</button>
	</div>
	<hr />

	<!------------------------------------------------------------------------- -->

	<h3>플랜을 작성할 팀을 선택하세요</h3>

	<p v-html="noTeam"></p>
	<div v-if="showingTeamList">
		<div v-for="(value, index) in teamList" :key="index">
			<button @click="loadTeamMemberList(value.teamCode)">
				{{ value.teamCode.teamName }}
			</button>
		</div>
	</div>

	<div>
		<p>팀원 요청 목록</p>
		<div v-for="(value, index) in unacceptedTeamCode" :key="index">
			팀<button>{{ value.teamCode.teamName }}</button>에서 요청이 왔습니다
			<button @click="accept(value.teamCode.teamCode)">수락하기</button>
			<button @click="refuse(value.teamCode.teamCode)">거절하기</button>
		</div>
	</div>

	<!------------------------------------------------------------------------- -->

	<div v-if="showingTeamMember">
		<hr />
		<h3>
			TEAM NAME - {{ $store.state.teamCode.teamCode.teamName }}의 MEMBERS
		</h3>

		<button
			v-for="(value, index) in $store.state.teamMemberList"
			:key="index"
		>
			{{ value.mcode.mname }}
		</button>
		<router-link to="/insertCopy"
			>{{ $store.state.teamCode.teamCode.teamName }}에 플랜 생성하기
		</router-link>
	</div>
	<!------------------------------------------------------------------------- -->
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
		};
	},
	created() {
		this.TeamManage(this.$store.getters.getLoginState.mcode);
	},
	methods: {
		// -----------------------------------------------------

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
							this.$store.state.insertName = '';
							//	component('TeamMember').addTeamMember(this.mcode);
						} else {
							console.log('중복값!');
							alert('이미 존재하는 팀 이름입니다');
						}
					})
					.catch((error) => {
						console.log('에러!');
						console.log(this.$store.state.member);
						console.log(error);
					});
			}
		},
		// -----------------------------------------------------
		TeamManage: function (mcode) {
			console.log(mcode);
			this.mcode = mcode;
			this.showingTeamList = true;
			this.teamList.length = 0;
			this.unacceptedTeamCode.length = 0;
			const url = 'http://localhost:9002';
			axios
				.post(`${url}/api/TeamManagementPage/` + this.mcode)
				.then((response) => {
					console.log(response);
					this.noTeam = '';
					console.log(response.data);
					response.data.map((item) => {
						this.$store.commit('updateMyTeam', item.mcode);
						this.teamCodeList = item;
						if (item.acception === 'y') {
							this.teamList.push(item);
						} else {
							this.unacceptedTeamCode.push(item);
						}
						return console.log('teamName' + item.teamCode.teamName);
					});
				})
				.catch((error) => {
					this.noTeam = '<h1>소속 팀이 없습니다</h1>';
					console.log('실패');
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
			const url = 'http://localhost:9002/api/refuseTeam';
			const member = { mcode: this.$store.state.member.mcode };
			const tc = { teamCode: teamCode };
			axios
				.post(url, { mcode: member, teamCode: tc })
				.then((response) => {
					alert('거절하였습니다!');
					this.TeamManage(this.$store.state.member.mcode);
				})
				.catch((error) => {
					console.log(error);
				});
		},
		// -----------------------------------------------------

		loadTeamMemberList: function (teamCode) {
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
					console.log('성공');

					response.data.map((item) => {
						console.log(item);
						this.$store.commit('updateTeamCode', item);
						console.log(item);
						console.log(item);
						console.log(item);
						console.log(item);
						if (item.acception === 'y') {
							this.$store.commit('updateTeamMemberList', item);
						}
						return console.log('teamMembers' + item.mcode.mname);
					});
				})
				.catch((error) => {
					console.log('실패');
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

<style></style>
