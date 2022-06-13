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
      <select>
        <option>전체</option>
        <option>수리</option>
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
      searchText: '',
    }
  },
  mounted() {
    this.getBackData(this.$route.params.useBoxCode)
    this.getRepairList()
  },
  methods: {
    getList(){
      axios.get()
    },
    sch() {
      axios.get()
    },
    getBackData(useCode) {
      console.log(useCode)
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