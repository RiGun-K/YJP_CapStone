<template>
  <br>
  <br>
  <div class="img-block">
    <img :src="'/api/product_detail_images/' + content.filename" class="card-img-top" alt="...">
    <div class="blocks">
      <h1>{{ this.content.rentalName }}</h1>
      <br>
      <h1>가격</h1><h2> {{ this.content.rentalPrice }}</h2>
      <br>
      <h1>설명</h1><h2>{{ this.content.rentalEx }}</h2>
      <br>
      <h1>대여기간 설정</h1>
      <div class="reservation">
        <Datepicker style="margin-left: 3%; margin-bottom: 3%; width: 20%"
                    locale="ko-KR"
                    :min-date="today"
                    :max-date="end"
                    type="date"
                    range
                    format="yyyy/MM/dd"
                    value-format="yyyyMMdd"
                    :enableTimePicker="false"
                    autoApply
                    v-on="toString()"
                    :closeOnAutoApply="false"
                    placeholder="대여하실 기간을 선택해주세요."
                    v-model="reservationDate"
                    :disabledDates="disabledDates"/>
      </div>
      <div class="d-grid gap-2 d-md-flex justify-content-md-end">
        <b-button type="button" class="btn btn-outline-primary btn-lg"  @click="buyData">구매</b-button>
        <b-button type="button" class="btn btn-outline-primary btn-lg"  @click="putData">찜</b-button>
      </div>
    </div>
  </div>

  <div v-if="areaCheck">
    <br>
    <h2>캠핑장 위치정보</h2>
    <div class="mapDiv">
      <div id="map"></div>
    </div>
  </div>

  <br>
  <div class="btn_area">
    <button type="button" @click="detail_1" class="btn_Bottom">
      <span>상품 상세</span>
    </button>
    <button type="button" @click="detail_2" class="btn_Bottom_3">
      <span>상품평</span>
    </button>
    <button type="button" @click="detail_3" class="btn_Bottom_3">
      <span>상품문의</span>
    </button>
    <button type="button" @click="detail_4" class="btn_Bottom_3">
      <span>배송/교환/반품 안내</span>
    </button>
  </div>

  <div v-if="areaCheckB">
    <br>
    <img :src="'/api/product_detail_images/' + images[0].filename" class="card-img-top" alt="...">
  </div>

  <div v-if="areaCheckA">
    <br>
    <div class="review-t">
      <h2>리뷰</h2>
    </div>
    <div class="content-detail-list-1">
      <br>
      <div class="my-box" v-for="(reviews, index) in list" :key="index.id" :item="reviews">
        <div class="Recommend">
          <button class="button" @click="addPush(reviews)">추천수 : {{ reviews.recommend }}</button>
        </div>

        <div class="review">
          <div class="review-title">{{reviews.mcode.mname}} | {{reviews.savedTime}}</div>
          <div class="review-text">{{ reviews.campingTitle }}</div>
          <div class="review-text">{{ reviews.campingContent }}</div>

          <router-link to="{name: 'BuyDetailList', params: { BoardCampingCode:reviews.BoardCampingCode }}"></router-link>

          <div class="my-box-3">
          </div>
          <div class="image_1">
            <p class="review-image">이미지</p>
            <img :src="'/api/product_detail_images/' + reviews.filename" />
          </div>
          <div class="btn_area_2">
            <button type="button" @click="delete_1(reviews)" class="btn_Bottom_2">
              <span>삭제</span>
            </button>
          </div>

          <div class="btn_area_2">
            <button type="button" @click="update_1(reviews)" class="btn_Bottom_2">
              <span>수정</span>
            </button>
          </div>

        </div>



      </div>

      <br>
      <div class="btn_area_1">
        <button type="button" @click="detail_5" class="btn_Bottom_1">
          <span>리뷰 작성</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import dayjs from "dayjs";
export default {
  name: "ShareDetailList",
  created() {
    this.DataList();
  },
  data() {
    return {
      id: '',
      content: [],
      images: [],

      count: 1,
      buyMenuCheckPut: false,

      disabledDates: [],
      reservationDate: [],
      startDate: new Date(),
      endDate: new Date(),
      period: '',
      areaCheckA: false,
      areaCheckB: false,

    }
  },
  setup() {
    const today = new Date()
    const todayEnd = new Date()
    const end = new Date(todayEnd.setDate(todayEnd.getDate() + 200))
    return {
      today,
      end
    }
  },
  methods: {
    DataList() {
      this.id = this.$route.params.rentalId;
      console.log(this.id);
      axios.get('http://localhost:9002/api/product_detailRR/' + this.id)
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
      console.log(this.startDate)
      console.log(this.endDate)
      const period = this.endDate - this.startDate
      console.log("대여 기간은 " + period + " 일 입니다.")
      if (this.startDate == this.endDate) {
        alert('대여 날짜를 선택해주세요.')
      } else {
        this.$router.push({
          path: `/itemShare/shareNow/${this.content.rentalId}`,
          query: {
            count: this.count,
            startDate: this.startDate,
            endDate: this.endDate,
            period: period
          }
        })
      }
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

    toString() {
      const start = dayjs(this.reservationDate[0]);
      this.startDate = start.format('YYYYMMDD');
      const end = dayjs(this.reservationDate[1]);
      this.endDate = end.format('YYYYMMDD');
    },

    fetchData() {
      console.log(this.id)
      axios.get('/api/CampingBoardlist/' + this.id)
          // 캠핑장 아이디를 넘겨줌 = 이 캠핑장에 해당하는 게시글 가져오기
          .then((res) => {
            console.log("게시글 조회 성공" + res.data);
            console.log(res.data);
            this.list = res.data;
          })
          .catch((ex) => {
            console.log("게시글 조회 실패", ex)
          })
    },
    delete_1(reviews) {
      console.log("삭제하실 리뷰번호는" + reviews.boardCampingCode)
      const boardCampingCode = reviews.boardCampingCode;
      if (confirm("삭제하시겠습니까?")) {
        axios.delete('/api/CampingBoardDelete/' + boardCampingCode)
            .then((res) => {
              console.log("삭제되었습니다.", res)
              alert("리뷰가 삭제되었습니다.")
              this.$router.push({
                path: `/infoter/infoterList/${this.id}`
              })
            })
            .catch((err) => {
              console.log("삭제실패" + err)
            })
      }
    },
    update_1(reviews) {
      console.log(reviews.campingId.campingId);
      console.log(reviews.boardCampingCode);
      console.log(reviews.campingContent, reviews.campingTitle, reviews.filename);
      this.$router.push({
        path: `/infoter/infoterupdate/${reviews.boardCampingCode}`,
        query: {
          title: reviews.campingTitle,
          content: reviews.campingContent,
          mname: reviews.mcode.mname,
          filename: reviews.filename,
          campingId: reviews.campingId.campingId,
          recommend: reviews.recommend
        }
      })
    },
    addPush(reviews) {
      console.log(reviews.boardCampingCode)
      axios.post('/api/Reviews_countView', { a: reviews.boardCampingCode })
          .then((res) => {
            alert("추천수가 증가되었습니다.")
            console.log("추천수가 증가되었습니다.")
          })
          .catch(e => {
            console.log(e)
          })
    },
    detail_1() {
      this.areaCheckB = true;
      this.areaCheckA = false;

    },
    detail_4() {
      this.areaCheckA = true;
      this.areaCheckB = false

    },
  }
}
</script>

<style scoped>
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
.listBody{
  padding: 0.5%;
  margin-left: 30%;
  margin-top: 1%;
  margin-right: 1%;
  width: 45%;
}
.card-body img {
  transition: all 0.2s linear;
}
.card-body:hover img {
  transform: scale(1.5);
}
.card {
  text-align: center;
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
  margin-left: 33%;
  margin-top: 5%;
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
.reservation {
  width: 538%;
  margin-left: -31px;
}

img {
  width: 40%;
  height: 40%;
}
.img-block {
  display: flex;
  justify-content: center;
  /*align-items: center;*/
}
.blocks {
  border: 1px solid skyblue;
  width: 30%;
  height: 500px;
  /*margin-top: -100%;*/

}


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
  margin-left: 10%;
  position: center;
}
.btn_Bottom_3{
  width: 20%;
  padding: 21px 0 17px;
  border: 0;
  cursor: pointer;
  color: white;
  background-color: #96adc0;
  font-size: 20px;
  font-weight: 400;
  margin-left: 10px;
  position: center;
}
.my-box {               /*리뷰 젤 큰 박스*/
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
.button{
  margin-left : 40%;
  display: inline-block;
  padding: 10px 15px;
  font-size: 16px;
  cursor: pointer;
  text-align: center;
  text-decoration: none;
  outline: none;
  color: #fff;
  background-color: #0152b9;
  border: none;
  border-radius: 15px;
  box-shadow: 0 9px #999;
}
.button:hover {background-color: #61cce0
}
.button:active {
  background-color: #3e8e41;
  box-shadow: 0 5px #666;
  transform: translateY(4px);
}
.Recommend{
  margin-left: 70%;
}

.review-title{
  /*background-color: blue;*/
  position: relative;
  margin-top: -22%;
  left:20%;
  font-size: 25px;
}
.review-mid{
  position: relative;
  margin-top: -7%;
  /*border:1px solid;*/
  /*box-sizing: border-box;*/
  width: 57%;
  margin-left: 40%;
  font-size: 20px;

}
.review-text{
  position: relative;
  left: 50px;
  font-size: 22px;
  margin-top: 20%;
  /*margin-left: 40%;*/
}
.btn_Bottom_2{
  width: 30%;
  padding: 10px 0 10px;
  border: 0;
  cursor: pointer;
  color: black;
  background-color: #9dc7ea;
  font-size: 20px;
  font-weight: 400;
  margin-right: 5%;
  margin-top: 5px;
  margin-bottom: 20px;
  float: right;
  border-radius: 15px;
}

.image_1{
  width: 200%;
  height: 200%;
  margin-left: 230%;
  margin-bottom: 10%;
  margin-top: -50%;
  /*width: 300px;*/
  /*height: 180px;*/

}
.review-t{
  text-align: center;
}
</style>