<template>
  <div>
    <div style="text-align: right">
      <button @click="$router.push({name:'InputStorageManager'})"
      style="margin-right: 8%; margin-top: 1%">추가</button>
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
