<template>
  <div v-if="chk">
    <table>
      <tbody>
      <tr>
        <td>보관함</td>
        <td>{{ box.boxName }}</td>
      </tr>
      <tr>
        <td>상태</td>
        <td v-if="box.boxState == 0">
          <p>빈 상태</p>
        </td>
        <td v-else-if="box.boxState == 1">결제완료
          <button @click="updateState">보관완료</button>
        </td>
        <td v-else-if="box.boxState == 6">
          해지
          <button @click="clearBox()">빈상태</button>
        </td>

        <td v-else>사용중</td>

      </tr>
      <tr>
        <td>사용중인 사용자</td>
        <td>{{ box.userName }}</td>
      </tr>
      <tr>
        <td>사용자 추가정보</td>
        <td v-if="box.useStorageState == 0">구독시작</td>
        <td v-else-if="box.useStorageState == 1">구독종료</td>
        <td v-else-if="box.useStorageState == 2">구독 중</td>
      </tr>
      </tbody>
    </table>
    <hr>
    장비 이동 / 장비 수리 신청 내역
    <table>
      <tr>
        <td>장비 이동</td>
        <td v-if="box.useStorageState == 3">
          이동지 :
          <p v-if="boxCode.storageBoxState == '3'">
            보낼곳
            보관소 : {{ moveInfo.storageName }}
            보관함 : {{ moveInfo.storageBoxName }}
            장비 이동 신청
            <button @click="moveStateUpDate()">이동 시작</button>
          </p>
          <p v-else-if="boxCode.storageBoxState == '4'">
            받을곳
            보관소 : {{ moveInfo.storageName }}
            보관함 : {{ moveInfo.storageBoxName }}
          </p>

        </td>
        <td v-else-if="box.useStorageState == 4">이동 중</td>
        <td v-else-if="box.useStorageState == 5">이동 도착
          도착 하면 버튼 클릭
          <button @click="endMoveupdate()">도착</button>
        </td>
        <td v-else-if="box.useStorageState == 1">
          남은 기간

          <button>배송시작</button>
        </td>
        <td v-else-if="box.useStorageState == 9">
          주소지

          <button>배송시작</button>
        </td>
        <td v-else>없음</td>
      </tr>
      <tr>
        <td>장비 수리</td>
        <td v-if="box.useStorageState == 6">장비 수리 신청</td>
        <td v-else>없음</td>
      </tr>
    </table>
  </div>
  <div v-else>
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
      chk: false,
      box: {},
    }
  },
  methods: {
    getBoxInfo() {
      this.box = {}
      axios.get('/api/getBox/' + this.boxCode)
          .then(res => {
            if (res.data[0].length > 2) {
              const data = {
                userName: '',
                boxCode: '',
                boxName: '',
                boxState: '',
                storageCode: '',
                useStorageCode: '',
                useStorageState: ''
              }
              const datak = res.data[0]
              data.userName = datak[0]
              data.boxCode = datak[1]
              data.boxName = datak[2]
              data.boxState = datak[4]
              data.boxType = datak[5]
              data.storageCode = datak[6]
              data.useStorageCode = datak[7]
              data.useStorageState = datak[8].charAt(0)
              this.box = data
              this.box.updateusbCode = datak[8].substring(1, datak[8].length)
              this.chk = true

              if (this.box.updateusbCode != '') {
                axios.get('/api/moveBoxInfo/' + this.box.updateusbCode)
                    .then(res => {
                      const data = res.data[0]
                      this.moveInfo.storageCode = data[0]
                      this.moveInfo.storageName = data[1]
                      this.moveInfo.storageBoxCode = data[2]
                      this.moveInfo.storageBoxName = data[3]
                    })
                    .catch(err => {
                      console.log(err)
                    })
              }
            } else {
              this.box = res.data[0]
              this.chk = false
            }
          })
          .catch(err => {
            console.log(err)
          })
    },
    updateState() {
      axios.put('/api/boxStateUpdate/' + this.box.useStorageCode)
          .then(res => {
            alert("사용자 보관 확인 되었습니다")
            this.getBoxInfo()
            this.$emit('updata')
          })
          .catch(err => {
            console.log(err)
          })
    },
    moveStateUpDate() {
      //버튼은 이동중으로 변경하고 받는곳은 도착버튼으로 변경
      axios.get('/api/moveStateUpdate/' + this.box.useStorageCode + '/' + this.box.updateusbCode)
          .then(res => {
            if (res.data.result = 'ok') {
              alert("이동합니다")
              this.getBoxInfo()
              this.$emit('updata')
            }
          })
          .catch(err => {
            console.log(err)
          })
    },
    endMoveupdate() {
      // 도착하면 누르는 버튼
      axios.get('/api/endmoveUpdate/' + this.box.useStorageCode)
          .then(res => {
            alert("보관되었습니다")
            this.getBoxInfo()
            this.$emit('updata')
          })
          .catch(err => {
            console.log(err)
          })
    },
    clearBox() {
      axios.get('/api/clearBox/' + this.boxCode)
          .then(res => {
            if (res.data.result == 'ok') {
              alert('보관함이 비었습니다')
            } else {
              alert('에러')
            }
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
            }else {
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

</style>