<template>
  <ProductPage></ProductPage>
  <br>
  <h2>상품등록</h2>
  <div class="cart">
    <div class="cart-list">
      <div class="buy-cart-btn" @click="PBuy">
        <img :src="require('@/assets/buyBtn.png')">
        <h5>구매</h5>
      </div>
      <div class="share-cart-btn" @click="PRental">
        <img :src="require('@/assets/rentBtn.png')">
        <h5>렌탈</h5>
      </div>
      <div class="reservation-cart-btn" @click="PCamping">
        <img :src="require('@/assets/calendarBtn.png')">
        <h5>캠핑장</h5>
      </div>
    </div>
    <button class="cart-back"><router-link class="nav-link" to="/">뒤로가기</router-link></button>
  </div>



</template>

<script>
import ProductPage from "@/components/product/ProductPage";

export default {
  name: "main",
  components: {ProductPage},
  methods: {
    PBuy() {
      window.location.href = 'http://localhost:8081/ProductBuy'
    },
    PRental() {
      window.location.href = 'http://localhost:8081/ProductRental'
    },
    PCamping() {
      window.location.href = 'http://localhost:8081/ProductCamping'
    }
  }
}
</script>

<style scoped>

.navbar navbar-expand-lg navbar-light bg-light {
  justify-content: center;
}
h2,h3{
  margin: 4%;
}
.cart{
  width: 100%;
  height: 100%;
}
.cart-back{
  margin-top: 5% ;
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