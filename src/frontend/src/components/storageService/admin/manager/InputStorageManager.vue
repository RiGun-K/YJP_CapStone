<template>
  <div style="margin-left: 5%; margin-right: 5%; margin-top: 1%;width: 90%;">
    <button @click="$router.push({name:'manager'})">뒤로가기</button>
  </div>

  <div class="manager-add">
    <h5>보관소 매니저</h5>
    <div>
      <div>
        <labe for="storage-search">보관소</labe>
        <input id="storage-search" type="text" v-model="searchStorage"
               @keyup.enter="storageSearch()" placeholder="보관소명"
               style="margin: 1%;">
        <button @click="storageSearch()">검색</button>
      </div>
      <div>
        <table>
          <thead>
          <tr>
            <th>보관소번호</th>
            <th>지역</th>
            <th>보관소명</th>
          </tr>
          </thead>
          <tbody>
            <tr v-for="(storage,index) in storageList">
              <td>{{ index + 1 }}</td>
              <td>{{ storage.storageAddress }}</td>
              <td>{{ storage.storageName }}</td>
              <td>
                <input type="checkbox" name="aaa" :id="storage.storageCode"
                       @click="checkStorage(storage.storageCode)">
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div>
      <div class="managerBottom">
        <label for="exampleFormControlInput1" class="form-label" style="width: 10%">매니저아이디</label>
        <input type="text" v-model="memberId" class="form-control" id="exampleFormControlInput1"
               placeholder="매니저id" style="width: 20%;display: inline">
        <button class="storage-box-b" @click="CheckMember()"
                style="width: 60px; margin-left: 10px">확인</button>
        <p v-if="memberIdCheck" style="margin-right: 10px;margin-left: 10px;display: inline">가능</p>
        <button v-if="memberIdCheck" @click="postManager" style="width: 60px;">추가</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import store from "@/store";

export default {
  name: "InputStorageManager",
  data() {
    return {
      managerList: [],
      memberId: '',
      memberIdCheck: false,
      searchStorage: '',
      storageList: [],
      searchList: [],
      searchStorageList: [],
      storage: {},
      storageCode: '',
    }
  },
  mounted() {
    this.allroundsearch()
    if (store.getters.getLoginState.stateCode != 5) {
      this.$router.push('/')
      alert('보관소 매니저만 확인이 가능합니다')
    }
  },
  methods: {
    allroundsearch() {
      axios.get('/api/getStorage')
          .then((res) => {
            this.storageList = res.data
            this.searchList = res.data
          })
          .catch((error) => {
            console.log(error)
          })
    },
    storageSearch() {
      this.searchStorageList = []
      if (this.searchStorage != '') {
        for (let i = 0; i < this.searchList.length; i++) {
          if (this.searchList[i].storageName.includes(this.searchStorage)) {
            this.searchStorageList.push(this.searchList[i])
          }
        }
        if (this.searchStorageList.length < 1) {
          alert('검색하신 보관소은 없습니다')
          return
        }
        this.storageList = this.searchStorageList
      } else {
        this.search()
      }
      this.allMarker()
    },
    CheckMember() {
      if (!this.memberId) {
        alert('아이디를 입력하세요')
      } else {
        axios.get('/api/checkManager/' + this.memberId)
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
    checkStorage(storageCode) {
      this.storageCode = storageCode
      document.getElementsByName("aaa")
          .forEach(el => {
        el.checked = false
      });
      document.getElementById(storageCode).checked = true;
    },
    postManager() {
      let manager = {
        member: this.memberId,
        storage: this.storageCode
      }
      if (this.memberIdCheck) {
        axios.post('/api/postManager', manager)
            .then((res) => {
              console.log(res.data.result)
              if (res.data.result === 'ok') {
                console.log('생성')
                alert('추가되었습니다')
                this.clearInput()
                this.memberIdCheck = false
                this.$router.push({name:'manager'})
              } else {
                alert('추가 되지 않음')
              }
            }).catch((err) => {
          console.log(err)
        })
      }
    },
    clearInput() {
      this.memberId = ''
    },
  },
}
</script>

<style scoped>
th,td{
  width: 25%;
}
.manager-add{
  margin-left: 5%;
  margin-right: 5%;
  margin-top: 1%;
  width: 90%;
}
.managerBottom{
  margin-top: 1%;
}
</style>
