<template>
  박스 이동 결제

  이동할 박스
    <table>
      <tr>
        <td>보관소 명</td>
        <td>{{data.storageName}}</td>
      </tr>
      <tr>
        <td>보관함</td>
        <td>{{data.boxName}}</td>
      </tr>
    </table>
  <hr>
  이동 장소
  <table>
    <tr>
      <td>주소</td>
      <td>{{data.address}}</td>
    </tr>
    <tr v-if="data.detailAddress != ''">
      <td>상세주소</td>
      <td>{{data.detailAddress}}</td>
    </tr>
  </table>
  <hr>
  도착 예정 일:
   <button @click="pay">결제</button>
</template>

<script>
import axios from "axios";

export default {
  name: "BoxMovePay",
  mounted() {
  },
  data(){
    return{
      data: {
        userId:this.$route.params.userId,
        storageName:this.$route.params.storageName,
        boxName:this.$route.params.boxName,
        useBoxCode:this.$route.params.useBoxCode,
        zipCode:this.$route.params.zipCode,
        address:this.$route.params.address,
        detailAddress:this.$route.params.detailAddress
      }
    }
  },
  methods:{
    pay(){
      const form={
        userId: this.data.userId,
        useBoxCode: this.data.useBoxCode,
        zipCode:this.data.zipCode,
        address:this.data.address,
        detailAddress:this.data.detailAddress
      }
      console.log(form)
      axios.post('/api/roundMoveBox',form)
      .then(res=>{
        console.log(res)
        if(res.data.result == 'ok'){
          alert('결제되었습니다')
          this.$router.push({name:"myBox"})
        }else if(res.data.result == 'umm'){
          alert('장비가 보관중이 아닙니다')
        }else{
          alert('보관중이 아니거나 보관함에 장비가 없습니다.')
        }
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