<template>
  <div class="allBody">
    <div v-if="get">
      <GetStorage></GetStorage>
      <button class="storage-back-btn" style="margin-top: -120%; margin-left: 80%; " @click="inputStorage">추가</button>
    </div>
    <div v-if="input">
      <InputStorage></InputStorage>
    </div>
  </div>
</template>

<script>
import InputStorage from '@/components/storageService/admin/storage/InputStorage.vue'
import GetStorage from "@/components/storageService/admin/storage/GetStorage";
import store from "@/store";

export default {

  name: "StorageView",
  components: {
    InputStorage,
    GetStorage
  },
  mounted() {
    if (store.getters.getLoginState.stateCode != 5) {
      this.$router.push('/')
      alert('보관소 매니저만 확인이 가능합니다')
    }
  },
  data() {
    return {
      get: true,
      input: false
    }
  },
  methods: {
    getStorage() {
      this.get = true
      this.input = false
    },
    inputStorage() {
      this.input = true
      this.get = false
    }
  }

}
</script>

<style scoped>

.allBody{
  width: 50%;
  margin-left: 25%;
  margin-right: 25%;
  margin-top: 1%;
}

.storage-back-btn{
  margin-left: 2%;
  margin-top: 2%;
  text-align: center;
  width: 100px;
  padding: 0.5%;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
}
.storage-back-btn:hover{
  color: white;
  background-color: #b2e2fd;
}

</style>
