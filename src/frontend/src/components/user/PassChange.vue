<template>
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
          <input type="text" v-model="authPass" class="form-control" placeholder="변경 비밀번호재입력"
             aria-label="Recipient's username" aria-describedby="button-addon2">
    </span>
    <p>{{inputCheck}}</p>
    <span class="error_next_box"></span>
    <button @click="chPass" style="float:right;">확인</button>
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
      passCheck:false
    }
  },
  methods:{
    returnLogin(){

    },
    chPass(){
      console.log(store.getters.getLoginState.mcode)
      if(this.passCheck && this.currentPass != '')
      axios.post("/api/changePass",{
        MCODE:store.getters.getLoginState.mcode,
        currentPass:this.currentPass,
        changePass:this.clientPass
      }).then((res)=>{
        if(res.data){
          alert("비밀번호가 변경되었습니다")
          this.$router.push("/myPage")
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
