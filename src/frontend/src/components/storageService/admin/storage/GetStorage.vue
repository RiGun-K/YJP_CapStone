<template>
  <div class="renewal-box">
    <h3>보관소 리스트 페이지</h3>
    <div class="card"  style="width: 70%; margin-bottom: 5%; font-weight: bolder; margin-left: 7%">
      <table>
        <thead>
          <tr>
            <th>번호</th>
            <th>지역</th>
            <th colspan="2">보관소명</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(storage,index) in storageList" :key="index" >
            <td>{{ index+1 }}</td>
            <td>{{ storage.storageAddress }}</td>
            <td>{{ storage.storageName }}</td>
            <td><button @click="GetStorageDetail(storage.storageCode)">수정</button></td>
          </tr>
        </tbody>
      </table>
    </div>

  </div>

</template>

<script>
import axios from "axios";

export default {
  name: "GetStorage",
  components: {},
  mounted() {
    this.GetStorage()
  },
  data() {
    return {
      storageList: [],
      boxList: [],
      name: '',
    }
  },
  methods: {
    GetStorage() {
      axios.get('/api/getStorage')
          .then((res) => {
            this.storageList = res.data
          })
          .catch((error) => {
            console.log(error)
          })
    },
    GetStorageDetail(storageCode) {

      console.log('storageCode')
      console.log(storageCode)
      this.$router.push({name:'StorageRevise', params:{storageCode:storageCode}})
      this.detailCheck()
      axios.get('/api/storageView/' + storageCode)
          .then((resp) => {
            this.boxList = resp.data
            let storageName = this.boxList.storageName
            this.name = storageName
            this.GetManger(storageCode)
            this.memberIdCheck = false
          })
          .catch((err) => {
            console.log(err)
          })
    },

  }
}

</script>

<style scoped>
.storage-box {
  border: solid 3px #00a3de;
  border-radius: 10px;
  width: 200px;

}
.storage-view {
  display: -webkit-flex;
  display: flex;
}
.renewal-box{
  width: 70%;
}
.storage {
  padding-top: 1%;
  border: solid 3px #000a69;
  width: 80%;
  justify-content: center;
  align-items: center;
  margin-left: 7%;
  margin-top: 2%;
  padding-bottom: 1%;
}

.renewal-box h3{
  margin-top: 3%;
  margin-left: 7%;
  margin-bottom: 3%;
}

.form-control{
  width: 20%;
}

.storage-box-b{
  margin-left: 1%;
  margin-bottom: 1%;
  text-align: center;
  width: 10%;
  padding: 0.5%;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
}
.storage-box-b:hover {
  color: white;
  background-color: #b2e2fd;
}
.form-label{
  font-weight: normal;
  font-size: large;
}
.card{
  margin-top: -1%;
}
.storage-box-name-btn{
  display: flex;
}
.storage-close{
  margin-left: 50%;
  margin-bottom: 1%;
  text-align: center;
  width: 40%;
  padding: 0.5%;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
}
.storage-close:hover {
  color: white;
  background-color: #b2e2fd;
}

</style>
