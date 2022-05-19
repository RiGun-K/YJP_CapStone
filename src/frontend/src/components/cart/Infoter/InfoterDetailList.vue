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

    detail_3() {
      this.areaCheck = true

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
</style>