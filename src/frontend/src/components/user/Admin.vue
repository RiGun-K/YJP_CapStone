<template>
  <table border="1px" align="center" style="margin-left: 5%; margin-right: 5%;
                                            width: 90%" >
    <tr>
      <th colspan="9">회원 관리자</th>
    </tr>
    <tr>
      <th>아이디</th>
      <th>닉네임</th>
      <th>이메일</th>
      <th>주소</th>
      <th>상태코드</th>
      <th>가입일자</th>
      <th>탈퇴일자</th>
    </tr>
    <tr v-for="(member, index) in members" v-bind:key="member.mcode" v-show="showDisable(index)">
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
      <td>{{member.mld}}</td>
    </tr>
  </table>
  <br/><br/>
</template>

<script>
import axios from "axios";
export default {
  //eslint-disable-next-line
  name: "Admin",
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
</style>
