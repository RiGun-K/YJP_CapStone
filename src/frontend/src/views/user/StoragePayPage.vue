<template>
  <div class="divBody">
    결제페이지
    <table>
      <tr>
        <td colspan="2">보관소</td>
      </tr>
      <tr>
        <td>보관소</td>
        <td>{{ form.storageName }}</td>
      </tr>
      <tr>
        <td>보관함</td>
        <td>{{ form.storageBoxName }}</td>
      </tr>
    </table>
    <table>
      <tr>
        <td colspan="2">보관장비</td>
      </tr>
      <tr v-for="(item,index) in form.item" :key="index">
        <td>{{ index + 1 }}.</td>
        <td>{{ item.memEquipmentName }}</td>
      </tr>

    </table>
    <hr>
    <hr>
    <table>
      <tr>
        <td colspan="2">사용기간</td>
      </tr>
      <tr>
        <td>시작일</td>
        <td>{{ form.useStorageStartTime }}</td>
      </tr>
      <tr>
        <td>종료일</td>
        <td>{{ form.useStorageEndTime }}</td>
      </tr>
    </table>
    <hr>
    <table>
      <tr>
        <td colspan="2">결제금액</td>
      </tr>
      <tr>
        <td>금액</td>
        <td>{{ form.price }}원</td>
      </tr>
    </table>
    <hr>
    <h5>구매조건 확인 및 결제대행 서비스 약관 동의
      <button @click="checkBuy">보기</button>
    </h5>
    <h5>개인정보 제3자 제공 동의
      <button>보기</button>
    </h5>

    <h5 class="buy-now-info-check">위 주문 내용을 확인하였으며, 회원 본인은 개인정보 이용 및 제공(해외직구의 경우 국외제공) 및 결제에 동의합니다.</h5>
    <button class="payNow" @click="paymentBtn">결제하기</button>
  </div>
</template>

<script>
import axios from "axios";
import store from "@/store";

export default {
  name: "storagePayPage",
  data() {
    return {
      form: {
        storageName: '',
        storageBoxName: '',
        storageBoxCode: '',
        useStorageStartTime: '',
        useStorageEndTime: '',
        price: '',
        item: []
      },
    }
  },
  created() {
    this.upload()
  },
  mounted() {
    const IMP = window.IMP
    IMP.init('imp35975601')
  },
  methods: {
    upload() {
      this.form = this.$store.state.infoStorage
      this.form.useStorageStartTime = this.$store.state.timeStorage.useStorageStartTime
      this.form.useStorageEndTime = this.$store.state.timeStorage.useStorageEndTime
      this.form.item = this.$store.state.itemStorage
      this.form.userId = store.getters.getLoginState.loginState

      console.log(this.form)
      axios.get('/api/storageBoxGet/' + this.form.storageName+'/'+this.form.storageBoxName)
          .then(res => {
            this.form.storageBoxCode = res.data
          }).catch(err => {
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
    savePay() {
      const jsonData = {
        userId: this.form.userId,
        storageBoxCode: this.form.storageBoxCode,
        useStorageStartTime: new Date(this.form.useStorageStartTime),
        useStorageEndTime: new Date(this.form.useStorageEndTime),
        price: this.form.price,
        item: []
      }
      for (let i = 0; i < this.form.item.length; i++) {
        jsonData.item.push(this.form.item[i].memEquipmentCode)
      }
      this.$store.commit('storageClear')
      console.log('data')
      console.log(jsonData)
      axios.post('/api/payBox', jsonData)
          .then(res => {
            this.$store.commit('cartStorageClear')
            alert('결제가 완료 되었습니다.')
            console.log(res)
            this.$router.push("/storageView");
          })
          .catch(err => {
            alert('결제가 실패 되었습니다.')
            console.log(err)
          })

    },
    checkBuy() {
      if (this.buyCheck === true) {
        this.buyCheck = false
      } else {
        this.buyCheck = true
      }
    }

  }
}
</script>

<style scoped>
.divBody{
  margin-left: 25%;
  margin-right: 25%;
  margin-top: 1%;
  width: 50%;
}
</style>
