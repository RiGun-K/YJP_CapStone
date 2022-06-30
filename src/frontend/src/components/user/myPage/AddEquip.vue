<template>
  <img :src="backImg" class="backImg">
  <div class="divBody">
    <div class="inputDiv">
      <label class="labelTitle">장비명 </label>
      <input type="text" class="inputBody" v-model="equipName">
    </div>
    <div class="inputDiv">
      <label class="labelTitle">장비분류</label>
      <select class="inputBody" v-model="equipKind">
        <option v-for="obj in kindList" :key="obj.kindid">{{obj.kindname}}</option>
      </select>
    </div>
    <div class="inputDiv">
      <label class="labelTitle">장비수량</label>
      <input type="text" class="inputBody" style="width: 50px;text-align: center" v-model="equipCount" readonly>
      <button class="countBtn" @click="addCount">증가</button>
      <button class="countBtn" @click="removeCount">감소</button>
    </div>
    <div class="btnDiv">
      <button class="addBtn" @click="addEquip">추가</button>
      <button class="addBtn" @click="returnEquip">취소</button>
    </div>
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
      equipCount:1,
      kindList:[],
      backImg:require("@/assets/camp1.jpg")
    }
  },
  methods:{
    addCount(){
      this.equipCount++
    },
    removeCount(){
      this.equipCount--
      if (this.equipCount <= 0){
        this.equipCount = 1
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
        Count:this.equipCount
      }).then((res)=>{
        if(res.data){
          this.$router.push("/myPageEquip")
        }
      }).catch((err)=>{
        console.log(err)
      })
    },
    returnEquip(){
      this.$router.push("/myPageEquip")
    }
  },
  created() {
    axios.get("/api/allKindGet")
    .then((res)=>{
      for(var x = 0; x < res.data.length;x++){
        if(res.data[x].parentkind != null){
          this.kindList.push(res.data[x])
        }
      }
      this.equipKind = this.kindList[0].kindname
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
.divBody{
  border-radius: 100px;
  padding: 30px;
  padding-top: 5%;
  position: absolute;
  margin-top: 2%;
  margin-bottom: 5%;
  margin-left: 15%;
  margin-right: 15%;
  width: 70%;
  height: 143%;
  background: white;
  z-index: 2;
}
.inputDiv{
  left: 25%;
  width: 75%;
  text-align: left;
  position: relative;
}
.labelTitle{
  left: 10%;
  margin-top: 25px;
  margin-right: 2%;
  width: 15%;
}
.inputBody{
}
.btnDiv{
  width: 100%;
  text-align: right;
}
.addBtn{
  margin-right: 10px;
  margin-top: 1%;
}
.countBtn{
  margin-left: 1%;
  border: 0px;
  background: #41b883;
}
</style>
