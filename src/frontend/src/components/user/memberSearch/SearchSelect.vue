<template>
  <img :src="backImg" class="backImg">
  <div class="bodyDiv">
    <div style="text-align: center">
      <button @click="idSearch" class="btnCommon">
        <span>아이디 찾기</span>
      </button>
      <button @click="passSearch" class="btnCommon">
        <span>비밀번호 찾기</span>
      </button>
    </div>
  </div>
</template>

<script>
import store from "@/store";

export default {
  name: "SearchSelect",
data(){
  return{
    backImg:require("@/assets/camp1.jpg")
  }
},
  methods:{
    idSearch(){
      this.$router.push('searchId')
    },
    passSearch(){
      this.$router.push('searchPass')
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
.bodyDiv{
  padding: 30px;
  padding-top: 8%;
  padding-bottom: 5%;
  background: white;
  margin-left: 25%;
  margin-right: 25%;
  margin-top: 2%;
  margin-bottom: 7%;
  width: 50%;
  height: 93%;
  z-index: 2;
  position: relative;
  border-radius: 100px;
}

.btnCommon{
  margin-left: 15px;
  padding: 0.2%;
  width: 200px;
  height: 200px;
  text-align: center;
  border: black solid 3px;
  background: white;
  color: black;
}
.btnCommon:hover{
  border: red solid 3px;
  background: black;
  color: white;
}
</style>
