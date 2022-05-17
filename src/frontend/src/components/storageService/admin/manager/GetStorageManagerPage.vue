<template>
  <h3 style="margin-top: 5%; margin-left: 5%;">보관소 매니저 관리</h3>
<!--  <div class="manager-get">-->
<!--    <div v-for="(manager, index) in storageManagerList" :key="index" style="margin-top: 3%">-->
<!--      <div class="card" style="width: 25%; font-weight: bolder; margin-left: 7%">-->
<!--        <div class="card-body">-->
<!--          보관소: {{ manager.storageCode.storageName }}-->
<!--        </div>-->
<!--      </div>-->
<!--      <div class="card" style="display: flex; width: 25%; margin-left: 7%; margin-bottom: 3%">-->
<!--        <div class="card-body">-->
<!--          매니저이름: {{ manager.mcode.mnick }}-->
<!--        </div>-->
<!--        <div class="card-body">-->
<!--          매니저아이디: {{ manager.mcode.mid }}-->
<!--        </div>-->
<!--      </div>-->
<!--    </div>-->
<!--  </div>-->
  <!-- 보관소별 관리자 리스트 -->
  <table>
    <thead>
    <tr>
      <th>
        <select v-model="storagePick">
          <option value="전체">전체</option>
          <option v-for="pick in pickList" :value="pick.code">{{pick.name}}</option>
        </select>
      </th>
      <th>보관소명</th>
      <th>관리자 이름</th>
      <th>수정/변경</th>
    </tr>
    </thead>
    <tbody v-if="storagePick == '전체'" v-for="(manager,index) in storageManagerList" :key="index">
      <tr>
        <td>{{index+1}}.</td>
        <td>{{manager.storageCode.storageName}}</td>
        <td>{{ manager.mcode.mnick }}</td>
        <td><button>수정</button><button>삭제</button></td>
      </tr>
    </tbody>
    <tbody v-else v-for="(manager,index) in managerList">
      <tr>
        <td>{{index+1}}.</td>
        <td>{{manager.storageCode.storageName}}</td>
        <td>{{ manager.mcode.mnick }}</td>
        <td><button>수정</button><button>삭제</button></td>
      </tr>
    </tbody>
  </table>
</template>

<script>
import axios from "axios";

export default {
  name: "GetStorageManagerPage",
  data() {
    return {
      storageManagerList: [],
      storagePick:'전체',
      pickList:[],
      managerList:[]
    }
  },
  mounted() {
    this.GetStorageManager()

  },
  watch:{
    storagePick:function (){
      if(this.storagePick != "전체"){
        this.pickManager(this.storagePick)
      }
    }
  },
  methods: {
    GetStorageManager() {
      axios.get('/api/getStorageManger')
          .then((res) => {
            console.log(res)
            this.storageManagerList = res.data
            for (let i = 0; i < this.storageManagerList.length; i++) {
              if(i>0 && this.storageManagerList[i-1].storageCode.storageCode != this.storageManagerList[i].storageCode.storageCode){
                this.pickList.push({
                  code:this.storageManagerList[i].storageCode.storageCode,
                  name:this.storageManagerList[i].storageCode.storageName
                })
              }else{
                this.pickList.push({
                  code:this.storageManagerList[i].storageCode.storageCode,
                  name:this.storageManagerList[i].storageCode.storageName
                })
              }
            }
          })
          .catch((error) => {
            console.log(error)
          })
    },
    pickManager(storageCode){
      axios.get('/api/stGetManager/'+storageCode)
      .then(res=>{
        console.log(res.data)
        this.managerList = res.data
      })
      .catch(err=>{
        console.log(err)
      })
    }
  }
}
</script>

<style scoped>

</style>