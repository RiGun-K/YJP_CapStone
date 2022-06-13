<template>
  <img :src="backImg" class="backImg">
  <div class="bodyDiv">
    <div id="content">
      비밀번호 찾기
      <div>
        <h3 class="join_title">
          <label for="email">아이디</label>
        </h3>
        <span class="input-group mb-3">
          <input type="text" v-model="clientID" class="form-control" placeholder="아이디입력" aria-label="Recipient's username"
                 aria-describedby="button-addon2" :readonly="idBool">
          <button class="btn btn-outline-secondary" type="button" id="button-addon2"
                  :disabled="idBool" @click="idCheck">아이디확인</button>
        </span>
        <span class="error_next_box"></span>
      </div>
      <div v-if="idBool">
        <h3 class="join_title">
          <label for="email">전화번호</label>
        </h3>
        <span class="input-group mb-3">
          <input type="text" v-model="clientPH" class="form-control" placeholder="전화번호 입력" aria-label="Recipient's username" maxlength="11"
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
      <div style="text-align: center" v-if="authCheck">
        <br/><br/>
        <h3>
          <label>{{resultString}}</label>
        </h3>
        <br/>
      </div>
      <div style="text-align: center">
        <button @click="returnLogin" class="btnCommon">돌아가기</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "PassSearch",
  data(){
    return{
      clientPH:'',
      clientAuth:'',
      serverAuth:'',
      authCheck:false,
      clientID:'',
      idBool:false,
      resultString:'',
      backImg:require("@/assets/camp1.jpg")
    }
  },
  methods:{
    phSend(){
      axios.post("/api/phequalCheck",{
        MID:this.clientID,
        MPH:this.clientPH
      }).then((res)=>{
        if(res.data){
          axios.post("/api/phCheck",{
            MPH:this.clientPH
          }).then((res)=>{
            console.log(res)
            this.serverAuth = res.data
            alert("인증번호가 발송되었습니다")
          }).catch((err)=>{
            console.log(err)
            alert("전화번호를 다시 확인해주세요")
          })
        }else{
          alert("아이디와 사용 전화번호가 불일치 합니다")
        }
      }).catch((err)=>{
        console.log(err)
      })
    },
    phAuthCheck(){
      if(this.serverAuth == this.clientAuth){
        alert("인증되었습니다")
        this.authCheck = true
        axios.post("/api/checkPass",{
          MID:this.clientID
        }).then((res)=>{
              console.log(res)
              this.resultString = res.data
            }).catch((err)=>{
          console.log(err)
        })
      }else{
        alert("인증번호가 다릅니다 다시 확인해주세요")
      }
    },
    idCheck(){
      axios.post("/api/userCheck",{
        MID:this.clientID
      }).then((res)=>{
        if(res.data){
          alert("존재하지 않는 아이디입니다")
        }else{
          alert("사용자 전화번호를 입력해주세요")
          this.idBool = true
        }
      }).catch((err)=>{
        console.log(err)
      })
    },
    returnLogin(){
      this.$router.push("/login")
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
.bodyDiv{
  border-radius: 100px;
  padding: 30px;
  padding-top: 8%;
  position: absolute;
  margin-top: 2%;
  margin-bottom: 5%;
  margin-left: 25%;
  margin-right: 25%;
  width: 50%;
  height: 93%;
  background: white;
  z-index: 2;
}
.btnCommon{
  margin-left: 1%;
  padding: 0.2%;
  width: 100px;
  text-align: center;
  border: black solid 3px;
  background: white;
  color: black;
}
.btnCommon:hover{
  border: red solid 3px;
  background: black;
  color: white;
}
</style>
