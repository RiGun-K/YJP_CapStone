<template>
  <div class="back">
    <br>
    <div class="frame">
      <h1 style="padding: 4%; font-weight: bold">장바구니</h1>
      <div class="cart">
        <div class="cart-list">
          <div class="buy-cart-btn" @click="buyCartLink">
            <img :src="require('@/assets/buyBtn.png')">
            <h5>BUY</h5>
            <h5>CART</h5>
          </div>
          <div class="share-cart-btn" @click="shareCartLink">
            <img :src="require('@/assets/rentBtn.png')">
            <h5>SHARE</h5>
            <h5>LIKE</h5>
          </div>
          <div class="reservation-cart-btn" @click="reservationCartLink">
            <img :src="require('@/assets/calendarBtn.png')">
            <h5>RESERVATION</h5>
            <h5>LIKE</h5>
          </div>
        </div>
        <button class="cart-back"><router-link class="nav-link" to="/">Back</router-link></button>
      </div>
    </div>
    <br>
  </div>

</template>

<script>
export default {
  name: 'CartList',
  data(){
    return{
      content: []
    }
  },
  methods: {
    buyCartLink () {
      window.location.href = `http://localhost:8081/cart/buy/${this.content.mcode}`
    },
    shareCartLink () {
      window.location.href = `http://localhost:8081/cart/share/${this.content.mcode}`
    },
    reservationCartLink () {
      window.location.href = `http://localhost:8081/cart/reservation/${this.content.mcode}`
    }
  },
  created(){
    this.content= this.$store.state.loginState
  },
}
</script>

<style scoped>
.cart{
  width: 100%;
  height: 100%;
  padding-bottom: 4%;
}
.cart-back{
  margin-top: 10% ;
  margin-left: 47%;
  padding: 1%;
  background-color: #ffffff;
  color: #00a3de;
  font-weight: bolder;
  border-color: #00a3de;
  border-radius: 1em;
  font-size: 1em;
}
.cart-back:hover{
  color: white;
  background-color: #b2e2fd;
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