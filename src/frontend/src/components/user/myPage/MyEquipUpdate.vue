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
      <button class="btnCommon" @click="updateBtn">수정</button>
      <button class="btnCommon" @click="deleteBtn">삭제</button>
      <button class="btnCommon" @click="returnBtn">취소</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "MyEquipUpdate",
  data(){
    return{
      equipName:'',
      equipKind:'',
      equipCount:1,
      kindList:[],
      backImg:require("@/assets/camp1.jpg")
    }
  },
  created() {
    axios.post("/api/searchEquip", {
      equipCode:this.$route.params.equipCode
    }).then((res)=>{
      this.equipName = res.data.memEquipmentName
      this.equipKind = res.data.kindid.kindname
      this.equipCount = res.data.memEquipmentCount
    }).catch((err)=>{
      console.log(err)
    })

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
    returnBtn(){
      this.$router.push("/myPageEquip")
    },
    deleteBtn(){
      axios.post("/api/myEquipDelete",{
        equipCode:this.$route.params.equipCode
      }).then((res)=>{
        if(res.data){
          alert("장비가 삭제되었습니다")
          this.$router.push("/myPageEquip")
        }else{
          alert("오류가 발생했습니다")
        }
      }).catch((err)=>{
        console.log(err)
      })
    },
    updateBtn(){
      axios.post("/api/myEquipUpdate",{
        equipCode:this.$route.params.equipCode,
        equipName : this.equipName,
        equipCount : this.equipCount,
        kindName : this.equipKind
      }).then((res)=>{
        if(res.data){
          alert("장비가 수정되었습니다")
          this.$router.push("/myPageEquip")
        }else{
          alert("오류가 발생했습니다")
        }
      }).catch((err)=>{
        console.log(err)
      })
    }
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
.divBody{
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
.inputDiv{
  left: 23%;
  width: 73%;
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
  width: 96%;
  text-align: right;
}
.countBtn{
  margin-left: 10px;
  border: black solid 2px;
  background: #41b883;
}
.countBtn:hover{
  margin-left: 10px;
  border: #7ea6f6 solid 2px;
  background: black;
  color: white;
}
.btnCommon{
  margin-left: 10px;
  margin-top: 1%;
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
