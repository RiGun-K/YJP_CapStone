<template>
  <div>
    <h5>장비수리</h5>
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
      <select v-model="groupPick" @change="pickGroup(groupPick)">
        <option value="0">전체</option>
        <option v-for="group in GroupList" :value="group.kindid">{{group.kindname}}</option>
      </select>
      <input type="text" v-model="searchText">
      <button @click="sch()">검색</button>
    </div>
    <div>
      <table>
        <tbody>
        <tr v-for="re in reList">
          <td>{{ re.buyName }}</td>
          <td>{{ re.buyEx }}</td>
          <td><input type="checkbox" v-model="requestList" :value="re"></td>
        </tr>
        </tbody>
      </table>
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
      myItemList: [],
      repairList: [],
      requestList: [],
      reList: [],
      GroupList: [],
      searchList:[],
      searchText: '',
      groupPick:0,
    }
  },
  mounted() {
    this.getBackData(this.$route.params.useBoxCode)
    this.pickGroup(this.groupPick)
    this.getList()
  },
  methods: {
    getList(){
      axios.get("/api/RepairGroupList")
          .then(res => {
            console.log(res.data)
            this.GroupList = res.data
          })
          .catch(err => {
            console.log(err)
          })
    },
    pickGroup(kindId){
      if(kindId == 0){
        this.getRepairList()
      }else {
        axios.get("/api/PickRepairList/"+kindId)
            .then(res => {
              this.reList = res.data
            })
            .catch(err => {
              console.log(err)
            })
      }
    },
    sch() {
        axios.get("/api/searchRepairList/"+this.searchText+"/"+this.groupPick)
            .then(res => {
              this.reList = res.data
            })
            .catch(err => {
              console.log(err)
            })
    },
    getBackData(useCode) {
      axios.get("/api/getBoxInItem/" + useCode)
          .then(res => {
            this.myItemList = res.data
          })
          .catch(err => {
            console.log(err)
          })
    },
    getRepairList() {
      axios.get("/api/repairItemList")
          .then(res => {
            console.log(res.data)
            this.reList = res.data
          })
          .catch(err => {
            console.log(err)
          })
    },
    payPage() {
      let item = {}
      item.repairList = this.repairList
      item.requestList = this.requestList
      this.$store.commit('careItemInfo', item)
      this.$router.push({name: 'repairBoxPay'})
    },
  },
}
</script>

<style scoped>

</style>