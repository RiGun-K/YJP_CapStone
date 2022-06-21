<template>
  <img :src="backImg" class="backImg">
    <div class="container">
      <div class="text-box">
        <br>
        <br>
        <br>
        <h1>결제가 완료 되었습니다.</h1>
      </div>
      <br>
      <br>
      <br>
      <hr>
      <br>
      <br>
      <div class="btn-box">
        <button class="payNow" @click="$router.push('/')">홈으로</button>
        <button class="payNow" @click="$router.push({name:'myBox'})">내 보관함</button>
      </div>
    </div>
</template>

<script>
export default {
  name: "StorageComplete",
  data(){
    return{
      backImg:require("@/assets/camp1.jpg")
    }
  },
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
.container{
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
  position: center;
}
.text-box{
  text-align: center;
}

.btn-box{
  position: center;
}

.border{
  background-image: ;
}

.payNow{
  position: center;
  text-align: center;
  width: 20%;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
}

h1{
  text-overflow: clip;
}
</style>