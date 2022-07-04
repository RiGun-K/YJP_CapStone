<template>
  <div class="container">
    <div class="container-box">
      <div class="check-list">
        <input type="radio" name="move-option" v-model="moveOption" value="home">집 <br>
        <input type="radio" name="move-option" v-model="moveOption" value="round">장소 <br>
        <input type="radio" name="move-option" v-model="moveOption" value="storage">보관소
      </div>
      <hr>
      <div>
        <div class="container-box" v-if="moveOption=='home'">
          <HomeMoveBox :useBoxCode="useBoxCode" class="option"/>
        </div>
        <div class="container-box" v-if="moveOption=='round'">
          <RoundMoveBox :useBoxCode="useBoxCode" class="option"/>
        </div>
        <div class="container-box" v-if="moveOption=='storage'">
          <StorageMoveBox :useBoxCode="useBoxCode" class="option"/>
        </div>
      </div>
    </div>
    <br>
    <br>
    <hr>
    <div>
      <button @click="$router.push({name:'myBox'})">취소</button>
    </div>
  </div>
</template>

<script>
import StorageMoveBox from "@/components/storageService/user/StorageMoveBox.vue";
import RoundMoveBox from "@/components/storageService/user/RoundMoveBox.vue";
import HomeMoveBox from "@/components/storageService/user/HomeMoveBox.vue";

export default {
  name: "MoveBox",
  components: {
    StorageMoveBox,
    RoundMoveBox,
    HomeMoveBox
  },
  watch:{
    moveOption:function (){
      this.clear()
    },
  },
  data() {
    return {
      moveOption: '',
      useBoxCode:'',
    }
  },
  mounted() {
    this.useBoxCode = this.$route.params.useBoxCode
  },
  methods:{
    clear(){
      this.$store.commit('clearMoveBoxInfo')
    }
  }
}
</script>

<style scoped>
.container{
  position: center;
  width: 95%;
}

.container-box{

}

.check-list{
  width: 20%;
}

.option{
  height: 100%;
  width: 100%;
}

button{
  margin-bottom: 2%;
  text-align: center;
  width: 12%;
  padding: 1%;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
}
</style>