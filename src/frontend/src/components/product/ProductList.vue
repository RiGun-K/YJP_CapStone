<template>
  <ProductPage></ProductPage>
  <div>
    <br>
    <table class="table table-striped">
      <thead>
      <tr>
        <th>메뉴번호</th>
        <th>메뉴명</th>
        <th>분류</th>
        <th>가격</th>
        <th>설명</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="product in list"
          :key="product.id"
          :item="product" @click="toDetail(product)" style="cursor:pointer;">
        <th scope="row">{{ product.menuid }}</th>
        <td>{{ product.menuname }}</td>
        <td>{{ product.kindid.kindname }}</td>
        <td>{{ product.price }}</td>
        <td>{{ product.ex }}</td>
      </tr>
      <!-- PathVariable 을 위해서는 router-link 작성 -->
<!--      <router-link :to="{name: 'productDetail', params: { menuid:product.menuid }}"></router-link>-->
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
import ProductPage from "@/components/product/ProductPage";

export default {
  name: "ProductList",
  components: { ProductPage },
  created() {
    this.goData()
  },
  data() {
    return {
      selected: false,
      list: [],
      product: '',
    }
  },
  methods: {
    goData() {
      axios.get('/api/product_list')
        .then((res) => {
          console.log(res.data);
          this.list = res.data;
        })
        .catch(e => {
          console.log(e)
        })
    },
    // path로 받기
    toDetail(product){
      this.$router.push({
        path: `/ProductDetail/${product.menuid}`
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
