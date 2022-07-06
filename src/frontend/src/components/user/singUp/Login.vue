<template>
  <div id="wrapper">
    <div id="content">
      <br>
      <h2>Login</h2>
      <div>
        <br>
        <h3 class="join_title">
          <label>아이디</label>
        </h3>
        <span class="input-group mb-3">
                        <input type="text" name="MID" v-model="userID"  placeholder="아이디 입력" class="form-control" maxlength="20">
                    </span>
        <span class="error_next_box"></span>
        <h3 class="join_title">
          <label>비밀번호</label>
        </h3>

        <span class="input-group mb-3">
          <input type="password" name="MPASS" v-model="password" class="form-control" placeholder="비밀번호 입력" aria-label="Recipient's username" aria-describedby="button-addon2">
          <button class="btn btn-outline-secondary" type="button" id="button-addon2" @click="login">로그인</button>
        </span>
        <span class="error_next_box"></span>
        <br>
        <div class="btn_area">
          <button type="button" @click="signup" id="btnJoin" class="btn_Bottom">
            <span>회원가입</span>
          </button>
          <br/><br/>
          <button type="button" @click="search" id="btnSearch" class="btn_Bottom">
            <span>아이디/비번찾기</span>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios"
import store from "@/store";

export default {
  name: "Login",
  data() {
    return {
      userID: '',
      password: ''
    }
  },
  methods: {
    signup() {
      this.$router.push('agreement')
    },
    search(){
      this.$router.push('searchSelect')
    }
    ,
    login(){
      if(this.userID==''){
        alert("아이디를 입력해주세요")
      }else if(this.password==''){
        alert("비밀번호를 입력해주세요")
      }else{
        axios.post('/api/login', {
          MID:this.userID,
          MPASS:this.password
        }).then(res=>{
          switch (res.data){
            case "ID":
              alert("아이디를 확인해주세요")
              this.$router.push("/login")
              break;
            case "fail":
              alert("비밀번호를 확인해주세요")
              this.$router.push("/login")
              break;
            case 0:
              alert("탈퇴한 회원입니다")
              this.$router.push("/login")
              break;
            case 1:
              alert("정지된 회원입니다")
              this.$router.push("/login")
              break;
            case '':
              alert("로그인 실패 아이디와 비밀번호를 확인해주세요")
              break;
            default :
              alert("로그인에 성공했습니다.")
              store.commit("setLoginState", {
                loginState:this.userID,
                stateCode:res.data.msc,
                mcode:res.data.mcode
              })
              this.$router.push("/")
              break;
          }
        }).catch(err =>{
          console.log(err)
          alert("로그인 오류 발생")
          this.userID=''
          this.password=''
        })
      }
    },
    // exploer(){
    //   this.$router.push('exploer')
    // }

  }
}
</script>

<style scoped>
body {
  margin: 0;
  height: 100%;
  background-color: #E6E6FA;
}
#content {
  position: absolute;
  left: 50%;
  transform: translate(-50%);
  width: 460px;
}
/* 입력폼 */
h3 {
  margin: 19px 0 8px;
  font-size: 14px;
  font-weight: 700;
}
.box {
  display: block;
  width: 100%;
  height: 51px;
  border: solid 1px #dadada;
  padding: 10px 14px;
  box-sizing: border-box;
  background: #fff;
  position: relative;
}
.int {
  display: block;
  position: relative;
  width: 100%;
  height: 29px;
  border: none;
  background: #fff;
  font-size: 15px;
}
.box.int_id {
  padding-right: 110px;
}
.box.int_pass {
  padding-right: 40px;
}
.box.int_pass_check {
  padding-right: 40px;
}
select {
  width: 100%;
  height: 29px;
  font-size: 15px;
  background-size: 20px 8px;
  -webkit-appearance: none;
  display: inline-block;
  text-align: start;
  border: none;
  cursor: default;
}
.error_next_box {
  margin-top: 9px;
  font-size: 12px;
  color: red;
  display: none;
}
#alertTxt {
  position: absolute;
  top: 19px;
  right: 38px;
  font-size: 12px;
  color: red;
  display: none;
}
/* 버튼 */
.btn_area {
  margin: 20px 0 91px;
}
.btn_Bottom {
  width: 100%;
  padding: 21px 0 17px;
  border: 0;
  cursor: pointer;
  color: white;
  background-color: #52a3ef;
  font-size: 20px;
  font-weight: 400;
}
</style>
