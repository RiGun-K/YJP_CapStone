<template>
   <div class="container">
     <br><br>
    <h1> 연장 결제</h1>
     <br><br>
     <div class="left-box">
       <div class="storage-info-box">
         <h4>보관</h4>
         <div class="info-box">
           <table>
             <tr>
               <td>보관소</td>
               <td>{{ storageName }}</td>
             </tr>
             <tr>
               <td>보관함</td>
               <td>{{ boxName }}</td>
             </tr>
           </table>
         </div>
       </div>
       <br>
       <div class="time-range-box">
         <h4>기간</h4>
         <div class="info-box">
           <table>
             <tr>
               <td>시작일</td>
               <td>{{ startTime }}</td>
             </tr>
             <tr>
               <td>종료일</td>
               <td>{{ endTime }}</td>
             </tr>
           </table>
         </div>
       </div>
       <div class="time-range-box">
         <h4>연장기간</h4>
         <div class="info-box">
           <table>
             <tr>
               <td>시작일</td>
               <td>{{ newStartTime }}</td>
             </tr>
             <tr>
               <td>종료일</td>
               <td>{{ newEndTime }}</td>
             </tr>
           </table>
         </div>
       </div>
       <br>
       <div class="price-info-box">
         <h4>금액</h4>
         <div class="info-box">
           <table>
             <tr>
               <td>금액</td>
               <td>{{ price }}원</td>
             </tr>
           </table>
         </div>
       </div>
       <br><br><br><br>
     </div>
     <div class="bin-box"></div>
    <div class="right-box">
      <hr>
      <button class="payNow-l" @click="this.$router.push({name:'myBox'})" >뒤로가기</button>
      <button class="payNow-r" @click="paymentBtn()">연장결제</button>
    </div>
  </div>
</template>

<script>

import axios from "axios";

export default {
  name: "RenewalBox",
  components: {},
  mounted() {
    this.useBoxCode = this.$route.params.useBoxCode
    this.getTimes()
    this.getBoxData()
    this.getPrice()
  },
  data() {
    return {
      useBoxCode: '',
      range: {
        start: '',       // 시작date
        end: ''          // 종료date
      },
      storageName: '',
      boxName: '',
      startTime: '',  // 시작년월일
      endTime: '',   // 종료년월일
      newStartTime: '',
      newEndTime: '',
      price: ''
    }
  },
  methods: {
    getTimes() {
      axios.get('/api/useTime/' + this.useBoxCode)
          .then(res => {
            this.startTime = res.data.useStorageStartTime[0] + '년' +
                res.data.useStorageStartTime[1] + '월' +
                res.data.useStorageStartTime[2] + '일'
            this.endTime = res.data.useStorageEndTime[0] + '년' +
                res.data.useStorageEndTime[1] + '월' +
                res.data.useStorageEndTime[2] + '일'

            let start = new Date(res.data.useStorageEndTime[0],
                res.data.useStorageEndTime[1] - 1, res.data.useStorageEndTime[2] + 1)
            this.range.start = new Date(res.data.useStorageEndTime[0],
                res.data.useStorageEndTime[1] - 1, res.data.useStorageEndTime[2] + 1)
            this.range.end = new Date(start.setDate(start.getDate() + 29))
            this.newDate()
          })
          .catch(err => {
            console.log(err)
          })
    },
    getPrice() {
      axios.get('/api/getUseBox/'+this.useBoxCode)
      .then(res=>{
        let data = res.data
        this.price = data.storageBoxCode.storageBoxPrice
      })
      .catch(err=>{
        console.log(err)
      })
    },
    newDate() {
      let day = new Date(this.range.start)
      this.newStartTime = day.getFullYear() + '년' +
          (day.getMonth() + 1) + '월' +
          day.getDate() + '일'
      day = new Date(this.range.end)
      this.newEndTime = day.getFullYear() + '년' +
          (day.getMonth() + 1) + '월' +
          day.getDate() + '일'
    },
    getBoxData() {
      axios.get('/api/moveBoxInfo/' + this.useBoxCode)
          .then(res => {
            this.storageName = res.data[0][1]
            this.boxName = res.data[0][3]
          })
          .catch(err => {
            console.log(err)
          })
    },
    paymentBtn() {
      // if (confirm('결제 하시겠습니까?')) {
      //   const IMP = window.IMP
      //   IMP.init('imp35975601')
      //   IMP.request_pay({
      //     pg: 'html5_inicis',
      //     pay_method: 'card',
      //     merchant_uid: 'merchant_' + new Date().getTime(),
      //     name: this.storageName +'보관소'+this.BoxName+'보관함 연장 구독',
      //     amount: this.price/100,
      //     buyer_tel: '01012345678',
      //     confirm_url: ''
      //   }, (rsp) => {
      //     if (rsp.success) {
      //
      //       this.renewalBoxPay()
      //
      //     } else {
      //       let msg = '결제에 실패하였습니다.'
      //       msg += '에러 내용 : ' + rsp.error_msg
      //       alert(msg)
      //
      //     }
      //   })
      // }
      this.renewalBoxPay()
    },
    renewalBoxPay() {
      const data = {
        startTime: this.range.start,
        endTime: this.range.end,
        useBoxCode: this.useBoxCode,
        price : this.price
      }
      axios.post('/api/renewalPay', data)
          .then(res => {
            console.log(res.data.result)
            if (res.data.result == 'ok') {
              alert('다음달 사용 연장 되었습니다 ')
              this.$router.push({name: "storageComplete"})
            } else {
              alert('error')
            }
          })
          .catch(err => {
            console.log(err)
          })
    },


  }
}
</script>

<style scoped>
.container{
  position: center;
  height: 100%;
  width: 95%;
}
.info-box{
  width: 80%;
}
td:first-child{
  width: 30%;
  background-color: rgba(189, 185, 185, 0.98);
}

td,tr{
  border: 1px solid black;
  padding-top:17px;
  padding-bottom:12px;
  word-spacing:9px;
  text-align:center;
}

.left-box{
  float: left;
  width: 50%;
}

.right-box{
  float: right;
  height: 50%;
  width: 50%;
}

.bin-box{
  float: right;
  width: 50%;
  margin-bottom: 30%;
}

.payNow-l{
  position: center;
  text-align: center;
  width: 20%;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
  float: left;
  margin-left: 20%;
}
.payNow-r{
  position: center;
  text-align: center;
  width: 20%;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
  float: right;
  margin-right: 20%;
}
</style>