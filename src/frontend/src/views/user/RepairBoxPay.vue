<template>
  <div class="container">
    <h2>수리 결제</h2>
    <div class="left-box">
      <div class="user-info">
        <h4>구매자</h4>
        <div class="info-box">
          <table>
            <tr>
              <td>이름</td>
              <td>{{ member.mname }}</td>
            </tr>
            <tr>
              <td>이메일</td>
              <td>{{ member.mmail }}</td>
            </tr>
            <tr>
              <td>전화번호</td>
              <td>{{ member.mph }}</td>
            </tr>
          </table>
        </div>
      </div>
      <br>
      <div class="storage-info-box">
        <h4>보관</h4>
        <div class="info-box">
          <table>
            <tr>
              <td>보관소</td>
              <td>{{ storageInfo.storageName }}</td>
            </tr>
            <tr>
              <td>보관함</td>
              <td>{{ storageInfo.boxName }}</td>
            </tr>
          </table>
        </div>
      </div>
      <div>
        <h4>수리신청항목</h4>
        <div v-for="repair in repairList">
          <div>
            <h5>{{repair.item.memEquipmentName}}의 수리</h5>
          </div>
          <div class="info-box">
            <table>
              <tbody>
              <tr>
                <td>{{ repair.option.buyName }}</td>
                <td>{{ repair.option.buyEx }}</td>
                <td>{{ repair.option.buyPrice }}원</td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <br>
      <br>
      <br>
      <br>
      <br>
    </div>
    <div class="bin-box">
      <div>
        <h5>추가 요청사항</h5>
        <div>
          <textarea v-model="requestText"></textarea>
        </div>
        <table class="aaa">
          <tr>
            <td>금액</td>
            <td>{{ price }}원</td>
          </tr>
        </table>
      </div>
    </div>
    <div class="right-box">
      <div>
        <div>
          <button class="payNow-r" @click="paymentBtn()">결제</button>
          <button class="payNow-l" @click="$router.push({name:'myBox'})">취소</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import store from "@/store";

export default {
  name: "RepairBoxPay",
  mounted() {
    this.useCode = this.$route.params.useBoxCode
    this.repairList = this.$store.state.careItemInfo
    console.log(this.repairList)
    this.getMemberData()
    this.getBoxInfo()
    this.allPrice()
  },
  data(){
    return{
      useCode:'',
      member:{},
      repairList:[],
      option:'',
      storageInfo:{},
      requestText:'',
      price:0,
    }
  },
  methods:{
    allPrice(){
      for (let i = 0; i < this.repairList.length; i++) {
        this.price = this.price + this.repairList[i].option.buyPrice
      }
    },
    getMemberData(){
      axios.get('/api/myAddress/' + store.getters.getLoginState.loginState)
          .then(res => {
            this.member = res.data
          })
          .catch(err => {
            console.log(err)
          })
    },
    getBoxInfo() {
      axios.get('/api/moveBoxInfo/' + this.useCode)
          .then(res => {
            console.log(res.data)
            this.storageInfo.storageName = res.data[0][1]
            this.storageInfo.boxName = res.data[0][3]
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
          name: '장비 수리 결제',
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
            this.savePay()
          }
        })
      }

    },
    savePay(){
      const formData = {}
      const data = []
      for (let i = 0; i < this.repairList.length; i++) {
        const form ={}
        form.memEquipmentCode = this.repairList[i].item.memEquipmentCode
        form.buyId = this.repairList[i].option.buyId
        data.push(form)
      }
      formData.list = data
      formData.mid = this.member.mid
      formData.text = this.requestText
      formData.price = this.price
      axios.post('/api/postCarePay',formData)
          .then(res => {
            console.log(res.data.result)
          })
          .catch(err => {
            console.log(err)
          })
      this.$store.commit('clearCareItem')
      this.$router.push({name:'storageComplete'})
    }
  },
}
</script>

<style scoped>

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
.aaa{
  width: 80%;
}

.right-box {
  float: right;
  height: 50%;
  width: 50%;
}

textarea{
  width: 80%;
  height: 30%;
}

.bin-box {
  float: right;
  padding-bottom: 10%;
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