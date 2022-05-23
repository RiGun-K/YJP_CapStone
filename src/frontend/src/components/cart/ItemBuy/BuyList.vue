<template>
<!--  <Sidebar />-->
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
    <SidebarLink class="sidebar-link" icon="" to="/itemBuy" @click="menu(3)">캠핑패키지</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/itemBuy" @click="menu(4)">텐트</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/itemBuy" @click="menu(5)">타프</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/itemBuy" @click="menu(6)">테이블</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/itemBuy" @click="menu(7)">체어</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/itemBuy" @click="menu(8)">침낭</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/itemBuy" @click="menu(9)">매트</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/itemBuy" @click="menu(10)">식기</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/itemBuy" @click="menu(11)">버너</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/itemBuy" @click="menu(12)">화로</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/itemBuy" @click="menu(13)">캠핑소품</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/itemBuy" @click="menu(14)">등산용품</SidebarLink>

    <h5><span v-if="collapsed">
      </span>
      <span v-else @click="menu(2)">식품</span></h5>
    <SidebarLink class="sidebar-link" icon="" to="/itemBuy" @click="menu(15)">식품패키지</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/itemBuy" @click="menu(16)">육류</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/itemBuy" @click="menu(17)">과일</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/itemBuy" @click="menu(18)">음료</SidebarLink>

    <span
        class="collapse-icon"
        :class="{ 'rotate-180': collapsed }"
        @click="toggleSidebar">
          <i class='fas fa-angle-double-left'>  〈〈  </i>
    </span>
  </div>

  <!-- !-->
  <div :style="{ 'margin-left': sidebarWidth }">
    <router-view />
  </div>
  <div class="ItemBuy">

    <h2>Item Buy</h2>
    <span class="btn-group" role="group" aria-label="Basic radio toggle button group">
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
  </span>


    <section>
        <br>
        <div class="search">
          <input type="text" v-model="searchBuy" placeholder="검색어를 입력하세요">
          <button @click="buyFilter(this.searchBuy)">검색</button>
        </div>
    </section>

    <div class="listBody">
      <br>
      <h2> 용품 구매 </h2>
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
            상품명 - {{ product.buyName }}
          </div>
          <div class="card-body">
            가격 - {{ product.buyPrice }}
          </div>
          <div class="card-body">
            조회수 - {{ product.buyViews }}
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
import ProductList from "@/components/product/ProductList";
import { collapsed, toggleSidebar } from '@/components/cart/Sidebar/state'
import SidebarLink from '@/components/cart/Sidebar/SidebarLink'

export default {
  name: 'ItemBuy',
  props: {},
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
      searchBuy: '',
    }
  },
  methods: {
    goData() {
      axios.get('http://localhost:9002/api/product_BuyList')
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
      axios.post('/api/Buy_countView', {a: product.buyId})
          .then((res) => {
            console.log("조회수 증가됨" + res.data);
            this.$router.push({
              path: `/itemBuy/buyList/${product.buyId}`
            })
          })
          .catch(e => {
            console.log(e)
          })

    },

    menu(index) {
      axios.get('/api/product_detail_buy/' + index)
        .then((res) => {
          console.log(res.data);
          this.list = res.data;
          if ( this.list.length == 0) {
            alert("해당 상품의 재고가 없습니다.")
            window.location.href = 'http://localhost:8081/itemBuy'
          }
        })
        .catch(e => {
          console.log(e)
        })
    },
    buyFilter(tag) {
      if (this.searchBuy == '') {
        this.goData();
      } else {
        console.log("접근")
        axios.get('/api/search_BuyList', { params: { searchBuy: tag }})
          .then((res) => {
            // res.data.map((item) => {
            //   this.list.push(item);
            // })
            this.list = res.data;
            if (this.list.length == 0) {
              alert("해당 상품은 존재하지 않습니다.")
            }
            console.log(this.list);
          })
          .catch(e => {
            console.log(e)
          })
      }

    }

  }

}
</script>

<style scoped>
.ItemBuy{
  width: 100%;
  height: 100%;
  margin-top: 3%;
}
.ItemBuy .btn-group{
  margin-top: 2%;
  width: 25%;
}
/*.buy-btn-group{*/
/*  text-align: center;*/
/*  margin: 30%;*/
/*  width: 50%;*/
/*}*/
/*.buy-btn-group .buy-list-btn{*/
/*  margin-left: 2%;*/
/*  margin-right: 3%;*/
/*  width: 12%;*/
/*  padding: 1.5%;*/
/*  background-color: #ffffff;*/
/*  font-weight: bolder;*/
/*  border-color: #00a3de;*/
/*  border-radius: 1em;*/
/*  color: #00a3de;*/
/*}*/
/*.buy-list-btn:hover{*/
/*  color: white;*/
/*  background-color: #b2e2fd;*/
/*}*/

.buy-list{
  width: 50%;
  height: 100%;
  display: flex;
  margin-top: 5%;
}
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


:root {
  --sidebar-bg-color: #e6f4ff;
  --sidebar-item-hover: #b2e2fd;
}
</style>

<style scoped>
.sidebar {
  color: black;
  background-color: var(--sidebar-bg-color);
  float: left;
  z-index: 1;
  height: 100%;
  left: 0;
  bottom: 0;
  padding-bottom: 50%;
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

/* 글씨체 변경 */
.listBody {
  font-family: Consolas, monospace;
}
.listObj {
  width: 80%
}

/* 사진 드래그 시 이벤트 */

.card-body img {
  transition: all 0.2s linear;
}
.card-body:hover img {
  transform: scale(3.0);
}

</style>