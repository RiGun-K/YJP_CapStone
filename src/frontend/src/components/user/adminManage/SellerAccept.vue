<template>
  <img :src="backImg" class="backImg">
  <div class="contentBody">
    <table>
      <tr>
        <th colspan="10"><h1 style="color: black">판매 관리자</h1></th>
      </tr>
      <tr>
        <th>회원아이디</th>
        <th>신청일자</th>
        <th>회사명</th>
        <th>사업자코드</th>
        <th>CEO</th>
        <th>주소</th>
        <th>이메일</th>
        <th>홈페이지</th>
        <th>전화번호</th>
        <th>상태</th>
      </tr>
      <tr v-for="(company) in viewList" v-bind:key="company.ccode">
        <td @click="showMemberCompany(company.member.mcode)">{{company.member.mid}}</td>
        <td>{{company.cst}}</td>
        <td>{{company.cname}}</td>
        <td>{{company.ccode}}</td>
        <td>{{company.ceo}}</td>
        <td>{{company.cradd}} <br/> {{company.cadd}}</td>
        <td>{{company.cemail}}</td>
        <td>{{company.cph}}</td>
        <td>{{company.chp}}</td>
        <td v-if="company.csc == 1">
          <button @click="accept(company.ccode)">승인</button>
          <button @click="refuse(company.ccode)">취소</button>
        </td>
        <td v-else @click="chScs(company.ccode)">
          {{acceptCheck(company.csc)}}
        </td>
      </tr>
    </table>
    <div class="searchDiv">
      <input type="text" v-model="searchWord">
      <button class="searchBtn" @click="searchId">검색</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "SellerAccept",
  data(){
    return{
      request:null,
      viewList:[],
      searchWord:'',
      backImg:require("@/assets/camp1.jpg")
    }
  },
  methods:{
    accept(index){
      axios.post("/api/acceptCompany",{
        CCode:index
      }).then((res)=>{
        if(res.data){
          this.$router.go()
        }
      }).catch((err)=>{
        console.log(err)
      })
    },
    searchId(){
        this.viewList = []
        for(var i = 0; i < this.request.length; i++){
          if(this.request[i].member.mid.includes(this.searchWord)){
            this.viewList.push(this.request[i])
          }
        }
    },
    refuse(index){
      axios.post("/api/refuseCompany",{
        CCode:index
      }).then((res)=>{
        if(res.data){
          this.$router.go()
        }
      }).catch((err)=>{
        console.log(err)
      })
    },
    acceptCheck(index){
      switch (index){
        case "0":
          return "자격정지"
        case "2":
          return "자격승인"
      }
    },
    chScs(index){
      if(confirm("판매자의 상태를 변경합니까?")){
        axios.post("/api/chcscCompany",{
          CCode:index
        }).then((res)=>{
          if(res.data){
            alert("판매자의 상태가 변경됩니다")
            this.$router.go()
          }
        }).catch((err)=>{
          console.log(err)
        })
      }
    },
    reStart(){
      this.$router.go()
    },
    showMemberCompany(mid){
      this.$router.push({
        name:"companyData",
        params:{
          mid:mid
        }
      })
    }
  },
  created() {
    axios.get("/api/getRequestCompany").then((res)=>{
      this.request = res.data
      this.viewList = this.request
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
.contentBody{
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
th, td{
  text-align: center;
  border: 1px solid #111111;
}
.searchBtn{
  margin-left: 15px;
  padding: 0.2%;
  text-align: center;
  border: black solid 3px;
  background: white;
  width: 60px;
  color: black;
}
.searchBtn:hover{
  border: red solid 3px;
  background: black;
  color: white;
}
.searchDiv{
  margin-left: 5%;
  margin-right: 5%;
  margin-top: 1%;
  width: 90%;
  text-align: center;
}
</style>
