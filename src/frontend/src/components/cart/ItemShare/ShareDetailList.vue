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
        <img :src="'/api/product_detail_images/' + content.filename"/>
      </div>
      <div class="nav">
        <label for="img-1" class="prev">&#x2039;</label>
        <label for="img-3" class="next">&#x203a;</label>
      </div>
    </li>

    <input type="radio" name="radio-btn" id="img-3" />
    <li class="slide-container">
      <div class="slide">
        <img src="http://farm9.staticflickr.com/8055/8098750623_66292a35c0_z.jpg" />
      </div>
      <div class="nav">
        <label for="img-2" class="prev">&#x2039;</label>
        <label for="img-4" class="next">&#x203a;</label>
      </div>
    </li>

    <input type="radio" name="radio-btn" id="img-4" />
    <li class="slide-container">
      <div class="slide">
        <img src="http://farm9.staticflickr.com/8055/8098750623_66292a35c0_z.jpg" />
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

  <div class="listBody">
    <b-card-text>
      <div class="content-detail-list">
        <div class="card" style="width: 18rem;">
          <img :src="'/api/product_detail_images/' + content.filename" class="card-img-top" alt="...">
          <div class="card-body">
            <h5 class="card-title">상품명: {{ this.content.rentalName }}</h5>
            <p class="card-text">가격: 1일 당 {{ this.content.rentalPrice }}</p>
            <p class="card-text">설명: {{ this.content.rentalEx }}</p>
            <br>
            <h3>대여기간 설정</h3>
            <div class="reservation">
              <Datepicker style="margin-left: 3%; margin-bottom: 3%; width: 20%"
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
                          placeholder="대여하실 기간을 선택해주세요."
                          v-model="reservationDate"
                          :disabledDates="disabledDates"/>
            </div>
          </div>
        </div>
      </div>
      <div class="d-grid gap-2 d-md-flex justify-content-md-end">
        <b-button type="button" class="btn btn-outline-primary btn-lg"  @click="buyData">구매</b-button>
        <b-button type="button" class="btn btn-outline-primary btn-lg"  @click="putData">찜</b-button>
      </div>
    </b-card-text>
  </div>
</template>

<script>
import axios from "axios";
import dayjs from "dayjs";
export default {
  name: "ShareDetailList",
  created() {
    this.DataList();
  },
  data() {
    return {
      id: '',
      content: [],
      images: [],

      count: 1,
      buyMenuCheckPut: false,

      disabledDates: [],
      reservationDate: [],
      startDate: new Date(),
      endDate: new Date(),
      period: '',
    }
  },
  setup() {
    const today = new Date()
    const todayEnd = new Date()
    const end = new Date(todayEnd.setDate(todayEnd.getDate() + 200))
    return {
      today,
      end
    }
  },
  methods: {
    DataList() {
      this.id = this.$route.params.rentalId;
      console.log(this.id);
      axios.get('http://localhost:9002/api/product_detailRR/' + this.id)
          .then(res => {
            console.log(res.data);
            this.content = res.data;
            this.images = this.content.images;
            console.log(this.images);
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
    buyData() {
      console.log(this.startDate)
      console.log(this.endDate)
      const period = this.endDate - this.startDate
      console.log("대여 기간은 " + period + " 일 입니다.")
      if (this.startDate == this.endDate) {
        alert('대여 날짜를 선택해주세요.')
      } else {
        this.$router.push({
          path: `/itemShare/shareNow/${this.content.rentalId}`,
          query: {
            count: this.count,
            startDate: this.startDate,
            endDate: this.endDate,
            period: period
          }
        })
      }
    },
    putData() {
      this.axios.post('http://localhost:9002/api/buyCartPut', {
        buyId: this.content.buyId,
        count: this.count,
        MID: this.content.mid.mid,
      }).then(res => {
        this.buyMenuCheckPut = res.data
        if(this.buyMenuCheckPut === false) {
          if (confirm('이미 담겨있습니다. \n장바구니에서 확인하시겠습니까?')) {
            this.$router.push({
              path: `/cart/buy/${this.content.mid.mcode}`
            })
          }
        }else{
          if (confirm('추가되었습니다. \n장바구니에서 확인하시겠습니까?')) {
            this.$router.push({
              path: `/cart/buy/${this.content.mid.mcode}`
            })
          }
        }
      }).catch((err)=>{
        console.log(err)
      })
    },

    toString() {
      const start = dayjs(this.reservationDate[0]);
      this.startDate = start.format('YYYYMMDD');
      const end = dayjs(this.reservationDate[1]);
      this.endDate = end.format('YYYYMMDD');
    },

  }
}
</script>

<style scoped>
.mt-4 {
  text-align: center;
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
.listBody{
  padding: 0.5%;
  margin-left: 30%;
  margin-top: 1%;
  margin-right: 1%;
  width: 45%;
}
.card-body img {
  transition: all 0.2s linear;
}
.card-body:hover img {
  transform: scale(1.5);
}
.card {
  text-align: center;
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

.content-detail-list {
  padding: 0.5%;
  margin-left: 33%;
  margin-top: 5%;
  margin-right: 1%;
  width: 45%;
}
.d-grid gap-2 d-md-flex justify-content-md-end {
  margin-left: 15%;
}

.buy-count-sub{
  color: #00a3de;
  background-color: white;
  font-size: 0.5em;
  padding-top: 5px;
  padding-bottom: 5px;
}
.buy-count-add{
  color: #00a3de;
  font-weight: bolder;
  background-color: white;
  font-size: 0.5em;
  padding-top: 5px;
  padding-bottom: 5px;
}
.reservation {
  width: 538%;
  margin-left: -31px;
}
</style>