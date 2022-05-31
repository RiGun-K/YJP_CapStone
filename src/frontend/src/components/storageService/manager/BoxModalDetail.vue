<template>
  <div v-if="chk" class="storageBodyDiv">
    <div>
      <h5>보관함</h5>
      <div>
        <table>
          <thead>
            <tr>
              <th>보관함</th>
              <th>상태</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>{{ box.boxName }}</td>
              <td>{{ changeState(box.boxState) }}</td>
              <td v-if="box.boxState==1">
                <button @click="updateState">보관완료</button>
              </td>
              <td v-if="box.boxState==6">
                <button @click="updateState">해지</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div>
      <h5>사용중인 사용자 정보</h5>
      <div>
        아이디 : {{ box.nickName }}
      </div>
      <div>
        상태 : {{ useState(box.useStorageState) }}
      </div>
    </div>
    <hr>
    <div  v-if="moveChk">
      <h5>보관소 이동 내역</h5>
      <div>
        <h5>이동 정보</h5>
        <div v-if="box.useStorageState == 3">
          <div>
            <table>
              <thead>
                <tr>
                  <th>배송 도착지</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>보관소</td>
                  <td>{{ moveInfo.storageName }}</td>
                </tr>
                <tr>
                  <td>보관함</td>
                  <td>{{ moveInfo.storageBoxName }}</td>
                </tr>
              </tbody>
            </table>
          </div>
          <div>
            <button @click="moveStateUpDate()">배송 시작</button>
          </div>
        </div>
        <div v-if="box.useStorageState == 4 || box.useStorageState == 5">
          <div>
            <table>
              <thead>
                <tr>
                  <th>배송 출발지</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>보관소</td>
                  <td>{{ moveInfo.storageName }}</td>
                </tr>
                <tr>
                  <td>보관함</td>
                  <td>{{ moveInfo.storageBoxName }}</td>
                </tr>
              </tbody>
            </table>
          </div>
          <div v-if="box.useStorageState == 5">
            <button @click="endMoveupdate()">도착</button>
          </div>
        </div>
      </div>
    </div>
    <div  v-if="careChk">
      <h5>수리 신청 내역</h5>
      <div>
        <div v-if="box.useStorageState == 6">
          <h5>수리 신청</h5>
          <div>
            수리할 장비 목록

          </div>
          <div>
            요청사항

          </div>
        </div>
      </div>
    </div>
    <div v-if="box.useStorageState == 9">
      <h5>배송</h5>
      <div>
        <div>
          <h5>주소지</h5>
        </div>
        <div>
          <button>배송시작</button>
        </div>
      </div>
    </div>
    <div v-if="box.useStorageState == 1">
      <div>
        <h5>남은 기간</h5>
      </div>
      <div>
        <h5>주소지</h5>
      </div>
      <div>
        <button>배송시작</button>
      </div>
    </div>
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
      moveInfo: {},
      moveChk: false,
      careChk: false,
    }
  },
  methods: {
    changeState(state) {
      switch (state) {
        case '1':
          return "결제완료"
          break
        case '2':
          return "사용중"
          break
        case '3':
          return "장비이동 신청 "
          break
        case '4':
          return "장비이동 신청"
          break
        case '5':
          return "수리 신청"
          break
        case '6':
          return "해지"
          break
        case '7':
          return "이동"
          break
      }
    },
    useState(useState) {
      switch (useState) {
        case '0':
          return "보관전"
          break
        case '1':
          return "구독종료"
          break
        case '2':
          return "사용중"
          break
        case '3': case '4':
          return "보관함이동 신청 "
          break
        case '5':
          return "보관함이동 중"
          break
        case '6':
          return "수리신청"
          break
        case '7':
          return "수리중"
          break
        case '8':
          return "수리완료"
          break
        case '9':
          return "배송 신청"
          break
      }
    },
    pickChange(){
      switch (this.box.useStorageState){
        case '3':case'4':case'5':
          this.moveChk = true;
          this.careChk = false;
          break
        case '6':case'7':case'8':
          this.moveChk = false;
          this.careChk = true;
          break
        default:
          this.moveChk = false;
          this.careChk = false;
          break
      }
    },
    getBoxInfo() {
      this.box = {}
      axios.get('/api/getBox/' + this.boxCode)
          .then(res => {
            if (res.data[0].length > 2) {
              //보관함이 누군가 사용할 때 정보
              this.chk = true

              const data = {}
              data.nickName = res.data[0][0]
              data.boxCode = res.data[0][1]
              data.boxName = res.data[0][2]
              data.boxState = res.data[0][3]
              data.storageCode = res.data[0][4]
              data.useStorageCode = res.data[0][5]
              data.useStorageState = res.data[0][6].charAt(0)
              this.box = data
              this.box.updateusbCode = res.data[0][6].substring(1, res.data[0][6].length)
              this.pickChange()
              if (this.box.updateusbCode != undefined) {
                this.getMoveBox()
              }
            } else {
              // 보관소가 비어 있을 때
              this.chk = false
              this.box = res.data[0]
            }
          })
          .catch(err => {
            console.log(err)
          })
    },
    getMoveBox(){
      axios.get('/api/moveBoxInfo/' + this.box.updateusbCode)
          .then(res => {
            this.moveInfo.storageCode = res.data[0][0]
            this.moveInfo.storageName = res.data[0][1]
            this.moveInfo.storageBoxCode = res.data[0][2]
            this.moveInfo.storageBoxName = res.data[0][3]
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
