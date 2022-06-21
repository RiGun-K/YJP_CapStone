<template>
  <div>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">

    <br>
    <h1 style="font-weight: bold">게시글 작성</h1>
    <button type="button" class="w-btn w-btn-blue" @click="list" style="float: left; margin-left: 95%; padding: 10px" >목록</button>

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
  <div class="btnWrap_4">
    <div class="button_5">
    <button @click="main" class="btn">취소</button>
    </div>
    <div class="button_6">
    <button type="submit" @click="write" class="btn">작성</button>
  </div>
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
      content: '',
      file: '',
    }
  },
  methods: {
    write() {
      const formData = new FormData();
      formData.append('title', this.title);
      formData.append('content', this.content);
      formData.append('mid', store.getters.getLoginState.loginState);
      formData.append('file', this.file);

      axios.post('/api/Writing', formData, {headers: {'Content-Type': 'multipart/form-data'}})
          .then((res) => {
            console.log("성공" + res)
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

  }
}
</script>


<style scoped>
@import url("https://fonts.googleapis.com/css?family=Poppins:200,300,400,500,600,700,800,900&display=swap");
.w-btn {
  position: relative;
  border: none;
  display: inline-block;
  padding: 15px 30px;
  border-radius: 15px;
  font-family: "paybooc-Light", sans-serif;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
  text-decoration: none;
  font-weight: 600;
  transition: 0.25s;
}

.w-btn-blue {
  background-color: #6aafe6;
  color: #d4dfe6;
}
.w-btn-blue-outline {
  border: 3px solid #6aafe6;
  color: #6e6e6e;
}
.w-btn:hover {
  letter-spacing: 2px;
  transform: scale(1.2);
  cursor: pointer;
}

.tbAdd{border-top:1px solid #888;}
.tbAdd th, .tbAdd td{border-bottom:1px solid #eee; padding:5px 0;}
.tbAdd td{padding:10px 10px; box-sizing:border-box;}
.tbAdd td input{width:100%; min-height:30px; box-sizing:border-box; padding:0 10px;}
.tbAdd td textarea{width:100%; min-height:300px; padding:10px; box-sizing:border-box;}
.btn{
  font-size: 10px;
  font-weight: bolder;
  text-align: center;
  margin-left: 1px;
  margin-top: 2px;
}
.button_6{text-align:center;
  margin-left: 30%;
  background-color: #58a8e5;
  color: white;
  text-decoration: none;
  display: inline-block;
  cursor: pointer;
  border-radius: 7px;
  position: absolute}
.button_6 a{margin:0 10px;}
.button_6:hover {background-color: #f7bafa}
.button_6:active {
  background-color: #b464f6;
  box-shadow: 0 5px #666;
  transform: translateY(4px);
}
.btnWrap_4{text-align:center;
  margin-left: 90%;
  margin-top: 3%;
  background-color: #58a8e5;
  color: white;
  text-decoration: none;
  display: inline-block;
  cursor: pointer;
  border-radius: 7px;
  position: absolute
}
.button_5{
  text-align:center;
  margin-left: -1%;
  margin-top: 0%;
  background-color: #58a8e5;
  color: white;
  text-decoration: none;
  display: inline-block;
  cursor: pointer;
  border-radius: 7px;
 }
.button_5 a{margin:0 10px;
}
.button_5:hover {background-color: #f7bafa}
.button_5:active {
  background-color: #b464f6;
  box-shadow: 0 5px #666;
  transform: translateY(4px);
}
/*.btnAdd {text-align:center;*/
/*  width: 17%;*/
/*  padding: 10px 0 10px;*/
/*  border: 0;*/
/*  cursor: pointer;*/
/*  color: white;*/
/*  background-color: #5f8c98;*/
/*  font-size: 20px;*/
/*  font-weight: 400;*/
/*  margin-left: 10%;*/
/*  margin-top: 20px;}*/


</style>
