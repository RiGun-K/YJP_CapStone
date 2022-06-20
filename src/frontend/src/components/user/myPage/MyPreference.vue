<template>
  <img :src="backImg" class="backImg">
  <div class="divBody">
    <div class="btnOuter" v-for="(obj, index) in largeList">
      <div class="labelDiv">
        <label style="margin-right: 20px"><h1>{{obj.kindname}}</h1></label>
        <label @click="viewCheck(index)" v-show="checkList[index]">숨기기</label>
        <label @click="viewCheck(index)" v-show="!checkList[index]">보이기</label>
      </div>
      <div v-for="(obj2, index2) in smallList[index]" class="btnDiv"  v-show="checkList[index]">
        <button class="btn" @click="chBack(index, index2)">{{obj2.kindname}}</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import store from "@/store";

export default {
  name: "myPreference",
  data(){
    return{
      largeList:[], //대분류
      smallList:[], //소분류
      checkList:[], //대분류 보이기-숨기기
      userList:[],  //유저 선호도
      flag:false,    //초기화 플래그 변수
      backImg:require("@/assets/camp1.jpg")
    }
  },
  created() {
    //전체 선호도를 받아온다
    axios.get("/api/allKindGet")
        .then((res)=>{
          for(var x = 0; x < res.data.length; x++){
            if(res.data[x].parentkind == null) {
              this.largeList.push(res.data[x])
              this.checkList.push(true);
            }
          }

          for(var x = 0; x < this.largeList.length; x++){
            var list = []
            for(var y = 0; y < res.data.length; y++){
              if(res.data[y].parentkind == null){
              }else if(this.largeList[x].kindname == res.data[y].parentkind.kindname){
                list.push(res.data[y])
              }
            }
            this.smallList.push(list)
          }
        }).catch((err)=>{
      console.log(err)
    })
    //유저의 전체 선호도를 받아온다
    axios.post("/api/allPreference",{
      mid:store.getters.getLoginState.mcode
    }).then((res)=>{
      this.userList = res.data
    }).catch((err)=>{
      console.log(err)
    })

    //플래그 실행 타이밍 조작
    setTimeout(()=>{
      this.flag = true
    },400)
  },
  methods:{
    chBack(index, index2){
      //버튼 색상 변경
      var btn = document.getElementsByClassName("btn")
      var i = index2;

      if(index != 0){
        for(var x = 0; x < index; x++){
          i += this.smallList[x].length
        }
      }
      //이미 선택한 버튼 선택 취소, 유저 선호도 목록에서 삭제
      if(btn[i].classList.item(1) == "clicked"){
        btn[i].classList.remove("clicked")
        axios.post("/api/removePreference",{
          mid:store.getters.getLoginState.mcode,
          kindName:this.smallList[index][index2].kindname
        })
      }
      //유저 선호도 선택, 추가
      else{
        btn[i].classList.add("clicked")
        axios.post("/api/addPreference",{
          mid:store.getters.getLoginState.mcode,
          kindName:this.smallList[index][index2].kindname
        }).then((res)=>{
          if(res.data){
            console.log("저장성공")
          }
        }).catch((err)=>{
          console.log(err)
        })
      }
    },
    //버튼 보이기-숨기기
    viewCheck(index){
      if(this.checkList[index]){
        this.checkList[index] = false
      }else{
        this.checkList[index] = true
      }
    }
  },
  watch:{
    flag(){
      var check = false
      var index = 0
      var btn = document.getElementsByClassName("btn")

      for(var x = 0; x < this.userList.length; x++){
        for(var y = 0; y < this.smallList.length; y++){
          for(var z = 0; z < this.smallList[y].length; z++){
            if(this.smallList[y][z].kindname == this.userList[x].kind.kindname){
              btn[index].classList.add("clicked")
              check = true
              index = 0
              break
            }
            index++
          }
          if(check){
            check = false
            break
          }
        }
      }
    }
  }
}
</script>

<style scoped>
.backImg{
  margin-top: 1px;
  width: 100%;
  height: 100%;
  position: absolute;
  z-index: 1;
}
.divBody{
  margin-right: 15%;
  margin-left: 15%;
  margin-top: 2%;
  margin-bottom: 5%;
  text-align: center;
  width: 70%;
  height: 93%;
  padding: 30px;
  z-index: 2;
  position: relative;
  background: white;
}
.btnDiv{
  display: inline;
}
.labelDiv{
  text-align: left;
}
.btnOuter{
  margin-bottom: 5px;
  display: block;
  border: 1px solid black;
  text-align: left;
}
.btn{
  margin-left: 10px;
  margin-right: 10px;
  margin-bottom: 10px;
  background: #b2e2fd;
  color: black;
  width: 100px;
}
.btn:hover{
  background: #000a69;
  color: white;
}
.clicked{
  background: black;
  color: white;
}
h1{
  color: #5f8c98;
}
</style>
