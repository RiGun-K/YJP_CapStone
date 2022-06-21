<template>
  <nav class="navBody">
    <div class="divBody">
      <ul>
        <li class="topMenu" v-if="uiLogin">
          <a href="/TeamManagementPage">계획작성</a>
        </li>
        <li class="topMenu" v-if="uiLogin">
          <a href="/storageView">보관함</a>
        </li>
        <li class="topMenu" v-if="uiLogin">
          <a href="/infoter">캠핑장예약</a>
        </li>
        <li class="topMenu">
          <a href="/itemBuy">Item Buy</a>
        </li>
        <li class="topMenu">
          <a href="/itemShare">Item Share</a>
        </li>
        <li class="topMenu" v-if="uiLogin">
          <a href="/Read">게시판</a>
          <ul class="subMenu">
            <li class="menu"><a href="/Read">자유게시판</a></li>
          </ul>
        </li>
        <li class="topMenu" v-if="uiLogin==4">
          <a href="/ProductMain">판매자전용</a>
        </li>
        <li class="topMenu" v-if="uiLogin!='' && uiLogin!=5">
          <a href="/memberUpdate">마이페이지</a>
          <ul class="subMenu">
            <li class="menu"><a href="/memberUpdate">정보수정</a></li>
            <li class="menu"><a href="/changePass">비밀번호변경</a></li>
            <li class="menu"><a href="/myPreference">선호도관리</a></li>
            <li class="menu"><a href="/myPageWritter"><a href="/myPageWritter">게시글목록</a></a></li>
            <li class="menu"><a href="/myPageEquip">장비목록</a></li>
            <li class="menu"><a href="/myBox">보관함관리</a></li>
            <li class="menu"><a href="/ordersList">주문목록</a></li>
            <li class="menu" v-if="uiLogin==3"><a href="/company">판매자신청</a></li>
          </ul>
        </li>
        <li class="topMenu" v-if="uiLogin==5" @click="storageManager">
          <a href="/storageManager">보관소관리자</a>
        </li>
        <li class="topMenu" v-if="uiLogin==5">
          <a href="/memberAdmin">관리자업무</a>
          <ul class="subMenu">
            <li class="menu"><a href="/memberAdmin">멤버관리</a></li>
            <li class="menu"><a href="/sellerAccept">판매자관리</a></li>
            <li class="menu"><a href="/storageAdmin">보관소관리</a></li>
            <li class="menu"><a href="/orderAdmin">주문관리</a></li>
            <li class="menu"><a href="/writerAdmin">게시글관리</a></li>
          </ul>
        </li>
        <li class="topMenu" v-if="uiLogin">
          <a href="/noticeboard">고객센터</a>
          <ul class="subMenu">
            <li class="menu"><a href="/noticeboard">공지사항</a></li>
            <li class="menu"><a href="/questionboard">문의사항</a></li>
          </ul>
        </li>
      </ul>
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
    }

  },
  watch:{
    loginCheck(){
    }
  }
}
</script>

<style scoped>
a{
  text-decoration: none;
  color: black;
  display: block;
  text-align: center;
  width: 100%;
}
a:hover{
  color: white;
  text-decoration: none;
}
router-link{
  color: black;
}
.navBody{
  background: ivory;
  border: 1px solid black;
  padding-right: 0.5%;
  padding-left: 0.5%;
  width: 100%;
  height: 6%;
  z-index: 3;
  text-align: left;
}
.divBody{
  width: 100%;
  z-index: 3;
}
.topMenu{
  margin-top: 1%;
  margin-right: 0.5%;
  margin-left: 0.5%;
  width: 10%;
  text-align: center;
  display: inline-block;
  list-style: none;
  position: relative;
  border-radius: 10px;
  font-size: 18px;
  z-index: 3;
}
.topMenu:hover > .subMenu{
  display: block;
}
.topMenu:hover{
  background: lightgreen;
}
.subMenu{
  padding: 0.5%;
  margin: 0.5%;
  display: none;
  list-style: none;
  position: absolute;
  left: -1px;
  right: -1px;
  top: 100%;
  text-align: center;
  border: 1px solid black;
  border-radius: 10px;
  z-index: 3;
  font-size: 16px;
  background: white;
}
.menu{
  margin-top: 1%;
  background: white;
  border-radius: 10px;
  border: 1px solid black;
  z-index: 3;
}
.menu:hover{
  background: crimson;
}
</style>
