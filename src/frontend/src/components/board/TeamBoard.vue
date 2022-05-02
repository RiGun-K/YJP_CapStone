<template>
  <h2 style="font-weight: bold">공지사항</h2>

<br>

<div class="listWrap">
<table class="tbList">
  <tr>
    <td style="font-size:20px; color: green;">제목</td>
    <td style="font-size:20px; color: green;">내용</td>
  </tr>

  <tr v-for="item in list" :key="item.id" :item="item" @click="detail(item)">
    <td>{{item.noticetitle}}</td>
    <td>{{item.noticecontent}}</td>
    <td>{{item.teamMaster.teamMaster}}</td>
  </tr>


</table>
</div>
</template>

<script>

export default {
  name: 'TeamBoard',
  created() {
    this.fetchData()
  },
  data() {
    return {
      list: [],
      item: '',
    }
  },
  methods: {
    detail(item) {
      this.$router.push({
        path:'TBCreate'
      })
    }

  }
}
</script>


<style>

#divPaging {
clear:both;
margin:0 auto;
width:220px;
height:50px;
}

#divPaging > div {
float:left;
width: 30px;
margin:0 auto;
text-align:center;
}

.tbList th{border-top:1px solid #888;}
.tbList th, .tbList td{border-bottom:1px solid #eee; padding:5px 0;}
.tbList td.txt_left{text-align:left;}
.btnWrap{text-align:center; margin:20px 0 0 0;}
.btnWrap a{margin:0 10px;}
.btnAdd {background:#43b984}
.btnDelete{background:#f00;}

.searchWrap{border:1px solid #888; border-radius:5px; text-align:center; padding:20px 0; margin-bottom:40px;}
.searchWrap input{width:60%; height:36px; border-radius:3px; padding:0 10px; border:1px solid #888;}
.searchWrap .btnSearch{display:inline-block; margin-left:10px;}



</style>