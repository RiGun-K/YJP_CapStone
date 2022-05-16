<template>
  <nav class="navBody">
    <div class="divBody">
      <ul>
        <li class="topMenu" v-if="uiLogin" @click="teamManagementPage">캠핑플랜
        </li>
        <li class="topMenu" v-if="uiLogin" @click="storageView">보관함
        </li>
        <li class="topMenu" v-if="uiLogin" @click="infoter">인포터
        </li>
        <li class="topMenu" @click="itemBuy">Item Buy
        </li>
        <li class="topMenu" @click="itemShare">Item Share
        </li>
        <li class="topMenu" v-if="uiLogin" @click="board">게시판
          <ul class="subMenu">
            <li class="menu">자유게시판</li>
            <li class="menu">리뷰게시판</li>
            <li class="menu">게시판1</li>
            <li class="menu">게시판2</li>
          </ul>
        </li>
        <li class="topMenu" v-if="uiLogin==4" @click="ProductMain">판매자전용
        </li>
        <li class="topMenu" v-if="uiLogin!='' && uiLogin!=5">마이페이지
          <ul class="subMenu">
            <li @click="memberUpadte" class="menu">정보수정</li>
            <li @click="changePass" class="menu">비밀번호변경</li>
            <li @click="myWritterList" class="menu">게시글목록</li>
            <li @click="myEquip" class="menu">장비목록</li>
            <li @click="myOrder" class="menu">주문목록</li>
            <li class="menu">고객센터</li>
          </ul>
        </li>
        <li class="topMenu" v-if="uiLogin==5" @click="storageManager">보관소관리자
        </li>
        <li class="topMenu" v-if="uiLogin==5">관리자업무
          <ul class="subMenu">
            <li @click="memberAdmin" class="menu">멤버관리</li>
            <li @click="sellerAccept" class="menu">판매자관리</li>
            <li @click="storageAdmin" class="menu">보관소관리</li>
            <li class="menu">문의관리</li>
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
    },
    memberAdmin(){
      this.$router.push("/memberAdmin")
    },
    storageAdmin(){
      this.$router.push("/storageAdmin")
    },
    sellerAccept(){
      this.$router.push("/sellerAccept")
    },
    storageManager(){
      this.$router.push("/storageManager")
    },
    teamManagementPage(){
      this.$router.push("/TeamManagementPage")
    },
    storageView(){
      this.$router.push("/storageView")
    },
    infoter(){
      this.$router.push("/infoter")
    },
    itemBuy(){
      this.$router.push("/itemBuy")
    },
    itemShare(){
      this.$router.push("/itemShare")
    },
    board(){
      this.$router.push("/board")
    },
    ProductMain(){
      this.$router.push("/ProductMain")
    },
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

  },
  watch:{
    loginCheck(){

    }
  }

}
</script>

<style scoped>
router-link{
  color: black;
}
.navBody{
  background: #E6E6FA;
  border: 1px solid black;
  width: 100%;
  text-align: left;
}
.divBody{
}
.topMenu{
  margin-top: 1%;
  margin-left: 1%;
  padding: 0.5%;
  width: 10%;
  display: inline-block;
  text-align: center;
  list-style: none;
  position: relative;
}
.topMenu:hover > .subMenu{
  display: block;
}
.topMenu:hover{
  background: #00a3de;
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
}
.menu{
  margin-top: 1%;
  background: #41b883;
  border: 1px solid black;
}
.menu:hover{
  background: #7ea6f6;
}

</style>
