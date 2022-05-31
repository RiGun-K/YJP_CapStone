<template>
  <div>
    <h5>장비수리</h5>
    <div>
      <table>
        <tr>
          <td>보관소 명</td>
          <td>{{ storageName }}</td>
        </tr>
        <tr>
          <td>보관함</td>
          <td>{{ boxName }}</td>
        </tr>
      </table>
    </div>
  </div>
  <div>
    <h5>장비리스트 수리 맡길 장비 선택</h5>
    <div>
      <ul v-for="(item,index) in myItemList" :key="index">
        <li>
          <table style="width: 300px" border="1">
            <tbody>
              <tr>
                <td>{{ item.memEquipmentName }}</td>
                <td>{{ item.memEquipmentCount }}</td>
                <td><input type="checkbox" :value="item" v-model="repairList"></td>
              </tr>
            </tbody>
          </table>
        </li>
      </ul>
    </div>
  </div>
  <div>
    <h5>수리 맡길 장비 기본 사항 선택+ 기타(직접입력)</h5>
    <div>
      <input type="checkbox" v-model="requestList" value="건조">건조 <br>
      <input type="checkbox" v-model="requestList" value="세탁">세탁 <br>
      <input type="checkbox" v-model="requestList" value="폴대 수리">폴대 수리 <br>
      <input type="checkbox" v-model="reqGita" value="기타">기타 <br>
      <textarea v-model="gitaRepair" />
    </div>
  </div>
  <div>
    <button @click="$router.push({name:'myBox'})">취소</button>
    <button @click="payPage()">다음</button>

  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "RepairBox",
  data() {
    return {
      storageName: '',
      boxName: '',
      myItemList: [],
      repairList: [],
      requestList:[],
      reqGita:'',
      gitaRepair:'',
      gita:false
    }
  },
  mounted() {
    this.getBackData(this.$route.params.useBoxCode)
    this.getBoxData(this.$route.params.useBoxCode)
  },
  watch:{
    reqGita:function (){
      if (this.reqGita.length>0){
        this.gita = true
      }else{
        this.gita = false
      }
    }
  },
  methods: {
    getBoxData(useCode){
      axios.get('/api/moveBoxInfo/' + useCode)
          .then(res => {
            this.storageName = res.data[0][1]
            this.boxName = res.data[0][3]
          })
          .catch(err => {
            console.log(err)
          })
    },
    getBackData(useCode) {
      console.log(useCode)
      axios.get("/api/getBoxInItem/" + useCode)
          .then(res => {
            console.log(res.data)
            this.myItemList = res.data
          })
          .catch(err => {
            console.log(err)
          })
    },
    payPage(){
      let item = {}
      item.repairList = this.repairList
      item.requestList = this.requestList
      if(this.gita){
        item.gitaRepair = this.gitaRepair
      }
      this.$store.commit('careItemInfo',item)
      this.$router.push({name:'repairBoxPay'})
    }
  },
}
</script>

<style scoped>

</style>