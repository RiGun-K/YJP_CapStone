<!--<template>-->
<!--  &lt;!&ndash;  관리하는 보관함 보기 &ndash;&gt;-->
<!--  <div class="storage">-->
<!--    <div style="width: 100%">-->
<!--      <h3 style="margin: 10px">보관소 이름: {{ this.storageList.storageName }}</h3>-->
<!--    </div>-->
<!--    <div class="storage-view">-->
<!--      <div class="storage-box" v-for="(box) in storageList.storageBoxes" :key="index"-->
<!--           @click="modalViewChk(box.storageBoxCode)">-->
<!--          <ul>-->
<!--            <li>보관함 : {{ box.storageBoxName }}</li>-->
<!--            <li>상태 : {{ stateString(box.storageBoxState) }}</li>-->
<!--          </ul>-->
<!--      </div>-->
<!--    </div>-->
<!--  </div>-->

<!--  &lt;!&ndash; 모달&ndash;&gt;-->
<!--  <div v-if="modalView" class="modal">-->
<!--    <div style="width: 100%; text-align: right">-->
<!--      <button @click="close()" class="cancleBtn">X</button>-->
<!--    </div>-->
<!--    <div class="modal-body">-->
<!--      <BoxModalDetail :boxCode="boxCode" @updata="getBackData()"/>-->
<!--    </div>-->
<!--  </div>-->
<!--</template>-->

<!--<script>-->
<!--import axios from "axios";-->
<!--import store from "@/store"-->
<!--import BoxModalDetail from "@/components/storageService/manager/NotUsingInfo.vue";-->

<!--export default {-->
<!--  name: "StorageDetail",-->
<!--  components: {-->
<!--    BoxModalDetail-->
<!--  },-->
<!--  data() {-->
<!--    return {-->
<!--      managerId: '',-->
<!--      storageList: '',-->
<!--      name: '',-->
<!--      modalView: false,-->
<!--      modal: false,-->
<!--      message: '',-->
<!--      boxCode: '',-->
<!--      boxArray: [],-->
<!--    }-->
<!--  },-->
<!--  created() {-->
<!--    // axios.get('/api/updateBoxStateChk/'+)-->
<!--  },-->
<!--  mounted() {-->
<!--    this.managerId = store.getters.getLoginState.loginState-->
<!--    if (store.getters.getLoginState.stateCode != 5) {-->
<!--      this.$router.push('/')-->
<!--      alert('보관소 매니저만 확인이 가능합니다')-->
<!--    } else {-->
<!--      this.getBackData()-->
<!--    }-->
<!--  },-->
<!--  methods: {-->
<!--    stateString(index) {-->
<!--      switch (index) {-->
<!--        case '0':-->
<!--          return '사용안함'-->
<!--        case '1':-->
<!--          return '결제완료'-->
<!--        case '2':-->
<!--          return '사용중'-->
<!--        case '3':-->
<!--        case '4':-->
<!--          return '사용중 - 보관소 이동 신청'-->
<!--        case '5':-->
<!--          return '사용중 - 수리신청'-->
<!--        case '6':-->
<!--          return '사용중 - 해지'-->
<!--        case '7':-->
<!--          return '배송 신청'-->
<!--        case 'x':-->
<!--          return '비활성화'-->
<!--      }-->
<!--    },-->
<!--    getBackData() {-->
<!--      axios.get('/api/getManagerStorage/' + this.managerId)-->
<!--          .then(res => {-->
<!--            this.storageList = res.data-->
<!--          })-->
<!--          .catch(err => {-->
<!--            console.log(err)-->
<!--          })-->
<!--    },-->
<!--    modalViewChk(storage) {-->
<!--      this.boxCode = storage-->
<!--      if (!this.modalView) {-->
<!--        this.modalView = !this.modalView-->
<!--      }-->

<!--    },-->
<!--  }-->
<!--}-->
<!--</script>-->

<!--<style lang="css" scoped>-->
<!--.storage-box {-->
<!--  margin: 10px;-->
<!--  border: solid 3px #DAA520;-->
<!--  border-radius: 10px;-->
<!--  width: 15%;-->
<!--}-->

<!--.storage-view {-->
<!--  display: flex;-->
<!--  width: 100%;-->
<!--}-->

<!--.storage {-->
<!--  margin-top: 2%;-->
<!--  margin-left: 5%;-->
<!--  margin-right: 5%;-->
<!--  border: solid 3px #42b983;-->
<!--  width: 90%;-->
<!--}-->

<!--.cancleBtn {-->
<!--  margin-left: 5%;-->
<!--  margin-right: 5%;-->
<!--  margin-top: 1%;-->
<!--}-->

<!--.cancleBtn:hover {-->
<!--  background: black;-->
<!--  color: white;-->
<!--}-->
<!--</style>-->
