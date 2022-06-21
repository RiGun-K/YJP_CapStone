<template>
  <div class="user-storage-view">
    <h3>보관소</h3>
    <div class="searchDiv">
      <select v-model="bigPick" @change="bigCheck(bigPick)">
        <option value="0">전국</option>
        <option v-for="big in bigRound" :value="big.areaId">{{ big.areaName }}</option>
      </select>
      <select v-model="smallPick" @change="search()" style="margin-left: 5px">
        <option value="0">전체</option>
        <option v-for="small in smallRound" :value="small.areaId">{{ small.areaName }}</option>
      </select>
    </div>
    <div class="searchDiv">
      <label for="storageName">보관소이름</label>
      <input type="text" id="storageName" v-model="stSearch" placeholder="보관소이름" @keyup.enter="storageSearch()"
             style="margin-left: 5px">
      <button @click="storageSearch()" style="margin-left: 8px">검색</button>
    </div>

    <div class="listBody">
      <div v-for="(storage,index) in storageList" :key="index"
           @click="GetStorageDetail(storage.storageCode)" class="listObj">
        <div class="card">
          <table >
            <tbody>
            <tr>
              <td rowspan="3"><img class="storage-image" :src="'/api/storageImage/'+storage.storageCode"></td>
              <td>이름</td>
              <td>{{ storage.storageName }}</td>
            </tr>
            <tr>
              <td>주소</td>
              <td>{{ storage.storageAddress }}</td>
            </tr>
            <tr>
              <td>전화번호</td>
              <td>{{ storage.storageTel }}</td>
            </tr>
            <tr>
              <td colspan="2"><button @click="askBox(storage)" class="storage-submit-btn">신청</button></td>
            </tr>
            </tbody>
          </table>
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
  name: "StorageMoveBox",
  components: {},
  created() {
    this.memberId = store.getters.getLoginState.loginState
    this.allroundsearch()
    this.getmoveBoxInfo()
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
  props:{
    useBoxCode: '',
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
      moveBox:{}
    }
  },
  methods: {
    getmoveBoxInfo(){
      axios.get('/api/moveBoxInfo/' + this.useBoxCode)
          .then(res => {
            this.moveBox.storageCode = res.data[0][0]
            this.moveBox.storageName = res.data[0][1]
            this.moveBox.boxCode = res.data[0][2]
            this.moveBox.boxName = res.data[0][3]
            this.moveBox.useBoxCode = this.useBoxCode
          })
          .catch(err => {
            console.log(err)
          })
    },
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(35.89527721605076, 128.62277217540984),
        level: 9,
      };
      this.map = new kakao.maps.Map(container, options);

      // 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
      var mapTypeControl = new kakao.maps.MapTypeControl();

      // 지도에 컨트롤을 추가해야 지도위에 표시됩니다
      // kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
      this.map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

      // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
      var zoomControl = new kakao.maps.ZoomControl();
      this.map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

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

    askBox(storageCode) {
      this.$store.commit('moveBoxInfo',this.moveBox)
      this.$router.push({name: 'StorageMoveBoxDetail',
                         params: {
                           storageCode: storageCode.storageCode
                         }
      })
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
  overflow: auto;
  height: 550px;
}

.searchDiv {
  margin-left: 5.5%;
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
  float: right;
}

.storage-submit-btn:hover {
  color: white;
  background-color: #b2e2fd;
}

.storage-image{
  float: left;
  width: 30%;
  height: 30%;
}
td{
  width: 20%;
}

</style>
