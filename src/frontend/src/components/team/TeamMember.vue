<template>
  <div class="background">
    <div class="outer">
      <div class="boardDiv">
        <p class="boardP">팀게시판</p>
        <table class="tbAdd">
          <th>작성자</th>
          <th>내용</th>
          <th>작성일</th>
          <tr
            :class="{
              master:
                this.$store.state.teamCode.teamCode.teamMaster ==
                value.memberDto.mcodeDto,
            }"
            v-for="(value, index) in contentList"
            :key="index"
          >
            <td>{{ value.memberDto.mname }}</td>
            <td class="tdContent">{{ value.contentDto }}</td>
            <td>{{ value.boardDateDto }}</td>
            <td
              v-if="
                value.memberDto.mname === this.$store.state.member.mname ||
                this.$store.state.teamCode.teamCode.teamMaster ===
                  this.$store.state.member.mcode
              "
            >
              <button
                @click="editContent(value.teamBoardCodeDto)"
                class="editButton"
              >
                수정
              </button>

              <button
                @click="deleteContent(value.teamBoardCodeDto)"
                class="editButton"
              >
                삭제
              </button>
            </td>
          </tr>
        </table>
        <p>
          페이지
          <button class="pages" v-for="i in pageSize" @click="selectedPage(i)">
            {{ i }}
          </button>
        </p>

        <div class="textDiv">
          <textarea
            class="textArea"
            v-model="content"
            placeholder="게시글을 작성하세요"
          ></textarea>
          <button @click="insertContent(content)" class="textButton">
            작성
          </button>
        </div>
      </div>

      <div class="boardDiv">
        <p class="memberListP">MemberList</p>
        <div class="addMember">
          회원추가하기
          <input type="text" v-model="newMemberCode" placeholder="회원번호" />
          <button
            class="w-btn-outline2 w-btn-blue-outline2"
            @click="addTeamMember(this.newMemberCode)"
          >
            추가하기
          </button>
        </div>

        <div class="memberListDiv">
          <div
            v-for="(value, index) in $store.state.teamMemberList"
            :key="index"
            class="teamListDiv"
          >
            <p>
              name:
              {{ value.mcode.mname }}
            </p>
            <p>phone: {{ value.mcode.mph }}</p>
            <p>email: {{ value.mcode.mmail }}</p>
            <form>
              <input
                v-model="value.teamMemberAuthority"
                type="radio"
                name="manager"
                value="y"
                @click="changeManager(value)"
              />운영자
              <input
                v-model="value.teamMemberAuthority"
                type="radio"
                name="manager"
                value="n"
                @click="changeManager(value)"
              />일반회원
            </form>
            <button
              class="banishment"
              v-if="showingDeleteTeamButton"
              @click="banishment(value.teamMemberCode, index)"
            >
              추방하기
            </button>
          </div>
        </div>
      </div>
      <div class="boardDiv">
        <div class="planListDiv">
          <div v-if="showingDeleteTeamButton" class="createNewPlan">
            <p>PlanList</p>
            <button
              class="w-btn-outline2 w-btn-blue-outline2"
              type="button"
              @click="openWindow('/selectCopy')"
              title="새 플랜 만들기"
            >
              +
            </button>
          </div>
          <div v-for="(value, index) in planList" :key="index" class="planInfo">
            <div>
              <button
                @Click="updatePlanCode(value)"
                class="w-btn-outline w-btn-red-outline"
              >
                <p>{{ value.planName }}</p>
                <p>인원: {{ value.planNumber }}</p>
                <p>{{ value.planDestination }}</p>
                <p>{{ value.planStart }}~{{ value.planEnd }}</p>
              </button>
              <button
                class="w-btn w-btn-red deleteButton"
                @click="deletePlan(value)"
              >
                플랜삭제
              </button>
            </div>
          </div>
        </div>

        <div>
          <button class="w-btn1 w-btn-indigo" @click="deleteTeam()">
            팀 삭제하기
          </button>
          <button class="w-btn1 w-btn-indigo" @click="refuse()">
            탈퇴하기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { tsThisType } from "@babel/types";
import axios from "axios";

export default {
  name: "TeamMemberVue",
  created() {
    this.showingDeleteTeam();
    this.loadTeamPlans();
    this.loadTeamBoards();
  },
  data() {
    return {
      newMemberCode: "",
      showingDeleteTeamButton: false,
      open: false,
      planList: [],
      content: "",
      contentList: [],
      myContent: true,
      pageSize: "",
      pageNumber: 0,
    };
  },
  methods: {
    selectedPage(i) {
      this.pageNumber = i - 1;
      this.loadTeamBoards();
    },
    changeManager: function (teamMember) {
      console.log(teamMember);
      const url = "api/changeManager";
      axios
        .put(url, teamMember)
        .then((response) => {})
        .catch((error) => {
          console.log(error);
        });
    },
    banishment: function (teamMemberCode, index) {
      const url = "api/banishment";
      axios
        .delete(url, { params: { teamMemberCode: teamMemberCode } })
        .then((response) => {
          this.$store.state.teamMemberList.splice(index);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    deletePlan: function (planCode) {
      const delConfirm = confirm("정말로 삭제 하시겠습니까?");
      const url = "api/deletePlan";
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
      const url = "api/deleteContent";
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
      const url = "api/editContent";
      const newContent = prompt("새 글을 입력하세요");
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
      const url = "api/loadTeamBoards";
      const data = {
        teamCode: this.$store.state.teamCode.teamCode.teamCode,
        page: this.pageNumber,
        size: 4,
      };
      this.contentList.length = 0;
      axios
        .get(url, { params: data })
        .then((response) => {
          console.log(response);
          this.pageSize = response.data.totalPages;
          response.data.content.map((item) => {
            this.contentList.push(item);
          });
        })
        .catch((error) => {
          console.log(error);
        });
    },
    insertContent: function (content) {
      const url = "api/insertContent";
      const data = {
        contentDto: content,
        teamDto: this.$store.state.teamCode.teamCode,
        memberDto: this.$store.state.member.mcode,
      };
      axios
        .post(url, data)
        .then((response) => {
          this.loadTeamBoards();
          this.content = "";
        })
        .catch((error) => {
          console.log(error);
        });
    },
    loadTeamPlans: function () {
      const url = "/api/loadTeamPlans/";
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
      const url = "api/addTeamMember";
      const member = { mcode: newMemberCode };
      const team = {
        teamCode: this.$store.state.teamCode.teamCode.teamCode,
      };
      if (this.$store.state.loginedTeamCode.teamMemberAuthority === "y") {
        if (this.newMemberCode !== "") {
          axios
            .post(url, {
              mcode: member,
              teamCode: team,
            })
            .then((response) => {
              if (response.data !== "n") {
                alert("팀원추가 요청을 보냈습니다");
              } else {
                alert("이미 요청을 보낸 회원입니다");
              }
              this.newMemberCode = "";
            })
            .catch((error) => {
              alert("존재하지 않는 회원입니다");
              console.log(error);
              this.newMemberCode = "";
            });
        } else {
          alert("회원번호를 입력하세요");
        }
      } else {
        alert("권한이 없습니다");
      }
    },
    showingDeleteTeam: function () {
      if (
        this.$store.state.member.mcode ===
          this.$store.state.teamCode.teamCode.teamMaster ||
        this.$store.state.teamCode.teamMemberAuthority === "y"
      ) {
        this.showingDeleteTeamButton = true;
      }
      const url = "/api/loginedTeamCode";

      axios
        .post(url, {
          mcode: this.$store.state.member,
          teamCode: this.$store.state.teamCode.teamCode,
        })
        .then((response) => {
          this.$store.commit("updateLoginedTeamCode", response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    refuse: function () {
      const delConfirm = confirm("정말로 탈퇴 하시겠습니까?");
      if (delConfirm) {
        const url = "http://localhost:9002/api/refuseTeam";
        const member = { mcode: this.$store.state.member.mcode };
        const tc = {
          teamCode: this.$store.state.teamCode.teamCode.teamCode,
        };
        axios
          .post(url, { mcode: member, teamCode: tc })
          .then((response) => {
            alert("탈퇴성공!");
            this.$router.push({
              name: "teamManage",
            });
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
    deleteTeam: function () {
      const url = "/api/deleteTeam";
      const delConfirm = confirm("정말로 삭제 하시겠습니까?");
      if (delConfirm) {
        axios
          .delete(url, {
            params: {
              teamCode: this.$store.state.teamCode.teamCode.teamCode,
            },
          })
          .then((response) => {
            alert("팀이 삭제되었습니다");
            this.$router.push({
              name: "teamManage",
            });
          })
          .catch((error) => {
            alert("삭제에러");
            console.log(error);
          });
      }
    },
    updatePlanCode: function (planCode) {
      this.$store.commit("updatePlanCode", planCode);
      this.$router.push({
        name: "detailPlan",
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
.tbAdd tr:nth-child(even) {
  background-color: rgba(240, 240, 240, 0.37);
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
.master {
  color: red;
}
.editButton {
  background-color: rgba(136, 136, 136, 0);
  border: none;
  font-size: 10px;
  margin: 5px;
  color: rgba(136, 136, 136, 0.534);
}
.textArea {
  width: 1000px;
  height: 100px;
  margin-top: 40px;
  margin-left: 100px;
}
.textButton {
  width: 90px;
  height: 100px;
  position: relative;
  top: -45px;
  border: none;
}
.memberListDiv {
  display: inline-block;
}
.teamListDiv {
  background-color: rgb(231, 231, 231);
  margin: 60px;
  margin-top: 40px;
  width: 300px;
  height: 100px;
  float: left;
  border-radius: 15px;
}
.banishment {
  border-radius: 10px;
  border: none;
  background-color: rgb(240, 150, 32);
}
.planListDiv {
  margin-top: 50px;
  display: inline-block;
}
.planListDiv div.planInfo {
  float: left;
  margin: 40px;
  height: 200px;
}
.planListButton {
  border-radius: 5px;
  background-color: rgba(255, 0, 0, 0.205);
  border: none;
}
.w-btn {
  position: relative;
  border: none;
  display: inline-block;
  padding: 15px 30px;
  border-radius: 15px;
  font-family: "paybooc-Light", sans-serif;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
  text-decoration: none;
  font-weight: 600;
  transition: 0.25s;
}

.w-btn-red {
  background-color: #ff5f2e;
  color: #e1eef6;
}
.w-btn:hover {
  letter-spacing: 2px;
  transform: scale(1.2);
  cursor: pointer;
}
.w-btn-outline {
  position: relative;
  padding: 15px 30px;
  border-radius: 15px;
  font-family: "paybooc-Light", sans-serif;
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

.createNewPlan {
  background-color: rgb(199, 201, 200);
}

.createNewPlan p {
  font-size: 30px;
  display: inline;
}

.memberListP {
  font-size: 25px;
  background-color: rgb(199, 201, 200);
}

.boardP {
  font-size: 30px;
  margin-top: 50px;
  margin-bottom: 30px;
  background-color: rgb(199, 201, 200);
}

.w-btn-outline:active {
  transform: scale(1.5);
}
.w-btn-blue-outline2:hover {
  background-color: #6aafe6;
  color: #d4dfe6;
}
.w-btn-blue-outline2 {
  border: 3px solid #6aafe6;
  color: #6e6e6e;
}
.w-btn-outline2 {
  position: relative;
  border-radius: 15px;
  font-family: "paybooc-Light", sans-serif;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
  text-decoration: none;
  font-weight: 600;
  transition: 0.25s;
  width: 40px;
  margin-left: 20px;
  font-size: 20px;
}

.addMember button {
  position: relative;
  border-radius: 15px;
  font-family: "paybooc-Light", sans-serif;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
  text-decoration: none;
  font-weight: 600;
  transition: 0.25s;
  width: 80px;
  margin-left: 20px;
  font-size: 12px;
}
.addMember input {
  border-radius: 5px;
}
.w-btn-outline2:hover {
  letter-spacing: 2px;
  transform: scale(1.2);
  cursor: pointer;
}
.tdContent {
  max-width: 800px;
  word-break: break-all;
}

.w-btn1 {
  position: relative;
  border: none;
  display: inline-block;
  padding: 15px 30px;
  border-radius: 15px;
  font-family: "paybooc-Light", sans-serif;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
  text-decoration: none;
  font-weight: 600;
  transition: 0.25s;
  margin-right: 50px;
  float: right;
}

.w-btn-indigo {
  background-color: aliceblue;
  color: #1e6b7b;
  margin-bottom: 30px;
}
.w-btn1:active {
  transform: scale(1.5);
}

.w-btn1:hover {
  letter-spacing: 2px;
  transform: scale(1.2);
  cursor: pointer;
}
.boardDiv {
  border: none;
  border-radius: 50px;
  background-color: rgb(247, 246, 230);
  width: 90%;
  height: auto;
  display: inline-block;
  margin: 50px;
}
.outer {
  text-align: center;
  position: absolute;
  background-image: url(@/assets/campwall2.webp);
  background-size: 100%;
  background-repeat: repeat-y;
}

.pages {
  border: none;
  background-color: transparent;
}
</style>
