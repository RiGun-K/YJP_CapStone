<template>
  <div class="main">
  <img :src="mainImage">
  </div>

  <div class="infoter">
    <div class="sub_title_wrap2">

      <div class="card">
        <div class="card-body">
          <h3>글램핑,기타,당일피크닉,전체,카라반,차박,캠핑,팬션</h3>
        </div>
        <div class="card-body">
          ㅇ
        </div>
        <div class="card-body">
          ㅇ
        </div>
        <div class="card-body">
          ㅇ
        </div>
        <div class="card-body">
          ㅇ
        </div>
      </div>


      <br>
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
      <button @click="search()" class="w-btn-neon2" type="button">검색</button>
    </div>

    <section>
      <div class="search">
        <input type="text" v-model="searchCamping" placeholder="검색어를 입력하세요">
        <button class="w-btn-outline w-btn-blue-outline" type="button" @click="campingFilter(this.searchCamping)">검색</button>
      </div>
    </section>
    </div>

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


    <div class="listBody">
      <div v-for="(product,index) in list" :key="product.id"
           :item="product" @click="toDetail(product)" class="listObj">
        <div class="card">
          <div class="card-body">
            <img :src="'/api/product_detail_images/' + product.filename" alt="...">
          </div>
          <div class="card-body">
            분류 - {{ product.infoterId.infoterName }}
          </div>
          <div class="card-body">
            캠핑장명 - {{ product.campingName }}
          </div>
          <div class="card-body">
            주소 - {{ product.address }}
          </div>
          <div class="card-body">
            조회수 - {{ product.campingViews }}
          </div>
          <div class="card-body">
            객실수 - {{ product.campingDetails.length }}
          </div>
        </div>
        <br>
      </div>
    </div>

  </div>
</template>

<script>
import axios from 'axios'
import ProductList from "@/components/product/ProductList";
import { collapsed, toggleSidebar } from '@/components/cart/Sidebar/state'
import Sidebar from '@/components/cart/Sidebar/Sidebar'
import { sidebarWidth } from '@/components/cart/Sidebar/state'
import SidebarLink from '@/components/cart/Sidebar/SidebarLink'
export default {
  name: 'InfoterList',
  components: { SidebarLink },
  return: {
    ProductList, sidebarWidth
  },
  setup () {
    return { collapsed, toggleSidebar, sidebarWidth }
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
      searchCamping: '',
      mainImage: require('@/assets/camp3.jpg'),
      carImage: require('@/assets/차박.png')
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
      if(product.campingDetails.length > 0) {
        axios.post('/api/Camping_countView', {a: product.campingId})
            .then((res) => {
              console.log("조회수 증가됨" + res.data);
              this.$router.push({
                path: `/infoter/infoterList/${product.campingId}`
              })
            })
            .catch(e => {
              console.log(e)
            })

      } else {
        alert("객실이 없습니다.")
      }

    },
    cam(index) {
      axios.get('/api/product_detail_camping/' + index)
          .then((res) => {
            console.log(res.data);
            this.list = res.data;
            if (this.list.length == 0) {
              alert("캠핑장이 없습니다.")
              window.location.href = 'http://localhost:8081/infoter'
            }

          })
          .catch(e => {
            console.log(e)
          })
    },

    campingFilter(index) {
      if (this.searchCamping == '') {
        this.goData();
      } else {
        axios.get('/api/search_CampingList', { params: { searchCamping: index }})
         .then((res) => {
           this.list = res.data;
           if (this.list.length == 0) {
             alert("해당 상품은 존재하지 않습니다.")
             window.location.href = 'http://localhost:8081/infoter'
           }
           })
         .catch(e => {
           console.log(e)
         })
      }
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
@import url("https://fonts.googleapis.com/css?family=Poppins:200,300,400,500,600,700,800,900&display=swap");
.infoter{
  width: 60%;
  height: 100%;
  margin-top: 1%;
  margin-left: 21%;
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
.main img {
  width: 100%;
  height: 300px;
  object-fit: cover;
}
.mainArea {
  width: 50px;
  height: 100px;
  margin-left: 30px;
}


img {
  width : 10%;
  height: 10%;
}

.search {
  width: 300px;
  height: 100px;
  margin-left: 75%;
}
.search input {
  width: 80%;
  height: 30px;
  font-size: 18px;
  border: none;
  border-bottom: 1px black solid;
}

/*.search button {*/
/*  font-size: 18px;*/
/*  border: none;*/
/*  background-color: green;*/
/*  width: 50px;*/
/*  height: 30px;*/
/*  border-radius: 15px;*/
/*  color: #fff;*/
/*  cursor: pointer;*/
/*  margin-left: 85%;*/
/*  margin-top: -30%;*/
/*}*/

.campingkindimage {
  width:380px;
  height:320px;
  margin:0 auto;  /* 양 사이즈 간격  */
}
.image-thumbnail{
  float: left;
}

img {
  width: 40%;
  height: 40%;
}

.card {
  width: 120%;
  margin: auto;
  margin-left: -10%;
}

.card-body {
  overflow: hidden;
}
.card-body img {
  transition: all 0.2s linear;
}
.card-body:hover img {
  transform: scale(1.5);
}

.rotate-180 {
  transform: rotate(180deg);
  transition: 0.2s linear;
}
.w-btn-neon2 {
  position: relative;
  border: none;
  min-width: 90px;
  min-height: 40px;
  background: linear-gradient(
      90deg,
      rgba(129, 230, 217, 1) 0%,
      rgba(79, 209, 197, 1) 100%
  );
  border-radius: 1000px;
  color: darkslategray;
  cursor: pointer;
  box-shadow: 12px 12px 24px rgba(79, 209, 197, 0.64);
  font-weight: 700;
  transition: 0.3s;
}
.w-btn-neon2:hover {
  transform: scale(1.2);
}

.w-btn-neon2:hover::after {
  content: "";
  width: 30px;
  height: 30px;
  border-radius: 100%;
  border: 6px solid #00ffcb;
  position: absolute;
  z-index: -1;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  animation: ring 1.5s infinite;
}
@keyframes ring {
  0% {
    width: 30px;
    height: 30px;
    opacity: 1;
  }
  100% {
    width: 300px;
    height: 300px;
    opacity: 0;
  }
}
.w-btn-blue-outline {
  border: 3px solid #6aafe6;
  color: #6e6e6e;
}
.w-btn-blue-outline:hover {
  background-color: #6aafe6;
  color: #d4dfe6;
}
.w-btn-outline {
  position: relative;
  padding: 12px 25px;
  border-radius: 15px;
  font-family: "paybooc-Light", sans-serif;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
  text-decoration: none;
  font-weight: 600;
  transition: 0.25s;
  right: -230px;
  top: -50px;
}
</style>
