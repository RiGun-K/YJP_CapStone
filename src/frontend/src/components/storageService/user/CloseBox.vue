<template>
  보관함 사용 해지
  <div>
    <button @click="this.$router.push({name:'myBox'})" class="renewal-box-back-btn">뒤로가기</button>
  </div>
  사용중인보관함
  <div>
    <table>
      <tbody>
      <tr>
        <td>보관소</td>
        <td>보관함</td>
      </tr>
      <tr>
        <td>{{ storage.storageName }}</td>
        <td>{{ boxName }}</td>
      </tr>
      </tbody>
    </table>
  </div>

  보관중인장비
  <div>
    <table>
      <tbody>
      <tr v-for="(item,index) in myItemList">
        <td>{{ index + 1 }}</td>
        <td>{{ item.memEquipmentName }}</td>
        <td>{{ item.memEquipmentCount }}</td>
      </tr>
      </tbody>
    </table>
  </div>
  사용기간
  <div>
    <table border="1" width="500px">
      <tbody>
      <tr>
        <td>시작일</td>
        <td>종료일</td>
      </tr>
      <tr>
        <td>{{ startTime }}</td>
        <td>{{ endTime }}</td>
      </tr>
      </tbody>
    </table>
  </div>
  <div>
    <button @click="closeBox" class="renewal-box-cancel">연장 해지</button>
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
.renewal-box-cancel:hover {
  color: white;
  background-color: #b2e2fd;
}
</style>