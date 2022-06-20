<template>
  <div>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <br><H2 style="font-weight: bold">문의 상세</H2>
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
            <td>{{question.mid.mname}}</td>
          </tr>
          <tr>
            <th>제목</th>
            <td>{{ question.q_title }}</td>
          </tr>
          <tr>
            <th>내용</th>
            <td>{{ question.q_content }}</td>
          </tr>
                    <tr>
                      <th>사진</th>
                      <td><img :src="'/api/product_detail_images/' + question.filename " /></td>
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
import store from "@/store";

export default {
  name: 'QuestionDetail',
  data() {
    return {
      questionId: '',
      q_title: '',
      q_content: '',
      question: [],
      ch: true,

      id: ''
    }
  },
  created() {
    this.DataList();
  },
  methods: {
    check() {
      if (store.getters.getLoginState.loginState == this.List.mid.mid) {
        return true
      } else {
        return false
      }
    },
    DataList() {
      this.id = this.$route.params.questionId;
      console.log(this.id);
      console.log(this.id);
      axios.get('/api/questionList/' + this.id)
          .then((res) => {
            console.log("내가 받은 데이터는", res.data)
            this.List = res.data;
            this.ch = this.check()
            console.log(this.question.questionId)
          })
          .catch(error => {
            console.log("에러" + error)
          })

    },
    list() {
      this.$router.push({
        path: '/questionboard'
      })
    }
  }
}

</script>