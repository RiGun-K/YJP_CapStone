<template>
  <div class="divBody">
    <div class="inputDiv">
      <label class="labelTitle">장비명 </label>
      <input type="text" class="inputBody" v-model="equipName">
    </div>
    <div class="inputDiv">
      <label class="labelTitle">장비분류</label>
      <select class="inputBody" v-model="equipKind">
        <option v-for="obj in kindList">{{obj.kindname}}</option>
      </select>
    </div>
    <div class="inputDiv">
      <label class="labelTitle">장비수량</label>
      <input type="text" class="inputBody" style="width: 50px;text-align: center" v-model="equipcount" readonly>
      <button class="countBtn" @click="addCount">증가</button>
      <button class="countBtn" @click="removeCount">감소</button>
    </div>
    <button class="addBtn" @click="addEquip">추가</button>
  </div>
</template>

<script>
import axios from "axios";
import store from "@/store";

export default {
  name: "AddEquip",
  data(){
    return{
      equipName:'',
      equipKind:'',
      equipcount:1,
      kindList:''
    }
  },
  methods:{
    addCount(){
      this.equipcount++
    },
    removeCount(){
      this.equipcount--
      if (this.equipcount <= 0){
        this.equipcount = 1
      }
    },
    chKindId(index){
      this.equipKind = index
      console.log(this.equipKind)
    },
    addEquip(){
      axios.post("/api/addMyEquip",{
        MID:store.getters.getLoginState.mcode,
        KindName:this.equipKind,
        NAME:this.equipName,
        Count:this.equipcount
      }).then((res)=>{
        console.log(res.data)
      }).catch((err)=>{
        console.log(err)
      })
    }
  },
  created() {
    axios.get("/api/allKindGet")
    .then((res)=>{
      this.kindList = res.data
      this.equipKind = this.kindList[0].kindname
    }).catch((err)=>{
      console.log(err)
    })
  }
}
</script>

<style scoped>
.divBody{
  margin-left: 2%;
  margin-right: 2%;
  margin-top: 1%;
  width: 96%;
  text-align: center;
}
.inputDiv{
  left: 35%;
  width: 65%;
  text-align: left;
  position: relative;
}
.labelTitle{
  left: 10%;
  margin-top: 1%;
  margin-right: 2%;
  width: 10%;
}
.inputBody{
}
.addBtn{
  margin-top: 1%;
  right: 35%;
  position: absolute;
}
.countBtn{
  margin-left: 1%;
  border: 0px;
  background: #41b883;
}
</style>
