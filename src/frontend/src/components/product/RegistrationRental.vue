<template>
  <ProductPage></ProductPage>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
  <br>
  <h1>상품 등록 페이지 입니다.</h1>
  <br>
  <div class="products-info">
    <div class="product-input-form">
      <label for="text-select">상품분류</label>

      <form v-on:submit.prevent="formSubmit" method="post" enctype="multipart/form-data">
        <select v-model="kindid" class="form-select" aria-label="Default select example">
          <option v-for="(option, index) in options" :key="index" :value="option">
            {{option.text}}
          </option>
        </select>
        <div class="mt-3">선택유형 : <strong>{{ kindid.text }}</strong></div>

        <form class="was-validated">
          <div class="mb-1">
            <label for="validationTextarea" class="form-label">메뉴명</label>
            <textarea v-model="menuname" :state="menuname" id="feedback-user" class="form-control is-invalid" placeholder="메뉴명을 입력하세요." required></textarea>
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
            <textarea v-model="price" id="feedback-user" class="form-control is-invalid" placeholder="가격을 입력하세요." :state="stock" required></textarea>
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
  <button variant="outline-primary" type="submit" @click="ProductSubmit">등록하기</button>
</template>

<script>
import ProductPage from "@/components/product/ProductPage";
import axios from 'axios'
import store from "@/store";

export default {
  name: "RegistrationRental",
  components: { ProductPage },
  created() {
    this.goData()
  },
  data() {

  },
  methods: {

  }
}
</script>

<style scoped>

</style>