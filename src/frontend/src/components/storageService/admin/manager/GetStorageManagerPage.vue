<template>
  <h3 style="margin-top: 5%; margin-left: 5%;">리스트 컴포넌트</h3>
  <div class="manager-get">
    <div v-for="(manager, index) in storageManagerList" :key="index" style="margin-top: 3%">
      <div class="card" style="width: 25%; font-weight: bolder; margin-left: 7%">
        <div class="card-body">
          보관소: {{ manager.storageCode.storageName }}
        </div>
      </div>
      <div class="card" style="display: flex; width: 25%; margin-left: 7%; margin-bottom: 3%">
        <div class="card-body">
          매니저이름: {{ manager.mcode.mnick }}
        </div>
        <div class="card-body">
          매니저아이디: {{ manager.mcode.mid }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "GetStorageManagerPage",
    data(){
      return {
        storageManagerList: []
      }
    },
    mounted(){
      this.GetStorageManager()
    },
    methods:{
      GetStorageManager()
      {
        axios.get('/api/getStorageManger')
            .then((res) => {
              console.log(res)
              this.storageManagerList = res.data

            })
            .catch((error) => {
              console.log(error)
            })
      }
    }
}
</script>

<style scoped>
</style>
