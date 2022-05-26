<template>
  <button @click="$router.push({name:'manager'})">뒤로가기</button>

  <h5>보관소 매니저</h5>
  <div class="manager-add">
    <div>
      <div>
        <labe for="storage-search">보관소</labe>
        <input id="storage-search" type="text" v-model="searchStorage" @keyup.enter="storageSearch()" placeholder="보관소명">
        <button @click="storageSearch()">검색</button>
      </div>
      <div>
        <table>
          <thead>
          <tr>
            <th calspan="2">지역</th>
            <th calspan="2">보관소명</th>
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
      <div class="mb-3">
        <label for="exampleFormControlInput1" class="form-label">매니저아이디</label>
        <input type="text" v-model="memberId" class="form-control" id="exampleFormControlInput1"
               placeholder="매니저id">
      </div>
      <button class="storage-box-b" @click="CheckMember()">확인</button>
      <p v-if="memberIdCheck">가능</p>
    </div>

    <div>
      <button v-if="memberIdCheck" @click="postManager">추가</button>
    </div>

  </div>
</template>

<script>
import axios from "axios";

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

</style>