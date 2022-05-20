<template>
  <div>
    <h1>revise - 수정</h1>
    <button @click="$router.push({name:'storage'})">뒤로가기</button>
  </div>
  <div>
    <div class="storage">
      <div class="storage-box-name-btn">
        <h5 style="width: 100%;">보관소 이름:{{ name }}</h5>
      </div>
      <div>
        <h5>보관소 매니저</h5>
        <div class="manager-add">
          <div class="mb-3">
            <label for="exampleFormControlInput1" class="form-label">매니저아이디</label>
            <input type="text" v-model="memberId" class="form-control" id="exampleFormControlInput1"
                   placeholder="매니저id">
          </div>
          <button class="storage-box-b" @click="CheckMember()">CHECK</button>
          <p v-if="memberIdCheck">가능</p>
          <button v-if="memberIdCheck" @click="postManager">ADD</button>
        </div>
        <table>
          <thead>
          <tr>
            <th></th>
            <th>이름</th>
            <th>아이디</th>
            <th>직급</th>
          </tr>
          </thead>
          <tbody v-for="(manager, index) in managerList" :key="index">
          <tr>
            <td>{{index+1}}</td>
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
            <th calspan="2">보관함</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(box,index) in boxList.storageBoxes" :key="index">
            <td>{{index+1}}</td>
            <td>{{ box.storageBoxName }}</td>
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
  components: {},
  data() {
    return {
      boxList: [],
      name: '',
      managerList: [],
      memberId: '',
      memberIdCheck: false,
    }
  },
  mounted() {
    console.log("111")
    console.log(this.$route.params.storageCode)
    this.getStorageInfo(this.$route.params.storageCode)
  },
  methods: {
    getStorageInfo(storageCode) {
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
    },
    GetManger(storageCode) {
      axios.get('/api/getManager/' + storageCode)
          .then((res) => {
            console.log('res.data')
            console.log(res.data)
            this.managerList = res.data
          })
          .catch((err) => {
            console.log(err)
          })
    },
    clearInput() {
      this.memberId = ''
    },
    CheckMember() {
      if (!this.memberId) {
        alert('아이디를 입력하세요')
      } else {
        axios.get('api/checkManager/' + this.memberId)
            .then((res) => {
              console.log(res)
              if (res.data.result == 'ok') {
                console.log('가능')
                alert('가능합니다')
                this.memberIdCheck = true

              } else if (res.data.result == 'overlap') {
                console.log('불가능')
                alert('이미 있습니다')
                this.memberIdCheck = false
              } else {
                console.log('없음')
                alert('입력하신 아이디가 없습니다')
                this.memberIdCheck = false
              }
            })
            .catch((error) => {
              console.log(error)
            })
      }
    },
    postManager() {
      let memberId = this.memberId
      let storageCode = this.boxList.storageCode
      console.log('storageCode')
      console.log(storageCode)
      let manager = {
        member: memberId,
        storage: storageCode
      }
      if (this.memberIdCheck) {
        axios.post('api/postManager', manager)
            .then((res) => {
              console.log(res.data.result)
              if (res.data.result === 'ok') {
                console.log('생성')
                alert('추가되었습니다')
                this.GetManger(storageCode)
                this.clearInput()
                this.memberIdCheck = false
              } else {
                alert('추가 되지 않음')
              }
            }).catch((err) => {
          console.log(err)
        })
      }
    },
  }

}
</script>

<style scoped>

</style>