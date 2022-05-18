<template>
  <div class="infoter">
    <h2>캠핑장 예약</h2>
    <br>
    <div class="campingkindimage">
    <img src="@/assets/전체.png" class="card-img-top" alt="..." @click="goData">
    <img src="@/assets/캠핑.png" class="card-img-top" alt="..." @click="cam(1)">
    <img src="@/assets/카라반.png" class="card-img-top" alt="..." @click="cam(2)">
    <img src="@/assets/글램핑.png" class="card-img-top" alt="..." @click="cam(3)">
    <img src="@/assets/팬션.png" class="card-img-top" alt="..." @click="cam(4)">
    <img src="@/assets/차박.png" class="card-img-top" alt="..." @click="cam(5)">
    <img src="@/assets/당일피크닉.png" class="card-img-top" alt="..." @click="cam(6)">
      <img src="@/assets/기타.png" class="card-img-top" alt="..." @click="cam(7)">
    </div>
    <br>
    <br>
    <section>
    <form action="https://search.naver.com/search.naver">
      <div class="search">
        <input type="text" name="query" value="">
        <button type="submit">검색</button>
      </div>
    </form>
    </section>


    <div class="btn-group" role="group" aria-label="Basic radio toggle button group">
      <input type="radio" class="btn-check" name="btnradio" id="btnradio1" autocomplete="off" checked>
      <label class="btn btn-outline-primary" for="btnradio1" @click="goData">전체</label>

      <input type="radio" class="btn-check" name="btnradio" id="btnradio2" autocomplete="off">
      <label class="btn btn-outline-primary" for="btnradio2" @click="orderBy('latest')">최신순</label>

      <input type="radio" class="btn-check" name="btnradio" id="btnradio3" autocomplete="off">
      <label class="btn btn-outline-primary" for="btnradio3" @click="orderBy('latestd')">인기순</label>

      <input type="radio" class="btn-check" name="btnradio" id="btnradio4" autocomplete="off">
      <label class="btn btn-outline-primary" for="btnradio4">낮은 가격순</label>

      <input type="radio" class="btn-check" name="btnradio" id="btnradio5" autocomplete="off">
      <label class="btn btn-outline-primary" for="btnradio5">높은 가격순</label>
    </div>

    <h3>지역분류</h3>
    <div class="searchDiv">
      <select v-model="bigPick" @change="bigCheck(bigPick)">
        <option value="0">전국</option>
        <option v-for="big in bigRound" :value="big.areaId">{{ big.areaName }}</option>
      </select>
      <select v-model="smallPick">
        <option value="0">전체</option>
        <option v-for="small in smallRound" :value="small.areaId">{{ small.areaName }}</option>
      </select>
      <button @click="search()">검색</button>
    </div>

    <span class="buy-list">
      <table class="table table-striped">
        <thead>
        <tr>
          <th>분류</th>
          <th>상품명</th>
          <th>주소</th>
          <th>등록일자</th>
          <th>조회수</th>
          <th>이미지</th>
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
          <td>{{ product.campingViews }}</td>
          <td><img :src="'/api/product_detail_images/' + product.filename"> </td>
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

<!--    <img src="@\assets\595cf686-1619-43f3-8b76-e32c9965c14e_camp2.jpg"/>-->

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
    this.goData(),
    axios.get('/api/campingRound')
        .then(res => {
          console.log(res.data)
          this.bigRound = res.data
        })
        .catch(err => {
          console.log(err)
        })

  },
  data() {
    return {
      selected: false,
      list: [],
      product: '',

      bigRound: [],
      smallRound: [],
      bigPick: 0,
      smallPick: 0,
    }
  },
  methods: {
    goData() {
      axios.get('/api/product_CampingList')
          .then((res) => {
            console.log(res.data);
            this.list = res.data;

            axios.get('/api/product_detail_images/' + this.product.filename )
                .then(res => {
                  console.log("이미지 불러오기 성공");
                })
                .catch(e => {
                  console.log("이미지 불러오기 실패" + e);
                })
          })
          .catch(e => {
            console.log(e)
          })
    },

    bigCheck(index) {
      if (index == '0') {
        this.smallRound = []
        this.smallPick = 0
      } else {
        axios.get('/api/campingSmallRound/' + index)
            .then(res => {
              console.log(res.data)
              this.smallRound = res.data
              this.smallPick = 0
            })
            .catch(err => {
              console.log(err)
            })
      }

    },
    search() {
      if (this.bigPick == "0" && this.smallPick == "0") {
        this.goData();
      } else if (this.bigPick != "0" && this.smallPick == '0') {
        console.log("AREA_ID 는" + this.bigPick + "입니다..");
        axios.get('/api/product_detail_campingDetailArea/' + this.bigPick)
            .then((res) => {
              console.log(res.data);
              this.list = res.data;
            })
            .catch(e => {
              console.log(e)
            })
      } else if (this.bigPick != "0" && this.smallPick != '0' ) {
        console.log("AREA_ID 는" + this.smallPick + "입니다.");
        axios.get('/api/product_detail_campingArea/' + this.smallPick)
            .then((res) => {
              console.log(res.data);
              this.list = res.data;
            })
            .catch(e => {
              console.log(e)
            })
      }



    },

    // 상세페이지 접속
    toDetail(product){
      console.log(product.campingId);
      axios.post('/api/Camping_countView', { a: product.campingId })
          .then((res) => {
            console.log("조회수 증가됨" + res.data);
            this.$router.push({
              path: `/infoter/infoterList/${product.campingId}`
            })
          })
          .catch(e => {
            console.log(e)
          })

    },
    cam(index) {
      axios.get('/api/product_detail_camping/' + index)
          .then((res) => {
            console.log(res.data);
            this.list = res.data;
          })
          .catch(e => {
            console.log(e)
          })
    },

    orderBy: function (orderBy) {
      if(orderBy == 'latest') {
        this.list.sort(function (a, b) {
          return b.latest - a.latest;
        });

      } else if (orderBy == 'latestd') {
        this.list.sort(function (a, b) {
          return b.latestd - a.latestd;
        })
      }
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


.table table-striped {
  width : 30%;
  height: 30%;
}
.buy-list {
  width : 20%;
  height: 20%;
}



img {
  width : 10%;
  height: 10%;
}

.search {
  width: 300px;
  height: 100px;
}
.search input {
  width: 80%;
  height: 30px;
  font-size: 18px;
  border: none;
  border-bottom: 1px black solid;
}

.search button {
  font-size: 18px;
  border: none;
  background-color: green;
  width: 50px;
  height: 30px;
  border-radius: 15px;
  color: #fff;
  cursor: pointer;
}
</style>