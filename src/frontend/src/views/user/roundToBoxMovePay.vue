<template>
  <div class="container">
    <div class="top-container">
      <h2>이동 결제</h2>
    </div>
    <br>
    <div class="left-box">
      <div class="storage-info-box">
        <h4>보관</h4>
        <div class="info-box">
          <table>
            <tr>
              <td>보관소</td>
              <td>{{ moveBoxInfo.storageName }}</td>
            </tr>
            <tr>
              <td>보관함</td>
              <td>{{ moveBoxInfo.boxName }}</td>
            </tr>
          </table>
        </div>
      </div>
      <br>
      <div class="item-info-box">
        <h4>보관할 나의 장비</h4>
        <div class="info-box">
          <table>
            <tr v-for="(item,index) in myItem" :key="index">
              <td>{{ index + 1 }}</td>
              <td>{{ item.memEquipmentCode.memEquipmentName }}</td>
              <td>{{ item.count }}개</td>
            </tr>
          </table>
        </div>
      </div>
      <br>
      <div class="move-info-box">
        <h4>배송지</h4>
        <div class="info-box">
          <table>
            <tbody>
            <tr>
              <td>받는사람</td>
              <td>{{ data.name }}</td>
            </tr>
            <tr>
              <td>연락처</td>
              <td>{{ data.tel }}</td>
            </tr>
            <tr>
              <td>우편번호</td>
              <td>{{ data.zipCode }}</td>
            </tr>
            <tr>
              <td>주소</td>
              <td>{{ data.address }}</td>
            </tr>
            <tr>
              <td>상세주소</td>
              <td>{{ data.detailAddress }}</td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
      <br>
    </div>
    <br>
    <br>
    <br>
    <br>
    <div class="bin-box"></div>
    <div class="right-box">
      <br>
      <hr>
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
        <button @click="$router.push({name:'myBox'})">취소</button>
        <button class="payNow-r" @click="paymentBtn()">결제</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import store from "@/store";

export default {
  name: "roundToBoxMovePay",
  mounted() {
    this.data = this.$store.state.moveBoxInfo
    this.getBoxInfo()
    this.myItem = this.data.myItem
    for (let i = 0; i < this.myItem.length; i++) {
      if (this.myItem[i].count == 0){
        this.myItem.splice(i,1)
      }
    }
  },
  data() {
    return {
      data: {},
      moveBoxInfo: {},
      myItem: [],
      price: 10000
    }
  },
  methods: {
    getBoxInfo() {
      axios.get('/api/moveBoxInfo/' + this.data.useBoxCode)
          .then(res => {
            this.moveBoxInfo.storageName = res.data[0][1]
            this.moveBoxInfo.boxName = res.data[0][3]
          })
          .catch(err => {
            console.log(err)
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
          name: this.moveBoxInfo.storageName +'보관소'+this.moveBoxInfo.BoxName+'보관함 장소 배송',
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
      const form = {
        userId: store.getters.getLoginState.loginState,
        name: this.data.name,
        useBoxCode: this.data.useBoxCode,
        zipCode: this.data.zipCode,
        address: this.data.address,
        detailAddress: this.data.detailAddress,
        tel : this.data.tel,
        list: this.data.list,
        price: this.price
      }
      console.log(form)
      axios.post('/api/roundMoveBox', form)
          .then(res => {
            console.log(res)
            if (res.data.result == 'ok') {
              this.$store.commit('clearMoveBoxInfo')
              this.$router.push({name: "storageComplete"})
            } else if (res.data.result == 'umm') {
              alert('장비가 보관중이 아닙니다')
            } else {
              alert('보관중이 아니거나 보관함에 장비가 없습니다.')
            }
          })
          .catch(err => {
            console.log(err)
          })
    }
  }
}
</script>

<style scoped>
.top-container{
  position: center;
  width: 95%;
}
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
}
td:nth-child(3) {
  width: 20%;
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
  height: 200%;
  width: 50%;
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
}
</style>