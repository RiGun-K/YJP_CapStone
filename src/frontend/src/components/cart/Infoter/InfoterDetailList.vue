<template>
    <br>
    <ul class="slides">
      <input type="radio" name="radio-btn" id="img-1" checked />
      <li class="slide-container">
        <div class="slide">
          <img :src="'/api/product_detail_images/' + content.filename"/>
        </div>
        <div class="nav">
          <label for="img-6" class="prev">&#x2039;</label>
          <label for="img-2" class="next">&#x203a;</label>
        </div>
      </li>

      <input type="radio" name="radio-btn" id="img-2" />
      <li class="slide-container">
        <div class="slide">
          <img src="http://farm9.staticflickr.com/8504/8365873811_d32571df3d_z.jpg" />
        </div>
        <div class="nav">
          <label for="img-1" class="prev">&#x2039;</label>
          <label for="img-3" class="next">&#x203a;</label>
        </div>
      </li>

      <input type="radio" name="radio-btn" id="img-3" />
      <li class="slide-container">
        <div class="slide">
          <img src="http://farm9.staticflickr.com/8068/8250438572_d1a5917072_z.jpg" />
        </div>
        <div class="nav">
          <label for="img-2" class="prev">&#x2039;</label>
          <label for="img-4" class="next">&#x203a;</label>
        </div>
      </li>

      <input type="radio" name="radio-btn" id="img-4" />
      <li class="slide-container">
        <div class="slide">
          <img :src="'/api/product_detail_images/' + content.campingDetails[0].filename" />
        </div>
        <div class="nav">
          <label for="img-3" class="prev">&#x2039;</label>
          <label for="img-5" class="next">&#x203a;</label>
        </div>
      </li>

      <input type="radio" name="radio-btn" id="img-5" />
      <li class="slide-container">
        <div class="slide">
          <img src="http://farm9.staticflickr.com/8055/8098750623_66292a35c0_z.jpg" />
        </div>
        <div class="nav">
          <label for="img-4" class="prev">&#x2039;</label>
          <label for="img-6" class="next">&#x203a;</label>
        </div>
      </li>

      <input type="radio" name="radio-btn" id="img-6" />
      <li class="slide-container">
        <div class="slide">
          <img src="http://farm9.staticflickr.com/8195/8098750703_797e102da2_z.jpg" />
        </div>
        <div class="nav">
          <label for="img-5" class="prev">&#x2039;</label>
          <label for="img-1" class="next">&#x203a;</label>
        </div>
      </li>

      <li class="nav-dots">
        <label for="img-1" class="nav-dot" id="img-dot-1"></label>
        <label for="img-2" class="nav-dot" id="img-dot-2"></label>
        <label for="img-3" class="nav-dot" id="img-dot-3"></label>
        <label for="img-4" class="nav-dot" id="img-dot-4"></label>
        <label for="img-5" class="nav-dot" id="img-dot-5"></label>
        <label for="img-6" class="nav-dot" id="img-dot-6"></label>
      </li>
    </ul>

    <!--  <div v-for="(image,index) in content" :key="index" class="listObj">-->
    <!--    <img :src="'/api/product_detail_images/' + image.filename"/>-->
    <!--  </div>-->
    <!--  // 현재 이미지 여러개 불러오는 과정에서 [0] 로 처리하는 중...-->
    <!--  // v-for를 사용하여 캠핑장테이블 이미지 1개 랑 캠핑장 내부 이미지 테이블 여러개를 불러오도록하자..-->
    <!--  -->


    <div class="mt-4">

      <h4>{{ this.content.campingName }}</h4>
      <p class="card-text">010-9699-4238</p>
      <p class="card-text">{{ this.content.campingInfo }}</p>
      <p class="card-text">{{ this.content.address }}</p>
      <p class="card-text">등록 객실 수: {{ this.content.campingDetailState }}</p>
      <p class="card-text">조회 수: {{ this.content.campingViews }}</p>
      <p class="card-text">예약 수: {{ this.content.orderMenus.length }}</p>

      <br>


      <div class="listBody">
        <h2> 캠핑장 내 객실 선택 및 예약 </h2>
        <div v-for="(room,index) in roomContent" :key="index"
             @click="" class="listObj">
          <div class="card">
            <div class="card-body">
              <img :src="'/api/product_detail_images/' + room.filename" alt="...">
            </div>
            <div class="card-body">
              객실명 - {{ room.detailName }}
            </div>
            <div class="card-body">
              설명 - {{ room.detailFunction }}
            </div>
            <div class="card-body">
              최대인원 - {{ room.maximumNumber }}
            </div>
            <div class="card-body">
              객실 가격 - {{ room.detailPrice }}
            </div>
            <br>
            <p>예약기간 설정</p>
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
                          placeholder="예약 날짜를 선택해주세요."
                          v-model="reservationDate"
                          @click="DayList(room.orderMenus)"
                          :disabledDates="disabledDates"/>
            </div>
            <button @click="buyData(room.detailId)" class="btn btn-primary">예약 및 결제</button>
          </div>
          <br>
        </div>
      </div>
    </div>


    <br>
    <br>
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
        <span>캠핑장 소개</span>
      </button>
      <button type="button" @click="detail_2" class="btn_Bottom_3">
        <span>이용 안내</span>
      </button>
      <button type="button" @click="detail_3" class="btn_Bottom_3">
        <span>위치/주변정보</span>
      </button>
      <button type="button" @click="detail_4" class="btn_Bottom_3">
        <span>캠핑/여행후기</span>
      </button>
    </div>


      <div v-if="areaCheck">
        <br>
        <h2>캠핑장 위치정보</h2>
        <div class="mapDiv">
          <div id="map"></div>
        </div>
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
            <div class="review-title">캠핑장명 {{ reviews.campingId.campingName}} </div><br>
            <div class="review-mid">{{reviews.mcode.mname}} | {{reviews.savedTime}}</div>
              <div class="review-text">설명 {{ reviews.campingContent }}</div>

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
          <br>
        </div>
      </div>

</template>

<script>
import axios from "axios";
import Datepicker from '@vuepic/vue-datepicker'
import '@vuepic/vue-datepicker/dist/main.css'
import dayjs from "dayjs"

export default {
  name: "BuyDetailList",
  created() {
    this.DataList();
    this.fetchData();
  },
  mounted() {
  },
  data() {
    return {
      id: '',
      content: [],
      roomContent: [],
      image: require('@/assets/camp1.jpg'),
      // file: this.content.origFilename
      images: '',
      stateCheck: false,
      map: null,
      markers: [],
      markPositions1: [],
      areaCheck: false,
      list: [],
      areaCheckA: false,
      // likecnt: 0
      disabledDates: [],
      reservationDate: [],
      // date: [],
      // end: new Data(this.today.setDate(this.today.getDate() + 7))
      startDate: new Date(),
      endDate: new Date(),
    }
  },
  setup() {
    const today = new Date()
    const todayEnd = new Date()
    const end = new Date(todayEnd.setDate(todayEnd.getDate() + 30))
    return {
      today,
      end
    }
  },
  methods: {
    // orderMenus 가 있으면 예약일자가 있음 , 없으면 예약없다는 뜻
    DayList(orderMenus) {
      this.disabledDates = []
      for (var i = 0; i < orderMenus.length; i++) {
        let startDate = new Date(
            orderMenus[i].orders.startDate[0],
            orderMenus[i].orders.startDate[1] - 1,
            orderMenus[i].orders.startDate[2])
        let endDate = new Date(
            orderMenus[i].orders.endDate[0],
            orderMenus[i].orders.endDate[1] - 1,
            orderMenus[i].orders.endDate[2])
        var length = Math.ceil((endDate.getTime() - startDate.getTime()) / (1000 * 3600 * 24))
        const tomorrow = startDate
        this.disabledDates.push(tomorrow.toString())
        for (var j = 0; j < length; j++) {
          tomorrow.setDate(tomorrow.getDate() + 1)
          this.disabledDates.push(tomorrow.toString())
        }
      }
    },
    DataList() {
      this.id = this.$route.params.campingId;
      console.log(this.id);
      axios.get('http://localhost:9002/api/product_detailC/' + this.id)
          .then(res => {
            console.log(res.data);
            this.content = res.data;
            this.roomContent = this.content.campingDetails;
            console.log(this.roomContent);
            console.log(this.content.filePath);
            console.log(this.content.filename);
          })
          .catch(e => {
            console.log(e);
          })
    },
    detailData() {
      this.stateCheck = true;
    },
    buyData(detailId) {
      console.log(this.startDate)
      console.log(this.endDate)
      const period = this.endDate - this.startDate
      if (this.startDate == this.endDate) {
        alert('예약 날짜를 선택해주세요.')
        return
      } else {
        this.$router.push({
          path: `/infoter/infoterNow/${this.content.campingId}/${detailId}`,
          query: {
            startDate: this.startDate,
            endDate: this.endDate,
            period: period
          }
        })
      }},

    detail_3() {
      this.areaCheck = true
      let check = prompt("1+1 은?");
      alert("귀요미 ㅋ");
      // const point = [this.content.longitude, this.content.latitude]
      // console.log(point);
      // this.displayMarker([point])
      //  카카오맵
      if (window.kakao && window.kakao.maps) {
        this.initMap();
      } else {
        const script = document.createElement("script");
        /* global kakao */
        script.onload = () => kakao.maps.load(this.initMap);
        script.src =
            "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=8a536388b1cc33e00ae2dbf18b8509ba&libraries=services";
        document.head.appendChild(script);
      }
    },
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(35.89527721605076, 128.62277217540984),
        level: 8,
      };
      console.log(options)
      this.map = new kakao.maps.Map(container, options);
      // 마커 만들기
      let position = []
      position = [this.content.longitude, this.content.latitude]
      this.markPositions1.push(position)
      this.displayMarker(this.markPositions1)
    },
    displayMarker(markerPositions) {
      if (this.markers.length > 0) {
        this.markers.forEach((marker) => marker.setMap(null));
      }
      const positions = markerPositions.map(
          (position) => new kakao.maps.LatLng(...position)
      );
      console.log("길이는" + positions.length)
      if (positions.length > 0) {
        this.markers = positions.map(
            (position) =>
                new kakao.maps.Marker({
                  map: this.map,
                  position,
                })
        );
        const bounds = positions.reduce(
            (bounds, latlng) => bounds.extend(latlng),
            new kakao.maps.LatLngBounds()
        );
        this.map.setBounds(bounds);
      }
    },
    create() {
      this.id = store.getters.getLoginState.loginState;
      console.log(this.id);
      const formData = new FormData();
      formData.append('campingTitle', this.title);
      formData.append('campingContent', this.content);
      formData.append('mcode', this.id);
      formData.append('boardMenuCode', '1');
      formData.append('file', this.file);
      axios.post('/api/CampingBoard', formData, {headers: {'Content-Type': 'multipart/form-data'}})
          .then((res) => {
            console.log(res.data);
            alert("게시글이 등록되었습니다.");
          })
          .catch((ex) => {
            console.log("fail", ex)
          })
    },
    detail_4() {
      this.areaCheckA = true;
    },

    toString() {
      const start = dayjs(this.reservationDate[0]);
      this.startDate = start.format('YYYYMMDD');
      const end = dayjs(this.reservationDate[1]);
      this.endDate = end.format('YYYYMMDD');
    },

    detail_5() {
      this.$router.push({
        path: '/infoter/infoterBoard',
        query: {
          campingId: this.id
        }
      })
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

    }
  }
}
</script>

<style scoped>
  img {
    width: 40%;
    height: 40%;
  }
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
    text-align: center;
  }
  .mapDiv{
    margin-top: -17%;
    width: 63%;
    float: right;
    margin-right: -47%;
  }
  #map {
    width: 400px;
    height: 400px;
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
  /*.slides * {*/
  /*  user-select: none;*/
  /*  -ms-user-select: none;*/
  /*  -moz-user-select: none;*/
  /*  -khtml-user-select: none;*/
  /*  -webkit-user-select: none;*/
  /*  -webkit-touch-callout: none;*/
  /*}*/
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
  .listBody{
    padding: 0.5%;
    margin-left: 30%;
    margin-top: 1%;
    margin-right: 1%;
    width: 45%;
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
    margin-left: 10px;
  }
  .reservation {
    width: 285%;
    margin-left: 80px
  }
  .card-body img {
    transition: all 0.2s linear;
  }
  .card-body:hover img {
    transform: scale(1.5);
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
    margin-top: 1%;
    left:20%;
    font-size: 40px;
  }
.review-mid{
  position: relative;
  margin-top: -7%;
  /*background-color: green;*/
  /*border:1px solid;*/
  /*box-sizing: border-box;*/
  width: 57%;
  margin-left: 40%;
  font-size: 20px;

}
.review-text{
  /*background-color: pink;*/
  position: relative;
  left: 50px;
  font-size: 38px;
  margin-top: 20%;
  margin-left: 40%;
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