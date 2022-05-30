<template>
  박스 이동 결제
  <div>
    <div>
      보관 정보
    </div>
    <div>
      <table>
        <tr>
          <td>보관소 명</td>
          <td>{{ moveBoxInfo.storageName }}</td>
        </tr>
        <tr>
          <td>보관함</td>
          <td>{{ moveBoxInfo.boxName }}</td>
        </tr>
      </table>
    </div>
    <div>
      보관장비
      <div v-if="myItem.length > 1">
        <table>
          <thead>
          <tr>
            <th colspan="2">장비</th>
            <th>수량</th>
          </tr>
          </thead>
          <tbody v-for="(item,index) in myItem" :key="index">
          <tr>
            <td>{{ index + 1 }}</td>
            <td>{{ item.memEquipmentName }}</td>
            <td>{{ item.memEquipmentCount }}</td>
          </tr>
          </tbody>
        </table>
      </div>
      <div v-else>
        보관중인 장비가 없습니다.
      </div>
    </div>
  </div>
  <hr>
  <div>
    이동 장소
    <div>
      <table>
        <tr>
          <td>주소</td>
          <td>{{ data.address }}</td>
        </tr>
        <tr v-if="data.detailAddress != ''">
          <td>상세주소</td>
          <td>{{ data.detailAddress }}</td>
        </tr>
      </table>
    </div>
  </div>
  <hr>
  <div>
    <button @click="pay">결제</button>
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
    this.boxinItem()
  },
  data() {
    return {
      data: {},
      moveBoxInfo: {},
      myItem: {},
    }
  },
  methods: {
    boxinItem() {
      axios.get('/api/getBoxInItem/' + this.data.useBoxCode)
          .then(res => {
            this.myItem = res.data
          })
          .catch(err => {
            console.log(err)
          })
    },
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
    pay() {
      const form = {
        userId: store.getters.getLoginState.loginState,
        useBoxCode: this.data.useBoxCode,
        zipCode: this.data.zipCode,
        address: this.data.address,
        detailAddress: this.data.detailAddress
      }
      console.log(form)
      axios.post('/api/roundMoveBox', form)
          .then(res => {
            console.log(res)
            if (res.data.result == 'ok') {
              alert('결제되었습니다')
              this.$store.commit('clearMoveBoxInfo')
              this.$router.push({name: "myBox"})
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

</style>