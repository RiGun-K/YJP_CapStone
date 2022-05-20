<template>
  <div id="content">
    <table border="1px">
      <tr>
        <th colspan="3">
          <h1 style="color: black; text-align: center">내 게시글</h1>
        </th>
      </tr>
      <tr>
        <th>게시글번호</th>
        <th>제목</th>
        <th>작성일</th>
      </tr>
      <tr v-for="(body) in viewList" :key="body.writer_code">
        <td>{{ body.writer_code}}</td>
        <td>{{ body.title}}</td>
        <td>공란</td>
      </tr>
    </table>
    <div class="searchDiv">
      <input type="text" v-model="searchWord">
      <button class="searchBtn" @click="searchTitle">검색</button>
      <button @click="reStart" class="resetBtn">목록 초기화</button>
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
      searchWord:''
    }
  },
  methods:{
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
    reStart(){
      this.$router.go()
    }
  },
  created() {
    axios.post("/api/myWritter",{
      MID:store.getters.getLoginState.mcode
    }).then((res)=>{
      console.log(res.data)
      this.writerList = res.data
      this.viewList = this.writerList
    }).catch((err)=>{
      console.log(err)
    })
  }
}
</script>

<style scoped>
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
.resetBtn{
  float: right;
  position: relative;
  padding: 0.5%;
  background: #7ea6f6;
  color: white;
  text-align: center;
}
#content {
  position: absolute;
  left: 50%;
  transform: translate(-50%);
  width: 560px;
}
</style>

