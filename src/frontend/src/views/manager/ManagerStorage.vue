<template>
  <!--  관리하는 보관함 보기 -->
  <div class="storage">
    <div style="width: 100%">
      <h3 style="margin: 10px">보관소 이름: {{ this.storageList.storageName }}</h3>
    </div>
    <div class="storage-view">
      <div class="storage-box" v-for="(box) in storageList.storageBoxes" :key="index"
           @click="modalViewChk(box)">
        <div>
          <ul>
            <li>보관함 : {{ box.storageBoxName }}</li>
            <li>상태 : {{stateString(box.storageBoxState)}}</li>
          </ul>
        </div>
      </div>
    </div>
  </div>

  <!-- 모달-->
  <div v-if="modalView">
    <div style="width: 100%; text-align: right">
      <button @click="modalView = false" class="cancleBtn">X</button>
    </div>
    <div v-if="useChk == 'not'">
      <NotUsingInfo :boxCode="boxCode" @updata="getBackData()" />
    </div>
    <div v-else-if="useChk == 'use'">
      <UsingBoxInfo :boxCode="boxCode" @updata="getBackData()"/>
    </div>
    <div>

    </div>
  </div>
</template>

<script>
import axios from "axios";
import store from "@/store"
import NotUsingInfo from "@/components/storageService/manager/NotUsingInfo.vue";
import UsingBoxInfo from "@/components/storageService/manager/UsingBoxInfo.vue";

export default {
  name: "ManagerStorage",
  components: {
    NotUsingInfo,
    UsingBoxInfo
  },
  data() {
    return {
      managerId: '',
      storageList: '',
      name: '',
      modalView: false,
      modal: false,
      message: '',
      boxCode: '',
      boxArray: [],
      useChk:'',
    }
  },
  mounted() {
    this.managerId = store.getters.getLoginState.loginState
    if(store.getters.getLoginState.stateCode != 5){
      this.$router.push('/')
      alert('보관소 매니저만 확인이 가능합니다')
    }else{
      this.getBackData()
    }
  },
  methods: {
    stateString(index){
      switch (index){
        case '0':
          return '사용안함'
        case '1':
          return '결제완료'
        case '2':
          return '사용중'
        case '3':case '4':
          return '사용중 - 보관소 이동 신청'
        case '5':
          return '사용중 - 수리신청'
        case '6':
          return '사용중 - 해지'
        case '7':
          return '배송 신청'
        case 'x':
          return '비활성화'
      }
    },
    getBackData(){
      axios.get('/api/getManagerStorage/' + this.managerId)
          .then(res => {
            this.storageList = res.data

          })
          .catch(err => {
            console.log(err)
          })
    },
    modalViewChk(storage) {
      this.boxCode = storage.storageBoxCode
      if (storage.storageBoxState == '0' || storage.storageBoxState == 'x' ){
        this.useChk = 'not'
      }else{
        this.useChk = 'use'
      }
      if (!this.modalView) {
        this.modalView = !this.modalView
      }
    },
  }
}
</script>

<style lang="css" scoped>
.storage-box {
  margin: 10px;
  border: solid 3px #DAA520;
  border-radius: 10px;
  width: 15%;
}

.storage-view {
  display: -webkit-flex;
  display: flex;
  width: 100%;
}

.storage {
  margin-left: 5%;
  margin-right: 5%;
  border: solid 3px #42b983;
  width: 90%;
}
.cancleBtn{
  margin-left: 5%;
  margin-right: 5%;
  margin-top: 1%;
}
.cancleBtn:hover{
  background: black;
  color: white;
}
</style>
