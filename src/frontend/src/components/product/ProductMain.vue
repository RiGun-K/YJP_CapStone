<template>
  <div class="back">
    <div class="frame">
      <ProductPage></ProductPage>
    <br>
  <h2>상품등록</h2>
  <div class="cart">
    <div class="cart-list">
      <div class="buy-cart-btn" @click="PBuy">
        <img :src="require('@/assets/buyBtn.png')">
        <br>
        <button class="w-btn-neon2">구매</button>
      </div>
      <div class="share-cart-btn" @click="PRental">
        <img :src="require('@/assets/rentBtn.png')">
        <br>
        <br>
        <button class="w-btn-neon2">렌탈</button>

      </div>
      <div class="reservation-cart-btn" @click="PCamping">
        <img :src="require('@/assets/calendarBtn.png')">
        <br>
        <br>
        <button class="w-btn-neon2">캠핑장</button>

      </div>
    </div>
    <button class="w-btn-outline w-btn-red-outline"><router-link class="nav-link" to="/">뒤로가기</router-link></button>
  </div>

    </div>
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
.w-btn:hover {
  letter-spacing: 2px;
  transform: scale(1.2);
  cursor: pointer;
}
.w-btn-outline {
  position: relative;
  padding: 15px 30px;
  border-radius: 15px;
  font-family: 'paybooc-Light', sans-serif;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
  text-decoration: none;
  font-weight: 600;
  transition: 0.25s;

  margin-top: 5% ;
  margin-left: 45%;
}
.w-btn-red-outline {
  border: 3px solid #2093e5;
  color: #6e6e6e;
}
.w-btn-red-outline:hover {
  background-color: #2093e5;
  color: #e1eef6;
}
.w-btn-outline:hover {
  letter-spacing: 2px;
  transform: scale(1.2);
  cursor: pointer;
}
.w-btn-outline:active {
  transform: scale(1.5);
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
@import url("https://fonts.googleapis.com/css?family=Poppins:200,300,400,500,600,700,800,900&display=swap");

@keyframes ring {
  0% {
    width: 30px;
    height: 30px;
    opacity: 1;
  }
  100% {
    width: 300px;
    height: 300px;
    opacity: 0;
  }
}

.w-btn-neon2 {
  position: relative;
  margin-left: 10%;
  border: none;
  min-width: 140px;
  min-height: 50px;
  background: linear-gradient(
      90deg,
      rgba(129, 230, 217, 1) 0%,
      rgba(79, 209, 197, 1) 100%
  );
  border-radius: 1000px;
  color: darkslategray;
  cursor: pointer;
  box-shadow: 12px 12px 24px rgba(79, 209, 197, 0.64);
  font-weight: 700;
  transition: 0.3s;
}

.w-btn-neon2:hover {
  transform: scale(1.2);
}

.w-btn-neon2:hover::after {
  content: "";
  width: 30px;
  height: 30px;
  border-radius: 100%;
  border: 6px solid #00ffcb;
  position: absolute;
  z-index: -1;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  animation: ring 1.5s infinite;
}
</style>