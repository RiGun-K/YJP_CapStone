<template>
  <div id="wrapper">
    <div id="content">
      <br>
      <h2>회원가입</h2>
      <br>
      <div>
        <h3 class="join_title">
          <label for="id">아이디</label>
        </h3>

        <span class="input-group mb-3">
          <input type="text" v-model="userID" class="form-control" placeholder="id 입력" aria-label="Recipient's username"
                 aria-describedby="button-addon2">
          <button class="btn btn-outline-secondary" type="button" id="button-addon2" @click="userCheck">중복확인</button>
        </span>
        <span class="error_next_box"></span>
        <h3 class="join_title">
          <label for="id">비밀번호</label>
        </h3>
        <span class="box int_id">
                        <input type="password" v-model="password" id="id" placeholder="비밀번호 입력" class="int"
                               maxlength="20">
                    </span>
        <span class="error_next_box"></span>
      </div>

      <!-- PW1 -->
      <div>
        <h3 class="join_title">
          <label for="password">비밀번호 재입력</label>
        </h3>
        <!--        <input type="password" v-model="pasCheck">-->
        {{ paCheck }}

        <span class="box int_id">
                        <input type="password" v-model="pasCheck" id="id" placeholder="비밀번호 재입력" class="int"
                               maxlength="20">
                    </span>
        <span class="error_next_box"></span>

      </div>
      <div>
        <h3 class="join_title">
          <label for="pswd2">닉네임</label>
        </h3>
        <span class="input-group mb-3">
        <input type="text" v-model="nick" class="form-control" placeholder="닉네임 입력" aria-label="Recipient's username"
               aria-describedby="button-addon2">
        <button class="btn btn-outline-secondary" type="button" id="button-addon2" @click="nameCheck">중복확인</button>
        </span>

      </div>

      <div>
        <h3 class="join_title">
          <label for="Addtess">주소</label>
        </h3>
        <span class="input-group mb-3">
          <input readonly id="postcode" type="text" v-model="zadd" class="form-control" placeholder="우편주소 입력"
                 aria-label="Recipient's username" aria-describedby="button-addon2">
          <button class="btn btn-outline-secondary" type="button" id="button-addon2" @click="zcGet">우편주소검색</button>
        </span>
        <span class="input-group mb-3">
          <input readonly id="roadAddress" type="text" v-model="radd" class="form-control" placeholder="도로명주소"
                 aria-label="Recipient's username" aria-describedby="button-addon2">
        </span>
        <span class="input-group mb-3">
          <input type="text" v-model="add" id="email" class="form-control" maxlength="100" placeholder="상세주소">
        </span>
        <span class="error_next_box">상세주소를 다시 확인해주세요.</span>
      </div>

      <div>
        <h3 class="join_title">
          <label for="email">전화번호</label>
        </h3>
        <span class="input-group mb-3">
          <input type="text" v-model="ph" class="form-control" placeholder="전화번호 입력" aria-label="Recipient's username" maxlength="11"
                 aria-describedby="button-addon2" :disabled="authCheck" oninput="javascript: this.value = this.value.replace(/[^0-9]/g, '');">
          <button class="btn btn-outline-secondary" type="button" id="button-addon2" @click="phSend" :disabled="authCheck">전송</button>
        </span>
        <span class="input-group mb-3">
          <input type="text" v-model="clientAuth" class="form-control" placeholder="인증번호입력" aria-label="Recipient's username"
                 aria-describedby="button-addon2" :disabled="authCheck">
          <button class="btn btn-outline-secondary" type="button" id="button-addon2" @click="phAuthCheck" :disabled="authCheck">인증확인</button>
        </span>
        <span class="error_next_box"></span>
      </div>

      <div>
        <h3 class="join_title">
          <label for="email">이메일</label>
        </h3>
        <span class="input-group mb-3">
          <input type="text" v-model="email" class="form-control" placeholder="이메일 입력" aria-label="Recipient's username"
                 aria-describedby="button-addon2" :disabled="emailAuthBoolean">
          <button class="btn btn-outline-secondary" type="button" id="button-addon2" @click="emailSend" :disabled="emailAuthBoolean">전송</button>
        </span>
        <span class="input-group mb-3">
          <input type="text" v-model="emailAuthInput" class="form-control" placeholder="인증번호입력" aria-label="Recipient's username"
                 aria-describedby="button-addon2" :disabled="emailAuthBoolean">
          <button class="btn btn-outline-secondary" type="button" id="button-addon2" @click="mailAuthCheck" :disabled="emailAuthBoolean">인증확인</button>
        </span>
        <span class="error_next_box"></span>

      </div>

      <!-- JOIN BTN-->
      <div class="btn_area">
        <button type="button" @click="signup" id="btnJoin">
          <span>가입하기</span>
        </button>

      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios"

export default {
  name: "Signup",
  data() {
    return {
      userID: "",
      password: "",
      nick: "",
      zadd: "",
      radd: "",
      add: "",
      ph: "",
      email: "",
      userCh: false,
      nickCheck: false,
      wordCheck: false,
      pasCheck: '',
      emailAuth:'',
      emailAuthInput:'',
      emailAuthBoolean:false,
      clientAuth:'',
      serverAuth:'',
      authCheck:false
    }
  },
  methods: {
    signup() {
      if (this.empityCheck()) {
        axios.post('http://localhost:9002/api/signUp', {
          MID: this.userID,
          MPASS: this.password,
          MNick: this.nick,
          MZadd: this.zadd,
          MRadd:this.radd,
          MAdd: this.add,
          MPH: this.ph,
          MEmail: this.email,
          MSC: '3'
        }).then(res => {
          console.log(res.data)
          if (res.data) {
            alert(`가입했습니다 환영합니다 '${this.nick}'회원님`)
            this.$router.push("login")
          } else {
            alert("이미 존재하는 유저입니다")
          }
        }).catch(err => {
          console.log(err)
          alert("가입에 실패했습니다.")
        })
      } else {
        alert("항목을 확인하세요.")
      }
    },
    zcGet() {
      new window.daum.Postcode({
        oncomplete: (data) => {
          this.zadd = data.zonecode;
          this.radd = data.roadAddress;
        }
      }).open({popupKey: '주소검색'})
    },
    userCheck() {
      if (this.idCheck()) {
        axios.post('http://localhost:9002/api/userCheck', {
          MID: this.userID
        })
            .then((res) => {
              if (res.data) {
                alert("사용이 가능합니다.")
                this.userCh = true
              } else {
                alert("사용이 불가능합니다.")
                this.userCh = false
              }
            }).catch((err) => {
          console.log(err)
          alert("유저 중복 확인 오류 발생")
        })
      } else {
        alert("아이디를 입력하세요.")
      }
    },
    nameCheck() {
      if (this.niCheck()) {
        axios.post('http://localhost:9002/api/nameCheck', {
          MNick: this.nick
        })
            .then((res) => {
              if (res.data) {
                alert("사용가능한 닉네임입니다.")
                this.nickCh = true
              } else {
                alert("사용불가능한 닉네임입니다.")
                this.nickCh = false
              }
            }).catch((err) => {
          console.log(err)
          alert("유저 중복 확인 오류 발생")
        })
      } else {
        alert("닉네임을 입력하세요")
      }
    },
    Exit() {
      this.$router.push('/')
    },
    empityCheck() {
      if (this.idCheck() && this.userCh && this.nickCh && this.wordCheck &&
          this.passCheck() && this.niCheck && this.zcCheck() && this.addCheck() &&
          this.phCheck() && this.mailCheck()
          && this.emailAuthBoolean) {
        return true
      } else {
        return false
      }
    },
    idCheck() {
      if (this.userID === '') {
        return false
      } else {
        return true
      }
    },
    passCheck() {
      if (this.password === '') {
        return false
      } else {
        return true
      }
    },
    niCheck() {
      if (this.nick === '') {
        return false
      } else {
        return true
      }
    },
    zcCheck() {
      if (this.zc === '') {
        return false
      } else {
        return true
      }
    },
    addCheck() {
      if (this.add === '') {
        return true
      } else {
        return true
      }
    },
    phCheck() {
      if (this.ph === '') {
        return false
      } else {
        return true
      }
    },
    mailCheck() {
      if (this.email === '') {
        return false
      } else {
        return true
      }
    },
    emailSend(){
      axios.post("http://localhost:9002/api/mailCheck",{
        email:this.email
      }).then((res)=>{
        this.emailAuth = res.data
        alert("인증코드가 발송되었습니다")
      }).catch((err)=>{
        console.log(err)
        console.log("fail")
        alert("이메일을 다시 확인해주세요")
      })
    },
    mailAuthCheck(){
      if(this.emailAuth == this.emailAuthInput){
        this.emailAuthBoolean=true
        alert("인증번호가 맞습니다")
      }else{
        alert("인증번호가 다릅니다")
      }
    },
    phSend(){
      axios.post("/api/phCheck",{
        MPH:this.ph
      }).then((res)=>{
        console.log(res)
        this.serverAuth = res.data
        alert("인증번호가 발송되었습니다")
      }).catch((err)=>{
        console.log(err)
        alert("전화번호를 다시 확인해주세요")
      })
    },
    phAuthCheck(){
      if(this.serverAuth == this.clientAuth){
        alert("인증되었습니다")
        this.authCheck = true
      }else{
        alert("인증번호가 다릅니다 다시 확인해주세요")
      }
    }
  },
  computed: {
    paCheck: function () {
      if (this.password === '') {
        return ''
      } else if (this.password !== this.pasCheck) {
        this.wordCheck = false
        return "비밀번호가 다릅니다"
      } else if (this.password === this.pasCheck) {
        this.wordCheck = true
        return "비밀번호가 동일합니다"
      }
      return ''
    }
  },
  watch: {
    userID(){
      this.userCh = false
    },
    nick(){
      this.nickCh = false
    }
  }
}
</script>

<style scoped>
body {
  margin: 0;
  height: 100%;
  background-color: #E6E6FA;
}

#wrapper {
  position: relative;
  height: 100%;
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
  margin: 30px 0 91px;
}

#btnJoin {
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
