<template>
  <div class="storage">
    <button @click="backPage" class="storage-back-btn">되돌아가기</button>

    <h5 class="storage-name-h5">보관소 이름:{{ name }}</h5>

    <div class="storage-view">
      <h5 style="margin-left: 3%" >사용하실 보관함을 선택하세요</h5>
      <div class="storage-box" v-for="(box,index) in boxList.storageBoxes" :key="index">
        <ul class="storage-box-ck" @click="nextTab(box.storageBoxCode, box.storageBoxState, box.storageBoxName)">
          <li>보관함 이름: {{ box.storageBoxName }}</li>
          <li>보관함 상태:<p v-if="box.storageBoxState == '0'">사용가능</p>
            <p v-else>사용불가능</p>
          </li>
        </ul>
      </div>
    </div>
    <div v-if="stateCheck">
      <div>
        이동 금액
        +
        보관함 변경 추가금액
        결제금액 : {{ newForm.price }}원
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
    this.id = this.$route.params.storageCode
    this.form.storageName = this.$route.params.storageName
    this.form.boxName = this.$route.params.boxName
    this.form.boxCode = this.$route.params.boxCode
    this.form.useBoxCode = this.$route.params.useBoxCode
    this.userId = store.getters.getLoginState.loginState
    this.moveBoxInfo = this.$store.state.moveBoxInfo
    console.log('this.userId')
    console.log(this.userId)
    console.log(this.form)
    console.log(this.moveBoxInfo)
  },
  mounted() {
    axios.get('/api/storageView/' + this.id)
        .then((res) => {
          console.log(res.data)
          this.boxList = res.data
          console.log('this.boxList')
          console.log(this.boxList)
          this.name = this.boxList.storageName
        })
        .catch((err) => {
          console.log(err)
        })
  },
  data() {
    return {
      form: undefined||{},
      id: '',
      boxList: [],
      name: '',
      userId:'',
      boxCode:'선택',
      newForm:{
        userId:'',
        storageName:'',
        boxName:'',
        storageBoxCode:'',
        price:''
      },
      stateCheck:false,
      moveBoxInfo:{},
    }
  },
  watch:{
    boxCode:function (newBoxCode){
      this.boxPrice(newBoxCode)
    }
  },
  methods:{
    boxPrice(newBoxCode){
      axios.get('/api/boxPrice/'+newBoxCode)
          .then(res=>{
            console.log(res.data)
            this.newForm.price = res.data
          })
    },
    nextTab(boxCode, state, name){
      if(state != "0"){
        this.stateCheck = false
      }else {
        this.stateCheck = true
        this.boxCode = boxCode
        this.newForm.boxName = name
      }

    },
    backPage(){
      this.$router.push('/myBox/moveBox')
    },
    pay(){
      this.newForm.storageName = this.name
      this.newForm.storageBoxCode = this.boxCode
      this.$router.push({
            name:"BoxToBoxMovePay",
            params:{
              // 이전
              beforeStorageCode: this.id,
              beforeStorageName:this.form.storageName,
              beforeBoxName:this.form.boxName,
              beforeStorageBoxCode:this.form.boxCode,
              beforeuseBoxCode:this.form.useBoxCode,
              // 이동
              newStorageName:this.name,
              newBoxName:this.newForm.boxName,
              newStorageBoxCode:this.boxCode
            }
          })

      this.newForm.storageBoxCode = ''
      this.newForm.userId = ''
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
.storage-box-ck{
  width: 15%;
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
