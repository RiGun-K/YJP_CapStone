<template>
  <div class="renewal-box">
    <h3>사용 중인 보관함 조회</h3>
    <div class="card"  style="width: 50%; font-weight: bolder; margin-left: 7%">
      <div class="card-body">
        <div>
          <table class='scrolltbody'>
            <tbody>
              <tr>
                <td>보관소</td>
                <td>보관함</td>
                <td>시작일</td>
                <td>종료 예정일</td>
              </tr>
              <tr v-for="(useBox,index) in useBoxes" :key="index">
                <td @click="detailBox(useBox.useBoxCode)">{{ useBox.storageName }}</td>
                <td @click="detailBox(useBox.useBoxCode)">{{ useBox.boxName }}</td>
                <td @click="detailBox(useBox.useBoxCode)">{{ useBox.startTime }}</td>
                <td @click="detailBox(useBox.useBoxCode)">{{ useBox.endTime }}</td>
                <td v-if="useBox.none">
                  <button class="mystoragebox-re" v-if="useBox.useBoxState=='2'" @click="moveBox(useBox)">장비 이동</button>
                  <button class="mystoragebox-re" v-if="useBox.useBoxState=='2'" @click="repairBox(useBox)">장비 수리</button>
                  <button class="mystoragebox-re" v-if="useBox.BoxState == '3' || useBox.useBoxState=='2'" @click="renewalPay(useBox)">연장</button>
                  <button class="mystoragebox-re" @click="closeBox(useBox)">해지</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <div v-if="modal">
      <button @click="close()">X</button>
      <h5>보관 캠핑장비</h5>

      보관된 장비를 추가하기
      <button @click="addShow()"><h9 v-if="!addItemCheck">보기</h9><h9 v-else>숨기기</h9></button>
      <div v-if="addItemCheck">
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
            <td>{{index+1}}</td>
            <td>{{item.memEquipmentName}}</td>
            <td>{{item.memEquipmentCount}}</td>
            <td><input type="checkbox" :value="item.memEquipmentCode" v-model="addBoxInItem" ></td>
          </tr>
          </tbody>
        </table>
        <button @click="addItem()">추가하기</button>
      </div>
      <div v-if="myItem.length > 0">
        <h5>보관장비</h5>
        <hr>
        <table>
          <thead>
          <tr>
            <th colspan="2">장비</th>
            <th>수량</th>
            <th>선택 <button @click="outItem()">빼내기</button></th>
          </tr>
          </thead>
          <tbody v-for="(item,index) in myItem" :key="index">
          <tr>
            <td>{{index+1}}</td>
            <td>{{item.memEquipmentName}}</td>
            <td>{{item.memEquipmentCount}}</td>
            <td><input type="checkbox" :value="item.memEquipmentCode" v-model="outBoxItem" ></td>
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
</template>

<script>
import axios from "axios";
import store from "@/store";

export default {
  name: "MyStorageBox",
  components:{
  },
  data() {
    return {
      modal:false,
      memberId: '',
      useBoxes: [],
      myItem:{},
      notInItem:[],
      addBoxInItem:[],
      addItemCheck:false,
      pickUseBox:'',
      outBoxItem:[],
    }
  },
  mounted() {
    this.getBakData()
    this.getMyItem()
  },
  methods: {
    getBakData(){
      this.memberId = store.getters.getLoginState.loginState
      this.useBoxes = []
      axios.get('/api/checkMember/' + this.memberId)
          .then(res => {
            if (res.data.length < 1) {
              alert('사용중인 보관함이 없습니다')
              this.memberId = ''
            } else {
              const boxes = res.data
              this.useBoxes = []
              const arrDayStr = ['일', '월', '화', '수', '목', '금', '토']
              for (let i = 0; i < boxes.length; i++) {
                const box = {
                  startTime: '',
                  endTime: '',
                  boxCode:'',
                  boxName: '',
                  boxState: '',
                  storageName: '',
                  useBoxCode:'',
                  useBoxState:'',
                  start: '',
                  end: '',
                  none: true
                }
                const start = new Date(boxes[i][0])
                const end = new Date(boxes[i][1])
                box.startTime = start.getFullYear() + '년' + (start.getMonth() + 1) + '월' + start.getDate() + '일 (' + arrDayStr[start.getDay()] + ')'
                box.endTime = end.getFullYear() + '년' + (end.getMonth() + 1) + '월' + end.getDate() + '일 (' + arrDayStr[end.getDay()] + ')'
                box.boxCode = boxes[i][2]
                box.boxName = boxes[i][3]
                box.boxState = boxes[i][4]
                box.storageName = boxes[i][5]
                box.useBoxCode = boxes[i][6]
                box.useBoxState = boxes[i][7]
                box.start = start
                box.end = end
                // box.boxState 가 1 이면 연장 버튼 on 0이면 연장 버튼 off
                if (i > 0) {
                  // 이전 보관함이름 = 지금 보관함이름 && 이전 보관소 이름 = 지금 보관소 이름
                  if (boxes[i - 1][3] == box.boxName && box.storageName == boxes[i - 1][5]) {
                    console.log(box.boxState)
                    if (box.boxState == '1') {
                      this.useBoxes[i - 1].none = false

                      box.none = true
                      console.log(box.none)
                    } else if (box.boxState == '0') {
                      box.none = false
                    }
                    // 이전 보관함이름 == 지금 보관함이름 && 이전 보관소 이름 != 지금 보관소 이름
                  } else if (boxes[i - 1][3] == box.boxName && box.storageName != boxes[i - 1][5]) {
                    if (box.boxState == '1') {
                      box.none = true
                    } else if (box.boxState == '0') {
                      box.none = false
                    }
                    // 이전 보관함이름 != 지금 보관함이름 && 이전 보관소 이름 == 지금 보관소 이름
                  } else if (boxes[i - 1][3] != box.boxName && box.storageName == boxes[i - 1][5]) {
                    if (box.boxState == '1') {
                      box.none = true
                    } else if (box.boxState == '0') {
                      box.none = false
                    }
                    // 이전 보관함이름 != 지금 보관함이름 && 이전 보관소 이름 != 지금 보관소 이름
                  } else if (boxes[i - 1][3] != box.boxName && box.storageName != boxes[i - 1][5]) {
                    if (box.boxState == '1') {
                      box.none = true
                    } else if (box.boxState == '0') {
                      box.none = false
                    }
                  }
                } else {
                  if (box.boxState == '1') {
                    box.none = true
                  } else if (box.boxState == '0') {
                    box.none = false
                  }
                }
                this.useBoxes.push(box)
              }
              console.log(this.useBoxes)
            }
          })
          .catch(err => {
            console.log(err)
          })
    },
    close(){
      this.modal = false
    },
    detailBox(useBoxCode){
      if(!this.modal){
        this.modal = !this.modal
      }
      axios.get('/api/getBoxInItem/'+ useBoxCode)
      .then(res=>{
        console.log(res.data)
        this.myItem = res.data
        this.pickUseBox = useBoxCode
      })
      .catch(err=>{
        console.log(err)
      })
    },
    moveBox(useBox){
      this.$store.commit('moveBoxInfo',useBox)
      this.$router.push({
        name: 'moveBox',
        params: {
          userId: this.memberId,  // 사용자 아이디
          storageName: useBox.storageName,    // 보관소 이름
          boxName: useBox.boxName,       // 보관함 이름
          boxCode: useBox.boxCode,        // 보관함 코드
          useBoxCode : useBox.useBoxCode // 사용 보관함 코드
        }
      })
    },
    repairBox(useBox){
      this.$router.push({
        name: 'repairBox',
        params: {
          storageName: useBox.storageName,    // 보관소 이름
          boxName: useBox.boxName,       // 보관함 이름
          useBoxCode : useBox.useBoxCode  // 사용 보관함 코드
        }
      })
    },
    renewalPay(useBox) {
      console.log('useBox.useBoxCode')
      console.log(useBox.useBoxCode)
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
          useBoxCode : useBox.useBoxCode  // 사용 보관함 코드
        }
      })
    },
    closeBox(useBox){
      this.$router.push({name:'closeBox',params:{useBoxCode:useBox.useBoxCode}})
    },
    getMyItem(){
      axios.get('/api/myItem/'+this.memberId)
          .then(res=>{
            this.notInItem = res.data
          })
          .catch(err=>{
            console.log(err)
          })
    },
    addShow(){
      if(this.addItemCheck){
        this.addItemCheck = false
      }else{
        this.addItemCheck = true
      }
    },
    addItem(){
      if (this.addBoxInItem.length < 1){
        alert('추가하실 장비를 선택하세요')
        return
      }
      let data = {
        useBoxCode:this.pickUseBox,
        itemList:this.addBoxInItem
      }
      axios.post('/api/addBoxInItem',data)
      .then(res=>{
        if(res.data.result=='ok'){
          alert('추가되었습니다')
          this.addBoxInItem=[]
          this.getMyItem()
          this.detailBox(this.pickUseBox)
        }else {
          alert('에러, 다시 시도해 주세요')
        }
      })
      .catch(err=>{
        console.log(err)
      })
    },
    outItem(){
      if (this.outBoxItem.length < 1){
        alert('제거하실 장비를 선택하세요')
        return
      }
      let data = {
        useBoxCode:this.pickUseBox,
        itemList:this.outBoxItem
      }
      axios.post('/api/outBoxInItem',data)
          .then(res=>{
            if(res.data.result=='ok'){
              alert('제거 되었습니다')
              this.outBoxItem=[]
              this.getMyItem()
              this.detailBox(this.pickUseBox)
            }else {
              alert('에러, 다시 시도해 주세요')
            }
          })
          .catch(err=>{
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
.renewal-box h3{
  margin-top: 3%;
  margin-left: 7%;
  margin-bottom: 3%;
}
.mystoragebox-re{
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
.scrolltbody td{
  padding: 1%;
  width: 3%;
}

</style>
