<template>
  <div>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <br><H2 style="font-weight: bold">게시판 상세보기</H2>
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
            <td>{{List.mid.mid}}</td>
          </tr>
          <tr>
            <th>제목</th>
            <td>{{ List.title }}</td>
          </tr>
          <tr>
            <th>내용</th>
            <td>{{ List.content }}</td>
          </tr>

        </table>
      </form>
    </div>
  </div>


  <button @click="commentsList" class="btn" style="float: left;">댓글확인</button>

  <div class="dap_ins">
      <div style="margin-top:30px; ">
        <textarea name="commenttext" v-model="commenttext" class="reply_content" id="re_content" ></textarea>
        <button @click="comments" id="rep_bt" class="re_bt">댓글</button>
      </div>

  </div>
  <div id="foot_box"></div>

    <div class="btnWrap">
      <button @click="list" class="btn" style="float: left;">목록</button>

      <button @click="updateData(List)" class="btn" style="float: left;" v-if="ch">수정</button>
      <button @click="deleteData" class="btn" style="float: left;" v-if="ch">삭제</button>
    </div>
</template>

<script>
import axios from "axios"
import store from "@/store";

export default {
  name: 'View',
  data() {
    return {
      writer_code: '',
      title: '',
      content: '',
      List: [],
      ch:true
    }
  },
  created() {
    this.DataList();
  },

  methods: {
    check(){
      if(store.getters.getLoginState.loginState == this.List.mid.mid){
        return true
      }else{
        return false
      }
    },
    DataList() {
      this.id = this.$route.params.writer_code;
      console.log(this.id);
      console.log(this.id);
      axios.get('/api/myList/' + this.id)
          .then((res) => {
            console.log("내가 받은 데이터는", res.data)
            this.List = res.data;
            this.ch = this.check()
            console.log(this.List.writer_code)
          })
          .catch(error => {
            console.log("에러" + error)
          })

    },

    deleteData() {
      if (confirm("삭제하시겠습니까?")) {
        axios.delete('/api/deleteList/'+ this.id)
            .then((res) => {
              console.log("삭제되었습니다.", res)
              alert("게시글이 삭제되었습니다.")
              this.$router.push({
                path: '/read'
              })
            })
            .catch((err) => {
              console.log(err)
            })
      }

    },
    updateData(List) {
        console.log(List.mid.mid, List.title, List.content);
        this.$router.push({
          path: `/update/${List.writer_code}`,
          query: {
            writer_code: List.writer_code,
            title: List.title,
            content: List.content
          }
        })

    },
    list() {
      this.$router.push({
        path: '/read'
      })
    },

    comments(){
      const data= {
        mid: store.getters.getLoginState.loginState,
        commenttext: this.commenttext,
      }
      console.log(data);
      axios.post('/comment/list', data)
      .then((res) => {
        console.log("성공" + res.data)
      })
      .catch((ex) => {
        console.log("fail", ex)
      })
      // this.$router.push({
      //   path: '/Read'
      // })
    },

    commentsList() {
      axios.get('/api/commentsList' )
          .then((res) => {
            console.log(res.data);
            this.listss = res.data;
          })
          .catch((ex) =>{
            console.log("fail", ex)
          })
    }

  }
}

</script>

<style scoped>
.tbAdd{border-top:1px solid #888;}
.tbAdd th, .tbAdd td{border-bottom:1px solid #eee; padding:5px 0; }
.tbAdd td{padding:10px 10px; box-sizing:border-box; text-align:left;}
.tbAdd td.txt_cont{height:300px; vertical-align:top;}
.btnWrap{text-align:center; margin:20px 0 0 0;}
.btnWrap a{margin:0 10px;}
.btnAdd {background:#43b984}
.btnDelete{background:#f00;}

.btn {
  margin: 10px;
}
.dap_ins {
  margin-top:50px;
}
.re_bt {
  position: absolute;
  width:100px;
  height:56px;
  font-size:16px;
  margin-left: 10px;
}

#re_content {
  width:700px;
  height: 56px;
}

#foot_box {
  height: 50px;
}

</style>