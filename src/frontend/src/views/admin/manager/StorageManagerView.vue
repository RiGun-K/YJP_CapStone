<!-- storage admin page(manager)-->
<template>
  <div>
    <div>
      <GetStorageManagerPage></GetStorageManagerPage>
    </div>
  </div>
</template>

<script>
import GetStorageManagerPage from "@/components/storageService/admin/manager/GetStorageManagerPage.vue";

export default {
  name: "StorageManagerView",
  components:{
    GetStorageManagerPage
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
