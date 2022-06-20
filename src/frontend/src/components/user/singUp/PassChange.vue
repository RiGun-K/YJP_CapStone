<template>
  <img :src="backImg" class="backImg">
  <div id="content">
    <h3 class="join_title">
      <label for="id">비밀번호 변경</label>
    </h3>
    <span class="input-group mb-3">
          <input type="text" v-model="currentPass" class="form-control" placeholder="현재 비밀번호입력"
                 aria-label="Recipient's username" aria-describedby="button-addon2">
    </span>
    <span class="input-group mb-3">
          <input type="text" v-model="clientPass" class="form-control" placeholder="변경 비밀번호입력"
                 aria-label="Recipient's username" aria-describedby="button-addon2">
    </span>
    <span class="input-group mb-3">
          <input type="password" v-model="authPass" class="form-control" placeholder="변경 비밀번호재입력"
             aria-label="Recipient's username" aria-describedby="button-addon2">
    </span>
    <p>{{inputCheck}}</p>
    <span class="error_next_box"></span>
    <button @click="chPass" style="float:right;" class="btnCommon">확인</button>
  </div>
</template>

<script>
import axios from "axios";
import store from "@/store";

export default {
  name: "PassChange",
  data(){
    return{
      currentPass:'',
      clientPass:'',
      authPass:'',
      passCheck:false,
      backImg:require("@/assets/camp1.jpg")
    }
  },
  methods:{
    returnLogin(){

    },
    chPass(){
      if(this.passCheck && this.currentPass != '')
      axios.post("/api/changePass",{
        MCODE:store.getters.getLoginState.mcode,
        currentPass:this.currentPass,
        changePass:this.clientPass
      }).then((res)=>{
        if(res.data){
          alert("비밀번호가 변경되었습니다")
          this.$router.go()
        }else{
          alert("비밀번호를 확인해주세요")
        }
      })
    }
  },
  computed:{
    inputCheck(){
      if(this.clientPass==''){
        this.passCheck = false
        return ""
      }else if(this.clientPass == this.authPass){
        this.passCheck = true
        return "비밀번호가 일치합니다"
      }else{
        this.passCheck = false
        return "비밀번호가 불일치합니다"
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
#content {
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
