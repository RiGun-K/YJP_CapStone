<template>
  <div class="buyNow">
    <h2>주문/결제</h2>
    <h3>구매자 정보</h3>
    <table>
      <tr>
        <td class="buy-now-td">이름</td>
        <td>{{ this.user.mnick }}</td>
      </tr>
      <tr>
        <td class="buy-now-td">이메일</td>
        <td>{{ this.user.mmail }}</td>
      </tr>
      <tr>
        <td class="buy-now-td">휴대폰 번호</td>
        <td>{{ this.user.mph }}</td>
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
        <td class="buy-now-td">객실 이름</td>
        <td>{{ this.content.detailName }}</td>
      </tr>
      <tr>
        <td class="buy-now-td">객실 금액</td>
        <td>{{ this.content.detailPrice }}</td>
      </tr>
      <tr>
        <td class="infoter-now-td">총 결제 금액</td>
        <td>{{price}}</td>
      </tr>
    </table>


    <h5>구매조건 확인 및 결제대행 서비스 약관 동의 <button @click="checkBuy()">보기</button></h5>
    <h5>개인정보 제3자 제공 동의<button>보기</button></h5>

    <h5 class="buy-now-info-check">위 주문 내용을 확인하였으며, 회원 본인은 개인정보 이용 및 제공(해외직구의 경우 국외제공) 및 결제에 동의합니다.</h5>
    <button class="payNow" @click="paymentBtn()">결제하기</button>

    <button class="cancel-buy-now" @click="cancelBtn()">취소</button>

  </div>
</template>

<script>
import Datepicker from '@vuepic/vue-datepicker'
import '@vuepic/vue-datepicker/dist/main.css'
import axios from "axios";
import store from "@/store";
export default {
  name: 'InfoterNow',
  components: { Datepicker },
  created() {
    this.DataList();
  },
  data () {
    return {
      zip: '',
      basicAddress: '',
      detailAddress: '',
      price: 1000,
      buyCheck: false,
      getterName: '',
      getterPhoneNumber: '',
      deliveryMessage: '',
      onlyNumber: true,
      paymentDate: new Date(),
      content: [],
      user: [],
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
    DataList() {
      this.id = this.$route.params.detailId;
      this.user = store.getters.getLoginState.mcode;
      console.log(this.id);
      console.log(this.user);
      axios.get('http://localhost:9002/api/product_detailR/' + this.id)
          .then(res => {
            console.log(res.data);
            this.content = res.data;
            //
          })
          .catch(e => {
            console.log(e);
          })
      //
      axios.get('http://localhost:9002/api/product_detailU/' + this.user)
          .then(res => {
            console.log(res.data);
            this.user = res.data;
            //
          })
          .catch(e => {
            console.log(e);
          })
    },

    paymentBtn () {
      if (confirm('결제 하시겠습니까?')) {
        const IMP = window.IMP
        IMP.init('imp35975601')
        IMP.request_pay({
          pg: 'html5_inicis',
          pay_method: 'card',
          merchant_uid: 'merchant_' + new Date().getTime(),
          name: this.content.detailName,
          amount: this.price,
          buyer_tel: this.getterPhoneNumber,
          buyer_name: this.user.mid,
          buyer_email: this.user.mmail,
          confirm_url: ''
        }, (rsp) => {
          if (rsp.success) {
            const msg = '결제가 완료되었습니다.'
            // msg += '고유ID : ' + rsp.imp_uid
            // msg += '상점 거래 ID : ' + rsp.merchant_uid
            // msg += '결제 금액 : ' + rsp.paid_amount
            // msg += '카드 승인번호 : ' + rsp.apply_num
            alert(msg)
            console.log(this.content.buyId);
            this.axios.post('http://localhost:9002/api/CampingRoomData', {
              MID: this.user.mid,
              deliveryZipcode: this.zip,
              deliveryAddress: this.detailAddress,
              deliveryGetter: this.getterName,
              deliveryGetterTel: this.getterPhoneNumber,
              deliveryRequest: this.deliveryMessage,
              orderPrice: this.price,
              orderType: rsp.pay_method,
              paymentCode: rsp.merchant_uid,
              orderState: '2',
              orderMenuCount: 1,
              menuId: this.content.detailId,
            })
                .then((res)=>{
                  console.log(res.data);
                })
                .catch((err)=>{
                  console.log(err)
                });
            this.$router.push({
              name: "BuyComplete",
              params: {
                orderMenuCount: 1,
                menuName: this.content.detailName,
                orderPrice: this.price,
                orderType: rsp.pay_method
              }
            })
          } else {
            let msg = '결제에 실패하였습니다.'
            msg += '에러 내용 : ' + rsp.error_msg
            alert(msg)
          }
        })
      }
    },
    cancelBtn () {
      window.location.href = 'http://localhost:8081/itemBuy'
    },
    checkBuy () {
      if (this.buyCheck === true) {
        this.buyCheck = false
      } else {
        this.buyCheck = true
      }
    }
  },
}
</script>

<style scoped>
.buyNow{
  margin: 1% 2%;
  width: 100%;
  height: 100%;
}
.buyNow h3{
  margin: 1% 10%;
  width: 100%;
  height: 100%;
}
.buy-now-td{
  text-align: center;
  width: 20%;
}
.buyNow table {
  margin: 1.5% 15%;
  width: 35%;
  border: 1px solid #444444;
  border-collapse: collapse;
}
.buyNow td {
  border: 1px solid #444444;
  padding: 2%;
}
.buyNow h5{
  margin: 1% 10%;
}
.buyNow h5 button{
  margin: 0% 2%;
}
.payNow{
  margin-left: 27%;
}
.buy-now-info-check{
  margin: 3% 30%;
  padding: 1.5%;
}
.cancel-buy-now{
  margin-left: 5%;
}
</style>