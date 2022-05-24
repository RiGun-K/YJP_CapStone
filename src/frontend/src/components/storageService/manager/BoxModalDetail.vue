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
        <td v-if="box.boxState == 0">빈 상태</td>
        <td v-else-if="box.boxState == 1">결제완료
          <button @click="updateState">보관완료</button>
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
        <td v-else>없음</td>
      </tr>
      <tr>
        <td>장비 수리</td>
        <td v-if="box.useStorageState == 7">장비 수리 신청</td>
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
        <td>사용안함</td>
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
    boxCode: null
  },
  mounted() {
    this.getBoxInfo()
  },
  watch: {
    boxCode() {
      this.getBoxInfo()
    }
  },
  data() {
    return {
      chk: false,
      box: {},
      moveInfo: {},
    }
  },
  methods: {
    getBoxInfo() {
      this.box = {}
      axios.get('/api/getBox/' + this.boxCode.storageBoxCode)
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

              console.log(this.box.updateusbCode)

              axios.get('/api/moveBoxInfo/' + this.box.updateusbCode)
                  .then(res => {
                    const data = res.data[0]
                    this.moveInfo.storageCode = data[0]
                    this.moveInfo.storageName = data[1]
                    this.moveInfo.storageBoxCode = data[2]
                    this.moveInfo.storageBoxName = data[3]
                  })
              .catch(err=>{
                console.log(err)
              })
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
            console.log(res)
            alert("사용자 보관 확인 되었습니다")
            this.getBoxInfo()
          })
          .catch(err => {
            console.log(err)
          })
    },
    moveStateUpDate() {
      //버튼은 이동중으로 변경하고 받는곳은 도착버튼으로 변경
      axios.get('/api/moveStateUpdate/'+this.box.useStorageCode+'/'+this.box.updateusbCode)
      .then(res=>{
        console.log(res.data.result)
        alert("이동합니다")
        this.getBoxInfo()
      })
    },
    endMoveupdate(){
      // 도착하면 누르는 버튼
      axios.get('/api/endmoveUpdate/'+this.box.useStorageCode)
      .then(res=>{
        console.log(res.data.result)
        alert("보관되었습니다")
        this.getBoxInfo()
      })
      .catch(err=>{
        console.log(err)
      })
    },
  },
}
</script>

<style scoped>

</style>