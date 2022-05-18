<template>
  <table border="1px" align="center" style="margin-left: 5%; margin-right: 5%;
                                            width: 90%; margin-top: 1%">
    <tr style="text-align: center;">
      <th colspan="9"><h1 style="color: #111111">회원 관리자</h1></th>
    </tr>
    <tr style="text-align: center">
      <th>아이디</th>
      <th>닉네임</th>
      <th>이메일</th>
      <th>주소</th>
      <th>상태코드</th>
      <th>가입일자</th>
      <th>탈퇴일자</th>
    </tr>
    <tr v-for="(member, index) in members" v-bind:key="member.mcode" v-show="showDisable(index)"
        style="text-align: center">
      <td >{{member.mid}}</td>
      <td>{{member.mnick}}</td>
      <td>{{member.mmail}}</td>
      <td>{{member.mradd}} <br/> {{member.madd}}</td>
      <td>
        <select v-model="member.msc" @change="changeSelect(index)" :disabled="mscDisable(index)" >
          <option value="0" disabled="true">탈퇴회원</option>
          <option value="1">정지회원</option>
          <option value="2" disabled="true">미승인회원</option>
          <option value="3">일반회원</option>
          <option value="4">판매자회원</option>
        </select>
      </td>
      <td>{{member.msd}}</td>
      <td v-if="member.mld">{{member.mld}}</td>
      <td v-else>-</td>
    </tr>
  </table>
  <br/><br/>
</template>

<script>
import axios from "axios";

export default {
  name: "MemberManagementAdmin",
  data(){
    return{
      members:[]
    }
  },
  methods:{
    mscDisable(index){
      if(this.members[index].msc == 0){
        return true
      }else{
        return false
      }
    },
    showDisable(index){
      if(this.members[index].msc == 5){
        return false
      }else{
        return true
      }
    },
    changeSelect(index){
      axios.post("api/chagneMSC",{
        MID:this.members[index].mid,
        MSC:this.members[index].msc
      }).then((res)=>{
        console.log(res.data)
      }).catch((err)=>{
        console.log(err)
      })
    }
  },
  created() {
    axios.get("api/getAllmember")
        .then((res)=>{
          this.members = res.data
        })
        .catch((err)=>{
          console.log(err)
        })
  }
}
</script>


<style scoped>
th, td{
  border: 1px solid #111111;
}
</style>
