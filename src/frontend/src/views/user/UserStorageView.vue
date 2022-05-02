<template>
  <div class="user-storage-view">
    <h3>보관소 리스트 페이지</h3>

      <div class="storage-get" v-for="(storage,index) in storageList" :key="index" @click="GetStorageDetail(storage.storageCode)"
           style="margin-bottom: 3%">
        <div class="card" style="width: 35%; font-weight: bolder; margin-left: 7%">
          <div class="card-body">
            이름: {{ storage.storageName }}
          </div>
          <div class="card-body">
            주소: {{ storage.storageAddress }}
          </div>
        </div>
      </div>
      <div>
        <div id="map"></div>
      </div>

    <div v-if="check">
      <div class="storage">
        <div class="storage-name-btn">
          <h5 class="storage-name-h5">보관소 이름: {{ name }}</h5>

          <button @click="askBox(this.boxList.storageCode)" class="storage-submit-btn">신청</button>
          <button @click="closeDetail" class="storage-submit-btn">닫기</button>
        </div>
        <div class="storage-view">
          <div class="storage-box" v-for="(box,index) in boxList.storageBoxes" :key="index">
            <ul>
              <li>보관함 이름: {{ box.storageBoxName }}</li>
              <li>보관함 상태:<p v-if="box.storageBoxState == '0'">사용가능</p>
                <p v-else>사용불가능</p>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>


</template>
<script>
import axios from "axios";
import store from "@/store";

export default {
  name: "UserStorageView",
  components: {},
  created() {
    this.memberId = store.getters.getLoginState.loginState
    console.log('this.memberId')
    console.log(this.memberId)
    axios.get('/api/getStorage')
        .then((res) => {
          this.storageList = res.data

          console.log('ALL this.storageList')
          console.log(this.storageList)
        })
        .catch((error) => {
          console.log(error)
        })
  },
  mounted() {
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
  data() {
    return {
      map: null,
      storageList: [],
      check: false,
      boxList: [],
      name: '',
      memberId: ''
    }
  },
  methods: {
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(35.89527721605076, 128.62277217540984),
        level: 5,
      };
      this.map = new kakao.maps.Map(container, options);

      var bounds = new kakao.maps.LatLngBounds();
      // 마커 만들기
      for (let i = 0; i < this.storageList.length; i++) {
        // 마커를 생성하고 지도에 표시합니다
        var marker = new kakao.maps.Marker({
          map: this.map,
          position: new kakao.maps.LatLng(this.storageList[i].longitude, this.storageList[i].latitude)
        })

        // 마커를 클릭하면 장소명을 표출할 인포윈도우 입니다
        let infowindow = new kakao.maps.InfoWindow({zIndex: 1})

        // 마커에 클릭이벤트를 등록합니다
        kakao.maps.event.addListener(marker, 'click', () => {
          // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
          infowindow.open(this.map, marker)
        })
        bounds.extend(new kakao.maps.LatLng(this.storageList[i].longitude, this.storageList[i].latitude));

      }

      // this.map.setBounds(bounds);
    },
    displayMarker(place) {
      console.log(place)
      var bounds = new kakao.maps.LatLngBounds();

      bounds.extend(new kakao.maps.LatLng(place[0], place[1]));

      // 마커를 생성하고 지도에 표시합니다
      var marker = new kakao.maps.Marker({
        map: this.map,
        position: new kakao.maps.LatLng(place[0], place[1])
      })
      // 마커를 클릭하면 장소명을 표출할 인포윈도우 입니다
      let infowindow = new kakao.maps.InfoWindow({zIndex: 1})

      // 마커에 클릭이벤트를 등록합니다
      kakao.maps.event.addListener(marker, 'click', function () {
        // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
        infowindow.open(this.map, marker)
      })
      // this.map.setBounds(bounds);
    },

    GetStorageDetail(storageCode) {
      this.detailCheck()
      axios.get('/api/storageView/' + storageCode)
          .then((res) => {
            console.log(res.data)
            this.boxList = res.data
            this.name = this.boxList.storageName
            const point = [this.boxList.longitude,this.boxList.latitude]
            this.displayMarker([point])
          })
          .catch((err) => {
            console.log(err)
          })
    },
    detailCheck() {
      if (!this.check) {
        this.check = !this.check
      }
    },
    closeDetail() {
      if (this.check) {
        this.check = !this.check
        this.initMap()
      }
    },
    askBox(storageCode) {
      console.log('보관소 코드')
      console.log(storageCode)
      this.$router.push({name: 'userStorageDetail', params: {storageCode: storageCode}})
    }
  }
}
</script>

<style scoped>
#map {
  width: 400px;
  height: 400px;
}

.user-storage-view {
  width: 100%;
  height: 100%;
}

.user-storage-view h3 {
  margin-top: 3%;
  margin-left: 7%;
  margin-bottom: 5%;
}

.card-body {
  padding: 3%;
}

.storage-box {
  border: solid 3px #00a3de;
  border-radius: 10px;
  width: 30%;
}

.storage-view {
  display: -webkit-flex;
  display: flex;
}

.storage {
  border: solid 3px #000a69;
  margin-left: 7%;
  width: 50%;
  padding-top: 2%;
  padding-bottom: 2%;
}

.storage-name-h5 {
  margin-left: 2%;
  margin-top: 2%;
  font-weight: bolder;
  width: 40%;
}

.storage-name-btn {
  display: flex;
  width: 100%;
  height: 30%;
}

.storage-submit-btn {
  margin: 2% -30% 2% 33%;
  text-align: center;
  width: 10%;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
}

.storage-submit-btn:hover {
  color: white;
  background-color: #b2e2fd;
}
</style>