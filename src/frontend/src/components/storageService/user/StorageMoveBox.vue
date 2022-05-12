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
    <div style="float: right;">
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
  name: "StorageMoveBox",
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
  props:{
    form: {}
  },
  data() {
    return {
      map: null,
      markers: [],
      markPositions1: [],
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
        level: 8,
      };
      console.log(options)
      this.map = new kakao.maps.Map(container, options);

      // 마커 만들기

      let position = []
      for (let i = 0; i < this.storageList.length; i++) {
        position = [this.storageList[i].longitude, this.storageList[i].latitude]
        this.markPositions1.push(position)
      }
      this.displayMarker(this.markPositions1)
    },
    displayMarker(markerPositions) {
      if (this.markers.length > 0) {
        this.markers.forEach((marker) => marker.setMap(null));
      }

      const positions = markerPositions.map(
          (position) => new kakao.maps.LatLng(...position)
      );

      if (positions.length > 0) {
        this.markers = positions.map(
            (position) =>
                new kakao.maps.Marker({
                  map: this.map,
                  position,
                })
        );

        const bounds = positions.reduce(
            (bounds, position) => bounds.extend(position.latlng),
            new kakao.maps.LatLngBounds()
        );

        this.map.setBounds(bounds);
      }
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
      console.log(this.form)
      this.$router.push({name: 'StorageMoveBoxDetail',
                         params: {
                           storageCode: storageCode,
                           storageName:this.form.storageName,
                           boxName:this.form.boxName,
                           boxCode: this.form.boxCode,
                           useBoxCode:this.form.useBoxCode
                         }
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