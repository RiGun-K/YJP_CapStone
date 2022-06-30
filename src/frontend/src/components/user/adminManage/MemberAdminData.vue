<template>
  <img :src="backImg" class="backImg">
  <div class="divBody">
    <div style="text-align: center">
      <h1 style="color: black">{{member.mid}}회원 정보조회</h1>
    </div>
    <div>
      <label>회원 아이디</label>
      <p class="inputText">{{member.mid}}</p>
    </div>
    <div>
      <label>회원 비밀번호</label>
      <p class="inputText">{{member.mpass}}</p>
    </div>
    <div>
      <label>회원 닉네임</label>
      <p class="inputText">{{member.mnick}}</p>
    </div>
    <div>
      <label>회원 이름</label>
      <p class="inputText">{{member.mname}}</p>
    </div>
    <div>
      <label>회원 주소</label>
      <p class="inputText" style="width: 320px">{{member.mzadd}}</p>
      <p class="inputAdd">{{member.mradd}}</p>
      <p class="inputAdd">{{maddCheck(member.madd)}}</p>
    </div>
    <div>
      <label>회원 전화번호</label>
      <p class="inputText">{{member.mph}}</p>
    </div>
    <div>
      <label>회원 이메일</label>
      <p class="inputText">{{member.mpass}}</p>
    </div>
    <div>
      <label>회원 상태</label>
      <p class="inputText">{{chMsc(member.msc)}}</p>
    </div>
    <div>
      <label>회원 가입일</label>
      <p class="inputText">{{member.msd}}</p>
      <br/>
      <label>회원 탈퇴일</label>
      <p class="inputText">{{chMld(member.mld)}}</p>
    </div>
    <div style="text-align: right;">
      <button @click="returnMemberList" class="btnCommon">돌아가기</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "memberData",
  data(){
    return{
      member:[],
      backImg:require("@/assets/camp1.jpg")
    }
  },
  methods:{
    chMsc(index){
      switch (index){
        case "0":
          return "정지회원"
        case "1":
          return "탈퇴회원"
        case "2":
          return "판매자 승인대기"
        case "3":
          return "일반회원"
        case "4":
          return "판매자회원"
        case "5":
          return "관리자"
      }
    },
    chMld(index){
      if(index == null){
        return "미탈퇴회원"
      }else{
        return index
      }
    },
    returnMemberList(){
      this.$router.push("/memberAdmin")
    },
    maddCheck(text){
      if(text == null){
        return "상세주소없음"
      }else{
        return text
      }
    }
  },
  created() {
    axios.post("/api/getMember",{
      MID:this.$route.params.mid
    }).then((res)=>{
      console.log(res)
      this.member = res.data
    }).catch((err)=>{
      console.log(err)
    })
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
.divBody{
  border-radius: 100px;
  padding: 50px;
  position: absolute;
  margin-top: 2%;
  margin-bottom: 5%;
  margin-left: 10%;
  margin-right: 10%;
  width: 80%;
  height: 80%;
  background: white;
  z-index: 2;
}
label{
  margin-top: 5px;
  margin-bottom: 5px;
  width: 120px;
}
.inputText{
  border: 1px solid black;
  width: 200px;
  display: inline-flex;
  padding-left: 5px;
}
.inputAdd{
  border: 1px solid black;
  margin-left: 120px;
  width: 320px;
  padding-left: 5px;
}
.btnCommon{
  margin-left: 15px;
  padding: 0.2%;
  text-align: center;
  border: black solid 3px;
  background: white;
  width: 100px;
  color: black;
}
.btnCommon:hover{
  border: red solid 3px;
  background: black;
  color: white;
}
</style>
