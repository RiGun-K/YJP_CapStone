<template>
  장비 이동
  현재 보관중인 보관함 위치
  이동할 보관소 or 주소 입력
  <br>
  <input type="radio" name="move-option" v-model="moveOption" value="round">장소 <br>
  <input type="radio" name="move-option" v-model="moveOption" value="storage">보관함
  <hr>
  <div v-if="moveOption=='round'"><!--장소 검색-->
    <RoundMoveBox :form="form"/>
  </div>
  <div v-if="moveOption=='storage'"><!--보관함-->
    <StorageMoveBox :form="form"/>
  </div>
</template>

<script>
import StorageMoveBox from "@/components/storageService/user/StorageMoveBox.vue";
import RoundMoveBox from "@/components/storageService/user/RoundMoveBox.vue";

export default {
  name: "MoveBox",
  components: {
    StorageMoveBox,
    RoundMoveBox
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