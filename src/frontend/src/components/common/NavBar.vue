<template>


  <nav class="navbar navbar-expand-lg navbar-light bg-light" style="background-color: #9f0b27;">
    <div class="container-fluid">
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <router-link class="nav-link" to="/TeamManagementPage" v-if="uiLogin">캠핑플랜</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/storageView" v-if="uiLogin">보관함</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/infoter" v-if="uiLogin">인포터</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/itemBuy">Item Buy</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/itemShare">Item Share</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/board" v-if="uiLogin">게시판</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/ProductMain" v-if="uiLogin==4">판매자 전용</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/company" v-if="sellerCheck()">판매자 신청</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/memberupdate" v-if="uiLogin">정보 수정</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/mAdmin" v-if="uiLogin==5">관리자 페이지</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/sellerAccept" v-if="uiLogin==5">판매자승인</router-link>
          </li>

        </ul>
        <form class="d-flex">
          <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
          <button class="btn btn-outline-success" type="submit">Search</button>
        </form>
      </div>
    </div>
  </nav>
  <store></store>
</template>

<script>
import store from '@/store'

export default {
  name: 'NavBar',
  data(){
    return{
      uiLogin:false,
    }
  },
  methods:{
    sellerCheck(){
      switch (this.uiLogin){
        case "4":
        case "5":
        case false:
          return false
        default:
          return true
      }
    }
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
@import "https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"
@import "https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"

.login {
  text-align: center;
}
.d-flex{
  color: #b2e2fd;
}
.btn{
  border-color: #009fff;
  color: #00a3de;
}
.btn:hover{
  border-color: #009fff;
  background-color: #b2e2fd;
}
</style>
