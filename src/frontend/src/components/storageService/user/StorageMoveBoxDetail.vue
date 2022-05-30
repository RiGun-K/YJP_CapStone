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
      <h5>{{boxName}}</h5>
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
        })
  },
  data() {
    return {
      boxList: [],
      name: '',
      boxCode:'',
      boxName:'',
      price:'',
      stateCheck:false,
      boxArray: {},
    }
  },
  watch:{
    boxCode:function (newBoxCode){
      this.boxPrice(newBoxCode)
    }
  },
  methods:{
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
    boxPrice(newBoxCode){
      axios.get('/api/boxPrice/'+newBoxCode)
          .then(res=>{
            this.price = res.data
          })
    },
    nextTab(box){
      let code = box.storageBoxCode
      let name = box.storageBoxName
      let state = box.storageBoxState
      if(state == '0'){
        this.stateCheck = true
        this.boxCode = code
        this.boxName = name
      }else {
        this.stateCheck = false
      }

    },
    backPage(){
      this.$router.push({name:'moveBox'})
    },
    pay(){
      this.$router.push({
            name:"BoxToBoxMovePay",
            params:{
              newStorageBoxCode:this.boxCode
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
</style>
