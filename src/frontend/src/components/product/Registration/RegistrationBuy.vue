<template>
  <ProductPage></ProductPage>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
  <br>
  <h1>구매 등록 페이지 입니다.</h1>
  <br>
  <div id="wrapper">
    <div id="content">
      <form v-on:submit.prevent="formSubmit" method="post" enctype="multipart/form-data">

        <div>
          <h3 class="join_title">
            <label for="id">상품분류</label>
          </h3>
          <select v-model="kindid" class="form-select form-select-sm" aria-label=".form-select-sm example">
            <option selected disabled>등록하실 상품을 분류해주세요.</option>
            <optgroup label="캠핑용품">
              <option value="3">캠핑패키지</option>
              <option value="4">텐트</option>
              <option value="5">타프</option>
              <option value="6">테이블</option>
              <option value="7">체어</option>
              <option value="8">침낭</option>
              <option value="9">매트</option>
              <option value="10">식기</option>
              <option value="11">버너</option>
              <option value="12">화로</option>
              <option value="13">캠핑소품</option>
              <option value="14">등산용품</option>
            </optgroup>
            <optgroup label="식품">
              <option value="15">식품패키지</option>
              <option value="16">육류</option>
              <option value="17">과일</option>
              <option value="18">음료</option>
            </optgroup>
            <optgroup label="수리">
              <option value="21">일광건조</option>
              <option value="22">세탁</option>
              <option value="23">폴대교체</option>
              <option value="24">텐트 천 수선</option>
              <option value="25">맞춤 수리</option>
            </optgroup>
          </select>
          <br>
          <br>
          <span class="error_next_box"></span>
          <h3 class="join_title">
            <label for="id">상품명</label>
          </h3>
          <span class="box int_id">
                        <input type="text" v-model="buyName" id="id" placeholder="상품명을 입력하세요" class="int" maxlength="20">
                    </span>
          <span class="error_next_box"></span>
        </div>

        <br>
        <div>
          <h3 class="join_title">
            <label for="pswd1" >상품수량</label>
          </h3>

          <span class="box int_id">
          <input type="text" v-model="buyStock" id="id" placeholder="상품 수량을 입력하세요" class="int" maxlength="100">
                    </span>
          <span class="error_next_box"></span>

        </div>

        <br>
        <div>
          <h3 class="join_title">
            <label for="email">상품가격</label>
          </h3>
          <span class="box int_email">
            <input type="text" v-model="buyPrice" id="email" class="int" maxlength="100" placeholder="상품 가격을 입력하세요">
          </span>
        </div>

        <br>
        <div>
          <h3 class="join_title">
            <label for="email">상품설명</label>
          </h3>
          <span class="box int_email">
            <input type="text" v-model="buyEx" id="email" class="int" maxlength="100" placeholder="상품 설명을 입력하세요">
          </span>
        </div>

        <br>
        <h3 class="join_title">
          <label for="pswd2">썸네일 이미지 등록</label>
        </h3>
        <div class="input-group">
          <form>
            <input multiple
                   type="file"
                   id="file"
                   name="profile_files"
                   ref="serveyImage"
                   @change="handleImage"
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
import axios from 'axios'
import store from "@/store";


export default {
  name: "RegistrationBuy",
  components: { ProductPage },
  created() {

  },
  data() {
    return {
      kindid: '',
      buyName: '',
      buyStock: '',
      buyPrice: '',
      buyEx: '',

      file: '',
      files: '',

      mid: store.getters.getLoginState.loginState,

      id: '',
      myContent: [],
      clist: [],
      stateCheck: false,
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

    BuySubmit: function () {
      console.log(this.file.length);  // undefined
      console.log(this.files.length);

      const formData = new FormData();
      formData.append('kindid', this.kindid);
      formData.append('buyName', this.buyName);
      formData.append('buyPrice', this.buyPrice);
      formData.append('buyStock', this.buyStock);
      formData.append('buyEx', this.buyEx);
      formData.append('mid', this.mid);
      formData.append('file', this.file);

      axios.post('/api/Buy_Signup', formData, {headers: {'Content-Type': 'multipart/form-data'}})
          .then(res => {
            console.log("성공" + res);
            console.log(res.data);
            this.clist = res.data;
            this.buyId = this.clist.buyId;

            // 구매 상품 등록처리 후
            // 다중 이미지 업로드
            const formDatas = new FormData();

            for (let i = 0; i < this.files.length; i++) {
              formDatas.append('files', this.files[i]);
            }

            formDatas.append('buyId', this.buyId);

            console.log(this.buyId);


            if (confirm("상품을 등록하시겠습니까?")) {
              axios.post('/api/Buy_Signup_Files/', formDatas, {headers: {'Content-Type': 'multipart/form-data'}})
                  .then(res => {
                    console.log("성공" + res);
                    alert("상품이 등록되었습니다.");
                    this.$router.push({
                      name: "ProductMain"
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
          })



    },
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