<template>
  <div>
    <h1>Orders</h1>
    <div>
      <button @click="buyOrders">구매 주문정보</button>
      <button @click="shareOrders">대여 주문정보</button>
      <button @click="reservationOrders">예약 주문정보</button>
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
      window.location.href = 'http://localhost:8081/ordersList/buyOrders/' + this.Content.mcode
    },
    shareOrders(){
      window.location.href = 'http://localhost:8081/ordersList/shareOrders'
    },
    reservationOrders(){
      window.location.href = 'http://localhost:8081/ordersList/reservationOrders'
    }
  },
}
</script>

<style scoped>

</style>