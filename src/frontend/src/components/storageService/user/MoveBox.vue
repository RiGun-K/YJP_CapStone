<template>
  <input type="radio" name="move-option" v-model="moveOption" value="home">집 <br>
  <input type="radio" name="move-option" v-model="moveOption" value="round">장소 <br>
  <input type="radio" name="move-option" v-model="moveOption" value="storage">보관소
  <hr>
  <div v-if="moveOption=='home'">
    <HomeMoveBox :useBoxCode="useBoxCode"/>
  </div>
  <div v-if="moveOption=='round'">
    <RoundMoveBox :useBoxCode="useBoxCode"/>
  </div>
  <div v-if="moveOption=='storage'">
    <StorageMoveBox :useBoxCode="useBoxCode"/>
  </div>

  <router-view />
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

</style>