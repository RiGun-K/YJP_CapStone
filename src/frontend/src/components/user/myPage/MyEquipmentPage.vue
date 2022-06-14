<template>
  <img :src="backImg" class="backImg">
  <div id="content">
    <table border="1px" style="margin-top: 1%">
      <tr>
        <th colspan="5">
          <h1 style="color: black; text-align: center">내 장비</h1>
        </th>
      </tr>
      <tr>
        <th>장비명</th>
        <th>장비분류</th>
        <th>장비상태</th>
        <th>장비수량</th>
        <th>수정</th>
      </tr>
      <tr v-for="(body, index) in EquipList" :key="body.memEquipmentCode">
        <td>{{ body.memEquipmentName}}</td>
        <td>{{ body.kindid.kindname}}</td>
        <td>{{ stateCheck(body.memEquipmentState)}}</td>
        <td>{{ body.memEquipmentCount}}</td>
        <td><button @click="updateEquip(index)" class="btnCommon">수정</button></td>
      </tr>
    </table>
    <button @click="addEquip" class="EquipBtn">장비추가</button>
  </div>
</template>

<script>
import axios from "axios";
import store from "@/store";

export default {
  name: "MyEquipmentPage",
  data(){
    return{
      EquipList:[],
      backImg:require("@/assets/camp1.jpg")
    }
  },
  methods:{
    stateCheck(sCode) {
      switch (sCode) {
        case "1":
          return "정상"
        default:
          return "비정상"
      }
    },
    addEquip(){
      this.$router.push("/addEquipment")
    },
    updateEquip(index){
      this.$router.push({
        name:"updateEquipment",
        params:{
          equipCode:this.EquipList[index].memEquipmentCode
        }
      })
    }
  },
  created(){
    axios.post("/api/myPageEquip",{
      MID:store.getters.getLoginState.mcode
    })
    .then((res)=>{
      this.EquipList = res.data
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
th, td{
  border: 1px solid black;
}
.EquipBtn{
  margin-top: 1%;
  margin-left: 1%;
  padding: 0.2%;
  text-align: center;
  border: black solid 3px;
  width: 100px;
  background: white;
  color: black;
  float: right;
}
.EquipBtn:hover{
  margin-top: 1%;
  float: right;
  border: red solid 3px;
  background: black;
  color: white;
}
#content {
  margin-right: 15%;
  margin-left: 15%;
  margin-top: 2%;
  margin-bottom: 5%;
  text-align: center;
  width: 70%;
  height: 93%;
  padding: 30px;
  z-index: 2;
  position: relative;
  background: white;
}

.btnCommon{
  margin-left: 1%;
  padding: 0.2%;
  text-align: center;
  border: black solid 3px;
  width: 50px;
  background: white;
  color: black;
}
.btnCommon:hover{
  border: red solid 3px;
  background: black;
  color: white;
}
</style>

