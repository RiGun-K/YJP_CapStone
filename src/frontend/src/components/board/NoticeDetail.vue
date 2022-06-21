<template>
  <div>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <br><H2 style="font-weight: bold">상세보기</H2>
    <br>
    <div class="AddWrap">
      <form>
        <table class="tbAdd">
          <colgroup>
            <col width="15%" />
            <col width="*" />
          </colgroup>
          <tr>
            <th>글쓴이</th>
            <td>{{notice.mcode.mname}}</td>
          </tr>
          <tr>
            <th>제목</th>
            <td>{{ notice.s_title }}</td>
          </tr>
          <tr>
            <th>내용</th>
            <td><h5 v-html="this.notice.s_content"></h5></td>
          </tr>

        </table>
      </form>
    </div>
  </div>
<br>
  <br>
  <div id="foot_box"></div>
  <div class="btnWrap_1">
    <button @click="list" class="btn" style="float: left;">목록</button>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: 'NoticeDetail',
  data() {
    return{
      serviceId: '',
      s_title: '',
      s_content: '',
      notice: [],
      ch: true,

      id: ''
    }
  },
  created() {
    this.DataList();
  },

  methods: {
    DataList() {
      this.id = this.$route.query.serviceId;
      console.log(this.id);
      console.log(this.id);
      axios.get('/api/noticemyList/' + this.id)
          .then((res) => {
            console.log("내가 받은 데이터는", res.data)
            this.notice = res.data;
            this.ch = this.check()
            console.log(this.notice.serviceId)
          })
          .catch(error => {
            console.log("에러" + error)
          })
    },
    list(){
      this.$router.push({
        path: '/noticeboard'
      })
    },
    }
}
</script>

<style>
.tbAdd{
  border-top:1px solid #888;
}
.tbAdd_a{
  border:1px solid;
  padding:1% 0 3%;
  box-sizing: border-box;
  width: 50%;
  margin-left: 15%;
  margin-top: 5%;

}
.tbAdd_1{margin-left: 5%; margin-top: 5%; font-size: 20px; font-weight: bolder;}
.tbAdd th, .tbAdd td{border-bottom:1px solid #eee; padding:5px 0; }
.btnWrap_1{text-align:center;
  margin-left: 60%;
  background-color: #58a8e5;
  color: white;
  text-decoration: none;
  display: inline-block;
  cursor: pointer;
  border-radius: 7px;
  position: absolute}
.btnWrap_1 a{margin:0 10px;}
.btnWrap_1:hover {background-color: #f7bafa}
.btnWrap_1:active {
  background-color: #b464f6;
  box-shadow: 0 5px #666;
  transform: translateY(4px);
}
</style>