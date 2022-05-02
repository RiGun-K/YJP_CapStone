<template>
  <ProductPage></ProductPage>
  <div>
    <br>
    <table class="table table-striped">
      <thead>
      <tr>
        <th>메뉴번호</th>
        <th>분류</th>
        <th>메뉴명</th>
        <th>설명</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="myProduct in list"
          :key="myProduct.id"
          :item="myProduct" @click="showInfo(myProduct)" style="cursor:pointer;">
        <th scope="row">{{ myProduct.menuid }}</th>
        <td>{{ myProduct.kindid.kindname }}</td>
        <td>{{ myProduct.menuname }}</td>
        <td>{{ myProduct.ex }}</td>
      </tr>
<!--      <router-link :to="{name: 'MyProductDetail', params: { menuid:myProduct.menuid }}"></router-link>-->
      </tbody>
    </table>
  </div>

  <nav aria-label="page">
    <ul class="pagination pagination-lg">
      <li class="page-item disabled">
        <a class="page-link">Previous</a>
      </li>
      <li class="page-item active" aria-current="page">
        <a class="page-link" href="#">1</a>
      </li>
      <li class="page-item"><a class="page-link" href="#">2</a></li>
      <li class="page-item"><a class="page-link" href="#">3</a></li>
      <li class="page-item">
        <a class="page-link" href="#">Next</a>
      </li>
    </ul>
  </nav>
</template>

<script>
import axios from 'axios'
import store from "@/store";
import ProductPage from "@/components/product/ProductPage";

export default {
  name: "MyProductList",
  components: { ProductPage },

  created() {
    this.goMyData()
  },
  data() {
    return {
      selected: false,
      list: [],
      myProduct: '',
    }
  },
  methods: {
    goMyData() {
      this.user = store.getters.getLoginState.loginState
      console.log("현재 사용자 아이디는" + this.user)
      axios.get('http://localhost:9002/api/myProduct_list/'+this.user)
        .then((res) => {
          console.log(res.data);

          this.list = res.data;
        })
        .catch(e => {
          console.log(e)
        })
    },
    showInfo(myProduct) {
      this.$router.push({
        path: `/MyProductDetail/${myProduct.menuid}`
      })
    }

  }
}
</script>

<style scoped>

.pagination {
  display: flex;
  padding-left: 0;
  list-style: none;
  justify-content: center
}
</style>
