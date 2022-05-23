<template>
  <div class="allBody">
    <button @click="memberAdmin" class="Btn">멤버관리</button>
    <button @click="storageAdmin" class="Btn">보관소전체관리</button>
  </div>
</template>

<script>
export default {
  //eslint-disable-next-line
  name: "Admin",
  methods:{
    memberAdmin(){
      this.$router.push("/memberAdmin")
    },
    storageAdmin(){
      this.$router.push("/storageAdmin")
    }
  }
}
</script>


<style scoped>
.allBody{
  margin-top: 1%;
  margin-right: 1%;
  width: 100%;
  text-align: center;
}
.Btn{
  margin-top: 1%;
  margin-left: 1%;
}

/*사이드 바 테스트*/
</style>
