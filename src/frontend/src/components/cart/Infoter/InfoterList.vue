<template>
  <div class="sidebar" :style="{ width: sidebarWidth }">
    <h1>
      <span v-if="collapsed">
        <div>C</div>
      </span>
      <span v-else >Category</span>
    </h1>

    <h5><span v-if="collapsed">
      </span></h5>
    <button class="w-btn-outline w-btn-red-outline" @click="goData">캠핑장 전체</button>
    <br>
    <button class="w-btn-outline w-btn-red-outline" @click="cam(1)">캠핑</button>
    <button class="w-btn-outline w-btn-red-outline" @click="cam(2)">카라반</button>
    <button class="w-btn-outline w-btn-red-outline" @click="cam(3)">글램핑</button>
    <button class="w-btn-outline w-btn-red-outline" @click="cam(4)">팬션</button>
    <button class="w-btn-outline w-btn-red-outline" @click="cam(5)">차박</button>
    <button class="w-btn-outline w-btn-red-outline" @click="cam(6)">당일 피크닉</button>
    <button class="w-btn-outline w-btn-red-outline" @click="cam(7)">기타</button>

    <span
        class="collapse-icon"
        :class="{ 'rotate-180': collapsed }"
        @click="toggleSidebar">
          <i class='fas fa-angle-double-left'>  〈〈  </i>
      </span>
  </div>

  <div :style="{ 'margin-left': sidebarWidth }">
    <router-view />
  </div>

  <div class="infoter">
    <div class="btn-group" role="group" aria-label="Basic radio toggle button group">
      <input type="radio" class="btn-check" name="btnradio" id="btnradio1" autocomplete="off" checked>
      <label class="btn btn-outline-primary" for="btnradio1" @click="goData">전체</label>

      <input type="radio" class="btn-check" name="btnradio" id="btnradio2" autocomplete="off">
      <label class="btn btn-outline-primary" for="btnradio2" @click="orderByLatest">최신순</label>

      <input type="radio" class="btn-check" name="btnradio" id="btnradio3" autocomplete="off">
      <label class="btn btn-outline-primary" for="btnradio3" @click="orderByView">인기순</label>
    </div>

    <h1>지역분류</h1>
    <div class="searchDiv">
      <select v-model="bigPick" @change="bigCheck(bigPick)">
        <option value="0">전국</option>
        <option v-for="big in bigRound" :value="big.areaId">{{ big.areaName }}</option>
      </select>
      <select v-model="smallPick">
        <option value="0">전체</option>
        <option v-for="small in smallRound" :value="small.areaId">{{ small.areaName }}</option>
      </select>
      <button class="w-btn-neon2"  @click="search()">검색</button>
    </div>


    <section>
      <div class="search">
        <input type="text" v-model="searchCamping" placeholder="검색어를 입력하세요">
        <button @click="campingFilter(this.searchCamping)">검색</button>
      </div>
    </section>


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
            잔여 객실수 - {{ product.campingDetails.length }}
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

    orderByView() {
      axios.get('/api/product_detail_campingManyViews')
          .then((res) => {
            this.list = res.data;
          })
          .catch(e => {
            console.log(e)
          })
    },
    orderByLatest() {
      axios.get('/api/product_detail_campingDesc')
          .then((res) => {
            this.list = res.data;
          })
          .catch(e => {
            console.log(e)
          })
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

.search button {
  font-size: 18px;
  border: none;
  background-color: green;
  width: 50px;
  height: 30px;
  border-radius: 15px;
  color: #fff;
  cursor: pointer;
  margin-left: 85%;
  margin-top: -30%;
}
select {width: 200px; padding: .8em .5em; border: 1px solid #999;font-family: inherit;  no-repeat: 95% 50%; border-radius: 0px; -webkit-appearance: none; -moz-appearance: none;appearance: none;}

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
  width: 50%;
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
.sidebar {
   color: black;
   background-color: var(--sidebar-bg-color);
   float: left;
   z-index: 1;
   height: 100%;
   left: 0;
   bottom: 0;
   padding-bottom: 3%;
   margin-right: 3%;
   transition: 0.3s ease;
   display: flex;
   flex-direction: column;
 }
.sidebar h5{
  margin-left: 10%;
  margin-top: 15%;
  margin-bottom: 3%;
}
.sidebar-link{
  color: black;
}
.collapse-icon{
  position: absolute;
  top: 80% ;
  padding: 0.75em;
  color: black;
  transition: 0.2s linear;
}
.rotate-180 {
  transform: rotate(180deg);
  transition: 0.2s linear;
}

.listObj {
  width: 50%;
  float: left;
  position: relative;
  flex-direction: row;
  margin: 0 auto;
  text-align: center;
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

.w-btn-neon2 {
  position: relative;
  margin-left: 45%;
  border: none;
  min-width: 200px;
  min-height: 50px;
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
@import url("https://fonts.googleapis.com/css?family=Poppins:200,300,400,500,600,700,800,900&display=swap");
.w-btn:hover {
  letter-spacing: 2px;
  transform: scale(1.2);
  cursor: pointer;
}
.w-btn-outline {
  position: relative;
  padding: 15px 30px;
  border-radius: 15px;
  font-family: 'paybooc-Light', sans-serif;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
  text-decoration: none;
  font-weight: 600;
  transition: 0.25s;
}
.w-btn-red-outline {
  border: 3px solid #2093e5;
  color: #6e6e6e;
}
.w-btn-red-outline:hover {
  background-color: #2093e5;
  color: #e1eef6;
}
.w-btn-outline:hover {
  letter-spacing: 2px;
  transform: scale(1.2);
  cursor: pointer;
}
.w-btn-outline:active {
  transform: scale(1.5);
}
</style>
