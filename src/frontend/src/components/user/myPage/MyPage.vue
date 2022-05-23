<template>
  <div id="content">
    <button @click="memberUpadte">정보수정</button>
    <button @click="changePass" style="margin-left: 10px; margin-top: 2%">비밀번호변경</button>
    <button @click="myWritterList" style="margin-left: 10px;">작성게시글</button>
    <button @click="myOrder" style="margin-left: 10px;">주문/결제</button>
    <button @click="myEquip" style="margin-left: 10px;">장비목록</button>
  </div>
</template>

<script>
export default {
  name: "MyPage",
  methods:{
    memberUpadte(){
      this.$router.push("memberUpdate")
    },
    changePass(){
      this.$router.push("changePass")
    },
    myWritterList(){
      this.$router.push("myPageWritter")
    },
    myEquip(){
      this.$router.push("myPageEquip")
    },
    myOrder(){
      this.$router.push("ordersList")
    }
  }
}
</script>

<style scoped>

body {
  margin: 0;
  height: 100%;
  background-color: #E6E6FA;
}
#content {
  position: absolute;
  left: 50%;
  transform: translate(-50%);
  width: 560px;
}
/* 입력폼 */
h3 {
  margin: 19px 0 8px;
  font-size: 14px;
  font-weight: 700;
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
</style>
