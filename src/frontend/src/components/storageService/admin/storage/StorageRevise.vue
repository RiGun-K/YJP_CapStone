<template>
<div>
  <h1>revise - 수정</h1>
</div>
  <div>
    <div class="storage">
      <div class="storage-box-name-btn">
        <h5 style="width: 100%;">보관소 이름:{{ name }}</h5>
        <button class="storage-close" @click="closeDetail">닫기</button>
      </div>
      <div>
        <h5>보관소 매니저</h5>
        <p>추가</p>
        <div class="manager-add">
          <div class="mb-3">
            <label for="exampleFormControlInput1" class="form-label">매니저아이디</label>
            <input type="text" v-model="memberId" class="form-control" id="exampleFormControlInput1" placeholder="매니저id">
          </div>
          <button class="storage-box-b" @click="CheckMember()">CHECK</button>
          <p v-if="memberIdCheck">가능</p>
          <button v-if="memberIdCheck" @click="postManager">ADD</button>
        </div>
        <table>
          <thead>
          <tr>
            <td>이름</td>
            <td>아이디</td>
            <td>직급</td>
          </tr>
          </thead>
          <tbody v-for="(manager, index) in managerList" :key="index">
          <tr>
            <td>{{ manager.mcode.mnick }}</td>
            <td>{{ manager.mcode.mid }}</td>
            <td>매니저</td>
          </tr>
          </tbody>
        </table>
      </div>

      <div class="storage-view">
        <table class="storage-box">
          <thead>
          <tr>
            <th>보관함</th>
            <th>상태</th>
            <th>수정</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(box,index) in boxList.storageBoxes" :key="index">
            <td>{{ box.storageBoxName }}</td>
            <td><p v-if="box.storageBoxState == '0'">사용안함</p>
              <p v-else-if="box.storageBoxState == '1' ">사용중</p></td>
            <td><button>수정</button></td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>

import axios from "axios";

export default {
  name: "StorageRevise",
  components: {

  },
  data(){
    return{
      boxList: [],
      name: '',
    }
  },
  mounted() {
    console.log("111")
    console.log(this.$route.params.storageCode)
    this.getStorageInfo(this.$route.params.storageCode)
  },
  methods:{
    getStorageInfo(storageCode){
      axios.get('/api/storageView/' + storageCode)
          .then((resp) => {
            this.boxList = resp.data
            console.log('resp.data')
            console.log(resp.data)
            let storageName = this.boxList.storageName
            this.name = storageName
            this.GetManger(storageCode)
            this.memberIdCheck = false
          })
          .catch((err) => {
            console.log(err)
          })
    }
  }

}
</script>

<style scoped>

</style>