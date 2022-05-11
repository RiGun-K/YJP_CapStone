<template>
  <button @click="this.$router.push({name:'myBox'})" class="renewal-box-back-btn">뒤로가기</button>
  <div class="renewal-box">
    <h3> 연장 </h3>
    <!--    현재 사용 기간-->
    <!--    시작:{{form.startTime}}-->
    <!--    종료:{{form.endTime}}-->
    <!--    <br>-->
    <!--    연장 기간-->
    <!--    시작:{{startTime}}-->
    <!--    종료:{{endTime}}-->
    <div class="card" style="width: 25%; font-weight: bolder; margin-left: 7%">
      <div class="card-body">
        현재 사용 기간
      </div>
    </div>
    <div class="card" style="display: flex; width: 25%; margin-left: 7%; margin-bottom: 3%">
      <div class="card-body">
        시작: {{form.startTime}}
      </div>
      <div class="card-body">
        종료: {{form.endTime}}
      </div>
    </div>

    <div class="card" style="width: 25%; font-weight: bolder; margin-left: 7%">
      <div class="card-body">
        연장 기간
      </div>
    </div>
    <div class="card" style="display: flex; width: 25%; margin-left: 7%">
      <div class="card-body">
        시작: {{startTime}}
      </div>
      <div class="card-body">
        종료: {{endTime}}
      </div>
    </div>

    <div>
      <button @click="renewalBoxPay" class="renewal-box-pay">연장결제</button>
      <button @click="closeBox" class="renewal-box-cancel">연장 해지</button>
    </div>
  </div>
</template>

<script>

import axios from "axios";

export default {
  name: "RenewalBox",
  components: {},
  mounted() {

    this.form.userId = this.$route.params.userId
    this.form.storageName = this.$route.params.storageName
    this.form.boxName = this.$route.params.boxName
    this.form.startTime = this.$route.params.startTime
    this.form.endTime = this.$route.params.endTime
    this.form.start = this.$route.params.start
    this.form.end = this.$route.params.end
    this.form.useBoxCode = this.$route.params.useBoxCode
    //  연장 할 기간 보여주시
    const arrDayStr = ['일','월','화','수','목','금','토']
    const newStart = new Date(this.form.end)
    this.range.start =  newStart.setDate((newStart.getDate()+1))
    this.startTime = newStart.getFullYear()+'년'+(newStart.getMonth()+1)+'월'+ newStart.getDate()+'일 ('+ arrDayStr[ newStart.getDay()] +')'
    this.range.end = newStart.setDate((newStart.getDate()+29))
    this.endTime = newStart.getFullYear()+'년'+(newStart.getMonth()+1)+'월'+newStart.getDate()+'일 ('+ arrDayStr[ newStart.getDay()] +')'

  },
  data() {
    return {
      form: {
        userId: '',    // 사용자 아이디
        storageName:'',    // 보관소 이름
        boxName: '',   // 보관함 이름
        startTime: '',  // 시작년월일
        endTime: '',   // 종료년월일
        start:'',       // 시작date
        end:''          // 종료date
      },
      range :{
        start:'',       // 시작date
        end:''          // 종료date
      },
      startTime: '',  // 시작년월일
      endTime: '',   // 종료년월일
    }
  },
  methods: {
    renewalBoxPay(){
      const startTime = new Date(this.range.start)
      const endTime = new Date(this.range.end)
      const data = {
        userId:this.form.userId,
        storageName: this.form.storageName,
        boxName:this.form.boxName,
        startTime:startTime,
        endTime:endTime,
        useBoxCode : this.useBoxCode
      }
      axios.post('/api/renewalPay', data)
          .then(res=>{
            console.log(res.data.result)
            if(res.data.result == 'ok'){
              alert('다음달 사용 연장 되었습니다 ')
              this.$router.push({name:"myBox"})
            }else {
              alert('error')
            }
          })
          .catch(err=>{
            console.log(err)
          })
    },
    closeBox(){
      const data={
        storageName: this.form.storageName,
        boxName:this.form.boxName,
      }
      console.log(this.form.boxName)
      axios.post('/api/closeBox',data)
          .then(res=>{
            console.log(res.data.result)
            if(res.data.result == 'ok'){
              alert('다음달 사용 해지 되었습니다 ')
              this.$router.push({name:"myBox"})
            }else {
              alert('error')
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
.renewal-box-back-btn{
  margin-left: 2%;
  margin-top: 1%;
  text-align: center;
  width: 7%;
  padding: 0.5%;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
}
.renewal-box-back-btn:hover{
  color: white;
  background-color: #b2e2fd;
}
.renewal-box h3{
  margin-top: 3%;
  margin-left: 7%;
  margin-bottom: 3%;
}
.renewal-box-pay, .renewal-box-cancel{
  margin-right: -5%;
  margin-left: 10%;
  margin-top: 3%;
  text-align: center;
  width: 7%;
  padding: 0.5%;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
}
.renewal-box-pay:hover, .renewal-box-cancel:hover{
  color: white;
  background-color: #b2e2fd;
}
</style>