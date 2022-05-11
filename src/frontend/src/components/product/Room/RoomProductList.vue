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
      <a class="nav-link disabled" href="RoomProductList">객실</a>
    </li>
  </ul>
  <br>
  <h1>객실 조회</h1>
  <div>
    <br>
    <table class="table table-striped">
      <thead>
      <tr>
        <th>객실명</th>
        <th>기능</th>
        <th>가격</th>
        <th>등록일자</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="roomList in list"
          :key="roomList.id"
          :item="roomList" @click="showInfo(roomList)" style="cursor:pointer;">
        <th scope="row">{{ roomList.detailName }}</th>
        <td>{{ roomList.detailFunction }}</td>
        <td>{{ roomList.detailPrice }}</td>
        <td>{{ roomList.savedTime }}</td>
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
      id: '',
      selected: false,
      list: [],
      user: store.getters.getLoginState.loginState,
    }
  },
  methods: {
    goMyData() {
      this.id = this.$route.params.campingId;
      console.log("현재 객실의 캠핑장 아이디는" + this.id)
      axios.get('http://localhost:9002/api/Room_List/' + this.id)
          .then((res) => {
            console.log(res.data);
            this.list = res.data;
          })
          .catch(e => {
            console.log(e)
          })
    },
    showInfo(roomList) {
      this.$router.push({
        path: `/RoomProductDetail/${roomList.detailId}`
      })
      store.commit("setCampingIdState", {
        CampingIdOfRooms:this.id,
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