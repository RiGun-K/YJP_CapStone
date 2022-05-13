<template>
  <p></p>
  <nav class="nav nav-pills nav-justified">
    <a class="nav-link active" href="/ProductMain">상품 등록</a>
    <a class="nav-link" href="/BuyProductList">나의 상품</a>
    <a class="nav-link" href="/mAdmin">판매자 정보</a>
    <a class="nav-link disabled">못드감 ㅋ</a>
  </nav>
</template>
<script>
export default {
  name: "ProductPage",
}
</script>

<style scoped>
.navbar navbar-expand-lg navbar-light bg-light {
  justify-content: center;
}
.nav nav-pills nav-justified {

}
h2,h3{
  margin: 4%;
}
</style>