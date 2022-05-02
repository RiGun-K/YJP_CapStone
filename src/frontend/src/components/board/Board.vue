<template>
  <div class="hello">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
<!--    <br><p style="font-size: 26px">게시글 작성</p>-->
<!--    <button type="button" class="btn btn-outline-primary" @click="read">글쓰기</button>-->

    <br><br><p style="font-size: 26px">게시글 리스트</p>
    <button type="button" class="btn btn-outline-primary" @click="list">리스트</button>

  </div>
</template>

<script>
export default {
  name: "Board",
  props: {
    msg: String
  },

  methods: {
    list() {
      this.$router.push({
        path: 'Read'
      })
    },
    read() {
      this.$router.push({
        path: 'Create'
      })
    }
  }
}
</script>

<style scoped>
.hello{
  text-align: center;
}
h3 {
  margin: 40px 0 0;

}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>