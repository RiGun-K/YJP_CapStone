<template>
  <div class="divBody">
    <div class="btnOuter" v-for="(obj, index) in largeList">
      <div class="labelDiv">
        <label style="margin-right: 20px"><h1>{{obj.kindname}}</h1></label>
        <label @click="viewCheck(index)">보이기</label>
      </div>
      <div v-for="(obj2, index2) in smallList[index]" class="btnDiv"  v-show="checkList[index]">
        <button class="btn" @click="chBack(index, index2)">{{obj2.kindname}}</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "myPreference",
  data(){
    return{
      largeList:[],
      smallList:[],
      checkList:[],
      flag:false
    }
  },
  created() {
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

          setTimeout(()=>{
            this.flag = true
          },100)
        }).catch((err)=>{
      console.log(err)
    })
  },
  mounted() {
  },
  methods:{
    chBack(index, index2){
      var btn = document.getElementsByClassName("btn")
      console.log(btn[0])
      var i = index2;

      if(index != 0){
        for(var x = 0; x < index; x++){
          i += this.smallList[x].length
        }
      }
      if(btn[i].classList.item(1) == "clicked"){
        btn[i].classList.remove("clicked")
      }else{
        btn[i].classList.add("clicked")
      }
    },
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
      var list = ["소분류11", "소분류12"]
      var check = false
      var index = 0
      var btn = document.getElementsByClassName("btn")
      console.log(this.smallList)
      console.log(this.smallList.length)

      for(var x = 0; x < 2; x++){
        for(var y = 0; y < this.smallList.length; y++){
          for(var z = 0; z < this.smallList[y].length; z++){
            if(this.smallList[y][z].kindname == list[x]){
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
.divBody{
  margin-right: 25%;
  margin-left: 25%;
  margin-top: 1%;
  text-align: center;
  width: 50%;
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
  width: 60px;
}
.btn:hover{
  background: #000a69;
  color: white;
}
.btn:active{
}
.btn:focus{
  background: #34445c;
  color: red;
}
.clicked{
  background: red;
}
h1{
  color: #5f8c98;
}
</style>
