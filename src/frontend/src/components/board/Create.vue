<template>
  <div>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <br>
    <h1 style="font-weight: bold">게시글 작성</h1>
    <button type="button" class="btn btn-outline-primary" @click="list" style="float: left;" >목록</button>

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
    <button @click="main" class="btn" style="float: left;">취소</button>
    <button type="submit" @click="write" class="btnAdd btn">작성</button>

  </div>

</template>
<script>
import axios from "axios"
import store from "@/store";

export default {
  name: 'Create',
  data() {
    return {

      title: '',
      content: ''
    }
  },
  methods: {
    write() {
      const data = {
        mid: store.getters.getLoginState.loginState,
        title: this.title,
        content: this.content
      }
      console.log(data);
      axios.post('/api/Writing', data)
          .then((res) => {
            console.log("성공" + res.data)
          })
          .catch((ex) => {
            console.log("fail", ex)
          })
      this.$router.push({
        path: '/Read'
      })

      if(!this.title) {
        alert("제목을 입력해 주세요");
        this.$refs.title.focus();
        return;
      }
      this.$router.go(-1)
    },

    list(){
      this.$router.push({
        path: 'Read'
      })
    },
    main(){
      this.$router.push({
        path: '/'
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