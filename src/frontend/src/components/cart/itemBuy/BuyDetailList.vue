<template>
  <img src="@/assets/camp3.jpg" class="img-fluid" alt="...">
  <div class="mt-4">
    <b-card-text>
      <div class="content-detail-list">
        <h2>상품분류 : {{ this.content.kindid.kindname }}</h2><br>
        <h2>상품명 : {{ this.content.menuname }}</h2><br>
        <h2>상품에 대한 설명: {{ this.content.ex }}</h2><br>
        <h2>상품가격 : {{ this.content.price }}</h2><br>
      </div>
      <div class="d-grid gap-2 d-md-flex justify-content-md-end">
        <b-button type="button" class="btn btn-outline-primary btn-lg"  @click="buyData">구매</b-button>
        <b-button type="button" class="btn btn-outline-primary btn-lg"  @click="putData">찜</b-button>
      </div>
    </b-card-text>
    <!--    </b-card>-->
  </div>

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
      // file: this.content.origFilename,
    }
  },
  methods: {
    DataList() {
      this.id = this.$route.params.menuid;
      console.log(this.id);
      axios.get('http://localhost:9002/api/product_detail/' + this.id)
          .then(res => {
            console.log(res.data);
            this.content = res.data;
            console.log(this.content.filePath);
            console.log(this.content.filename);
          })
          .catch(e => {
            console.log(e);
          })
    },
    buyData() {
      this.$router.push({
        path: `/itemBuy/buyNow/${this.content.menuid}`
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
</style>