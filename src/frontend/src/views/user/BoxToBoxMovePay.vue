<template>
  <div class="container">
    <div class="top-container">
      <h2>이동 결제</h2>
    </div>
    <br>
    <div class="left-box">
      <div class="storage-info-box">
        <h4>현재위치</h4>
        <div class="info-box">
          <table>
            <tr>
              <td>보관소</td>
              <td>{{ beforeBoxInfo.storageName }}</td>
            </tr>
            <tr>
              <td>보관함</td>
              <td>{{ beforeBoxInfo.boxName }}</td>
            </tr>
          </table>
        </div>
      </div>
      <br>
      <div class="storage-info-box">
        <h4>이동위치</h4>
        <div class="info-box">
          <table>
            <tr>
              <td>보관소</td>
              <td>{{ afterBoxInfo.storageName }}</td>
            </tr>
            <tr>
              <td>보관함</td>
              <td>{{ afterBoxInfo.boxName }}</td>
            </tr>
          </table>
        </div>
      </div>
      <br>
      <div class="item-info-box">
        <h4>보관중인 장비</h4>
        <div class="info-box">
          <table class="aaa-table">
            <tr v-for="(item,index) in myItem" :key="index">
              <td>{{ index + 1 }}</td>
              <td>{{ item.memEquipmentName }}</td>
              <td>{{ item.memEquipmentCount }}개</td>
            </tr>
          </table>
        </div>
      </div>
      <br>
    </div>
    <div class="bin-box"></div>
    <br><br>
    <div class="right-box">
      <div class="info-box">
        <table>
          <tr>
            <td>금액</td>
            <td>{{ price }}원</td>
          </tr>
        </table>
      </div>
      <br>
      <br>
      <div>
        <button class="payNow-r" @click="paymentBtn()">결제</button>
        <button class="payNow-l" @click="$router.push({name:'myBox'})">취소</button>
      </div>
    </div>

  </div>
</template>

<script>
import axios from "axios";
import store from "@/store";

export default {
  name: "BoxToBoxMovePay",
  mounted() {
    this.newBoxCode = this.$route.params.newStorageBoxCode
    this.beforeBoxInfo = this.$store.state.moveBoxInfo
    this.getBoxInfo()
    this.getItem()
    this.getPrice()
  },
  data() {
    return {
      newBoxCode: '',
      beforeBoxInfo: {},
      afterBoxInfo: {},
      myItem: {},
      beforeBoxPrice: 0,
      price: 0,
    }
  },
  methods: {
    beforePrice() {
      axios.get('/api/boxPrice/' + this.$store.state.moveBoxInfo.boxCode)
          .then(res => {
            this.beforeBoxPrice = res.data
          })
          .catch((err) => {
            console.log(err)
          })
    },
    getPrice() {
      axios.get('/api/boxPrice/' + this.beforeBoxInfo.boxCode)
          .then(res => {
            this.beforePrice()
            this.price = res.data
            if (this.price < this.beforeBoxPrice) {
              this.stateCheck = false
            } else if (this.price == this.beforeBoxPrice) {
              this.price = 5000
            } else {
              this.price = 5000 + this.price - this.beforeBoxPrice
            }
          })
    },
    getItem() {
      axios.get('/api/getBoxInItem/' + this.beforeBoxInfo.useBoxCode)
          .then(res => {
            this.myItem = res.data
          })
          .catch(err => {
            console.log(err)
          })
    },
    getBoxInfo() {
      axios.get('/api/getNamesToBoxCode/' + this.newBoxCode)
          .then(res => {
            this.afterBoxInfo.storageName = res.data[0][0]
            this.afterBoxInfo.boxName = res.data[0][1]
            this.beforeBoxInfo.boxCode = res.data[0][2]
          })
    },
    paymentBtn() {
      if (confirm('결제 하시겠습니까?')) {
        const IMP = window.IMP
        IMP.init('imp35975601')
        IMP.request_pay({
          pg: 'html5_inicis',
          pay_method: 'card',
          merchant_uid: 'merchant_' + new Date().getTime(),
          name: '보관함 이동',
          amount: this.price/100,
          buyer_tel: '01012345678',
          confirm_url: ''
        }, (rsp) => {
          if (rsp.success) {

            this.pay()

          } else {
            let msg = '결제 완료하였습니다.'
            // msg += '에러 내용 : ' + rsp.error_msg
            alert(msg)
            this.pay()
          }
        })
      }

    },
    pay() {
      const data = {
        userId: store.getters.getLoginState.loginState,
        use: this.beforeBoxInfo.useBoxCode,
        before: this.beforeBoxInfo.boxCode,
        after: this.newBoxCode
      }
      console.log(data)
      axios.post('/api/boxToBoxPay', data)
          .then(res => {
            console.log(res)
            this.$router.push({name: 'storageComplete'});
          }).catch(err => {
        console.log(err)
      })
    }
  }
}
</script>

<style scoped>
.top-container {
  position: center;
  width: 95%;
}

.container {
  position: center;
  height: 100%;
  width: 95%;
}

.info-box {
  width: 80%;
}

td:first-child {
  width: 30%;
  background-color: #d8d8d8;
}

.aaa-table td:first-child{
  width: 20%;
  background-color: #ffffff;
}

td:nth-child(3) {
  width: 20%;
}

td, tr {
  border: 1px solid black;
  padding-top: 17px;
  padding-bottom: 12px;
  word-spacing: 9px;
  text-align: center;
}

.left-box {
  float: left;
  width: 50%;
}

.right-box {
  float: right;
  height: 50%;
  width: 50%;
}

.bin-box {
  float: right;
  padding-bottom: 30%;
  width: 50%;
}

.payNow-r {
  position: center;
  text-align: center;
  width: 20%;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
  float: right;
  margin-right: 25%;
}

.payNow-l {
  position: center;
  text-align: center;
  width: 20%;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
  float: left;
  margin-left: 25%;
}
</style>