<template>
  <div>
    <br>
    <h1 style="font-weight: bold">팀 공지사항 작성</h1>
    <button type="button" class="btn btn-outline-primary" @click="list" style="float: left;" >목록</button>

    <form>
      <table class="tbAdd">

        <tr>
          <th>공지 제목</th>
          <td><input type="text" v-model="noticetitle" ref="subject" placeholder="제목"/></td>
        </tr>
        <tr>
          <th>공지 내용</th>
          <td><textarea v-model="noticecontent" placeholder="내용을 입력하세요."/></td>
        </tr>

      </table>

    </form>
  </div>
  <div class="btnWrap">
    <button @click="main" class="btn" style="float: left;">취소</button>
    <button type="submit" @click="write" class="btnAdd btn">작성</button>
    <button type="submit" @click="list" class="btnAdd btn">조회</button>
  </div>
</template>
<script>
import store from "@/store";
import axios from "axios";

export default {
  name: 'TBCreate',
  data() {
    return {

      noticetitle: '',
      noticecontent: '',
      teamMaster: 41
    }
  },
  methods: {
    write() {
      // 1. DB에 저장할  data를 담는다.
      const data = {
        teamMaster: this.teamMaster,
        noticetitle: this.noticetitle,
        noticecontent: this.noticecontent
      }
      console.log(data);

      // 2. 담은 data를 post 방식으로 '/api/teamwriting 주소로 보낸다
      axios.post('/api/teamwriting', data)
          .then((res) => {
            console.log("성공" + res.data)
          })
          .catch((ex) => {
            console.log("실패", ex)
          })
      this.$router.push({
        path: '/teamboard'
      })
    },
    list() {
      this.$router.push({
        path: 'TeamBoard'
      })
    },
    main(){
      this.$router.push({
        path: '/TeamBoard'
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