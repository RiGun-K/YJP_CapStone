<template>
  <div>
    <button @click="$router.push({name:'storage'})">뒤로가기</button>
  </div>
  <div class="storage" >
    <h5>보관소 관리</h5>
    <div class="storage-box-name-btn">
      <label for="name">보관소</label>
      <input type="text" id="name" v-model="name" >
      <label for="zipCode">우편번호</label>
      <input type="text" id="zipCode" v-model="zipCode" >
      <label for="address">주소</label>
      <input type="text" id="address" v-model="address" >
      <label for="detailAddress">상세주소</label>
      <input type="text" id="detailAddress" v-model="detailAddress" >
      <label for="telNumber">전화번호</label>
      <input type="text" id="telNumber" v-model="telNumber" >
<!--      <label for="image">이미지</label>-->
<!--      <input type="file" id="image" v-model="image" >-->
      <div>
        <button @click="updataStorage()">저장</button>
        <button v-if="stopen == 'open'" @click="closeStorage()">보관소 정지</button>
        <button v-if="stopen == 'close'" @click="openStorage()">보관소 시작</button>
      </div>
    </div>
    <div class="storage-view">
      <table class="storage-box">
        <thead>
        <tr>
          <th>보관함</th>
          <th>상태</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(box,index) in boxList.storageBoxes" :key="index">
          <td>{{ box.storageBoxName }}</td>
          <td v-if="box.storageBoxState == 'x'">비활성화</td>
          <td v-else>활성화</td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
  <hr>
  <div class="manager" >
    <div>
      <h5>보관소 매니저 관리</h5>
      <div class="manager-add">
        <div class="mb-3" @click="mnshow()">
          <div v-if="mnopen">
            매니저 추가 ▲
            <div>
              <div>
                <label for="exampleFormControlInput1" class="form-label">매니저아이디</label>
                <input type="text" v-model="memberId" class="form-control" id="exampleFormControlInput1"
                       placeholder="매니저id">
              </div>
              <button class="storage-box-b" @click="CheckMember()">검사</button>
              <div v-if="memberIdCheck">
                <p>가능</p>
                <button @click="postManager()">추가</button>
              </div>
            </div>
          </div>
          <div v-else>
            매니저 추가 ▼
          </div>
        </div>
      </div>
    </div>
    <div>
      <table>
        <thead>
        <tr>
          <th></th>
          <th>이름</th>
          <th>아이디</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(manager, index) in managerList" :key="index">
          <td>{{ index + 1 }}</td>
          <td>{{ manager.mcode.mnick }}</td>
          <td>{{ manager.mcode.mid }}</td>
          <td><button @click="removeManager(manager.storageManagerCode)">삭제</button></td>
        </tr>
        </tbody>
      </table>
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
      boxList: {},
      name: '',
      zipCode:'',
      address:'',
      detailAddress:'',
      telNumber:'',
      image:null,
      managerList: [],
      memberId: '',
      memberIdCheck: false,
      stopen:'',
      mnopen:false,
    }
  },
  mounted() {
    this.getStorageInfo(this.$route.params.storageCode)
  },
  methods: {
    mnshow(){
      this.mnopen = !this.mnopen
    },
    getStorageInfo(storageCode) {
      axios.get('/api/storageView/' + storageCode)
          .then((res) => {
            console.log(res.data)
            this.boxList = res.data
            this.name = this.boxList.storageName
            this.zipCode = this.boxList.storageZipcode
            this.address = this.boxList.storageAddress
            this.telNumber = this.boxList.storageTel
            this.detailAddress = this.boxList.storageDetailAddress
            this.stopen = (this.boxList.storageState == '0')?'open':'close'
            this.GetManger(storageCode)
            this.memberIdCheck = false
          })
          .catch((err) => {
            console.log(err)
          })
    },
    updataStorage(){
      const data = {
        storageCode : this.boxList.storageCode,
        storageName : this.name,
        storageZipcode: this.zipCode,
        storageAddress: this.address,
        storageDetailAddress: this.detailAddress,
        storageTel: this.telNumber
      }
      axios.post('/api/updateStorage',data)
          .then((res) => {
            console.log(res.data.result)
            alert('저장되었습니다')
            this.$router.push({name:'storage'})
          })
          .catch((err) => {
            console.log(err)
          })
    },
    closeStorage(){
      for (let i = 0; i < this.boxList.storageBoxes.length; i++) {
        if (this.boxList.storageBoxes[i].storageBoxState != 'x'){
          alert('활성화 되어있는 보관함이 있습니다')
          return
        }
      }
      axios.get('/api/closeStorage/'+this.boxList.storageCode)
          .then((res) => {
            console.log(res.data.result)
            if (res.data.result == 'ok'){
              alert('폐업되었습니다')
              this.$router.push({name:'storage'})
            }else{
              alert('에러')
            }

          })
          .catch((err) => {
            console.log(err)
          })
    },
    openStorage(){
      axios.get('/api/openStorage/'+this.boxList.storageCode)
          .then((res) => {
            console.log(res.data.result)
            alert('개업되었습니다')
            this.stopen = true
            this.$router.push({name:'storage'})
          })
          .catch((err) => {
            console.log(err)
          })
    },
    GetManger(storageCode) {
      axios.get('/api/getManager/' + storageCode)
          .then((res) => {
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
    postManager() {
      let memberId = this.memberId
      let storageCode = this.boxList.storageCode
      let manager = {
        member: memberId,
        storage: storageCode
      }
      if (this.memberIdCheck) {
        axios.post('/api/postManager', manager)
            .then((res) => {
              console.log(res.data.result)
              if (res.data.result === 'ok') {
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
    removeManager(index) {
      console.log(index)
      axios.get('/api/deleteManager/'+index)
          .then(res => {
            if(res.data.result =='ok'){
              alert('제거되었습니다')
              this.GetManger(this.boxList.storageCode)
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