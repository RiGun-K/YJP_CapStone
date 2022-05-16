<template>

  <div class="mt-4">
    <h2> 캠핑장 예약 </h2>
    <b-card-text>
      <div class="content-detail-list">
        <!--        <h2><img :src="'/api/product_detail_images/' + content.filename"></h2><br>-->
        <div class="card" style="width: 18rem;">
          <img :src="'/api/product_detail_images/' + content.filename" class="card-img-top" alt="...">
          <div class="card-body">
            <h5 class="card-title">상품명: {{ this.content.campingName }}</h5>
            <p class="card-text">설명: {{ this.content.campingInfo }}</p>
            <p class="card-text">주소: {{ this.content.address }}</p>
            <p class="card-text">등록 객실 수: {{ this.content.campingDetailState }}</p>
            <a href="#" class="btn btn-primary" @click="detailData">객실선택</a>
          </div>
        </div>
      </div>

    </b-card-text>
    <br>


    <div v-if="stateCheck">
      <h2> 캠핑장 내 객실 선택 및 예약</h2>
      <b-card-text>
        <div class="content-detail-list">
          <div class="card" style="width: 18rem;">
            <img :src="'/api/product_detail_images/' + content.campingDetails[0].filename" class="card-img-top" alt="...">
            <div class="card-body">
              <h5 class="card-title">객실명: {{ this.content.campingDetails[0].detailName }}</h5>
              <p class="card-text">설명: {{ this.content.campingDetails[0].detailFunction }}</p>
              <p class="card-text">최대인원: {{ this.content.campingDetails[0].maximumNumber }}</p>
              <p class="card-text">객실 가격: {{ this.content.campingDetails[0].detailPrice }}</p>
              <a href="#" class="btn btn-primary" @click="buyData">예약 및 결제</a>
            </div>
          </div>
        </div>
      </b-card-text>
      <b-card-text>
        <div class="content-detail-list">
          <div class="card" style="width: 18rem;">
            <img :src="'/api/product_detail_images/' + content.campingDetails[1].filename" class="card-img-top" alt="...">
            <div class="card-body">
              <h5 class="card-title">객실명: {{ this.content.campingDetails[1].detailName }}</h5>
              <p class="card-text">설명: {{ this.content.campingDetails[1].detailFunction }}</p>
              <p class="card-text">최대인원: {{ this.content.campingDetails[1].maximumNumber }}</p>
              <p class="card-text">객실 가격: {{ this.content.campingDetails[1].detailPrice }}</p>
              <a href="#" class="btn btn-primary" @click="buyData">예약 및 결제</a>
            </div>
          </div>
        </div>
      </b-card-text>
    </div>
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
      stateCheck: false,
    }
  },
  methods: {
    DataList() {
      this.id = this.$route.params.campingId;
      console.log(this.id);
      axios.get('http://localhost:9002/api/product_detailC/' + this.id)
          .then(res => {
            console.log(res.data);
            this.content = res.data;
            console.log(this.content.filePath);
            console.log(this.content.filename);
            console.log(this.content.campingDetails[0].filename);
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


    detailData() {
      this.stateCheck = true;
    },
    buyData() {
      this.$router.push({
            path: `/infoter/infoterNow/${this.content.campingDetails[0].detailId}`
        // path: `/infoter/infoterNow/${this.content.campingDetails[1].detailId}`
          })
    },


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