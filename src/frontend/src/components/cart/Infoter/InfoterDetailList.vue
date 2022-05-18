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
            <a href="#" class="btn btn-primary" @click="buyData">객실선택</a>
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
    </div>


    <br>

    <div class="btn_area">
      <button type="button" @click="detail_1" class="btn_Bottom">
        <span>캠핑장 소개</span>
      </button>
      <button type="button" @click="detail_2" class="btn_Bottom">
        <span>이용 안내</span>
      </button>
      <button type="button" @click="detail_3" class="btn_Bottom">
        <span>위치/주변정보</span>
      </button>
      <button type="button" @click="detail_4" class="btn_Bottom">
        <span>캠핑/여행후기</span>
      </button>
    </div>

    <br>
<!--        <div v-if="stateCheckB">-->
    <h2>리뷰</h2>
    <div class="content-detail-list-1">
      <br>
      <div class="my-box">
        <div class="Recommend">
        <button class="button" v-on:click="likecnt++">좋아요</button> <span>좋아요 : {{likecnt}}</span>
        </div>
          <div class="review">
          <td class="review-mid">작성자 | 작성일</td>
            <br>
<!--          <input type="text" class="form-control" name ="subject" id="subject" placeholder="">-->
          <td class="review-title">상품명</td><br>
          <td class="review-text">설명</td>



          <div class="my-box-3">
            aaaa
          </div>
          <p class="review-image">이미지</p>
<!--          <button type=""-->

<!--        </div>-->
          </div>
<!--        <tr v-for="item in list" :key="item.id" :item="item" @click="detail(item)">-->
      </div>

      <br>
      <div class="btn_area_1">
        <button type="button" @click="detail_5" class="btn_Bottom_1">
          <span>리뷰 작성</span>
        </button>
      </div>
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
    this.fetchData();
  },
  data() {
    return {
      id: '',
      content: [],
      image: require('@/assets/camp1.jpg'),
      // file: this.content.origFilename
      images: '',
      stateCheck: false,
      stateCheckB: false,
      likecnt : 0,
      list: [],
      item: '',

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
    // buyData() {
    //   this.$router.push({
    //     path: `/itemBuy/buyNow/${this.content.buyId}`
    //   })
    // },

    buyData() {
      this.stateCheck = true
          axios.get('http://localhost:9002/api/product_detail_images/' + this.content.campingDetails[0].filename )
              .then(res => {
                console.log("이미지 불러오기 성공");
              })
              .catch(e => {
                console.log("이미지 불러오기 실패" + e);
              })
      this.$router.push({
            path: `/infoter/infoterNow/${this.content.campingDetails[0].detailId}`
          })
    },
    detail_4(){
      this.stateCheckB = true

    },

    detail_5() {
      window.location.href = 'http://localhost:8081/infoter/infoterBoard'

    },
    fetchData() {
      axios.get('/api/CampingBoardlist/' + this.id)
          .then((res) => {
            console.log("게시글 조회 성공" + res.data);
            console.log(res.data);
            this.list = res.data;
          })
          .catch((ex) => {
            console.log("게시글 조회 실패", ex)
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


/* 버튼 */
.btn_area {
  margin: 20px 0 91px;
}
.btn_Bottom {
  width: 20%;
  padding: 21px 0 17px;
  border: 0;
  cursor: pointer;
  color: white;
  background-color: #96adc0;
  font-size: 20px;
  font-weight: 400;
  margin-left: 10px;
}
.my-box {
  border:3px solid;
  padding:50px 0 5%;
  box-sizing: border-box;
  width: 70%;
  margin:0 auto;
  margin-bottom: 1%;
}
.review{
  font-size: 20px;
  margin-right: 70%;
}
.btn_Bottom_1{
  width: 10%;
  padding: 15px 0 15px;
  border: 0;
  cursor: pointer;
  color: white;
  background-color: #9dc7ea;
  font-size: 20px;
  font-weight: 400;
  margin-right: 70%;
  margin-top: 20px;
}
.review-image{
  margin-top: 10%;
  margin-right: 5%;
}
.review-mid{
  border:1px solid;
  box-sizing: border-box;
  width: 60%;
  margin-left: 30%;
}

.my-box-3{
  border:1px solid;
  padding:30px 0 50%;
  box-sizing: border-box;
  width: 230%;
  margin-left: 77%;
  margin-top: 5%;
}
.button{
  margin-left : 15px;
}
.Recommend{
  margin-left: 70%;
}


</style>