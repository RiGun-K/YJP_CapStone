<template>
  <div class="container">
    <div class="container-top">
      <br><br><br>
      <h3>사용 중인 보관함 조회</h3>
    </div>
    <div class="left-box">
      <div class="card-body">
        <div>
          <table class='scrolltbody'>
            <thead>
              <tr>
                <th>보관소</th>
                <th>보관함</th>
                <th>상태</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(useBox,index) in useBoxes" :key="index" @click="detailBox(useBox,index)" class="item">
                <td>{{ useBox.storageName }}</td>
                <td>{{ useBox.boxName }}</td>
                <td>{{updateState(useBox.useState)}}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
  <div v-if="modal" class="right-box" >
    <MyUseBoxDetail :modal="modal" :useData="useData" @close="close()" />
  </div>
</template>

<script>
import axios from "axios";
import store from "@/store";
import MyUseBoxDetail from "@/components/storageService/user/MyUseBoxDetail.vue";

export default {
  name: "MyStorageBox",
  components: {
    MyUseBoxDetail
  },
  data() {
    return {
      modal: false,
      memberId: '',
      useBoxes: [],
      useData:{},
    }
  },
  mounted() {
    this.getBakData()
  },
  methods: {
    updateState(state){
      switch (state){
        case "0":
          return "결제완료"
        break
        case "2":
          return "사용중"
          break
        case "3":
          case "4":
          return "보관함이동"
          break
        case "6":
          return "수리신청"
          break
        case "7":
          return "수리 중"
          break
        case "8":
          return "수리완료"
          break
        case "5":
        case "9":
        case "a":
          return "배송"
          break
      }
    },
    getBakData() {
      this.memberId = store.getters.getLoginState.loginState
      this.useBoxes = []
      axios.get('/api/checkMember/' + this.memberId)
          .then(res => {
            console.log(res.data)
            if (res.data.length < 1) {
              alert('사용중인 보관함이 없습니다')
            } else {
              const boxes = res.data
              this.useBoxes = []
              for (let i = 0; i < boxes.length; i++) {
                const box = {}
                box.storageCode = boxes[i].STORAGECODE
                box.storageName = boxes[i].STORAGENAME
                box.boxCode = boxes[i].BOXCODE
                box.boxName = boxes[i].BOXNAME
                box.boxState = boxes[i].BOXSTATE
                box.useCode = boxes[i].USECODE
                box.useState = boxes[i].USESTATE.toString().charAt(0)
                if (box.useState == "9" || box.useState == "a"){
                  box.del = boxes[i].USESTATE.substring(1, boxes[i].USESTATE.length)
                }else {
                  if (boxes[i].USESTATE.length > 1) {
                    box.moveUseCode = boxes[i].USESTATE.substring(1, boxes[i].USESTATE.length)
                  }
                }
                this.useBoxes.push(box)
              }
            }
          })
          .catch(err => {
            console.log(err)
          })
    },
    close() {
      this.modal = false
      let a = document.getElementsByClassName("item")
      for (let i = 0; i < a.length; i++) {
        a[i].style.backgroundColor = "white"
      }
    },
    detailBox(us,index) {
      let a = document.getElementsByClassName("item")
      for (let i = 0; i < a.length; i++) {
        a[i].style.backgroundColor = "white"
      }
      a[index].style.backgroundColor = "#cde1e8"
      this.useData = {}
      if (!this.modal) {
        this.modal = !this.modal
      }
      this.useData = us

    },
  }
}
</script>

<style scoped>
.container{
  padding: initial;
  position: center;
  width: 95%;
}
.left-box{
  border: 1px solid black;
  float: left;
  width: 40%;
}
.right-box{
  border: 1px solid black;
  float: right;
  margin-right: 10%;
  height: 50%;
  width: 40%;
}
td{
  border-bottom: 1px solid black;
  padding-top:17px;
  padding-bottom:12px;
  word-spacing:9px;
  text-align:center;
}

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
