<template>
  <div class="bodyDiv">
    <div>
      <textarea readonly v-model="Lorem"
              rows="20" cols="40" style="resize: none; padding: 10px">
      </textarea>
    </div>
    <div>
      <input type="radio" name="rg1" @change="radioChange()" class="radioBtn">동의합니다
      <input type="radio" name="rg1" @change="falseChange()" class="radioBtn">동의하지 않습니다.
    </div>
    <div>
      <button @click="next" class="btn" :disabled="btnCheck" >다음</button>
      <button @click="preExit" class="btn">취소</button>
    </div>
  </div>
</template>

<script>
export default {
  //eslint-disable-next-line
  name: "Agreement",
  data(){
    return{
      btnCheck : true,
      radioCheck : null,
      address:null,
      Lorem:'Where does it come from?\n' +
          'Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of "de Finibus Bonorum et Malorum" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, "Lorem ipsum dolor sit amet..", comes from a line in section 1.10.32.\n' +
          'The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from "de Finibus Bonorum et Malorum" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.'
    }
  },
  methods:{
    next(){
      this.$router.push("signup")
    },
    preExit(){
      this.$router.push("/")
    },
    radioChange(){
      this.btnCheck = !this.btnCheck
    },
    falseChange(){
      this.btnCheck = true
    }
  }
}
</script>

<style scoped>
.bodyDiv{
  margin-top: 2%;
  margin-bottom: 5%;
  margin-left: 25%;
  margin-right: 25%;
  width: 50%;
  text-align: center;
}
.radioBtn{
  margin-left: 5px;
  margin-right: 5px;
}
.btn{
  margin-left: 5px;
  margin-right: 5px;
  background: #E6E6FA;
  color: black;
  width: 80px;
  border: 1px solid black;
}
.btn:hover{
  background: black;
  color: white;
  border: 1px solid red;
}
.btn:disabled{
  background: gray;
  color: white;
}
</style>
