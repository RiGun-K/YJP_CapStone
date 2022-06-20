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
            <td>{{ notice.title }}</td>
          </tr>
          <tr>
            <th>내용</th>
            <td>{{ notice.content }}</td>
          </tr>
          <tr>
            <th>사진</th>
            <td><img :src="'/api/product_detail_images/' + List.filename " /></td>
          </tr>
        </table>
      </form>
    </div>
  </div>

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
      title: '',
      content: '',
      notice: [],
      ch: true
    }
  },
  created() {
    this.DataList();
  },

  methods: {
    DataList() {
      this.id = this.$route.params.serviceId;
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
    }
  }
}
</script>
