<template>
  <div class="buyNow">
    <h1 style="font-weight: bold">주문/결제</h1>
    <h2>구매자 정보</h2>
    <table>
      <tr>
        <td class="buy-now-td">이름</td>
        <td>{{ this.buyMember.mname }}</td>
      </tr>
      <tr>
        <td class="buy-now-td">이메일</td>
        <td>{{ this.buyMember.mmail }}</td>
      </tr>
      <tr>
        <td class="buy-now-td">휴대폰 번호</td>
        <td>{{ this.buyMember.mph }}</td>
      </tr>
    </table>

    <h2>받는사람 정보</h2>
    <table>
      <tr>
        <td class="buy-now-td">이름</td>
        <td><input type="text" v-model="getterName"></td>
      </tr>
      <tr>
        <td class="buy-now-td">우편번호</td>
        <td><input v-bind:value="zip" v-bind:disabled="zip" placeholder="우편번호"><button @click="showApi()">우편번호 찾기</button></td>
      </tr>
      <tr>
        <td rowspan='2' class="buy-now-td">배송 주소</td>
        <td><input size="40" v-bind:value="basicAddress" v-bind:disabled="basicAddress" placeholder="기본 주소"></td>
      </tr>
      <tr>
        <!--        <td></td>-->
        <td><input size="40" v-model="detailAddress" placeholder="상세 주소 입력"> </td>
      </tr>
      <tr>
        <td class="buy-now-td">연락처</td>
        <td><input type="text" v-model="getterPhoneNumber" maxlength="11"></td>
      </tr>
      <tr>
        <td class="buy-now-td">배송 요청사항</td>
        <td><input v-model="deliveryMessage" size="40" type="text"></td>
      </tr>
    </table>

    <h2>상품 정보</h2>
    <table>
      <tr>
        <td class="buy-now-td">상품 이름</td>
        <td>{{ this.content.buyName }}</td>
      </tr>
      <tr>
        <td class="buy-now-td">상품 금액</td>
        <td>{{ this.content.buyPrice }}</td>
      </tr>
      <tr>
        <td class="buy-now-td">수량</td>
        <td>{{ this.count }}</td>
      </tr>
      <tr>
        <td class="buy-now-td">배송비</td>
        <td>10000</td>
      </tr>
    </table>

    <h2>결제 정보</h2>
    <table>
      <tr>
        <td class="buy-now-td">총 상품 금액</td>
        <td>{{ this.content.buyPrice * this.count }}</td>
      </tr>
      <tr>
        <td class="buy-now-td">배송비</td>
        <td>10000</td>
      </tr>
      <tr>
        <td class="buy-now-td">총 결제 금액</td>
        <td>{{ this.content.buyPrice * this.count + 10000}}</td>
      </tr>
    </table>

    <h5 class="buy-now-info-check">위 주문 내용을 확인하였으며, 회원 본인은 개인정보 이용 및 제공(해외직구의 경우 국외제공) 및 결제에 동의합니다.</h5>
    <div style="display: flex; justify-content: center; align-items: center">
      <button class="buyBtn" @click="paymentBtn()">결제하기</button>
      <button class="buyBtn" @click="cancelBtn()">취소</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: 'BuyNow',
  created() {
    this.member = this.$store.state.loginState
    console.log(this.member)
    this.DataList();
  },
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
      member: [],
      buyMember: [],
      AllPrice: 0
    }
  },
  props: {
    BuyCount:{
      type: Number,
    }
  },
  mounted () {
    const IMP = window.IMP
    IMP.init('imp35975601')
  },
  methods: {
    DataList() {
      this.id = this.$route.params.buyId;
      this.count = this.$route.query.count;
      console.log(this.id);
      console.log(this.count)
      axios.get('http://localhost:9002/api/buyMember/' + this.member.mcode)
          .then(res => {
            console.log(res.data)
            this.buyMember = res.data
          })
      axios.get('http://localhost:9002/api/product_detailB/' + this.id)
          .then(res => {
            console.log(res.data);
            this.content = res.data;
            //
          })
          .catch(e => {
            console.log(e);
          })
      this.AllPrice = this.content.buyPrice * this.BuyCount
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
          name: this.content.buyName,
          amount: this.content.buyPrice,
          buyer_tel: this.getterPhoneNumber,
          buyer_name: this.content.mid.mid,
          buyer_email: this.content.mid.mmail,
          confirm_url: ''
        }, (rsp) => {
          if (true) {
            const msg = '결제가 완료되었습니다.'
            // msg += '고유ID : ' + rsp.imp_uid
            // msg += '상점 거래 ID : ' + rsp.merchant_uid
            // msg += '결제 금액 : ' + rsp.paid_amount
            // msg += '카드 승인번호 : ' + rsp.apply_num
            alert(msg)
            console.log(this.content.buyId);
            this.axios.post('http://localhost:9002/api/buyData', {
              MID: this.member.mcode,
              deliveryZipcode: this.zip,
              deliveryAddress: this.detailAddress,
              deliveryGetter: this.getterName,
              deliveryGetterTel: this.getterPhoneNumber,
              deliveryRequest: this.deliveryMessage,
              orderPrice: this.content.buyPrice,
              orderType: rsp.pay_method,
              paymentCode: rsp.merchant_uid,
              orderState: '2',
              orderMenuCount: 1,
              menuId: this.content.buyId,
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
                menuName: this.content.buyName,
                orderPrice: this.content.buyPrice,
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
.buyNow{
  padding: 1% 2%;
  width: 100%;
  height: 100%;
}
.buyNow h2{
  padding: 1% 14%;
  width: 100%;
  height: 100%;
  font-weight: bold;
}
.buy-now-td{
  text-align: center;
  width: 20%;
}
.buyNow table {
  margin: 1.5% 24%;
  width: 50%;
  border: 1px solid #444444;
  border-collapse: collapse;
  font-size: 1.5em;
}
.buyNow td {
  border: 1px solid #444444;
  padding: 2%;
}
.buyNow h5{
  margin-left: 28%;
  padding: 1.5%;
  margin-top: 5%;
}
.buyBtn{
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