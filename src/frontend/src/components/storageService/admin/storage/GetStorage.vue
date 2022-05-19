<template>
  <button @click="$router.push('/storageAdmin')"> 목록</button>
  <div class="renewal-box">
    <h3>보관소 리스트 페이지</h3>
    <div class="card"  style="width: 70%; margin-bottom: 5%; font-weight: bolder; margin-left: 7%">
      <div class="card-body">
        <div v-for="(storage,index) in storageList" :key="index" @click="GetStorageDetail(storage.storageCode)">
          <div>
            <div class="card" style="display: flex; width: 85%; margin-top: 4%;margin-left: 7%; margin-bottom: 3%">
              이름:{{ storage.storageName }}
              <br>
              주소:{{ storage.storageAddress }}
            </div>
          </div>
        </div>
      </div>

    </div>
    <div v-if="check">
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
            <tr  v-for="(box,index) in boxList.storageBoxes" :key="index">
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