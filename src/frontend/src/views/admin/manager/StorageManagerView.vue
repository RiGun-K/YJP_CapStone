<template>
  <div>
    <div>
      <button @click="$router.push({name:'InputStorageManager'})">추가</button>
    </div>
    <div>
      <GetStorageManager></GetStorageManager>
    </div>
  </div>
</template>

<script>
import GetStorageManager from "@/components/storageService/admin/manager/GetStorageManager.vue";

export default {
  name: "StorageManagerView",
  components:{
    GetStorageManager
  }
}
</script>

<style scoped>
.allBody{
  width: 90%;
  margin-left: 5%;
  margin-right: 5%;
  align-content: center;
}
</style>
