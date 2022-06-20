<template>

  <div class="container">
    <div class="top-container">
      <h2>결제</h2>
    </div>
    <hr>
    <div>
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
        <div class="storage-info-box">
          <h4>보관</h4>
          <div class="info-box">
            <table>
              <tr>
                <td>보관소</td>
                <td>{{ form.storageName }}</td>
              </tr>
              <tr>
                <td>보관함</td>
                <td>{{ form.storageBoxName }}</td>
              </tr>
            </table>
          </div>
        </div>
        <div class="item-info-box">
          <h4>보관할 나의 장비</h4>
          <div class="info-box">
            <table>
              <tr v-for="(item,index) in form.item" :key="index">
                <td>{{ index + 1 }}</td>
                <td>{{ item.memEquipmentName }}</td>
                <td>{{ item.memEquipmentCount }}개</td>
              </tr>
            </table>
          </div>
        </div>
        <div class="time-range-box">
          <h4>기간</h4>
          <div class="info-box">
            <table>
              <tr>
                <td>시작일</td>
                <td>{{ form.useStorageStartTime }}</td>
              </tr>
              <tr>
                <td>종료일</td>
                <td>{{ form.useStorageEndTime }}</td>
              </tr>
            </table>
          </div>
        </div>
      </div>
      <div class="bin-box"></div>
      <div class="right-box">
        <div class="price-info-box">
          <h4>금액</h4>
          <div class="info-box">
            <table>
              <tr>
                <td>금액</td>
                <td>{{ form.price }}원</td>
              </tr>
            </table>
          </div>
        </div>
        <hr>
        <div class="pat-info-box">
          <h5>구매조건 확인 및 결제대행 서비스 약관 동의
            <button @click="checkBuy">보기</button>
          </h5>
          <h5>개인정보 제3자 제공 동의
            <button>보기</button>
          </h5>
          <h5 class="buy-now-info-check">위 주문 내용을 확인하였으며, 회원 본인은 개인정보 이용 및 제공(해외직구의 경우 국외제공) 및 결제에 동의합니다.</h5>

        </div>
      </div>
    </div>
    <div class="bin-box-2"></div>
    <hr>
    <div class="btn-box">
      <button class="payNow-l" @click="$router.push({name: 'userStorageDetail'})">돌아가기</button>
      <button class="payNow-r" @click="paymentBtn">결제하기</button>
    </div>

  </div>
  <div class="bin-box-3"></div>
</template>

<script>
import axios from "axios";
import store from "@/store";

export default {
  name: "storagePayPage",
  data() {
    return {
      member:{},
      form: {
        storageName: '',
        storageBoxName: '',
        storageBoxCode: '',
        useStorageStartTime: '',
        useStorageEndTime: '',
        price: '',
        item: []
      },
    }
  },
  created() {
    this.upload()
    this.memberInfo()
  },
  mounted() {
    const IMP = window.IMP
    IMP.init('imp35975601')
  },
  methods: {
    upload() {
      this.form = this.$store.state.infoStorage
      this.form.useStorageStartTime = this.$store.state.timeStorage.useStorageStartTime
      this.form.useStorageEndTime = this.$store.state.timeStorage.useStorageEndTime
      this.form.item = this.$store.state.itemStorage
      this.form.userId = store.getters.getLoginState.loginState

      console.log(this.form)
      axios.get('/api/storageBoxGet/' + this.form.storageName+'/'+this.form.storageBoxName)
          .then(res => {
            this.form.storageBoxCode = res.data
          }).catch(err => {
        console.log(err)
      })
    },
    memberInfo(){
      axios.get('/api/myAddress/'+this.form.userId)
      .then(res=>{
        this.member = res.data
        console.log(this.member)
      })
      .catch(err=>{
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
      //     name: this.form.storageName +'보관소'+this.form.storageBoxName+'보관함',
      //     amount: this.form.price/100,
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
      this.savePay()
    },
    savePay() {
      const jsonData = {
        userId: this.form.userId,
        storageBoxCode: this.form.storageBoxCode,
        useStorageStartTime: new Date(this.form.useStorageStartTime),
        useStorageEndTime: new Date(this.form.useStorageEndTime),
        price: this.form.price,
        item: []
      }
      for (let i = 0; i < this.form.item.length; i++) {
        jsonData.item.push(this.form.item[i].memEquipmentCode)
      }
      axios.post('/api/payBox', jsonData)
          .then(res => {
            this.$store.commit('cartStorageClear')
            this.$store.commit('storageClear')
            console.log(res)
            this.$router.push({name:'storageComplete'});
          })
          .catch(err => {
            alert('결제가 실패 되었습니다.')
            console.log(err)
          })

    },
    checkBuy() {
      if (this.buyCheck === true) {
        this.buyCheck = false
      } else {
        this.buyCheck = true
      }
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
  height: 350px;
  width: 50%;
}

.bin-box-2{
  height: 20px;
  width: 50%;
}

.bin-box-3{
  height: 800px;
  width: 100%;
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
