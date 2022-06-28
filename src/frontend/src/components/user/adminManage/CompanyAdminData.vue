<template>
  <img :src="backImg" class="backImg">
  <div class="divBody">
    <div style="text-align: center">
      <h1 style="color: black">판매자 정보조회</h1>
    </div>
    <div>
      <label>사업자코드</label>
      <p class="inputText">{{company.ccode}}</p>
    </div>
    <div>
      <label>회사명</label>
      <p class="inputText">{{company.cname}}</p>
    </div>
    <div>
      <label>CEO</label>
      <p class="inputText">{{company.ceo}}</p>
    </div>
    <div>
      <label>판매자 주소</label>
      <p class="inputText" style="width: 320px">{{company.czadd}}</p>
      <p class="inputAdd">{{company.cradd}}</p>
      <p class="inputAdd">{{addCheck(company.cadd)}}</p>
    </div>
    <div>
      <label>판매자 전화번호</label>
      <p class="inputText">{{company.cph}}</p>
    </div>
    <div>
      <label>판매자 홈페이지</label>
      <p class="inputText">{{company.chp}}</p>
    </div>
    <div>
      <label>판매자 메일</label>
      <p class="inputText">{{company.cemail}}</p>
    </div>
    <div style="text-align: right;">
      <button @click="returnCompanyList" class="btnCommon">돌아가기</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import store from "@/store";

export default {
  name: "CompanyAdminData",
  data(){
    return{
      company:[],
      backImg:require("@/assets/camp1.jpg")
    }
  },
  methods:{
    returnCompanyList(){
      this.$router.push("/sellerAccept")
    },
    addCheck(str){
      if(str == null){
        return "상세주소없음"
      }else{
        return str
      }
    }
  },
  created() {
    axios.post("/api/myCompanySearch",{
      MID:this.$route.params.mid
    }).then((res)=>{
      this.company = res.data
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
  height: 150%;
  position: absolute;
  z-index: 1;
}
.divBody{
  border-radius: 100px;
  padding: 30px;
  padding-top: 5%;
  position: absolute;
  margin-top: 2%;
  margin-bottom: 5%;
  margin-left: 10%;
  margin-right: 10%;
  width: 80%;
  height: 143%;
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
