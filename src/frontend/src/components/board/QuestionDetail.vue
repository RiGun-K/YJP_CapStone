<template>
  <div class="back">
    <div class="frame">
  <div style="padding: 2%">
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
            <td>{{List.mid.mname}}</td>
          </tr>
          <tr>
            <th>문의 제목</th>
            <td>{{ List.q_title }}</td>
          </tr>
          <tr>
            <th>문의 내용</th>
            <td>{{ List.q_content }}</td>
          </tr>
                    <tr>
                      <th>사진</th>
                      <td><img :src="'/api/product_detail_images/' + List.filename " class="img-thumbnail" alt="..."/></td>
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
    </div>
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

      id: '',
      List: [],
    }
  },
  created() {
    console.log("start")
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
            // console.log(this.question.questionId)
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

  margin-left: 78%;
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