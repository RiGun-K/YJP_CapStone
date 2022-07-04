<template>
  <div class="container">
    <div class="left-box">
      <div class="storage-info-box">
        <h4>보관</h4>
        <div class="info-box">
          <table>
            <tbody>
            <tr>
              <td>보관소</td>
              <td>{{ moveBoxInfo.storageName }}</td>
            </tr>
            <tr>
              <td>보관함</td>
              <td>{{ moveBoxInfo.boxName }}</td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
      <br>
      <div class="item-info-box">
        <h4>보관할 장비</h4>
        <div class="info-box">
          <table>
            <tr v-for="(item,index) in myItem">
              <td>{{ index + 1 }}</td>
              <td>{{ item.memEquipmentCode.memEquipmentName }}</td>
              <td><select v-model="item.count">
                <option value="0">0</option>
                <option v-for="inx in item.boxItemCount" :value="inx">{{ inx }}</option>
              </select>개</td>
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
              <td>{{ member.mname }}</td>
            </tr>
            <tr>
              <td>우편번호</td>
              <td>{{ member.mzadd }}</td>
            </tr>
            <tr>
              <td>주소</td>
              <td>{{ member.madd }}</td>
            </tr>
            <tr>
              <td>상세주소</td>
              <td>{{ member.mradd }}</td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
      <br>
    </div>
    <div class="bin-box">
      <h5>구매조건 확인 및 결제대행 서비스 약관 동의</h5>
      <h5>개인정보 제3자 제공 동의</h5>
      <h5 class="buy-now-info-check">위 주문 내용을 확인하였으며, 회원 본인은 개인정보 이용 및 제공(해외직구의 경우 국외제공) 및 결제에 동의합니다.</h5>
    </div>
    <div class="right-box">
      <div class="price-box">
        <h5>가격 {{ price }}원</h5>
      </div>
      <hr>
      <div>
        <button class="payNow-r" @click="paymentBtn()">결제</button>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import store from "@/store";

export default {
  name: "HomeMoveBox",
  props: {
    useBoxCode: ''
  },
  data() {
    return {
      myItem: {},
      boxName: '',
      storageName: '',
      zipCode: '',
      address: '',
      member: {},
      moveBoxInfo: {},
      price: 4500,
    }
  },
  mounted() {
    this.getItem()
    this.getAddress()
    this.getUseBoxInfo()
  },
  methods: {
    getUseBoxInfo() {
      axios.get('/api/moveBoxInfo/' + this.useBoxCode)
          .then(res => {
            this.moveBoxInfo.storageCode = res.data[0][0]
            this.moveBoxInfo.storageName = res.data[0][1]
            this.moveBoxInfo.boxCode = res.data[0][2]
            this.moveBoxInfo.boxName = res.data[0][3]
          })
          .catch(err => {
            console.log(err)
          })
    },
    getItem() {
      axios.get('/api/getBoxInItem/' + this.useBoxCode)
          .then(res => {
            this.myItem = res.data
            for (let i = 0; i < this.myItem.length; i++) {
              this.myItem[i].count = this.myItem[i].boxItemCount
            }
          })
          .catch(err => {
            console.log(err)
          })
    },
    getAddress() {
      axios.get('/api/myAddress/' + store.getters.getLoginState.loginState)
          .then(res => {
            this.member = res.data
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
          name: this.moveBoxInfo.storageName +'보관소'+this.moveBoxInfo.BoxName+'보관함 집 배송',
          amount: this.price/100,
          buyer_tel: '01012345678',
          confirm_url: ''
        }, (rsp) => {
          if (rsp.success) {

            this.savePay()

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
      let data = {}
      let list = []
      for (let i = 0; i <this.myItem.length; i++) {
        let form = {}
        form.itemCode = this.myItem[i].boxItemCode
        form.count = this.myItem[i].count
        list.push(form)
      }
      data.useBoxCode = this.useBoxCode
      data.member = this.member
      data.list = list
      console.log(data)
      axios.post('/api/homeToMovePay', data)
          .then(res => {
            console.log(res)
            this.$router.push({name: 'storageComplete'})
          })
          .catch(err => {
            console.log(err)
          })
    }
  }
}
</script>

<style scoped>
.container {
  position: center;
  width: 95%;
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
  height: 350px;
  width: 50%;
}

.info-box {
  width: 80%;
}

td:first-child {
  width: 30%;
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

.payNow-r {
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