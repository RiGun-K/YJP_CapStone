<template>
  <div>
    <div>
      <h5>현재 박스 위치</h5>
      <div>
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
    <div>
      <h5>이동할 박스 위치</h5>
      <div>
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
    <div>
      <h5>보관중인 장비</h5>
      <div>
        <table>
          <thead>
          <tr>
            <th colspan="3">보관중인장비</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(item,index) in myItem">
            <td>{{ index + 1 }}</td>
            <td>{{ item.memEquipmentName }}</td>
            <td>{{ item.memEquipmentCount }}</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div>
      이동에 대한 금액 + 보관함 사이즈 변경 추가 금액
    </div>
    <div>
      <button @click="pay()">결제</button>
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
    console.log(this.newBoxCode)
    console.log(this.beforeBoxInfo)
    this.getBoxInfo()
    this.getItem()
  },
  data() {
    return {
      newBoxCode: '',
      beforeBoxInfo:{},
      afterBoxInfo: {},
      myItem:{},
    }
  },
  methods: {
    getItem(){
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
            alert('결제되었습니다')

          }).catch(err => {
        console.log(err)
      })
    }
  }
}
</script>

<style scoped>

</style>