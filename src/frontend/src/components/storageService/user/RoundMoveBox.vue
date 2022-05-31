<template>
  <div>
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
            <td>{{moveBoxInfo.storageName}}</td>
          </tr>
          <tr>
            <td>보관함</td>
            <td>{{ moveBoxInfo.boxName }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div>
      <label for="zipCode">우편주소</label>
      <input type="text" id="zipCode" v-model="zipCode">
      <button @click="showApi()">우편번호 찾기</button>
      <label for="address" >주소</label>
      <input type="text" id="address" v-model="address">
      <label for="detailAddress">상세주소</label>
      <input type="text" id="detailAddress" v-model="detailAddress">
    </div>
    <hr>
    <div>
      <button @click="boxMovePay"> 결제</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "RoundMoveBox",
  props: {
    useBoxCode: ''
  },
  data() {
    return {
      moveBoxInfo:{},
      zipCode: '',
      address: '',
      detailAddress: '',
    }
  },
  mounted() {
    this.boxInfo()
  },
  methods: {
    boxInfo(){
      axios.get('/api/moveBoxInfo/' + this.useBoxCode)
          .then(res => {
            this.moveBoxInfo.storageName = res.data[0][1]
            this.moveBoxInfo.boxName = res.data[0][3]
          })
          .catch(err => {
            console.log(err)
          })
    },
    boxMovePay() {
      if (this.address == '' || this.zipCode == '') {
        alert('이동할 주소를 입력하세요')
        return
      }
      let data = {
        useBoxCode:this.useBoxCode,
        zipCode: this.zipCode,
        address: this.address,
        detailAddress: this.detailAddress
      }
      this.$store.commit('moveBoxInfo',data)
      this.$router.push({name: 'roundToBoxMovePay'})

    },
    showApi() {
      new window.daum.Postcode({
        oncomplete: (data) => {

          this.zipCode = data.zonecode
          this.address = data.roadAddress
        }
      }).open({popupKey: '주소검색'})
    },

  }
}
</script>

<style scoped>

</style>