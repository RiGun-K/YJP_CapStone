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
          <button @click="buyData(room.detailId)" class="btn btn-primary">예약 및 결제</button>
        </div>
        <br>
      </div>
    </div>

    <br>
    <br>

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

    <h2>리뷰</h2>
    <div class="content-detail-list-1">
      <br>
      <div class="my-box">
        <div class="Recommend">
          <button class="button" v-on:click="likecnt++">좋아요</button> <span>좋아요 : </span>
        </div>
        <div class="review">
          <tr>
            <td class="review-mid"></td>
            <br>
            <!--          <input type="text" class="form-control" name ="subject" id="subject" placeholder="">-->
            <td class="review-title">캠핑장명 </td><br>
            <td class="review-text">설명</td>
          </tr>

          <div class="my-box-3">
          </div>
          <p class="review-image">이미지</p>
<!--          <img :src="'/api/product_detail_images/' + this.list[1].filename" />-->
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
      <br>
    </div>

  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "BuyDetailList",
  created() {
    this.DataList();
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
      this.$router.push({
        path: `/infoter/infoterNow/${this.content.campingId}/${detailId}`
      })
    },
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

    detail_5() {
      window.location.href = 'http://localhost:8081/infoter/infoterBoard'
    },


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
  margin-top: 5%;
  width: 63%;
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
</style>