<template>

  <!--  관리하는 보관함 보기 -->
  <div class="storage">
    보관소 이름: {{ this.storageList.storageName }}
    <div class="storage-view">
      <div class="storage-box" v-for="(storage,index) in storageList.storageBoxes" :key="index"
           @click="modalViewChk(storage.storageBoxCode)">
        <div>
          <ul>
            <li>보관함 이름: {{ storage.storageBoxName }}</li>
            <li>보관함 상태:<p v-if="storage.storageBoxState == '0'">사용안함</p>
              <p v-else-if="storage.storageBoxState == '1' ">결제완료</p>
              <p v-else-if="storage.storageBoxState == '2' ">사용중</p>
              <p v-else-if="storage.storageBoxState == '3' ">사용중 - 장비 이동 신청</p>
              <p v-else-if="storage.storageBoxState == '4' ">사용중 - 장비 이동 신청</p>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>


  <!-- 모달-->
  <div v-if="modalView">
    <button @click="modalView = false">X</button>
    <div>
      <BoxModalDetail :boxCode="boxCode"></BoxModalDetail>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import store from "@/store"
import BoxModalDetail from "@/components/storageService/manager/BoxModalDetail.vue";

export default {
  name: "StorageDetail",
  components: {
    BoxModalDetail
  },
  data() {
    return {
      managerId: '',
      storageList: '',
      name: '',
      modalView: false,
      modal: false,
      message: '',
      boxCode: ''
    }
  },
  mounted() {
    this.managerId = store.getters.getLoginState.loginState
    if(store.getters.getLoginState.stateCode != 5){
      this.$router.push('/')
      alert('보관소 매니저만 확인이 가능합니다')
    }
    axios.get('/api/getManagerStorage/' + this.managerId)
        .then(res => {
          console.log(res.data)
          this.storageList = res.data

          console.log(this.storageList.storageName)
        })
        .catch(err => {
          console.log(err)
        })
  },
  methods: {
    modalViewChk(storage) {
      // console.log('post storageCode')
      // console.log(storage)
      this.boxCode = storage
      if (!this.modalView) {
        this.modalView = !this.modalView
      }

      // this.$router.push({name:'boxDetail', params:{storage:storage}})
    },
    inputMamager() {

    },
    openModal() {
      this.modal = true
    },
    closeModal() {
      this.modal = false
    },
    doSend() {
      if (this.message.length > 0) {
        alert(this.message)
        this.message = ''
        this.closeModal()
      } else {
        alert('메시지를 입력해주세요.')
      }
    }
  }
}
</script>

<style lang="css" scoped>
.storage-box {
  border: solid 3px #DAA520;
  border-radius: 10px;
  width: 200px;

}

.storage-view {
  border: solid 1px #2c3e50;
  display: -webkit-flex;
  display: flex;
}

.storage {
  border: solid 3px #42b983;
}


</style>