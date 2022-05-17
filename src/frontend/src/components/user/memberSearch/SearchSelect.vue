<template>
  <div align="center">
    <button @click="idSearch">
      <span>아이디 찾기</span>
    </button>
    <button @click="passSearch">
      <span>비밀번호 찾기</span>
    </button>
  </div>
</template>

<script>
export default {
  name: "SearchSelect",
  methods:{
    idSearch(){
      this.$router.push('searchId')
    },
    passSearch(){
      this.$router.push('searchPass')
    }
  }
}
</script>

<style scoped>

</style>
