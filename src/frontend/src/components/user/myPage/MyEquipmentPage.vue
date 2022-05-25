<template>
  <div id="content">
    <table border="1px" style="margin-top: 1%">
      <tr>
        <th colspan="4">
          <h1 style="color: black; text-align: center">내 장비</h1>
        </th>
      </tr>
      <tr>
        <th>장비명</th>
        <th>장비분류</th>
        <th>장비상태</th>
        <th>장비수량</th>
      </tr>
      <tr v-for="(body) in EquipList" :key="body.memEquipmentCode">
        <td>{{ body.memEquipmentName}}</td>
        <td>{{ body.kindid.kindname}}</td>
        <td>{{ stateCheck(body.memEquipmentState)}}</td>
        <td>{{ body.memEquipmentCount}}</td>
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
      EquipList:[]
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
th, td{
  border: 1px solid black;
}
.EquipBtn{
  margin-top: 1%;
  float: right;
  padding: 0.5px;
}
body {
  margin: 0;
  height: 100%;
  background-color: #E6E6FA;
}
#content {
  position: absolute;
  left: 50%;
  transform: translate(-50%);
  width: 560px;
}
</style>

