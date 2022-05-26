<template>
  <input type="radio" name="move-option" v-model="moveOption" value="home">집 <br>
  <input type="radio" name="move-option" v-model="moveOption" value="round">장소 <br>
  <input type="radio" name="move-option" v-model="moveOption" value="storage">보관소
  <hr>
  <div v-if="moveOption=='home'">
    <HomeMoveBox :form="form"/>
  </div>
  <div v-if="moveOption=='round'">
    <RoundMoveBox :form="form"/>
  </div>
  <div v-if="moveOption=='storage'">
    <StorageMoveBox :form="form"/>
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
  data() {
    return {
      moveOption: '',
      form:undefined||{}
    }
  },
  mounted() {
    this.form.userId = this.$route.params.userId
    this.form.storageName = this.$route.params.storageName
    this.form.boxName = this.$route.params.boxName
    this.form.useBoxCode = this.$route.params.useBoxCode
    this.form.boxCode = this.$route.params.boxCode
  }
}
</script>

<style scoped>

</style>