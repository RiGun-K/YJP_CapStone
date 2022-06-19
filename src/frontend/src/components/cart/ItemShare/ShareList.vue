<template>
  <div class="sidebar" :style="{ width: sidebarWidth }">
    <h1>
      <span v-if="collapsed">
        <div>C</div>
      </span>
      <span v-else >Category</span>
    </h1>

    <h5><span v-if="collapsed">
      </span>
      <span v-else @click="menu(1)">캠핑용품</span></h5>
    <SidebarLink class="sidebar-link" icon="" to="/itemShare" @click="menu(3)">캠핑패키지</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/itemShare" @click="menu(4)">텐트</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/itemShare" @click="menu(5)">타프</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/itemShare" @click="menu(6)">테이블</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/itemShare" @click="menu(7)">체어</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/itemShare" @click="menu(8)">침낭</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/itemShare" @click="menu(9)">매트</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/itemShare" @click="menu(10)">식기</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/itemShare" @click="menu(11)">버너</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/itemShare" @click="menu(12)">화로</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/itemShare" @click="menu(13)">캠핑소품</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/itemShare " @click="menu(14)">등산용품</SidebarLink>

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

  <div class="ItemBuy">

    <h2>Item Buy</h2>
    <span class="btn-group" role="group" aria-label="Basic radio toggle button group">
    <input type="radio" class="btn-check" name="btnradio" id="btnradio1" autocomplete="off" checked>
    <label class="btn btn-outline-primary" for="btnradio1" @click="goData">전체</label>

    <input type="radio" class="btn-check" name="btnradio" id="btnradio2" autocomplete="off">
    <label class="btn btn-outline-primary" for="btnradio2" @click="RentalByLatest">최신순</label>

    <input type="radio" class="btn-check" name="btnradio" id="btnradio3" autocomplete="off">
    <label class="btn btn-outline-primary" for="btnradio3" @click="RentalByViews">인기순</label>

  </span>

    <section>
      <br>
      <div class="search">
        <input type="text" v-model="searchRental" placeholder="검색어를 입력하세요">
        <button @click="rentalFilter(this.searchRental)">검색</button>
      </div>
    </section>

    <div class="listBody">
      <br>
      <h2> 용품 렌탈 </h2>
      <div v-for="(product,index) in list" :key="product.id"
           :item="product" @click="toDetail(product)" class="listObj">
        <div class="card">
          <div class="card-body">
            <img :src="'/api/product_detail_images/' + product.filename" alt="...">
          </div>
          <div class="card-body">
            분류 - {{ product.kindid.kindname }}
          </div>
          <div class="card-body">
            상품명 - {{ product.rentalName }}
          </div>
          <div class="card-body">
            조회수 - {{ product.rentalViews }}
          </div>
        </div>
        <br>
      </div>
    </div>

  </div>

</template>

<script>
import Sidebar from '@/components/cart/Sidebar/Sidebar'
import { sidebarWidth } from '@/components/cart/Sidebar/state'
import axios from 'axios'
import ProductList from "@/components/product/ProductList"
import { collapsed, toggleSidebar } from '@/components/cart/Sidebar/state'
import SidebarLink from '@/components/cart/Sidebar/SidebarLink'
// import { reactive } from 'vue'
export default {
  name: 'ItemBuy',
  components: { SidebarLink },
  return: {
    sidebarWidth, ProductList
  },
  setup () {
    return { collapsed, toggleSidebar, sidebarWidth }
  },
  created() {
    this.goData()
  },
  data() {
    return {
      selected: false,
      list: [],
      product: '',
      searchRental: '',
    }
  },
  methods: {
    goData() {
      axios.get('http://localhost:9002/api/product_RentalList')
          .then((res) => {
            console.log(res.data);
            this.list = res.data;
          })
          .catch(e => {
            console.log(e)
          })
    },

    toDetail(product){
      axios.post('/api/Rental_countView', {a: product.rentalId})
          .then((res) => {
            console.log("조회수 증가됨" + res.data);
            this.$router.push({
              path: `/itemShare/rentalList/${product.rentalId}`
            })
          })
          .catch(e => {
            console.log(e)
          })

    },

    menu(index) {
      axios.get('/api/product_detail_rental/' + index)
          .then((res) => {
            console.log(res.data);
            this.list = res.data;
            if ( this.list.length == 0) {
              alert("해당 상품의 재고가 없습니다.")
              window.location.href = 'http://localhost:8081/itemShare'
            }
          })
          .catch(e => {
            console.log(e)
          })
    },
    rentalFilter(tag) {
      if (this.searchRental == '') {
        this.goData();
      } else {
        console.log("접근")
        axios.get('/api/search_RentalList', { params: { searchRental: tag }})
            .then((res) => {
              this.list = res.data;
              if (this.list.length == 0) {
                alert("해당 상품은 존재하지 않습니다.")
                window.location.href = 'http://localhost:8081/itemShare'
              }
            })
            .catch(e => {
              console.log(e)
            })
      }

    },
    RentalByLatest() {
      axios.get('/api/product_ByRentalLatest')
          .then((res) => {
            console.log(res.data);
            this.list = res.data;
          })
          .catch(e => {
            console.log(e)
          })
    },
    RentalByViews() {
      axios.get('/api/product_ByRentalViews')
          .then((res) => {
            console.log(res.data);
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
.card {
  width: 50%;
}

.ItemBuy{
  width: 100%;
  height: 100%;
  margin-top: 3%;
}
.ItemBuy .btn-group{
  margin-top: 2%;
  width: 25%;
}
.buy-list{
  width: 50%;
  height: 100%;
  display: flex;
  margin-top: 5%;
}
.sidebar {
  color: black;
  background-color: var(--sidebar-bg-color);
  float: left;
  z-index: 1;
  height: 100%;
  left: 0;
  bottom: 0;
  padding-bottom: 100%;
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
.search {
  text-align: right;
  width: 90%;
}
.listBody {
  font-family: Consolas, monospace;
}
.listObj {
  width: 80%
}
:root {
  --sidebar-bg-color: #e6f4ff;
  --sidebar-item-hover: #b2e2fd;
}
.card-body img {
  transition: all 0.2s linear;
}
.card-body:hover img {
  transform: scale(2.0);
}

/* */

img {
  width: 10%;
  height: 10%;
}

.search input {
  width: 20%;
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