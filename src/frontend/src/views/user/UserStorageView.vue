<template>
  <div class="user-storage-view">
    <h3>보관소 리스트 페이지</h3>
    <div class="searchDiv">
      <select v-model="bigPick" @change="bigCheck(bigPick)">
        <option value="0">전국</option>
        <option v-for="big in bigRound" :value="big.areaId">{{ big.areaName }}</option>
      </select>
      <select v-model="smallPick" @change="search()">
        <option value="0">전체</option>
        <option v-for="small in smallRound" :value="small.areaId">{{ small.areaName }}</option>
      </select>
      <label for="storageName">보관소이름</label>
      <input type="text" id="storageName" v-model="stSearch" placeholder="보관소이름" @keyup.enter="storageSearch()">
      <button @click="storageSearch()">검색</button>
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

    <div class="mapDiv">
      <div id="map"></div>
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
    this.allroundsearch()
    axios.get('/api/aRound')
        .then(res => {
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
  data() {
    return {
      map: null,
      markers: [],
      storageList: [],
      boxList: [],
      name: '',
      memberId: '',
      markPositions1: [],
      bigRound: [],
      smallRound: [],
      bigPick: 0,
      smallPick: 0,
      stSearch: '',
      searchStorageList: [],
      searchList: [],
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
            this.boxList = res.data
            this.name = this.boxList.storageName
            const point = [this.boxList.longitude, this.boxList.latitude]
            this.displayMarker([point])
          })
          .catch((err) => {
            console.log(err)
          })
    },

    askBox(storage) {
      this.$store.commit('storageCheck', storage)
      this.$router.push({name: 'userStorageDetail'})
    },
    allroundsearch() {
      axios.get('/api/getStorage')
          .then((res) => {
            this.storageList = res.data
            this.searchList = res.data
            this.allMarker()
          })
          .catch((error) => {
            console.log(error)
          })
    },
    bigCheck(index) {
      if (index == '0') {
        this.smallPick = 0
        this.smallRound = []
        this.allroundsearch()
        this.allMarker()
      } else {
        axios.get('/api/smallRound/' + index)
            .then(res => {
              this.smallRound = res.data
              this.smallPick = 0
              this.search()
            })
            .catch(err => {
              console.log(err)
            })
      }
      this.allMarker()
    },
    search() {
      if (this.bigPick == "0" && this.smallPick == '0') {
        this.allroundsearch()
        this.allMarker()
      } else if (this.bigPick != "0" && this.smallPick == '0') {
        axios.get('/api/roundPick/' + this.bigPick + '/' + this.smallPick)
            .then(res => {
              this.storageList = res.data
              this.searchList = res.data
              this.allMarker()
            })
            .catch(err => {
              console.log(err)
            })
      } else if (this.bigPick != "0" && this.smallPick != '0') {
        axios.get('/api/roundPick/' + this.bigPick + '/' + this.smallPick)
            .then(res => {
              this.storageList = res.data
              this.searchList = res.data
              this.allMarker()
            })
            .catch(err => {
              console.log(err)
            })
      }
      this.allMarker()
    },
    storageSearch() {
      this.searchStorageList = []
      if (this.stSearch != '') {
        for (let i = 0; i < this.searchList.length; i++) {
            if (this.searchList[i].storageName.includes(this.stSearch)) {
              this.searchStorageList.push(this.searchList[i])
            }
        }
        if (this.searchStorageList.length < 1){
          alert('검색하신 보관소은 없습니다')
          return
        }
        this.storageList = this.searchStorageList
      } else {
        this.search()
      }
      this.allMarker()
    },
  }
}
</script>

<style scoped>
/*추가*/
.listBody {
  padding: 0.5%;
  margin-left: 5%;
  margin-top: 1%;
  margin-right: 1%;
  width: 40%;
  float: left;
  overflow:auto;
  height:550px;
}

.searchDiv {
  margin-left: 2%;
  margin-top: 1%;
}

.listObj {
  width: 100%;
}

.mapDiv {
  margin-top: 1%;
  margin-right: 1%;
  margin-bottom: 1%;
  width: 50%;
  float: right;
}

.card {
  margin-top: 1%;
  width: 100%;
  text-align: right;
}

/*기존*/
#map {
  width: 98%;
  height: 600px;
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
