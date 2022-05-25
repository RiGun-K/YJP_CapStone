<template>
  <div class="divBody">
    <div class="divTitle">
      <h1 style="color: black">보관소 매니저</h1>
    </div>
    <div class="divAdd">
        <label for="exampleFormControlInput1" class="labelTitle">매니저아이디</label>
        <input type="text" v-model="memberId" id="exampleFormControlInput1"
               placeholder="매니저id" class="inputSearch">
        <button @click="CheckMember()" class="btnCheck">CHECK</button>
        <p v-if="memberIdCheck" style="margin-top: 1%">상태확인 : 사용가능</p>
    </div>
    <div class="divSearch">
      <label class="labelTitle">보관소</label>
      <input id="storage-search" type="text" v-model="searchStorage"
             @keyup.enter="storageSearch()" placeholder="보관소명"
             class="inputSearch">
      <button @click="storageSearch()" class="btnSearch">검색</button>
    </div>
    <div class="divManager">
        <table>
          <thead>
            <tr>
              <th>지역</th>
              <th>보관소명</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(storage,index) in storageList">
              <td>{{ storage.storageAddress }}</td>
              <td>{{ storage.storageName }}</td>
              <td>
                <button @click="checkStorage(storage.storageCode)">선택</button>
              </td>
            </tr>
          </tbody>
        </table>
    </div>
      <button v-if="memberIdCheck" @click="postManager">ADD</button>
    <div class="divReturn">
      <button @click="$router.push({name:'manager'})">뒤로가기</button>
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

    },
    postManager() {
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
    clearInput() {
      this.memberId = ''
    },
  },
}
</script>

<style scoped>
.divBody{
  margin-left: 25%;
  margin-right: 25%;
  width: 50%;
}
.divTitle, .divAdd, .divSearch, .divManager{
  margin-top: 1.5%;
}
.btnCheck, .btnSearch{
  margin-left: 10px;
  width: 100px;
}
.btnCheck:hover, .btnSearch:hover{
  background: black;
  color: white;
}
.inputSearch{
  margin-left: 10px;
}
table{
  width: 100%;
}
th, td{
  width: 30%;
}
.divReturn{
  margin-top: 1%;
  text-align: center;
}
.labelTitle{
  width: 100px;
}
</style>
