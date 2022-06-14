<template>
  <div>
    <h5>pay</h5>
    <div>
      <h5>user</h5>
      <div>
        <table>
          <tr>
            <td>이름</td>
            <td>{{ member.mnick }}</td>
          </tr>
          <tr>
            <td>이메일</td>
            <td>{{ member.mmail }}</td>
          </tr>
          <tr>
            <td>휴대폰 번호</td>
            <td>{{ member.mph }}</td>
          </tr>
        </table>
      </div>
    </div>
    <div>
      <h5>storage</h5>
      <div>
        <table>
          <thead>
          <tr>
            <th>사용중인보관함</th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <td>보관소</td>
            <td>{{storageInfo.storageName}}</td>
          </tr>
          <tr>
            <td>보관함</td>
            <td>{{ storageInfo.boxName }}</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div>
      <h5>item</h5>
      <div>
        <table>
          <thead>
          <tr>
            <th >수리신청 장비</th>
            <th>장비</th>
            <th>수량</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(item,index) in careItemList">
            <td>{{ index + 1 }}</td>
            <td>{{ item.memEquipmentName }}</td>
            <td>{{ item.memEquipmentCount }}</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div>
      수리 기업 선택?
    </div>
    <div>
      <h5>option</h5>
      <div>
        <table>
          <thead>
          <tr>
            <th>수리 목록</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="care in careList">
            <td>{{ care }}</td>
          </tr>
          <tr>
            <td>{{option}}</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div>
      <h5>추가 요청사항</h5>
      <div>
        <textarea v-model="requestText"></textarea>
      </div>
    </div>
    <div>
      <h5>price</h5>
      <div>
        <button @click="paymentBtn">결제하기</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import store from "@/store";

export default {
  name: "RepairBoxPay",
  mounted() {
    this.useCode = this.$route.params.useBoxCode
    this.careItemList = this.$store.state.careItemInfo.repairList
    this.careList = this.$store.state.careItemInfo.requestList
    this.option = this.$store.state.careItemInfo.gitaRepair
    this.getMemberData()
    this.getBoxInfo()
  },
  data(){
    return{
      useCode:'',
      member:{},
      careItemList:[],
      careList:[],
      option:'',
      storageInfo:{},
      requestText:'',
    }
  },
  methods:{
    getMemberData(){
      axios.get('/api/myAddress/' + store.getters.getLoginState.loginState)
          .then(res => {
            this.member = res.data
          })
          .catch(err => {
            console.log(err)
          })
    },
    getBoxInfo() {
      axios.get('/api/moveBoxInfo/' + this.useCode)
          .then(res => {
            console.log(res.data)
            this.storageInfo.storageName = res.data[0][1]
            this.storageInfo.boxName = res.data[0][3]
          })
          .catch(err => {
            console.log(err)
          })
    },
    paymentBtn() {
      // if (confirm('결제 하시겠습니까?')) {
      //   const IMP = window.IMP
      //   IMP.init('imp35975601')
      //   IMP.request_pay({
      //     pg: 'html5_inicis',
      //     pay_method: 'card',
      //     merchant_uid: 'merchant_' + new Date().getTime(),
      //     name: this.form.storageName +'보관소'+this.form.storageBoxName+'보관함',
      //     amount: this.form.price/100,
      //     buyer_tel: '01012345678',
      //     confirm_url: ''
      //   }, (rsp) => {
      //     if (rsp.success) {
      //
      //       this.savePay()
      //
      //     } else {
      //       let msg = '결제에 실패하였습니다.'
      //       msg += '에러 내용 : ' + rsp.error_msg
      //       alert(msg)
      //
      //     }
      //   })
      // }
      this.savePay()
    },
    savePay(){

    }
  },
}
</script>

<style scoped>

</style>