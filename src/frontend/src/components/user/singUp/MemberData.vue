<template>
  <img :src="backImg" class="backImg">
  <div id="bodyDiv">
    <div>
      <h2>정보수정</h2>
    </div>
    <div class="inputDiv">
      <label>아이디</label>
      <input type="text" readonly v-model="MID" placeholder="아이디 입력" maxlength="20">
    </div>
    <div class="inputDiv">
      <label>닉네임</label>
      <input type="text" v-model="MNick" placeholder="닉네임 입력">
      <button @click="nameCheck" :disabled="btnNch" class="inputBtn">중복확인</button>
    </div>
    <div class="inputDiv">
      <div>
        <label>주소</label>
        <input type="text" v-model="MZadd" placeholder="우편주소" class="inputTextAdd" readonly="true">
        <button id="button-addon2" @click="zcGet" class="inputBtn" style="width: 120px;">우편주소검색</button>
      </div>
      <div class="lineDiv">
        <input type="text" v-model="MRadd" maxlength="100" class="inputTextAdd" placeholder="도로명주소" readonly>
      </div>
      <div class="lineDiv">
        <input type="text" v-model="MAdd" maxlength="100" class="inputTextAdd" placeholder="상세주소">
      </div>
    </div>
    <div class="inputDiv">
      <label>이름</label>
      <input type="text" v-model="Mname" placeholder="이름입력" readonly>
    </div>
    <div class="inputDiv">
      <div>
        <label>전화번호</label>
        <input type="text" v-model="MPH" placeholder="전화번호 입력" maxlength="11"
               :disabled="authCheck" oninput="javascript: this.value = this.value.replace(/[^0-9]/g, '');">
        <button @click="phSend" :disabled="authCheck" class="inputBtn">전송</button>
      </div>
      <div class="lineDiv">
        <input type="text" v-model="clientAuth" placeholder="인증번호입력" :disabled="authCheck">
        <button @click="phAuthCheck" :disabled="authCheck" class="inputBtn">인증확인</button>
      </div>
    </div>
    <div class="inputDiv">
      <div>
        <label>이메일</label>
        <input type="text" v-model="MEmail" placeholder="이메일 입력" :disabled="emailAuthBoolean"
               style="width: 250px">
        <button @click="emailSend" :disabled="emailAuthBoolean" class="inputBtn">전송</button>
      </div>
      <div class="lineDiv">
        <input type="text" v-model="MEmailAuthInput" placeholder="인증번호입력" :disabled="emailAuthBoolean"
               style="width: 250px">
        <button @click="mailAuthCheck" :disabled="emailAuthBoolean" class="inputBtn">인증확인</button>
      </div>
    </div>
    <div class="inputDiv" style="text-align: center;">
      <button type="button" @click="update" class="updateBtn">수정</button>
      <button type="button" @click="deleteMem" class="updateBtn">탈퇴</button>
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
      Mname:'',
      MEmail:'',
      Member:null,
      nickCh:false,
      btnNch:true,
      emailAuth:'',
      MEmailAuthInput:'',
      emailAuthBoolean:false,
      clientAuth:'',
      serverAuth:'',
      authCheck:false,
      clientCheckMail:true,
      clientCheckPH:true,
      backImg:require("@/assets/camp1.jpg")
    }
  },
  methods:{
    update(){
      if(this.empityCheck() && this.nickCh
        && this.clientCheckMail && this.clientCheckPH
      ){
        axios.post("api/memberUpdate",{
          MID:this.MID,
          MNick:this.MNick,
          MZadd:this.MZadd,
          MRadd:this.MRadd,
          MAdd:this.MAdd,
          MPH:this.MPH,
          MEmail:this.MEmail,
          Mname:this.Mname
        }).then((res)=>{
          if(res.data){
            alert("업데이트 되었습니다")
            this.$router.go()
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
          this.MZadd = data.zonecode;
          this.MRadd = data.roadAddress;
        }
      }).open({popupKey: '주소검색'})
    },
    empityCheck() {
      if (this.MNick === '') {
        return false
      } else if (this.MZadd === '') {
        return false
      } else if (this.MRadd === '') {
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
        this.emailAuthBoolean = true
        this.clientCheckMail = true
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
        this.clientCheckPH = true
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
      this.Mname = this.Member.mname
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
    },
    MEmail:function (){
      if(this.MEmail === this.Member.mmail){
        this.clientCheckMail = true
      }else{
        this.clientCheckMail = false
        this.test = '변동'
      }
    },
    MPH:function (){
      if(this.MPH === this.Member.mph){
        this.clientCheckPH = true
      }else{
        this.clientCheckPH = false
      }
    }
  }
}
</script>

<style scoped>
.backImg{
  margin-top: 1px;
  width: 100%;
  height: 100%;
  position: absolute;
  z-index: 1;
}
#bodyDiv{
  padding: 30px;
  padding-top: 5%;
  padding-bottom: 5%;
  background: white;
  margin-left: 25%;
  margin-right: 25%;
  margin-top: 2%;
  margin-bottom: 7%;
  width: 50%;
  height: 93%;
  z-index: 2;
  position: absolute;
  border-radius: 100px;
}
label{
  width: 80px;
}
.inputDiv{
  margin-top: 5px;
  margin-bottom: 5px;
}
.inputBtn{
  margin-left: 10px;
  padding: 1px;
  width: 80px;
  color: black;
  background: #E6E6FA;
  border: 1px solid black;
}
.lineDiv{
  margin-left: 80px;
  margin-top: 5px;
  margin-bottom: 5px;
}
.inputTextAdd{
  width: 320px;
}
.updateBtn{
  margin-top: 20px;
  width: 100%;
  padding-top: 15px;
  padding-bottom: 15px;
  font-size: 30px;
  color: black;
  background: #E6E6FA;
  border: 1px solid black;
}
.updateBtn:hover, .inputBtn:hover{
  color: white;
  background: black;
  border: 1px solid red;
}
</style>
