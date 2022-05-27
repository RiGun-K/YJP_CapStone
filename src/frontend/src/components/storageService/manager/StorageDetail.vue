<template>
  <!--  관리하는 보관함 보기 -->
  <div class="storage">
    <div style="width: 100%">
      <h3 style="margin: 10px">보관소 이름: {{ this.storageList.storageName }}</h3>
    </div>
    <div class="storage-view">
      <div class="storage-box" v-for="(box) in storageList.storageBoxes" :key="index"
           @click="modalViewChk(box.storageBoxCode)">
        <div>
          <ul>
            <li>보관함 이름: {{ box.storageBoxName }}</li>
            <li>보관함 상태:<p v-if="box.storageBoxState == '0'">사용안함</p>
              <p v-else-if="box.storageBoxState == '1' ">결제완료</p>
              <p v-else-if="box.storageBoxState == '2' ">사용중</p>
              <p v-else-if="box.storageBoxState == '3' ">사용중 - 장비 이동 신청</p>
              <p v-else-if="box.storageBoxState == '4' ">사용중 - 장비 이동 신청</p>
              <p v-else-if="box.storageBoxState == '5' ">사용중 - 수리신청</p>
              <p v-else-if="box.storageBoxState == '6' ">사용중</p>
              <p v-else-if="box.storageBoxState == '7' ">사용중 - 이동신청</p>
              <p v-else-if="box.storageBoxState == 'x' ">비활성화</p>
            </li>
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
    <div>
      <BoxModalDetail :boxCode="boxCode" @updata="getBackData()"></BoxModalDetail>
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
      boxCode: '',
      boxArray: [],
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
      this.boxCode = storage
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
