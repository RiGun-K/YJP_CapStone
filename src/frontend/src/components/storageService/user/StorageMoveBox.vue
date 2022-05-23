<template>
  <div class="user-storage-view">
    <h3>보관소 리스트 페이지</h3>
    <div class="searchDiv">
      <select v-model="bigPick" @change="bigCheck(bigPick)">
        <option value="0">전국</option>
        <option v-for="big in bigRound" :value="big.areaId">{{ big.areaName }}</option>
      </select>
      <select v-model="smallPick">
        <option value="0">전체</option>
        <option v-for="small in smallRound" :value="small.areaId">{{ small.areaName }}</option>
      </select>
      <button @click="search()">검색</button>
    </div>

    <div class="listBody">
      <div v-for="(storage,index) in storageList" :key="index"
           @click="GetStorageDetail(storage.storageCode)" class="listObj">
        <div class="card">
          <div class="card-body">
            이름: {{ storage.storageName }}
          </div>
          <div class="card-body">
            주소: {{ storage.storageAddress }}
          </div>
          <button @click="askBox(storage)" class="storage-submit-btn">신청</button>
        </div>
      </div>
    </div>

    <div style="float: right;">
      <div id="map"></div>
    </div>

<!--    <div v-if="check">-->
<!--      <div class="storage">-->
<!--        <div class="storage-name-btn">-->
<!--          <h5 class="storage-name-h5">보관소 이름: {{ name }}</h5>-->

<!--          <button @click="askBox(this.boxList.storageCode)" class="storage-submit-btn">신청</button>-->
<!--          <button @click="closeDetail" class="storage-submit-btn">닫기</button>-->
<!--        </div>-->
<!--        <div class="storage-view">-->
<!--          <div class="storage-box" v-for="(box,index) in boxList.storageBoxes" :key="index">-->
<!--            <ul>-->
<!--              <li>보관함 이름: {{ box.storageBoxName }}</li>-->
<!--              <li>보관함 상태:<p v-if="box.storageBoxState == '0'">사용가능</p>-->
<!--                <p v-else>사용불가능</p>-->
<!--              </li>-->
<!--            </ul>-->
<!--          </div>-->
<!--        </div>-->
<!--      </div>-->
<!--    </div>-->
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
    this.allroundsearch()
    axios.get('/api/aRound')
        .then(res => {
          console.log(res.data)
          this.bigRound = res.data
        })
        .catch(err => {
          console.log(err)
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
      memberId: '',
      bigRound: [],
      smallRound: [],
      bigPick: 0,
      smallPick: 0,
    }
  },
  methods: {
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(35.89527721605076, 128.62277217540984),
        level: 9,
      };
      this.map = new kakao.maps.Map(container, options);
      this.allMarker();
    },
    allMarker() {
      // 마커 지우기
      if (this.markers.length > 0) {
        this.markers.forEach((item) => {
          item.setMap(null);
        })
      }
      const pos = [];
      for (let i = 0; i < this.storageList.length; i++) {
        pos.push([
          this.storageList[i].longitude, this.storageList[i].latitude
        ]);
      }
      console.log(pos)
      const positions = pos.map(
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
            (bounds, latlng) => bounds.extend(latlng),
            new kakao.maps.LatLngBounds()
        );

        this.map.setBounds(bounds);
      }
    },
    displayMarker(place) {
      if (this.markers.length > 0) {
        this.markers.forEach((item) => {
          item.setMap(null);
        })
      }
      const positions = place.map(
          (position) => new kakao.maps.LatLng(...position)
      );
      console.log(positions)
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
    GetStorageDetail(storageCode) {
      axios.get('/api/storageView/' + storageCode)
          .then((res) => {
            console.log(res.data)
            this.boxList = res.data
            this.name = this.boxList.storageName
            const point = [this.boxList.longitude, this.boxList.latitude]
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
      this.$router.push({name: 'StorageMoveBoxDetail',
                         params: {
                           storageCode: storageCode.storageCode,
                           storageName:this.form.storageName,
                           boxName:this.form.boxName,
                           boxCode: this.form.boxCode,
                           useBoxCode:this.form.useBoxCode
                         }
      })
    },
    allroundsearch() {
      axios.get('/api/getStorage')
          .then((res) => {
            this.storageList = res.data

          })
          .catch((error) => {
            console.log(error)
          })
      this.allMarker()
    },
    bigCheck(index) {
      if (index == '0') {
        this.smallPick = 0
        this.smallRound = []
      } else {
        axios.get('/api/smallRound/' + index)
            .then(res => {
              this.smallRound = res.data
              this.smallPick = 0
            })
            .catch(err => {
              console.log(err)
            })
      }
    },
    search() {
      if (this.bigPick == "0" && this.smallPick == '0') {
        this.allroundsearch()
      } else if (this.bigPick != "0" && this.smallPick == '0') {
        console.log(this.bigPick)
        axios.get('/api/roundPick/' + this.bigPick + '/'+ this.smallPick)
            .then(res => {
              this.storageList = res.data
              this.allMarker()
            })
            .catch(err => {
              console.log(err)
            })
      } else if (this.bigPick != "0" && this.smallPick != '0'){
        axios.get('/api/roundPick/' + this.bigPick + '/'+ this.smallPick)
            .then(res => {
              this.storageList = res.data
              this.allMarker()
            })
            .catch(err => {
              console.log(err)
            })
      }
      this.allMarker()
    },
  }
}
</script>

<style scoped>
/*추가*/
.listBody{
  padding: 0.5%;
  margin-left: 5%;
  margin-top: 1%;
  margin-right: 1%;
  width: 45%;
  float: left;
}
.searchDiv{
  margin-left: 2%;
  margin-top: 1%;
}
.listObj{
  width: 100%;
}
.mapDiv{
  margin-top: 1%;
  width: 45%;
  float: right;
}
.card{
  margin-top: 1%;
  width: 100%;
  text-align: right;
}

/*기존*/
#map {
  width: 950px;
  height: 700px;
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

.storage-submit-btn {
  margin: 2% -30% 2% 33%;
  text-align: center;
  width: 20%;
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