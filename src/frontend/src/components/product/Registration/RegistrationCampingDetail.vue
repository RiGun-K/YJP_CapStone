<template>
  <ProductPage></ProductPage>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
  <br>
  <h1>캠핑장 내 객실 등록 페이지 입니다.</h1>
  <br>
  <div id="wrapper">
    <div id="content">
      <form v-on:submit.prevent="formSubmit" method="post" enctype="multipart/form-data">

        <div>
          <br>
          <span class="error_next_box"></span>
          <h3 class="join_title">
            <label for="id">객실명</label>
          </h3>
          <span class="box int_id">
                        <input type="text" v-model="detailName" id="id" placeholder="객실명을 입력하세요" class="int" maxlength="20">
                    </span>
          <span class="error_next_box"></span>
        </div>

        <div>
          <br>
          <span class="error_next_box"></span>
          <h3 class="join_title">
            <label for="id">객실가격</label>
          </h3>
          <span class="box int_id">
                        <input type="text" v-model="detailPrice" id="id" placeholder="객실가격을 입력하세요" class="int" maxlength="20">
                    </span>
          <span class="error_next_box"></span>
        </div>

        <br>
        <div>
          <h3 class="join_title">
            <label for="pswd1" >기준인원</label>
          </h3>

          <span class="box int_id">
          <input type="text" v-model="baseNumber" id="id" placeholder="객실 기준인원을 입력하세요" class="int" maxlength="100">
                    </span>
          <span class="error_next_box"></span>

        </div>

        <br>
        <div>
          <h3 class="join_title">
            <label for="email">최대인원</label>
          </h3>
          <span class="box int_email">
            <input type="text" v-model="maximumNumber" id="email" class="int" maxlength="100" placeholder="객실 최대인원을 입력하세요">
          </span>
        </div>

        <br>
        <div>
          <h3 class="join_title">
            <label for="email">객실기능</label>
          </h3>
          <span class="box int_email">
            <input type="text" v-model="detailFunction" id="email" class="int" maxlength="100" placeholder="객실기능을 입력하세요">
          </span>
        </div>

        <br>
        <h3 class="join_title">
          <label for="pswd2">썸네일 이미지 등록</label>
        </h3>
        <div class="input-group">
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
        <br>

        <div v-if="stateCheck">
          <h3 class="join_title">
            <label for="pswd2">내부 이미지 등록</label>
          </h3>
          <div class="input-group">
            <form>
              <input multiple
                     type="file"
                     id="files"
                     name="profile_files"
                     ref="serveyImage"
                     @change="handleImages"
                     enctype="multipart/form-data"
                     aria-describedby="inputGroupFileAddon04"
                     aria-label="Upload"
                     placeholder="상품을 설명할 이미지 파일을 업로드하세요."
                     accept="image/*"
                     drop-placeholder="Drop file here..."
              >
              <div id="image_container"/>
            </form>
          </div>
        </div>

        <br>
        <div class="btn_area">
          <button type="submit" class="btn btn-outline-primary" @click="BuySubmit" id="btnJoin">
            <span>등록하기</span>
          </button>

        </div>
      </form>
    </div>
  </div>
</template>

<script>
import ProductPage from "@/components/product/ProductPage";
import axios from "axios";

export default {
  name: "RegistrationCampingDetail",
  components: { ProductPage },
  created() {
    this.DataList();
  },
  data() {
    return {
      // 방금 등록한 캠핑장이름 찾기
      campingName: '',
      content: [],

      // 등록할 DB 칼럼
      detailName: '',
      detailPrice: '',
      baseNumber: '',
      maximumNumber: '',
      detailFunction: '',


      roomlist: [],
      file: '',
      files: '',
      stateCheck: false,
    }
  },
  methods: {
    DataList() {
      this.campingName = this.$route.params.campingName;
      console.log(this.campingName);
      axios.get('/api/CampingDetail_BeforeSignup/' + this.campingName)
          .then(res => {
            console.log("방금 등록한 캠핑장 이름 가져오기 성공 ! ");
            console.log(res.data);
            this.content = res.data;
            console.log(this.content.campingId);
          })
          .catch(e => {
            console.log(e);
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
        this.stateCheck = true;
      }
      else {
        return false
      }

    },

    handleImages(e) {
      this.files = e.target.files
      for(let i = 0; i < e.target.files.length; i++) {
        const reader = new FileReader();
        reader.readAsDataURL(this.files[i]);
        // 파일 읽기가 완료되는 시점
        reader.addEventListener('load', function(e1){
          // 완료되는 시점!!!!!!!!!!!!!!!
          self.imgsrc = e1.target.result;
          // 지금 reader 안에서는 this 못 씀. 그래서 35줄에 this를 self로 변수지정함


          let img = document.createElement("img");
          img.setAttribute("src", e1.target.result);
          document.querySelector("div#image_container").appendChild(img);


        });
      }

    },


    BuySubmit() {
      const formData = new FormData();

      formData.append('detailName', this.detailName);
      formData.append('detailPrice', this.detailPrice);
      formData.append('baseNumber', this.baseNumber);
      formData.append('maximumNumber', this.maximumNumber);
      formData.append('detailFunction', this.detailFunction);
      formData.append('file', this.file);
      formData.append('campingId', this.content.campingId);

      console.log(this.detailName, this.detailPrice, this.baseNumber, this.maximumNumber, this.detailFunction, this.file, this.content.campingId);


        axios.post('/api/CampingDetail_Signup', formData, {headers: {'Content-Type': 'multipart/form-data'}})
            .then(res => {
              console.log("성공" + res);
              this.roomlist = res.data;
              this.detailId = this.roomlist.detailId;
              // 구매 상품 등록처리 후
              // 다중 이미지 업로드
              const formDatas = new FormData();

              for (let i = 0; i < this.files.length; i++) {
                formDatas.append('files', this.files[i]);
              }

              formDatas.append('detailId', this.detailId);

              console.log(this.detailId);


              if (confirm("객실을 등록하시겠습니까?")) {
                axios.post('/api/CampingRoom_Signup_Files/', formDatas, {headers: {'Content-Type': 'multipart/form-data'}})
                    .then(res => {
                      console.log("성공" + res);
                      alert("객실이 등록되었습니다.");
                      this.$router.push({
                        path: "ProductMain"
                      });
                    })
                    .catch(function (error) {
                      console.log("에러" + error);
                      alert("상품이 등록되지않았습니다.");
                    })

              }


            })
            .catch(function (error) {
              console.log("에러" + error);
              alert("객실이 등록되지않았습니다.");
            })


    }

  }

}
</script>

<style scoped>
h1{
  text-align: center;
}
textarea {
  width: 100%;
  height: 6.25em;
  border: none;
  resize: none;
}
body {
  margin: 0;
  height: 100%;
  background-color: #E6E6FA;
}
#wrapper {
  position: relative;
  height: 100%;
}
#content {
  position: absolute;
  left: 50%;
  transform: translate(-50%);
  width: 460px;
}
/* 입력폼 */
h3 {
  margin: 19px 0 8px;
  font-size: 14px;
  font-weight: 700;
}
.box {
  display: block;
  width: 100%;
  height: 51px;
  border: solid 1px #dadada;
  padding: 10px 14px;
  box-sizing: border-box;
  background: #fff;
  position: relative;
}
.int {
  display: block;
  position: relative;
  width: 100%;
  height: 29px;
  border: none;
  background: #fff;
  font-size: 15px;
}

.box.int_pass {
  padding-right: 40px;
}
.box.int_pass_check {
  padding-right: 40px;
}
select {
  width: 100%;
  height: 29px;
  font-size: 15px;
  background-size: 20px 8px;
  -webkit-appearance: none;
  display: inline-block;
  text-align: start;
  border: none;
  cursor: default;
}
.error_next_box {
  margin-top: 9px;
  font-size: 12px;
  color: red;
  display: none;
}

#btnJoin {
  margin: auto;
  display: block;
}
</style>