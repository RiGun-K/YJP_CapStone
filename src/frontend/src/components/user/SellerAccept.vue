<template>
  <table border="1px" align="center" style="margin-left: 5%; margin-right: 5%;
                                            width: 90%" >
    <tr>
      <th colspan="10">판매 관리자</th>
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
    <tr v-for="(company, index) in request" v-bind:key="company.ccode">
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
        <button @click="accept(index)">승인</button>
        <button>취소</button>
      </td>
      <td v-else>
        {{acceptCheck(company.csc)}}
      </td>
    </tr>
  </table>
  <br/><br/>
</template>

<script>
import axios from "axios";
export default {
  name: "SellerAccept",
  data(){
    return{
      request:null
    }
  },
  methods:{
    accept(index){
      axios.post("/api/acceptCompany",{
        CCode:this.request[index].ccode
      }).then((res)=>{
        console.log(res)
      }).catch((err)=>{
        console.log(err)
      })
    },
    refuse(){

    },
    acceptCheck(index){
      if(index ==0){
        return "정지됨"
      }else{
        return "승인됨"
      }
    }
  },
  created() {
    axios.get("/api/getRequestCompany").then((res)=>{
      this.request = res.data
    }).catch((err)=>{
      console.log(err)
    })
  }
}
</script>

<style scoped>

</style>
