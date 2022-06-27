<template>
  <!--  사용중인 보관함 정보-->
  <div class="container">
    <h5>보관함</h5>
    <div class="box-info">
      <table>
        <tbody>
        <tr>
          <td>보관함</td>
          <td>{{ box.boxName }}</td>
        </tr>
        <tr>
          <td>상태</td>
          <td>{{ changeState(box.boxState) }}</td>
        </tr>
        <tr v-if="box.boxState==1 || (box.boxState==6 && remainder) ">
          <td></td>
          <td v-if="box.boxState==1">
            <button @click="updateState">보관완료</button>
          </td>
          <td v-if="box.boxState==6 && remainder">
            <button @click="updateState">해지</button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
    <br><br>
  </div>
  <div class="user-info">
    <h5>사용중인 사용자 정보</h5>
    <div class="info-box">
      <table>
        <thead>
        <tr>
          <th>아이디</th>
          <th>상태</th>
        </tr>
        </thead>
        <tbody>
        <tr style="border-bottom: 1px solid black">
          <td>{{ box.nickName }}</td>
          <td v-if="timeck">{{ useStateString }}</td>
          <td v-else>{{ useState(box.useStorageState) }}</td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
  <hr>
  <div v-if="moveChk">
    <h5>보관소 이동 내역</h5>
    <div>
      <h5>이동 정보</h5>
      <div v-if="box.useStorageState == 3">
        <div class="info-box">
          <table>
            <thead>
            <tr>
              <th>배송 도착지</th>
              <th></th>
            </tr>
            <tr>
              <th>보관소</th>
              <th>보관함</th>
            </tr>
            </thead>
            <tbody>
            <tr>
              <td>{{ moveInfo.storageName }}</td>
              <td>{{ moveInfo.storageBoxName }}</td>
            </tr>
            <tr>
              <td colspan="2"><button class="payNow" style="float: right" @click="moveStateUpDate()">배송 시작</button></td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
      <div v-if="box.useStorageState == 4 || box.useStorageState == 5">
        <div>
          <table class="info-box">
            <thead>
            <tr>
              <th>배송 출발지</th>
              <th></th>
            </tr>
            <tr>
              <th>보관소</th>
              <th>보관함</th>
            </tr>
            </thead>
            <tbody>
            <tr>
              <td>{{ moveInfo.storageName }}</td>
              <td>{{ moveInfo.storageBoxName }}</td>
            </tr>
            </tbody>
          </table>
        </div>
        <div v-if="box.useStorageState == 5">
          <button class="payNow" style="float: right" @click="endMoveupdate()">도착</button>
        </div>
      </div>
    </div>
  </div>
  <!--  <div v-if="careChk">-->
  <!--    <h5>수리 신청 내역</h5>-->
  <!--    <div>-->
  <!--      <div v-if="box.useStorageState == 6">-->
  <!--        <h5>수리 신청</h5>-->
  <!--        <div>-->
  <!--          수리할 장비 목록-->
  <!--        </div>-->
  <!--        <div>-->
  <!--          요청사항-->
  <!--        </div>-->
  <!--      </div>-->
  <!--    </div>-->
  <!--  </div>-->
  <div v-if="box.useStorageState == 9">
    <h5>배송</h5>
    <div>
      <div>
        <h5>주소지</h5>
        <div class="box-info">
          <table>
            <tbody>
            <tr>
              <td>받는 사람</td>
              <td>{{ order.deliveryGetter }}</td>
            </tr>
            <tr>
              <td>우편번호</td>
              <td>{{ order.deliveryZipcode }}</td>
            </tr>
            <tr>
              <td>주소</td>
              <td>{{ order.deliveryAddress }}</td>
            </tr>
            <tr>
              <td>연락처</td>
              <td>{{ order.deliveryGetterTel }}</td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
      <div>
        <button @click="moveDel()">배송시작</button>
      </div>
    </div>
  </div>
  <div v-if="box.useStorageState == 1">
    <div v-if="remainder">
      <h5>종료까지 {{ remainderTime }}일 남았습니다.</h5>
      <div>
        <h5>주소지</h5>
        <div class="box-info">
          <table>
            <tbody>
            <tr>
              <td>사용자</td>
              <td>{{ user.mnick }}</td>
            </tr>
            <tr>
              <td>우편번호</td>
              <td>{{ user.mzadd }}</td>
            </tr>
            <tr>
              <td>주소</td>
              <td>{{ user.madd }} {{user.mradd}}</td>
            </tr>
            <tr>
              <td>전화번호</td>
              <td>{{ user.mhp }}</td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
    <div>
      <button @click="moveDel()">배송시작</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "UsingBoxInfo",
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
      remainder: false,
      remainderTime: '',
      user: {},
      timeck: false,
      useStateString: '',
      del:'',
      order:{},
    }
  },
  methods: {
    moveDel(){
      this.box.useStorageState =2
    },
    changeState(state) {
      switch (state) {
        case '1':
          return "결제완료"
          break
        case '2':
        case '5':
          return "사용중"
          break
        case '3':
          return "장비이동 신청 "
          break
        case '4':
          return "장비이동 신청"
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
        case '3':
        case '4':
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
    pickChange() {
      switch (this.box.useStorageState) {
        case '3':
        case'4':
        case'5':
          this.moveChk = true;
          this.careChk = false;
          break
        case '6':
        case'7':
        case'8':
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
      let arry = []
      axios.get('/api/getBox/' + this.boxCode)
          .then(res => {
            arry = res.data
            this.inputDate(arry)
          })
          .catch(err => {
            console.log(err)
          })

    },
    inputDate(arry){
      let data = {}
      data.nickName = arry[0][0]
      data.boxCode = arry[0][1]
      data.boxName = arry[0][2]
      data.boxState = arry[0][3]
      data.storageCode = arry[0][4]
      data.useStorageCode = arry[0][5]
      data.useStorageState = arry[0][6]?.toString().charAt(0)||''
      this.box = data
      if (data.useStorageState == "9"){
        this.del = arry[0][6]?.substring(1, arry[6].length)||''
        this.delInfo()
      }else{
        this.box.updateusbCode = arry[0][6]?.substring(1, arry[0][6].length)||''
      }
      this.pickChange()
      if (this.box.updateusbCode != '') {
        this.getMoveBox()
      }
      this.ifCheck()
    },
    delInfo(){
      axios.get("/api/delInfo/"+this.del)
      .then(res=>{
        this.order = res.data
      })
          .catch(err => {
            console.log(err)
          })
    },
    ifCheck() {
      if (this.box.updateusbCode != '') {
        this.getMoveBox()
      }
      if (this.box.useStorageState == 1) {
        this.addUseBoxInfo()
      }
    },
    addUseBoxInfo() {
      axios.get('/api/moreUseBox/' + this.box.boxCode)
          .then(res => {
            this.box = {}
            const data = {}
            data.nickName = res.data[0][0]
            data.boxCode = res.data[0][1]
            data.boxName = res.data[0][2]
            data.boxState = res.data[0][3]
            data.storageCode = res.data[0][4]
            data.useStorageCode = res.data[0][5]
            data.useStorageState = res.data[0][6].toString().charAt(0)
            this.box = data
            this.checkMoreReNewal()

          })
          .catch(err => {
            console.log(err)
          })
    },
    checkMoreReNewal() {
      axios.get('/api/findUseState/' + this.box.boxCode)
          .then(res => {
            this.timeck = res.data
            if (this.timeck) {
              this.useStateString = '사용중'
            } else {
              this.getRemainder()
              this.getAddress()
            }
          })
          .catch(err => {
            console.log(err)
          })
    },
    getAddress() {
      axios.get('/api/UseBoxMemAddress/' + this.box.useStorageCode)
          .then(res => {
            this.user = res.data
          })
          .catch(err => {
            console.log(err)
          })
    },
    getRemainder() {
      axios.get('/api/remainderTime/' + this.box.useStorageCode)
          .then(res => {
            this.remainderTime = res.data
            if (this.remainderTime < 4) {
              this.remainder = true
            } else {
              this.remainder = false
            }
          })
          .catch(err => {
            console.log(err)
          })
    },
    getMoveBox() {
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
  },
}
</script>

<style scoped>

.box-info th {
  border: 1px solid black;
  padding: 5px;
  padding-left: 5%;
  width: 50%;
}

.box-info td {
  border: 1px solid black;
  padding: 5px;
  padding-left: 5%;
  width: 50%;
}

.box-info td:first-child {
  width: 20%;
  background-color: #d8d8d8;
}

th {
  text-align: center;
}

thead {
  background-color: #6e6e6e;
  color: white;
}

td {
  text-align: center;
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