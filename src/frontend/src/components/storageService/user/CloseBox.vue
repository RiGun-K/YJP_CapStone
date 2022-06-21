<template>
  <div class="container">
    <br><br>
    <h2>사용 해지</h2>
    <br><br>
    <div class="left-box">
      <div class="storage-info-box">
        <h4>보관</h4>
        <div class="info-box">
          <table>
            <tr>
              <td>보관소</td>
              <td>{{ storage.storageName }}</td>
            </tr>
            <tr>
              <td>보관함</td>
              <td>{{ boxName }}</td>
            </tr>
          </table>
        </div>
      </div>
      <br>
      <div class="item-info-box">
        <h4>보관 장비</h4>
        <div class="info-box">
          <table>
            <tr v-for="(item,index) in myItemList">
              <td>{{ index + 1 }}</td>
              <td>{{ item.memEquipmentName }}</td>
              <td>{{ item.memEquipmentCount }}개</td>
            </tr>
          </table>
        </div>
      </div>
      <br>
      <div class="time-range-box">
        <h4>기간</h4>
        <div class="info-box">
          <table>
            <tr>
              <td>시작일</td>
              <td>{{ startTime }}</td>
            </tr>
            <tr>
              <td>종료일</td>
              <td>{{ endTime }}</td>
            </tr>
          </table>
        </div>
      </div>
      <br><br>
      <br>
      <div>
        <button class="payNow-l" @click="this.$router.push({name:'myBox'})">뒤로가기</button>
        <button class="payNow-r" @click="closeBox">연장 해지</button>
      </div>
      <br>
      <br><br><br>

    </div>
  </div>

</template>

<script>
import axios from "axios";

export default {
  name: "CloseBox",
  data() {
    return {
      useBoxCode: '',
      useBox: {},
      storage: {},
      myItemList: [],
      boxName: '',
      startTime: '',
      endTime: '',
    }
  },
  mounted() {
    this.useBoxCode = this.$route.params.useBoxCode
    this.getBackData(this.$route.params.useBoxCode)
    this.getMyItem(this.$route.params.useBoxCode)
  },
  methods: {
    getBackData(useBoxCode) {
      axios.get('/api/getUseBox/' + useBoxCode)
          .then(res => {
            this.useBox = res.data
            this.boxName = this.useBox.storageBoxCode.storageBoxName
            this.dataReturn(this.useBox.useStorageStartTime, this.useBox.useStorageEndTime)
            this.getStorageName(this.useBox.storageBoxCode.storageBoxCode)
          })
          .catch(err => {
            console.log(err)
          })
    },
    getMyItem(useBoxCode) {
      axios.get('/api/getBoxInItem/' + useBoxCode)
          .then(res => {
            this.myItemList = res.data
          })
          .catch(err => {
            console.log(err)
          })
    },
    getStorageName(boxCode) {
      axios.get('/api/getStorageToBox/' + boxCode)
          .then(res => {
            this.storage = res.data
          })
          .catch(err => {
            console.log(err)
          })
    },
    dataReturn(startTime, endTime) {
      const arrDayStr = ['일', '월', '화', '수', '목', '금', '토']
      const start = new Date(startTime)
      const end = new Date(endTime)
      this.startTime = start.getFullYear() + '년' + (start.getMonth() + 1) + '월' + start.getDate() + '일 (' + arrDayStr[start.getDay()] + ')'
      this.endTime = end.getFullYear() + '년' + (end.getMonth() + 1) + '월' + end.getDate() + '일 (' + arrDayStr[end.getDay()] + ')'
    },
    closeBox() {
      axios.post('/api/closeBox/' + this.useBoxCode)
          .then(res => {
            if (res.data.result == 'ok') {
              alert('다음달 사용 해지 되었습니다 ')
              this.$router.push({name: "myBox"})
            } else {
              alert('error')
            }
          })
          .catch(err => {
            console.log(err)
          })
    },
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
.payNow-l{
  position: center;
  text-align: center;
  width: 20%;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
  float: left;
  margin-left: 20%;
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
  margin-right: 20%;
}
</style>