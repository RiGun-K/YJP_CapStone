<template>
  <table border="1px" align="center" style="margin-left: 5%; margin-right: 5%;
                                            width: 90%;">
    <tr>
      <th colspan="9">회원 관리자</th>
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
  width: 100%;
  padding: 21px 0 17px;
  border: 0;
  cursor: pointer;
  color: white;
  background-color: #52a3ef;
  font-size: 20px;
  font-weight: 400;
}
</style>
