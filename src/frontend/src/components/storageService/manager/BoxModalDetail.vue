<template>

  <div v-if="chk">
    <table>
      <tbody>
      <tr>
        <td>보관함</td>
        <td>{{ box.boxName }}</td>
      </tr>
      <tr>
        <td>상태</td>
        <td v-if="box.boxState == 0">빈 상태</td>
        <td v-else-if="box.boxState == 1">결제완료<button @click="updateState">보관완료</button></td>
        <td v-else>사용중</td>
      </tr>
      <tr>
        <td>사용중인 사용자</td>
        <td>{{ box.userName }}</td>
      </tr>
      <tr>
        <td>사용자 추가정보</td>
        <td v-if="box.useStorageState == 0">구독시작</td>
        <td v-else-if="box.useStorageState == 1">구독종료</td>
        <td v-else-if="box.useStorageState == 2">구독 중</td>
      </tr>
      </tbody>
    </table>
    <hr>
    장비 이동 / 장비 수리 신청 내역
    <table>
      <tr>
        <td>장비 이동</td>
        <td v-if="box.useStorageState == 4">장비 이동 신청</td>
        <td v-else>없음</td>
      </tr>
      <tr>
        <td>장비 수리</td>
        <td v-if="box.useStorageState == 7">장비 수리 신청</td>
        <td v-else>없음</td>
      </tr>
    </table>
  </div>
  <div v-else>
    <table>
      <tbody>
      <tr>
        <td>보관함</td>
        <td>{{ box.storageBoxName }}</td>
      </tr>
      <tr>
        <td>상태</td>
        <td>사용안함</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "BoxMadalDetail",
  props:{
    boxCode:null
  },
  mounted() {
    this.getd()

  },
  methods:{
    getd(){
      this.box={}
      axios.get('/api/getBox/'+ this.boxCode)
          .then(res=>{
            console.log('res.data')
            console.log(res.data)
            if(res.data[0].length > 2){
              const data={
                userName:'',
                boxCode:'',
                boxName:'',
                boxState:'',
                storageCode:'',
                useStorageCode:'',
                useStorageState:''
              }
              const datak = res.data[0]
              data.userName = datak[0]
              data.boxCode = datak[1]
              data.boxName = datak[2]
              data.boxState = datak[4]
              data.boxType = datak[5]
              data.storageCode = datak[6]
              data.useStorageCode = datak[7]
              data.useStorageState = datak[8]
              this.box = data
              this.chk = true
            }else{
              this.box = res.data[0]
              this.chk = false
            }
            console.log(this.box)
          })
          .catch(err=>{
            console.log(err)
          })
    },
    updateState(){
      axios.put('/api/boxStateUpdate/'+this.box.useStorageCode)
      .then(res=>{
        console.log(res)
        alert("사용자 보관 확인 되었습니다")
        this.getd()
      })
      .catch(err=>{
        console.log(err)
      })
    }
  },
  watch: {
    boxCode(){
      this.getd()

    }
  },
  data(){
    return{
      chk:false,
      box:{}
    }
  }
}
</script>

<style scoped>

</style>