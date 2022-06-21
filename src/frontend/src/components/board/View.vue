<template>
  <div>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <br><H2 style="font-weight: bold">게시글 상세보기</H2>
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
            <th>제목</th>
            <td>{{ List.title }}</td>
          </tr>
          <tr>
            <th>작성일</th>
            <td>{{ List.savedTime}}</td>
          </tr>
          <tr>
            <th>내용</th>
            <td>{{ List.content }}</td>
          </tr>
          <tr>
            <th>사진</th>
            <td><img :src="'/api/product_detail_images/' + List.filename " class="img-thumbnail" alt="..." /></td>
          </tr>

        </table>
      </form>
    </div>
  </div>

  <div id="foot_box"></div>
  <div class="btnWrap_1">
    <button @click="list" class="btn" style="float: left;">목록</button>
  </div>
  <div class="btnWrap_2">
    <button @click="updateData(List)" class="btn" style="float: left;" v-if="ch">수정</button>
  </div>
  <div class="btnWrap_3">
    <button @click="deleteData" class="btn" style="float: left;" v-if="ch">삭제</button>
  </div>

  <br><br><br>
  <div class="dap_ins">
      <div style="margin-top:30px; ">
        <textarea name="commenttext" v-model="comment" class="reply_content" id="re_content" />
        <button @click="addComments" id="rep_bt" class="re_bt">댓글작성</button>
      </div>

    <div class="dap_ins2" v-for="item in Comment" :key="item.id" :item="item">
      <form>
        <div class="tbAdd_a">
          <div class="tbAdd_1">
          {{item.mid.mname}} | {{item.savedTime}}
          </div>
            <div class="tbAdd_2">
            <button @click="commentdelete(item)" class="btn" style="float: left;" v-if="ch">삭제</button>
            </div>
            <th></th>

            <div class="tbAdd_3">
              <textarea rows="4" cols="1" name="commenttext" v-model="item.content" class="reply_content2" id="re_content2" disabled />
            </div>
        </div>
      </form>
      <br>
    </div>


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
      Comment: [],
      comment: '',
      ch:true
    }
  },
  created() {
    this.DataList();
    this.DataComment();
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
      this.id = this.$route.params.boardId;
      console.log(this.id);
      console.log(this.id);
      axios.get('/api/myList/' + this.id)
          .then((res) => {
            console.log("내가 받은 데이터는", res.data)
            this.List = res.data;
            this.ch = this.check()
            console.log(this.List.boardId)
          })
          .catch(error => {
            console.log("에러" + error)
          })

    },
    DataComment() {
      axios.get('/api/myCommentList/' + this.id)
          .then((res) => {
            console.log("내가 받은 데이터는", res.data)
            this.Comment = res.data;
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
                path: '/Read'
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
          path: `/update/${List.boardId}`,
          query: {
            title: List.title,
            content: List.content,
            boardId: List.boardId,
          }
        })

    },
    list() {
      this.$router.push({
        path: '/read'
      })
    },

    addComments(){
      const data= {
        mid: store.getters.getLoginState.loginState,
        content: this.comment,
        parentBoard: this.id
      }
      console.log(data);
      if (confirm("작성하시겠습니까?")) {
        axios.post('/api/addComment/', data)
            .then((res) => {
              console.log("성공" + res.data)
            })
            .catch((ex) => {
              console.log("fail", ex)
            })
        this.$router.push({
          path: '/Read'
        })
      }
    },

    commentdelete(item){
      console.log(item.boardId);
      if (confirm("삭제하시겠습니까?")) {
        axios.delete('/api/commentdelete/'+ item.boardId)
            .then((res) => {
              console.log("삭제되었습니다.", res)
              alert("댓글이 삭제되었습니다.")
              this.$router.push({
                path: '/view/:boardId'
              })
            })
            .catch((err) => {
              console.log(err)
            })
      }
    }
  }
}

</script>

<style scoped>
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
.btnWrap_2{text-align:center;
  margin-left: 65%;
margin-top: 0%;
  background-color: #58a8e5;
  color: white;
  text-decoration: none;
  display: inline-block;
  cursor: pointer;
  border-radius: 7px;
  position: absolute}
.btnWrap_2 a{margin:0 10px;}
.btnWrap_2:hover {background-color: #f7bafa}
.btnWrap_2:active {
  background-color: #b464f6;
  box-shadow: 0 5px #666;
  transform: translateY(4px);
}
.btnWrap_3{text-align:center;
  margin-left: 70%;
  margin-top: 0%;
  background-color: #58a8e5;
  color: white;
  text-decoration: none;
  display: inline-block;
  cursor: pointer;
  border-radius: 7px;
  position: absolute}
.btnWrap_3 a{margin:0 10px;}
.btnWrap_3:hover {background-color: #f7bafa}
.btnWrap_3:active {
  background-color: #b464f6;
  box-shadow: 0 5px #666;
  transform: translateY(4px);
}

.btn {
  font-size: 16px;
  font-weight: bolder;
  text-align: center;
  margin-left: 1px;
  margin-top: 2px;
}
.dap_ins {
  margin-top:5%;
  margin-left: 10%;


}
.re_bt {
  position: absolute;
  width:100px;
  height:56px;
  font-size:16px;
  margin-left: 10px;
  text-align:center;
  background-color: #58a8e5;
  color: white;
  text-decoration: none;
  display: inline-block;
  cursor: pointer;
  border-radius: 7px;
}
.re_bt:hover {background-color: #e891ec
}
.re_bt:active {
  background-color: #b464f6;
  box-shadow: 0 5px #666;
  transform: translateY(4px);
}

#re_content {
  width:700px;
  height: 56px;
}

#foot_box {
  height: 50px;
}

.reply_content{
  margin-left: 10%;
  margin-top: 0%;
}
.tbAdd_2{
  margin-left: 80%;
  margin-top: -2%;
  background-color: #58a8e5;
  color: white;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  cursor: pointer;
  width: 8%;
  border-radius: 7px;
}
.tbAdd_2:hover {background-color: #f7bafa
}
.tbAdd_2:active {
  background-color: #c27dea;
  box-shadow: 0 5px #666;
  transform: translateY(4px);
}
.tbAdd_3{
  margin-top: 3%;
  margin-left: -5%;
  width: 100%;
}
.reply_content2{

}
.dap_ins2{
  width: 100%;
}

#re_content2{
  width: 90%;
  height: 10%;
  margin-left: 10%;
}
.AddWrap th{
  margin-left: 5%;

}
</style>