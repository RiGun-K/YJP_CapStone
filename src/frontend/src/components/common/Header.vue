<template>
  <link rel="canonical" href="https://getbootstrap.com/docs/5.2/examples/headers/">

  <svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
    <symbol id="bootstrap" viewBox="0 0 118 94">
      <title>Bootstrap</title>
      <path fill-rule="evenodd" clip-rule="evenodd" d="M24.509 0c-6.733 0-11.715 5.893-11.492 12.284.214 6.14-.064 14.092-2.066 20.577C8.943 39.365 5.547 43.485 0 44.014v5.972c5.547.529 8.943 4.649 10.951 11.153 2.002 6.485 2.28 14.437 2.066 20.577C12.794 88.106 17.776 94 24.51 94H93.5c6.733 0 11.714-5.893 11.491-12.284-.214-6.14.064-14.092 2.066-20.577 2.009-6.504 5.396-10.624 10.943-11.153v-5.972c-5.547-.529-8.934-4.649-10.943-11.153-2.002-6.484-2.28-14.437-2.066-20.577C105.214 5.894 100.233 0 93.5 0H24.508zM80 57.863C80 66.663 73.436 72 62.543 72H44a2 2 0 01-2-2V24a2 2 0 012-2h18.437c9.083 0 15.044 4.92 15.044 12.474 0 5.302-4.01 10.049-9.119 10.88v.277C75.317 46.394 80 51.21 80 57.863zM60.521 28.34H49.948v14.934h8.905c6.884 0 10.68-2.772 10.68-7.727 0-4.643-3.264-7.207-9.012-7.207zM49.948 49.2v16.458H60.91c7.167 0 10.964-2.876 10.964-8.281 0-5.406-3.903-8.178-11.425-8.178H49.948z"></path>
    </symbol>
    <symbol id="home" viewBox="0 0 16 16">
      <path d="M8.354 1.146a.5.5 0 0 0-.708 0l-6 6A.5.5 0 0 0 1.5 7.5v7a.5.5 0 0 0 .5.5h4.5a.5.5 0 0 0 .5-.5v-4h2v4a.5.5 0 0 0 .5.5H14a.5.5 0 0 0 .5-.5v-7a.5.5 0 0 0-.146-.354L13 5.793V2.5a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v1.293L8.354 1.146zM2.5 14V7.707l5.5-5.5 5.5 5.5V14H10v-4a.5.5 0 0 0-.5-.5h-3a.5.5 0 0 0-.5.5v4H2.5z"/>
    </symbol>
    <symbol id="speedometer2" viewBox="0 0 16 16">
      <path d="M8 4a.5.5 0 0 1 .5.5V6a.5.5 0 0 1-1 0V4.5A.5.5 0 0 1 8 4zM3.732 5.732a.5.5 0 0 1 .707 0l.915.914a.5.5 0 1 1-.708.708l-.914-.915a.5.5 0 0 1 0-.707zM2 10a.5.5 0 0 1 .5-.5h1.586a.5.5 0 0 1 0 1H2.5A.5.5 0 0 1 2 10zm9.5 0a.5.5 0 0 1 .5-.5h1.5a.5.5 0 0 1 0 1H12a.5.5 0 0 1-.5-.5zm.754-4.246a.389.389 0 0 0-.527-.02L7.547 9.31a.91.91 0 1 0 1.302 1.258l3.434-4.297a.389.389 0 0 0-.029-.518z"/>
      <path fill-rule="evenodd" d="M0 10a8 8 0 1 1 15.547 2.661c-.442 1.253-1.845 1.602-2.932 1.25C11.309 13.488 9.475 13 8 13c-1.474 0-3.31.488-4.615.911-1.087.352-2.49.003-2.932-1.25A7.988 7.988 0 0 1 0 10zm8-7a7 7 0 0 0-6.603 9.329c.203.575.923.876 1.68.63C4.397 12.533 6.358 12 8 12s3.604.532 4.923.96c.757.245 1.477-.056 1.68-.631A7 7 0 0 0 8 3z"/>
    </symbol>
    <symbol id="calendar2 check" viewBox="0 0 16 16">
      <path d="M10.854 8.146a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708 0l-1.5-1.5a.5.5 0 0 1 .708-.708L7.5 10.793l2.646-2.647a.5.5 0 0 1 .708 0z"/>
      <path d="M3.5 0a.5.5 0 0 1 .5.5V1h8V.5a.5.5 0 0 1 1 0V1h1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h1V.5a.5.5 0 0 1 .5-.5zM2 2a1 1 0 0 0-1 1v11a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V3a1 1 0 0 0-1-1H2z"/>
      <path d="M2.5 4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5H3a.5.5 0 0 1-.5-.5V4z"/>
    </symbol>
    <symbol id="box_seam" viewBox="0 0 16 16">
      <path d="M8.186 1.113a.5.5 0 0 0-.372 0L1.846 3.5l2.404.961L10.404 2l-2.218-.887zm3.564 1.426L5.596 5 8 5.961 14.154 3.5l-2.404-.961zm3.25 1.7-6.5 2.6v7.922l6.5-2.6V4.24zM7.5 14.762V6.838L1 4.239v7.923l6.5 2.6zM7.443.184a1.5 1.5 0 0 1 1.114 0l7.129 2.852A.5.5 0 0 1 16 3.5v8.662a1 1 0 0 1-.629.928l-7.185 2.874a.5.5 0 0 1-.372 0L.63 13.09a1 1 0 0 1-.63-.928V3.5a.5.5 0 0 1 .314-.464L7.443.184z"/>
    </symbol>
    <symbol id="search" viewBox="0 0 16 16">
      <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
    </symbol>
    <symbol id="people-circle" viewBox="0 0 16 16">
      <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
      <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
    </symbol>
    <symbol id="shop" viewBox="0 0 16 16">
      <path d="M2.97 1.35A1 1 0 0 1 3.73 1h8.54a1 1 0 0 1 .76.35l2.609 3.044A1.5 1.5 0 0 1 16 5.37v.255a2.375 2.375 0 0 1-4.25 1.458A2.371 2.371 0 0 1 9.875 8 2.37 2.37 0 0 1 8 7.083 2.37 2.37 0 0 1 6.125 8a2.37 2.37 0 0 1-1.875-.917A2.375 2.375 0 0 1 0 5.625V5.37a1.5 1.5 0 0 1 .361-.976l2.61-3.045zm1.78 4.275a1.375 1.375 0 0 0 2.75 0 .5.5 0 0 1 1 0 1.375 1.375 0 0 0 2.75 0 .5.5 0 0 1 1 0 1.375 1.375 0 1 0 2.75 0V5.37a.5.5 0 0 0-.12-.325L12.27 2H3.73L1.12 5.045A.5.5 0 0 0 1 5.37v.255a1.375 1.375 0 0 0 2.75 0 .5.5 0 0 1 1 0zM1.5 8.5A.5.5 0 0 1 2 9v6h1v-5a1 1 0 0 1 1-1h3a1 1 0 0 1 1 1v5h6V9a.5.5 0 0 1 1 0v6h.5a.5.5 0 0 1 0 1H.5a.5.5 0 0 1 0-1H1V9a.5.5 0 0 1 .5-.5zM4 15h3v-5H4v5zm5-5a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v3a1 1 0 0 1-1 1h-2a1 1 0 0 1-1-1v-3zm3 0h-2v3h2v-3z"/>
    </symbol>
    <symbol id="chat" viewBox="0 0 16 16">
      <path d="M2 1a1 1 0 0 0-1 1v8a1 1 0 0 0 1 1h9.586a2 2 0 0 1 1.414.586l2 2V2a1 1 0 0 0-1-1H2zm12-1a2 2 0 0 1 2 2v12.793a.5.5 0 0 1-.854.353l-2.853-2.853a1 1 0 0 0-.707-.293H2a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2h12z"/>
      <path d="M3 3.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zM3 6a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9A.5.5 0 0 1 3 6zm0 2.5a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5z"/>
    </symbol>
    <symbol id="rent" viewBox="0 0 16 16">
      <path d="M5 1v8H1V1h4zM1 0a1 1 0 0 0-1 1v8a1 1 0 0 0 1 1h4a1 1 0 0 0 1-1V1a1 1 0 0 0-1-1H1zm13 2v5H9V2h5zM9 1a1 1 0 0 0-1 1v5a1 1 0 0 0 1 1h5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H9zM5 13v2H3v-2h2zm-2-1a1 1 0 0 0-1 1v2a1 1 0 0 0 1 1h2a1 1 0 0 0 1-1v-2a1 1 0 0 0-1-1H3zm12-1v2H9v-2h6zm-6-1a1 1 0 0 0-1 1v2a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1v-2a1 1 0 0 0-1-1H9z"/>
    </symbol>
    <symbol id="cart" viewBox="0 0 16 16">
      <path fill-rule="evenodd" d="M10.5 3.5a2.5 2.5 0 0 0-5 0V4h5v-.5Zm1 0V4H15v10a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V4h3.5v-.5a3.5 3.5 0 1 1 7 0ZM14 14V5H2v9a1 1 0 0 0 1 1h10a1 1 0 0 0 1-1ZM8 7.993c1.664-1.711 5.825 1.283 0 5.132-5.825-3.85-1.664-6.843 0-5.132Z"/>
    </symbol>
    <symbol id="cash" viewBox="0 0 16 16">
      <path fill-rule="evenodd" d="M11 15a4 4 0 1 0 0-8 4 4 0 0 0 0 8zm5-4a5 5 0 1 1-10 0 5 5 0 0 1 10 0z"/>
      <path d="M9.438 11.944c.047.596.518 1.06 1.363 1.116v.44h.375v-.443c.875-.061 1.386-.529 1.386-1.207 0-.618-.39-.936-1.09-1.1l-.296-.07v-1.2c.376.043.614.248.671.532h.658c-.047-.575-.54-1.024-1.329-1.073V8.5h-.375v.45c-.747.073-1.255.522-1.255 1.158 0 .562.378.92 1.007 1.066l.248.061v1.272c-.384-.058-.639-.27-.696-.563h-.668zm1.36-1.354c-.369-.085-.569-.26-.569-.522 0-.294.216-.514.572-.578v1.1h-.003zm.432.746c.449.104.655.272.655.569 0 .339-.257.571-.709.614v-1.195l.054.012z"/>
      <path d="M1 0a1 1 0 0 0-1 1v8a1 1 0 0 0 1 1h4.083c.058-.344.145-.678.258-1H3a2 2 0 0 0-2-2V3a2 2 0 0 0 2-2h10a2 2 0 0 0 2 2v3.528c.38.34.717.728 1 1.154V1a1 1 0 0 0-1-1H1z"/>
      <path d="M9.998 5.083 10 5a2 2 0 1 0-3.132 1.65 5.982 5.982 0 0 1 3.13-1.567z"/>
    </symbol>
    <symbol id="manager" viewBox="0 0 16 16">
      <path d="M7.752.066a.5.5 0 0 1 .496 0l3.75 2.143a.5.5 0 0 1 .252.434v3.995l3.498 2A.5.5 0 0 1 16 9.07v4.286a.5.5 0 0 1-.252.434l-3.75 2.143a.5.5 0 0 1-.496 0l-3.502-2-3.502 2.001a.5.5 0 0 1-.496 0l-3.75-2.143A.5.5 0 0 1 0 13.357V9.071a.5.5 0 0 1 .252-.434L3.75 6.638V2.643a.5.5 0 0 1 .252-.434L7.752.066ZM4.25 7.504 1.508 9.071l2.742 1.567 2.742-1.567L4.25 7.504ZM7.5 9.933l-2.75 1.571v3.134l2.75-1.571V9.933Zm1 3.134 2.75 1.571v-3.134L8.5 9.933v3.134Zm.508-3.996 2.742 1.567 2.742-1.567-2.742-1.567-2.742 1.567Zm2.242-2.433V3.504L8.5 5.076V8.21l2.75-1.572ZM7.5 8.21V5.076L4.75 3.504v3.134L7.5 8.21ZM5.258 2.643 8 4.21l2.742-1.567L8 1.076 5.258 2.643ZM15 9.933l-2.75 1.571v3.134L15 13.067V9.933ZM3.75 14.638v-3.134L1 9.933v3.134l2.75 1.571Z"/>
    </symbol>
    <symbol id="master" viewBox="0 0 16 16">
      <path d="M4 16s-1 0-1-1 1-4 5-4 5 3 5 4-1 1-1 1H4Zm4-5.95a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5Z"/>
      <path d="M2 1a2 2 0 0 0-2 2v9.5A1.5 1.5 0 0 0 1.5 14h.653a5.373 5.373 0 0 1 1.066-2H1V3a1 1 0 0 1 1-1h12a1 1 0 0 1 1 1v9h-2.219c.554.654.89 1.373 1.066 2h.653a1.5 1.5 0 0 0 1.5-1.5V3a2 2 0 0 0-2-2H2Z"/>
    </symbol>
    <symbol id="logout" viewBox="0 0 16 16">
      <path fill-rule="evenodd" d="M10 12.5a.5.5 0 0 1-.5.5h-8a.5.5 0 0 1-.5-.5v-9a.5.5 0 0 1 .5-.5h8a.5.5 0 0 1 .5.5v2a.5.5 0 0 0 1 0v-2A1.5 1.5 0 0 0 9.5 2h-8A1.5 1.5 0 0 0 0 3.5v9A1.5 1.5 0 0 0 1.5 14h8a1.5 1.5 0 0 0 1.5-1.5v-2a.5.5 0 0 0-1 0v2z"/>
      <path fill-rule="evenodd" d="M15.854 8.354a.5.5 0 0 0 0-.708l-3-3a.5.5 0 0 0-.708.708L14.293 7.5H5.5a.5.5 0 0 0 0 1h8.793l-2.147 2.146a.5.5 0 0 0 .708.708l3-3z"/>
    </symbol>



  </svg>
  <main>
    <header>
      <div class="px-3 py-2 text-white">
        <div class="container">
          <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
            <a href="/" class="d-flex align-items-center my-2 my-lg-0 me-lg-auto text-white text-decoration-none">
              <img :src="require('@/assets/img.png')" alt="..." style="width: 20%; height: 16%">
            </a>

            <ul class="nav col-12 col-lg-auto my-2 justify-content-center my-md-0 text-small">
              <li v-if="uiLogin">
                <a href="/TeamManagementPage" class="nav-link text-black">
                  <svg class="bi d-block mx-auto mb-1" width="24" height="24"><use xlink:href="#calendar2 check"/></svg>
                  Planner
                </a>
              </li>
              <li v-if="uiLogin">
                <a href="/storageView" class="nav-link text-black">
                  <svg class="bi d-block mx-auto mb-1" width="24" height="24"><use xlink:href="#box_seam"/></svg>
                  Storage
                </a>
              </li>
              <li v-if="uiLogin">
                <a href="/infoter" class="nav-link text-black">
                  <svg class="bi d-block mx-auto mb-1" width="24" height="24"><use xlink:href="#search"/></svg>
                  Place
                </a>
              </li>
              <li>
                <a href="/itemBuy" class="nav-link text-black">
                  <svg class="bi d-block mx-auto mb-1" width="24" height="24"><use xlink:href="#shop"/></svg>
                  Product
                </a>
              </li>
              <li>
                <a href="/itemShare" class="nav-link text-black">
                  <svg class="bi d-block mx-auto mb-1" width="24" height="24"><use xlink:href="#rent"/></svg>
                  Rental
                </a>
              </li>
              <li v-if="uiLogin">
                <a href="/Read" class="nav-link text-black">
                  <svg class="bi d-block mx-auto mb-1" width="24" height="24"><use xlink:href="#chat"/></svg>
                  Community
                </a>
              </li>
              <li v-if="uiLogin==4">
                <a href="/ProductMain" class="nav-link text-black">
                  <svg class="bi d-block mx-auto mb-1" width="24" height="24"><use xlink:href="#cash"/></svg>
                  Seller
                </a>
              </li>
              <li v-if="uiLogin==5">
                <a href="/storageManager" class="nav-link text-black">
                  <svg class="bi d-block mx-auto mb-1" width="24" height="24"><use xlink:href="#manager"/></svg>
                  Manager
                </a>
              </li>
              <li v-if="uiLogin==5">
                <a href="/memberAdmin" class="nav-link text-black">
                  <svg class="bi d-block mx-auto mb-1" width="24" height="24"><use xlink:href="#master"/></svg>
                  master
                </a>
              </li>

            </ul>
          </div>
        </div>
      </div>
      <div class="px-3 py-2 border-bottom mb-3">
        <div class="container d-flex flex-wrap justify-content-center">
          <form class="col-12 col-lg-auto mb-2 mb-lg-0 me-lg-auto" role="search">
            <input type="search" class="form-control" placeholder="Search..." aria-label="Search">
          </form>

          <div class="text-end">
            <button type="button"  class="btn btn-light text-dark me-2" v-if="!uiLogin">Sign-up</button>
            <button type="button" class="btn btn-primary" @click="login" v-if="!uiLogin">Login</button>

            <div style="display: flex">
              <div class="flex-shrink-0 dropdown" v-if="uiLogin!='' && uiLogin!=5" style="padding-top: 9px">
                <a href="/" class="d-block link-dark text-decoration-none" data-bs-toggle="dropdown">
                  <svg class="bi d-block mx-auto mb-1" width="24" height="24"><use xlink:href="#people-circle"/></svg>
                </a>
                <ul class="dropdown-menu text-small shadow" aria-labelledby="dropdownUser2">
                  <li><a class="dropdown-item" href="/memberUpdate">정보 수정</a></li>
                  <li><a class="dropdown-item" href="/changePass">비밀번호 변경</a></li>
                  <li><a class="dropdown-item" href="/myPreference">선호도 관리</a></li>
                  <li><a class="dropdown-item" href="/myPageWritter">게시글 목록</a></li>
                  <li><a class="dropdown-item" href="/myPageEquip">장비 목록</a></li>
                  <li><a class="dropdown-item" href="/myBox">보관함 관리</a></li>
                  <li><a class="dropdown-item" href="/ordersList">주문 목록</a></li>
                  <li v-if="uiLogin==3"><a class="dropdown-item" href="/company">판매자 신청</a></li>

                  <li><hr class="dropdown-divider"></li>
                  <li @click="logout" style="display: flex"><a class="dropdown-item" >Sign out</a></li>
                </ul>
              </div>
<!--              <a href="/memberUpdate" class="nav-link text-black"  v-if="uiLogin">-->
<!--                <svg class="bi d-block mx-auto mb-1" width="24" height="24"><use xlink:href="#people-circle"/></svg>-->
<!--              </a>-->
              <a href="/cart" class="nav-link text-black"  v-if="uiLogin">
                <svg class="bi d-block mx-auto mb-1" width="24" height="24"><use xlink:href="#cart" /></svg>
              </a>
              <button type="button" class="btn btn-light text-dark me-2" @click="logout" v-if="uiLogin!='' && uiLogin==5">Sign out</button>
            </div>
          </div>
        </div>
      </div>
    </header>

  </main>
<!--  <div class="header-info">-->
<!--    <header>-->
<!--      <h1 class="header-name"><a href="/" class="header-name-link"><img :src="require('@/assets/campingLogo.jpg')" alt="...">Camping Planner</a></h1>-->
<!--      <a class="header-signup-link" href="/cart"  v-if="uiLogin">CART / LIKE</a>-->
<!--      <a class="header-signup-link" href="/agreement" v-if="!uiLogin">회원가입</a>-->
<!--      <a class="header-login-link" href="/login" v-if="!uiLogin">로그인</a>-->
<!--      <a class="header-login-link" @click="logout"  v-if="uiLogin">로그아웃</a>-->

<!--      <store></store>-->
<!--    </header>-->
<!--  </div>-->
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
    login(){
      this.$router.push("/login")
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


.bi {
  vertical-align: -.125em;
  fill: currentColor;
}

.text-small {
  font-size: 85%;
}

@media screen and (min-width: 1400px) {
  .px-3 py-2 border-bottom mb-3{
    max-width: 71%;
    margin-left: 15%;
  }
}

@media screen and (min-width: 1200px) {
  .px-3 py-2 border-bottom mb-3{
    max-width: 71%;
    margin-left: 15%;
  }
}

/*header{*/
/*  width:100%;*/
/*  position:relative;*/
/*  height:100%;*/
/*  border-bottom:1px solid #35495e;*/
/*  text-align: center;*/
/*}*/
/*header img{*/
/*  width: 5%;*/
/*  height: 5%;*/
/*}*/

/*.header-name{*/
/*  margin-top: 3%;*/
/*  margin-bottom: 3%;*/
/*}*/

/*.header-name-link{*/
/*  text-decoration:none;*/
/*  color: black;*/
/*  margin-top: 3%;*/
/*}*/
/*.header-name-link:hover{*/
/*  color: cadetblue;*/
/*}*/

/*.header-login-link{*/
/*  text-decoration: none;*/
/*  color: black;*/
/*  position: absolute;*/
/*  top: -30%;*/
/*  left: 87%;*/
/*}*/
/*.header-login-link:hover{*/
/*  color: cadetblue;*/
/*}*/
/*.header-signup-link{*/
/*  text-decoration: none;*/
/*  color: black;*/
/*  position: absolute;*/
/*  top: -30%;*/
/*  left: 80%;*/
/*}*/
</style>
