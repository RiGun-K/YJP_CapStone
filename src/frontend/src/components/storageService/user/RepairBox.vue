<template>
  장비수리
  현재 보관중인 장비
  보관소 : {{ storageName }}
  보관함 : {{ boxName }}
  <div>
    장비리스트 수리 맡길 장비 선택
    <div>
      <ul v-for="(item,index) in myItemList" :key="index">
        <li>
          <table style="width: 300px" border="1">
            <tbody>
              <tr>
                <td>{{ item.memEquipmentName }}</td>
                <td>{{ item.memEquipmentCount }}</td>
                <td><input type="checkbox" :value="item.memEquipmentCode" v-model="repairList"></td>
              </tr>
            </tbody>
          </table>
        </li>
      </ul>
    </div>
    {{ repairList }}
  </div>
  <div>
    수리 맡길 장비 기본 사항 선택+ 기타(직접입력)
  </div>
  <div>
    다음으로
    <button>다음</button>
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
    }
  },
  mounted() {
    this.getBackData(this.$route.params.useBoxCode)
    this.storageName = this.$route.params.storageName
    this.boxName = this.$route.params.boxName
  },
  methods: {
    getBackData(useBoxCode) {
      console.log(useBoxCode)
      axios.get("/api/getBoxInItem/" + useBoxCode)
          .then(res => {
            console.log(res.data)
            this.myItemList = res.data
          })
          .catch(err => {
            console.log(err)
          })
    }
  },
}
</script>

<style scoped>

</style>