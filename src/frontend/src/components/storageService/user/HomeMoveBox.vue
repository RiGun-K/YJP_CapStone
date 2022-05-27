<template>
  <div>

    <div>
      <table>
        <thead>
        <tr>
          <th colspan="2">사용중인보관함</th>
        </tr>
        </thead>
        <tbody>
        <tr>
          <td>보관소</td>
          <td>{{form.storageName}}</td>
        </tr>
        <tr>
          <td>보관함</td>
          <td>{{ form.boxName }}</td>
        </tr>
        </tbody>
      </table>
    </div>

    <div>
      <table>
        <thead>
        <tr>
          <th colspan="3">보관중인장비</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(item,index) in myItemList">
          <td>{{ index + 1 }}</td>
          <td>{{ item.memEquipmentName }}</td>
          <td>{{ item.memEquipmentCount }}</td>
        </tr>
        </tbody>
      </table>
    </div>

    <div>
      <table>
        <thead>
        <tr>
          <th colspan="2">배송지</th>
        </tr>
        </thead>
        <tbody>
        <tr>
          <td>받는사람</td>
          <td>{{member.mname}}</td>
        </tr>
          <tr>
            <td>우편번호</td>
            <td>{{ member.mzadd }}</td>
          </tr>
          <tr>
            <td>주소</td>
            <td>{{ member.madd }}</td>
          </tr>
          <tr>
            <td>상세주소</td>
            <td>{{ member.mradd }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div>
      가격
    </div>
    <div>
      <hr>
      <button @click="pay()">결제</button>
    </div>
  </div>
</template>
<script>
import axios from "axios";

export default {
  name: "HomeMoveBox",
  props:{
    form:{}
  },
  data(){
    return{
      myItemList: [],
      boxName: '',
      storageName:'',
      zipCode:'',
      address:'',
      member:{},
    }
  },
  mounted() {
    this.getItem()
    this.getAddress()
  },
  methods:{
    getItem(){
      axios.get('/api/getBoxInItem/' + this.form.useBoxCode)
          .then(res => {
            this.myItemList = res.data
          })
          .catch(err => {
            console.log(err)
          })
    },
    getAddress(){
      axios.get('/api/myAddress/' + this.form.userId)
          .then(res => {
            this.member = res.data
          })
          .catch(err => {
            console.log(err)
          })
    },
    pay(){

    }
  }
}
</script>

<style scoped>

</style>