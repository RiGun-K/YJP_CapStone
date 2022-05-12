<template>
현재 박스 위치
  <table>
    <tr>
      <td>보관소</td>
      <td>{{form.beforeStorageName}}</td>
    </tr>
    <tr>
      <td>보관함</td>
      <td>{{form.beforeBoxName}}</td>
    </tr>
  </table>
  이동할 박스 위치
  <table>
    <tr>
      <td>보관소</td>
      <td>{{form.newStorageName}}</td>
    </tr>
    <tr>
      <td>보관함</td>
      <td>{{form.newBoxName}}</td>
    </tr>
  </table>
  이동에 대한 금액 + 보관함 사이즈 변경 추가 금액
  <button @click="pay()">결제</button>
</template>

<script>
import axios from "axios";
import store from "@/store";

export default {
  name: "BoxToBoxMovePay",
  mounted() {
    this.form.beforeStorageCode = this.$route.params.beforeStorageCode
    this.form.beforeStorageName = this.$route.params.beforeStorageName
    this.form.beforeBoxName = this.$route.params.beforeBoxName
    this.form.beforeBoxCode = this.$route.params.beforeStorageBoxCode
    this.form.beforeuseBoxCode = this.$route.params.beforeuseBoxCode
    this.form.newStorageName = this.$route.params.newStorageName
    this.form.newBoxName = this.$route.params.newBoxName
    this.form.newBoxCode = this.$route.params.newStorageBoxCode
    console.log(this.form)

  },
  data(){
    return{
      form: undefined||{},
    }
  },
  methods:{
    pay(){
      console.log(this.form.beforeuseBoxCode)
      console.log(this.form.beforeBoxCode)
      console.log(this.form.newBoxCode)
      const data = {
        userId : store.getters.getLoginState.loginState,
        use: this.form.beforeuseBoxCode,
        before:this.form.beforeBoxCode,
        after: this.form.newBoxCode
      }
      console.log(data)
      axios.post('/api/boxToBoxPay',data)
      .then(res=>{
        console.log(res)
        alert('결제되었습니다')

      }).catch(err=>{
        console.log(err)
      })
    }
  }
}
</script>

<style scoped>

</style>