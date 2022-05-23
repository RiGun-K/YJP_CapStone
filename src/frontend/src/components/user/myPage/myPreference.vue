<template>
  <div class="divBody">
    <div class="btnOuter" v-for="(obj, index) in aaa">
      <div class="labelDiv">
        <label style="margin-right: 20px"><h1>태그</h1></label>
        <label @click="viewCheck(index)">보이기</label>
      </div>
      <div v-for="(obj2, index2) in aaa[index]" class="btnDiv"  v-show="checkList[index]">
        <button class="btn" @click="chBack(index, index2)">{{obj2.a}}</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "myPreference",
  data(){
    return{
      allList:[],
      viewList:[],
      checkList:[],
      aaa:[[{a:'aa', b:1 },{a:'aa1', b:1 },{a:'aa2', b:0 }],
           [{a:'aa', b:0  },{a:'aa1', b:1 },{a:'aa2', b:0 },{a:'aa3', b:1 }],
           [{a:'aa', b:0  },{a:'aa1', b:1 },{a:'aa2', b:0 },{a:'aa3', b:1 }, {a:'aa', b:0  }, {a:'aa', b:0  }, {a:'aa', b:0  }, {a:'aa', b:0  }],
           [{a:'aa', b:0  },{a:'aa1', b:1 },{a:'aa2', b:0 },{a:'aa3', b:1 }]]
    }
  },
  created() {
    for (var i = 0; i < this.aaa.length; i++){
      this.checkList[i] = true;
    }
  },
  mounted() {
    var btn = document.getElementsByClassName("btn")
    var index = 0;
    for (var i = 0; i < this.aaa.length; i++){
      for(var y = 0; y < this.aaa[i].length; y++){
        if(this.aaa[i][y].b==0){
          btn[index].classList.add("clicked")
        }
        index++;
      }
    }
  },
  methods:{
    chBack(index, index2){
      var btn = document.getElementsByClassName("btn")
      var i = index2;
      for(var a = 0; a < index; a++){
        i += this.aaa[a].length
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
</style>
