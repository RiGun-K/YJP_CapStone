<template>
	<div>
		<h2>{{ $store.state.teamCode.teamCode.teamName }}팀의 상세정보</h2>

		<h3>팀 관리자 -{{ $store.state.teamCode.teamCode.teamMaster }}-</h3>
		<h1>팀게시판</h1>

		<table class="tbAdd">
			<colgroup>
				<col width="15%" />
			</colgroup>
			<th>작성자</th>
			<th>내용</th>
			<th>작성일</th>
			<tr v-for="(value, index) in contentList" :key="index">
				<td>{{ value.memberDto.mname }}</td>
				<td>{{ value.contentDto }}</td>
				<td>{{ value.boardDateDto }}</td>
				<td
					v-if="
						value.memberDto.mname === this.$store.state.member.mname
					"
				>
					<button @click="editContent(value.teamBoardCodeDto)">
						수정
					</button>
					<button @click="deleteContent(value.teamBoardCodeDto)">
						삭제
					</button>
				</td>
			</tr>
		</table>
		<textarea v-model="content">게시글을 작성하세요</textarea>
		<button @click="insertContent(content)">작성</button>
		<hr />
		<div v-if="showingDeleteTeamButton">
			<button @click="deleteTeam()">팀 삭제하기</button>
		</div>
		팀원 리스트

		<div v-for="(value, index) in $store.state.teamMemberList" :key="index">
			<button>
				{{ value.mcode.mname }}
			</button>
		</div>
		<div>
			회원추가하기
			<input type="text" v-model="newMemberCode" placeholder="회원번호" />
			<button @click="addTeamMember(this.newMemberCode)">추가하기</button>
		</div>

		<div>
			<h2>플랜목록</h2>
			<div v-for="(value, index) in planList" :key="index">
				<button @Click="updatePlanCode(value)">
					{{ value.planName }}
				</button>
				<button @click="deletePlan(value)">플랜삭제</button>
			</div>
			<div v-if="showingDeleteTeamButton">
				<button @click="openWindow('/selectCopy')">
					새 플랜 만들기
				</button>
			</div>
		</div>
	</div>
</template>

<script>
import axios from 'axios';

export default {
	name: 'TeamDetail',
	created() {
		this.showingDeleteTeam();
		this.loadTeamPlans();
		this.loadTeamBoards();
	},
	data() {
		return {
			newMemberCode: '',
			showingDeleteTeamButton: false,
			open: false,
			planList: [],
			content: '',
			contentList: [],
			myContent: true,
		};
	},
	methods: {
		deletePlan: function (planCode) {
			const delConfirm = confirm('정말로 삭제 하시겠습니까?');
			const url = 'api/deletePlan';
			if (delConfirm) {
				axios
					.delete(url, { params: { planCode: planCode.planCode } })
					.then((response) => {
						this.loadTeamPlans();
					})
					.catch((error) => {
						console.log(error);
					});
			}
		},
		deleteContent: function (boardCode) {
			const url = 'api/deleteContent';
			axios
				.delete(url, { params: { teamBoardDto: boardCode } })
				.then((response) => {
					this.loadTeamBoards();
				})
				.catch((error) => {
					console.log(error);
				});
		},
		editContent: function (boardCode) {
			const url = 'api/editContent';
			const newContent = prompt('새 글을 입력하세요');
			axios
				.put(url, {
					teamBoardCodeDto: boardCode,
					contentDto: newContent,
				})
				.then((response) => {
					this.loadTeamBoards();
				})
				.catch((error) => {
					console.log(error);
				});
		},
		loadTeamBoards: function () {
			const url = 'api/loadTeamBoards';
			const data = {
				teamCode: this.$store.state.teamCode.teamCode.teamCode,
			};
			this.contentList.length = 0;
			axios
				.get(url, { params: data })
				.then((response) => {
					response.data.map((item) => {
						this.contentList.push(item);
						console.log(this.contentList);
					});
				})
				.catch((error) => {
					console.log(error);
				});
		},
		insertContent: function (content) {
			const url = 'api/insertContent';
			const data = {
				contentDto: content,
				teamDto: this.$store.state.teamCode.teamCode,
				memberDto: this.$store.state.member.mcode,
			};
			axios
				.post(url, data)
				.then((response) => {
					this.loadTeamBoards();
					this.content = '';
				})
				.catch((error) => {
					console.log(error);
				});
		},
		loadTeamPlans: function () {
			const url = '/api/loadTeamPlans/';
			this.planList.length = 0;
			axios
				.get(url + this.$store.state.teamCode.teamCode.teamCode)
				.then((response) => {
					response.data.map((item) => {
						this.planList.push(item);
					});
				})
				.catch((error) => {
					console.log(error);
				});
		},
		addTeamMember: function (newMemberCode) {
			const url = 'api/addTeamMember';
			const member = { mcode: newMemberCode };
			const team = {
				teamCode: this.$store.state.teamCode.teamCode.teamCode,
			};
			if (this.$store.state.loginedTeamCode.teamMemberAuthority === 'y') {
				if (this.newMemberCode !== '') {
					axios
						.post(url, {
							mcode: member,
							teamCode: team,
						})
						.then((response) => {
							if (response.data !== 'n') {
								alert('팀원추가 요청을 보냈습니다');
							} else {
								alert('이미 요청을 보낸 회원입니다');
							}
							this.newMemberCode = '';
						})
						.catch((error) => {
							alert('존재하지 않는 회원입니다');
							console.log(error);
							this.newMemberCode = '';
						});
				} else {
					alert('회원번호를 입력하세요');
				}
			} else {
				alert('권한이 없습니다');
			}
		},
		showingDeleteTeam: function () {
			if (
				this.$store.state.member.mcode ===
					this.$store.state.teamCode.teamCode.teamMaster ||
				this.$store.state.teamCode.teamMemberAuthority === 'y'
			) {
				this.showingDeleteTeamButton = true;
			}
			const url = '/api/loginedTeamCode';

			axios
				.post(url, {
					mcode: this.$store.state.member,
					teamCode: this.$store.state.teamCode.teamCode,
				})
				.then((response) => {
					console.log(response.data);
					this.$store.commit('updateLoginedTeamCode', response.data);
				})
				.catch((error) => {
					console.log(error);
				});
		},
		deleteTeam: function () {
			const url = '/api/deleteTeam';
			const delConfirm = confirm('정말로 삭제 하시겠습니까?');
			if (delConfirm) {
				axios
					.delete(url, {
						params: {
							teamCode:
								this.$store.state.teamCode.teamCode.teamCode,
						},
					})
					.then((response) => {
						alert('팀이 삭제되었습니다');
						this.$router.push({
							name: 'teamManage',
						});
					})
					.catch((error) => {
						alert('삭제에러');
						console.log(error);
					});
			}
		},
		updatePlanCode: function (planCode) {
			this.$store.commit('updatePlanCode', planCode);
			this.$router.push({
				name: 'detailPlan',
			});
		},

		openWindow: function (url) {
			window.open(url);
		},
	},
};
</script>

<style>
.tbAdd {
	border-top: 1px solid #888;
}
.tbAdd th,
.tbAdd td {
	border-bottom: 1px solid #eee;
	padding: 5px 0;
}
.tbAdd td {
	padding: 10px 10px;
	box-sizing: border-box;
	text-align: left;
}
.tbAdd td.txt_cont {
	height: 300px;
	vertical-align: top;
}
</style>
