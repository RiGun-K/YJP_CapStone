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
      check: false,
      boxList: [],
      name: '',
      managerList: [],
      memberId: '',
      errorCheck: false,
      memberIdCheck: false
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
              } else{
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
      let manager ={
        member : memberId,
        storage : storageCode
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
    detailCheck() {
      if (!this.check) {
        this.check = !this.check
      }
    },
    closeDetail() {
      if (this.check) {
        this.check = !this.check
      }
    }
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