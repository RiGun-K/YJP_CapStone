<template>
  <br>
  <link rel="canonical" href="https://getbootstrap.com/docs/5.2/examples/blog/">

  <main class="container d-flex flex-wrap">
    <div style="display: flex">
      <div id="carouselExampleDark" class="carousel carousel-dark slide" data-bs-ride="carousel" style="width: 600px; height: 450px">
        <div class="carousel-indicators" >
          <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1" ></button>
          <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="1" aria-label="Slide 2"></button>
          <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="2" aria-label="Slide 3"></button>
          <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="3" aria-label="Slide 3"></button>
          <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="4" aria-label="Slide 3"></button>
          <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="5" aria-label="Slide 3"></button>
        </div>
        <div class="carousel-inner" >
          <div class="carousel-item active" data-bs-interval="4000">
            <img :src="'/api/product_detail_images/' + content.filename" alt="" class="d-block"/>
          </div>
          <div class="carousel-item" data-bs-interval="4000" v-for="(image, index) in campingImages" :key="index" >
            <img :src="'/api/product_detail_images/' + image.filename" alt="" class="d-block "/>
          </div>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleDark" data-bs-slide="prev">
          <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleDark" data-bs-slide="next">
          <span class="carousel-control-next-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Next</span>
        </button>
      </div>
      <div style="margin-left: 100px; margin-top: 30px; width: 500px">
        <table class="table">
          <thead>
          <tr>
            <th scope="col"></th>
            <th scope="col" style="font-size: 32px">{{ this.content.campingName }}</th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <th scope="row">문의처</th>
            <td>010-9699-4238</td>
          </tr>
          <tr>
            <th scope="row">캠핑장 소개</th>
            <td>{{ this.content.campingInfo }}</td>
          </tr>
          <tr>
            <th scope="row">주소</th>
            <td colspan="2">{{ this.content.address }}</td>
          </tr>
          <tr>
            <th scope="row">등록 객실 수</th>
            <td colspan="2">{{ this.content.campingDetailState }}</td>
          </tr>
          <tr>
            <th scope="row">조회 수</th>
            <td colspan="2">{{ this.content.campingViews }}</td>
          </tr>
          <tr>
            <th scope="row">예약 수</th>
            <td colspan="2">{{ this.content.orderMenus.length }}</td>
          </tr>
          </tbody>
        </table>
        <button type="button" class="btn btn-outline-secondary"  @click="putResCart()" style="margin-top: 8px; margin-left: 450px">찜</button>
      </div>
    </div>



    <div class="row mb-2" style="margin-top: 100px; width: 100%;">
      <div class="col-md-6" v-for="(room,index) in roomContent" :key="index">
        <div class="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative">
          <div class="col p-4 d-flex flex-column position-static">
            <strong class="d-inline-block mb-2 text-primary">객실 선택 및 예약</strong>
            <h3 class="mb-0">객실명 - {{ room.detailName }}</h3>
            <div class="mb-1 text-muted" style="font-size: 0.7em; padding: 0.2em">설명 - {{ room.detailFunction }}</div>
            <p class="card-text mb-auto">기준인원 - {{room.baseNumber}} | 최대인원 - {{ room.maximumNumber }}</p>
            <p class="mb-0"> 1 박 가격 - {{ room.detailPrice }}</p>
          </div>

          <div class="reservation" style="display: flex; margin-top: -15px">
            <Datepicker style="width: 450px; margin-left: 20px; margin-bottom: 20px"
                        locale="ko-KR"
                        :min-date="today"
                        :max-date="end"
                        type="date"
                        range
                        format="yyyy/MM/dd"
                        value-format="yyyyMMdd"
                        :enableTimePicker="false"
                        autoApply
                        v-on="toString()"
                        :closeOnAutoApply="false"
                        placeholder="예약 날짜를 선택해주세요."
                        v-model="reservationDate"
                        @click="DayList(room.orderMenus)"
                        :disabledDates="disabledDates"/>
            <input type="text" v-model="room.detailEx" placeholder="인원을 입력하세요">
            <button @click="buyData(room.detailId,room.detailEx,room.baseNumber,room.maximumNumber)" class="btn btn-outline-secondary" data-bs-toggle="modal" data-bs-target="#exampleModal" style="margin-left: 20%; margin-right: 20%; margin-bottom: 20%">예약 및 결제</button>
          </div>

          <div class="col-auto d-none d-lg-block" style="margin-left: 20px; margin-bottom: 20px" v-on:click="toDetail(room)">
            <img :src="'/api/product_detail_images/' + room.filename" alt="..." style="width: 580px; height:300px">
          </div>
        </div>
      </div>
    </div>

    <div v-show="is_show" style="position: fixed; z-index: 4;">
      <div class="modal modal-signin d-block py-5" tabindex="-1" role="dialog" id="modalSignin">
        <div class="modal-dialog" role="document">
          <div class="modal-content rounded-4 shadow" style="width: 800px; height: 1200px">
            <div class="modal-header p-5 pb-4 border-bottom-0">
              <!-- <h5 class="modal-title">Modal title</h5> -->
              <h2 class="fw-bold mb-0">예약 및 결제</h2>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" @click="buyDataBtn"></button>
            </div>

            <div class="modal-body p-5 pt-0">
              <div class="col-md-7 col-lg-8">
                <h4 class="mb-3">예약자 정보</h4>
                <form class="needs-validation" novalidate>
                  <div class="row g-3">
                    <div class="col-12">
                      <label for="address" class="form-label">Name</label>
                      <input type="text" v-model="this.user.mname" class="form-control" id="name" placeholder="1234 Main St" required>
                      <div class="invalid-feedback">
                        Please enter your shipping address.
                      </div>
                    </div>

                    <div class="col-12">
                      <label for="email" class="form-label">Email <span class="text-muted"></span></label>
                      <input type="email" v-model="this.user.mmail" class="form-control" id="email" placeholder="you@example.com">
                      <div class="invalid-feedback">
                        Please enter a valid email address for shipping updates.
                      </div>
                    </div>

                    <div class="col-12">
                      <label for="address" class="form-label">Phone Number</label>
                      <input type="text" v-model="this.user.mph" class="form-control" id="address" placeholder="1234 Main St" required>
                      <div class="invalid-feedback">
                        Please enter your shipping address.
                      </div>
                    </div>
                  </div>
                </form>
              </div>
              <br>

              <div class="col-md-7 col-lg-8">
                <h4 class="mb-3">예약정보</h4>
                <form class="needs-validation" novalidate>
                  <div class="row g-3">
                    <div class="col-12">
                      <label for="address" class="form-label">Reservation People</label>
                      <input type="text" v-model="this.people" class="form-control" id="name" placeholder="1234 Main St" required>
                      <div class="invalid-feedback">
                        Please enter your shipping address.
                      </div>
                    </div>

                    <div class="col-12">
                      <label for="email" class="form-label">Reservation Requests <span class="text-muted"></span></label>
                      <input type="email" v-model="reservationRequest" class="form-control" id="email" placeholder="요청사항을 입력하세요.">
                      <div class="invalid-feedback">
                        Please enter a valid email address for shipping updates.
                      </div>
                    </div>

                    <div class="col-12">
                      <label for="address" class="form-label">Reservation Days</label>
                      <input type="text" v-model="this.sDays" class="form-control" id="address" placeholder="1234 Main St" required>
                      <div class="invalid-feedback">
                        Please enter your shipping address.
                      </div>
                    </div>
                  </div>
                </form>
              </div>

              <br>

              <div class="col-md-7 col-lg-8">
                <h4 class="mb-3">결제정보</h4>
                <form class="needs-validation" novalidate>
                  <div class="row g-3">
                    <div class="col-12">
                      <label for="address" class="form-label">Room Name</label>
                      <input type="text" v-model="this.room.detailName" class="form-control" id="name" placeholder="1234 Main St" required>
                      <div class="invalid-feedback">
                        Please enter your shipping address.
                      </div>
                    </div>

                    <div class="col-12">
                      <label for="email" class="form-label">Room Price <span class="text-muted"></span></label>
                      <input type="email" v-model="this.room.detailPrice" class="form-control" id="email" placeholder="요청사항을 입력하세요.">
                      <div class="invalid-feedback">
                        Please enter a valid email address for shipping updates.
                      </div>
                    </div>

                    <div class="col-12">
                      <label for="address" class="form-label">All Room Price</label>
                      <input type="text" v-model="this.allPrice" class="form-control" id="address" placeholder="1234 Main St" required>
                      <div class="invalid-feedback">
                        Please enter your shipping address.
                      </div>
                    </div>
                  </div>
                </form>
              </div>

              <br>
              <button class="w-100 btn btn-primary btn-lg" @click="paymentBtn()" type="submit">결제하기</button>
            </div>
          </div>
        </div>
      </div>
    </div>


    <br>
    <br>
    <!--  <div v-for="(image,index) in content" :key="index" class="listObj">-->
    <!--    <img :src="'/api/product_detail_images/' + image.filename"/>-->
    <!--  </div>-->
    <!--  // 현재 이미지 여러개 불러오는 과정에서 [0] 로 처리하는 중...-->
    <!--  // v-for를 사용하여 캠핑장테이블 이미지 1개 랑 캠핑장 내부 이미지 테이블 여러개를 불러오도록하자..-->

    <div v-show="is_show2" style="position: fixed; z-index: 4;" >
      <div class="col-md-6">
        <div class="modal modal-signin d-block py-5" tabindex="-1" role="dialog" style="background-color: rgba(49,49,49,0.5); ">
          <div class="modal-dialog" role="document" style="margin-left: 550px; margin-top: 105px;">
            <div class="modal-content rounded-4 shadow" style="width: 800px; height: 638px; background-color: rgba(0,0,0,0.1)">

              <div style="display: flex;">
                <div id="carouselExample" class="carousel carousel-dark slide" data-bs-ride="carousel">

                  <div class="carousel-indicators" >
                    <button type="button" data-bs-target="#carouselExample" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1" ></button>
                    <button type="button" data-bs-target="#carouselExample" data-bs-slide-to="1" aria-label="Slide 2"></button>
                    <button type="button" data-bs-target="#carouselExample" data-bs-slide-to="2" aria-label="Slide 3"></button>
                    <button type="button" data-bs-target="#carouselExample" data-bs-slide-to="3" aria-label="Slide 3"></button>
                  </div>
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" @click="detailBtn()" style=" margin-left: 750px; margin-top: 1%;"></button>
                  <div class="carousel-inner" style="margin-top: 10px">
                    <div class="carousel-item active" data-bs-interval="4000">
                      <img :src="'/api/product_detail_images/' + this.detail_room.filename" alt="" class="d-block" style="width: 798px; height: 600px;"/>
                    </div>
                    <div class="carousel-item" data-bs-interval="4000" v-for="(image, index) in this.detail_room.images" :key="index" >
                      <img :src="'/api/product_detail_images/' + image.filename" alt="" class="d-block" style="width: 798px; height: 600px"/>
                    </div>
                  </div>
                  <button class="carousel-control-prev" type="button" data-bs-target="#carouselExample" data-bs-slide="prev" style="height: 600px; margin-top: 35px">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                  </button>
                  <button class="carousel-control-next" type="button" data-bs-target="#carouselExample" data-bs-slide="next" style="height: 600px;  margin-top: 35px">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>


    <br>
    <br>
    <div style="display: flex" >
      <button type="button" class="btn btn-secondary" @click="detail_1" style="margin-left: 70px;">캠핑장 소개 / 위치</button>
      <button type="button" class="btn btn-secondary" @click="detail_2" style="margin-left: 70px;">이용 안내</button>
      <button type="button" class="btn btn-secondary" @click="detail_3" style="margin-left: 70px;">위치/주변정보</button>
      <button type="button" class="btn btn-secondary" @click="detail_4" style="margin-left: 70px;">캠핑/여행후기</button>
    </div>

    <div v-if="areaCheck1" style="margin-left: 250px; display: flex; margin-top: 60px;">
      <br>
      <div>
        <img class="ilist" :src=imageC>
        <br>
        <img class="ilist" :src=imageC2>
      </div>
      <div class="mapDiv" style="margin-left: 40px;">
        <div id="map"></div>
      </div>
    </div>

    <div v-if="areaCheck2" style="margin-left: 250px; margin-top: 60px;">
      <br>
      <img class="ilist" :src=imageCC>
      <br>
      <img class="ilist" :src=imageCC2>
    </div>

    <div v-if="areaCheck3">
      <br>
      <br>
      <br>
      <br>
      <div class="mapDiv">
        <div id="map"></div>
      </div>
    </div>

    <div v-if="areaCheck4">

      <button type="button" class="btn btn-outline-secondary"  @click="detail_5" style="margin-top: 60px; margin-left: 1100px; width: 110px;">리뷰 작성</button>
      <div class="row mb-2">
        <div class="col-md-6" style="margin-top: 80px">
          <div class="" v-for="(room,index) in roomContent" :key="index">
            <div class="" v-for="(reviews, index) in list" :key="index.id" :item="reviews">
              <div class="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative" style="width: 1000px; margin-left:  120px; margin-top: -20px">
                <div class="col p-4 d-flex flex-column position-static">
                  <button @click="delete_1(reviews)" class="delete_btn" style="width: 60px; height: 20px; margin-left: 20px; font-size: 10px; font-weight: bold; border: 0; outline: 0; text-decoration: underline; margin-top: 2px ">삭제</button>
                  <button @click="update_1(reviews)" class="update_btn" style="width: 60px; height: 20px; margin-left: 5px; font-size: 10px; font-weight: bold; border: 0; outline: 0; text-decoration: underline; ">수정</button>

                  <strong class="d-inline-block mb-2 text-primary" style="margin-bottom: 40px;">객실명 - {{ room.detailName }}</strong>
                  <h3 class="mb-0">{{reviews.mcode.mname}} | {{reviews.savedTime}}</h3>
                  <div class="mb-1 text-muted">{{ reviews.campingTitle }}</div>
                  <p class="card-text mb-auto">{{ reviews.campingContent }}</p>
                  <div class="Recommend">
                    <button class="button" @click="addPush(reviews)">추천수 : {{ reviews.recommend }}</button>
                  </div>

                  <router-link to="{name: 'BuyDetailList', params: { BoardCampingCode:reviews.BoardCampingCode }}"></router-link>
                </div>
                <div class="col-auto d-none d-lg-block">
                  <img :src="'/api/product_detail_images/' + reviews.filename"  style=" width: 200px; height: 250px"/>
                  <!--            <svg class="bd-placeholder-img" width="200" height="250" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Thumbnail" preserveAspectRatio="xMidYMid slice" focusable="false"><title>Placeholder</title><rect width="100%" height="100%" fill="#55595c"/><text x="50%" y="50%" fill="#eceeef" dy=".3em">Thumbnail</text></svg>-->
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

  </main>
</template>

<script>

import axios from "axios";
import Datepicker from '@vuepic/vue-datepicker'
import '@vuepic/vue-datepicker/dist/main.css'
import dayjs from "dayjs"
import store from "@/store";
export default {
  name: "BuyDetailList",
  components: { Datepicker },
  created() {
    this.member = this.$store.state.loginState
    this.DataList();
    this.fetchData();
  },
  mounted () {
    const IMP = window.IMP
    IMP.init('imp35975601')
  },
  data() {
    return {
      id: '',
      content: [],
      roomContent: [],
      campingImages: [],
      image: require('@/assets/camp1.jpg'),
      // file: this.content.origFilename
      images: '',
      stateCheck: false,
      map: null,
      markers: [],
      markPositions1: [],
      list: [],
      // likecnt: 0
      disabledDates: [],
      reservationDate: [],
      // date: [],
      member: [],
      insertNumber: '',
      is_show: false,
      show: false,
      reservationB: '',
      reservationRequest: '',
      // end: new Data(this.today.setDate(this.today.getDate() + 7))
      startDate: new Date(),
      endDate: new Date(),
      areaCheck1: false,
      areaCheck2: false,
      areaCheck3: false,
      areaCheck4: false,
      imageC: require('@/assets/캠핑장 소개.png'),
      imageC2: require('@/assets/캠핑장 소개2.png'),
      imageCC: require('@/assets/이용안내.png'),
      imageCC2: require('@/assets/이용안내 2.png'),
      //
      room: [],
      user: [],
      people: '',
      sDays: '',
      eDays: '',
      allPrice: '',

      is_show2: false,
      detail_room: []
    }
  },
  setup() {
    const today = new Date()
    const todayEnd = new Date()
    const end = new Date(todayEnd.setDate(todayEnd.getDate() + 30))
    return {
      today,
      end
    }
  },
  methods: {
    // orderMenus 가 있으면 예약일자가 있음 , 없으면 예약없다는 뜻
    DayList(orderMenus) {
      this.disabledDates = []
      for (var i = 0; i < orderMenus.length; i++) {
        let startDate = new Date(
            orderMenus[i].orders.startDate[0],
            orderMenus[i].orders.startDate[1] - 1,
            orderMenus[i].orders.startDate[2])
        let endDate = new Date(
            orderMenus[i].orders.endDate[0],
            orderMenus[i].orders.endDate[1] - 1,
            orderMenus[i].orders.endDate[2])
        var length = Math.ceil((endDate.getTime() - startDate.getTime()) / (1000 * 3600 * 24))
        const tomorrow = startDate
        this.disabledDates.push(tomorrow.toString())
        for (var j = 0; j < length; j++) {
          tomorrow.setDate(tomorrow.getDate() + 1)
          this.disabledDates.push(tomorrow.toString())
        }
      }
    },
    DataList() {
      this.id = this.$route.params.campingId;
      console.log(this.id);
      axios.get('http://localhost:9002/api/product_detailC/' + this.id)
          .then(res => {
            console.log(res.data);
            this.content = res.data;
            this.roomContent = this.content.campingDetails;
            this.detailT = this.content.campingDetails.detailT;
            this.campingImages = this.content.images;
            console.log(this.roomContent);
            console.log(this.campingImages);
          })
          .catch(e => {
            console.log(e);
          })
    },
    detailData() {
      this.stateCheck = true;
    },
    buyDataBtn(){
      this.is_show = !this.is_show
    },
    buyData(detailId,detailEx,baseNumber,maximumNumber) {
      console.log(this.startDate)
      console.log(this.endDate)
      console.log(detailId)
      console.log(detailEx)
      const period = this.endDate - this.startDate
      const reservationNumber = detailEx
      // const reservationId = detailId
      if (this.startDate == this.endDate) {
        alert('예약 날짜를 선택해주세요.')
        return
      } else if (`${detailEx}` == 0) {
        alert('인원을 입력해주세요.')
        return
      } else if (`${detailEx}` < `${baseNumber}` | `${detailEx}` > `${maximumNumber}`) {
        alert('예약인원을 다시 설정해주세요.')
        return
      } else {
        // this.show = true;
        this.buyDataBtn();
        // 예약 및 결제 데이터
        this.id = detailId
        this.user = store.getters.getLoginState.mcode;
        this.people = detailEx + ' 명'
        this.sDays = this.startDate + ' ~ ' + this.endDate
        this.eDays = this.endDate
        axios.get('/api/product_detailR/' + this.id)
            .then(res => {
              console.log("해당 객실 정보입니다.")
              console.log(res.data)
              this.room = res.data;
              this.allPrice = this.room.detailPrice * period
              axios.get('/api/product_detailU/' + this.user)
                  .then(res => {
                    console.log("예약인 정보입니다.")
                    console.log(res.data)
                    this.user = res.data;
                  })
                  .catch(e => {
                    console.log(e)
                  })
            })
            .catch(e => {
              console.log(e)
            })
        /* 1] store 활용 , 2] DB에 임시저장 , 3] 전역변수 활용 */
        // this.$router.push({
        //   path: `/infoter/infoterNow/${this.content.campingId}/${detailId}`,
        //   query: {
        //     startDate: this.startDate,
        //     endDate: this.endDate,
        //     period: period,
        //     reservationNumber: reservationNumber,
        //   }
        // })
      }
      // if(this.show) {
      //   this.is_show = !this.is_show;
      // }
      // this.$router.push({
      //   path: `/infoter/infoterNow/${this.content.campingId}/${detailId}`,
      //   query: {
      //     startDate: this.startDate,
      //     endDate: this.endDate,
      //     period: period,
      //     reservationNumber: reservationNumber,
      //   }
      // })
    },
    reservation() {
    },
    detailBtn(){
      this.is_show2 = !this.is_show2;
    },
    detail_3() {
      this.areaCheck3 = true
      this.areaCheck1 = false
      this.areaCheck2 = false
      this.areaCheck4 = false
      // let check = prompt("1+1 은?");
      // alert("귀요미 ㅋ");
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
    fetchData() {
      console.log(this.id)
      axios.get('/api/CampingBoardlist/' + this.id)
          // 캠핑장 아이디를 넘겨줌 = 이 캠핑장에 해당하는 게시글 가져오기
          .then((res) => {
            console.log("게시글 조회 성공" + res.data);
            console.log(res.data);
            this.list = res.data;
          })
          .catch((ex) => {
            console.log("게시글 조회 실패", ex)
          })
    },
    delete_1(reviews) {
      console.log("삭제하실 리뷰번호는" + reviews.boardCampingCode)
      const boardCampingCode = reviews.boardCampingCode;
      if (confirm("삭제하시겠습니까?")) {
        axios.delete('/api/CampingBoardDelete/' + boardCampingCode)
            .then((res) => {
              console.log("삭제되었습니다.", res)
              alert("리뷰가 삭제되었습니다.")
              this.$router.push({
                path: `/infoter/infoterList/${this.id}`
              })
            })
            .catch((err) => {
              console.log("삭제실패" + err)
            })
      }
    },
    update_1(reviews) {
      console.log(reviews.campingId.campingId);
      console.log(reviews.boardCampingCode);
      console.log(reviews.campingContent, reviews.campingTitle, reviews.filename);
      this.$router.push({
        path: `/infoter/infoterupdate/${reviews.boardCampingCode}`,
        query: {
          title: reviews.campingTitle,
          content: reviews.campingContent,
          mname: reviews.mcode.mname,
          filename: reviews.filename,
          campingId: reviews.campingId.campingId,
          recommend: reviews.recommend
        }
      })
    },
    addPush(reviews) {
      console.log(reviews.boardCampingCode)
      axios.post('/api/Reviews_countView', { a: reviews.boardCampingCode })
          .then((res) => {
            alert("추천수가 증가되었습니다.")
            console.log("추천수가 증가되었습니다.")
          })
          .catch(e => {
            console.log(e)
          })
    },
    toString() {
      const start = dayjs(this.reservationDate[0]);
      this.startDate = start.format('YYYYMMDD');
      const end = dayjs(this.reservationDate[1]);
      this.endDate = end.format('YYYYMMDD');
    },
    detail_4() {
      this.areaCheck4 = true;
      this.areaCheck1 = false;
      this.areaCheck2 = false;
      this.areaCheck3 = false;
    },
    detail_5() {
      this.$router.push({
        path: '/infoter/infoterBoard',
        query: {
          campingId: this.id
        }
      })
    },
    detail_1() {
      this.areaCheck4 = false;
      this.areaCheck1 = true;
      this.areaCheck2 = false;
      this.areaCheck3 = false;
    },
    detail_2() {
      this.areaCheck4 = false;
      this.areaCheck1 = false;
      this.areaCheck2 = true;
      this.areaCheck3 = false;
    },
    putResCart(){
      this.axios.post('http://localhost:9002/api/ResCartPut', {
        campingId: this.$route.params.campingId,
        mid: this.member.mcode
      }).then(res => {
        this.resCheckPut = res.data
        if(this.resCheckPut === false) {
          if (confirm('이미 담겨있습니다. \n장바구니에서 확인하시겠습니까?')) {
            this.$router.push({
              path: `/cart/reservation/${this.member.mcode}`
            })
          }
        }else{
          if (confirm('추가되었습니다. \n장바구니에서 확인하시겠습니까?')) {
            this.$router.push({
              path: `/cart/reservation/${this.member.mcode}`
            })
          }
        }
      }).catch((err)=>{
        console.log(err)
      })
    },
    toDetail(room) {
      // console.log(room.images);
      // this.$router.push({
      //   path: `/infoter/infoterList/${this.id}/${room.detailId}`
      // })
      this.detailBtn();
      if(this.is_show2 === true){
        console.log(room)

        this.detail_room = room;
      }
    },
    paymentBtn () {
      if (confirm('결제 하시겠습니까?')) {
        const IMP = window.IMP
        console.log(this.startDate)
        console.log(this.endDate)
        IMP.init('imp35975601')
        IMP.request_pay({
          pg: 'html5_inicis',
          pay_method: 'card',
          merchant_uid: 'merchant_' + new Date().getTime(),
          name: this.room.detailName,
          amount: this.allPrice,
          buyer_tel: this.user.mph,
          buyer_name: this.user.mname,
          buyer_email: this.user.mmail,
          confirm_url: ''
        }, (rsp) => {
          if (true) {
            let msg = '결제가 완료되었습니다.'
            msg += '고유ID : ' + rsp.imp_uid
            msg += '상점 거래 ID : ' + rsp.merchant_uid
            msg += '결제 금액 : ' + rsp.paid_amount
            msg += '카드 승인번호 : ' + rsp.apply_num
            alert(msg)
            console.log(this.content);
            this.axios.post('http://localhost:9002/api/CampingRoomData', {
              MID: this.user.mid,
              reservationName: this.user.mname,
              reservationTel: this.user.mph,
              reservationRequest: this.reservationRequest,
              // orderPrice: this.price,
              orderPrice: this.allPrice,
              orderType: rsp.pay_method,
              paymentCode: rsp.merchant_uid,
              orderState: '2',
              orderMenuCount: 1,
              startDate: this.startDate,
              endDate: this.endDate,
              roomId: this.room.detailId,
              campingId: this.content.campingId
            })
                .then((res)=>{
                  console.log(res.data);
                })
                .catch((err)=>{
                  console.log(err)
                });
            if (confirm('예약을 확인하시겠습니까?')) {
              this.$router.push({
                path: `/ordersList/reservationOrders/${this.user.mcode}`,
              })
            } else {
              this.$router.push({
                name: 'InfoterList',
              })
            }
          } else {
            let msg = '결제에 실패하였습니다.'
            msg += '에러 내용 : ' + rsp.error_msg
            alert(msg)
          }
        })
      }
    },
  },
  watch: {
    reservationTel(val) {
      const reg = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
      if(reg.exec(val)!==null) {
        this.reservationTel = this.reservationTel.slice(0,-1);
        alert("숫자만 입력해주세요")
      }
      return this.reservationTel=this.reservationTel.replace(/[^-\.0-9]/g,'');
    }
  }
}
</script>

<style scoped>
img {
  width : 650px;
  height: 450px;
}

select {width: 200px; padding: .8em .5em; border: 1px solid #999;font-family: inherit;  no-repeat: 95% 50%; border-radius: 0px; -webkit-appearance: none; -moz-appearance: none;appearance: none;}

@import url("https://fonts.googleapis.com/css?family=Poppins:200,300,400,500,600,700,800,900&display=swap");

.mapDiv{
  margin-top: -17%;
  width: 10%;
  height: 10%;
  float: contour;
  margin-right: -27%;
}
#map {
  width: 400px;
  height: 400px;
}

.btn-secondary{
  width: 230px;
  height: 52px;
}

.my-box {               /*리뷰 젤 큰 박스*/
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
.button{
  margin-left : 40%;
  display: inline-block;
  padding: 10px 15px;
  font-size: 16px;
  cursor: pointer;
  text-align: center;
  text-decoration: none;
  outline: none;
  color: #fff;
  background-color: #0152b9;
  border: none;
  border-radius: 15px;
  box-shadow: 0 9px #999;
}
.button:hover {background-color: #61cce0
}
.button:active {
  background-color: #3e8e41;
  box-shadow: 0 5px #666;
  transform: translateY(4px);
}
.Recommend{
  margin-left: 70%;
}

.review-title{
  /*background-color: blue;*/
  position: relative;
  margin-top: -22%;
  left:20%;
  font-size: 25px;
}

.review-text{
  position: relative;
  left: 50px;
  font-size: 22px;
  margin-top: 20%;
  /*margin-left: 40%;*/
}
.btn_Bottom_2{
  width: 30%;
  padding: 10px 0 10px;
  border: 0;
  cursor: pointer;
  color: black;
  background-color: #9dc7ea;
  font-size: 20px;
  font-weight: 400;
  margin-right: 5%;
  margin-top: 5px;
  margin-bottom: 20px;
  float: right;
  border-radius: 15px;
}

.image_1{
  width: 200%;
  height: 200%;
  margin-left: 230%;
  margin-bottom: 10%;
  margin-top: -50%;
  /*width: 300px;*/
  /*height: 180px;*/

}
.review-t{
  text-align: center;
}

.mapDiv {
  margin-left: 35%;
  margin-bottom: 70%;
}
.delete_btn{
  background-color: transparent;
  position: relative;
  bottom: 20px;
  right: 10px;
}
.update_btn{
  background-color: transparent;
  position: relative;
  bottom: 40px;
  right: 30px;
}


</style>