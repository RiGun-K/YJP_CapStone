<template>
  <br>
  <div class="img-block">
    <img :src="'/api/product_detail_images/' + content.filename" class="card-img-top" alt="...">
    <div class="blocks">
      <h2>ㅎㅇ</h2>
    </div>
  </div>
  <div class="mt-4">
    <b-card-text>
      <div class="content-detail-list">
        <!--        <h2><img :src="'/api/product_detail_images/' + content.filename"></h2><br>-->
        <div class="card" style="width: 18rem;">
          <img :src="'/api/product_detail_images/' + content.filename" class="card-img-top" alt="...">
          <div class="card-body">
            <h5 class="card-title">상품명: {{ this.content.buyName }}</h5>
            <p class="card-text">가격: {{ this.content.buyPrice }}</p>
            <p class="card-text">설명: {{ this.content.buyEx }}</p>
            <p class="count-td"><button class="buy-count-sub" @click="subCount()"> ― </button> {{this.count}} <button class="buy-count-add" @click="addCount()"> ╊ </button></p>
            <a href="#" class="btn btn-primary" @click="buyData">구매</a>
          </div>
        </div>
      </div>
      <div class="d-grid gap-2 d-md-flex justify-content-md-end">
        <b-button type="button" class="btn btn-outline-primary btn-lg"  @click="buyData">구매</b-button>
        <b-button type="button" class="btn btn-outline-primary btn-lg"  @click="putData">찜</b-button>
      </div>
    </b-card-text>
  </div>
  <!--  <h2>상품분류 : {{ this.content.kindid.kindname }}</h2><br>-->
  <!--  <h2>상품명 : {{ this.content.buyName }}</h2><br>-->
  <!--  <h2>상품가격 : {{ this.content.buyPrice }}</h2><br>-->
  <!--  <h2>상품 이미지경로: {{ this.content.filePath }}</h2><br>-->
  <!--  <h2>상품 이미지경로: {{ this.content.filename }}</h2><br>-->

</template>

<script>
import axios from "axios";
export default {
  name: "BuyDetailList",
  created() {
    this.DataList();
  },
  data() {
    return {
      id: '',
      content: [],
      images: [],
      image: require('@/assets/camp1.jpg'),
      // file: this.content.origFilename

      count: 1,
      buyMenuCheckPut: false
    }
  },
  methods: {
    DataList() {
      this.id = this.$route.params.buyId;
      console.log(this.id);
      axios.get('http://localhost:9002/api/product_detailB/' + this.id)
          .then(res => {
            console.log(res.data);
            this.content = res.data;
            this.images = this.content.images;
            console.log(this.images);
            axios.get('http://localhost:9002/api/product_detail_images/' + this.content.filename )
                .then(res => {
                  console.log("이미지 불러오기 성공");
                })
                .catch(e => {
                  console.log("이미지 불러오기 실패" + e);
                })
          })
          .catch(e => {
            console.log(e);
          })
    },
    buyData() {
      this.$router.push({
        path: `/itemBuy/buyNow/${this.content.buyId}`,
        query: {
          count: this.count
        }
      })
    },
    putData() {
      this.axios.post('http://localhost:9002/api/buyCartPut', {
        buyId: this.content.buyId,
        count: this.count,
        MID: this.content.mid.mid,
      }).then(res => {
        this.buyMenuCheckPut = res.data
        if(this.buyMenuCheckPut === false) {
          if (confirm('이미 담겨있습니다. \n장바구니에서 확인하시겠습니까?')) {
            this.$router.push({
              path: `/cart/buy/${this.content.mid.mcode}`
            })
          }
        }else{
          if (confirm('추가되었습니다. \n장바구니에서 확인하시겠습니까?')) {
            this.$router.push({
              path: `/cart/buy/${this.content.mid.mcode}`
            })
          }
        }
      }).catch((err)=>{
        console.log(err)
      })
    },
    putCart() {
    },
    subCount() {
      if (this.count === 1) {
        alert('더 이상 뺄 수 없습니다.')
      } else {
        this.count--
      }
    },
    addCount(){
      if(this.count === this.content.buyStock){
        alert('더 이상 올릴 수 없습니다. (재고부족)')
      }else{
        this.count++
      }
    }
  }
}
</script>

<style scoped>
img {
  width: 50%;
  height: 50%;
}
.slide img {
  width: 10%;
  height: 10%;
}
.mt-4 {
  text-align: center;
}
.slides {
  padding: 0;
  width: 609px;
  height: 420px;
  right: 2.5%;
  display: block;
  margin: 0 auto;
  position: relative;
}
.slides input { display: none; }
.slide-container { display: block; }
.slide {
  top: 0;
  opacity: 0;
  width: 609px;
  height: 420px;
  display: block;
  position: absolute;
  transform: scale(0);
  transition: all .7s ease-in-out;
}
.slide img {
  width: 120%;
  height: 100%;
}
.nav label {
  width: 150px;
  height: 100%;
  display: none;
  position: absolute;
  opacity: 0;
  z-index: 9;
  cursor: pointer;
  transition: opacity .2s;
  color: #FFF;
  font-size: 156pt;
  text-align: center;
  line-height: 380px;
  font-family: "Varela Round", sans-serif;
  background-color: rgba(255, 255, 255, .3);
  text-shadow: 0px 0px 15px rgb(119, 119, 119);
}
.slide:hover + .nav label { opacity: 0.5; }
.nav label:hover { opacity: 1; }
.nav .next { right: -20%; }
input:checked + .slide-container  .slide {
  opacity: 1;
  transform: scale(1);
  transition: opacity 1s ease-in-out;
}
input:checked + .slide-container .nav label { display: block; }
.nav-dots {
  width: 100%;
  bottom: 9px;
  height: 11px;
  display: block;
  position: absolute;
  text-align: center;
  left: 10%;
}
.nav-dots .nav-dot {
  top: -5px;
  width: 11px;
  height: 11px;
  margin: 0 4px;
  position: relative;
  border-radius: 100%;
  display: inline-block;
  background-color: rgba(0, 0, 0, 0.6);
}
.nav-dots .nav-dot:hover {
  cursor: pointer;
  background-color: rgba(0, 0, 0, 0.8);
}
input#img-1:checked ~ .nav-dots label#img-dot-1,
input#img-2:checked ~ .nav-dots label#img-dot-2,
input#img-3:checked ~ .nav-dots label#img-dot-3,
input#img-4:checked ~ .nav-dots label#img-dot-4,
input#img-5:checked ~ .nav-dots label#img-dot-5,
input#img-6:checked ~ .nav-dots label#img-dot-6 {
  background: rgba(0, 0, 0, 0.8);
}

.content-detail-list {
  padding: 0.5%;
  margin-left: 45%;
  margin-top: 1%;
  margin-right: 1%;
  width: 45%;
}
.d-grid gap-2 d-md-flex justify-content-md-end {
  margin-left: 15%;
}

.buy-count-sub{
  color: #00a3de;
  background-color: white;
  font-size: 0.5em;
  padding-top: 5px;
  padding-bottom: 5px;
}
.buy-count-add{
  color: #00a3de;
  font-weight: bolder;
  background-color: white;
  font-size: 0.5em;
  padding-top: 5px;
  padding-bottom: 5px;
}
.img-block{
  display: flex;
}
.blocks{
  border: 1px solid red;
  width: 30%;
  height: 30%;

}
</style>