<template>
  <button @click="this.$router.push({name:'myBox'})" class="renewal-box-back-btn">뒤로가기</button>
  <div class="renewal-box">

    <h3> 연장 </h3>
    <div class="card" style="width: 25%; font-weight: bolder; margin-left: 7%">
      <div class="card-body">
        <table>
          <thead>
          <tr>
            <th> 보관소</th>
            <th>보관함</th>
          </tr>
          </thead>
        </table>
      </div>
    </div>
    <div class="card" style="display: flex; width: 25%; margin-left: 7%; margin-bottom: 3%">
      <div class="card-body">
        <table>
          <tbody>
          <tr>
            <td>{{ storageName }}</td>
            <td>{{ boxName }}</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div class="card" style="width: 25%; font-weight: bolder; margin-left: 7%">
      <div class="card-body">
        현재 사용 기간
      </div>
    </div>
    <div class="card" style="display: flex; width: 25%; margin-left: 7%; margin-bottom: 3%">
      <div class="card-body">
        시작: {{ startTime }}
      </div>
      <div class="card-body">
        종료: {{ endTime }}
      </div>
    </div>

    <div class="card" style="width: 25%; font-weight: bolder; margin-left: 7%">
      <div class="card-body">
        연장 기간
      </div>
    </div>
    <div class="card" style="display: flex; width: 25%; margin-left: 7%">
      <div class="card-body">
        시작: {{ newStartTime }}
      </div>
      <div class="card-body">
        종료: {{ newEndTime }}
      </div>
    </div>
    <div>
      <h5>가격 : {{ price }} 원</h5>
    </div>
    <div>
      <button @click="paymentBtn()" class="renewal-box-pay">연장결제</button>

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
      //       this.savePay()
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
              this.$router.push({name: "myBox"})
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
.renewal-box-back-btn {
  margin-left: 2%;
  margin-top: 1%;
  text-align: center;
  width: 7%;
  padding: 0.5%;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
}

.renewal-box-back-btn:hover {
  color: white;
  background-color: #b2e2fd;
}

.renewal-box h3 {
  margin-top: 3%;
  margin-left: 7%;
  margin-bottom: 3%;
}

.renewal-box-pay, .renewal-box-cancel {
  margin-right: -5%;
  margin-left: 10%;
  margin-top: 3%;
  text-align: center;
  width: 7%;
  padding: 0.5%;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
}

.renewal-box-pay:hover, .renewal-box-cancel:hover {
  color: white;
  background-color: #b2e2fd;
}
</style>