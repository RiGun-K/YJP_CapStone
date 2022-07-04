<template>
  <div class="container">
    <br><br>
    <h2>장비수리</h2>
    <br><br>
    <div class="myItem-box">
      <h5>수리 맡길 장비 선택</h5>
      <div class="info-box">
        <div>
          <table>
            <thead>
            <tr>
              <th>장비이름</th>
              <th>수량</th>
            </tr>
            </thead>
            <tbody>
            <tr class="item" v-for="(item,index) in myItemList" :key="index" @dblclick="clear()"
                @click="addRepairListInItem(item,index)">
              <td>{{ item.memEquipmentName }}</td>
              <td>{{ item.memEquipmentCount }}개</td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
    <div class="repair-box">
      <h5>{{ itemName }} 의 수리 사항 선택</h5>
      <div>
        <div class="search-box">
          <select v-model="groupPick" @change="pickGroup(groupPick)">
            <option value="0">전체</option>
            <option v-for="group in GroupList" :value="group.kindid">{{ group.kindname }}</option>
          </select>
          <input type="text" v-model="searchText" @keyup.enter="sch()">
          <button @click="sch()">검색</button>
        </div>
        <div class="info-box">
          <div>
            <table>
              <thead>
              <tr>
                <th>상품명</th>
                <th>설명</th>
                <th>가격</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="(re,index) in reList" @dblclick="clear()" @click="addRepairListInOption(re,index)"
                  class="repair">
                <td>{{ re.buyName }}</td>
                <td>{{ re.buyEx }}</td>
                <td>{{ re.buyPrice }}원</td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <div class="save-list">
        <br>
        <button class="payNow" @click="addRepairList()">적용</button>
      </div>
    </div>
    <br>
    <hr>
    <br>
    <div class="my-repair-box">
      <h5>수리목록</h5>
      <div class="info-box-list">
        <table class="table-st">
          <thead>
          <tr>
            <th>장비이름</th>
            <th>수리 상품</th>
            <th>가격</th>
            <th></th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(repair,index) in repairList">
            <td>{{ repair.item.memEquipmentName }}</td>
            <td>{{ repair.option.buyName }}</td>
            <td>{{ repair.option.buyPrice }}원</td>
            <td>
              <button class="payNow" @click="deleteRepairList(index)">제거</button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div class="container-bu">
      <button class="payNow-l" @click="$router.push({name:'myBox'})">취소</button>
      <button class="payNow-r" @click="payPage()">다음</button>
      <br><br><br><br>
    </div>
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
      repair: {},
      reList: [],
      GroupList: [],
      searchList: [],
      searchText: '',
      groupPick: 0,
      itemName: '_____',
      style: {},
    }
  },
  mounted() {
    this.getBackData(this.$route.params.useBoxCode)
    this.pickGroup(this.groupPick)
    this.getList()
  },
  methods: {
    addRepairListInItem(item, index) {
      this.repair.item = item
      this.itemName = item.memEquipmentName
      let a = document.getElementsByClassName("item")
      for (let i = 0; i < a.length; i++) {
        a[i].style.backgroundColor = "white"
      }
      a[index].style.backgroundColor = "#cde1e8"
    },
    addRepairListInOption(re, index) {
      this.repair.option = re
      let a = document.getElementsByClassName("repair")
      for (let i = 0; i < a.length; i++) {
        a[i].style.backgroundColor = "white"
      }
      a[index].style.backgroundColor = "#cde1e8"
    },
    addRepairList() {
      this.repairList.push(this.repair)
      this.repair = {}
      this.itemName = '_____'
      this.clear()
    },
    deleteRepairList(index) {
      this.repairList.splice(index, 1)
    },
    clear() {
      this.repair = {}
      this.itemName = '_____'
      let a = document.getElementsByClassName("item")
      for (let i = 0; i < a.length; i++) {
        a[i].style.backgroundColor = "white"
      }
      let b = document.getElementsByClassName("repair")
      for (let i = 0; i < a.length; i++) {
        b[i].style.backgroundColor = "white"
      }
    },
    getList() {
      axios.get("/api/RepairGroupList")
          .then(res => {
            this.GroupList = res.data
          })
          .catch(err => {
            console.log(err)
          })
    },
    pickGroup(kindId) {
      if (kindId == 0) {
        this.getRepairList()
      } else {
        axios.get("/api/PickRepairList/" + kindId)
            .then(res => {
              this.reList = res.data
            })
            .catch(err => {
              console.log(err)
            })
      }
    },
    sch() {
      if (this.searchText == '') {
        this.pickGroup(this.groupPick)
      } else {
        axios.get("/api/searchRepairList/" + this.searchText + "/" + this.groupPick)
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
    clickItem() {

    },
  },
}
</script>

<style scoped>
.container {
  position: center;
  height: 100%;
  width: 95%;
}

.myItem-box {
  padding-left: 10%;
  padding-bottom: 15%;
  float: left;
  width: 50%;
}

.repair-box {
  padding-right: 10%;
  padding-bottom: 15%;
  float: right;
  height: 50%;
  width: 50%;
}

.pick {
  background-color: #cde1e8;
}

th {
  text-align: center;
}

thead {
  background-color: #6e6e6e;
  color: white;
}

tr {
  border-bottom: 1px solid black;
  height: 10%;
}

td {
  text-align: center;
}

.info-box {
  width: 80%;
  border: 1px solid black;
}

.repair .item {
  width: 90%;
}

.my-repair-box {
  padding: 10%;
}

.search-box {
  padding-left: 20%;
}

.info-box-list{
  width: 90%;
}

.my-repair-box{
  margin-bottom: 3%;
}

.save-list {
  float: right;
  margin-right: 20%;
}

.payNow {
  position: center;
  text-align: center;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
}

.payNow-r {
  position: center;
  text-align: center;
  width: 20%;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
  float: right;
  margin-right: 30%;
}

.payNow-l {
  position: center;
  text-align: center;
  width: 20%;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
  float: left;
  margin-left: 20%;
}

.container-bu {
  margin-bottom: 10%;
}
</style>