<template>
  <div>
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
      console.log(this.id);
      axios.get('/api/teammyList' + this.id)
          .then((res) => {
            console.log("내가 받은 데이터는", res.data)
            this.List = res.data;
            this.ch = this.check()
            console.log(this.List.teamwriter_code)
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
    updateData(){

    },
    deleteData(){
      if (confirm("삭제하시겠습니까?")) {
        axios.delete('/api/teamdeleteList'+ this.id)
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