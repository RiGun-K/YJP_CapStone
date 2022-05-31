<template>
  <div class="sidebar" :style="{ width: sidebarWidth }">
    <h1>
      <span v-if="collapsed">
        <div>C</div>
      </span>
      <span v-else >Category</span>
    </h1>

    <h5><span v-if="collapsed">
      </span>
      <span v-else >캠핑용품</span></h5>
    <SidebarLink class="sidebar-link" icon="" to="/itemBuy" @click="menu(1)">캠핑패키지</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/">텐트</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/">타프</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/">테이블</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/">체어</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/">침낭</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/">매트</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/">식기</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/">버너</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/">화로</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/">캠핑소품</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/">등산용품</SidebarLink>

    <h5><span v-if="collapsed">
      </span>
      <span v-else >식품</span></h5>
    <SidebarLink class="sidebar-link" icon="" to="/">식품패키지</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/">육류</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/">과일</SidebarLink>
    <SidebarLink class="sidebar-link" icon="" to="/">음료</SidebarLink>

    <span
        class="collapse-icon"
        :class="{ 'rotate-180': collapsed }"
        @click="toggleSidebar">
          <i class='fas fa-angle-double-left'>  〈〈  </i>
    </span>
  </div>

</template>

<script>
import { collapsed, toggleSidebar, sidebarWidth } from '@/components/cart/Sidebar/state'
import SidebarLink from '@/components/cart/Sidebar/SidebarLink'
export default {
  name: 'Sidebar',
  props: {},
  components: { SidebarLink },
  setup () {
    return { collapsed, toggleSidebar, sidebarWidth }
  }
}
</script>

<style>
:root {
  --sidebar-bg-color: #e6f4ff;
  --sidebar-item-hover: #b2e2fd;
}
</style>

<style scoped>
.sidebar {
  color: black;
  background-color: var(--sidebar-bg-color);
  float: left;
  z-index: 1;
  height: 100%;
  left: 0;
  bottom: 0;
  padding-bottom: 100%;
  margin-right: 3%;
  transition: 0.3s ease;
  display: flex;
  flex-direction: column;
}
.sidebar h5{
  margin-left: 10%;
  margin-top: 15%;
  margin-bottom: 3%;
}
.sidebar-link{
  color: black;
}
.collapse-icon{
  position: absolute;
  top: 80% ;
  padding: 0.75em;
  color: black;
  transition: 0.2s linear;
}
.rotate-180 {
  transform: rotate(180deg);
  transition: 0.2s linear;
}
</style>