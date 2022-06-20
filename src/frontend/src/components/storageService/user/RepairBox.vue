<template>
  <div>
    <h5>장비수리</h5>
  </div>
  <div>
    <h5>장비리스트 수리 맡길 장비 선택</h5>
    <div>
      <div v-for="(item,index) in myItemList" :key="index" @click="addRepairListInItem(item)">
        <table style="width: 300px" border="1">
          <tbody>
          <tr>
            <td>{{ item.memEquipmentName }}</td>
            <td>{{ item.memEquipmentCount }}</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
  <div>
    <h5>{{itemName}}의 수리 사항 선택</h5>
    <div>
      <div>
        <select v-model="groupPick" @change="pickGroup(groupPick)">
          <option value="0">전체</option>
          <option v-for="group in GroupList" :value="group.kindid">{{group.kindname}}</option>
        </select>
        <input type="text" v-model="searchText" @keyup.enter="sch()">
        <button @click="sch()">검색</button>
      </div>
      <div>
        <div v-for="re in reList" @click="addRepairListInOption(re)">
          <table style="width: 300px" border="1" >
            <tbody>
            <tr >
              <td>{{ re.buyName }}</td>
              <td>{{ re.buyEx }}</td>
              <td>{{re.buyPrice}}원</td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
    <div>
      <button @click="addRepairList()">적용</button>
    </div>
  </div>
  <div>
    <div v-for="(repair,index) in repairList">
      <table style="width: 300px" border="1">
        <tbody>
        <tr>
          <td>{{ repair.item.memEquipmentName }}</td>
          <td>{{ repair.option.buyName }}</td>
          <td><button @click="deleteRepairList(index)">제거</button></td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
  <div>
    <div>

    </div>
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
      repair:{},
      reList: [],
      GroupList: [],
      searchList:[],
      searchText: '',
      groupPick:0,
      itemName:'',
    }
  },
  mounted() {
    this.getBackData(this.$route.params.useBoxCode)
    this.pickGroup(this.groupPick)
    this.getList()
  },
  methods: {
    addRepairListInItem(item){
      this.repair.item = item
      this.itemName = item.memEquipmentName
    },
    addRepairListInOption(re){
      this.repair.option = re
    },
    addRepairList(){
      this.repairList.push(this.repair)
      this.repair = {}
      this.itemName = ''
    },
    deleteRepairList(index){
      this.repairList.splice(index,1)

    },
    getList(){
      axios.get("/api/RepairGroupList")
          .then(res => {
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
      if(this.searchText == ''){
        this.pickGroup(this.groupPick)
      }else{
        axios.get("/api/searchRepairList/"+this.searchText+"/"+this.groupPick)
            .then(res => {
              this.reList = res.data
            })
            .catch(err => {
              console.log(err)
            })
      }
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
            this.reList = res.data
          })
          .catch(err => {
            console.log(err)
          })
    },
    payPage() {
      console.log(this.repairList)
      this.$store.commit('careItemInfo', this.repairList)
      this.$router.push({name: 'repairBoxPay'})
    },
  },
}
</script>

<style scoped>

</style>