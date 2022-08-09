<template>
  <div class="container">
    <div class="container-top">
      <br><br><br>
      <h3>My Storage Box</h3>
    </div>
    <div class="left-box" style="padding: 0">
      <div class="list-group w-auto">
        <a href="#" class="list-group-item list-group-item-action d-flex gap-3 py-3" aria-current="true" v-for="(useBox,index) in useBoxes" :key="index" @click="detailBox(useBox,index)">
          <div class="d-flex gap-2 w-100 justify-content-between">
            <div>
              <h6 class="mb-0">{{ useBox.storageName }}, {{ useBox.boxName }}</h6>
              <p class="mb-0 opacity-95">{{updateState(useBox.useState)}}</p>
            </div>
          </div>
        </a>
      </div>
<!--      <div class="list-group" v-for="(useBox,index) in useBoxes" :key="index" @click="detailBox(useBox,index)">-->
<!--        <a href="#" class="list-group-item list-group-item-action list-group-item-secondary">A simple secondary list group item</a>-->
<!--      </div>-->

<!--      <div class="card-body">-->
<!--        <div>-->
<!--          <table class='scrolltbody'>-->
<!--            <thead>-->
<!--              <tr>-->
<!--                <th>보관소</th>-->
<!--                <th>보관함</th>-->
<!--                <th>상태</th>-->
<!--              </tr>-->
<!--            </thead>-->
<!--            <tbody>-->
<!--              <tr v-for="(useBox,index) in useBoxes" :key="index" @click="detailBox(useBox,index)" class="item">-->
<!--                <td>{{ useBox.storageName }}</td>-->
<!--                <td>{{ useBox.boxName }}</td>-->
<!--                <td>{{updateState(useBox.useState)}}</td>-->
<!--              </tr>-->
<!--            </tbody>-->
<!--          </table>-->
<!--        </div>-->
<!--      </div>-->
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
        case "1":
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
                box.useState = boxes[i][6].toString().charAt(0)
                if (box.useState == "9" || box.useState == "a"){
                  box.del = boxes[i][6].substring(1, boxes[i][6].length)
                }else {
                  if (boxes[i][6].length > 1) {
                    box.moveUseCode = boxes[i][6].substring(1, boxes[i][6].length)
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
      // let a = document.getElementsByClassName("item")
      // for (let i = 0; i < a.length; i++) {
      //   a[i].style.backgroundColor = "white"
      // }
      // a[index].style.backgroundColor = "#cde1e8"
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
  width: 1400px;
}
.left-box{
  width: 500px;
  margin-top: -40px;
}
/*.right-box{*/
/*  border: 1px solid black;*/
/*  float: right;*/
/*  margin-right: 10%;*/
/*  height: 50%;*/
/*  width: 40%;*/
/*}*/



.list-group {
  max-width: 460px;
  margin: 4rem auto;
}

.form-check-input:checked + .form-checked-content {
  opacity: .5;
}

.form-check-input-placeholder {
  border-style: dashed;
}
[contenteditable]:focus {
  outline: 0;
}

.list-group-checkable .list-group-item {
  cursor: pointer;
}
.list-group-item-check {
  position: absolute;
  clip: rect(0, 0, 0, 0);
}
.list-group-item-check:hover + .list-group-item {
  background-color: var(--bs-light);
}
.list-group-item-check:checked + .list-group-item {
  color: #fff;
  background-color: var(--bs-blue);
}
.list-group-item-check[disabled] + .list-group-item,
.list-group-item-check:disabled + .list-group-item {
  pointer-events: none;
  filter: none;
  opacity: .5;
}

.list-group-radio .list-group-item {
  cursor: pointer;
  border-radius: .5rem;
}
.list-group-radio .form-check-input {
  z-index: 2;
  margin-top: -.5em;
}
.list-group-radio .list-group-item:hover,
.list-group-radio .list-group-item:focus {
  background-color: var(--bs-light);
}

.list-group-radio .form-check-input:checked + .list-group-item {
  background-color: var(--bs-body);
  border-color: var(--bs-blue);
  box-shadow: 0 0 0 2px var(--bs-blue);
}
.list-group-radio .form-check-input[disabled] + .list-group-item,
.list-group-radio .form-check-input:disabled + .list-group-item {
  pointer-events: none;
  filter: none;
  opacity: .5;
}

</style>
