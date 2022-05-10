<template>
  <ProductPage></ProductPage>
  <br>
  <h2>캠핑장 및 객실 상세페이지</h2>
  <h3>캠핑장 : {{ this.list.campingName }}</h3>
  <h3>객실수 : {{ this.list.campingDetails.length }}</h3>
  <div class="cart">
    <div class="cart-list">
      <div class="buy-cart-btn" @click="PCamping">
        <img :src="require('@/assets/buyBtn.png')">
        <h5>캠핑장</h5>
        <h5>수정/삭제</h5>
      </div>
      <div class="share-cart-btn" @click="PRoom">
        <img :src="require('@/assets/rentBtn.png')">
        <h5>객실</h5>
        <h5>수정/삭제</h5>
      </div>
      <div class="share-cart-btn" @click="CRoom">
        <img :src="require('@/assets/rentBtn.png')">
        <h5>객실</h5>
        <h5>등록</h5>
      </div>
    </div>
    <button class="cart-back"><router-link class="nav-link" to="/CampingProductList">뒤로가기</router-link></button>
  </div>

</template>

<script>
import axios from 'axios'
import store from "@/store";
import ProductPage from "@/components/product/ProductPage";

export default {
  name: "CampingProductDetail",
  components: { ProductPage },

  created() {
    this.goMyData()
  },
  data() {
    return {
      id: '',
      selected: false,
      list: [],
      myProduct: '',
    }
  },
  methods: {
    goMyData() {
      this.id = this.$route.params.campingId;
      this.user = store.getters.getLoginState.loginState
      console.log("현재 캠핑장 아이디는" + this.id)
      console.log("현재 사용자 아이디는" + this.user)
      axios.get('http://localhost:9002/api/Camping_Detail/'+this.id)
          .then((res) => {
            console.log(res.data);
            this.list = res.data;
          })
          .catch(e => {
            console.log(e)
          })
    },
    PCamping() {
      this.$router.push({
        path: `/CampingProductEdit/${this.list.campingId}`
      })
    },
    // showInfo(CampingList) {
    //   this.$router.push({
    //     path: `/CampingProductDetail/${CampingList.campingId}`
    //   })
    // }
    PRoom() {
      this.$router.push({
        path: `/RoomProductList/${this.list.campingId}`
      })
    },
    CRoom() {
      this.$router.push({
        path: `/RoomProductCreate/${this.list.campingId}`
      })
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