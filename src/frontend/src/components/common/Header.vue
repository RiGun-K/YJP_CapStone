<template>
  <div class="header-info">
    <header>
      <h1 class="header-name"><a href="/" class="header-name-link"><img :src="require('@/assets/campingLogo.jpg')" alt="...">Camping Planner</a></h1>
      <a class="header-signup-link" href="/cart"  v-if="uiLogin">CART / LIKE</a>
      <a class="header-signup-link" href="/agreement" v-if="!uiLogin">회원가입</a>
      <a class="header-login-link" href="/login" v-if="!uiLogin">로그인</a>
      <a class="header-login-link" @click="logout"  v-if="uiLogin">로그아웃</a>

      <store></store>
    </header>
  </div>
</template>

<script>
import store from "@/store";

export default {
  name: 'Header',
  data(){
    return{
      uiLogin:false,
    }
  },
  methods: {
    logout() {
      alert("로그아웃")
      store.commit("setLogoutState")
      this.$router.push("/")
    },
  },
  computed:{
    loginCheck(){
      if(store.getters.getLoginState != null){
        this.uiLogin = store.getters.getLoginState.stateCode;
        return "로그인 되어 있습니다"
      }else{
        this.uiLogin = false;
        return "로그인이 필요합니다"
      }
    },

  },
  watch:{
    loginCheck(){

    }
  }



}
</script>

<style scoped>
header{
  width:100%;
  position:relative;
  height:100%;
  border-bottom:1px solid #35495e;
  text-align: center;
}
header img{
  width: 5%;
  height: 5%;
}

.header-name{
  margin-top: 3%;
  margin-bottom: 3%;
}

.header-name-link{
  text-decoration:none;
  color: black;
  margin-top: 3%;
}
.header-name-link:hover{
  color: cadetblue;
}

.header-login-link{
  text-decoration: none;
  color: black;
  position: absolute;
  top: -30%;
  left: 87%;
}
.header-login-link:hover{
  color: cadetblue;
}
.header-signup-link{
  text-decoration: none;
  color: black;
  position: absolute;
  top: -30%;
  left: 80%;
}
</style>
