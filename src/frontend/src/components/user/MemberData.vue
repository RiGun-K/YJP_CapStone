<template>
  <div id="wrapper">
    <div id="content">
      <br><br>
      <h2>정보수정 입니다</h2>
      <div>
        <br>
        <h3 class="join_title">
          <label for="id">아이디</label>
        </h3>
        <span class="box int_id">
                        <input type="text" readonly v-model="MID" id="id" placeholder="아이디 입력" class="int" maxlength="20">
                    </span>
        <span class="error_next_box"></span>
        <div>
          <h3 class="join_title">
            <label for="id">닉네임</label>
          </h3>

          <span class="input-group mb-3">
          <input type="text" v-model="MNick" class="form-control" placeholder="닉네임 입력" aria-label="Recipient's username" aria-describedby="button-addon2">
          <button class="btn btn-outline-secondary" type="button" id="button-addon2" @click="nameCheck" :disabled="btnNch">중복확인</button>
        </span>
          <span class="error_next_box"></span>
        </div>
        <div>
          <h3 class="join_title">
            <label for="zCode">주소</label>
          </h3>
          <span class="input-group mb-3">
        <input type="text" v-model="MZadd" class="form-control" placeholder="우편주소"
               aria-label="Recipient's username" aria-describedby="button-addon2" readonly="true">
        <button class="btn btn-outline-secondary" type="button" id="button-addon2" @click="zcGet">우편주소검색</button>
        </span>
        </div>

        <div>
          <span class="input-group mb-3">
                        <input type="text" v-model="MRadd" id="email" class="form-control" maxlength="100" placeholder="도로명주소" readonly>
          </span>
          <span class="error_next_box">도로명주소를 다시 확인해주세요.</span>
        </div>

        <div>
          <span class="input-group mb-3">
                        <input type="text" v-model="MAdd" id="email" class="form-control" maxlength="100" placeholder="상세주소">
          </span>
          <span class="error_next_box">상세주소를 다시 확인해주세요.</span>
        </div>

        <div>
          <h3 class="join_title">
            <label for="email">전화번호</label>
          </h3>
          <span class="input-group mb-3">
          <input type="text" v-model="MPH" class="form-control" placeholder="전화번호 입력" aria-label="Recipient's username" maxlength="11"
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
          <input type="text" v-model="MEmail" class="form-control" placeholder="이메일 입력" aria-label="Recipient's username"
                 aria-describedby="button-addon2" :disabled="emailAuthBoolean">
          <button class="btn btn-outline-secondary" type="button" id="button-addon2" @click="emailSend" :disabled="emailAuthBoolean">전송</button>
        </span>
          <span class="input-group mb-3">
          <input type="text" v-model="MEmailAuthInput" class="form-control" placeholder="인증번호입력" aria-label="Recipient's username"
                 aria-describedby="button-addon2" :disabled="emailAuthBoolean">
          <button class="btn btn-outline-secondary" type="button" id="button-addon2" @click="mailAuthCheck" :disabled="emailAuthBoolean">인증확인</button>
        </span>
          <span class="error_next_box"></span>

        </div>
        <br>
        <div class="btn_area">
          <button type="button" @click="update" id="btnJoin">
            <span>수정</span>
          </button>
          <button type="button" @click="deleteMem" id="btnJoin">
            <span>탈퇴</span>

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
  name: "MemberData",
  data(){
    return{
      MID: store.getters.getLoginState.loginState,
      MNick:'',
      MZadd:'',
      MRadd:'',
      MAdd:'',
      MPH:'',
      MEmail:'',
      Member:null,
      nickCh:false,
      btnNch:true,
      emailAuth:'',
      MEmailAuthInput:'',
      emailAuthBoolean:false,
      clientAuth:'',
      serverAuth:'',
      authCheck:false
    }
  },
  methods:{
    update(){
      if(this.empityCheck() && this.nickCh){
        axios.post("api/memberUpdate",{
          MID:this.MID,
          MNick:this.MNick,
          MZadd:this.MZadd,
          MRadd:this.MRadd,
          MAdd:this.MAdd,
          MPH:this.MPH,
          MEmail:this.MEmail
        }).then((res)=>{
          if(res.data){
            alert("업데이트 되었습니다")
            this.$router.push("/")
          }else{
            alert("업데이트에 문제가 발생했습니다")
          }
        }).catch((err)=>{
          console.log(err)
        })
      }else{
        alert("항목을 다시 확인해주세요.")
      }
    },
    zcGet() {
      new window.daum.Postcode({
        oncomplete: (data) => {
          this.MZC = data.zonecode;
          this.roadAddress = data.roadAddress;
        }
      }).open({popupKey: '주소검색'})
    },
    empityCheck() {
      if (this.MNick === '') {
        return false
      } else if (this.MZC === '') {
        return false
      } else if (this.MAdd === '') {
        return false
      } else if (this.MPH === '') {
        return false
      } else if (this.MEmail === '') {
        return false
      } else {
        return true
      }
    },
    deleteMem(){
      if(confirm("정말로 탈퇴하시겠습니까?")){
        alert("탈퇴되었습니다")
        axios.post("api/deleteMem",{
          MID:this.MID
        }).then((res)=>{
          if(res.data){
            store.commit("setLogoutState")
            this.$router.push("/")
          }else{
            alert("오류가 발생했습니다 고객센터에 문의해주세요")
          }
        }).catch((err)=>{
          console.log(err)
        })
      }
    },
    nameCheck(){
      if(this.MNick != ''){
        axios.post('api/nameCheck',{
          MNick:this.MNick
        })
            .then((res)=>{
              if(res.data){
                alert("존재하지 않는 닉네임입니다.")
                this.nickCh=true
              }else{
                alert("존재하는 닉네임입니다.")
                this.nickCh=false
              }
            }).catch((err)=>{
          console.log(err)
          alert("유저 중복 확인 오류 발생")
        })
      }else{
        alert("닉네임을 입력하세요")
      }
    },
    emailSend(){
      axios.post("http://localhost:9002/api/mailCheck",{
        email:this.MEmail
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
      if(this.emailAuth == this.MEmailAuthInput){
        this.emailAuthBoolean=true
        alert("인증번호가 맞습니다")
      }else{
        alert("인증번호가 다릅니다")
      }
    },
    phSend(){
      axios.post("/api/phCheck",{
        MPH:this.MPH
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
  mounted() {
    axios.post("api/getMember", {
      MID:this.MID
    }).then((res)=>{
      this.Member = res.data
      this.MNick = this.Member.mnick
      this.MZadd = this.Member.mzadd
      this.MRadd = this.Member.mradd
      this.MAdd = this.Member.madd
      this.MPH = this.Member.mph
      this.MEmail = this.Member.mmail
    }).catch((err)=>{
      console.log(err)
    })
  },
  watch:{
    MNick:function (){
      if(this.MNick === this.Member.mnick){
        this.btnNch = true
        this.nickCh = true
      }else{
        this.btnNch = false
        this.nickCh = false
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
  width: 45%;
  margin-left: 4%;
  padding: 21px 0 17px;
  border: 0;
  cursor: pointer;
  color: white;
  background-color: #52a3ef;
  font-size: 20px;
  font-weight: 400;
}
</style>
