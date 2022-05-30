<template>
  <div style="margin-right: 8%; margin-left: 8%; margin-top: 1%;width: 84%">
    <h3>보관소 매니저 관리</h3>
    <table>
      <thead>
        <tr>
          <th>
            <select v-model="storagePick">
              <option value="전체">전체</option>
              <option v-for="pick in pickList" :value="pick.code">{{ pick.name }}</option>
            </select>
          </th>
          <th>보관소명</th>
          <th>관리자 이름</th>
          <th>수정/변경</th>
        </tr>
      </thead>
      <tbody v-if="storagePick == '전체'" >
        <tr v-for="(manager,index) in storageManagerList" :key="index">
          <td>{{ index + 1 }}.</td>
          <td>{{ manager.storageCode.storageName }}</td>
          <td>{{ manager.mcode.mnick }}</td>
          <td>
            <button @click="edit(manager)">수정</button>
            <button @click="remove(manager.storageManagerCode)">삭제</button>
          </td>
        </tr>
      </tbody>
      <tbody v-else>
        <tr v-for="(manager,index) in managerList">
          <td>{{ index + 1 }}.</td>
          <td>{{ manager.storageCode.storageName }}</td>
          <td>{{ manager.mcode.mnick }}</td>
          <td>
            <button>수정</button>
            <button @click="remove(manager.storageManagerCode)">삭제</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from "axios";
import store from "@/store";

export default {
  name: "GetStorageManagerPage",
  data() {
    return {
      storageManagerList: [],
      storagePick: '전체',
      pickList: [],
      managerList: []
    }
  },
  mounted() {
    this.GetStorageManager()
    if (store.getters.getLoginState.stateCode != 5) {
      this.$router.push('/')
      alert('보관소 매니저만 확인이 가능합니다')
    }
  },
  watch: {
    storagePick: function () {
      if (this.storagePick != "전체") {
        this.pickManager(this.storagePick)
      }
    }
  },
  methods: {
    GetStorageManager() {
      axios.get('/api/getStorageManger')
          .then((res) => {
            console.log(res.data)
            this.storageManagerList = res.data
            this.pickList = []
            for (let i = 0; i < this.storageManagerList.length; i++) {
              for (let j = 0; j < this.pickList.length; j++) {
                if(this.storageManagerList[i].storageCode.storageCode != this.pickList[j].storageCode){
                  this.pickList.push({
                    code: this.storageManagerList[i].storageCode.storageCode,
                    name: this.storageManagerList[i].storageCode.storageName
                  })
                }
              }
            }
            console.log(this.pickList)
          })
          .catch((error) => {
            console.log(error)
          })
    },
    pickManager(storageCode) {
      axios.get('/api/stGetManager/' + storageCode)
          .then(res => {
            console.log(res.data)
            this.managerList = res.data
          })
          .catch(err => {
            console.log(err)
          })
    },
    edit(index) {

    },
    remove(index) {
      console.log(index)
      axios.get('/api/deleteManager/'+index)
          .then(res => {
            if(res.data.result =='ok'){
              alert('제거되었습니다')
              this.GetStorageManager()
            }else{
             alert('에러')
            }
          })
          .catch(err => {
            console.log(err)
          })
    },

  }
}
</script>

<style scoped>
</style>
