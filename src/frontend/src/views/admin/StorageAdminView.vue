<template>
  <div class="allBody">
    <table class="menu-bar">
      <tbody>
      <tr>
        <td>
          <p @click="linkStorage">storage</p>
        </td>
        <td>
          <p @click="linkManager">manager</p>
        </td>
      </tr>
      </tbody>
    </table>
  </div>

  <div>
    <router-view></router-view>
  </div>
</template>

<script>
import store from '@/store';

export default {
  name: "StorageService",
  mounted() {
    if(store.getters.getLoginState.stateCode != '5'){
      this.$router.push('/')
      alert("관리자가 아닙니다.")
    }
  },
  methods:{
    linkStorage(){
      this.$router.push({name:'storage'})
    },
    linkManager(){
      this.$router.push({name:'manager'})
    }
  }

}
</script>

<style scoped>
.allBody{
  width: 84%;
  margin-left: 8%;
  margin-right: 8%;
  margin-top: 1%;
  align-content: center;
}
table.menu-bar {
  border-collapse: collapse;
  width: 100%;
}
th, td {
  padding: 10px;
  border-bottom: 1px solid #CD5C5C;
}
tr:hover { background-color: #F5F5F5; }
</style>
