<template>
  <ProductPage></ProductPage>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
  <br>
  <h1>객실 수정 및 삭제페이지 입니다.</h1>
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
                        <input type="text" v-model="list.detailName" id="id" placeholder="객실명을 입력하세요" class="int" maxlength="20">
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
                        <input type="text" v-model="list.detailPrice" id="id" placeholder="객실가격을 입력하세요" class="int" maxlength="20">
                    </span>
          <span class="error_next_box"></span>
        </div>

        <br>
        <div>
          <h3 class="join_title">
            <label for="pswd1" >기준인원</label>
          </h3>

          <span class="box int_id">
          <input type="text" v-model="list.baseNumber" id="id" placeholder="객실 기준인원을 입력하세요" class="int" maxlength="100">
                    </span>
          <span class="error_next_box"></span>

        </div>

        <br>
        <div>
          <h3 class="join_title">
            <label for="email">최대인원</label>
          </h3>
          <span class="box int_email">
            <input type="text" v-model="list.maximumNumber" id="email" class="int" maxlength="100" placeholder="객실 최대인원을 입력하세요">
          </span>
        </div>

        <br>
        <div>
          <h3 class="join_title">
            <label for="email">객실기능</label>
          </h3>
          <span class="box int_email">
            <input type="text" v-model="list.detailFunction" id="email" class="int" maxlength="100" placeholder="객실기능을 입력하세요">
          </span>
        </div>

        <br>
        <h3 class="join_title">
          <label for="pswd2">이미지 등록</label>
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
        <div class="btn_area">
          <button type="submit" class="btn btn-outline-primary" @click="updated" id="btnJoin1">
            <span>수정하기</span>
          </button>
          <button type="submit" class="btn btn-outline-primary" @click="deleted" id="btnJoin2">
            <span>삭제하기</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import ProductPage from "@/components/product/ProductPage";
import axios from "axios";
import store from "@/store";

export default {
  name: "RoomProductDetail",
  components: { ProductPage },
  created() {
    this.DataList();
  },
  data() {
    return {
      // 방금 등록한 캠핑장이름 찾기
      campingName: '',
      list: [],

      // 등록할 DB 칼럼
      detailName: '',
      detailPrice: '',
      baseNumber: '',
      maximumNumber: '',
      detailFunction: '',
      // 객실들의 부모(캠핑장의 아이디) 값 받아오기
      campingID: store.getters.getCampingIdState.CampingIdOfRooms
    }
  },
  methods: {
    DataList() {
      this.id = this.$route.params.detailId;
      console.log("현재 객실 아이디는" + this.id);
      axios.get('http://localhost:9002/api/Room_Detail/' + this.id)
          .then((res) => {
            console.log(res.data);
            this.list = res.data;
          })
          .catch(e => {
            console.log(e)
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
    updated() {
      console.log(this.campingID);
      const formData = new FormData();

      formData.append('detailName', this.list.detailName);
      formData.append('detailPrice', this.list.detailPrice);
      formData.append('baseNumber', this.list.baseNumber);
      formData.append('maximumNumber', this.list.maximumNumber);
      formData.append('detailFunction', this.list.detailFunction);
      formData.append('campingId', this.campingID);
      formData.append('detailId', this.list.detailId);
      formData.append('file', this.file);

      console.log(this.list.detailName, this.list.detailPrice, this.list.baseNumber, this.list.maximumNumber, this.list.detailFunction, this.file, this.list.detailId, this.id);
      const baseURI = 'http://localhost:9002';

      if (confirm("캠핑장 객실을 수정하시겠습니까?")) {
        axios.put(`${baseURI}/api/Room_Update`, formData, {headers: {'Content-Type': 'multipart/form-data'}})
            .then(res => {
              console.log("성공" + res);
              alert("객실이 수정되었습니다.");
              this.$router.push({
                name: 'CampingProductList'
              });
            })
            .catch(function (error) {
              console.log("에러" + error);
              alert("객실이 등록되지않았습니다.");
            })
      }

    },
    deleted() {
      if(confirm("삭제하시겠습니까?")) {
        axios.delete('http://localhost:9002/api/Room_Delete/' + this.list.detailId)
            .then(res => {
              console.log("삭제되었습니다.", res)
              alert("메뉴가 삭제되었습니다.");
              this.$router.push({
                name: "CampingProductList"
              })
            })
            .catch(error => {
              console.log("삭제가 실패했습니다", error)
              alert("삭제가 실패했습니다.");
            })
      }
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
.btn_area {
  margin: auto;
}
#btnJoin1 {
  margin-right: 20px;
}
#btnJoin2 {
  margin-left: 4px;
}
</style>