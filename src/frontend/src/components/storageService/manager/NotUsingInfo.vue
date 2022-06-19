<template>
  <div class="storageBodyDiv">
    <table>
      <tbody>
      <tr>
        <td>보관함</td>
        <td>{{ box.storageBoxName }}</td>
      </tr>
      <tr>
        <td>상태</td>
        <td v-if="box.storageBoxState == 'x'">
          <h5>비활성화</h5>
          <button @click="onBox()">활성화</button>
        </td>
        <td v-else>
          <h5>사용안함</h5>
          <button @click="offBox()">비활성화</button>
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
.storageBodyDiv {
  margin-right: 5%;
  margin-left: 5%;
  margin-top: 1%;
  margin-bottom: 10%;
}

th, td {
  padding: 5px;
  padding-left: 5%;
  width: 50%;
}
</style>
