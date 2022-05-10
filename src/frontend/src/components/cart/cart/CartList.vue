<template>
  <h2>장바구니</h2>
  <div class="cart">
    <div class="cart-list">
      <div class="buy-cart-btn" @click="buyCartLink">
        <img :src="require('@/assets/buyBtn.png')">
        <h5>구매</h5>
        <h5>장바구니</h5>
      </div>
      <div class="share-cart-btn" @click="shareCartLink">
        <img :src="require('@/assets/rentBtn.png')">
        <h5>대여</h5>
        <h5>장바구니</h5>
      </div>
      <div class="reservation-cart-btn" @click="reservationCartLink">
        <img :src="require('@/assets/calendarBtn.png')">
        <h5>예약</h5>
        <h5>장바구니</h5>
      </div>
    </div>
    <button class="cart-back"><router-link class="nav-link" to="/">Back</router-link></button>
  </div>
</template>

<script>
export default {
  name: 'CartList',
  methods: {
    buyCartLink () {
      window.location.href = 'http://localhost:8081/cart/buy'
    },
    shareCartLink () {
      window.location.href = 'http://localhost:8081/cart/share'
    },
    reservationCartLink () {
      window.location.href = 'http://localhost:8081/cart/reservation'
    }
  }
}
</script>

<style scoped>
h2{
  margin: 4%;
}
.cart{
  width: 100%;
  height: 100%;
}
.cart-back{
  margin-top: 10% ;
  margin-left: 48%;
}
.cart .cart-list{
  display:flex;
  justify-content: center;
  align-items: center;
}
.cart .buy-cart-btn{
  width: 15%;
  border: 1px solid black;
  text-align: center;
  padding: 3%;
  margin: 3%;
}
.cart .buy-cart-btn img{
  width: 50%;
  height: 50%;
}
.cart .buy-cart-btn h5 {
  margin-top: 7%;
}
.cart .share-cart-btn{
  margin: 3%;
  width: 15%;
  border: 1px solid black;
  padding: 3%;
  text-align: center;
}
.cart .share-cart-btn img{
  width: 50%;
  height: 50%;
}
.cart .share-cart-btn h5 {
  margin-top: 7%;
}
.cart .reservation-cart-btn{
  width: 15%;
  margin: 3%;
  border: 1px solid black;
  padding: 3%;
  text-align: center;
}
.cart .reservation-cart-btn img{
  width: 50%;
  height: 50%;
}
.cart .reservation-cart-btn h5 {
  margin-top: 7%;
}
</style>