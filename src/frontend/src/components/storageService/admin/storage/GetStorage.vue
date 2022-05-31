<template>
  <div class="renewal-box">
    <h3>보관소 리스트 페이지</h3>
    <div class="searchDiv">
      <select v-model="bigPick" @change="bigCheck(bigPick)">
        <option value="0">전국</option>
        <option v-for="big in bigRound" :value="big.areaId">{{ big.areaName }}</option>
      </select>
      <select v-model="smallPick" @change="search()" style="margin-left: 10px">
        <option value="0">전체</option>
        <option v-for="small in smallRound" :value="small.areaId">{{ small.areaName }}</option>
      </select>
      <label for="storageName" style="margin-left: 10px">보관소이름</label>
      <input type="text" id="storageName" v-model="stSearch" placeholder="보관소이름" @keyup.enter="storageSearch()"
      style="margin-left: 10px; display: inline; width: 100px">
      <button @click="storageSearch()" style="margin-left: 10px; display: inline">검색</button>
    </div>
    <div class="bottomDiv">
      <table>
        <thead>
          <tr>
            <th>번호</th>
            <th>지역</th>
            <th>보관소명</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(storage,index) in storageList" :key="index" @click="GetStorageDetail(storage.storageCode)">
            <td>{{ index+1 }}</td>
            <td style="width: 300px;">{{ storage.storageAddress }}</td>
            <td>{{ storage.storageName }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "GetStorage",
  components: {},
  mounted() {
    this.GetStorage()
    axios.get('/api/aRound')
        .then(res => {
          this.bigRound = res.data
        })
        .catch(err => {
          console.log(err)
        })
  },
  data() {
    return {
      storageList: [],
      boxList: [],
      name: '',
      bigRound: [],
      smallRound: [],
      bigPick: 0,
      smallPick: 0,
      stSearch: '',
      searchStorageList: [],
      searchList: [],
    }
  },
  methods: {
    GetStorage() {
      axios.get('/api/getStorage')
          .then((res) => {
            this.storageList = res.data
            this.searchList = this.storageList
          })
          .catch((error) => {
            console.log(error)
          })
    },
    GetStorageDetail(storageCode) {

      this.$router.push({name:'StorageRevise', params:{storageCode:storageCode}})

      axios.get('/api/storageView/' + storageCode)
          .then((res) => {
            this.boxList = res.data
            let storageName = this.boxList.storageName
            this.name = storageName
            this.GetManger(storageCode)
            this.memberIdCheck = false
          })
          .catch((err) => {
            console.log(err)
          })
    },
    bigCheck(index) {
      if (index == '0') {
        this.smallPick = 0
        this.smallRound = []
        this.GetStorage()
      } else {
        axios.get('/api/smallRound/' + index)
            .then(res => {
              this.smallRound = res.data
              this.smallPick = 0
              this.search()
            })
            .catch(err => {
              console.log(err)
            })
      }
    },
    search() {
      if (this.bigPick == "0" && this.smallPick == '0') {
        this.GetStorage()
      } else if (this.bigPick != "0" && this.smallPick == '0') {
        axios.get('/api/roundPick/' + this.bigPick + '/' + this.smallPick)
            .then(res => {
              this.storageList = res.data
              this.searchList = this.storageList
            })
            .catch(err => {
              console.log(err)
            })
      } else if (this.bigPick != "0" && this.smallPick != '0') {
        axios.get('/api/roundPick/' + this.bigPick + '/' + this.smallPick)
            .then(res => {
              this.storageList = res.data
              for (let i = 0; i < this.storageList.length; i++) {
                if(this.storageList[i].storageState == '1'){
                  this.storageList.splice(i,1)
                }
              }
              this.searchList = this.storageList
            })
            .catch(err => {
              console.log(err)
            })
      }
    },
    storageSearch() {
      this.searchStorageList = []
      if (this.stSearch != '') {
        for (let i = 0; i < this.searchList.length; i++) {
          if (this.searchList[i].storageName.includes(this.stSearch)) {
            this.searchStorageList.push(this.searchList[i])
          }
        }
        if (this.searchStorageList.length < 1){
          alert('검색하신 보관소은 없습니다')
          return
        }
        this.storageList = this.searchStorageList
      } else {
        this.search()
      }
    },
  }
}

</script>

<style scoped>
.searchDiv{
  margin-top: 10px;
  margin-bottom: 20px;
}
.bottomDiv{
  margin-top: 1%;
}
.renewal-box{
  width: 100%;
}
.renewal-box h3{
  width: 100%;
}
</style>
