<template>
  <div>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <br><H2 style="font-weight: bold">게시판 수정</H2>
    <br>
    <button @click="list" style="float: left;">목록</button>
    <form>
      <table class="tbAdd">

        <tr>
          <th>제목</th>
          <td><input type="text" v-model="title" ref="subject" placeholder="제목"/></td>
        </tr>
        <tr>
          <th>내용</th>
          <td><textarea v-model="content" placeholder="내용을 입력하세요."/></td>
        </tr>

      </table>

    </form>
  </div>
  <div class="btnWrap">
    <button @click="list" class="btn" style="float: left;">취소</button>
    <button @click="update" type="submit" class="btnAdd btn">수정</button>

  </div>
</template>

<script>
import axios from "axios";
import store from "@/store";

export default {
  name: "Update",
  data() {
    return {
      writer_code: '',
      title: '',
      content: '',
    }
  },
  created() {
    this.writer_code = this.$route.query.writer_code;
    this.title = this.$route.query.title;
    this.content = this.$route.query.content;
  },


  methods: {
    update() {
      this.id = this.writer_code;
      const data = {
        // mid: store.getters.getLoginState.loginState,
        writer_code: this.writer_code,
        title: this.title,
        content: this.content
      }
      console.log(data);
      if (confirm("수정하시겠습니까?")) {
        axios.put('/api/update', data)
            .then((res) => {
              console.log("수정되었습니다.", res.data)
              alert("수정되었습니다.")
              this.$router.push({
                path: '/Read'
              })
            })
            .catch((error) => {
              console.log(error)
            })

      }
    },

    list(){
      this.$router.push({
        path: '/Read'
      })
    }
  }
}
</script>

<style scoped>
.tbAdd{border-top:1px solid #888;}
.tbAdd th, .tbAdd td{border-bottom:1px solid #eee; padding:5px 0;}
.tbAdd td{padding:10px 10px; box-sizing:border-box;}
.tbAdd td input{width:100%; min-height:30px; box-sizing:border-box; padding:0 10px;}
.tbAdd td textarea{width:100%; min-height:300px; padding:10px; box-sizing:border-box;}
.btnWrap{text-align:center; margin:20px 0 0 0;}
.btnWrap a{margin:0 10px;}
.btnAdd {background:#43b984; text-align:center; margin:20px 0 0 0;}
.btnDelete{background:#f00;}
</style>