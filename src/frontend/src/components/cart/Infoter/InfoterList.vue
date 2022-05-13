<template>
  <div class="infoter">
    <h2>인포터</h2>
    <div class="btn-group" role="group" aria-label="Basic radio toggle button group">
      <input type="radio" class="btn-check" name="btnradio" id="btnradio1" autocomplete="off" checked>
      <label class="btn btn-outline-primary" for="btnradio1">전체</label>

      <input type="radio" class="btn-check" name="btnradio" id="btnradio2" autocomplete="off">
      <label class="btn btn-outline-primary" for="btnradio2">최신순</label>

      <input type="radio" class="btn-check" name="btnradio" id="btnradio3" autocomplete="off">
      <label class="btn btn-outline-primary" for="btnradio3">인기순</label>

      <input type="radio" class="btn-check" name="btnradio" id="btnradio4" autocomplete="off">
      <label class="btn btn-outline-primary" for="btnradio4">낮은 가격순</label>

      <input type="radio" class="btn-check" name="btnradio" id="btnradio5" autocomplete="off">
      <label class="btn btn-outline-primary" for="btnradio5">높은 가격순</label>
    </div>

    <br>
    <br>
    <span class="buy-list">
      <table class="table table-striped">
        <thead>
        <tr>
          <th>분류</th>
          <th>상품명</th>
          <th>주소</th>
          <th>등록일자</th>
          <th>조회수</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="product in list"
            :key="product.id"
            :item="product" @click="toDetail(product)" style="cursor:pointer;">

          <td>{{ product.infoterId.infoterName }}</td>
          <td>{{ product.campingName }}</td>
          <td>{{ product.address }}</td>
          <td>{{ product.savedTime }}</td>
          <td>51</td>
        </tr>
        <!-- PathVariable 을 위해서는 router-link 작성 -->
        <!--      <router-link :to="{name: 'productDetail', params: { menuid:product.menuid }}"></router-link>-->
        </tbody>
      </table>
    </span>

    <div class="infoter-btn-group">
      <button class="infoter-list-btn" @click="ReservationNowBtn">Reservation Now</button>
      <button class="infoter-list-btn" @click="ReservationAddCart">Add to Cart</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import ProductList from "@/components/product/ProductList";
export default {
  name: 'InfoterList',
  return: {
    ProductList
  },
  created() {
    this.goData()
  },
  data() {
    return {
      selected: false,
      list: [],
      product: '',
    }
  },
  methods: {
    goData() {
      axios.get('http://localhost:9002/api/product_CampingList')
          .then((res) => {
            console.log(res.data);
            this.list = res.data;
          })
          .catch(e => {
            console.log(e)
          })
    },
    // path로 받기
    toDetail(product){
      this.$router.push({
        path: `/infoter/infoterList/${product.campingId}`
      })
    },
    ReservationNowBtn () {
      window.location.href = 'http://localhost:8081/infoter/infoterNow'
    },
    ReservationAddCart () {
      window.location.href = 'http://localhost:8081/cart'
    }
  }
}
</script>

<style scoped>
.infoter{
  width: 90%;
  height: 100%;
  margin-top: 3%;
  margin-left: 5%;
}
.infoter button{
  margin: 5%;
}
.infoter .btn-group{
  margin-top: 2%;
  margin-left: 75%;
  width: 25%;
}
.infoter-btn-group{
  text-align: center;
  margin: 30%;
  width: 50%;
}
.infoter-btn-group .infoter-list-btn{
  margin-left: 2%;
  margin-right: 3%;
  width: 17%;
  padding: 1.5%;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
  border-radius: 1em;
}
.infoter-list-btn:hover{
  color: white;
  background-color: #b2e2fd;
}
</style>