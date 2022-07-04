<template>
  <div class="back">
    <br>
    <div class="frame">
      <div class="orders">
        <h1 style="font-weight: bold; padding: 4%">Orders</h1>
        <div class="orders-btn-list">
          <div class="buy-orders-btn" @click="buyOrders">
            <img :src="require('@/assets/buyBtn.png')">
            <h5>BUY</h5>
            <h5>ORDERS</h5>
          </div>
          <div class="share-orders-btn" @click="shareOrders">
            <img :src="require('@/assets/rentBtn.png')">
            <h5>SHARE</h5>
            <h5>ORDERS</h5>
          </div>
          <div class="reservation-orders-btn" @click="reservationOrders">
            <img :src="require('@/assets/calendarBtn.png')">
            <h5>RESERVATION</h5>
            <h5>ORDERS</h5>
          </div>
        </div>
      </div>
    </div>
    <br>
  </div>
</template>

<script>
export default {
  name: "Orders",
  data(){
    return{
      Content: []
    }
  },
  created(){
    this.Content= this.$store.state.loginState
    console.log(this.Content)
  },
  methods:{
    buyOrders(){
      window.location.href = 'http://localhost:8081/ordersList/buyOrders/' + this.Content.mcode
    },
    shareOrders(){
      window.location.href = 'http://localhost:8081/ordersList/shareOrders/' + this.Content.mcode
    },
    reservationOrders(){
      window.location.href = 'http://localhost:8081/ordersList/reservationOrders/' + this.Content.mcode
    }
  },
}
</script>

<style scoped>
.orders{
  width: 100%;
  height: 100%;
  padding-bottom: 10%;
}
.orders-btn-list{
  display:flex;
  justify-content: center;
  align-items: center;
}
.orders .buy-orders-btn{
  width: 15%;
  border: 1px solid black;
  text-align: center;
  padding: 3%;
  margin: 3%;
}
.orders .buy-orders-btn img{
  width: 50%;
  height: 50%;
}
.orders .buy-orders-btn h5 {
  margin-top: 7%;
}
.orders .share-orders-btn{
  margin: 3%;
  width: 15%;
  border: 1px solid black;
  padding: 3%;
  text-align: center;
}
.orders .share-orders-btn img{
  width: 50%;
  height: 50%;
}
.orders .share-orders-btn h5 {
  margin-top: 7%;
}
.orders .reservation-orders-btn{
  width: 15%;
  margin: 3%;
  border: 1px solid black;
  padding: 3%;
  text-align: center;
}
.orders .reservation-orders-btn img{
  width: 50%;
  height: 50%;
}
.orders .reservation-orders-btn h5 {
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