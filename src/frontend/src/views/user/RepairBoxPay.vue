<template>
  <div>
    <h5>pay</h5>
    <div>
      <h5>user</h5>
      <div>
        <table>
          <tr>
            <td>이름</td>
            <td>{{ member.mnick }}</td>
          </tr>
          <tr>
            <td>이메일</td>
            <td>{{ member.mmail }}</td>
          </tr>
          <tr>
            <td>휴대폰 번호</td>
            <td>{{ member.mph }}</td>
          </tr>
        </table>
      </div>
    </div>
    <div>
      <h5>storage</h5>
      <div>
        <table>
          <thead>
          <tr>
            <th>사용중인보관함</th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <td>보관소</td>
            <td>{{storageInfo.storageName}}</td>
          </tr>
          <tr>
            <td>보관함</td>
            <td>{{ storageInfo.boxName }}</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div>
      <h5>수리신청항목</h5>
      <div v-for="repair in repairList">
        <div>
          <h6>{{repair.item.memEquipmentName}}의 수리</h6>
        </div>
        <div>
          <table>
            <tbody>
            <tr>
              <td>{{ repair.option.buyName }}</td>
              <td>{{ repair.option.buyEx }}</td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
    <div>
      <h5>추가 요청사항</h5>
      <div>
        <textarea v-model="requestText"></textarea>
      </div>
    </div>
    <div>
      <h5>price</h5>
      <div>
        <h6>{{price}}</h6>
      </div>
      <div>
        <button @click="paymentBtn">결제하기</button>
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
          name: 장비수리,
          amount: this.price/100,
          buyer_tel: '01012345678',
          confirm_url: ''
        }, (rsp) => {
          if (rsp.success) {

            this.savePay()

          } else {
            let msg = '결제에 실패하였습니다.'
            msg += '에러 내용 : ' + rsp.error_msg
            alert(msg)

          }
        })
      }
      // this.savePay()
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
      // data =
      console.log(formData)
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

</style>