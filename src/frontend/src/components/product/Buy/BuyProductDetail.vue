<template>
  <ProductPage></ProductPage>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
  <br>
  <br>
  <div class="products-info">
    <div class="product-input-form">
      <label for="text-select">상품분류</label>

      <form v-on:submit.prevent="formSubmit" method="post" enctype="multipart/form-data">
        <select v-if="myContent.kindid.kindid" v-model="myContent.kindid.kindid" class="form-select" aria-label="Default select example" readonly>
          <option v-for="(option, index) in options" :key="index" :value="option">
          </option>
        </select>
        <div class="mt-3">선택유형 : <strong>{{ myContent.kindid.kindid }}</strong></div>

        <form class="was-validated" readonly>
          <div class="mb-1" readonly>
            <label for="validationTextarea" class="form-label" readonly>메뉴명</label>
            <textarea v-if="myContent.menuname" v-model="myContent.menuname" :state="menuname" id="feedback-user" class="form-control is-invalid" placeholder="메뉴명을 입력하세요." readonly></textarea>
          </div>
        </form>
        <br>
        <form class="was-validated">
          <div class="mb-1">
            <label for="validationTextarea" class="form-label">수량</label>
            <textarea readonly v-if="myContent.stock" v-model="myContent.stock" id="feedback-user" class="form-control is-invalid" placeholder="수량을 입력하세요." :state="stock" required></textarea>
          </div>
        </form>


        <br>
        <form class="was-validated">
          <div class="mb-1">
            <label for="validationTextarea" class="form-label">가격</label>
            <textarea readonly v-if="myContent.price" v-model="myContent.price" id="feedback-user" class="form-control is-invalid" placeholder="가격을 입력하세요." :state="stock" required></textarea>
          </div>
        </form>


        <br>
        <form class="was-validated">
          <div class="mb-1">
            <label for="validationTextarea" class="form-label">설명</label>
            <textarea readonly v-if="myContent.ex" v-model="myContent.ex" class="form-control is-invalid" placeholder="설명을 입력하세요." :state="ex" id="feedback-user" required></textarea>
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
  <button variant="outline-primary" type="submit" @click="deleted">삭제하기</button>




</template>

<script>
import axios from "axios";
import store from "@/store";
import ProductPage from "@/components/product/ProductPage";

export default {
  name: "BuyProductDetail",
  components: { ProductPage },

  data() {
    return {
      id: '',
      myContent: [],
      kindid: '',
      menuname: '',
      stock: '',
      price: '',
      ex: '',
      file: '',
      mid: store.getters.getLoginState.loginState,

      options: [
        { value: null, text: '판매하실 상품의 유형을 선택해주세요'},
        { value: '1', text: '캠핑'},
        { value: '2', text: '구매'},
        { value: '3', text: '렌탈'},
      ],
    }
  },
  created() {
    this.myDataList();
  },
  methods: {
    myDataList() {
      this.id = this.$route.params.buyId;
      axios.get('http://localhost:9002/api/myProduct_detail/' + this.id)
        .then(res => {
          console.log(res.data);
          this.myContent = res.data;
          console.log(this.myContent.mid.mcode)
          console.log("메뉴번호는" + this.id);
          console.log("회원번호는" + this.mid);
        })
        .catch(e => {
          console.log(e);
        })
    },
    updated() {
      this.$router.push({
        name: "BuyProductEdit",
        params: {
                  menuid: this.id,
                  mid: this.myContent.mid.mid,
                  menuname: this.myContent.menuname,
                  stock: this.myContent.stock,
                  price: this.myContent.price,
                  ex: this.myContent.ex,
        },

      });

    },
    deleted() {
        if(confirm("삭제하시겠습니까?")) {
          axios.delete('http://localhost:9002/api/myProduct_Delete/' + this.id)
            .then(res => {
            console.log("삭제되었습니다.", res)
            alert("메뉴가 삭제되었습니다.");
            this.$router.push({
              name: "BuyProductList"
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
button{
  margin-left: 49%;
  margin-top: 3%;
}
h1{
  text-align: center;
}
.products-info{
  display: flex;
  justify-content: center;
  align-items: center;
}
.product-input-form{
  width: 30%;
  text-align: center;
}
</style>
