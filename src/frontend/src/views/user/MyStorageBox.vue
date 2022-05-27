<template>
  <div class="renewal-box">
    <h3>사용 중인 보관함 조회</h3>
    <div class="card" style="width: 50%; font-weight: bolder; margin-left: 7%">
      <div class="card-body">
        <div>
          <table class='scrolltbody'>
            <tbody>
            <tr>
              <th>보관소</th>
              <th>보관함</th>
              <th>상태</th>
            </tr>
            <tr v-for="(useBox,index) in useBoxes" :key="index" @click="detailBox(useBox)">
              <td>{{ useBox.storageName }}</td>
              <td>{{ useBox.boxName }}</td>
              <td v-if="useBox.useState == 0">결제완료</td>
              <td v-if="useBox.useState == 2">사용중</td>
              <td v-if="useBox.useState == 3">보관함이동</td>
              <td v-if="useBox.useState == 4">보관함이동</td>
              <td v-if="useBox.useState == 5">배송</td>
              <td v-if="useBox.useState == 6">수리신청</td>
              <td v-if="useBox.useState == 7">수리 중</td>
              <td v-if="useBox.useState == 8">수리완료</td>
              <td v-if="useBox.useState == 9">배송</td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <div v-if="modal">
      <div>
        <button @click="close()">X</button>
        <div>
          <div>
            <button class="mystoragebox-re" v-if="detailUseState==2" @click="moveBox(pickUseBox)">장비 이동</button>
            <button class="mystoragebox-re" v-if="detailUseState==2" @click="repairBox(pickUseBox)">장비 수리</button>
            <button class="mystoragebox-re" @click="renewalPay(pickUseBox)">연장</button>
            <button class="mystoragebox-re" v-if="detailUseState==2" @click="closeBox(pickUseBox)">해지</button>
          </div>
          <div>
            <table>
              <thead>
              <tr>
                <th>시작일</th>
                <th>종료예정일</th>
              </tr>
              </thead>
              <tbody>
              <tr>
                <td>{{useTime[0]}}</td>
                <td>{{useTime[1]}}</td>
              </tr>
              </tbody>
            </table>
          </div>
          <h5>보관 캠핑장비</h5>
          보관된 장비를 추가하기
          <button @click="addShow()">
            <h9 v-if="!addItemCheck">보기</h9>
            <h9 v-else>숨기기</h9>
          </button>
          <div v-if="addItemCheck">
            <div>
              <table>
                <thead>
                <tr>
                  <th colspan="2">장비</th>
                  <th>수량</th>
                  <th>선택</th>
                </tr>
                </thead>
                <tbody v-for="(item,index) in notInItem" :key="index">
                <tr>
                  <td>{{ index + 1 }}</td>
                  <td>{{ item.memEquipmentName }}</td>
                  <td>{{ item.memEquipmentCount }}</td>
                  <td><input type="checkbox" :value="item.memEquipmentCode" v-model="addBoxInItem"></td>
                </tr>
                </tbody>
              </table>
              <button @click="addItem()">추가하기</button>
            </div>
          </div>
          <div v-if="myItem.length > 0">
            <h5>보관장비</h5>
            <hr>
            <table>
              <thead>
              <tr>
                <th colspan="2">장비</th>
                <th>수량</th>
                <th>선택
                  <button @click="outItem()">빼내기</button>
                </th>
              </tr>
              </thead>
              <tbody v-for="(item,index) in myItem" :key="index">
              <tr>
                <td>{{ index + 1 }}</td>
                <td>{{ item.memEquipmentName }}</td>
                <td>{{ item.memEquipmentCount }}</td>
                <td><input type="checkbox" :value="item.memEquipmentCode" v-model="outBoxItem"></td>
              </tr>
              </tbody>
            </table>
          </div>
          <div v-else>
            <h5>보관장비</h5>
            <hr>
            <h5>보관중인 장비가 없습니다.</h5>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import store from "@/store";

export default {
  name: "MyStorageBox",
  components: {},
  data() {
    return {
      modal: false,
      memberId: '',
      useBoxes: [],
      myItem: {},
      notInItem: [],
      addBoxInItem: [],
      addItemCheck: false,
      pickUseBox: '',
      outBoxItem: [],
      detailUseState:'',
      useTimeList:[],
      useTime:[],
    }
  },
  mounted() {
    this.getBakData()
    this.getMyItem()
  },
  methods: {
    getBakData() {
      this.memberId = store.getters.getLoginState.loginState
      this.useBoxes = []
      axios.get('/api/checkMember/' + this.memberId)
          .then(res => {
            if (res.data.length < 1) {
              alert('사용중인 보관함이 없습니다')
            } else {
              const boxes = res.data
              this.useBoxes = []
              for (let i = 0; i < boxes.length; i++) {
                const box = {}
                box.storageCode = boxes[i][0]
                box.storageName = boxes[i][1]
                box.boxCode = boxes[i][2]
                box.boxName = boxes[i][3]
                box.boxState = boxes[i][4]
                box.useCode = boxes[i][5]
                box.useState = boxes[i][6].charAt(0)
                this.useBoxes.push(box)
              }
              console.log('this.useBoxes')
              console.log(this.useBoxes)
            }
          })
          .catch(err => {
            console.log(err)
          })
    },
    close() {
      this.modal = false
    },
    detailBox(us) {
      if (!this.modal) {
        this.modal = !this.modal
      }
      this.pickUseBox = us.useCode
      this.detailUseState = us.useState
      this.boxinItem(us.useCode)
      this.getBoxTimes(us)
    },
    getBoxTimes(us){
      axios.get('/api/findUseBoxTimes/'+ us.storageCode+'/'+us.boxCode +'/'+ this.memberId)
          .then(res => {
            this.useTimeList = res.data
            console.log(this.useTimeList)
            this.useTime = []

            let A = [];
            let B = [];
            for (let i = 0; i < this.useTimeList.length; i++) {
              for (let j = 0; j < this.useTimeList[i].length; j++) {
                if (i == 0 && j ==0){
                  this.useTime.push(
                      this.useTimeList[i][j][0] +"년" +
                      this.useTimeList[i][j][1] +"월" +
                      this.useTimeList[i][j][2] +"일"
                  )
                }
                if (i == this.useTimeList.length-1 && j ==this.useTimeList[i].length-1){
                  this.useTime.push(
                      this.useTimeList[i][j][0] +"년" +
                      this.useTimeList[i][j][1] +"월" +
                      this.useTimeList[i][j][2] +"일"
                  )
                }

                let date = new Date(
                     this.useTimeList[i][j][0],
                     this.useTimeList[i][j][1],
                     this.useTimeList[i][j][2]
                 )
                  A.push(date)
              }
              B.push(A)
              A =[]
            }
          })
          .catch(err => {
            console.log(err)
          })

      console.log('this.useTime')
      console.log(this.useTime)
    },
    boxinItem(index) {
      axios.get('/api/getBoxInItem/' + index)
          .then(res => {
            this.myItem = res.data
          })
          .catch(err => {
            console.log(err)
          })
    },
    moveBox(useBox) {
      this.$store.commit('moveBoxInfo', useBox)
      this.$router.push({
        name: 'moveBox',
        params: {
          userId: this.memberId,  // 사용자 아이디
          storageName: useBox.storageName,    // 보관소 이름
          boxName: useBox.boxName,       // 보관함 이름
          boxCode: useBox.boxCode,        // 보관함 코드
          useBoxCode: useBox.useBoxCode // 사용 보관함 코드
        }
      })
    },
    repairBox(useBox) {
      this.$router.push({
        name: 'repairBox',
        params: {
          storageName: useBox.storageName,    // 보관소 이름
          boxName: useBox.boxName,       // 보관함 이름
          useBoxCode: useBox.useBoxCode  // 사용 보관함 코드
        }
      })
    },
    renewalPay(useBox) {
      this.$router.push({
        name: 'renewalBox',
        params: {
          userId: this.memberId,  // 사용자 아이디
          storageName: useBox.storageName,    // 보관소 이름
          boxName: useBox.boxName,       // 보관함 이름
          startTime: useBox.startTime,  // 시작년월인
          endTime: useBox.endTime,      // 종료년월인
          start: useBox.start,            // 시작date
          end: useBox.end,                 // 종료date
          none: false,
          useBoxCode: useBox.useBoxCode  // 사용 보관함 코드
        }
      })
    },
    closeBox(useBox) {
      this.$router.push({name: 'closeBox', params: {useBoxCode: useBox.useBoxCode}})
    },
    getMyItem() {
      axios.get('/api/myItem/' + this.memberId)
          .then(res => {
            this.notInItem = res.data
          })
          .catch(err => {
            console.log(err)
          })
    },
    addShow() {
      if (this.addItemCheck) {
        this.addItemCheck = false
      } else {
        this.addItemCheck = true
      }
    },
    addItem() {
      if (this.addBoxInItem.length < 1) {
        alert('추가하실 장비를 선택하세요')
        return
      }
      let data = {
        useBoxCode: this.pickUseBox,
        itemList: this.addBoxInItem
      }
      axios.post('/api/addBoxInItem', data)
          .then(res => {
            if (res.data.result == 'ok') {
              alert('추가되었습니다')
              this.addBoxInItem = []
              this.getMyItem()
              this.detailBox(this.pickUseBox)
            } else {
              alert('에러, 다시 시도해 주세요')
            }
          })
          .catch(err => {
            console.log(err)
          })
    },
    outItem() {
      if (this.outBoxItem.length < 1) {
        alert('제거하실 장비를 선택하세요')
        return
      }
      let data = {
        useBoxCode: this.pickUseBox,
        itemList: this.outBoxItem
      }
      axios.post('/api/outBoxInItem', data)
          .then(res => {
            if (res.data.result == 'ok') {
              alert('제거 되었습니다')
              this.outBoxItem = []
              this.getMyItem()
              this.detailBox(this.pickUseBox)
            } else {
              alert('에러, 다시 시도해 주세요')
            }
          })
          .catch(err => {
            console.log(err)
          })
    }
  }
}
</script>

<style scoped>
.scrolltbody {
  display: block;
  border-collapse: collapse;
}

.scrolltbody tbody tr td {
  border-top: 0;
}

.scrolltbody tbody {
  display: block;
  height: 100%;
  overflow: auto;
}

.renewal-box h3 {
  margin-top: 3%;
  margin-left: 7%;
  margin-bottom: 3%;
}

.mystoragebox-re {
  margin-left: 4%;
  margin-bottom: 1%;
  text-align: center;
  width: 90%;
  padding: 0.5%;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
}

.mystoragebox-re:hover {
  color: white;
  background-color: #b2e2fd;
}

.scrolltbody td {
  padding: 1%;
  width: 3%;
}

</style>
