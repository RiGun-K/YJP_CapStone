<template>
  <img :src="backImg" class="backImg">
  <div class="bodyDiv">
    <table class="tableBody">
      <tr>
        <th colspan="6" class="tableHead">
          <h1 style="color: black">게시글관리</h1>
        </th>
      </tr>
      <tr>
        <th>게시글번호</th>
        <th>게시글제목</th>
        <th>작성자</th>
        <th>게시판</th>
        <th>작성일</th>
        <th>삭제</th>
      </tr>
      <tr v-for="(obj, index) in viewList" :key="obj.writer_code">
        <td>{{obj.boardId}}</td>
        <td  @click="toWriter(obj.boardId)">{{obj.title}}</td>
        <td>{{obj.mcode.mnick}}</td>
        <td>{{}}</td>
        <td>{{obj.savedTime}}</td>
        <td><button @click="deleteWriter(index)">삭제</button></td>
      </tr>
    </table>
    <div class="searchDiv">
      <input type="text" v-model="searchWord">
      <button class="searchBtn" @click="searchTitle">검색</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "WriterManagement",
  data(){
    return{
      writerList:[],
      viewList:[],
      searchWord:'',
      backImg:require("@/assets/camp1.jpg")
    }
  },
  methods:{
    deleteWriter(index){
      axios.post("api/adminDeleteWriter",{
        wcode:this.viewList[index].boardId
      }).then((res)=>{
        if(res.data){
          alert("삭제되었습니다")
          // this.$router.go()
          this.viewList.splice(index,1)
        }else {
          alert("삭제오류발생")
        }
      }).catch((err)=>{
        console.log(err)
      })
    },
    searchTitle(){
      if(this.searchWord.length <= 0){
        alert("검색어를 입력해주세요")
      }else{
        this.viewList = []
        for(var i = 0; i < this.writerList.length; i++){
          if(this.writerList[i].title.includes(this.searchWord)){
            this.viewList.push(this.writerList[i])
          }
        }
      }
    },
    toWriter(index){
      this.$router.push("/view/"+index)
    }
  },
  created() {
    axios.get("/api/adminAllWriter")
    .then((res)=>{
      this.writerList = res.data
      this.viewList = this.writerList
    })
    .catch((err)=>{
      console.log(err)
    })
  }
}
</script>

<style scoped>
.backImg{
  margin-top: 1px;
  width: 100%;
  height: 100%;
  position: absolute;
  z-index: 1;
}
.bodyDiv{
  opacity: 0.95;
  padding: 30px;
  margin-left: 5%;
  margin-right: 5%;
  margin-top: 2%;
  margin-bottom: 5%;
  background: white;
  width: 90%;
  height: 93%;
  z-index: 2;
  position: relative;
}
th, td{
  border: 1px solid #111111;
}
tr, td{
  text-align: center;
}
.tableHead{
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
