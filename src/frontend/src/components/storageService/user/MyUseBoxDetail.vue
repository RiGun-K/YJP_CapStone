<template>
  <div class="container">
    <div class="modal modal-signin d-block py-5" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document" >
        <div class="modal-content rounded-4 shadow" style="margin-left: 300px; margin-top: 130px">
          <div class="modal-header p-5 pb-4 border-bottom-0">
            <h2 class="fw-bold mb-0">{{useData.storageName}}, {{useData.boxName}}</h2>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" @click="close"></button>
          </div>

          <div class="modal-body p-5 pt-0">
            <form class="">
              <div class="form-floating mb-3">
                <div style="display: flex">
                  <strong class="d-inline-block mb-0 text-success">사용 시작일</strong>
                  <div style="margin-left: 5px">
                     - {{ useTime[0] }}
                  </div>
                </div>
              </div>
              <div class="form-floating mb-3">
                <div style="display: flex">
                  <strong class="d-inline-block mb-0 text-success">사용 종료일</strong>
                  <div style="margin-left: 5px">
                    - {{ useTime[1] }}
                  </div>
                </div>
              </div>
              <div v-if="moveInfo">
                <div class="form-floating mb-3">
                  <strong class="d-inline-block text-success" style="margin-bottom: 3px">이동정보</strong>
                  <div  v-if="!kkk">
                    <div style="margin-left: 21px; display: flex">
                      <img :src="require('@/assets/free-icon-location-pin-1201684.png')" alt="..." style="width: 4.6%; height: 5%; margin-top: 2px;margin-right: 1px">
                      {{useData.storageName}} 보관소, {{useData.boxName}} 보관함에서
                    </div>
                    <div style="margin-left: 20px; display: flex" v-if="moveBoxInfo.moveState == 3">
                      <img :src="require('@/assets/premium-icon-location-pin-2776067.png')" alt="..." style="width: 5%; height: 5%; margin-top: 2px">
                      {{ moveBoxInfo.storageName }} 보관소, {{ moveBoxInfo.boxName }}보관함으로 이동
                    </div>
                    <div style="margin-left: 20px; display: flex" v-if="moveBoxInfo.moveState == 4">
                      <img :src="require('@/assets/premium-icon-location-pin-2776067.png')" alt="..." style="width: 5.2%; height: 5%; margin-top: 2px">
                      {{ moveBoxInfo.storageName }} 보관소, {{ moveBoxInfo.boxName }} 보관함으로 이동
                    </div>
                    <div style="margin-left: 20px; display: flex" v-if="moveBoxInfo.moveState == 5">
                      <img :src="require('@/assets/premium-icon-location-pin-2776067.png')" alt="..." style="width: 5%; height: 5%; margin-top: 2px">
                      {{ moveBoxInfo.storageName }} 보관소, {{ moveBoxInfo.boxName }} 보관함으로 배송 중
                    </div>
                  </div>
                  <div v-else>
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
              </div>
              <div class="form-floating mb-3" >
                <div style="display: flex; margin-bottom: 3px">
                  <strong class="d-inline-block mb-0 text-success">보관 중인 장비</strong>
                  <button class="btn btn-outline-secondary" v-if="myItem.length > 0" style="width: 60px; padding: 2px; margin-left: 242px" @click="outItem()">빼내기</button>
                </div>
                <div @click="addShow()" style="margin-bottom: 3px">
                  <h9>보관 장비 추가</h9>
                  <h9 v-if="!addItemCheck">▼</h9>
                  <h9 v-else>▲
                    <button class="item-btn" @click="addItem()">추가하기</button>
                  </h9>
                </div>
                <div v-if="addItemCheck">
                  <div>
                    <table class="table">
                      <thead>
                      <tr>
                        <th scope="col">No</th>
                        <th scope="col">장비</th>
                        <th scope="col">수량</th>
                        <th scope="col">선택</th>
                      </tr>
                      </thead>
                      <tbody class="table-group-divider">
                      <tr v-for="(item,index) in notInItem" :key="index" class="item">
                        <th scope="row">{{index+1}}</th>
                        <td>{{ item.memEquipmentName }}</td>
                        <td>
                          <div style="display: flex">
                            <select class="form-select" aria-label="Default select example" v-model="item.count">
                              <option v-for="inx in item.memEquipmentCount" :value="inx">{{ inx }}</option>
                            </select>
                            <div style="margin-left: 5px; margin-top: 2px"> 개</div>
                          </div>
                        </td>
                        <td>{{ stateCheck(item.memEquipmentCode.memEquipmentState) }}</td>
                        <td><input type="checkbox" :value="item" v-model="addBoxInItem"></td>
                      </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
                <div v-if="myItem.length > 0">
                  <table class="table">
                    <thead>
                    <tr>
                      <th scope="col">No</th>
                      <th scope="col">장비</th>
                      <th scope="col">수량</th>
                      <th scope="col">상태</th>
                      <th scope="col">선택</th>
                    </tr>
                    </thead>
                    <tbody class="table-group-divider">
                    <tr v-for="(item,index) in myItem" :key="index">
                      <th scope="row">{{index+1}}</th>
                      <td>{{ item.memEquipmentCode.memEquipmentName }}</td>
                      <td>
                        <div style="display: flex">
                          <select class="form-select" aria-label="Default select example" v-model="item.count">
                            <option v-for="inx in item.boxItemCount" :value="inx">{{ inx }}</option>
                          </select>
                          <div style="margin-left: 5px; margin-top: 2px"> 개</div>
                        </div>
                      </td>
                      <td>{{ stateCheck(item.memEquipmentCode.memEquipmentState) }}</td>
                      <td><input type="checkbox" :value="item" v-model="outBoxItem"></td>
                    </tr>
                    </tbody>
                  </table>
                </div>
                <div v-else>
                  <div style="margin-left: 20px;">보관중인 장비가 없습니다.</div>
                </div>
              </div>

              <button class="w-100 mb-2 btn btn-lg rounded-3 btn-primary" id="button-addon2" type="button"  v-if="(detailUseState==2 && myItem.length > 0) || detailUseState==6" @click="moveBox(pickUseBox)" style="margin-top: 10px">Move Item</button>
              <button class="w-100 mb-2 btn btn-lg rounded-3 btn-primary" id="button-addon2" type="button"  v-if="(detailUseState==2 && myItem.length > 0) || detailUseState==6" @click="repairBox(pickUseBox)" style="margin-top: 10px">Repair Item</button>
              <button class="w-100 mb-2 btn btn-lg rounded-3 btn-primary" id="button-addon2" type="button" v-if="moveBoxInfo.moveState != 3 && detailUseState != 1" @click="renewalPay(pickUseBox)" style="margin-top: 10px">Renewal Box</button>
              <button class="w-100 mb-2 btn btn-lg rounded-3 btn-primary" id="button-addon2" type="button" v-if="detailUseState==2 || detailUseState==6" @click="closeBox(pickUseBox)" style="margin-top: 10px">Clear Box</button>
              <button class="w-100 mb-2 btn btn-lg rounded-3 btn-primary" id="button-addon2" type="button" v-if="detailUseState == 1 && backChk" @click="backItem()">Back Item</button>
              <small class="text-muted">By clicking Button, you agree to the terms of use.</small>
            </form>
          </div>
        </div>
      </div>
    </div>
<!--    <div class="btn">-->
<!--      <button class="close-box" @click="close()">X</button>-->
<!--    </div>-->
<!--    <div class="container-box">-->
<!--      <div class="service-btn">-->
<!--        <button class="mystoragebox-re" v-if="(detailUseState==2 && myItem.length > 0) || detailUseState==6"-->
<!--                @click="moveBox(pickUseBox)">장비 이동-->
<!--        </button>-->
<!--        <button class="mystoragebox-re" v-if="(detailUseState==2 && myItem.length > 0) || detailUseState==6"-->
<!--                @click="repairBox(pickUseBox)">장비 수리-->
<!--        </button>-->
<!--        <button class="mystoragebox-re" v-if="moveBoxInfo.moveState != 3 && detailUseState != 1" @click="renewalPay(pickUseBox)">연장</button>-->
<!--        <button class="mystoragebox-re" v-if="detailUseState==2 || detailUseState==6" @click="closeBox(pickUseBox)">해지</button>-->
<!--        <button class="mystoragebox-re" v-if="detailUseState == 1 && backChk" @click="backItem()">장비회수</button>-->
<!--      </div>-->
<!--      <br>-->
<!--      <br>-->
<!--      <div class="range-info-box">-->
<!--        <h4>사용기간</h4>-->
<!--        <div class="box-info-a">-->
<!--          <table>-->
<!--            <thead>-->
<!--            <tr>-->
<!--              <th>시작일</th>-->
<!--              <th>종료예정일</th>-->
<!--            </tr>-->
<!--            </thead>-->
<!--            <tbody>-->
<!--            <tr>-->
<!--              <td>{{ useTime[0] }}</td>-->
<!--              <td>{{ useTime[1] }}</td>-->
<!--            </tr>-->
<!--            </tbody>-->
<!--          </table>-->
<!--        </div>-->
<!--      </div>-->
<!--      <br>-->
<!--      <div v-if="moveInfo">-->
<!--        <div class="move-info-box">-->
<!--          <h6>이동정보</h6>-->
<!--          <hr>-->
<!--          <div class="box-info-b">-->
<!--            <table v-if="!kkk">-->
<!--              <thead>-->
<!--              <tr>-->
<!--                <th>이동 보관함</th>-->
<!--                <th>상태</th>-->
<!--              </tr>-->
<!--              </thead>-->
<!--              <tbody>-->
<!--              <tr v-if="moveBoxInfo.moveState == 3">-->
<!--                <td>{{ moveBoxInfo.storageName }}보관소{{ moveBoxInfo.boxName }}보관함으로 이동</td>-->
<!--                <td>접수</td>-->
<!--              </tr>-->
<!--              <tr v-if="moveBoxInfo.moveState == 4">-->
<!--                <td>{{ moveBoxInfo.storageName }}보관소{{ moveBoxInfo.boxName }}보관함으로 이동</td>-->
<!--                <td>접수</td>-->
<!--              </tr>-->
<!--              <tr v-if="moveBoxInfo.moveState == 5">-->
<!--                <td>{{ moveBoxInfo.storageName }}보관소{{ moveBoxInfo.boxName }}보관함에서 이 곳으로 이동</td>-->
<!--                <td>이동중</td>-->
<!--              </tr>-->
<!--              </tbody>-->
<!--            </table>-->
<!--            <table v-else>-->
<!--              <tbody>-->
<!--              <tr>-->
<!--                <td>받는 사람</td>-->
<!--                <td>{{ order.deliveryGetter }}</td>-->
<!--              </tr>-->
<!--              <tr>-->
<!--                <td>우편번호</td>-->
<!--                <td>{{ order.deliveryZipcode }}</td>-->
<!--              </tr>-->
<!--              <tr>-->
<!--                <td>주소</td>-->
<!--                <td>{{ order.deliveryAddress }}</td>-->
<!--              </tr>-->
<!--              <tr>-->
<!--                <td>연락처</td>-->
<!--                <td>{{ order.deliveryGetterTel }}</td>-->
<!--              </tr>-->
<!--              </tbody>-->
<!--            </table>-->
<!--          </div>-->
<!--        </div>-->
<!--      </div>-->
<!--      <br>-->
<!--      <div>-->
<!--        <h2>보관 중 캠핑장비</h2>-->
<!--        <div class="item-info-box">-->
<!--          <div @click="addShow()">-->
<!--            <h9>보관 장비 추가</h9>-->
<!--            <h9 v-if="!addItemCheck">▼</h9>-->
<!--            <h9 v-else>▲-->
<!--              <button class="item-btn" @click="addItem()">추가하기</button>-->
<!--            </h9>-->
<!--          </div>-->
<!--          <div v-if="addItemCheck" class="box-info-c">-->
<!--            <div>-->
<!--              <table>-->
<!--                <thead>-->
<!--                <tr>-->
<!--                  <th colspan="2">장비</th>-->
<!--                  <th>수량</th>-->
<!--                  <th>선택</th>-->
<!--                </tr>-->
<!--                </thead>-->
<!--                <tbody class="body-sc">-->
<!--                <tr v-for="(item,index) in notInItem" :key="index" class="item">-->
<!--                  <td>{{ index + 1 }}</td>-->
<!--                  <td>{{ item.memEquipmentName }}</td>-->
<!--                  <td>-->
<!--                    <select v-model="item.count">-->
<!--                      <option v-for="inx in item.memEquipmentCount" :value="inx">{{ inx }}</option>-->
<!--                    </select>-->
<!--                    개-->
<!--                  </td>-->
<!--                  <td><input type="checkbox" :value="item" v-model="addBoxInItem"></td>-->
<!--                </tr>-->
<!--                </tbody>-->
<!--              </table>-->
<!--            </div>-->
<!--          </div>-->
<!--          <div v-if="myItem.length > 0">-->
<!--            <h3>보관장비-->
<!--              <button class="out-btn" @click="outItem()">빼내기</button>-->
<!--            </h3>-->
<!--            <div class="box-info">-->
<!--              <table>-->
<!--                <thead>-->
<!--                  <tr>-->
<!--                    <th colspan="2">장비</th>-->
<!--                    <th>수량</th>-->
<!--                    <th></th>-->
<!--                    <th>선택</th>-->
<!--                  </tr>-->
<!--                </thead>-->
<!--                <tbody>-->
<!--                  <tr v-for="(item,index) in myItem" :key="index">-->
<!--                    <td>{{ index + 1 }}</td>-->
<!--                    <td>{{ item.memEquipmentCode.memEquipmentName }}</td>-->
<!--                    <td>-->
<!--                      <select v-model="item.count">-->
<!--                        <option v-for="inx in item.boxItemCount" :value="inx">{{ inx }}</option>-->
<!--                      </select>-->
<!--                      개-->
<!--                    </td>-->
<!--                    <td>{{ stateCheck(item.memEquipmentCode.memEquipmentState) }}</td>-->
<!--                    <td><input type="checkbox" :value="item" v-model="outBoxItem"></td>-->
<!--                  </tr>-->
<!--                </tbody>-->
<!--              </table>-->
<!--            </div>-->
<!--          </div>-->
<!--          <div v-else>-->
<!--            <div>-->
<!--              <h5>보관장비</h5>-->
<!--              <hr>-->
<!--              <div>-->
<!--                <h5>보관중인 장비가 없습니다.</h5>-->
<!--              </div>-->
<!--            </div>-->
<!--          </div>-->
<!--          <div v-if="repairList.length>0">-->
<!--            <h3>수리장비</h3>-->
<!--            <div class="box-info">-->
<!--              <table>-->
<!--                <thead>-->
<!--                <tr>-->
<!--                  <th colspan="2">장비</th>-->
<!--                  <th>수리 수량</th>-->
<!--                  <th>수리 항목</th>-->
<!--                </tr>-->
<!--                </thead>-->
<!--                <tbody>-->
<!--                <tr v-for="(item,index) in repairList" :key="index">-->
<!--                  <td>{{ index + 1 }}</td>-->
<!--                  <td>{{ item.boxItemCode.memEquipmentCode.memEquipmentName }}</td>-->
<!--                  <td>{{item.repairItemCount}} 개</td>-->
<!--                  <td>{{ item.buyId.buyName }}</td>-->
<!--                </tr>-->
<!--                </tbody>-->
<!--              </table>-->
<!--            </div>-->
<!--          </div>-->
<!--        </div>-->
<!--      </div>-->
<!--    </div>-->
  </div>
</template>

<script>
import axios from "axios";
import store from "@/store";

export default {
  name: "MyUseBoxDetail",
  props: {
    modal: '',
    useData: {},
  },
  data() {
    return {
      memberId: '',
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
      order: {},
      kkk: false,
      repairList:[],
      backChk:false,
    }
  },
  mounted() {
    this.memberId = store.getters.getLoginState.loginState
    this.detailBox(this.useData)

  },
  watch:{
    useData:function (){
      this.detailBox(this.useData)
    }
  },
  methods: {
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
    backItem(){
      var delConfirm = confirm('장비를 회수 하겠습니까?');
      if (delConfirm) {
        axios.get('/api/backToItemCall/'+this.pickUseBox)
            .then(res=>{
              console.log(res)
              alert('회수 신청이 되었습니다.');
              this.backChk = false
            })
            .catch(err=>{
              console.log(err)
            })

      }
    },
    close() {
      this.$emit('close')
    },
    delInfo(code) {
      axios.get("/api/delInfo/" + code)
          .then(res => {
            this.order = res.data
            this.moveInfo = true
            this.kkk = true
          })
          .catch(err => {
            console.log(err)
          })
    },
    detailBox(us) {
      console.log('us')
      console.log(us)
      if (us.useState == "1"){
        this.setTimeChk()
      }
      this.moveInfo = false
      this.pickUseBox = us.useCode
      this.detailUseState = us.useState
      this.boxinItem(this.pickUseBox)
      this.delInfo(us.del)
      this.getBoxTimes(us)
      this.getRepairList()
      if (us.moveUseCode != undefined) {
        this.moveInfo = true
        if (us.useState == 3 || us.useState == 4 || us.useState == 5 || us.useState == 'a') {
          this.boxDetailMoveInfo(us.moveUseCode, us.useState)
        }
      }
    },
    setTimeChk(){
      axios.get('/api/remainderTime/'+this.useData.useCode)
          .then(res=>{
            console.log(res.data)
            if(res.data < 5){
              this.backChk = true
            }
          })
          .catch(err=>{
            console.log(err)
          })
    },
    boxDetailMoveInfo(useCode, useState) {
      this.moveBoxInfo.moveState = useState
      axios.get('/api/moveBoxInfo/' + useCode)
          .then(res => {
            let arry = []
            arry = res.data

            this.inputInfo(arry)
          })
          .catch(err => {
            console.log(err)
          })
    },
    inputInfo(arry) {
      this.moveBoxInfo.storageCode = arry[0][0]
      this.moveBoxInfo.storageName = arry[0][1]
      this.moveBoxInfo.boxCode = arry[0][2]
      this.moveBoxInfo.boxName = arry[0][3]
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
            console.log(this.myItem)
            for (let i = 0; i < this.myItem.length; i++) {
              this.myItem[i].count = this.myItem[i].boxItemCount
            }
            this.getMyItem()
          })
          .catch(err => {
            console.log(err)
          })
    },
    getRepairList(){
      axios.get('/api/getCareList/'+this.pickUseBox)
          .then(res => {
            this.repairList = res.data
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
            for (let i = 0; i < this.notInItem.length; i++) {
              this.notInItem[i].count = this.notInItem[i].memEquipmentCount
            }
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
      let arry = []
      for (let i = 0; i < this.addBoxInItem.length; i++) {
        let form = {}
        form.itemCode = this.addBoxInItem[i].memEquipmentCode
        form.count = this.addBoxInItem[i].count
        arry.push(form)
      }
      let data = {
        useBoxCode: this.pickUseBox,
        itemList: arry
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
        let form = {}
        form.itemCode = this.outBoxItem[i].memEquipmentCode.memEquipmentCode
        form.count = this.outBoxItem[i].count
        list.push(form)
        if (this.outBoxItem[i].memEquipmentState == "2") {
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
.container {
  position: center;
  height: 100%;
  width: 95%;
  margin: 5%;
}

h5 {
  text-effect: emboss;
}

.container-box {
  padding-right: 5%;
}

.body-sc {
  overflow: scroll;
  height: 60px;
}

.item-info-box {
  border: 1px solid black;
}

th {
  text-align: center;
}

thead {
  background-color: #6e6e6e;
  color: white;
}

tr {
  border-bottom: 1px solid black;
}

td {
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

.item-btn {
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

.out-btn {
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

.close-box {
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

.btn {
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