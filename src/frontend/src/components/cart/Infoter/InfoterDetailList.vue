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


  <div class="mt-4">
    <h4>{{ this.content.campingName }}</h4>
    <p class="card-text">010-9699-4238</p>
    <p class="card-text">{{ this.content.campingInfo }}</p>
    <p class="card-text">{{ this.content.address }}</p>
    <p class="card-text">등록 객실 수: {{ this.content.campingDetailState }}</p>
    <a href="#" class="btn btn-primary" @click="detailData">객실선택</a>
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

    <div v-if="areaCheck">
      <br>
      <h2>캠핑장 위치정보</h2>
      <div class="mapDiv">
        <div id="map"></div>
      </div>
    </div>

    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>



    <h2>리뷰</h2>
    <div class="content-detail-list-1">
      <br>
      <div class="my-box">
        <div class="Recommend">
        <button class="button" v-on:click="likecnt++">좋아요</button> <span>좋아요 : {{likecnt}}</span>
        </div>
          <div class="review">
            <tr>
          <td class="review-mid">{{this.list[0].mcode.mnick}} | {{this.list[0].savedTime}}</td>
             <br>
<!--          <input type="text" class="form-control" name ="subject" id="subject" placeholder="">-->
          <td class="review-title">캠핑장명 {{this.list[0].campingId.campingName}} </td><br>
          <td class="review-text">설명 {{ this.list[0].recommended }}</td>
            </tr>

            <div class="my-box-3">
          </div>
          <p class="review-image">이미지</p>
            <img :src="'/api/product_detail_images/' + this.list[1].filename" />
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

<!--    <h2>상품분류 : {{ this.content.kindid.kindname }}</h2><br>-->
<!--    <h2>상품명 : {{ this.content.buyName }}</h2><br>-->
<!--    <h2>상품가격 : {{ this.content.buyPrice }}</h2><br>-->
<!--    <h2>상품 이미지경로: {{ this.content.filePath }}</h2><br>-->
<!--    <h2>상품 이미지경로: {{ this.content.filename }}</h2><br>-->

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
      map: null,
      markers: [],
      markPositions1: [],
      areaCheck: false,

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
    detailData() {
      this.stateCheck = true;
    },
    detail_3() {
      this.areaCheck = true
      alert("한번 더 눌러줘~");
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
.mapDiv{
  margin-top: 1%;
  width: 45%;
  float: right;
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
  width: 57%;
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
img {
  margin-left: 100%;
  width: 100%;
  height: 100%;
}


</style>