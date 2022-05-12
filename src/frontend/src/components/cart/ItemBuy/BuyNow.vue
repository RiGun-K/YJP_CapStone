<template>
  <div class="buyNow">
    <h2>주문/결제</h2>
    <h3>구매자 정보</h3>
    <table>
      <tr>
        <td class="buy-now-td">이름</td>
        <td>{{ this.Content.mid.mid }}</td>
      </tr>
      <tr>
        <td class="buy-now-td">이메일</td>
        <td>{{ this.Content.mid.mmail }}</td>
      </tr>
      <tr>
        <td class="buy-now-td">휴대폰 번호</td>
        <td>{{ this.Content.mid.mph }}</td>
      </tr>
    </table>

    <h3>받는사람 정보</h3>
    <table>
      <tr>
        <td class="buy-now-td">이름</td>
        <td><input type="text" v-model="Content.mid.mid"></td>
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
        <td><input size="40" v-bind:name="detailAddress" placeholder="상세 주소 입력"> </td>
      </tr>
      <tr>
        <td class="buy-now-td">연락처</td>
        <td><input type="text" v-model="getterPhoneNumber" maxlength="12"></td>
      </tr>
      <tr>
        <td class="buy-now-td">배송 요청사항</td>
        <td><input v-model="deliveryMessage" size="40" type="text"></td>
      </tr>
    </table>

    <h3>상품 정보</h3>
    <table>
      <tr>
        <td class="buy-now-td">상품 이름</td>
        <td>{{ this.Content.menuname }}</td>
      </tr>
      <tr>
        <td class="buy-now-td">상품 금액</td>
        <td>{{ this.Content.price }}</td>
      </tr>
      <tr>
        <td class="buy-now-td">배송비</td>
        <td>10000</td>
      </tr>
    </table>

    <h3>결제 정보</h3>
    <table>
      <tr>
        <td class="buy-now-td">총 상품 금액</td>
        <td>200000</td>
      </tr>
      <tr>
        <td class="buy-now-td">배송비</td>
        <td>10000</td>
      </tr>
      <tr>
        <td class="buy-now-td">총 결제 금액</td>
        <td>{{price}}</td>
      </tr>
    </table>

    <h5>구매조건 확인 및 결제대행 서비스 약관 동의 <button @click="checkBuy()">보기</button></h5>
    <h5>개인정보 제3자 제공 동의<button>보기</button></h5>

    <h5 class="buy-now-info-check">위 주문 내용을 확인하였으며, 회원 본인은 개인정보 이용 및 제공(해외직구의 경우 국외제공) 및 결제에 동의합니다.</h5>
    <button class="payNow" @click="paymentBtn()">결제하기</button>

    <!--    <div class="buy-check-scroll" v-if="buyCheck">-->
    <!--      <div>-->
    <!--        <p>This is some placeholder content for the scrollspy page. Note that as you scroll down the page, the appropriate navigation link is highlighted. It's repeated throughout the component example. We keep adding some more example copy here to emphasize the scrolling and highlighting.-->
    <!--          This is some placeholder content for the scrollspy page. Note that as you scroll down the page, the appropriate navigation link is highlighted. It's repeated throughout the component example. We keep adding some more example copy here to emphasize the scrolling and highlighting.-->
    <!--          This is some placeholder content for the scrollspy page. Note that as you scroll down the page, the appropriate navigation link is highlighted. It's repeated throughout the component example. We keep adding some more example copy here to emphasize the scrolling and highlighting.-->
    <!--          This is some placeholder content for the scrollspy page. Note that as you scroll down the page, the appropriate navigation link is highlighted. It's repeated throughout the component example. We keep adding some more example copy here to emphasize the scrolling and highlighting.-->
    <!--          This is some placeholder content for the scrollspy page. Note that as you scroll down the page, the appropriate navigation link is highlighted. It's repeated throughout the component example. We keep adding some more example copy here to emphasize the scrolling and highlighting.-->
    <!--          This is some placeholder content for the scrollspy page. Note that as you scroll down the page, the appropriate navigation link is highlighted. It's repeated throughout the component example. We keep adding some more example copy here to emphasize the scrolling and highlighting.-->
    <!--          This is some placeholder content for the scrollspy page. Note that as you scroll down the page, the appropriate navigation link is highlighted. It's repeated throughout the component example. We keep adding some more example copy here to emphasize the scrolling and highlighting.-->
    <!--        </p>-->
    <!--      </div>-->
    <!--    </div>-->
    <button class="cancel-buy-now" @click="cancelBtn()">취소</button>

  </div>
</template>

<script>
import axios from "axios";
export default {
  name: 'BuyNow',
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
      Content: [],
    }
  },
  mounted () {
    const IMP = window.IMP
    IMP.init('imp35975601')

  },
  methods: {
    DataList() {
      this.id = this.$route.params.menuid;
      console.log(this.id);
      axios.get('http://localhost:9002/api/product_detail/' + this.id)
          .then(res => {
            console.log(res.data);
            this.Content = res.data;
            console.log(this.Content.menuname);
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
          name: this.Content.menuname,
          amount: this.price,
          buyer_tel: this.getterPhoneNumber,
          buyer_name: this.Content.mid.mid,
          buyer_email: this.Content.mid.mmail,
          confirm_url: ''
        }, (rsp) => {
          if (rsp.success) {
            // let msg = '결제가 완료되었습니다.'
            // msg += '고유ID : ' + rsp.imp_uid
            // msg += '상점 거래 ID : ' + rsp.merchant_uid
            // msg += '결제 금액 : ' + rsp.paid_amount
            // msg += '카드 승인번호 : ' + rsp.apply_num
            // alert(msg)

            window.location.href = 'http://localhost:8081/itemBuy/buyComplete'
          } else {
            let msg = '결제에 실패하였습니다.'
            msg += '에러 내용 : ' + rsp.error_msg
            alert(msg)
            // let data = {
            //   MID: this.Content.mid.mcode,
            //   deliveryZipcode: this.zip,
            //   deliveryAddress: this.detailAddress,
            //   deliveryGetter: this.getterName,
            //   deliveryGetterTel: this.getterPhoneNumber,
            //   deliveryRequest: this.deliveryMessage,
            //   orderPrice: this.price,
            //   orderType: rsp.pay_method,
            //   paymentCode: rsp.merchant_uid,
            //   orderState: '2'
            // }
            //
            // this.axios.post('/api/buyData', data)
            //     .then((res)=>{
            //       console.log(res);
            //     })
            //     .catch((err)=>{
            //       console.log(err)
            //     });
            window.location.href = 'http://localhost:8081/itemBuy/buyComplete'
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