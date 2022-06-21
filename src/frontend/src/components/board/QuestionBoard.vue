<template>
  <div class="back">
    <div class="frame">
  <div style="padding: 2%">

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <br>
    <h2 style="font-weight: bold">문의사항</h2>
    <br>
    <div class="listWrap">
      <table class="tbList">
        <tr>
          <td style="font-size:20px; padding: 10px 20px 10px 180px">제목</td>
          <td style="font-size:20px; padding: 10px 10px 10px 10px">글쓴이</td>
          <td style="font-size:20px;">등록일</td>

        </tr>

        <tr v-for="question in list" :key="question.id" :item="question" @click="detail(question)">
          <td style="padding: 10px 20px 10px 180px" >{{question.q_title}}</td>
          <td style="padding: 10px 10px 10px 10px">{{question.mid.mname}}</td>
          <td>{{question.q_savedTime}}</td>
        </tr>
        <router-link to="{name: 'QuestionDetail', params: { questionId:question.questionId }}"></router-link>
      </table>
      <div class="btnWrap">
        <button @click="write" class="btn" style="float: right" >글쓰기</button>
      </div>

    </div>
  </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: 'QuestionBoard',
  created() {
    this.fetchData()
  },
  data() {
    return {
      list: [],
      questionin: '',
    }
  },

  methods: {
    fetchData() {
      axios.get('/api/QuestionList')
          .then((res) => {
            console.log(res.data);
            this.list = res.data;
          })
          .catch((ex) => {
            console.log("fail", ex)
          })
    },
    write() {
      this.$router.push({
        path: 'questioncreate'
      })
    },
    detail(question) {
      console.log(question.questionId);
      axios.post('/api/Question_countView', {a: question.questionId})
          .then((res) => {
            console.log("조회수 증가됨" + res.data);
            this.$router.push({
              path: `/questionDetail/${question.questionId}`
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
.back {
  position: absolute;
  background-image: url(@/assets/campwall2.webp);
  background-size: 100%;
  width: 100%;
  background-repeat: repeat-y;
  padding: auto;
}
.frame {
  border: none;
  border-radius: 50px;
  background-color: rgb(247, 246, 230);
  width: 86%;
  height: 800px;
  margin: auto;
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
  margin-left: 70%;
  margin-top: 2%;
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
</style>