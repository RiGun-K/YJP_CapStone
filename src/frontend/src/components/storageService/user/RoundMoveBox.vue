<template>
  <div>
<!--    위치검색 하고 지도에 보여주고 확인하고 선택하면 주소 등록 되고 다음으로 가기-->
<!--    아니면 집으로 선택하면 집주소로 주소 등록되고 다음으로 가기-->


    <input type="text" v-model="zipCode">우편주소
    <button @click="showApi()">우편번호 찾기</button>
    <input type="text" v-model="address">주소
    <input type="text" v-model="detailAddress">상세주소
    <button @click="search">검색</button>
    <hr>
    <div id="map"></div>
    <hr>
    <button @click="boxMovePay"> 결제</button>
  </div>

  <div>
    집주소 가져오기
  </div>

</template>

<script>
import axios from "axios";

export default {
  name: "RoundMoveBox",
  props: {
    form: {}
  },
  data() {
    return {
      map: null,
      zipCode: '',
      address: '',
      detailAddress: '',
    }
  },
  mounted() {
    console.log(this.form)
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

    axios.get('/api/myAddress/'+ this.form.userId)
    .then(res=>{
      console.log(res.data)
    }).catch(err=>{
      console.log(err)
    })

  },
  methods: {
    boxMovePay() {
      if (this.address == '' || this.zipCode == '') {
        alert('이동할 주소를 입력하세요')
        return
      }
      this.$router.push({
        name: "BoxMovePay",
        params: {
          userId:this.form.userId,
          storageName:this.form.storageName,
          boxName:this.form.boxName,
          useBoxCode:this.form.useBoxCode,
          zipCode: this.zipCode,
          address: this.address,
          detailAddress: this.detailAddress
        }
      })

    },
    showApi() {
      new window.daum.Postcode({
        oncomplete: (data) => {

          this.zipCode = data.zonecode
          this.address = data.roadAddress
        }
      }).open({popupKey: '주소검색'})
    },
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(35.89527721605076, 128.62277217540984),
        level: 8,
      };
      this.map = new kakao.maps.Map(container, options);


    },
    search() {
      // 장소 검색 객체를 생성합니다
      var ps = new kakao.maps.services.Places();
      // 키워드로 장소를 검색합니다
      var keyword = this.address + this.detailAddress
      ps.keywordSearch(keyword, this.placesSearchCB);

    },
    // 키워드 검색 완료 시 호출되는 콜백함수 입니다
    placesSearchCB(data, status, pagination) {
      if (status === kakao.maps.services.Status.OK) {

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
        // LatLngBounds 객체에 좌표를 추가합니다
        var bounds = new kakao.maps.LatLngBounds();

        for (var i = 0; i < data.length; i++) {
          this.displayMarker(data[i]);
          bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
        }

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
        this.map.setBounds(bounds);
      }
    },

    displayMarker(place) {
      // 마커를 생성하고 지도에 표시합니다
      var marker = new kakao.maps.Marker({
        map: this.map,
        position: new kakao.maps.LatLng(place.y, place.x)
      })
      // 마커를 클릭하면 장소명을 표출할 인포윈도우 입니다
      let infowindow = new kakao.maps.InfoWindow({zIndex: 1})

      // 마커에 클릭이벤트를 등록합니다
      kakao.maps.event.addListener(marker, 'click', function () {
        // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
        infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>')
        infowindow.open(this.map, marker)
      })
    }
  }
}
</script>

<style scoped>
#map {
  width: 400px;
  height: 400px;
}
</style>