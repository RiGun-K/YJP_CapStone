<template>
  <img :src="backImg" class="backImg">
  <div class="viewBody">
    <table class="tableBody">
      <tr>
        <th colspan="7"><h1 style="color: #111111">회원 관리자</h1></th>
      </tr>
      <tr>
        <th class="idSet">아이디</th>
        <th class="nickSet">닉네임</th>
        <th class="emailSet">이메일</th>
        <th class="addSet">주소</th>
        <th class="stateSet">상태코드</th>
        <th class="startSet">가입일자</th>
        <th class="leaveSet">탈퇴일자</th>
      </tr>
      <tr v-for="(member, index) in viewList" v-bind:key="member.mcode" v-show="showDisable(index)">
        <td class="idSet" @click="showMemberData(member.mid)">{{member.mid}}</td>
        <td class="nickSet" @click="showMemberData(member.mid)">{{member.mnick}}</td>
        <td class="emailSet" @click="showMemberData(member.mid)">{{member.mmail}}</td>
        <td class="addSet" @click="showMemberData(member.mid)">{{member.mradd}} <br/> {{member.madd}}</td>
        <td class="stateSet">
          <select v-model="member.msc" @change="changeSelect(index)" :disabled="mscDisable(index)" >
            <option value="0" disabled="true">탈퇴회원</option>
            <option value="1">정지회원</option>
            <option value="2" disabled="true">미승인회원</option>
            <option value="3">일반회원</option>
            <option value="4">판매자회원</option>
          </select>
        </td>
        <td class="startSet">{{member.msd}}</td>
        <td v-if="member.mld" class="leaveSet">{{member.mld}}</td>
        <td v-else class="leaveSet">-</td>
      </tr>
    </table>
    <div class="searchDiv">
      <input type="text" v-model="searchWord">
      <button class="searchBtn" @click="searchList">검색</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "MemberManagementAdmin",
  data(){
    return{
      members:[],
      searchWord:'',
      viewList:[],
      backImg:require("@/assets/camp1.jpg")
    }
  },
  methods:{
    searchList(){
        this.viewList = []
        for(var i = 0; i < this.members.length; i++){
          if(this.members[i].mid.includes(this.searchWord)){
            this.viewList.push(this.members[i])
          }
        }
    },
    mscDisable(index){
      if(this.viewList[index].msc == 0){
        return true
      }else{
        return false
      }
    },
    showDisable(index){
      if(this.viewList[index].msc == 5){
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
    },
    showMemberData(index){
        this.$router.push({
          name:"memberData",
          params:{
            mid:index
          }
        })
    }
  },
  created() {
    axios.get("api/getAllmember")
        .then((res)=>{
          this.members = res.data
          this.viewList = this.members
          console.log(this.viewList)
        })
        .catch((err)=>{
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
th, td{
  border: 1px solid #111111;
}
tr, td{
  text-align: center;
}
.viewBody{
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
.tableBody{
  width: 100%;
  border: 1px solid black;
  text-align: center;
}
.searchDiv{
  margin-left: 5%;
  margin-right: 5%;
  margin-top: 1%;
  width: 90%;
  text-align: center;
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
.idSet, .nickSet, .leaveSet, .startSet, .stateSet{
  width: 10%;
}
.emailSet{
  width: 20%;
}
.addSet{
  width: 25%;
}
</style>
