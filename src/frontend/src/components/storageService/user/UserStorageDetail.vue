<template>
  <div class="storage">
    <button @click="backPage" class="storage-back-btn">되돌아가기</button>

    <h5 class="storage-name-h5">보관소 이름:{{ name }}</h5>

    <div class="storage-view">
      <div class="storage-box" v-for="(box,index) in boxList.storageBoxes" :key="index" @click="findTime(box)">
        <ul>
          <li>보관함 이름: {{ box.storageBoxName }}</li>
          <li>보관함 상태:<p v-if="box.storageBoxState == '0'">사용가능</p>
            <p v-else>사용불가능</p>
          </li>
          <li>가격 : {{box.storageBoxPrice}}원</li>
        </ul>
      </div>
    </div>
    <div v-if="stateCheck">
      <div><h3>{{boxName}}</h3></div>
      <div>
        <p style="margin-left: 3%; margin-top: 2%">대여기간 설정</p>
        <Datepicker style="margin-left: 3%; margin-bottom: 5%; width: 20%"
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
            :disabledDates="disabledDates" />
      </div>
      <div>
        넣을 장비 선택
        내 캠핑장비
        <div>
          <ul v-for="(item, index) in myItem" :key="index">
            <li><input type="checkbox" v-model="checkItem" v-bind:value="item">{{item.memEquipmentName}}</li>
          </ul>
        </div>
      </div>
      <div>
        결제금액 : {{form.price}}원
      </div>
      <div>
        <button class="pay-btn" @click="pay">다음</button>
      </div>
    </div>

  </div>

</template>

<script asp-append-version=“true”>
import axios from "axios";

import Datepicker  from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css';
import store from "@/store";

export default {
  //eslint-disable-next-line
  name: "StorageDetail",
  components:{
    Datepicker
  },
  mounted() {
    axios.get('/api/myItem/'+this.userId)
    .then(res=>{
      console.log(res.data)
      this.myItem = res.data
    }).catch(err=>{
      console.log(err)
    });
  },
  created() {
    this.userId = store.getters.getLoginState.loginState
    this.boxList = this.$store.state.storage
    this.name = this.boxList.storageName
  },
  data() {
    return {
      checkItem:[],
      myItem:undefined||{},
      id: '',
      boxList: [],
      name: '',
      boxName:'',
      date:null,
      startDay:'',
      endDay:'',
      userId:'',
      form:{
        storageName:'',
        storageBoxCode:'',
        useStorageStartTime:'',
        useStorageEndTime:'',
        price:'',
        item:[]
      },
      today: new Date(),
      useTimeList:[],
      disabledDates:[],
      stateCheck:false
    }
  },
  methods:{
    backPage(){
      this.$router.push('/storageView')
    },
    // 각 보관함별 사용일 찾기
    findTime(boxCode){
      this.date = null
      this.disabledDates=[]
      axios.get('/api/findUseTime/' + boxCode.storageBoxCode)
          .then(res=>{
            this.useTimeList = res.data
            console.log(res.data)

            for (let i = 0; i < this.boxList.storageBoxes.length; i++) {
              if(boxCode.storageBoxCode == this.boxList.storageBoxes[i].storageBoxCode){
                if(this.boxList.storageBoxes[i].storageBoxState == '0'){
                  this.stateCheck = true
                }else{
                  this.stateCheck = false
                }
              }
            }
            for(var i = 0; i < this.useTimeList.length;i++){
              let startDate = new Date(
                  this.useTimeList[i].useStorageStartTime[0],
                  this.useTimeList[i].useStorageStartTime[1]-1,
                  this.useTimeList[i].useStorageStartTime[2])

              let endDate = new Date(
                  this.useTimeList[i].useStorageEndTime[0],
                  this.useTimeList[i].useStorageEndTime[1]-1,
                  this.useTimeList[i].useStorageEndTime[2])

              var length = Math.ceil((endDate.getTime() - startDate.getTime())/(1000*3600*24))
              console.log(length)
              const tomorrow = startDate
              this.disabledDates.push(tomorrow.toString())

              for (var j = 0; j < length; j++) {
                tomorrow.setDate(tomorrow.getDate() + 1)
                this.disabledDates.push(tomorrow.toString())
              }
            }
          })
          .catch(err=>{
            console.log(err)
          })
      this.boxName = boxCode.storageBoxName
      this.form.price = boxCode.storageBoxPrice
      console.log('this.form.price')
      console.log(this.form.price)
      this.form.storageBoxCode = boxCode.storageBoxCode
      console.log('this.form.storageBoxCode')
      console.log(this.form.storageBoxCode)
      console.log('this.form234234234')
      console.log(this.form)
    },
    pay(){
      if(this.date ==null){
        alert('날짜 선택하세요')
        return
      }
      this.startDay = this.date
      const start = new Date(this.startDay)
      this.endDay = new Date(start.setDate(start.getDate()+29))

      this.form.storageName = this.name
      this.form.useStorageStartTime = this.startDay
      this.form.useStorageEndTime = this.endDay
      this.form.item = this.checkItem


      this.$store.commit('putCartStorage',this.form)
      this.form.item = []
      this.$router.push({name:"storagePay",params:this.form})

      this.checkItem = []
      this.date = []
      this.startDay = ''
      this.endDay = ''
      this.form.storageBoxCode = ''
      this.form.userId = ''
      this.form.useStorageStartTime = ''
      this.form.useStorageEndTime = ''
    }
  }

}
</script>

<style scoped>
.user-storage-view h3{
  margin-top: 3%;
  margin-left: 7%;
  margin-bottom: 7%;
}
.storage-box {
  border: solid 3px #00a3de;
  border-radius: 10px;
  width: 30%;
}
.storage-view {
  display: -webkit-flex;
  display: flex;
  margin-bottom: 3%;
}
.storage-back-btn{
  margin-top: 1%;
  margin-left: 1%;
  margin-bottom: 1.5%;
  text-align: center;
  width: 12%;
  padding: 0.8%;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
}
.storage-back-btn:hover{
  color: white;
  background-color: #b2e2fd;
}
.storage {
  border: solid 3px #000a69;
  margin-left: 7%;
  width: 87%;
  margin-top: 5%;
}
.storage-name-h5{
  margin-left: 2%;
  margin-top: 2%;
  margin-bottom: 2%;
  font-weight: bolder;
  width: 40%;
}
.pay-btn{
  margin-left: 85%;
  margin-bottom: 2%;
  text-align: center;
  width: 12%;
  padding: 1%;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
}
.pay-btn:hover{
  color: white;
  background-color: #b2e2fd;
}
</style>