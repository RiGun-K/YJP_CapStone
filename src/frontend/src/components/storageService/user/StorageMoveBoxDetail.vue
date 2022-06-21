<template>
  <div class="storage">
    <button @click="backPage" class="storage-back-btn">되돌아가기</button>

    <h5 class="storage-name-h5">보관소 이름:{{ name }}</h5>

    <div class="storage-view" v-for="(obj, index) in boxArray">
      <div class="storage-box" v-for="box in boxArray[index]" @click="nextTab(box)">
        <ul>
          <li>보관함 이름: {{ box.storageBoxName }}</li>
          <li>보관함 상태:<p v-if="box.storageBoxState == '0'">사용가능</p>
            <p v-else>사용불가능</p>
          </li>
          <li>가격 : {{ box.storageBoxPrice }}원</li>
        </ul>
      </div>
    </div>
    <div v-if="stateCheck">
      <h5>{{ boxName }}</h5>
      <div>
        이동 금액
        +
        보관함 변경 추가금액
        결제금액 : {{ price }}원
      </div>
      <div>
        <button class="pay-btn" @click="pay">다음</button>
      </div>
    </div>

  </div>
</template>

<script asp-append-version=“true”>
import axios from "axios";
import store from "@/store";

export default {
  //eslint-disable-next-line
  name: "StorageMoveBoxDetail",
  created() {
    axios.get('/api/boxPrice/' + this.$store.state.moveBoxInfo.boxCode)
        .then(res => {
          this.beforeBoxPrice = res.data
        })
        .catch((err) => {
          console.log(err)
        })
  },
  mounted() {
    axios.get('/api/storageView/' + this.$route.params.storageCode)
        .then((res) => {
          this.boxList = res.data
          this.name = this.boxList.storageName
          this.boxArrayR()
        })
        .catch((err) => {
          console.log(err)
        });
    setTimeout(() => {
      this.backFlag = true
    }, 100)
  },
  data() {
    return {
      boxList: [],
      name: '',
      boxCode: '',
      boxName: '',
      price: '',
      stateCheck: false,
      boxArray: {},
      beforeBoxPrice: '',
      backFlag: false,
    }
  },
  watch: {
    boxCode: function (newBoxCode) {
      this.boxPrice(newBoxCode)
    },
    backFlag() {
      var divItem = document.getElementsByClassName("storage-box")
      var index = 0
      for (var x = 0; x < this.boxArray.length; x++) {
        for (var y = 0; y < this.boxArray[x].length; y++) {
          if (this.boxArray[x][y].storageBoxState != 0) {
            divItem[index].classList.add("disabledDiv")
          } else if (this.boxArray[x][y].storageBoxState == 6) {
            divItem[index].classList.add("playOutDiv")
          }
          index++
        }
      }
    }

  },
  methods: {
    boxArrayR() {
      let arrayone = {}
      let k = 0;
      for (let i = 0; i < this.boxList.storageBoxes.length; i++) {
        arrayone[0 + i % 5] = this.boxList.storageBoxes[i]

        if ((i + 1) % 5 == 0 || (i + 1) == this.boxList.storageBoxes.length) {
          this.boxArray[0 + k] = arrayone
          arrayone = {}
          k = k + 1;
        }
      }
    },
    boxPrice(newBoxCode) {
      axios.get('/api/boxPrice/' + newBoxCode)
          .then(res => {
            this.price = res.data
            if (this.price < this.beforeBoxPrice) {
              this.stateCheck = false
            }else if(this.price == this.beforeBoxPrice){
              this.price = 5000
            }else{
              this.price = 5000 + this.price - this.beforeBoxPrice
            }
          })
    },
    nextTab(box) {
      let code = box.storageBoxCode
      let name = box.storageBoxName
      let state = box.storageBoxState
      if (state == '0') {
        this.stateCheck = true
        this.boxCode = code
        this.boxName = name
      } else {
        this.stateCheck = false
      }

    },
    backPage() {
      this.$router.push({name: 'moveBox'})
    },
    pay() {
      this.$router.push({
        name: "BoxToBoxMovePay",
        params: {
          newStorageBoxCode: this.boxCode
        }
      })

    }
  }

}
</script>

<style scoped>
/*기존*/
.user-storage-view h3 {
  margin-top: 3%;
  margin-left: 7%;
  margin-bottom: 7%;
}

.storage-box {
  margin-right: 5%;
  border: solid 3px #00a3de;
  border-radius: 10px;
  width: 15%;
}

.storage-view {
  margin-bottom: 1%;
  margin-left: 5%;
  margin-right: 5%;
  display: -webkit-flex;
  display: flex;
}

.storage-back-btn {
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

.storage-back-btn:hover {
  color: white;
  background-color: #b2e2fd;
}

.storage {
  border: solid 3px #000a69;
  margin-left: 7%;
  width: 87%;
  margin-top: 5%;
}

.storage-name-h5 {
  margin-left: 2%;
  margin-top: 2%;
  margin-bottom: 2%;
  font-weight: bolder;
  width: 40%;
}

.pay-btn {
  margin-bottom: 2%;
  text-align: center;
  width: 12%;
  padding: 1%;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
}

.pay-btn:hover {
  color: white;
  background-color: #b2e2fd;
}
.disabledDiv {
  background: rgba(161, 156, 156, 0.97);
  border: solid 3px rgba(16, 33, 145, 0.99);
  color: white;
}

.playOutDiv {
  background: #c3c3c3;
  color: #000000;
}
</style>
