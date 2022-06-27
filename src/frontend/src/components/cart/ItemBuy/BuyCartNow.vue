<template>
  <div class="back">
    <br>
    <div class="frame">
      <div class="buyNow">
        <h2>주문/결제</h2>
        <h3>구매자 정보</h3>
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

        <h3>받는사람 정보</h3>
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
            <td><input type="text" v-model="getterPhoneNumber" maxlength="12"></td>
          </tr>
          <tr>
            <td class="buy-now-td">배송 요청사항</td>
            <td><input v-model="deliveryMessage" size="40" type="text"></td>
          </tr>
        </table>

        <h3>상품 정보</h3>

        <table v-for="(menu, index) in cart" :key="index">
          <tr>
            <td class="buy-now-td">상품</td>
            <td> <img :src="'/api/product_detail_images/' + menu.menuBuy.filename" class="img-fluid rounded-start" alt="..." style="width: 10%; height: 10%"> {{menu.menuBuy.buyName}}</td>
          </tr>
          <tr>
            <td class="buy-now-td">상품 금액</td>
            <td>{{menu.menuBuy.buyPrice}}</td>
          </tr>
          <tr>
            <td class="buy-now-td">수량</td>
            <td>{{menu.cartItemCount}}</td>
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
            <td>{{this.cartAllPrice}}</td>
          </tr>
          <tr>
            <td class="buy-now-td">배송비</td>
            <td>10000</td>
          </tr>
          <tr>
            <td class="buy-now-td">총 결제 금액</td>
            <td>{{this.cartAllPrice + 10000}}</td>
          </tr>
        </table>

        <h5>구매조건 확인 및 결제대행 서비스 약관 동의 <button @click="checkBuy()">보기</button></h5>
        <h5>개인정보 제3자 제공 동의<button>보기</button></h5>

        <h5 class="buy-now-info-check">위 주문 내용을 확인하였으며, 회원 본인은 개인정보 이용 및 제공(해외직구의 경우 국외제공) 및 결제에 동의합니다.</h5>
        <button class="payNow" @click="paymentBtn()">결제하기</button>

        <button class="cancel-buy-now" @click="cancelBtn()">취소</button>

      </div>
    </div>
    <br>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: 'BuyCartNow',
  created() {
    this.member = this.$store.state.loginState
    this.cartSelected = this.$store.state.selectedList
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
      paymentDate: new Date(),
      cart: [],
      member: [],
      buyMember: [],
      cartAllPrice: 0,
      cartSelected: [],
    }
  },
  mounted () {
    const IMP = window.IMP
    IMP.init('imp35975601')
  },
  methods: {
    DataList() {
      axios.get('http://localhost:9002/api/buyMember/' + this.member.mcode)
          .then(res => {
            console.log(res.data)
            this.buyMember = res.data
          })
          .catch(err => {
            console.log(err);
          })
      console.log(this.cartSelected.length)
      for(let i=0; i < this.cartSelected.length; i++){
        axios.get('http://localhost:9002/api/buyCartOrder/' + this.cartSelected[i])
            .then(res => {
              console.log(res.data);
              this.cart[i] = res.data;
              this.cartAllPrice += this.cart[i].menuBuy.buyPrice * this.cart[i].cartItemCount
            })
            .catch(e => {
              console.log(e);
            })
      }
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
          name: "zz",
          amount: 1000,
          buyer_tel: this.getterPhoneNumber,
          buyer_name: this.buyMember.mcode,
          buyer_email: this.buyMember.mmail,
          confirm_url: ''
        }, (rsp) => {
          //rsp.success
          if (true) {
            const msg = '결제가 완료되었습니다.'
            // msg += '고유ID : ' + rsp.imp_uid
            // msg += '상점 거래 ID : ' + rsp.merchant_uid
            // msg += '결제 금액 : ' + rsp.paid_amount
            // msg += '카드 승인번호 : ' + rsp.apply_num
            alert(msg)
            for(let i = 0; i < this.cart.length; i++){
              this.axios.post('http://localhost:9002/api/buyData', {
                MID: this.buyMember.mcode,
                deliveryZipcode: this.zip,
                deliveryAddress: this.detailAddress,
                deliveryGetter: this.getterName,
                deliveryGetterTel: this.getterPhoneNumber,
                deliveryRequest: this.deliveryMessage,
                orderPrice: this.cart[i].menuBuy.buyPrice * this.cart[i].cartItemCount,
                orderType: rsp.pay_method,
                paymentCode: rsp.merchant_uid,
                orderState: '2',
                orderMenuCount: this.cart[i].cartItemCount,
                menuId: this.cart[i].menuBuy.buyId,
                cartCode: this.cart[i].cartCode
              })
                  .then((res)=>{
                    console.log(res.data);
                  })
                  .catch((err)=>{
                    console.log(err)
                  });
            }
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
  },
}
</script>

<style scoped>
.buyNow{
  padding: 1% 2%;
  width: 100%;
  height: 100%;
  padding-bottom: 3%;
}
.buyNow h3{
  padding: 1% 10%;
  width: 100%;
  height: 100%;
}
.buy-now-td{
  text-align: center;
  width: 20%;
}
.buyNow table {
  margin: 1.5% 15%;
  width: 50%;
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
  margin-right: 3%;
  width: 8%;
  padding: 1%;
  background-color: #ffffff;
  color: #00a3de;
  font-weight: bolder;
  border-color: #00a3de;
  border-radius: 1em;
}
.buy-now-info-check{
  margin: 3% 30%;
  padding: 1.5%;
}
.cancel-buy-now{
  margin-left: 5%;
  margin-right: 3%;
  width: 8%;
  padding: 1%;
  background-color: #ffffff;
  color: #00a3de;
  font-weight: bolder;
  border-color: #00a3de;
  border-radius: 1em;
}
.payNow:hover, .cancel-buy-now:hover{
  color: white;
  background-color: #b2e2fd;
}
.back {
  position: absolute;
  background-image: url(@/assets/campwall2.webp);
  background-size: 100%;
  width: 100%;
  height: auto;
  background-repeat: repeat-y;
  padding: auto;
}
.frame {
  border: none;
  border-radius: 50px;
  background-color: rgb(247, 246, 230);
  width: 86%;
  height: auto;
  margin: auto;
}
</style>