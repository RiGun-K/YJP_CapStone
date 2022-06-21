<template>
  <div class="back">
    <div class="frame">
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
  <h1>캠핑장 조회</h1>
  <br>
  <h2 div="campingKind">
    <ul class="nav justify-content-center">
      <li class="nav-item">
        <a class="nav-link active" @click="goMyData">전체</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" @click="Infoter(1)">캠핑장</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" @click="Infoter(2)">카라반</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" @click="Infoter(3)">글램핑</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" @click="Infoter(4)">팬션</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" @click="Infoter(5)">차박</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" @click="Infoter(6)">당일 피크닉</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" @click="Infoter(7)">기타</a>
      </li>
    </ul>
  </h2>

  <div>
    <br>
    <table class="table table-striped">
      <thead>
      <tr>
        <th>분류</th>
        <th>캠핑장명</th>
        <th>가용 객실 수</th>
        <th>등록일자</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="CampingList in list"
          :key="CampingList.id"
          :item="CampingList" @click="showInfo(CampingList)" style="cursor:pointer;">
        <th scope="row">{{ CampingList.infoterId.infoterName }}</th>
        <td>{{ CampingList.campingName }}</td>
        <td>{{ CampingList.campingDetailState }}</td>
        <td>{{ CampingList.savedTime }}</td>
      </tr>
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
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import store from "@/store";
import ProductPage from "@/components/product/ProductPage";

export default {
  name: "CampingProductList",
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
      axios.get('http://localhost:9002/api/Camping_List/'+this.user)
          .then((res) => {
            console.log(res.data);

            this.list = res.data;
          })
          .catch(e => {
            console.log(e)
          })
    },
    showInfo(CampingList) {
      this.$router.push({
        path: `/CampingProductDetail/${CampingList.campingId}`
      })
    },
    Infoter(index) {
      axios.get('/api/product_detail_camping/' + index)
          .then((res) => {
            console.log(res.data);
            this.list = res.data;
            if (this.list.length == 0) {
              alert("등록하신 캠핑장이 없습니다.")
              window.location.href = 'http://localhost:8081/CampingProductList'
            }

          })
          .catch(e => {
            console.log(e)
          })
    },

  }
}
</script>

<style scoped>
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
.pagination {
  display: flex;
  padding-left: 0;
  list-style: none;
  justify-content: center
}
</style>