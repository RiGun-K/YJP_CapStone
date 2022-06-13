<template>
  <img :src="backImg" class="backImg">
  <div id="content">
    <table border="1px">
      <tr>
        <th colspan="6">
          <h1 style="color: black; text-align: center">내 게시글</h1>
        </th>
      </tr>
      <tr>
        <th>번호</th>
        <th>제목</th>
        <th>댓글</th>
        <th>조회수</th>
        <th>작성일</th>
        <th>삭제</th>
      </tr>
      <tr v-for="(body, index) in viewList" :key="body.writer_code">
        <td style="width: 10%;">{{body.boardId}}</td>
        <td @click="goBoard(body.boardId)" style="width: 40%">{{body.title}}</td>
        <td style="width: 10%;">공란</td>
        <td style="width: 10%;">{{ body.boardViews }}</td>
        <td style="width: 10%;">{{body.savedTime}}</td>
        <td style="width: 10%;"><button @click="deleteWriter(index)">삭제</button></td>
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
import store from "@/store";

export default {
  name: "MyWritter",
  data(){
    return{
      writerList:[],
      viewList:[],
      searchWord:'',
      backImg:require("@/assets/camp1.jpg")
    }
  },
  methods:{
    searchTitle(){
        this.viewList = []
        for(var i = 0; i < this.writerList.length; i++){
          if(this.writerList[i].title.includes(this.searchWord)){
            this.viewList.push(this.writerList[i])
          }
        }
    },
    reStart(){
      this.$router.go()
    },
    goBoard(index){
      this.$router.push("/view/"+index)
    },
    deleteWriter(index){
      axios.post("api/adminDeleteWriter",{
        wcode:this.viewList[index].boardId
      }).then((res)=>{
        if(res.data){
          alert("삭제되었습니다")
          this.viewList.splice(index,1)
        }else {
          alert("삭제오류발생")
        }
      }).catch((err)=>{
        console.log(err)
      })
    }
  },
  created() {
    axios.post("/api/myWritter",{
      MID:store.getters.getLoginState.mcode
    }).then((res)=>{
      this.writerList = res.data
      this.viewList = this.writerList
    }).catch((err)=>{
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
.searchBtn{
  margin-left: 1%;
  padding: 0.5%;
}
.searchDiv{
  margin-left: 10%;
  margin-right: 5%;
  margin-top: 1%;
  width: 90%;
  text-align: center;
}
th, td{
  border: 1px solid black;
}
#content {
  margin-right: 15%;
  margin-left: 15%;
  margin-top: 2%;
  margin-bottom: 5%;
  text-align: center;
  width: 70%;
  height: 93%;
  padding: 30px;
  z-index: 2;
  position: relative;
  background: white;
}
</style>

