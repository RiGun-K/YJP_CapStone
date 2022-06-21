<template>
  <div class="buy-orders">
    <h1>등록한 캠핑장 예약현황</h1>
    <div class="order-card-list">
      <span class="btn-group" role="group" aria-label="Basic radio toggle button group">
        <input type="radio" class="btn-check" name="btnradio" id="btnradio1" autocomplete="off" @click="todayResOrders()" checked>
        <label class="btn btn-outline-primary" for="btnradio1" style="font-size: 1em; padding: 1%">오늘</label>

        <input type="radio" class="btn-check" name="btnradio" id="btnradio2" autocomplete="off" @click="weekResOrders()">
        <label class="btn btn-outline-primary" for="btnradio2" style="font-size: 1em; padding: 1%">일주일</label>

        <input type="radio" class="btn-check" name="btnradio" id="btnradio3" autocomplete="off" @click="monthResOrders()">
        <label class="btn btn-outline-primary" for="btnradio3" style="font-size: 1em; padding: 1%">한 달</label>

        <input type="radio" class="btn-check" name="btnradio" id="btnradio4" autocomplete="off" @click="yearResOrders()">
        <label class="btn btn-outline-primary" for="btnradio4" style="font-size: 1em; padding: 1%">일년</label>
      </span>
      <div class="card border-info mb-3" v-for="(menu, index) in todayMenu" :key="index">
        <div class="card-header">{{ menu.orders.paymentDate.year()}}년 {{ menu.orders.paymentDate.month()}}월 {{ menu.orders.paymentDate.date()}}일</div>
        <div class="card-body">
          <table class="table table-striped">
            <thead>
            <tr>
              <th>예약자</th>
              <th>캠핑장</th>
              <th>객실명</th>
              <th>예약날짜</th>
              <th>주문금액</th>
              <th>연락처</th>
            </tr>
            </thead>
            <tbody>
            <tr>
              <td>{{ menu.orders.mcode.mname }}</td>
              <td>{{ this.todayCamping[index].campingName}}</td>
              <td>{{ this.todayDetail[index].detailName }}</td>
              <td>{{ menu.orders.startDate[0] }}년 {{ menus[index].orders.startDate[1] }}월 {{ menus[index].orders.startDate[2] }}일 ~
                {{ menu.orders.endDate[0] }}년 {{ menu.orders.endDate[1] }}월 {{ menu.orders.endDate[2] }}일</td>
              <td>{{ menu.orders.orderPrice }}</td>
              <td>{{ menu.orders.mcode.mph }}</td>
            </tr>
            <!-- PathVariable 을 위해서는 router-link 작성 -->
            <!--      <router-link :to="{name: 'productDetail', params: { menuid:product.menuid }}"></router-link>-->
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import dayjs from "dayjs";
export default {
  name: "ReservationOrders",
  data(){
    return{
      content: [],
      menus: [],
      campingInfo: [],
      detailInfo: [],
      today: dayjs().format("YYYY-MM-DD"),
      todayMenu: [],
      todayCamping: [],
      todayDetail: []
    }
  },
  created(){
    this.content= this.$store.state.loginState
    this.DataList()
  },
  methods:{
    DataList() {
      axios.get('http://localhost:9002/api/ordersList/reservationOrderMenu/' + this.content.mcode)
          .then(res =>{
            this.menus = res.data;
            console.log(res.data);
            this.toDate();

            for(let i=0; i<this.menus.length; i++){
              axios.get('http://localhost:9002/api/ordersList/reservationCamping/' + this.menus[i].orderMenuId)
                  .then(res =>{
                    this.campingInfo[i] = res.data;
                    axios.get('http://localhost:9002/api/ordersList/reservationDetail/' + this.menus[i].orderMenuId)
                        .then(res =>{
                          this.detailInfo[i] = res.data;
                          if(i == this.menus.length-1){
                            this.todayResOrders();
                          }
                        }).catch(e =>{
                      console.log(e)
                    })

                  }).catch(e =>{
                console.log(e)
              })
            }

          }).catch(e =>{
        console.log(e)
      })


    },
    toDate(){
      for(let i=0; i < this.menus.length; i++){
        let theDay = dayjs(`2021-04-16`)
        theDay = theDay.year(this.menus[i].orders.paymentDate[0]);
        theDay = theDay.month(this.menus[i].orders.paymentDate[1]);
        theDay = theDay.date(this.menus[i].orders.paymentDate[2]);

        this.menus[i].orders.paymentDate = theDay


      }
    },
    todayResOrders(){
      this.todayMenu = [];
      this.todayCamping = [];
      this.todayDetail = [];

      let today = dayjs();
      today.format()

      let count = 0;

      for(let i=0; i < this.menus.length; i++){
        if(today.year() == this.menus[i].orders.paymentDate.year()){
          if(today.month()+1 == this.menus[i].orders.paymentDate.month()){
            if(today.date() == this.menus[i].orders.paymentDate.date()){
              this.todayMenu[count] = this.menus[i];
              this.todayCamping[count] = this.campingInfo[i];
              this.todayDetail[count] = this.detailInfo[i];

              count++
            }
          }
        }
      }


    },
    weekResOrders(){
      this.todayMenu = [];
      this.todayCamping = [];
      this.todayDetail = [];
      let now = dayjs();
      now.format();

      let count = 0;


      for(let i=0; i < this.menus.length; i++){
        if (now.subtract(1, "week").year() == this.menus[i].orders.paymentDate.year()) {
          if (now.subtract(1, "week").month() + 1 == this.menus[i].orders.paymentDate.month()) {
            if (now.subtract(1, "week").date() <= this.menus[i].orders.paymentDate.date()) {
              this.todayMenu[count] = this.menus[i];
              this.todayCamping[count] = this.campingInfo[i];
              this.todayDetail[count] = this.detailInfo[i];
              count++;
            }
          }else if(now.subtract(1, "week").month() + 1 < this.menus[i].orders.paymentDate.month()){
            this.todayMenu[count] = this.menus[i];
            this.todayCamping[count] = this.campingInfo[i];
            this.todayDetail[count] = this.detailInfo[i];
            count++;
          }
        }else if(now.subtract(1, "week").year() < this.menus[i].orders.paymentDate.year()){
          this.todayMenu[count] = this.menus[i];
          this.todayCamping[count] = this.campingInfo[i];
          this.todayDetail[count] = this.detailInfo[i];
          count++;
        }
      }

      this.todayMenu.sort(function (a, b){
        return new Date(b.orders.paymentDate) - new Date(a.orders.paymentDate);
      })
      console.log(this.todayMenu);
    },
    monthResOrders(){
      this.todayMenu = [];
      this.todayCamping = [];
      this.todayDetail = [];
      let now = dayjs();
      now.format();

      let count = 0;


      for(let i=0; i < this.menus.length; i++){
        if (now.subtract(1, "month").year() == this.menus[i].orders.paymentDate.year()) {
          if (now.subtract(1, "month").month() + 1 == this.menus[i].orders.paymentDate.month()) {
            if (now.subtract(1, "month").date() <= this.menus[i].orders.paymentDate.date()) {
              this.todayMenu[count] = this.menus[i];
              this.todayCamping[count] = this.campingInfo[i];
              this.todayDetail[count] = this.detailInfo[i];
              count++;
            }
          }else if(now.subtract(1, "month").month()+1 < this.menus[i].orders.paymentDate.month()){
            this.todayMenu[count] = this.menus[i];
            this.todayCamping[count] = this.campingInfo[i];
            this.todayDetail[count] = this.detailInfo[i];
            count++;
          }
        }else if(now.subtract(1, "month").year() < this.menus[i].orders.paymentDate.year()){
          this.todayMenu[count] = this.menus[i];
          this.todayCamping[count] = this.campingInfo[i];
          this.todayDetail[count] = this.detailInfo[i];
          count++;
        }
      }

      this.todayMenu.sort(function (a, b){
        return new Date(b.orders.paymentDate) - new Date(a.orders.paymentDate);
      })
      console.log(this.todayMenu);
    },
    yearResOrders(){
      this.todayMenu = [];
      this.todayCamping = [];
      this.todayDetail = [];
      let now = dayjs();
      now.format();

      let count = 0;


      for(let i=0; i < this.menus.length; i++){
        if (now.subtract(1, "year").year() == this.menus[i].orders.paymentDate.year()) {
          if (now.subtract(1, "year").month() + 1 == this.menus[i].orders.paymentDate.month()) {
            if (now.subtract(1, "year").date() <= this.menus[i].orders.paymentDate.date()) {
              this.todayMenu[count] = this.menus[i];
              this.todayCamping[count] = this.campingInfo[i];
              this.todayDetail[count] = this.detailInfo[i];
              count++;
            }
          }else if(now.subtract(1, "month").month()+1 < this.menus[i].orders.paymentDate.month()){
            this.todayMenu[count] = this.menus[i];
            this.todayCamping[count] = this.campingInfo[i];
            this.todayDetail[count] = this.detailInfo[i];
            count++;
          }
        }else if(now.subtract(1, "year").year() < this.menus[i].orders.paymentDate.year()){
          this.todayMenu[count] = this.menus[i];
          this.todayCamping[count] = this.campingInfo[i];
          this.todayDetail[count] = this.detailInfo[i];
          count++;
        }
      }

      this.todayMenu.sort(function (a, b){
        return new Date(b.orders.paymentDate) - new Date(a.orders.paymentDate);
      })
      console.log(this.todayMenu);
    }
  }
}
</script>

<style scoped>
.buy-orders{
  width: 100%;
  height: 100%;
  margin-top: 2%;
  margin-left: 3%;
  font-size: 1.5em;
}
.table{
  padding: 0;
  margin: 0;
  text-align: center;
  border: 1px solid silver;
  border-collapse: collapse;

}
.table th, td {
  border: 1px solid silver;
  padding: 1%;
}
.table th:first-child, td:first-child {
  border-left: none;
}
.order-card-list{
  width: 70%;
  height: 100%;
  margin-top: 2%;
  margin-left: 3%;
}
.card-header{
  font-size: 1.5em;
}
.order-card-list .btn-group{
  margin-top: 2%;
  margin-bottom: 5%;
  width: 50%;
}
</style>