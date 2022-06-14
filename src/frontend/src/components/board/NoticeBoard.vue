<template>
  <div>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <br>
    <h2 style="font-weight: bold">공지사항</h2>
    <br>
        <div class="Servicelist">
          <table class="slist">
            <tr>
              <td style="font-size:20px; color: green; padding: 10px 20px 10px 180px">제목</td>
              <td style="font-size:20px; color: green; padding: 10px 10px 10px 10px">글쓴이</td>
              <td style="font-size:20px; color: green;">등록일</td>
              <td style="font-size:20px; color: green;">조회수</td>
            </tr>

            <tr v-for="(notice, index) in list" :key="index.id" :item="notice" @click="detail(notice)">
              <td style="padding: 10px 20px 10px 180px" >{{notice.title}}</td>
              <!--        <td>{{item.content}}</td>-->
              <td style="padding: 10px 10px 10px 10px">{{notice.mcode.mname}}</td>
              <td>{{notice.savedTime}}</td>
              <td>{{notice.boardViews}}</td>
            </tr>
            <router-link to="{name: 'Noticedetail', params: { writer_code:item.writer_code }}"></router-link>
          </table>
        </div>
  </div>
</template>

<script>
import axios from "axios";

export  default {
  name: 'Noticeboard',
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
      axios.get('/api/NoticeList')
          .then((res) => {
            console.log(res.data);
            this.list = res.data;
          })
          .catch((ex) => {
            console.log("fail", ex)
          })
    },
    detail(notice) {
      console.log(notice.serviceId);
      axios.post('/api/Notice_countView', {a: notice.serviceId})
          .then((res) => {
            console.log("조회수 증가됨" + res.data);
            this.$router.push({
              path: `/noticedetail/${notice.serviceId}`
            })
          })
          .catch(e => {
            console.log(e)
          })
    }
  }
}
</script>