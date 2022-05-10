<template>
  <div class="pay-share-complete">
    <h2>대여/결제</h2>
    <div class="pay-share-info">
      <h2>"감사합니다. 결제가 완료 되었습니다."</h2>
      <h5>결제내역<button >바로가기</button></h5>
      <table class="pay-share-now-info">
        <tr>
          <td>대여 금액 {{price}}</td>
          <td>배송비 {{price}}</td>
          <td>결제 금액 {{price}}</td>
        </tr>
        <tr>
          <td>대여상품 총 {{n}}개</td>
          <td> </td>
          <td>결제수단</td>
        </tr>
      </table>

      <h5>대여하신 상품 정보</h5>
      <table class="pay-share-now-item-info">
        <tr>
          <td>상품/옵션 정보</td>
          <td>대여기간</td>
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
                    <h5 class="card-title">상품 이름</h5>
                    <p class="card-text">옵션</p>
                  </div>
                </div>
              </div>
            </div>
          </td>
          <td></td>
          <td>{{n}}</td>
          <td>{{price}}</td>
          <td>{{price}}</td>
        </tr>
      </table>

      <table class="pay-share-now-item-info">
        <tr>
          <td>상품/옵션 정보</td>
          <td>대여기간</td>
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
                    <h5 class="card-title">상품 이름</h5>
                    <p class="card-text">옵션</p>
                  </div>
                </div>
              </div>
            </div>
          </td>
          <td></td>
          <td>{{n}}</td>
          <td>{{price}}</td>
          <td>{{price}}</td>
        </tr>
      </table>
    </div>
    <button class="pay-share-info-back-btn"><router-link class="nav-link" to="/itemShare">돌아가기</router-link></button>
  </div>
</template>

<script>
export default {
  name: 'ShareComplete',
  data () {
    return {
      price: 1000,
      n: 5
    }
  }
}
</script>

<style scoped>
.pay-share-complete{
  margin: 1% 2%;
  width: 100%;
  height: 100%;
}
.pay-share-info h2{
  margin: 7%;
  text-align: center;
}
.pay-share-info h5{
  margin: 1% 15%;
}
.pay-share-info .pay-share-now-info {
  margin: 1% 0% 4% 15% ;
  width: 70%;
  border: 1px solid #444444;
  border-collapse: collapse;
}
.pay-share-info .pay-share-now-info td {
  border: 1px solid #444444;
  padding: 3%;
  width: 10%;
}
.pay-share-info .pay-share-now-item-info {
  margin: 1% 15%;
  width: 70%;
  border: 1px solid #444444;
  border-collapse: collapse;
}
.pay-share-info .pay-share-now-item-info td{
  border: 1px solid #444444;
  padding: 2%;
}
.pay-share-complete .pay-share-info-back-btn{
  margin: 4% 46%;
  width: 10%;
}
</style>
