<template>
  <div class="container">
    <table>
      <tbody>
      <tr>
        <td>보관함</td>
        <td>{{ box.storageBoxName }}</td>
      </tr>
      <tr>
        <td>상태</td>
        <td v-if="box.storageBoxState == 'x'">
          <h5>비활성화 <button class="payNow" style="float: right" @click="onBox()">활성화</button></h5>
        </td>
        <td v-else>
          <h5>사용안함 <button class="payNow" style="float: right" @click="offBox()">비활성화</button></h5>

        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "BoxMadalDetail",
  props: {
    boxCode: ''
  },
  mounted() {
    this.getBoxInfo()
  },
  watch: {
    boxCode: function () {
      this.getBoxInfo()
    }
  },
  data() {
    return {
      box: {},
    }
  },
  methods: {
    getBoxInfo() {
      this.box = {}
      axios.get('/api/notUsingBoxInfo/' + this.boxCode)
          .then(res => {
              this.box = res.data
          })
          .catch(err => {
            console.log(err)
          })
    },
    onBox() {
      axios.get('/api/onBox/' + this.boxCode)
          .then(res => {
            if (res.data.result == 'ok') {
              alert('보관함이 활성화 되었습니다.')
              this.getBoxInfo()
              this.$emit('updata')
            }
          })
          .catch(err => {
            console.log(err)
          })
    },
    offBox() {
      axios.get('/api/offBox/' + this.boxCode)
          .then(res => {
            if (res.data.result == 'ok') {
              alert('보관함이 비활성화 되었습니다.')
              this.getBoxInfo()
              this.$emit('updata')
            } else {
              alert('에러')
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
  width: 80%;
}
td:first-child {
  width: 20%;
  background-color: #d8d8d8;
}
th, td {
  border: 1px solid black;
  padding: 5px;
  padding-left: 5%;
  width: 50%;
}

.payNow {
  position: center;
  text-align: center;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
}
</style>
