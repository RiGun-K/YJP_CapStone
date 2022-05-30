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
<!--        <tr>-->
<!--          <th>사진</th>-->
<!--          <td><img :src="'/api/product_detail_images/' + List.filename " /></td>-->
<!--        </tr>-->

      </table>

    </form>

    <br>
    <form>
      <input type="file"
             id="file"
             @change="handleImage"
             enctype="multipart/form-data"
             aria-describedby="inputGroupFileAddon04"
             aria-label="Upload"
             placeholder="상품을 설명할 이미지 파일을 업로드하세요."
             multiple
             accept="image/*"
             drop-placeholder="Drop file here..." >
      <div id="image_container"/>
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
  // created() {
  //   this.fetchData()
  // },
  data() {
    return {
      writer_code: '',
      title: '',
      content: '',
      file: '',
    }
  },
  created() {
    this.id = this.$route.query.boardId;
    this.title = this.$route.query.title;
    this.content = this.$route.query.content;
    this.file = this.$route.query.filename;
    console.log(this.id)
  },


  methods: {
    update() {
      const formData = new FormData();

      formData.append('boardId', this.id);
      formData.append('title', this.title);
      formData.append('content', this.content);
      formData.append('mid', store.getters.getLoginState.loginState);
      formData.append('file', this.file);

      if (confirm("수정하시겠습니까?")) {
        axios.put('/api/update', formData, {headers: {'Content-Type': 'multipart/form-data'}})
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


    handleImage(e) {
      this.file = e.target.files[0];
      let self = this;
      if(e.target.files[0]) {
        // 파일 읽는 라이브러리
        const reader = new FileReader();

        // 파일 읽기가 완료되는 시점
        reader.addEventListener('load', function(e1){
          // 완료되는 시점!!!!!!!!!!!!!!!
          self.imgsrc = e1.target.result;
          // 지금 reader 안에서는 this 못 씀. 그래서 35줄에 this를 self로 변수지정함


          let img = document.createElement("img");
          img.setAttribute("src", e1.target.result);
          document.querySelector("div#image_container").appendChild(img);
        });


        // 파일 읽기 시작
        reader.readAsDataURL(e.target.files[0]);
      }
      else {
        return false
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
.btnAdd { width: 17%;
  padding: 10px 0 10px;
  border: 0;
  cursor: pointer;
  color: white;
  background-color: #5f8c98;
  font-size: 20px;
  font-weight: 400;
  margin-left: 10%;
  margin-top: 20px;}
</style>