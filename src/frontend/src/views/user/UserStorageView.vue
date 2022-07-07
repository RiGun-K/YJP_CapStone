<template>
  <symbol id="box_seam" viewBox="0 0 16 16">
    <path d="M8.186 1.113a.5.5 0 0 0-.372 0L1.846 3.5l2.404.961L10.404 2l-2.218-.887zm3.564 1.426L5.596 5 8 5.961 14.154 3.5l-2.404-.961zm3.25 1.7-6.5 2.6v7.922l6.5-2.6V4.24zM7.5 14.762V6.838L1 4.239v7.923l6.5 2.6zM7.443.184a1.5 1.5 0 0 1 1.114 0l7.129 2.852A.5.5 0 0 1 16 3.5v8.662a1 1 0 0 1-.629.928l-7.185 2.874a.5.5 0 0 1-.372 0L.63 13.09a1 1 0 0 1-.63-.928V3.5a.5.5 0 0 1 .314-.464L7.443.184z"/>
  </symbol>

  <h3 style="margin-top: 2%; margin-left: 250px; margin-bottom: 2%;">Storage Search</h3>

  <main class="container">
    <div class="searchDiv" style="display: flex;">
      <select class="form-select" aria-label="Default select example" v-model="bigPick" @change="bigCheck(bigPick)">
        <option value="0">전국</option>
        <option v-for="big in bigRound" :value="big.areaId">{{ big.areaName }}</option>
      </select>
      <select class="form-select" aria-label="Default select example"  v-model="smallPick" style="margin-left: 1%">
        <option value="0">전체</option>
        <option v-for="small in smallRound" :value="small.areaId">{{ small.areaName }}</option>
      </select>
      <button type="button" class="btn btn-outline-secondary" @click="search()" style="margin-left: 1%">검색</button>
    </div>

    <div class="d-flex flex-wrap" style="justify-content: right; margin-top: 30px">
      <section>
        <div class="search" style="display: flex">
          <input  type="text" id="storageName" v-model="stSearch" placeholder="보관소이름" @keyup.enter="storageSearch()"
                  style="margin-left: 5px">
          <button type="button" class="btn btn-outline-secondary" @click="storageSearch()" style="margin-left:2%; padding: 1%; width: 50px; height: 30px;">검색</button>
        </div>
      </section>
    </div>



      <div style="display: flex; justify-content: center; align-items: center; margin-bottom: 200px">
        <div class="d-flex flex-column align-items-stretch flex-shrink-0 bg-white" style="width: 380px;">
          <a href="/" class="d-flex align-items-center flex-shrink-0 p-3 link-dark text-decoration-none border-bottom">
            <svg class="bi pe-none me-2" width="30" height="24"><use xlink:href="#box_seam"/></svg>
            <span class="fs-5 fw-semibold">Storage</span>
          </a>
          <div class="list-group list-group-flush border-bottom scrollarea"  style="overflow: scroll; width: 380px; height: 600px">
            <a href="#" class="list-group-item list-group-item-action py-3 lh-sm" aria-current="true"
               v-for="(storage,index) in storageList" :key="index"
               @click="GetStorageDetail(storage.storageCode)">
              <div class="d-flex w-100 align-items-center justify-content-between">
                <strong class="mb-1">{{ storage.storageName }}</strong>
                <img :src="'/api/product_detail_images/' + storage.filename" alt="..." style="width: 50px; height:50px; margin-top: 10px" >
              </div>
              <div style="display: flex">
                <div class="col-10 mb-1 small">{{ storage.storageAddress }}</div>
                <button @click="askBox(storage)" class="storage-submit-btn">자세히</button>
              </div>
            </a>
          </div>
        </div>
        <div class="mapDiv">
          <div id="map"></div>
        </div>
      </div>



<!--    <div class="listBody">-->
<!--      <div v-for="(storage,index) in storageList" :key="index"-->
<!--           @click="GetStorageDetail(storage.storageCode)" class="listObj">-->
<!--        <div class="card">-->
<!--          <table>-->
<!--            <tbody>-->
<!--            <tr>-->
<!--              <td rowspan="3"><img class="storage-image" :src="'/api/storageImage/'+storage.storageCode"></td>-->
<!--              <td>이름</td>-->
<!--              <td>{{ storage.storageName }}</td>-->
<!--            </tr>-->
<!--            <tr>-->
<!--              <td>주소</td>-->
<!--              <td>{{ storage.storageAddress }}</td>-->
<!--            </tr>-->
<!--            <tr>-->
<!--              <td>전화번호</td>-->
<!--              <td>{{ storage.storageTel }}</td>-->
<!--            </tr>-->
<!--            <tr>-->
<!--              <td colspan="2">-->
<!--                <button @click="askBox(storage)" class="storage-submit-btn">자세히</button>-->
<!--              </td>-->
<!--            </tr>-->
<!--            </tbody>-->
<!--          </table>-->
<!--        </div>-->
<!--      </div>-->
<!--    </div>-->

  </main>
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

    askBox(storage) {
      this.$store.commit('storageCheck', storage)
      this.$router.push({name: 'userStorageDetail'})
    },
    allroundsearch() {
      axios.get('/api/getStorage')
          .then((res) => {
            this.storageList = res.data

            for (let i = 0; i < this.storageList.length; i++) {
              if (this.storageList[i].storageState == '1') {
                this.storageList.splice(i, 1)
              }
            }
            console.log(this.storageList)
            this.searchList = this.storageList

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
              for (let i = 0; i < this.storageList.length; i++) {
                if (this.storageList[i].storageState == '1') {
                  this.storageList.splice(i, 1)
                }
              }
              this.searchList = this.storageList
              this.allMarker()
            })
            .catch(err => {
              console.log(err)
            })
      } else if (this.bigPick != "0" && this.smallPick != '0') {
        axios.get('/api/roundPick/' + this.bigPick + '/' + this.smallPick)
            .then(res => {
              this.storageList = res.data
              for (let i = 0; i < this.storageList.length; i++) {
                if (this.storageList[i].storageState == '1') {
                  this.storageList.splice(i, 1)
                }
              }
              this.searchList = this.storageList
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
        if (this.searchStorageList.length < 1) {
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
.searchDiv {
  margin-top: 100px;
}

select {width: 200px; padding: .8em .5em; border: 1px solid #999;font-family: inherit;  no-repeat: 95% 50%; border-radius: 0px; -webkit-appearance: none; -moz-appearance: none;appearance: none;}


.mapDiv {
  margin-top: 60px;
  margin-left: 3%;
  margin-bottom: 1%;
  width: 900px;
  height: 600px;
  border: solid 1px black;
}

.search {
  width: 300px;
  height: 30px;

}
.search input {
  width: 250px;
  height: 30px;
  font-size: 18px;
  border: none;
  border-bottom: 1px black solid;
}
select {width: 200px; padding: .8em .5em; border: 1px solid #999;font-family: inherit;  no-repeat: 95% 50%; border-radius: 0px; -webkit-appearance: none; -moz-appearance: none;appearance: none;}



/*기존*/
#map {
  width: 100%;
  height: 600px;
}

.user-storage-view {
  width: 100%;
  height: 100%;
}

.user-storage-view h3 {
  margin-top: 2%;
  margin-left: 250px;
  margin-bottom: 5%;
}

td {
  width: 20%;
}

</style>
