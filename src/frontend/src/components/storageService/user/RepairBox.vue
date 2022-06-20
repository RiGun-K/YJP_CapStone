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
      <table>
        <tbody>
        <tr v-for="re in reList">
          <td>{{re.buyName}}</td>
          <td>{{re.buyEx}}</td>
          <td><input  type="checkbox" v-model="requestList" :value="re"></td>
        </tr>
        <td>기타</td>
        <td></td>
        <td><input type="checkbox" v-model="reqGita" value="기타"></td>
        </tbody>
      </table>
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
      reqGita:false,
      gitaRepair:'',
      reList:{},
    }
  },
  mounted() {
    this.getBackData(this.$route.params.useBoxCode)
    this.getBoxData(this.$route.params.useBoxCode)
    this.getRepairList()
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
    getRepairList(){
      axios.get("/api/repairItemList")
          .then(res => {
            console.log(res.data)
            this.reList = res.data
          })
          .catch(err => {
            console.log(err)
          })
    },
    payPage(){
      let item = {}
      item.repairList = this.repairList
      item.requestList = this.requestList
      if(this.reqGita){
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