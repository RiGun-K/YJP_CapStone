<template>
  <div>
    <div>
      <h5>현재 박스 위치</h5>
      <div>
        <table>
          <tr>
            <td>보관소</td>
            <td>{{ beforeBoxInfo.storageName }}</td>
          </tr>
          <tr>
            <td>보관함</td>
            <td>{{ beforeBoxInfo.boxName }}</td>
          </tr>
        </table>
      </div>
    </div>
    <div>
      <h5>이동할 박스 위치</h5>
      <div>
        <table>
          <tr>
            <td>보관소</td>
            <td>{{ afterBoxInfo.storageName }}</td>
          </tr>
          <tr>
            <td>보관함</td>
            <td>{{ afterBoxInfo.boxName }}</td>
          </tr>
        </table>
      </div>
    </div>
    <div>
      <h5>보관중인 장비</h5>
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
    </div>
    <div>
      <div>
        <h5>금액
          {{ price }}원</h5>
      </div>
      <div>
        <button @click="paymentBtn()">결제</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import store from "@/store";

export default {
  name: "BoxToBoxMovePay",
  mounted() {
    this.newBoxCode = this.$route.params.newStorageBoxCode
    this.beforeBoxInfo = this.$store.state.moveBoxInfo
    this.getBoxInfo()
    this.getItem()
    this.getPrice()
  },
  data() {
    return {
      newBoxCode: '',
      beforeBoxInfo: {},
      afterBoxInfo: {},
      myItem: {},
      beforeBoxPrice:0,
      price: 0,
    }
  },
  methods: {
    beforePrice(){
      axios.get('/api/boxPrice/' + this.$store.state.moveBoxInfo.boxCode)
          .then(res => {
            this.beforeBoxPrice = res.data
          })
          .catch((err) => {
            console.log(err)
          })
    },
    getPrice() {
      axios.get('/api/boxPrice/' + this.beforeBoxInfo.boxCode)
          .then(res => {
            this.beforePrice()
            this.price = res.data
            if (this.price < this.beforeBoxPrice) {
              this.stateCheck = false
            }else if(this.price == this.beforeBoxPrice){
              this.price = 5000
            }else{
              this.price = 5000 + this.price - this.beforeBoxPrice
            }
          })
    },
    getItem() {
      axios.get('/api/getBoxInItem/' + this.beforeBoxInfo.useBoxCode)
          .then(res => {
            this.myItem = res.data
          })
          .catch(err => {
            console.log(err)
          })
    },
    getBoxInfo() {
      axios.get('/api/getNamesToBoxCode/' + this.newBoxCode)
          .then(res => {
            this.afterBoxInfo.storageName = res.data[0][0]
            this.afterBoxInfo.boxName = res.data[0][1]
            this.beforeBoxInfo.boxCode = res.data[0][2]
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
      //       this.pay()
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
    pay() {
      const data = {
        userId: store.getters.getLoginState.loginState,
        use: this.beforeBoxInfo.useBoxCode,
        before: this.beforeBoxInfo.boxCode,
        after: this.newBoxCode
      }
      console.log(data)
      axios.post('/api/boxToBoxPay', data)
          .then(res => {
            console.log(res)
            this.$router.push({name: 'storageComplete'});
          }).catch(err => {
        console.log(err)
      })
    }
  }
}
</script>

<style scoped>

</style>