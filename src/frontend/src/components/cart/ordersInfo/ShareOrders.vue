<template>
  <div class="back">
    <br>
    <div class="frame">
      <div class="share-orders">
        <h1 style="font-weight: bold; padding: 2%">Share Orders</h1>
        <div class="order-card-list">
          <span class="btn-group" role="group" aria-label="Basic radio toggle button group">
            <input type="radio" class="btn-check" name="btnradio" id="btnradio1" autocomplete="off" @click="todayRentalOrders()" checked>
            <label class="btn btn-outline-primary" for="btnradio1" style="font-size: 1em; padding: 1%">오늘</label>

            <input type="radio" class="btn-check" name="btnradio" id="btnradio2" autocomplete="off" @click="weekRentalOrders()">
            <label class="btn btn-outline-primary" for="btnradio2" style="font-size: 1em; padding: 1%">일주일</label>

            <input type="radio" class="btn-check" name="btnradio" id="btnradio3" autocomplete="off" @click="monthRentalOrders()">
            <label class="btn btn-outline-primary" for="btnradio3" style="font-size: 1em; padding: 1%">한 달</label>

            <input type="radio" class="btn-check" name="btnradio" id="btnradio4" autocomplete="off" @click="yearRentalOrders()">
            <label class="btn btn-outline-primary" for="btnradio4" style="font-size: 1em; padding: 1%">일년</label>
          </span>

          <div class="card border-info mb-3" style="margin-top: 5%" v-for="(menu, index) in todayMenu" :key="index">
            <div class="card-header" style="background-color: #b2e2fd">{{ menu.orders.paymentDate.year()}}년 {{ menu.orders.paymentDate.month()}}월 {{ menu.orders.paymentDate.date()}}일</div>
            <div class="card-body">
              <div class="share-orders-item-image">
                <img :src="'/api/product_detail_images/' + menu.rental.filename" class="img-fluid rounded-start" alt="...">
              </div>
              <div style="justify-content: left; width:80%; height: 100%">
                <div style="width: 60%; padding-top: 2%; margin-left: 4%">
                  <p style="font-weight: bold; font-size: 1.2em">{{ menu.rental.rentalName}}</p>
                </div>
                <div style="width: 80%; margin-left: 4%; margin-top: 1%; display: flex">
                  <p style="font-weight: bold; font-size: 0.8em; margin-right: 2%; margin-top: 1%">대여기간: </p><p style="font-size: 1em">{{ this.startDate[index] }} ~ {{ this.endDate[index] }}</p>
                </div>
                <div style="display: flex">
                  <div style="width: 40%; padding-left: 3%; margin-left: 1%; display: flex">
                    <p style="font-weight: bold; font-size: 0.8em; margin-right: 8%; margin-top: 1.5%">상품가격: </p><p style="font-size: 1.0em">{{ menu.rental.rentalPrice }}</p>
                  </div>
                  <div style="width: 30%; padding-left: 2%; margin-left: 2%; display: flex">
                    <p style="font-weight: bold; font-size: 0.8em; margin-right: 8%; margin-top: 1.5%">대여일 수: </p><p style="font-size: 1em">{{ this.period[index]}}</p>
                  </div>
                  <div style="width: 35%; padding: 1%; margin-left: 10%; margin-top: 1%; display: flex">
                    <p style="font-weight: bold; font-size: 0.8em; margin-right: 8%; margin-top: 1.5%">주문금액: </p><p style="font-size: 1em">{{menu.orders.orderPrice }}</p>
                  </div>
                </div>
              </div>
              <div style="width: 15%">
                <button class="order-info-btn">배송조회</button>
                <button class="order-info-btn">대여취소</button>
              </div>
              <!--          <table class="table table-striped">-->
              <!--            <thead>-->
              <!--            <tr>-->
              <!--              <th>주문코드</th>-->
              <!--              <th>상품명</th>-->
              <!--              <th>수량</th>-->
              <!--              <th>주문금액</th>-->
              <!--            </tr>-->
              <!--            </thead>-->
              <!--            <tbody>-->
              <!--            <tr>-->
              <!--              <td>{{ menu.orders.orderCode }}</td>-->
              <!--              <td>{{ menu.rental.rentalName }}</td>-->
              <!--              <td>{{ menu.orderMenuCount }}개</td>-->
              <!--              <td>{{ menu.orders.orderPrice }}</td>-->
              <!--            </tr>-->
              <!--            </tbody>-->
              <!--          </table>-->
            </div>
          </div>
        </div>
      </div>
    </div>
    <br>
  </div>
</template>

<script>
import axios from "axios";
import dayjs from "dayjs";

export default {
  name: "ShareOrders",
  data(){
    return{
      content: [],
      menus: [],
      today: dayjs().format("YYYY-MM-DD"),
      todayMenu: [],
      startDate: [],
      endDate: [],
      period: []

    }
  },
  created(){
    this.content= this.$store.state.loginState
    this.DataList()
  },
  methods:{
    DataList() {
      axios.get('http://localhost:9002/api/ordersList/rentalOrderMenu/' + this.content.mcode)
          .then(res =>{
            this.menus = res.data;
            console.log(this.menus);
            this.toDate();
            this.todayRentalOrders();
          }).catch(e =>{
        console.log(e)
      })
          .catch(e => {
            console.log(e);
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
    todayRentalOrders(){
      this.todayMenu = [];
      let today = dayjs();
      today.format();

      console.log(today.date())

      let count = 0;

      for(let i=0; i < this.menus.length; i++){
        if(today.year() == this.menus[i].orders.paymentDate.year()){
          if(today.month()+1 == this.menus[i].orders.paymentDate.month()){
            if(today.date() == this.menus[i].orders.paymentDate.date()){
              this.todayMenu[count] = this.menus[i];
              count++
            }
          }
        }
      }

      this.toString();
      this.getPeriod();
    },
    weekRentalOrders(){
      this.todayMenu = [];
      let now = dayjs();
      now.format();

      let count = 0;


      for(let i=0; i < this.menus.length; i++){
        if (now.subtract(1, "week").year() == this.menus[i].orders.paymentDate.year()) {
          if (now.subtract(1, "week").month() + 1 == this.menus[i].orders.paymentDate.month()) {
            if (now.subtract(1, "week").date() <= this.menus[i].orders.paymentDate.date()) {
              this.todayMenu[count] = this.menus[i];
              count++;
            }
          }else if(now.subtract(1, "week").month() + 1 < this.menus[i].orders.paymentDate.month()){
            this.todayMenu[count] = this.menus[i];
            count++;
          }
        }else if(now.subtract(1, "week").year() < this.menus[i].orders.paymentDate.year()){
          this.todayMenu[count] = this.menus[i];
          count++;
        }
      }

      this.todayMenu.sort(function (a, b){
        return new Date(b.orders.paymentDate) - new Date(a.orders.paymentDate);
      })
      console.log(this.todayMenu);
      this.toString();
      this.getPeriod();
    },
    monthRentalOrders(){
      this.todayMenu = [];
      let now = dayjs();
      now.format();

      let count = 0;


      for(let i=0; i < this.menus.length; i++){
        if (now.subtract(1, "month").year() == this.menus[i].orders.paymentDate.year()) {
          if (now.subtract(1, "month").month() + 1 == this.menus[i].orders.paymentDate.month()) {
            if (now.subtract(1, "month").date() <= this.menus[i].orders.paymentDate.date()) {
              this.todayMenu[count] = this.menus[i];
              count++;
            }
          }else if(now.subtract(1, "month").month()+1 < this.menus[i].orders.paymentDate.month()){
            this.todayMenu[count] = this.menus[i];
            count++;
          }
        }else if(now.subtract(1, "month").year() < this.menus[i].orders.paymentDate.year()){
          this.todayMenu[count] = this.menus[i];
          count++;
        }
      }

      this.todayMenu.sort(function (a, b){
        return new Date(b.orders.paymentDate) - new Date(a.orders.paymentDate);
      })
      console.log(this.todayMenu);
      this.toString();
      this.getPeriod();
    },
    yearRentalOrders(){
      this.todayMenu = [];
      let now = dayjs();
      now.format();

      let count = 0;


      for(let i=0; i < this.menus.length; i++){
        if (now.subtract(1, "year").year() == this.menus[i].orders.paymentDate.year()) {
          if (now.subtract(1, "year").month() + 1 == this.menus[i].orders.paymentDate.month()) {
            if (now.subtract(1, "year").date() <= this.menus[i].orders.paymentDate.date()) {
              this.todayMenu[count] = this.menus[i];
              count++;
            }
          }else if(now.subtract(1, "month").month()+1 < this.menus[i].orders.paymentDate.month()){
            this.todayMenu[count] = this.menus[i];
            count++;
          }
        }else if(now.subtract(1, "year").year() < this.menus[i].orders.paymentDate.year()){
          this.todayMenu[count] = this.menus[i];
          count++;
        }
      }

      this.todayMenu.sort(function (a, b){
        return new Date(b.orders.paymentDate) - new Date(a.orders.paymentDate);
      })
      console.log(this.todayMenu);
      this.toString();
      this.getPeriod();
    },
    toString() {
      for(let i=0; i<this.todayMenu.length; i++){
        const start = dayjs(this.todayMenu[i].orders.startDate);
        this.startDate[i] = start.format('YYYY/MM/DD');
        const end = dayjs(this.todayMenu[i].orders.endDate);
        this.endDate[i] = end.format('YYYY/MM/DD');
      }
    },
    getPeriod(){
      for(let i=0; i<this.todayMenu.length; i++){
        this.period[i] = parseInt(this.todayMenu[i].orders.orderPrice)/parseInt(this.todayMenu[i].rental.rentalPrice)
      }
    }
  }
}
</script>

<style scoped>
.share-orders{
  width: 100%;
  height: 100%;
  margin-top: 2%;
  padding-left: 3%;
  font-size: 1.5em;
  padding-bottom: 3%;
}
.share-orders-item-image{
  width: 30%;
  height: 30%;
  padding: 2%;
}
.card-body{
  display: flex;
}
/*.table{*/
/*  padding: 0;*/
/*  margin: 0;*/
/*  text-align: center;*/
/*  border: 1px solid silver;*/
/*  border-collapse: collapse;*/
/*}*/
/*.table th, td {*/
/*  border: 1px solid silver;*/
/*  padding: 1%;*/
/*}*/
/*.table th:first-child, td:first-child {*/
/*  border-left: none;*/
/*}*/
.order-card-list{
  width: 70%;
  height: 100%;
  margin-top: 2%;
  margin-left: 3%;
  margin-bottom: 5%;
}
.card-header{
  font-size: 1.2em;
  padding: 1%;
}

.order-card-list .btn-group{
  margin-top: 2%;
  width: 50%;
  padding-bottom: 10%;
}
.order-info-btn{
  margin-top: 28%;
  width: 80%;
  padding: 1.5%;
  background-color: #ffffff;
  color: #00a3de;
  font-weight: bolder;
  border-color: #00a3de;
  border-radius: 1em;
  font-size: 0.7em;
}
.order-info-btn:hover{
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