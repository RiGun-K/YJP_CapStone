<template>
  <ProductPage></ProductPage>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
  <br>
  <h1>상품 수정 페이지 입니다.</h1>
  <br>
  <div class="products-info">
    <div class="product-input-form">
      <label for="text-select">상품분류</label>

      <form v-on:submit.prevent="formSubmit" method="post" enctype="multipart/form-data">
        <select v-model="kindid" class="form-select" aria-label="Default select example" >
          <option v-for="(option, index) in options" :key="index" :value="option">
            {{ option.text }}
          </option>
        </select>
        <div class="mt-3">선택유형 : <strong>{{ kindid.text }}</strong></div>

        <form class="was-validated" readonly>
          <div class="mb-1" readonly>
            <label for="validationTextarea" class="form-label" readonly>메뉴명</label>
            <textarea v-model="menuname" :state="menuname" id="feedback-user" class="form-control is-invalid" placeholder="메뉴명을 입력하세요." ></textarea>
          </div>
        </form>
        <br>
        <form class="was-validated">
          <div class="mb-1">
            <label for="validationTextarea" class="form-label">수량</label>
            <textarea v-model="stock" id="feedback-user" class="form-control is-invalid" placeholder="수량을 입력하세요." :state="stock" required></textarea>
          </div>
        </form>


        <br>
        <form class="was-validated">
          <div class="mb-1">
            <label for="validationTextarea" class="form-label">가격</label>
            <textarea  v-model="price" id="feedback-user" class="form-control is-invalid" placeholder="가격을 입력하세요." :state="price" required></textarea>
          </div>
        </form>


        <br>
        <form class="was-validated">
          <div class="mb-1">
            <label for="validationTextarea" class="form-label">설명</label>
            <textarea v-model="ex" class="form-control is-invalid" placeholder="설명을 입력하세요." :state="ex" id="feedback-user" required></textarea>
          </div>
        </form>

        <br>
        <div class="input-group">
          <form>
            <input type="file"
                   id="file"
                   @change="handleImage"
                   enctype="multipart/form-data"
                   aria-describedby="inputGroupFileAddon04"
                   aria-label="Upload"
                   placeholder="상품을 설명할 이미지 파일을 업로드하세요."
                   drop-placeholder="Drop file here..." >
          </form>
        </div>


      </form>
    </div>
    <br>
  </div>
  <br>
  <button variant="outline-primary" type="submit" @click="updated">수정하기</button>
  <button variant="outline-primary" type="submit" @click="cancel">삭제하기</button>

</template>

<script>
import axios from "axios";
import store from "@/store";
import ProductPage from "@/components/product/ProductPage";


export default {
  name: "MyProductEdit",
  components: { ProductPage },

  data() {
    return {
      kindid: '',
      menuname: '',
      stock: '',
      price: '',
      ex: '',
      file: '',



      options: [
        { value: null, text: '판매하실 상품의 유형을 선택해주세요'},
        { value: '1', text: '캠핑'},
        { value: '2', text: '구매'},
        { value: '3', text: '렌탈'},
      ],
    }
  },
  created() {
    this.editMode = this.$route.params.menuid ? true: false;
    console.log(this.$route.params.menuid)
    console.log(this.$route.params.menuname)
    console.log(this.$route.params.stock)
    console.log(this.$route.params.price)
    console.log(this.$route.params.ex)
    console.log(this.$route.params.mid)

    this.mid = store.getters.getLoginState.loginState;

    if(this.editMode) {
      this.menuid = this.$route.params.menuid
      this.menuname = this.$route.params.menuname;
      this.stock = this.$route.params.stock;
      this.price = this.$route.params.price;
      this.ex = this.$route.params.ex;
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
        });

        // 파일 읽기 시작
        reader.readAsDataURL(e.target.files[0]);
      }
      else {
        return false
      }

    },
    updated() {
      const updateData = new FormData();
        updateData.append('menuid', this.menuid);
        updateData.append('ex', this.ex);
        updateData.append('menuname', this.menuname);
        updateData.append('price', this.price);
        updateData.append('stock', this.stock);
        updateData.append('kindid', this.kindid.value);
        updateData.append('mid', this.mid);
        updateData.append('file', this.file);
         // updateData.append('') Savedtime 작성해주기 , 안하면 Null로 받아짐
        console.log(this.kindid.value, this.menuname, this.file, this.stock, this.price, this.ex, this.mid, this.menuid)

        axios.put('/api/myProduct_Update',  updateData, { headers: { 'Content-Type': 'multipart/form-data'}} )
          .then(res => {
            console.log("성공", res);
            console.log(this.mid);
            alert("상품이 수정되었습니다.")
            this.$router.push({
              name: "MyProductList"
            })
          })
          .catch(error => {
            console.log(error)
            alert("상품이 수정되지 않았습니다.")
          })

    },
    cancel() {
      this.$router.push({
        name: "MyProductList"
      })
    }


  }

}
</script>

<style scoped>
.content-detail-button {
  border: 1px solid black;
  margin-top: 1rem;
  padding: 2rem;
  size: 200px;
}
.button {
  cursor: pointer;
  size: 200px;
}
.content-detail-content {
  border: 1px solid black;
  margin-top: 2rem;
  padding-top: 2rem;
  min-height: 650px;
}
</style>
