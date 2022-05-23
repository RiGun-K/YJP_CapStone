<template>
  <table border="1px" align="center" style="margin-left: 5%; margin-right: 5%;
                                            width: 90%; margin-top: 1%" >
    <tr>
      <th colspan="10"><h1 style="color: black">판매 관리자</h1></th>
    </tr>
    <tr>
      <th>신청자</th>
      <th>신청일자</th>
      <th>회사명</th>
      <th>사업자코드</th>
      <th>사업자</th>
      <th>주소</th>
      <th>이메일</th>
      <th>홈페이지</th>
      <th>전화번호</th>
      <th>허가</th>
    </tr>
    <tr v-for="(company) in viewList" v-bind:key="company.ccode">
      <td >{{company.member.mid}}</td>
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
        <button>취소</button>
      </td>
      <td v-else>
        {{acceptCheck(company.csc)}}
      </td>
    </tr>
  </table>
  <div class="searchDiv">
    <input type="text" v-model="searchWord">
    <button class="searchBtn" @click="searchId">검색</button>
    <button @click="reStart" class="resetBtn">목록 초기화</button>
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
      searchWord:''
    }
  },
  methods:{
    accept(index){
      axios.post("/api/acceptCompany",{
        CCode:index
      }).then((res)=>{
        console.log(res)
        this.$router.go()
      }).catch((err)=>{
        console.log(err)
      })
    },
    searchId(){
      if(this.searchWord.length <= 0){
        alert("검색어를 입력해주세요")
      }else{
        this.viewList = []
        for(var i = 0; i < this.request.length; i++){
          if(this.request[i].member.mid.includes(this.searchWord)){
            this.viewList.push(this.request[i])
          }
        }
      }
    },
    refuse(){

    },
    acceptCheck(index){
      if(index ==0){
        return "정지됨"
      }else{
        return "승인됨"
      }
    },
    reStart(){
      this.$router.go()
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
th, td{
  text-align: center;
  border: 1px solid #111111;
}
.searchBtn{
  margin-left: 1%;
  padding: 0.5%;
}
.searchDiv{
  margin-left: 5%;
  margin-right: 5%;
  margin-top: 1%;
  width: 90%;
  text-align: center;
}
.resetBtn{
  float: right;
  position: relative;
  padding: 0.5%;
  background: #7ea6f6;
  color: white;
  text-align: center;
}
</style>
