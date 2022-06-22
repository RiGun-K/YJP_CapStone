<template>
  <div class="container">
    <div class="btn">
      <button class="close-box" @click="close()">X</button>
    </div>
    <div class="container-box">
      <div class="service-btn">
        <button class="mystoragebox-re" v-if="(detailUseState==2 && myItem.length > 0) || detailUseState==6" @click="moveBox(pickUseBox)">장비 이동</button>
        <button class="mystoragebox-re" v-if="(detailUseState==2 && myItem.length > 0) || detailUseState==6" @click="repairBox(pickUseBox)">장비 수리</button>
        <button class="mystoragebox-re" v-if="moveBoxInfo.moveState != 3" @click="renewalPay(pickUseBox)">연장</button>
        <button class="mystoragebox-re" v-if="detailUseState==2 || detailUseState==6" @click="closeBox(pickUseBox)">해지</button>
      </div>
      <br>
      <br>
      <div class="range-info-box">
        <h4>사용기간</h4>
        <div class="box-info-a">
          <table>
            <thead>
            <tr>
              <th>시작일</th>
              <th>종료예정일</th>
            </tr>
            </thead>
            <tbody>
            <tr>
              <td>{{ useTime[0] }}</td>
              <td>{{ useTime[1] }}</td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
      <br>
      <div v-if="moveInfo">
        <div class="move-info-box">
          <h6>이동정보</h6>
          <hr>
          <div class="box-info-b">
            <table v-if="!kkk">
              <thead>
              <tr>
                <th>이동 보관함</th>
                <th>상태</th>
              </tr>
              </thead>
              <tbody>
              <tr >
                <td>{{ moveBoxInfo.storageName }}보관소{{ moveBoxInfo.boxName }}보관함으로 이동</td>
                <td>접수</td>
              </tr>
              <tr v-if="moveBoxInfo.moveState == 4">
                <td>{{ moveBoxInfo.storageName }}보관소{{ moveBoxInfo.boxName }}보관함에서 이 곳으로 이동</td>
                <td>이동중</td>
              </tr>
              <tr v-if="moveBoxInfo.moveState == 5">
                <td>{{ moveBoxInfo.storageName }}보관소{{ moveBoxInfo.boxName }}보관함에서 이 곳으로 이동</td>
                <td>도착</td>
              </tr>
              </tbody>
            </table>
            <table v-else>
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
      </div>
      <br>
      <div>
        <h2>보관 중 캠핑장비</h2>
        <div class="item-info-box">
          <div @click="addShow()">
            <h9>보관 장비 추가</h9>
            <h9 v-if="!addItemCheck">▼</h9>
            <h9 v-else>▲<button class="item-btn" @click="addItem()">추가하기</button></h9>
          </div>
          <div v-if="addItemCheck" class="box-info-c">
            <div>
              <table>
                <thead>
                <tr>
                  <th colspan="2">장비</th>
                  <th>수량</th>
                  <th>선택</th>
                </tr>
                </thead>
                <tbody class="body-sc">
                <tr v-for="(item,index) in notInItem" :key="index">
                  <td>{{ index + 1 }}</td>
                  <td>{{ item.memEquipmentName }}</td>
                  <td>{{ item.memEquipmentCount }}개</td>
                  <td><input type="checkbox" :value="item.memEquipmentCode" v-model="addBoxInItem"></td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>
          <div v-if="myItem.length > 0">
              <h3>보관장비<button class="out-btn" @click="outItem()">빼내기</button></h3>
            <div class="box-info">
              <table>
                <thead>
                  <tr>
                    <th colspan="2">장비</th>
                    <th>수량</th>
                    <th></th>
                    <th>선택</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(item,index) in myItem" :key="index">
                    <td>{{ index + 1 }}</td>
                    <td>{{ item.memEquipmentName }}</td>
                    <td>{{ item.memEquipmentCount }}개</td>
                    <td>{{ stateCheck(item.memEquipmentState)}}</td>
                    <td><input type="checkbox" :value="item" v-model="outBoxItem"></td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
          <div v-else>
            <div>
              <h5>보관장비</h5>
              <hr>
              <div>
                <h5>보관중인 장비가 없습니다.</h5>
              </div>
            </div>
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
  name: "MyUseBoxDetail",
  props:{
    modal:'',
    useData:{},
  },
  data(){
    return{
      memberId:'',
      myItem: {},
      notInItem: [],
      addBoxInItem: [],
      addItemCheck: false,
      pickUseBox: '',
      outBoxItem: [],
      detailUseState: '',
      useTimeList: [],
      useTime: [],
      useStorageTime: [],
      moveBoxInfo: {},
      moveInfo: false,
      order:{},
      kkk:false,
    }
  },
  mounted() {
    this.memberId = store.getters.getLoginState.loginState
    this.detailBox(this.useData)

    },
  methods:{
    stateCheck(sCode) {
      switch (sCode) {
        case "1":
          return "보관중"
        case "2":
          return "수리중"
        default:
          return "비정상"
      }
    },
    close() {
      this.$emit('close')
    },
    delInfo(code){
      axios.get("/api/delInfo/"+code)
          .then(res=>{
            this.order = res.data
            this.moveInfo = true

            this.kkk = true
          })
          .catch(err => {
            console.log(err)
          })
    },
    detailBox(us) {
      this.moveInfo = false
      this.pickUseBox = us.useCode
      this.detailUseState = us.useState
      this.boxinItem(this.pickUseBox)
      this.delInfo(us.del)
      this.getBoxTimes(us)
      if (us.moveUseCode != undefined) {
        this.moveInfo = true
        if (us.useState == 3 || us.useState == 4 || us.useState == 5) {
          this.boxDetailMoveInfo(us.moveUseCode, us.useState)
        }
      }
    },
    boxDetailMoveInfo(useCode, useState) {
      console.log(useState)
      console.log(useState)
      console.log(useState)
      console.log(useState)

      this.moveBoxInfo.moveState = useState
      axios.get('/api/moveBoxInfo/' + useCode)
          .then(res => {
            this.moveBoxInfo.storageCode = res.data[0][0]
            this.moveBoxInfo.storageName = res.data[0][1]
            this.moveBoxInfo.boxCode = res.data[0][2]
            this.moveBoxInfo.boxName = res.data[0][3]
          })
          .catch(err => {
            console.log(err)
          })
    },
    getBoxTimes(us) {
      axios.get('/api/findUseBoxTimes/' + us.storageCode + '/' + us.boxCode + '/' + this.memberId)
          .then(res => {
            this.useTimeList = res.data
            this.useTime = []

            this.useStorageTime = []
            for (let i = 0; i < this.useTimeList.length; i++) {
              for (let j = 0; j < this.useTimeList[i].length; j++) {
                if (i == 0 && j == 0) {
                  this.useTime.push(
                      this.useTimeList[i][j][0] + "년" +
                      this.useTimeList[i][j][1] + "월" +
                      this.useTimeList[i][j][2] + "일"
                  )
                }
                if (i == this.useTimeList.length - 1 && j == this.useTimeList[i].length - 1) {
                  this.useTime.push(
                      this.useTimeList[i][j][0] + "년" +
                      this.useTimeList[i][j][1] + "월" +
                      this.useTimeList[i][j][2] + "일"
                  )
                }
                if (i == this.useTimeList.length - 1) {
                  let date = new Date(
                      this.useTimeList[i][j][0],
                      this.useTimeList[i][j][1],
                      this.useTimeList[i][j][2]
                  )
                  this.useStorageTime.push(date)
                }
              }
            }
            this.getMyItem()
          })
          .catch(err => {
            console.log(err)
          })

    },
    boxinItem(index) {
      axios.get('/api/getBoxInItem/' + index)
          .then(res => {
            this.myItem = res.data
            this.getMyItem()
          })
          .catch(err => {
            console.log(err)
          })
    },
    moveBox(useBox) {
      this.$router.push({
        name: 'moveBox',
        params: {
          useBoxCode: useBox // 사용 보관함 코드
        }
      })
    },
    repairBox(useBox) {
      this.$router.push({
        name: 'repairBox',
        params: {
          useBoxCode: useBox  // 사용 보관함 코드
        }
      })
    },
    renewalPay(useBox) {
      this.$router.push({
        name: 'renewalBox',
        params: {
          useBoxCode: useBox  // 사용 보관함 코드
        }
      })
    },
    closeBox(useBox) {
      this.$router.push({name: 'closeBox', params: {useBoxCode: this.pickUseBox}})
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
              this.boxinItem(this.pickUseBox)
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
      let list = []
      for (let i = 0; i < this.outBoxItem.length; i++) {
        console.log(this.outBoxItem[i])
        list.push(this.outBoxItem[i].memEquipmentCode)
        if(this.outBoxItem[i].memEquipmentState == "2"){
          alert('수리중에는 빼낼 수 없습니다.')
          return
        }
      }

      let data = {
        useBoxCode: this.pickUseBox,
        itemList: list
      }
      axios.post('/api/outBoxInItem', data)
          .then(res => {
            if (res.data.result == 'ok') {
              alert('제거 되었습니다')
              this.outBoxItem = []
              this.boxinItem(this.pickUseBox)
            } else {
              alert('에러, 다시 시도해 주세요')
            }
          })
          .catch(err => {
            console.log(err)
          })
    },
  }
}
</script>

<style scoped>
.container{
  position: center;
  height: 100%;
  width: 95%;
  margin: 5%;
}

h5{
  text-effect: emboss;
}

.container-box{
  padding-right: 5%;
}
.body-sc{
  overflow: scroll;
  height: 60px;
}

.item-info-box{
  border: 1px solid black;
}

th{
  text-align: center;
}
thead{
  background-color: #6e6e6e;
  color: white;
}

tr{
  border-bottom: 1px solid black;
}

td{
  text-align: center;
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
  width: 40%;
  padding: 0.5%;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
}
.item-btn{
  margin-left: 4%;
  margin-bottom: 1%;
  text-align: center;
  padding: 0.5%;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
  float: right;
  margin-right: 15%;
}

.out-btn{
  margin-left: 4%;
  margin-bottom: 1%;
  text-align: center;
  padding: 0.5%;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
  float: right;
  margin-right: 15%;
  font-size: 50%;
}

.close-box{
  margin-left: 4%;
  margin-bottom: 1%;
  text-align: center;
  width: 30px;
  padding: 0.5%;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;

}

.btn{
  float: right;
  width: 10%;
}

.mystoragebox-re:hover {
  color: white;
  background-color: #b2e2fd;
}

.scrolltbody td {
  padding: 1%;
  width: 3%;
}

.box-info-b th {
  border: 1px solid black;
  padding: 5px;
  padding-left: 5%;
  width: 50%;
}

.box-info-b td {
  border: 1px solid black;
  padding: 5px;
  padding-left: 5%;
  width: 50%;
}

.box-info-b td:first-child {
  width: 20%;
  background-color: #d8d8d8;
}
</style>