<template>
  <div>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <br>
    <h2 style="font-weight: bold">게시판 리스트</h2>
    <br>
        <div class="listWrap">
          <table class="tbList">
      <tr>
        <td style="font-size:20px; color: green; padding: 10px 20px 10px 180px">제목</td>
<!--        <td style="font-size:20px; color: green;">내용</td>-->
        <td style="font-size:20px; color: green; padding: 10px 10px 10px 10px">글쓴이</td>
        <td style="font-size:20px; color: green;">등록일</td>
        <td style="font-size:20px; color: green;">조회수</td>
      </tr>

      <tr v-for="item in list" :key="item.id" :item="item" @click="detail(item)">
        <td style="padding: 10px 20px 10px 180px" >{{item.title}}</td>
<!--        <td>{{item.content}}</td>-->
        <td style="padding: 10px 10px 10px 10px">{{item.mid.mname}}</td>
        <td>{{item.savedTime}}</td>
        <td>{{item.boardViews}}</td>
      </tr>
      <router-link to="{name: 'View', params: { writer_code:item.writer_code }}"></router-link>
          </table>
          <br>
          <div class="btnWrap">
    <button @click="write" class="btn" style="float: right" >글쓰기</button>
          </div>
          <br>
          <br>
          <br>

          <div class="searchWrap">
          <input type="text" v-model="keyword" @keyup.enter="fnSearch" /><a href="javascript:;" @click="fnSearch" class="btnSearch btn">검색</a>
          </div>

    <li>
      <div id="divPaging">
        <div>◀</div>
        <div><b>1</b></div>
        <div>2</div>
        <div>3</div>
        <div>4</div>
        <div>5</div>
        <div>▶</div>
      </div>
    </li>

  </div>
</div>

</template>

<script>
import axios from "axios";

export default {
  name: 'Read',
  created() {
    this.fetchData()
  },
  data() {
    return {
      list: [],
      item: '',
    }
  },

  methods: {
    fetchData() {
      axios.get('/api/boardList')
          .then((res) => {
            console.log(res.data);
            this.list = res.data;
          })
          .catch((ex) =>{
            console.log("fail", ex)
          })
    },
    write() {
      this.$router.push({
        path: 'create'
      })
    },
    detail(item) {
      console.log(item.boardId);
      axios.post('/api/Board_countView', {a: item.boardId})
          .then((res) => {
            console.log("조회수 증가됨" + res.data);
            this.$router.push({
              path: `/view/${item.boardId}`
            })
          })
          .catch(e => {
            console.log(e)
          })
    }
  }
}
</script>

<style>
#divPaging {
  clear:both;
  margin:0 auto;
  width:220px;
  height:50px;
}

#divPaging > div {
  float:left;
  width: 30px;
  margin:0 auto;
  text-align:center;
}

.tbList th{border-top:1px solid #888;}
.tbList th, .tbList td{border-bottom:1px solid #eee; padding:5px 0;}
.tbList td.txt_left{text-align:left;}
.btn{
  font-size: 50px;
  font-weight: bolder;
  text-align: center;
  margin-left: 1px;
  margin-top: 2px;
}
.btnWrap{text-align:center;
  margin-left: 2%;
  margin-top: 0%;
  background-color: #58a8e5;
  color: white;
  text-decoration: none;
  display: inline-block;
  cursor: pointer;
  border-radius: 7px;
  position: absolute}
.btnWrap a{margin:0 10px;}
.btnWrap:hover {background-color: #f7bafa}
.btnWrap:active {
  background-color: #b464f6;
  box-shadow: 0 5px #666;
  transform: translateY(4px);
}
.searchWrap{border:1px solid #888; border-radius:5px; text-align:center; padding:20px 0; margin-bottom:40px;}
.searchWrap input{width:60%; height:36px; border-radius:3px; padding:0 10px; border:1px solid #888;}
.searchWrap .btnSearch{display:inline-block; margin-left:10px;}

</style>