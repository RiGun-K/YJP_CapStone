<template>
  <div class="pay-complete">
    <h2>주문/결제</h2>
    <div class="pay-info">
      <h2>"감사합니다. 결제가 완료 되었습니다."</h2>
      <h5>결제내역<button >바로가기</button></h5>
      <table class="pay-now-info">
        <tr>
          <td>주문 금액 {{orderPrice}}</td>
          <td>배송비 1000</td>
          <td>결제 금액 {{orderPrice}}</td>
        </tr>
        <tr>
          <td>구매상품 총 {{orderMenuCount}}개</td>
          <td></td>
          <td>{{orderType}}</td>
        </tr>
      </table>

      <h5>상품 정보</h5>
      <table class="pay-now-item-info">
        <tr>
          <td>상품/옵션 정보</td>
          <td>수량</td>
          <td>상품 금액</td>
          <td>배송비</td>
        </tr>
        <tr>
          <td>
            <div class="card mb-3" style="max-width: 540px;">
              <div class="row g-0">
                <div class="col-md-4">
                  <img src="" class="img-fluid rounded-start" alt="...">
                </div>
                <div class="col-md-8">
                  <div class="card-body">
                    <h5 class="card-title">{{menuName}}</h5>
                    <p class="card-text">옵션</p>
                  </div>
                </div>
              </div>
            </div>
          </td>
          <td>{{orderMenuCount}}</td>
          <td>{{orderPrice}}</td>
          <td>1000</td>
        </tr>
      </table>

    </div>
    <button class="pay-info-back-btn"><router-link class="nav-link" to="/itemBuy">돌아가기</router-link></button>
  </div>
</template>

<script>
export default {
  name: 'BuyComplete',
  data () {

  },
  props: {
    orderMenuCount:{
      type: Number,
    },
    orderPrice:{
      type: Number,
    },
    menuName:{
      type: String,
    },
    orderType:{
      type: String,
    }
  }
  ,
  created() {
    console.log(this.orderMenuCount)
    console.log(this.menuName)
    console.log(this.orderType)

  }
}
</script>

<style scoped>
.pay-complete{
  margin: 1% 2%;
  width: 100%;
  height: 100%;
}
.pay-info h2{
  margin: 7%;
  text-align: center;
}
.pay-info h5{
  margin: 1% 15%;
}
.pay-info .pay-now-info {
  margin: 1% 0% 4% 15% ;
  width: 70%;
  border: 1px solid #444444;
  border-collapse: collapse;
}
.pay-info .pay-now-info td {
  border: 1px solid #444444;
  padding: 3%;
  width: 10%;
}
.pay-info .pay-now-item-info {
  margin: 1% 15%;
  width: 70%;
  border: 1px solid #444444;
  border-collapse: collapse;
}
.pay-info .pay-now-item-info td{
  border: 1px solid #444444;
  padding: 2%;
}
.pay-complete .pay-info-back-btn{
  margin: 4% 46%;
  width: 10%;
}

</style>
