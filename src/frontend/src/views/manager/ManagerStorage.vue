<template>
  <!--  관리하는 보관함 보기 -->
  <div class="container">
    <br><br><br>
    <div>
      <h3>{{ this.storageList.storageName }} 보관소</h3>
    </div>
    <div class="storage-view">
      <div class="scoll-list">
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
    <div v-if="modalView" class="right-box">
      <div style="width: 100%; text-align: right">
        <button @click="modalView = false" class="cancleBtn">X</button>
      </div>

      <div>
        <NotUsingInfo :boxCode="boxCode" @updata="getBackData()" v-if="useChk == 'not'"/>
        <UsingBoxInfo :boxCode="boxCode" @updata="getBackData()" v-else-if="useChk == 'use'"/>

      </div>

    </div>

    <br><br><br><br>

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
.container {
  position: center;
  height: 100%;
  width: 95%;
}
.storage-box {
  margin: 10px;
  border: solid 3px #DAA520;
  border-radius: 10px;
  width: 60%;
}

.storage-view {
  width: 50%;
  float: left;
}
.right-box{
  width: 40%;
  float: right;
  margin-right: 10%;
  margin-bottom: 10%;
  border: 1px solid black;
}
.scoll-list{
  border: 1px solid black;
  height: 400px;
  overflow: auto;
  width: 80%;
}

.storage {
  margin-left: 5%;
  margin-right: 5%;
  margin-top: 2%;
  border: solid 3px #42b983;
  width: 90%;
}
.cancleBtn{
  position: center;
  text-align: center;
  width: 5%;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;

}
.cancleBtn:hover{
  background: black;
  color: white;
}
</style>
