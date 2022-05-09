<template>
  <ProductPage></ProductPage>
  <br>
  <ul class="nav justify-content-center">
    <li class="nav-item">
      <a class="nav-link active" href="BuyProductList">구매</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="RentalProductList">렌탈</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="CampingProductList">캠핑장</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="RoomProductList">객실</a>
    </li>
  </ul>
  <br>
  <h1>객실 조회</h1>
  <div>
    <br>
    <table class="table table-striped">
      <thead>
      <tr>
        <th>분류</th>
        <th>메뉴명</th>
        <th>수량</th>
        <th>등록일자</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="RoomList in list"
          :key="RoomList.id"
          :item="RoomList" @click="showInfo(RoomList)" style="cursor:pointer;">
        <th scope="row">{{ RoomList.kindid.kindname }}</th>
        <td>{{ RoomList.rentalName }}</td>
        <td>{{ RoomList.rentalStock }}</td>
        <td>{{ RoomList.savedTime }}</td>
      </tr>
      <!--      <router-link :to="{name: 'MyProductDetail', params: { menuid:myProduct.menuid }}"></router-link>-->
      </tbody>
    </table>
  </div>

  <br>
  <nav aria-label="page">
    <ul class="pagination pagination-lg">
      <li class="page-item disabled">
        <a class="page-link">Previous</a>
      </li>
      <li class="page-item active" aria-current="page">
        <a class="page-link" href="#">1</a>
      </li>
      <li class="page-item"><a class="page-link" href="#">2</a></li>
      <li class="page-item"><a class="page-link" href="#">3</a></li>
      <li class="page-item">
        <a class="page-link" href="#">Next</a>
      </li>
    </ul>
  </nav>
</template>

<script>
import axios from 'axios'
import store from "@/store";
import ProductPage from "@/components/product/ProductPage";

export default {
  name: "RoomProductList",
  components: { ProductPage },

  created() {
    this.goMyData()
  },
  data() {
    return {
      selected: false,
      list: [],
      myProduct: '',
    }
  },
  methods: {
    goMyData() {
      this.user = store.getters.getLoginState.loginState
      console.log("현재 사용자 아이디는" + this.user)
      axios.get('http://localhost:9002/api/Room_List/'+this.user)
          .then((res) => {
            console.log(res.data);

            this.list = res.data;
          })
          .catch(e => {
            console.log(e)
          })
    },
    showInfo(RoomList) {
      this.$router.push({
        path: `/BuyProductDetail/${RoomList.roomId}`
      })
    }

  }
}
</script>

<style scoped>
.pagination {
  display: flex;
  padding-left: 0;
  list-style: none;
  justify-content: center
}
</style>