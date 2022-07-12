<template>

  <main>
    <h1 class="visually-hidden">Features examples</h1>

    <div class="container px-4 py-5" id="featured-3">
      <h2 class="pb-2 border-bottom">{{ name }}, Choose a Storage-Box</h2>
      <div class="row g-4 py-5 row-cols-1 row-cols-lg-2"  style="width: 400px; margin-left: 30px">
        <div class="feature col" v-for="box in this.boxList.storageBoxes" @click="findTime(box)"  style="margin-bottom: 30px;">
          <div class="feature-icon d-inline-flex align-items-center justify-content-center text-white fs-2 mb-3">
            <svg class="bi" width="1em" height="1em"><use xlink:href="#box_seam"/></svg>
          </div>
          <div style="display: flex">
            <h2>{{ box.storageBoxName }}</h2>
            <div style="margin-left: 3px; margin-top: 12px">
              <p v-if="box.storageBoxState == '0'">사용가능</p>
              <p v-else-if="box.storageBoxState == '6'">사용가능 <br>(바로사용불가)</p>
            </div>
          </div>
          <div class="mb-1 text-muted" style="font-size: 0.9em; padding: 0.2em; margin-top: -10px">가격: {{ box.storageBoxPrice }} 원</div>
        </div>
      </div>
    </div>


    <div v-show="stateCheck" style="position: fixed; z-index: 4; ">
      <div class="modal modal-signin d-block py-5" tabindex="-1" role="dialog" id="modalSignin">
        <div class="modal-dialog" role="document">
          <div class="modal-content rounded-4 shadow" style="margin-left: 200px; margin-top: 220px">
            <div class="modal-header p-5 pb-4 border-bottom-0">
              <h2 class="fw-bold mb-0">{{ name }}, {{ boxName }}</h2>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" @click="checkStateBtn"></button>
            </div>

            <div class="modal-body p-5 pt-0">
              <form class="">
                <div class="form-floating mb-3">
                  <strong class="d-inline-block mb-0 text-success">대여기간 설정</strong>
                  <Datepicker
                      locale="ko-KR"
                      :min-date="today"
                      type="date"
                      format="yyyy/MM/dd"
                      value-format="yyyyMMdd"
                      :enableTimePicker="false"
                      autoApply
                      :closeOnAutoApply="false"
                      placeholder="Select Date"
                      v-model="date"
                      :disabledDates="disabledDates" style="margin-top: 5px; margin-left: 20px"/>
                </div>
                <div class="form-floating mb-3">
                  <strong class="d-inline-block mb-0 text-success">나의 장비 선택</strong>
                  <div>
                    <ul v-for="(item, index) in myItem" :key="index" class="item" style="list-style: none; margin-top: 25px; padding-left: 20px">
                      <li @click="checkItemList(item,index)" style="margin-top: -20px; padding: 0">{{ item.memEquipmentName }}</li>
                    </ul>
                  </div>
                </div>
                <div class="form-floating mb-3">
                  <strong class="d-inline-block mb-0 text-success">보관 장비</strong>
                  <div style="margin-left: 20px">
                    <div class="chkItemList">
                      <table class="table">
                        <thead>
                        <tr>
                          <th scope="col">No</th>
                          <th scope="col">장비</th>
                          <th scope="col">수량</th>
                        </tr>
                        </thead>
                        <tbody class="table-group-divider">
                        <tr v-for="(item,index) in checkItem">
                          <th scope="row">{{index+1}}</th>
                          <td>{{ item.itemName }}</td>
                          <td>
                            <div style="display: flex">
                              <select class="form-select" aria-label="Default select example" v-model="item.count">
                                <option v-for="inx in item.itemCount" :value="inx">{{ inx }}</option>
                              </select>
                              <div style="margin-left: 5px; margin-top: 2px"> 개</div>
                            </div>
                          </td>
                        </tr>
                        </tbody>
                      </table>
                    </div>
                  </div>
                </div>
                <div class="form-floating mb-3" style="margin-top: 30px">
                  <div style="display: flex;">
                    <strong class="d-inline-block mb-0 text-success">결제금액</strong>
                    <div style="margin-left: 8px;">
                      - {{ form.price }}원
                    </div>
                  </div>
                </div>

                <button class="w-100 mb-2 btn btn-lg rounded-3 btn-primary" id="button-addon2" type="button" @click="payCheckStateBtn" style="margin-top: 10px">Next</button>
                <small class="text-muted">By clicking Next, you agree to the terms of use.</small>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>


    <div v-show="payCheck" style="position: fixed; z-index: 4;">
      <div class="modal modal-signin d-block py-5" tabindex="-1" role="dialog" id="modalSignin" style="background-color: rgba(0,0,0,0.25)">
        <div class="modal-dialog" role="document" >
          <div class="modal-content rounded-4 shadow" >
            <div class="modal-header p-5 pb-4 border-bottom-0">
              <h2 class="fw-bold mb-0">{{ name }}, {{ boxName }} - 결제</h2>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" @click="payCheckStateBtn"></button>
            </div>

            <div class="modal-body p-5 pt-0">
              <form class="">
                <div class="form-floating mb-3">
                  <div style="display: flex;">
                    <strong class="d-inline-block mb-0 text-success">구매자 이름</strong>
                    <div style="margin-left: 8px;">
                      - {{ member.mname }}
                    </div>
                  </div>
                </div>
                <div class="form-floating mb-3">
                  <div style="display: flex;">
                    <strong class="d-inline-block mb-0 text-success">구매자 이메일</strong>
                    <div style="margin-left: 8px;">
                      - {{ member.mmail }}
                    </div>
                  </div>
                </div>
                <div class="form-floating mb-3">
                  <div style="display: flex;">
                    <strong class="d-inline-block mb-0 text-success">구매자 전화번호</strong>
                    <div style="margin-left: 8px;">
                      - {{ member.mph }}
                    </div>
                  </div>
                </div>
                <hr class="my-4">
                <div class="form-floating mb-3">
                  <div style="display: flex;">
                    <strong class="d-inline-block mb-0 text-success">보관소 이름</strong>
                    <div style="margin-left: 8px;">
                      - {{ name }}
                    </div>
                  </div>
                </div>
                <div class="form-floating mb-3">
                  <div style="display: flex;">
                    <strong class="d-inline-block mb-0 text-success">보관함 이름</strong>
                    <div style="margin-left: 8px;">
                      - {{ boxName }}
                    </div>
                  </div>
                </div>
                <hr class="my-4">
                <div class="form-floating mb-3">
                  <strong class="d-inline-block mb-0 text-success">보관 장비</strong>
                  <div style="margin-left: 20px">
                    <div class="chkItemList">
                      <table class="table">
                        <thead>
                        <tr>
                          <th scope="col">No</th>
                          <th scope="col">장비</th>
                          <th scope="col">수량</th>
                        </tr>
                        </thead>
                        <tbody class="table-group-divider">
                        <tr v-for="(item,index) in form.item" :key="index">
                          <th scope="row">{{index+1}}</th>
                          <td>{{ item.itemName }}</td>
                          <td>
                            <div style="display: flex">
                              <div style="margin-left: 5px; margin-top: 2px">{{item.count}} 개</div>
                            </div>
                          </td>
                        </tr>
                        </tbody>
                      </table>
                    </div>
                  </div>
                </div>
                <hr class="my-4">
                <div class="form-floating mb-3">
                  <div style="display: flex;">
                    <strong class="d-inline-block mb-0 text-success">보관 시작일</strong>
                    <div style="margin-left: 8px;">
                      - {{ date }}
                    </div>
                  </div>
                </div>
                <div class="form-floating mb-3">
                  <div style="display: flex;">
                    <strong class="d-inline-block mb-0 text-success">보관 종료일</strong>
                    <div style="margin-left: 8px;">
                      - {{  }}
                    </div>
                  </div>
                </div>

                <button class="w-100 mb-2 btn btn-lg rounded-3 btn-primary" id="button-addon2" type="button" @click="pay" style="margin-top: 10px">Pay</button>
                <small class="text-muted">By clicking Pay, you agree to the terms of use.</small>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </main>
<!--  <div class="storage">-->
<!--    <button @click="backPage" class="storage-back-btn">되돌아가기</button>-->
<!--    <h5 class="storage-name-h5">보관소 이름:{{ name }}</h5>-->

<!--    <div class="storage-box" v-for="box in this.boxList.storageBoxes" @click="findTime(box)">-->
<!--      <ul>-->
<!--        <li>{{ box.storageBoxName }}</li>-->
<!--        <li v-if="box.storageBoxState == '0'">사용가능</li>-->
<!--        <li v-else-if="box.storageBoxState == '6'">사용가능 <br>(바로사용불가)</li>-->
<!--        <li v-else>사용불가능</li>-->
<!--        <li>가격 : {{ box.storageBoxPrice }}원</li>-->
<!--      </ul>-->
<!--    </div>-->
<!--    <div v-if="stateCheck" class="detailDiv">-->
<!--      <div><h3>{{ boxName }}</h3></div>-->
<!--      <div class="storageBody">-->
<!--        <div class="storageUpDiv">-->
<!--          <div class="storageTime">-->
<!--            <h5>대여기간 설정</h5>-->
<!--            <hr>-->
<!--            <Datepicker-->
<!--                locale="ko-KR"-->
<!--                :min-date="today"-->
<!--                type="date"-->
<!--                format="yyyy/MM/dd"-->
<!--                value-format="yyyyMMdd"-->
<!--                :enableTimePicker="false"-->
<!--                autoApply-->
<!--                :closeOnAutoApply="false"-->
<!--                placeholder="Select Date"-->
<!--                v-model="date"-->
<!--                :disabledDates="disabledDates"/>-->
<!--          </div>-->
<!--          <div class="storageEquip">-->
<!--            <h5>내 캠핑장비 선택</h5>-->
<!--            <hr>-->
<!--            <div>-->
<!--              <ul v-for="(item, index) in myItem" :key="index" class="item" >-->
<!--                <li @click="checkItemList(item,index)">{{ item.memEquipmentName }}</li>-->
<!--              </ul>-->
<!--            </div>-->
<!--          </div>-->
<!--        </div>-->
<!--        <div>-->
<!--          <h5>보관 장비</h5>-->
<!--          <hr>-->
<!--          <div>-->
<!--            <div class="chkItemList">-->
<!--              <table>-->
<!--                <thead>-->
<!--                <tr>-->
<!--                  <th>장비</th>-->
<!--                  <th>수량</th>-->
<!--                </tr>-->
<!--                </thead>-->
<!--                <tbody>-->
<!--                <tr v-for="(item,index) in checkItem">-->
<!--                  <td>{{ item.itemName }}</td>-->
<!--                  <td>-->
<!--                    <select v-model="item.count">-->
<!--                      <option v-for="inx in item.itemCount" :value="inx">{{ inx }}</option>-->
<!--                    </select>-->
<!--                    개-->
<!--                  </td>-->
<!--                </tr>-->
<!--                </tbody>-->
<!--              </table>-->
<!--            </div>-->
<!--          </div>-->
<!--        </div>-->
<!--        <div class="storageBottomDiv">-->
<!--          <div>-->
<!--            결제금액 : {{ form.price }}원-->
<!--          </div>-->
<!--          <div class="detailBtn">-->
<!--            <button class="pay-btn" @click="pay">다음</button>-->
<!--          </div>-->
<!--        </div>-->
<!--      </div>-->
<!--    </div>-->
<!--  </div>-->
</template>

<script asp-append-version=“true”>
import axios from "axios";

import Datepicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css';
import store from "@/store";

export default {
  //eslint-disable-next-line
  name: "StorageDetail",
  components: {
    Datepicker
  },
  mounted() {
    axios.get('/api/myItem/' + this.userId)
        .then(res => {
          this.myItem = res.data
          console.log(this.myItem)
        }).catch(err => {
      console.log(err)
    });
    this.boxArrayR()
    setTimeout(() => {
      this.backFlag = true
    }, 100)
  },
  created() {
    this.userId = store.getters.getLoginState.loginState
    this.boxList = this.$store.state.storage
    this.name = this.boxList.storageName
    this.form.storageName = this.name
  },
  data() {
    return {
      checkItem: [],
      myItem: undefined || {},
      id: '',
      boxList: [],
      name: '',
      boxName: '',
      date: null,
      userId: '',
      form: {
        storageBoxCode: '',
        storageName: '',
        price: '',
      },
      today: new Date(),
      useTimeList: [],
      disabledDates: [],
      stateCheck: false,
      boxArray: [],
      backFlag: false,
      putItem: {},

      payCheck: false,
      member: {},
    }
  },
  methods: {
    boxArrayR() {
      let arrayone = []
      for (let i = 0; i < this.boxList.storageBoxes.length; i++) {
        arrayone[0 + i % 5] = this.boxList.storageBoxes[i]

        if ((i + 1) % 5 == 0 || (i + 1) == this.boxList.storageBoxes.length) {
          this.boxArray.push(arrayone)
          arrayone = []
        }
      }
    },
    backPage() {
      this.$router.push('/storageView')
    },
    // 각 보관함별 사용일 찾기
    checkStateBtn(){
      this.stateCheck = !this.stateCheck
    },
    payCheckStateBtn(){
      if (this.date == null) {
        alert('날짜 선택하세요')
        return
      }else{
        if(this.stateCheck === true){
          this.stateCheck = !this.stateCheck
        }
        this.payCheck = !this.payCheck
        this.memberInfo()
        this.form.item = this.checkItem;
      }
    },
    memberInfo(){
      axios.get('/api/myAddress/'+this.userId)
          .then(res=>{
            this.member = res.data
            console.log(this.member)
          })
          .catch(err=>{
            console.log(err)
          })
    },
    findTime(boxCode) {
      this.boxName = boxCode.storageBoxName
      this.form.storageBoxName = boxCode.storageBoxName
      this.form.storageBoxCode = boxCode.storageBoxCode
      this.date = null
      this.disabledDates = []

      axios.get('/api/findUseTime/' + boxCode.storageBoxCode)
          .then(res => {
            this.useTimeList = res.data
            for (let i = 0; i < this.boxList.storageBoxes.length; i++) {
              if (boxCode.storageBoxCode == this.boxList.storageBoxes[i].storageBoxCode) {
                if (this.boxList.storageBoxes[i].storageBoxState == '0') {
                  this.stateCheck = true
                } else if (this.boxList.storageBoxes[i].storageBoxState == '6') {
                  this.stateCheck = true
                } else {
                  this.stateCheck = false
                }
              }
            }
            for (var i = 0; i < this.useTimeList.length; i++) {
              let startDate = new Date(
                  this.useTimeList[i].useStorageStartTime[0],
                  this.useTimeList[i].useStorageStartTime[1] - 1,
                  this.useTimeList[i].useStorageStartTime[2])

              let endDate = new Date(
                  this.useTimeList[i].useStorageEndTime[0],
                  this.useTimeList[i].useStorageEndTime[1] - 1,
                  this.useTimeList[i].useStorageEndTime[2])

              var length = Math.ceil((endDate.getTime() - startDate.getTime()) / (1000 * 3600 * 24))

              const tomorrow = startDate
              this.disabledDates.push(tomorrow.toString())

              for (var j = 0; j < length; j++) {
                tomorrow.setDate(tomorrow.getDate() + 1)
                this.disabledDates.push(tomorrow.toString())
              }
            }
          })
          .catch(err => {
            console.log(err)
          })
      for (let i = 0; i < this.boxList.storageBoxes.length; i++) {
        if (boxCode.storageBoxCode == this.boxList.storageBoxes[i].storageBoxCode) {
          if (this.boxList.storageBoxes[i].storageBoxState == '0') {
            this.stateCheck = true
          } else {
            this.stateCheck = false
          }
        }
      }
      // 달력에 날짜 비활성화
      for (var i = 0; i < this.useTimeList.length; i++) {
        let startDate = new Date(
            this.useTimeList[i].useStorageStartTime[0],
            this.useTimeList[i].useStorageStartTime[1] - 1,
            this.useTimeList[i].useStorageStartTime[2])

        let endDate = new Date(
            this.useTimeList[i].useStorageEndTime[0],
            this.useTimeList[i].useStorageEndTime[1] - 1,
            this.useTimeList[i].useStorageEndTime[2])

        var length = Math.ceil((endDate.getTime() - startDate.getTime()) / (1000 * 3600 * 24))
        const tomorrow = startDate
        this.disabledDates.push(tomorrow.toString())

        for (var j = 0; j < length; j++) {
          tomorrow.setDate(tomorrow.getDate() + 1)
          this.disabledDates.push(tomorrow.toString())
        }
      }
      this.form.price = boxCode.storageBoxPrice
      this.form.storageBoxCode = boxCode.storageBoxCode
    },
    checkItemList(item, index) {
      this.putItem.itemCode = item.memEquipmentCode
      this.putItem.itemName = item.memEquipmentName
      this.putItem.itemCount = item.memEquipmentCount
      this.putItem.count = this.putItem.itemCount
      for (let i = 0; i < this.checkItem.length; i++) {
        if (this.checkItem[i].itemCode == this.putItem.itemCode) {
          let a = document.getElementsByClassName("item")
          a[index].style.backgroundColor = "white"
          this.checkItem.splice(i, 1)
          return
        }
      }
      this.additem(this.putItem, index)
    },
    additem(putItem, index) {
      this.checkItem.push(putItem)
      this.putItem = {}
      let a = document.getElementsByClassName("item")
      a[index].style.backgroundColor = "#e5e5e5"
    },
    pay() {
      if (this.date == null) {
        alert('날짜 선택하세요')
        return
      }

      this.form.storageName = this.name
      this.form.item = this.checkItem

      const start = new Date(this.date)
      let timeStorage = {}
      timeStorage.useStorageStartTime = this.date
      timeStorage.useStorageEndTime = new Date(start.setDate(start.getDate() + 30))
      this.$store.commit('putItemStorage', this.checkItem)
      this.$store.commit('putTimeStorage', timeStorage)
      this.$store.commit('putInfoStorage', this.form)
      this.form.item = []
      this.$router.push({name: "storagePay"})

      this.checkItem = []
      this.date = []
      this.form.storageBoxCode = ''
      this.form.userId = ''
      this.form.useStorageStartTime = ''
      this.form.useStorageEndTime = ''
    }
  },
  watch: {
    backFlag() {
      var divItem = document.getElementsByClassName("storage-box")
      var index = 0
      for (var x = 0; x < this.boxList.storageBoxes.length; x++) {
        if (this.boxList.storageBoxes[x].storageBoxState != 0) {
          divItem[index].classList.add("disabledDiv")
        } else if (this.boxList.storageBoxes[x].storageBoxState == 6) {
          divItem[index].classList.add("playOutDiv")
        }
        index++
      }
    }
  }
}
</script>

<style scoped>
select {
  width: 50px;
  height: 30px;
  padding: 3px;
  border: 1px solid #999;
  font-family: inherit;
  no-repeat: 95% 50%;
  border-radius: 0px;
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
}


/*추가*/
/*.storageUpDiv {*/
/*  display: flex;*/
/*  width: 100%;*/
/*}*/

/*.storageTime {*/
/*  width: 30%;*/
/*  position: relative;*/
/*}*/

/*.storageEquip {*/
/*  margin-left: 5%;*/
/*  width: 30%;*/
/*  position: relative;*/
/*}*/

/*.storageBottomDiv {*/
/*  margin-top: 2%;*/
/*  margin-right: 5%;*/
/*  left: 70%;*/
/*  position: relative;*/
/*  width: 25%;*/
/*  text-align: left;*/
/*}*/

/*.disabledDiv {*/
/*  background: rgba(161, 156, 156, 0.97);*/
/*  border: solid 3px rgba(16, 33, 145, 0.99);*/
/*  color: white;*/
/*}*/

/*.playOutDiv {*/
/*  background: #c3c3c3;*/
/*  color: #000000;*/
/*}*/

/*.setting-date {*/
/*  width: 30%;*/
/*  float: left;*/
/*  display: inline-block;*/
/*}*/

/*ul {*/
/*  list-style: none;*/
/*  padding-left: 0px;*/
/*}*/

/*.detailDiv {*/
/*  margin-top: 3%;*/
/*  margin-right: 5%;*/
/*  margin-left: 5%;*/
/*}*/

/*.detailBtn {*/
/*  text-align: right;*/
/*  width: 100%;*/
/*}*/

/*!*기존*!*/
/*.user-storage-view h3 {*/
/*  margin-top: 3%;*/
/*  margin-left: 7%;*/
/*  margin-bottom: 7%;*/
/*}*/

/*.storage-box {*/
/*  margin-right: 5%;*/
/*  border: solid 3px #00a3de;*/
/*  border-radius: 10px;*/
/*  width: 15%;*/
/*}*/

/*.storage-view {*/
/*  margin-bottom: 1%;*/
/*  margin-left: 5%;*/
/*  margin-right: 5%;*/
/*  display: -webkit-flex;*/
/*  display: flex;*/
/*}*/

/*.storage-back-btn {*/
/*  margin-top: 1%;*/
/*  margin-left: 1%;*/
/*  margin-bottom: 1.5%;*/
/*  text-align: center;*/
/*  width: 12%;*/
/*  padding: 0.8%;*/
/*  background-color: #ffffff;*/
/*  font-weight: bolder;*/
/*  color: #00a3de;*/
/*  border-color: #00a3de;*/
/*}*/

/*.storage-back-btn:hover {*/
/*  color: white;*/
/*  background-color: #b2e2fd;*/
/*}*/

/*.storage {*/
/*  border: solid 3px #000a69;*/
/*  margin-left: 10%;*/
/*  margin-right: 10%;*/
/*  width: 80%;*/
/*  margin-top: 3%;*/
/*}*/

/*.storage-name-h5 {*/
/*  margin-left: 2%;*/
/*  margin-top: 2%;*/
/*  margin-bottom: 2%;*/
/*  font-weight: bolder;*/
/*  width: 40%;*/
/*}*/

/*.pay-btn {*/
/*  margin-bottom: 2%;*/
/*  text-align: center;*/
/*  width: 50px;*/
/*  padding: 1%;*/
/*  background-color: #ffffff;*/
/*  font-weight: bolder;*/
/*  color: #00a3de;*/
/*  border-color: #00a3de;*/
/*}*/

/*.pay-btn:hover {*/
/*  color: white;*/
/*  background-color: #b2e2fd;*/
/*}*/

/*.chkItemList thead {*/
/*  background-color: #6e6e6e;*/
/*  color: white;*/
/*}*/

/*.chkItemList tr {*/
/*  border-bottom: 1px solid black;*/
/*}*/
</style>