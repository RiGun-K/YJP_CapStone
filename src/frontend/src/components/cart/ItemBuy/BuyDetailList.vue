<template>
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
      image: require('@/assets/camp1.jpg'),
      // file: this.content.origFilename
      images: '',
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
            console.log(this.content.filePath);
            console.log(this.content.filename);
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
        path: `/itemBuy/buyNow/${this.content.buyId}`
      })
    },
    putData() {
      this.$router.push({
        path: "/cart"
      })
    }
  }
}
</script>

<style scoped>
.mt-4 {
  text-align: center;
}
.content-detail-list {
  margin-top: 1rem;
  padding: 1rem;
  text-align: center;
  text-decoration: none;
  display: inline-block;
}
.d-grid gap-2 d-md-flex justify-content-md-end {
  margin: auto;
  width: 100%;
  display: inline-block;
  text-align: center;
}
.card {
}
</style>