<template>
  <div>
    <table class="tableBody">
      <tr>
        <th colspan="6"><h1 style="color: black">게시글관리</h1></th>
      </tr>
      <tr>
        <th>게시글번호</th>
        <th>게시글제목</th>
        <th>작성자</th>
        <th>게시판</th>
        <th>작성일</th>
        <th>삭제</th>
      </tr>
      <tr v-for="(obj, index) in writerList" :key="obj.writer_code">
        <td>{{obj.writer_code}}</td>
        <td>{{obj.title}}</td>
        <td>{{obj.mid.mnick}}</td>
        <td></td>
        <td></td>
        <td><button @click="deleteWriter(obj.writer_code)">삭제</button></td>
      </tr>
    </table>
  </div>
  <div class="searchDiv">
    <input type="text">
    <button class="searchBtn">검색</button>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "WriterManagement",
  data(){
    return{
      writerList:[]
    }
  },
  methods:{
    deleteWriter(wcode){
      axios.post("api/adminDeleteWriter",{
        wcode:wcode
      }).then((res)=>{
        if(res.data){
          alert("삭제되었습니다")
          this.$router.go()
        }else {
          alert("삭제오류발생")
        }
      }).catch((err)=>{
        console.log(err)
      })
    }
  },
  created() {
    axios.get("/api/adminAllWriter")
    .then((res)=>{
      this.writerList = res.data
    })
    .catch((err)=>{
      console.log(err)
    })
  }
}
</script>

<style scoped>
th, td{
  border: 1px solid #111111;
}
tr, td{
  text-align: center;
}
.tableBody{
  margin-left: 5%;
  margin-right: 5%;
  margin-top: 1%;
  width: 90%;
  border: 1px solid black;
  text-align: center;
}
.searchDiv{
  margin-left: 5%;
  margin-right: 5%;
  margin-top: 1%;
  width: 90%;
  text-align: center;
}
.searchBtn{
  margin-left: 1%;
  padding: 0.2%;
  text-align: center;
}
</style>
