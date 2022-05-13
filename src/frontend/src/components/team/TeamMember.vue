<template>
  <div>
    <h2>{{ $store.state.teamCode.teamCode.teamName }}팀의 상세정보</h2>

    <h3>팀 관리자 -{{ $store.state.teamCode.teamCode.teamMaster }}-</h3>

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
  },
  data() {
    return {
      newMemberCode: '',
      showingDeleteTeamButton: false,
      open: false,
      planList: [],
    };
  },
  methods: {
    loadTeamPlans: function () {
      const url = '/api/loadTeamPlans/';
      axios
          .get(url + this.$store.state.teamCode.teamCode.teamCode)
          .then((response) => {
            response.data.map((item) => {
              this.planList.push(item);
              return console.log(item);
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
      const url = 'api/deleteTeam';
      axios
          .post(url, this.$store.state.teamCode.teamCode)
          .then((response) => {
            console.log(this.$store.state.teamCode.teamCode);
            console.log('삭제완료!!');
          })
          .catch((error) => {
            alert('삭제에러');
            console.log(error);
          });
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

<style></style>