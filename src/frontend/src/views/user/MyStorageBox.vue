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
              <td v-if="useBox.useState == 2 || useBox.useState == 6">사용중</td>
              <td v-if="useBox.useState == 3">보관함이동</td>
              <td v-if="useBox.useState == 4">보관함이동</td>
              <td v-if="useBox.useState == 5">배송</td>
              <td v-if="useBox.useState == 9">배송</td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>

  <div v-if="modal">
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
                if (boxes[i][6].length > 1) {
                  box.moveUseCode = boxes[i][6].substring(1, boxes[i][6].length)

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
    },
    detailBox(us) {
      if (!this.modal) {
        this.modal = !this.modal
      }
      this.useData = us

    },
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
