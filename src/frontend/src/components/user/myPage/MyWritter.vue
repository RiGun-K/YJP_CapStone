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
        <td @click="goBoard(body.writer_code)">{{ body.title}}</td>
        <td>공란</td>
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
      searchWord:''
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
#content {
  margin-left: 25%;
  margin-top: 1%;
  margin-right: 25%;
  width: 50%;
  text-align: center;
}
</style>

