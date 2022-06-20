<template>
  <div>
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
          <td>{{moveBoxInfo.storageName}}</td>
        </tr>
        <tr>
          <td>보관함</td>
          <td>{{ moveBoxInfo.boxName }}</td>
        </tr>
        </tbody>
      </table>
    </div>

    <div>
      <table>
        <thead>
        <tr>
          <th colspan="3">보관중인장비</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(item,index) in myItem">
          <td>{{ index + 1 }}</td>
          <td>{{ item.memEquipmentName }}</td>
          <td>{{ item.memEquipmentCount }}</td>
        </tr>
        </tbody>
      </table>
    </div>

    <div>
      <table>
        <thead>
        <tr>
          <th colspan="2">배송지</th>
        </tr>
        </thead>
        <tbody>
        <tr>
          <td>받는사람</td>
          <td>{{member.mname}}</td>
        </tr>
          <tr>
            <td>우편번호</td>
            <td>{{ member.mzadd }}</td>
          </tr>
          <tr>
            <td>주소</td>
            <td>{{ member.madd }}</td>
          </tr>
          <tr>
            <td>상세주소</td>
            <td>{{ member.mradd }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div>
      <h5>가격 {{price}}원</h5>
    </div>
    <div>
      <hr>
      <button @click="paymentBtn()">결제</button>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import store from "@/store";

export default {
  name: "HomeMoveBox",
  props:{
    useBoxCode:''
  },
  data(){
    return{
      myItem: {},
      boxName: '',
      storageName:'',
      zipCode:'',
      address:'',
      member:{},
      moveBoxInfo:{},
      price : 4500,
    }
  },
  mounted() {
    this.getItem()
    this.getAddress()
    this.getUseBoxInfo()
  },
  methods:{
    getUseBoxInfo(){
      axios.get('/api/moveBoxInfo/' + this.useBoxCode)
          .then(res => {
            this.moveBoxInfo.storageCode = res.data[0][0]
            this.moveBoxInfo.storageName = res.data[0][1]
            this.moveBoxInfo.boxCode = res.data[0][2]
            this.moveBoxInfo.boxName = res.data[0][3]
          })
          .catch(err => {
            console.log(err)
          })
    },
    getItem(){
      axios.get('/api/getBoxInItem/' + this.useBoxCode)
          .then(res => {
            this.myItem = res.data
          })
          .catch(err => {
            console.log(err)
          })
    },
    getAddress(){
      axios.get('/api/myAddress/' + store.getters.getLoginState.loginState)
          .then(res => {
            this.member = res.data
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
      //     name: this.moveBoxInfo.storageName +'보관소'+this.moveBoxInfo.BoxName+'보관함 집 배송',
      //     amount: this.price/100,
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
      this.pay()
    },
    pay(){
      let data = {}
      data.useBoxCode = this.useBoxCode
      data.member = this.member
      axios.post('/api/homeToMovePay',data)
      .then(res=>{

      })
    }
  }
}
</script>

<style scoped>

</style>