<template>
  <div>
    <label for="zipCode">우편주소</label>
    <input type="text" id="zipCode" v-model="zipCode">
    <button @click="showApi()">우편번호 찾기</button>
    <label for="address" >주소</label>
    <input type="text" id="address" v-model="address">
    <label for="detailAddress">상세주소</label>
    <input type="text" id="detailAddress" v-model="detailAddress">
    <hr>
    <button @click="boxMovePay"> 결제</button>
  </div>

</template>

<script>
import axios from "axios";

export default {
  name: "RoundMoveBox",
  props: {
    form: {}
  },
  data() {
    return {
      zipCode: '',
      address: '',
      detailAddress: '',
    }
  },
  mounted() {

  },
  methods: {
    boxMovePay() {
      if (this.address == '' || this.zipCode == '') {
        alert('이동할 주소를 입력하세요')
        return
      }
      this.$router.push({
        name: "roundToBoxMovePay",
        params: {
          userId:this.form.userId,
          storageName:this.form.storageName,
          boxName:this.form.boxName,
          useBoxCode:this.form.useBoxCode,
          zipCode: this.zipCode,
          address: this.address,
          detailAddress: this.detailAddress
        }
      })
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
#map {
  width: 400px;
  height: 400px;
}
</style>