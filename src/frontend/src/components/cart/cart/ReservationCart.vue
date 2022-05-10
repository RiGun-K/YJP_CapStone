<template>
  <div class="reservation-cart">
    <h2>장바구니(예약)</h2>

    <table class="reservation-cart-item-info">
      <tr>
        <td class="reservation-cart-checkbox"><input type="checkbox" @click="checkAll" v-model="allChecked">전체선택</td>
        <td>상품/옵션 정보</td>
        <td>객실 금액</td>
        <td>옵션 금액</td>
        <td>예약 금액</td>
      </tr>
      <tr  v-for="(mx, index) in list" :key="index">
        <td class="reservation-cart-checkbox"><input type="checkbox" :checked="mx.checked" ></td>
        <td>
          <div class="card mb-3" style="max-width: 540px;">
            <div class="row g-0">
              <div class="col-md-4">
                <img src="" class="img-fluid rounded-start" alt="...">
              </div>
              <div class="col-md-8">
                <div class="card-body">
                  <h5 class="card-title">상품이름: {{ mx.name }}</h5>
                  <h5 class="card-text">옵션</h5>
                  <h5 class="card-text">예약기간</h5>
                </div>
              </div>
            </div>
          </div>
        </td>
        <td>{{mx.roomPrice}}</td>
        <td>{{mx.optionPrice}}</td>
        <td>{{mx.roomPrice + mx.optionPrice}}</td>
      </tr>
    </table>
  </div>

  <div class="reservation-cart-btn-list">
    <button class="reservation-cart-btn">계속 쇼핑하기</button>
    <button class="reservation-cart-btn">대여하기</button>
  </div>
</template>

<script>
export default {
  name: 'ReservationCart',
  data () {
    return {
      allChecked: false,
      list: [
        { name: 1, count: 1, roomPrice: 25000, optionPrice: 5000, checked: false },
        { name: 2, count: 5, roomPrice: 20000, optionPrice: 7000, checked: false },
        { name: 3, count: 3, roomPrice: 15000, optionPrice: 10000, checked: false }
      ]
    }
  },
  methods: {
    checkAll () {
      if (this.allChecked === false) {
        this.allChecked = true
        this.list[0].checked = true
        this.list[1].checked = true
        this.list[2].checked = true
      } else {
        this.allChecked = false
        this.list[0].checked = false
        this.list[1].checked = false
        this.list[2].checked = false
      }
    }
  }
}
</script>

<style scoped>
.reservation-cart{
  margin: 4%;
  width: 100%;
  height: 100%;
}
.reservation-cart .reservation-cart-item-info {
  margin-left: 10%;
  margin-top: 6%;
  width: 70%;
  border: 1px solid #444444;
  border-collapse: collapse;
}
.reservation-cart td {
  border: 1px solid #444444;
  padding: 1%;
}
.reservation-cart-checkbox{
  width: 12%;
}
.reservation-cart-btn-list{
  text-align: center;
  margin-top: 7%;
  width: 100%;
}
.reservation-cart-btn{
  margin-left: 2%;
  margin-right: 3%;
  width: 8%;
  padding: 1%;
  background-color: #ffffff;
  color: #00a3de;
  font-weight: bolder;
  border-color: #00a3de;
  border-radius: 1em;
}
.reservation-cart-btn:hover{
  color: white;
  background-color: #b2e2fd;
}
</style>