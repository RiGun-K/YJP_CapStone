<template>
  <svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
    <symbol id="people" viewBox="0 0 16 16">
      <path d="M7 14s-1 0-1-1 1-4 5-4 5 3 5 4-1 1-1 1H7zm4-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
      <path fill-rule="evenodd" d="M5.216 14A2.238 2.238 0 0 1 5 13c0-1.355.68-2.75 1.936-3.72A6.325 6.325 0 0 0 5 9c-4 0-5 3-5 4s1 1 1 1h4.216z"/>
      <path d="M4.5 8a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5z"/>
    </symbol>
    <symbol id="mail" viewBox="0 0 16 16">
      <path d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V4Zm2-1a1 1 0 0 0-1 1v.217l7 4.2 7-4.2V4a1 1 0 0 0-1-1H2Zm13 2.383-4.708 2.825L15 11.105V5.383Zm-.034 6.876-5.64-3.471L8 9.583l-1.326-.795-5.64 3.47A1 1 0 0 0 2 13h12a1 1 0 0 0 .966-.741ZM1 11.105l4.708-2.897L1 5.383v5.722Z"/>
    </symbol>
    <symbol id="call" viewBox="0 0 16 16">
      <path d="M3.654 1.328a.678.678 0 0 0-1.015-.063L1.605 2.3c-.483.484-.661 1.169-.45 1.77a17.568 17.568 0 0 0 4.168 6.608 17.569 17.569 0 0 0 6.608 4.168c.601.211 1.286.033 1.77-.45l1.034-1.034a.678.678 0 0 0-.063-1.015l-2.307-1.794a.678.678 0 0 0-.58-.122l-2.19.547a1.745 1.745 0 0 1-1.657-.459L5.482 8.062a1.745 1.745 0 0 1-.46-1.657l.548-2.19a.678.678 0 0 0-.122-.58L3.654 1.328zM1.884.511a1.745 1.745 0 0 1 2.612.163L6.29 2.98c.329.423.445.974.315 1.494l-.547 2.19a.678.678 0 0 0 .178.643l2.457 2.457a.678.678 0 0 0 .644.178l2.189-.547a1.745 1.745 0 0 1 1.494.315l2.306 1.794c.829.645.905 1.87.163 2.611l-1.034 1.034c-.74.74-1.846 1.065-2.877.702a18.634 18.634 0 0 1-7.01-4.42 18.634 18.634 0 0 1-4.42-7.009c-.362-1.03-.037-2.137.703-2.877L1.885.511z"/>
    </symbol>
  </svg>

  <div class="container">
    <div style="width: 600px; margin-top: 30px; margin-left: 40px">
      <h3 class="teamNameH3">{{this.$store.state.teamCode.teamCode.teamName}}</h3>
    </div>
    <div style="display: flex; margin-top: 40px">
      <div style="width: 600px;">
<!--        <h3>팀게시판</h3>-->
        <table style="margin-left: 40px">
          <thead>
          <tr>
            <th>작성자</th>
            <th>내용</th>
            <th>작성일</th>
            <th></th>
          </tr>
          </thead>
          <tbody>
          <tr
              :class="{
              master:
                this.$store.state.teamCode.teamCode.teamMaster ==
                value.memberDto.mcodeDto,
            }"
              v-for="(value, index) in contentList"
              :key="index"
          >
            <td style="width: 100px">{{ value.memberDto.mname }}</td>
            <td style="width: 350px">{{ value.contentDto }}</td>
            <td style="width: 150px">{{ value.boardDateDto }}</td>
            <td style="width: 70px"
                v-if="
                value.memberDto.mname === this.$store.state.member.mname ||
                this.$store.state.teamCode.teamCode.teamMaster ===
                  this.$store.state.member.mcode
              "
            >
              <button type="button" class="btn btn-outline-success" @click="editContent(value.teamBoardCodeDto)" style="padding: 1px; margin-bottom: 5px; width: 40px; font-size: 0.9em">수정</button>
              <button type="button" class="btn btn-outline-success" @click="deleteContent(value.teamBoardCodeDto)" style="padding: 1px; width: 40px; font-size: 0.9em">삭제</button>
<!--              <button-->
<!--                  @click="editContent(value.teamBoardCodeDto)"-->
<!--                  class="editButton"-->
<!--              >-->
<!--                수정-->
<!--              </button>-->

<!--              <button-->
<!--                  @click="deleteContent(value.teamBoardCodeDto)"-->
<!--                  class="editButton"-->
<!--              >-->
<!--                삭제-->
<!--              </button>-->
            </td>
          </tr>
          </tbody>
        </table>
        <div aria-label="Page navigation example" style="width: 600px; margin-left: 40px; margin-top: 20px;">
          <ul class="pagination" style="justify-content: center; align-items: center">
            <li class="page-item" v-for="i in pageSize" @click="selectedPage(i)"><a class="page-link" href="#" style=" border: 1px solid #3e8e41">{{i}}</a></li>
          </ul>
        </div>
        <div style="display: flex; width: 600px; margin-left: 40px; margin-top: 40px">
          <div class="mb-3" style=" width: 505px">
            <textarea class="form-control" v-model="content" placeholder="게시글을 작성하세요" id="exampleFormControlTextarea1" rows="3" style="resize: none"></textarea>
          </div>
          <button type="button" class="btn btn-success" @click="insertContent(content)" style="width: 75px; margin-left: 20px; height: 87px; margin-right: 0px">작성</button>
        </div>
      </div>


      <div class="d-flex flex-column align-items-stretch flex-shrink-0 bg-white" style="width: 505px; border-radius: 0.6em; margin-left: 140px; margin-top: -70px; border: 1px solid #2aaf24">
        <a class="d-flex align-items-center flex-shrink-0 p-3 link-dark text-decoration-none" style="border-bottom: 1px solid #2aaf24;  background-color: #336434; border-radius: 0.6em;" >
          <svg class="bi pe-none me-2" width="30" height="24"><use xlink:href="#people"/></svg>
          <span class="fs-5 fw-semibold" style="color:white;">Member List</span>
        </a>
        <div class="list-group list-group-flush scrollarea"  style="overflow: scroll; width: 500px; height: 600px; margin-top: 10px; margin-bottom: 5px">
          <div style="margin-left: 25px; width: 440px; margin-top: 20px">
            <label for="formGroupExampleInput" class="form-label" style="color: #3e8e41">회원 초대</label>
            <div class="input-group mb-1">
              <input type="text" class="form-control" placeholder="회원번호를 입력하세요" aria-label="Recipient's username" v-model="newMemberCode" aria-describedby="button-addon2">
              <button class="btn btn-outline-success" type="button" id="button-addon2" @click="addTeamMember(this.newMemberCode)">초대</button>
            </div>
          </div>
          <div class="list-group-item py-3 lh-sm" aria-current="true" style="border: none"
             v-for="(value, index) in $store.state.teamMemberList" :key="index">
            <div style="width: 460px; padding: 10px; height: 170px">
              <div>
                <div class="row g-0 rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative" style="border: 1px solid #3e8e41">
                  <div class="col p-4 d-flex flex-column position-static">
                    <strong class="d-inline-block mb-2 text-success">
                      <form>
                      <input
                          v-model="value.teamMemberAuthority"
                          type="radio"
                          name="manager"
                          value="y"
                          @click="changeManager(value)"
                          class="form-check-input"
                          style="margin-right: 5px;"
                      />운영자
                      <input
                          v-model="value.teamMemberAuthority"
                          type="radio"
                          name="manager"
                          value="n"
                          @click="changeManager(value)"
                          class="form-check-input"
                          style="margin-right: 5px; margin-left: 7px"
                      />일반회원
                    </form></strong>
                    <h3 class="mb-0">{{ value.mcode.mname }}</h3>
                    <div style="display: flex">
                      <div style="margin-top: 10px">
                        <div style="display: flex">
                          <svg class="bi pe-none me-2" width="18" height="18"><use xlink:href="#mail"/></svg>
                          <div class="mb-1 text-muted" style="margin-left: 5px">{{ value.mcode.mmail }}</div>
                        </div>
                        <div style="display: flex">
                          <svg class="bi pe-none me-2" width="18" height="18"><use xlink:href="#call"/></svg>
                          <div class="mb-1 text-muted" style="margin-left: 5px;">{{ value.mcode.mph }}</div>
                        </div>
                      </div>
                      <div style="margin-left:180px; margin-top: 15px">
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
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>





<!--      <div class="boardDiv2"  >-->
<!--        <p class="memberListP">MemberList</p>-->
<!--        <div class="addMember">-->
<!--          회원추가하기-->
<!--          <input type="text" v-model="newMemberCode" placeholder="회원번호" />-->
<!--          <button-->
<!--            class="w-btn-outline2 w-btn-blue-outline2"-->
<!--            @click="addTeamMember(this.newMemberCode)"-->
<!--          >-->
<!--            추가하기-->
<!--          </button>-->
<!--        </div>-->
<!--	 <div class="col-md-6" v-for="(value, index) in $store.state-->
<!--								.teamMemberList"-->
<!--							:key="index"-->
<!--              style="float: left;"-->
<!--							>-->
<!--        <div class="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative">-->
<!--          <div class="col p-4 d-flex flex-column position-static">-->
<!--            <strong class="d-inline-block mb-2 text-success">{{ value.mcode.mname }}</strong>-->
<!--            <h3 class="mb-0" style="display:inline-block; max-width:280px; overflow:hidden">{{ value.mcode.mmail }}</h3>-->
<!--            <div class="mb-1 text-muted">{{ value.mcode.mph }}</div>-->
<!--        <form>-->
<!--              <input-->
<!--                v-model="value.teamMemberAuthority"-->
<!--                type="radio"-->
<!--                name="manager"-->
<!--                value="y"-->
<!--                @click="changeManager(value)"-->
<!--              />운영자-->
<!--              <input-->
<!--                v-model="value.teamMemberAuthority"-->
<!--                type="radio"-->
<!--                name="manager"-->
<!--                value="n"-->
<!--                @click="changeManager(value)"-->
<!--              />일반회원-->
<!--            </form>-->
<!--            <button-->
<!--              class="banishment"-->
<!--              v-if="showingDeleteTeamButton"-->
<!--              @click="banishment(value.teamMemberCode, index)"-->
<!--            >-->
<!--              추방하기-->
<!--            </button>-->
<!--            <a href="#" class="stretched-link">Continue reading</a>-->

<!--        </div>-->
<!--          <div class="col-auto d-none d-lg-block">-->
<!--            <svg class="bd-placeholder-img" width="200" height="250" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Thumbnail" preserveAspectRatio="xMidYMid slice" focusable="false"><title>Go to detail</title><rect width="100%" height="100%" fill="#55595c"/><text x="50%" y="50%" fill="#eceeef" dy=".3em">Thumbnail</text></svg>-->
<!--          </div>-->
<!--        </div>-->
<!--				</div>-->
<!--     -->
<!--      </div>-->

      <div style="width: 1205px; margin-left: 40px; background-color: white; margin-top: 50px; border-radius: 0.6em; border: 1px solid #3e8e41">
        <div>
          <div v-if="showingDeleteTeamButton" class="planAdd" >
            <h3 style="color:white; text-align: center; margin-top: 5px; margin-right: 20px">PlanList</h3>
            <div class="button" @click="openWindow('/selectCopy')">
              <p class="btnText">NEW</p>
              <div class="btnTwo">
                <p class="btnText2">+</p>
              </div>
            </div>
<!--            <button-->
<!--              type="button"-->
<!--              @click="openWindow('/selectCopy')"-->
<!--              title="새 플랜 만들기"-->
<!--            >-->
<!--              +-->
<!--            </button>-->
          </div>
          <div v-for="(value, index) in planList" :key="index" class="planInfo">
            <div class="planImg" style="position:relative">

                <button
                  class="w-btn w-btn-red"
                  style="position:absolute;"
                  @click="deletePlan(value)"
                >X
                </button>

              <div>
                <img :src="'/api/product_detail_image/' + value.filename"
                      class="imgbackground"

                />
              </div>
              <div>
                <button
                @Click="updatePlanCode(value)"
                class="w-btn-outline w-btn-red-outline"
              >
                <p>{{ value.planName }}</p>
                <p>{{ value.planStart }}~{{ value.planEnd }}</p>
              </button >
            </div>
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
      teamName: ""
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
  text-align: left;
}

thead {
  font-weight: bold;
  color: #fff;
  background: #336434;
}

td, th {
  padding: 1em .5em;
  vertical-align: middle;
  height: 70px;
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
.page-link{
  color: #3e8e41;
}

.page-link:hover{
  background-color: #3e8e41;
  color: white;
}

.page-link:active{
  background-color: #3e8e41;
  color: white;
}

.master {
  color: #199819;
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

.banishment {
  border-radius: 10px;
  border: 1px solid #3e8e41;
  background-color: #3e8e41;
  color: white;
  width: 80px;
  height: 40px;
}

.banishment:hover {
  background-color: white;
  color: #3e8e41;
}

.teamNameH3 {
  font-family: '휴먼둥근헤드라인', cursive;
  border-radius: 15px 15px 15px 0;
  border: 3px solid #154416;
  padding: 0.5em 0.6em;
  color: #218633;
}

.planAdd{
  background-color: #336434;
  width: 1205px;
  height: 70px;
  margin-top: 50px;
  margin-top: 0;
  border-radius: 0.6em;
  display: flex;
  justify-content: center;
  align-items: center;
}

.button {
  background: #083608;
  width : 130px;
  height : 40px;
  overflow: hidden;
  text-align : center;
  transition : .2s;
  cursor : pointer;
  border-radius: 3px;
  box-shadow: 0px 1px 2px rgba(0,0,0,.2);
}
.btnTwo {
  position : relative;
  width : 170px;
  height : 100px;
  margin-top: -100px;
  padding-top: 2px;
  background : #26a64a;
  left : -250px;
  transition : .3s;
}
.btnText {
  margin-top: 10px;
  color : white;
  transition : .3s;
}
.btnText2 {
  margin-top : 58px;
  margin-right : -130px;
  color : #FFF;
}
.button:hover .btnTwo{ /*When hovering over .button change .btnTwo*/
  left: -130px;
}
.button:hover .btnText{ /*When hovering over .button change .btnText*/
  margin-left : 40px;
}
.button:active { /*Clicked and held*/
  box-shadow: 0px 5px 6px rgba(0,0,0,0.3);
}







.planListDiv {
  margin-top: 50px;
  display: inline-block;
}
.planListDiv div.planInfo {
  float: left;
  margin: 40px;
  height: 100px;
  margin-bottom: 150px;
}

.w-btn {
  position: relative;
  border: none;
  /* padding: 15px 30px; */
  border-radius: 15px;
  font-family: "paybooc-Light", sans-serif;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
  text-decoration: none;
  font-weight: 600;
  transition: 0.25s;
}

.w-btn-red {
  background-color: #f30e0e;
  color: #e1eef6;
  width: 30px;
  height: 30px;
  font-size: 0.5px;
  border-radius: 0px;
  margin-left: 70px;
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
  border: none;
  color: #e1eef6;
  background-color: #6e6e6e;
  width: 200px;
  height: 60px;
  border-radius: 0%;
}
.w-btn-red-outline p{
  line-height: 1px;

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
  width: 70%;
  height: auto;
  display: inline-block;
  margin: 50px;
}
.boardDiv2 {
  border: none;
  border-radius: 50px;
  background-color: rgb(247, 246, 230);
  width: 70%;
  height: auto;
  display: inline-block;
  margin: 50px;
  
}
.imgbackground{
height: 100px;
width: 200px;

}.col-md-6{
	display: inline-block;
	width: 45%;
	margin-left: 1.65%;
	margin-right: 1.65%;
}

</style>
