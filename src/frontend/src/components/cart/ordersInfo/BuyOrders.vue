<template>
  <div class="buy-orders">
    <h1>Buy Orders</h1>
    <div class="order-list">
      <span>
      <table class="table table-striped">
        <thead>
        <tr>
          <th>주문코드</th>
          <th>상품명</th>
          <th>수량</th>
          <th>주문금액</th>
          <th>결제날짜</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(order, index) in orders" :key="order.orderCode">

          <td>{{ order.orderCode }}</td>
          <td>{{ this.menus[index].menu.menuname }}</td>
          <td>{{ this.menus[index].orderMenuCount }}개</td>
          <td>{{ order.orderPrice }}</td>
          <td>{{ order.paymentDate[0]}}년 {{ order.paymentDate[1]}}월 {{ order.paymentDate[2]}}일</td>
        </tr>
        <!-- PathVariable 을 위해서는 router-link 작성 -->
        <!--      <router-link :to="{name: 'productDetail', params: { menuid:product.menuid }}"></router-link>-->
        </tbody>
      </table>
    </span>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "BuyOrders",
  data(){
    return{
      content: [],
      orders: [],
      menus: [],
    }
  },
  created(){
    this.content= this.$store.state.loginState
    this.DataList()
  },
  methods:{
    DataList() {
      axios.get('http://localhost:9002/api/ordersList/buyOrders/' + this.content.mcode)
          .then(res => {
            axios.get('http://localhost:9002/api/ordersList/buyOrderMenu/' + this.content.mcode)
                .then(res2 =>{
                this.orders = res.data;
                this.menus = res2.data;

                console.log(this.menus);
            }).catch(e2 =>{
              console.log(e2)
            })
          })
          .catch(e => {
            console.log(e);
          })
    }

  }
}
</script>

<style scoped>
.buy-orders{
  width: 100%;
  height: 100%;
  margin-top: 2%;
  margin-left: 3%;
}
.order-list{
  width: 40%;
  height: 100%;
  margin-top: 2%;
  margin-left: 3%;
}
</style>