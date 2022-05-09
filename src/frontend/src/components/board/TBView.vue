<template>
  <div>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
  <br><H2 style="font-weight: bold">글 상세보기</H2>
  <br>
  <div class="AddWrap">
    <form>
      <table class="tbAdd">
        <colgroup>
          <col width="15%" />
          <col width="*" />
        </colgroup>
        <tr>
          <th>글쓴이</th>
          <td>{{List.teamMaster.teamMaster}}</td>
        </tr>
        <tr>
          <th>제목</th>
          <td>{{ List.noticetitle }}</td>
        </tr>
        <tr>
          <th>내용</th>
          <td>{{ List.noticecontent }}</td>
        </tr>

      </table>
    </form>
  </div>
  </div>

  <div id="foot_box"></div>

  <div class="btnWrap">
    <button @click="list" class="btn" style="float: left;">목록</button>

    <button @click="updateData(List)" class="btn" style="float: left;" v-if="ch">수정</button>
    <button @click="deleteData" class="btn" style="float: left;" v-if="ch">삭제</button>
  </div>

</template>

<script>

import store from "@/store";
import axios from "axios";

export  default  {
  name: 'TBView',
  data() {
    return {
      teamMaster: '',
      teamwriter_code: '',
      noticetitle: '',
      noticecontent: '',
      List: [],
      ch:true
    }
  },
  created() {
    this.DataList();
  },

  methods: {
    check(){
      if(store.getters.getLoginState.loginState == this.List.teamMaster.teamMaster){
        return true
      }else{
        return false
      }
    },

    DataList() {
      this.id = this.$route.params.teamwriter_code;
      console.log(this.id);
      axios.get('/api/teammyList/' + this.id)
          .then((res) => {
            console.log("내가 받은 데이터는", res.data)
            this.List = res.data;
          })
          .catch(error => {
            console.log("에러" + error)
          })
    },
    list(){
      this.$router.push({
        path: '/teamboard'
      })
    },
    updateData(List) {
      console.log(List.teamMaster.teamMaster, List.noticetitle, List.noticecontent);
      this.$router.push({
        path: `/tbupdate/${List.teamwriter_code}`,
        query: {
          teamwriter_code: List.teamwriter_code,
          noticetitle: List.noticetitle,
          noticecontent: List.noticecontent

        }
      })


    },
    deleteData(){
      if (confirm("삭제하시겠습니까?")) {
        axios.delete('/api/teamdeleteList/'+ this.id)
        .then((res) => {
          console.log("삭제되었습니다.", res)
          alert("게시글이 삭제되었습니다.")
          this.$router.push({
            path: '/teamboard'
          })
        })
        .catch((err) => {
          console.log(err)
        })
      }

    }

  }
}


</script>
<style scoped>
.tbAdd{border-top:1px solid #888;}
.tbAdd th, .tbAdd td{border-bottom:1px solid #eee; padding:5px 0; }
.tbAdd td{padding:10px 10px; box-sizing:border-box; text-align:left;}
.tbAdd td.txt_cont{height:300px; vertical-align:top;}
.btnWrap{text-align:center; margin:20px 0 0 0;}
.btnWrap a{margin:0 10px;}
.btnAdd {background:#43b984}
.btnDelete{background:#f00;}

.btn {
  margin: 10px;
}
.dap_ins {
  margin-top:50px;
}
.re_bt {
  position: absolute;
  width:100px;
  height:56px;
  font-size:16px;
  margin-left: 10px;
}

#re_content {
  width:700px;
  height: 56px;
}

#foot_box {
  height: 50px;
}

</style>