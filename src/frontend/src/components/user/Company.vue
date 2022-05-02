<template>
  <div id="wrapper">
    <div id="content">
      <br>
      <h2>판매자 등록</h2>
      <br>
      <div>
        <h3 class="join_title">
          <label for="id">사업자 코드</label>
        </h3>

        <span class="input-group mb-3">
          <input type="text" v-model="CCode" class="form-control" placeholder="사업자id 입력" aria-label="Recipient's username" aria-describedby="button-addon2">
          <button class="btn btn-outline-secondary" type="button" id="button-addon2" @click="codeCheck">중복확인</button>
        </span>
        <span class="error_next_box"></span>
        <h3 class="join_title">
          <label for="id">회사명</label>
        </h3>
        <span class="box int_id">
                        <input type="text" v-model="CName" id="id" placeholder="회사명 입력" class="int" maxlength="20">
                    </span>
        <span class="error_next_box"></span>
      </div>

      <div>
        <h3 class="join_title">
          <label for="pswd1" >CEO</label>
        </h3>

        <span class="box int_id">
                        <input type="text" v-model="CEO" id="id" placeholder="필수입력" class="int" maxlength="20">
                    </span>
        <span class="error_next_box"></span>

      </div>
      <div>
        <h3 class="join_title">
          <label for="pswd2">주소</label>
        </h3>
        <span class="input-group mb-3">
        <input type="text" v-model="CZadd" class="form-control" placeholder="우편주소 입력" aria-label="Recipient's username" aria-describedby="button-addon2" readonly>
        <button class="btn btn-outline-secondary" type="button" id="button-addon2" @click="zcGet">우편주소검색</button>
        </span>

      </div>
      <div>
        <span class="input-group mb-3">
                        <input type="text" v-model="CRadd" id="email" class="form-control" maxlength="100" placeholder="도로명입력" readonly>
        </span>
      </div>
      <div>
        <span class="input-group mb-3">
                        <input type="text" v-model="CAdd" id="email" class="form-control" maxlength="100" placeholder="상세주소">
        </span>
        <span class="error_next_box">상세주소를 다시 확인해주세요.</span>
      </div>

      <div>
        <h3 class="join_title">
          <label for="email">홈페이지</label>
        </h3>
        <span class="box int_email">
                        <input type="text" v-model="Chp" id="email" class="int" maxlength="100" placeholder="필수입력">
                    </span>
        <span class="error_next_box">홈페이지 주소를 다시 확인해주세요.</span>
      </div>

      <div>
        <h3 class="join_title">
          <label for="phoneNo">전화번호</label>
        </h3>
        <span class="box int_mobile">
                        <input type="tel" v-model="Cph" id="mobile" class="int" maxlength="11" placeholder="전화번호 입력"
                               oninput="javascript: this.value = this.value.replace(/[^0-9]/g, '');">
                    </span>
        <span class="error_next_box"></span>
      </div>

      <div>
        <h3 class="join_title">
          <label for="email">이메일</label>
        </h3>
        <span class="input-group mb-3">
          <input type="text" v-model="CEmail" class="form-control" placeholder="이메일 입력" aria-label="Recipient's username"
                 aria-describedby="button-addon2" :disabled="emailAuthBoolean">
          <button class="btn btn-outline-secondary" type="button" id="button-addon2" @click="emailSend" :disabled="emailAuthBoolean">전송</button>
        </span>
        <span class="input-group mb-3">
          <input type="text" v-model="CEmailAuthInput" class="form-control" placeholder="인증번호입력" aria-label="Recipient's username"
                 aria-describedby="button-addon2" :disabled="emailAuthBoolean">
          <button class="btn btn-outline-secondary" type="button" id="button-addon2" @click="mailAuthCheck" :disabled="emailAuthBoolean">인증확인</button>
        </span>
        <span class="error_next_box"></span>

      </div>

      <div class="btn_area">
        <button type="button" @click="signPost" id="btnJoin">
          <span>가입하기</span>
        </button>

      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import store from "@/store";

export default {
  name: "Company",
  data(){
    return{
      CCode:'',
      CName:'',
      CEO:'',
      CZadd:'',
      CRadd:'',
      CAdd:'',
      Chp:'',
      Cph:'',
      CEmail:'',
      cCheck:false,
      CEmailAuthInput:'',
      emailAuthBoolean:false,
      emailAuth:''
    }
  },methods:{
    Exit(){
      this.$router.push("/")
    },
    signPost(){
      if(this.empityCheck() && this.cCheck){
        axios.post("api/signCompany",{
          CCode:this.CCode,
          CName:this.CName,
          CEO:this.CEO,
          CZadd:this.CZadd,
          CRadd:this.CRadd,
          CAdd:this.CAdd,
          Chp:this.Chp,
          Cph:this.Cph,
          CEmail:this.CEmail,
          MID:store.getters.getLoginState.loginState
        }).then((res)=>{
          if(res.data){
            alert("정상적으로 신청되었습니다.")
            this.$router.push("/")
          }else{
            alert("이미 신청한 회원입니다.")
          }
        }).catch((err)=>{
          console.log(err)
        })
      }else{
        alert("항목을 다시 확인해주세요.")
      }
    },
    empityCheck(){
      if(this.CName == ''){
        return false
      }else if(this.CEO == ''){
        return false
      }else if(this.CZadd == ''){
        return false
      }else if(this.CAdd == ''){
        return false
      }else if(this.Chp == ''){
        return false
      }else if(this.Cph == ''){
        return false
      }else if(this.CEmail == ''){
        return false
      }else{
        return true
      }
    },
    zcGet() {
      new window.daum.Postcode({
        oncomplete: (data) => {
          this.CZadd = data.zonecode;
          this.CRadd = data.roadAddress;
        }
      }).open({popupKey: '주소검색'})
    },
    codeCheck(){
      axios.post("api/companyCodeCheck",{
        CCode:this.CCode
      }).then((res) =>{
        console.log(res)
        if(res.data){
          alert("사용가능한 코드입니다.")
          this.cCheck = true
        }else{
          alert("이미 사용된 코드입니다.")
          this.cCheck = false
        }
      }).catch((err) =>{
        console.log(err)
      })
    },
    emailSend(){
      axios.post("http://localhost:9002/api/mailCheck",{
        email:this.CEmail
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
      if(this.emailAuth == this.CEmailAuthInput){
        this.emailAuthBoolean=true
        alert("인증번호가 맞습니다")
      }else{
        alert("인증번호가 다릅니다")
      }
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
