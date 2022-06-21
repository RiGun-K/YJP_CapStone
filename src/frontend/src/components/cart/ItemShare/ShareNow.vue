<template>
  <div class="share-now">
    <h1 style="font-weight: bold">대여/결제</h1>
    <h2>구매자 정보</h2>
    <table>
      <tr>
        <td class="share-now-td">이름</td>
        <td>{{ user.mname }}</td>
      </tr>
      <tr>
        <td class="share-now-td">이메일</td>
        <td>{{ user.mmail }}</td>
      </tr>
      <tr>
        <td class="share-now-td">휴대폰 번호</td>
        <td>{{ user.mph }}</td>
      </tr>
    </table>

    <h2>받는사람 정보</h2>
    <table>
      <tr>
        <td class="share-now-td">이름</td>
        <td><input type="text" v-model="getterName"></td>
      </tr>
      <tr>
        <td class="share-now-td">우편번호</td>
        <td><input v-bind:value="zip" v-bind:disabled="zip" placeholder="우편번호"><button @click="showApi()">우편번호 찾기</button></td>
      </tr>
      <tr>
        <td rowspan='2' class="share-now-td">배송 주소</td>
        <td><input size="40" v-bind:value="basicAddress" v-bind:disabled="addr1" placeholder="기본 주소"></td>
      </tr>
      <tr>
        <!--        <td></td>-->
        <td><input size="40" v-model="detailAddress" placeholder="상세 주소 입력"> </td>
      </tr>
      <tr>
        <td class="share-now-td">연락처</td>
        <td><input type="text" v-model="getterPhoneNumber" maxlength="11"></td>
      </tr>
      <tr>
        <td class="share-now-td">배송 요청사항</td>
        <td><input size="40" type="text" v-model="deliveryMessage"></td>
      </tr>
    </table>

    <h2>대여상품 정보</h2>
    <table>
      <tr>
        <td class="share-now-td">상품 이름</td>
        <td>{{ content.rentalName }}</td>
      </tr>
      <tr>
        <td class="share-now-td">상품 1일당 금액</td>
        <td>{{ content.rentalPrice }}</td>
      </tr>
      <tr>
        <td class="share-now-td">대여일</td>
        <td>{{ this.$route.query.startDate }} ~ {{ this.$route.query.endDate }} ♡ 총 기간 {{ this.$route.query.period }} 일</td>
      </tr>
    </table>

    <h2>결제 정보</h2>
    <table>
      <tr>
        <td class="share-now-td">총 결제 금액</td>
        <td>{{ content.rentalPrice * this.$route.query.period }}</td>
      </tr>

    </table>

    <h5 class="share-now-info-check">위 주문 내용을 확인하였으며, 회원 본인은 개인정보 이용 및 제공(해외직구의 경우 국외제공) 및 결제에 동의합니다.</h5>
    <div style="display: flex; justify-content: center; align-items: center">
      <button class="shareBtn" @click="paymentBtn()">결제하기</button>
      <button class="shareBtn" @click="cancelBtn()">취소</button>
    </div>
  </div>
</template>

<script>
import Datepicker from '@vuepic/vue-datepicker'
import '@vuepic/vue-datepicker/dist/main.css'
import store from "@/store";
import axios from "axios";
export default {
  name: 'ShareNow',
  components: { Datepicker },
  data () {
    return {
      zip: '',
      basicAddress: '',
      detailAddress: '',
      buyCheck: false,
      getterName: '',
      getterPhoneNumber: '',
      deliveryMessage: '',
      onlyNumber: true,
      content: [],
      user: [],
    }
  },
  created() {
    this.DataList();
    console.log("대여 시작기간")
    console.log(this.$route.query.startDate)
    console.log("대여 종료기간")
    console.log(this.$route.query.endDate)
    console.log("대여 총 기간")
    console.log(this.$route.query.period)
  },
  mounted () {
    const IMP = window.IMP
    IMP.init('imp35975601')
  },
  methods: {
    DataList() {
      this.rentalId = this.$route.params.rentalId;
      this.user = store.getters.getLoginState.mcode;
      console.log(this.rentalId);
      console.log(this.user);
      axios.get('http://localhost:9002/api/product_detailRR/' + this.rentalId)
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
    showApi () {
      new window.daum.Postcode({
        oncomplete: (data) => {
          let fullRoadAddr = data.roadAddress
          let extraRoadAddr = ''
          if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
            extraRoadAddr += data.bname
          }
          if (data.buildingName !== '' && data.apartment === 'Y') {
            extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName)
          }
          if (extraRoadAddr !== '') {
            extraRoadAddr = ' (' + extraRoadAddr + ')'
          }
          if (fullRoadAddr !== '') {
            fullRoadAddr += extraRoadAddr
          }
          this.zip = data.zonecode
          this.basicAddress = fullRoadAddr
        }
      }).open()
    },
    paymentBtn () {
      if (confirm('결제 하시겠습니까?')) {
        const IMP = window.IMP
        IMP.init('imp35975601')
        IMP.request_pay({
          pg: 'html5_inicis',
          pay_method: 'card',
          merchant_uid: 'merchant_' + new Date().getTime(),
          name: this.content.rentalName,
          amount: this.content.rentalPrice * this.$route.query.period,
          buyer_tel: this.getterPhoneNumber,
          buyer_name: this.content.mid.mid,
          buyer_email: this.content.mid.mmail,
          confirm_url: ''
        }, (rsp) => {
          if (true) {
            let msg = '결제가 완료되었습니다.'
            // msg += '고유ID : ' + rsp.imp_uid
            // msg += '상점 거래 ID : ' + rsp.merchant_uid
            // msg += '결제 금액 : ' + rsp.paid_amount
            // msg += '카드 승인번호 : ' + rsp.apply_num
            alert(msg)
            console.log(this.content);
            this.axios.post('http://localhost:9002/api/rentalData', {
              MID: this.user.mcode,
              deliveryZipcode: this.zip,
              deliveryAddress: this.detailAddress,
              deliveryGetter: this.getterName,
              deliveryGetterTel: this.getterPhoneNumber,
              deliveryRequest: this.deliveryMessage,
              orderPrice: this.content.rentalPrice * this.$route.query.period,
              orderType: rsp.pay_method,
              paymentCode: rsp.merchant_uid,
              orderState: '2',
              orderMenuCount: 1,
              startDate: this.$route.query.startDate,
              endDate: this.$route.query.endDate,
              rentalId: this.content.rentalId
            })
                .then((res)=>{
                  console.log(res.data);
                })
                .catch((err)=>{
                  console.log(err)
                });
            this.$router.push({
              name: "InfoterComplete",
              params: {
                orderMenuCount: 1,
                menuName: this.content.detailName,
                orderPrice: this.content.detailPrice * this.$route.query.period,
                reservationDate: this.reservationDate,
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
      window.location.href = 'http://localhost:8081/itemShare'
    }
  },
  watch: {
    getterPhoneNumber(val) {
      const reg = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
      if(reg.exec(val)!==null) {
        this.getterPhoneNumber = this.getterPhoneNumber.slice(0,-1);
        alert("숫자만 입력해주세요")
      }
      return this.getterPhoneNumber=this.getterPhoneNumber.replace(/[^-\.0-9]/g,'');
    }
  }
}
</script>

<style scoped>
.share-now{
  margin: 1% 2%;
  width: 100%;
  height: 100%;
}
.share-now h2{
  padding: 1% 14%;
  width: 100%;
  height: 100%;
  font-weight: bold;
}
.share-now-td{
  text-align: center;
  width: 20%;
}
.share-now table {
  margin: 1.5% 24%;
  width: 50%;
  border: 1px solid #444444;
  border-collapse: collapse;
  font-size: 1.5em;
}
.share-now td {
  border: 1px solid #444444;
  padding: 2%;
}
.share-now-info-check{
  margin-left: 28%;
  padding: 1.5%;
  margin-top: 5%;
}
.shareBtn{
  margin-left: 2%;
  margin-right: 3%;
  width: 10%;
  padding: 1%;
  background-color: #ffffff;
  color: #00a3de;
  font-weight: bolder;
  border-color: #00a3de;
  border-radius: 1em;
  font-size: 1.5em;
}
</style>