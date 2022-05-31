<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<template>

  <div>
    <br>
    <h2> 리뷰 작성 페이지 </h2>
    <br>
    <div class="row">
      <div class="col-md-6">
        <form v-on:submit.prevent="formSubmit" method="post" enctype="multipart/form-data">
          <div class="form-group">
            <th for="subject">제목</th>
            <input type="text" class="form-control" v-model="this.$route.query.title" name ="subject" id="subject" placeholder="제목을 입력해 주세요.">
          </div>
          <br>
          <div class="form-group">
            <th for="name">작성자</th>
            <input type="text" class="form-control" v-model="this.$route.query.mname" id="name" placeholder="이름을 입력해 주세요.">
            <br>
            <th for="content">내용:</th>
            <textarea class="form-control" rows="10" v-model="this.$route.query.content" name="content" id="content"></textarea>
            <!--      //글 내용이 많으므로 <textarea>태그를 쓴다 rows는 textarea의 높이 조절-->
            <img :src="'/api/product_detail_images/' + this.$route.query.filename" alt="...">
          </div>

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

          <div class="center-block" >
            <input type="submit" class ="button_1" @click="update" value="수정하기">
            <input type="reset" class ="button_2" @click="cancle" value="취소">
            <br>
            <input type="button" value="리뷰목록" onclick="history.back(1)">
          </div>

        </form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import store from "@/store";
export default {

  name: "InfoterDetailBoardUpdate",
  created() {
    this.fetchData()
  },
  data() {
    return {
      list: [],
      item: '',
      user: store.getters.getLoginState.loginState,
      title: '',
      content: '',
      file: '',


    }
  },
  methods: {
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
    fetchData() {
      console.log("받아온 값")
      console.log(this.$route.query.title)
      console.log(this.$route.query.content)
      console.log(this.$route.query.filename)
    },

    update(){
      this.boardId = this.$route.params.boardCampingCode;
      this.id = store.getters.getLoginState.loginState;
      const formData = new FormData();

      formData.append('boardCampingCode', this.boardId);
      formData.append('campingTitle', this.$route.query.title);
      formData.append('campingContent', this.$route.query.content);
      formData.append('mcode', this.id);
      formData.append('campingId', this.$route.query.campingId);
      formData.append('boardMenuCode', '1');
      formData.append('file', this.file);
      formData.append('recommend', this.$route.query.recommend);


      console.log(this.boardId);
      console.log(this.$route.query.title);
      console.log(this.$route.query.content);
      console.log(this.id);
      console.log(this.$route.query.campingId);


      if (confirm("수정하시겠습니까?")) {
        axios.put('/api/CampingBoardupdate', formData, {headers: {'Content-Type': 'multipart/form-data'}})
        .then((res) => {
          console.log("수정되었습니다.", res.data)
          alert("수정되었습니다.")
          this.$router.push({
            path: `/infoter/infoterList/${this.id}`
          })
        })
        .catch((error) => {
          console.log(error)
        })

      }
    },

    cancle(){
      this.$router.push({
        path: '/infoter'
      })
    }

  }
}
</script>

<style scoped>
.row{
  position: center;
  margin-left: 5%;
}
.form-group{
  font-size: large;
}
.button_1{
  width: 17%;
  padding: 10px 0 10px;
  border: 0;
  cursor: pointer;
  color: white;
  background-color: #5f8c98;
  font-size: 20px;
  font-weight: 400;
  margin-left: 10%;
  margin-top: 20px;
}
.button_2{
  width: 17%;
  padding: 10px 0 10px;
  border: 0;
  cursor: pointer;
  color: white;
  background-color: #5f8c98;
  font-size: 20px;
  font-weight: 400;
  margin-left: 5%;
  margin-top: 20px;
}

img {
  width: 50%;
}

</style>