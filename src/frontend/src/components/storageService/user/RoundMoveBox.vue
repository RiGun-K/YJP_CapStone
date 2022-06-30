<template>
  <div class="container">
    <div>
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
        <h4>보관 장비</h4>
        <div class="info-box">
          <table>
            <tr v-for="(item,index) in myItem">
              <td>{{ index + 1 }}</td>
              <td>{{ item.memEquipmentCode.memEquipmentName }}</td>
              <td>
                <select v-model="item.count">
                  <option value="0">0</option>
                  <option v-for="inx in item.boxItemCount" :value="inx">{{ inx }}</option>
                </select>개
              </td>
            </tr>
          </table>
        </div>
      </div>
      <br>
      <div class="move-info-box">
        <h4>배송지</h4>
        <div class="info-box">
          <table>
            <tr>
              <td>수령인</td>
              <td>
                <input type="text" v-model="name">
              </td>
            </tr>
            <tr>
              <td>전호번호</td>
              <td>
                <input type="text" v-model="tel">
              </td>
            </tr>
            <tr>
              <td>우편주소</td>
              <td>
                <input type="text" v-model="zipCode">
                <button class="payNow-c" @click="showApi()">우편번호 찾기</button>
              </td>
            </tr>
            <tr>
              <td>주소</td>
              <td><input type="text" v-model="address"></td>
            </tr>
            <tr>
              <td>상세주소</td>
              <td><input type="text" v-model="detailAddress"></td>
            </tr>
          </table>
        </div>
      </div>
      <br>
      <div class="bin-box">
        <button class="payNow-b" @click="boxMovePay"> 결제</button>
      </div>
    </div>

  </div>
</template>

<script>
import axios from "axios";
import store from "@/store";

export default {
  name: "RoundMoveBox",
  props: {
    useBoxCode: ''
  },
  data() {
    return {
      moveBoxInfo: {},
      zipCode: '',
      address: '',
      detailAddress: '',
      myItem: {},
      name: '',
      tel: '',
    }
  },
  mounted() {
    this.boxInfo()
    this.getItem()
  },
  methods: {
    boxInfo() {
      axios.get('/api/moveBoxInfo/' + this.useBoxCode)
          .then(res => {
            this.moveBoxInfo.storageName = res.data[0][1]
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
    boxMovePay() {
      if (this.address == '' || this.zipCode == '') {
        alert('이동할 주소를 입력하세요')
        return
      }
      let list = []
      for (let i = 0; i <this.myItem.length; i++) {
        let form = {}
        form.itemCode = this.myItem[i].boxItemCode
        form.count = this.myItem[i].count
        list.push(form)
      }
      let data = {
        name: this.name,
        useBoxCode: this.useBoxCode,
        zipCode: this.zipCode,
        address: this.address,
        detailAddress: this.detailAddress,
        tel : this.tel,
        list: list,
        myItem : this.myItem
      }
      this.$store.commit('moveBoxInfo', data)
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
.container {
  position: center;
  width: 95%;
}

.info-box {
  width: 50%;
}

td:first-child {
  width: 30%;
}

td:nth-child(2) {
  text-align: left;
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

.payNow-b {
  position: center;
  text-align: center;
  width: 20%;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
  float: right;
}

.payNow-c {
  position: center;
  text-align: center;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
}

.bin-box {
  width: 50%;
}
</style>