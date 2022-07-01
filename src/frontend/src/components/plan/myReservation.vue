<template>
  <div class="back">
    <br />
    <div class="frame">
      <div class="reservation-orders">
        <h1 style="font-weight: bold; padding: 2%">Reservation Orders</h1>
        <div class="order-card-list">
          <span
            class="btn-group"
            role="group"
            aria-label="Basic radio toggle button group"
          >
            <input
              type="radio"
              class="btn-check"
              name="btnradio"
              id="btnradio1"
              autocomplete="off"
              @click="todayResOrders()"
              checked
            />
            <label
              class="btn btn-outline-primary"
              for="btnradio1"
              style="font-size: 1em; padding: 1%"
              >오늘</label
            >

            <input
              type="radio"
              class="btn-check"
              name="btnradio"
              id="btnradio2"
              autocomplete="off"
              @click="weekResOrders()"
            />
            <label
              class="btn btn-outline-primary"
              for="btnradio2"
              style="font-size: 1em; padding: 1%"
              >일주일</label
            >

            <input
              type="radio"
              class="btn-check"
              name="btnradio"
              id="btnradio3"
              autocomplete="off"
              @click="monthResOrders()"
            />
            <label
              class="btn btn-outline-primary"
              for="btnradio3"
              style="font-size: 1em; padding: 1%"
              >한 달</label
            >

            <input
              type="radio"
              class="btn-check"
              name="btnradio"
              id="btnradio4"
              autocomplete="off"
              @click="yearResOrders()"
            />
            <label
              class="btn btn-outline-primary"
              for="btnradio4"
              style="font-size: 1em; padding: 1%"
              >일년</label
            >
          </span>
          <div
            class="card border-info mb-3"
            style="margin-top: 5%"
            v-for="(menu, index) in todayMenu"
            :key="index"
          >
            <div class="card-header" style="background-color: #b2e2fd">

              {{ menu.orders.paymentDate.year() }}년
              {{ menu.orders.paymentDate.month() }}월
              {{ menu.orders.paymentDate.date() }}일
            </div>
            <div class="card-body">
              <div class="res-orders-item-image">
                <img
                  :src="
                    '/api/product_detail_images/' +
                    this.todayCamping[index].filename
                  "
                  class="img-fluid rounded-start"
                  alt="..."
                />
              </div>
              <div style="justify-content: left; width: 80%; height: 100%">
                <div style="width: 80%; padding-top: 2%; margin-left: 4%">
                  <p style="font-weight: bold; font-size: 1.2em">
                    {{ this.todayCamping[index].campingName }}
                  </p>
                </div>
                <div
                  style="
                    width: 68%;
                    padding-top: 0%;
                    margin-left: 4%;
                    display: flex;
                  "
                >
                  <p
                    style="
                      font-weight: bold;
                      font-size: 0.8em;
                      margin-right: 2%;
                      margin-top: 0.5%;
                    "

                  >
                    객실이름:
                  </p>
                  <p style="font-size: 1em">
                    {{ this.todayDetail[index].detailName }}
                  </p>
                </div>
                <div
                  style="
                    width: 80%;
                    margin-left: 4%;
                    margin-top: 1%;
                    display: flex;
                  "
                >
                  <p
                    style="
                      font-weight: bold;
                      font-size: 0.8em;
                      margin-right: 2%;
                      margin-top: 1%;
                    "

                  >
                    대여기간:
                  </p>
                  <p style="font-size: 1em">
                    {{ this.startDate[index] }} ~
                    {{ this.endDate[index] }}
                  </p>
                </div>
                <div style="display: flex">
                  <div
                    style="
                      width: 40%;
                      padding-left: 3%;
                      margin-left: 1%;
                      display: flex;
                    "
                  >
                    <p
                      style="
                        font-weight: bold;
                        font-size: 0.8em;
                        margin-right: 8%;
                        margin-top: 1.5%;
                      "

                    >
                      상품가격:
                    </p>
                    <p style="font-size: 1em">
                      {{ this.todayDetail[index].detailPrice }}
                    </p>
                  </div>
                  <div
                    style="
                      width: 30%;
                      padding-left: 2%;
                      margin-left: 1%;
                      display: flex;
                    "
                  >
                    <p
                      style="
                        font-weight: bold;
                        font-size: 0.8em;
                        margin-right: 8%;
                        margin-top: 1.5%;
                      "

                    >
                      대여일 수:
                    </p>
                    <p style="font-size: 1em">
                      {{ this.period[index] }}
                    </p>
                  </div>
                  <div
                    style="
                      width: 35%;
                      padding: 1%;
                      margin-left: 10%;
                      margin-top: 1%;
                      display: flex;
                    "
                  >
                    <p
                      style="
                        font-weight: bold;
                        font-size: 0.8em;
                        margin-right: 8%;
                        margin-top: 1.5%;
                      "

                    >
                      주문금액:
                    </p>
                    <p style="font-size: 1em">
                      {{ menu.orders.orderPrice }}
                    </p>
                  </div>
                </div>
              </div>
              <div style="width: 15%">
                <button @click="selectMyOrder(this.todayCamping[index], index)">

                  선택
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div>
        <button @click="skip">건너뛰기</button>
      </div>
    </div>
    <br />
  </div>
</template>

<script>
import axios from 'axios';
import dayjs from 'dayjs';
export default {
  name: "ReservationOrders",

  data() {
    return {
      content: [],
      menus: [],
      campingInfo: [],
      detailInfo: [],
      today: dayjs().format('YYYY-MM-DD'),
      todayMenu: [],
      todayCamping: [],
      todayDetail: [],
      startDate: "",
      endDate: "",

      period: [],
    };
  },
  created() {
    this.content = this.$store.state.loginState;
    this.DataList();
  },
  methods: {
    skip: function () {
      const confirmData = confirm(
        "예약된 캠핑장을 선택하지 않고 계획을 작성합니다"
      );
      if (confirmData) {
        // this.$store.state.myReservation = new Object();
        // this.$store.state.camping.camping.address = "";
        // this.$store.state.camping.camping.areaId = "";
        // this.$store.state.camping.camping.campingName = "";
        // this.$store.commit("setMyReservation", this.$store.state.myReservation);
        // this.$store.commit("setCamping", this.$store.state.camping);
        this.$router.push({ name: "basicPlan" });

      }
    },
    selectMyOrder(menu, index) {
      console.log(menu);
      console.log("ehllo");
      this.$store.commit("setCamping", menu);

      this.endDate =
        this.todayMenu[index].orders.endDate[0].toString() +
        "/" +
        this.todayMenu[index].orders.endDate[1].toString() +
        "/" +
        this.todayMenu[index].orders.endDate[2].toString() +
        "/";
      this.startDate =
        this.todayMenu[index].orders.startDate[0].toString() +
        "/" +
        this.todayMenu[index].orders.startDate[1].toString() +
        "/" +
        this.todayMenu[index].orders.startDate[2].toString() +
        "/";
      this.todayMenu[index].orders.endDate = this.endDate;
      this.todayMenu[index].orders.startDate = this.startDate;

      this.$store.commit("setMyReservation", this.todayMenu[index].orders);

      const confirmData = confirm(
        "해당 캠핑장으로 일정작성을 진행하시겠습니까?"
      );
      if (confirmData) {
        this.$router.push({ name: "basicPlan" });

      }
    },
    DataList() {
      axios
        .get(
          "http://localhost:9002/api/ordersList/reservationOrderMenu/" +
            this.content.mcode
        )
        .then((res) => {
          this.menus = res.data;
          console.log(res.data);
          this.toDate();

          for (let i = 0; i < this.menus.length; i++) {
            axios
              .get(
                "http://localhost:9002/api/ordersList/reservationCamping/" +
                  this.menus[i].orderMenuId
              )
              .then((res) => {
                this.campingInfo[i] = res.data;
                axios
                  .get(
                    "http://localhost:9002/api/ordersList/reservationDetail/" +
                      this.menus[i].orderMenuId
                  )
                  .then((res) => {
                    this.detailInfo[i] = res.data;
                    if (i == this.menus.length - 1) {
                      this.todayResOrders();
                    }

                  })
                  .catch((e) => {
                    console.log(e);
                  });
              })
              .catch((e) => {
                console.log(e);
              });
          }
        })
        .catch((e) => {
          console.log(e);
        });

    },
    toDate() {
      for (let i = 0; i < this.menus.length; i++) {
        let theDay = dayjs(`2021-04-16`);
        theDay = theDay.year(this.menus[i].orders.paymentDate[0]);
        theDay = theDay.month(this.menus[i].orders.paymentDate[1]);
        theDay = theDay.date(this.menus[i].orders.paymentDate[2]);

        this.menus[i].orders.paymentDate = theDay;
      }
    },
    todayResOrders() {
      this.todayMenu = [];
      this.todayCamping = [];
      this.todayDetail = [];

      let today = dayjs();
      today.format();

      let count = 0;

      for (let i = 0; i < this.menus.length; i++) {
        if (today.year() == this.menus[i].orders.paymentDate.year()) {
          if (today.month() + 1 == this.menus[i].orders.paymentDate.month()) {
            if (today.date() == this.menus[i].orders.paymentDate.date()) {

              this.todayMenu[count] = this.menus[i];
              this.todayCamping[count] = this.campingInfo[i];
              this.todayDetail[count] = this.detailInfo[i];

              count++;
            }
          }
        }
      }

      //this.toString();
      this.getPeriod();
    },
    weekResOrders() {
      this.todayMenu = [];
      this.todayCamping = [];
      this.todayDetail = [];
      let now = dayjs();
      now.format();

      let count = 0;

      for (let i = 0; i < this.menus.length; i++) {
        if (
          now.subtract(1, "week").year() ==
          this.menus[i].orders.paymentDate.year()
        ) {
          if (
            now.subtract(1, "week").month() + 1 ==
            this.menus[i].orders.paymentDate.month()
          ) {
            if (
              now.subtract(1, "week").date() <=
              this.menus[i].orders.paymentDate.date()

            ) {
              this.todayMenu[count] = this.menus[i];
              this.todayCamping[count] = this.campingInfo[i];
              this.todayDetail[count] = this.detailInfo[i];
              count++;
            }
          } else if (
            now.subtract(1, "week").month() + 1 <
            this.menus[i].orders.paymentDate.month()

          ) {
            this.todayMenu[count] = this.menus[i];
            this.todayCamping[count] = this.campingInfo[i];
            this.todayDetail[count] = this.detailInfo[i];
            count++;
          }
        } else if (
          now.subtract(1, "week").year() <
          this.menus[i].orders.paymentDate.year()

        ) {
          this.todayMenu[count] = this.menus[i];
          this.todayCamping[count] = this.campingInfo[i];
          this.todayDetail[count] = this.detailInfo[i];
          count++;
        }
      }

      this.todayMenu.sort(function (a, b) {
        return new Date(b.orders.paymentDate) - new Date(a.orders.paymentDate);

      });
      console.log(this.todayMenu);
      //this.toString();
      this.getPeriod();
    },
    monthResOrders() {
      this.todayMenu = [];
      this.todayCamping = [];
      this.todayDetail = [];
      let now = dayjs();
      now.format();

      let count = 0;

      for (let i = 0; i < this.menus.length; i++) {
        if (
          now.subtract(1, "month").year() ==
          this.menus[i].orders.paymentDate.year()
        ) {
          if (
            now.subtract(1, "month").month() + 1 ==
            this.menus[i].orders.paymentDate.month()
          ) {
            if (
              now.subtract(1, "month").date() <=
              this.menus[i].orders.paymentDate.date()

            ) {
              this.todayMenu[count] = this.menus[i];
              this.todayCamping[count] = this.campingInfo[i];
              this.todayDetail[count] = this.detailInfo[i];
              count++;
            }
          } else if (
            now.subtract(1, "month").month() + 1 <
            this.menus[i].orders.paymentDate.month()

          ) {
            this.todayMenu[count] = this.menus[i];
            this.todayCamping[count] = this.campingInfo[i];
            this.todayDetail[count] = this.detailInfo[i];
            count++;
          }
        } else if (
          now.subtract(1, "month").year() <
          this.menus[i].orders.paymentDate.year()

        ) {
          this.todayMenu[count] = this.menus[i];
          this.todayCamping[count] = this.campingInfo[i];
          this.todayDetail[count] = this.detailInfo[i];
          count++;
        }
      }

      this.todayMenu.sort(function (a, b) {
        return new Date(b.orders.paymentDate) - new Date(a.orders.paymentDate);

      });
      console.log(this.todayMenu);
      //this.toString();
      this.getPeriod();
    },
    yearResOrders() {
      this.todayMenu = [];
      this.todayCamping = [];
      this.todayDetail = [];
      let now = dayjs();
      now.format();

      let count = 0;

      for (let i = 0; i < this.menus.length; i++) {
        if (
          now.subtract(1, "year").year() ==
          this.menus[i].orders.paymentDate.year()
        ) {
          if (
            now.subtract(1, "year").month() + 1 ==
            this.menus[i].orders.paymentDate.month()
          ) {
            if (
              now.subtract(1, "year").date() <=
              this.menus[i].orders.paymentDate.date()

            ) {
              this.todayMenu[count] = this.menus[i];
              this.todayCamping[count] = this.campingInfo[i];
              this.todayDetail[count] = this.detailInfo[i];
              count++;
            }
          } else if (
            now.subtract(1, "month").month() + 1 <
            this.menus[i].orders.paymentDate.month()

          ) {
            this.todayMenu[count] = this.menus[i];
            this.todayCamping[count] = this.campingInfo[i];
            this.todayDetail[count] = this.detailInfo[i];
            count++;
          }
        } else if (
          now.subtract(1, "year").year() <
          this.menus[i].orders.paymentDate.year()

        ) {
          this.todayMenu[count] = this.menus[i];
          this.todayCamping[count] = this.campingInfo[i];
          this.todayDetail[count] = this.detailInfo[i];
          count++;
        }
      }

      this.todayMenu.sort(function (a, b) {
        return new Date(b.orders.paymentDate) - new Date(a.orders.paymentDate);

      });
      console.log(this.todayMenu);
      //   this.toString();
      this.getPeriod();
    },
    // toString() {
    //    for (let i = 0; i < this.todayMenu.length; i++) {
    //       const start = dayjs(this.todayMenu[i].orders.startDate);
    //       this.startDate[i] = start.format('YYYY/MM/DD');
    //       const end = dayjs(this.todayMenu[i].orders.endDate);
    //       this.endDate[i] = end.format('YYYY/MM/DD');
    //    }
    // },
    getPeriod() {
      for (let i = 0; i < this.todayMenu.length; i++) {
        this.period[i] =
          parseInt(this.todayMenu[i].orders.orderPrice) /
          parseInt(this.todayDetail[i].detailPrice);

      }
    },
  },
};
</script>

<style scoped>
.reservation-orders {
  width: 100%;
  height: 100%;
  margin-top: 2%;
  padding-left: 3%;
  font-size: 1.5em;
  padding-bottom: 3%;
}
.res-orders-item-image {
  width: 30%;
  height: 30%;
  padding: 2%;
}
.card-body {
  display: flex;
}

.order-card-list {
  width: 65%;
  height: 100%;
  margin-top: 2%;
  margin-left: 3%;
  margin-bottom: 5%;
}
.card-header {
  font-size: 1.2em;
  padding: 1%;
}
.order-card-list .btn-group {
  margin-top: 2%;
  width: 50%;
  padding-bottom: 10%;
}
.order-info-btn {
  margin-top: 60%;
  width: 80%;
  padding: 1.5%;
  background-color: #ffffff;
  color: #00a3de;
  font-weight: bolder;
  border-color: #00a3de;
  border-radius: 1em;
  font-size: 0.7em;
}
.order-info-btn:hover {
  color: white;
  background-color: #b2e2fd;
}
.back {
  position: absolute;
  background-image: url(@/assets/campwall2.webp);
  background-size: 100%;
  width: 100%;
  height: auto;
  background-repeat: repeat-y;
  padding: auto;
}
.frame {
  border: none;
  border-radius: 50px;
  background-color: rgb(247, 246, 230);
  width: 86%;
  height: auto;
  margin: auto;
}
</style>

