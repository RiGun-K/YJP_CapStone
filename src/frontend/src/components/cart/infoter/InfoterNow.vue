<template>
  <div class="infoterNow">
    <h2>예약/결제</h2>

    <h3>구매자 정보</h3>
    <table>
      <tr>
        <td class="infoter-now-td">이름</td>
        <td>갑돌이</td>
      </tr>
      <tr>
        <td class="infoter-now-td">이메일</td>
        <td>Tang@naver.com</td>
      </tr>
      <tr>
        <td class="infoter-now-td">휴대폰 번호</td>
        <td>01012345678</td>
      </tr>
    </table>

    <h3>예약 정보</h3>
    <table>
      <tr>
        <td class="infoter-now-td">이름</td>
        <td><input type="text"></td>
      </tr>
      <tr>
        <td class="infoter-now-td">연락처</td>
        <td><input type="text"></td>
      </tr>
      <tr>
        <td class="infoter-now-td">예약 요청사항</td>
        <td><input size="40" type="text"></td>
      </tr>
      <tr>
        <td class="infoter-now-td">예약일</td>
        <td><Datepicker v-model="reservationDate" :enable-time-picker="false" :min-date="today" :max-date="end" range placeholder="Select date range"></Datepicker></td>
      </tr>
    </table>

    <h3>결제 정보</h3>
    <table>
      <tr>
        <td class="infoter-now-td">객실 금액</td>
        <td>200000</td>
      </tr>
      <tr>
        <td class="infoter-now-td">옵션 금액</td>
        <td>10000</td>
      </tr>
      <tr>
        <td class="infoter-now-td">총 결제 금액</td>
        <td>{{price}}</td>
      </tr>
    </table>

    <h5>취소 및 환불수수료 동의<button>보기</button></h5>
    <h5>개인정보 제3자 제공 동의<button>보기</button></h5>
    <h5>개인정보 수집 및 이용 동의<button>보기</button></h5>

    <h5 class="infoter-now-info-check">위 주문 내용을 확인하였으며, 회원 본인은 개인정보 이용 및 제공(해외직구의 경우 국외제공) 및 결제에 동의합니다.</h5>
    <button class="pay-infoter-now" @click="paymentBtn()">결제하기</button>
    <button class="cancel-infoter-now" @click="cancelBtn()">취소</button>

  </div>
</template>

<script>
import Datepicker from '@vuepic/vue-datepicker'
import '@vuepic/vue-datepicker/dist/main.css'

export default {
  name: 'InfoterNow',
  components: { Datepicker },
  data () {
    return {
      price: 1000,
      reservationDate: null

    }
  },
  setup () {
    const today = new Date()
    const todayEnd = new Date()
    const end = new Date(todayEnd.setDate(todayEnd.getDate() + 21))
    return {
      today,
      end
    }
  },
  mounted () {
    const IMP = window.IMP
    IMP.init('imp35975601')
  },
  methods: {
    paymentBtn () {
      if (confirm('결제 하시겠습니까?')) {
        const IMP = window.IMP
        IMP.init('imp35975601')

        IMP.request_pay({
          pg: 'html5_inicis',
          pay_method: 'card',
          merchant_uid: 'merchant_' + new Date().getTime(),
          name: '상품명',
          amount: this.price,
          buyer_tel: '01012345678',
          confirm_url: ''
        }, (rsp) => {
          if (rsp.success) {
            let msg = '결제가 완료되었습니다.'
            msg += '고유ID : ' + rsp.imp_uid
            msg += '상점 거래 ID : ' + rsp.merchant_uid
            msg += '결제 금액 : ' + rsp.paid_amount
            msg += '카드 승인번호 : ' + rsp.apply_num
            alert(msg)
            window.location.href = 'http://localhost:8081/cart/itemBuy/buyComplete'
          } else {
            let msg = '결제에 실패하였습니다.'
            msg += '에러 내용 : ' + rsp.error_msg
            alert(msg)
            window.location.href = 'http://localhost:8081/cart/itemBuy/buyComplete'
          }
        })
      }
    },
    cancelBtn () {
      window.location.href = 'http://localhost:8081/infoter'
    }
  }
}
</script>

<style scoped>
.infoterNow{
  margin: 1% 2%;
  width: 100%;
  height: 100%;
}
.infoterNow h3{
  margin: 1% 10%;
  width: 100%;
  height: 100%;
}
.infoter-now-td{
  text-align: center;
  width: 20%;
}
.infoterNow table {
  margin: 1.5% 15%;
  width: 35%;
  border: 1px solid #444444;
  border-collapse: collapse;
}
.infoterNow td {
  border: 1px solid #444444;
  padding: 2%;
}
.infoterNow h5{
  margin: 1% 10%;
}
.infoterNow h5 button{
  margin: 0% 2%;
}
.pay-infoter-now{
  margin-left: 27%;
}
.infoter-now-info-check{
  margin: 3% 30%;
  padding: 1.5%;
}
.cancel-infoter-now{
  margin-left: 5%;
}
</style>
