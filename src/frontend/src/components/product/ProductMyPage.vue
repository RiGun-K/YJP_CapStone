<template>
  <div class="back">
    <div class="frame">
  <div class="orders">
    <h1>판매 정보</h1>
    <div class="orders-btn-list">
      <button class="shareBtn" @click="buyOrders" style="margin-right: 15px">구매 상품</button>
      <button class="shareBtn" @click="shareOrders" style="margin-right: 15px">대여 상품</button>
      <button class="shareBtn" @click="reservationOrders" style="margin-right: 15px">캠핑장 상품</button>
    </div>
  </div>
    </div>
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
      window.location.href = 'http://localhost:8081/SaleMyBuy/' + this.Content.mcode
    },
    shareOrders(){
      window.location.href = 'http://localhost:8081/SaleMyRental/' + this.Content.mcode
    },
    reservationOrders(){
      window.location.href = 'http://localhost:8081/SaleMyCamping/' + this.Content.mcode
    }
  },
}
</script>

<style scoped>
.orders{
  width: 100%;
  height: 100%;
  margin-top: 2%;
  margin-left: 3%;
}
.orders-btn-list{
  margin-top: 3%;
  margin-left: 3%;
}
.shareBtn{
  width: 10%;
  padding: 1%;
  background-color: #ffffff;
  color: #00a3de;
  font-weight: bolder;
  border-color: #00a3de;
  border-radius: 1em;
  font-size: 1.5em;
}
.back {
  position: absolute;
  background-image: url(@/assets/campwall2.webp);
  background-size: 100%;
  width: 100%;
  height: 200%;
  background-repeat: repeat-y;
  padding: auto;
}
.frame {
  border: none;
  border-radius: 50px;
  background-color: rgb(247, 246, 230);
  width: 86%;
  height: 800px;
  margin: auto;
}
</style>