<template>
  <img :src="backImg" class="backImg">
  <div id="content">
    <br>
    <h2>판매자 정보</h2>
    <br>
    <div>
      <h3 class="join_title">
        <label for="id">사업자 코드</label>
      </h3>
      <input type="text" v-model="company.ccode" class="form-control" placeholder="사업자id 입력" aria-label="Recipient's username" aria-describedby="button-addon2"
      readonly>
      <h3 class="join_title">
        <label for="id">회사명</label>
      </h3>
      <input type="text" v-model="company.cname" id="id" placeholder="회사명 입력"
             readonly class="form-control" maxlength="20">
    </div>

    <div>
      <h3 class="join_title">
        <label for="pswd1" >CEO</label>
      </h3>
      <input type="text" v-model="company.ceo" id="id" placeholder="필수입력"
             readonly class="form-control" maxlength="20">
    </div>
    <div>
      <h3 class="join_title">
        <label for="pswd2">주소</label>
      </h3>
      <span class="input-group mb-3">
        <input type="text" v-model="company.czadd" class="form-control" placeholder="우편주소 입력" aria-label="Recipient's username" aria-describedby="button-addon2" readonly>
        </span>

    </div>
    <div>
        <span class="input-group mb-3">
                        <input type="text" v-model="company.cradd" id="email" class="form-control" maxlength="100" placeholder="도로명입력" readonly>
        </span>
    </div>
    <div>
        <span class="input-group mb-3">
                        <input type="text" v-model="company.cadd" id="email"
                               readonly class="form-control" maxlength="100" placeholder="상세주소">
        </span>
      <span class="error_next_box">상세주소를 다시 확인해주세요.</span>
    </div>

    <div>
      <h3 class="join_title">
        <label for="email">홈페이지</label>
      </h3>
      <span class="input-group mb-3">
                        <input type="text" v-model="company.chp"
                               readonly id="email" class="form-control" maxlength="100" placeholder="필수입력">
                    </span>
      <span class="error_next_box">홈페이지 주소를 다시 확인해주세요.</span>
    </div>

    <div>
      <h3 class="join_title">
        <label for="phoneNo">전화번호</label>
      </h3>
      <span class="input-group mb-3">
                        <input type="tel" v-model="company.cph"
                               readonly id="mobile" class="form-control" maxlength="11" placeholder="전화번호 입력"
                               oninput="javascript: this.value = this.value.replace(/[^0-9]/g, '');">
                    </span>
      <span class="error_next_box"></span>
    </div>
    <div>
      <h3 class="join_title">
        <label for="email">이메일</label>
      </h3>
      <span class="input-group mb-3">
          <input type="text" v-model="company.cemail" class="form-control" placeholder="이메일 입력" aria-label="Recipient's username"
                 aria-describedby="button-addon2" :disabled="emailAuthBoolean" readonly>
        </span>
    </div>
    <div class="btn_area">
      <button type="button" @click="signPost" id="btnJoin">
        <span>돌아가기</span>
      </button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import store from "@/store";

export default {
  name: "MyCompany",
  data(){
    return{
      company:[],
      backImg:require("@/assets/camp1.jpg")
    }
  },methods: {
    signPost() {
      this.$router.push("ProductMain")
    }
  },
  created() {
    axios.post("/api/myCompanySearch",{
      MID:store.getters.getLoginState.mcode
    }).then((res)=>{
      this.company = res.data
    }).catch((err)=>{
      console.log(err)
    })
  }
}
</script>

<style scoped>
.backImg{
  margin-top: 1px;
  width: 100%;
  height: 150%;
  position: absolute;
  z-index: 1;
}
#content {
  margin-right: 25%;
  margin-left: 25%;
  margin-top: 2%;
  margin-bottom: 5%;
  width: 50%;
  height: 143%;
  padding: 30px;
  z-index: 2;
  position: relative;
  background: white;
}
/* 입력폼 */
h3 {
  margin: 19px 0 8px;
  font-size: 14px;
  font-weight: 700;
}
.box {
  display: block;
  width: 100%;
  height: 51px;
  border: solid 1px #dadada;
  padding: 10px 14px;
  box-sizing: border-box;
  background: #fff;
  position: relative;
}
.int {
  display: block;
  position: relative;
  width: 100%;
  height: 29px;
  border: none;
  background: #fff;
  font-size: 15px;
}
.box.int_id {
  padding-right: 110px;
}
.box.int_pass {
  padding-right: 40px;
}
.box.int_pass_check {
  padding-right: 40px;
}
select {
  width: 100%;
  height: 29px;
  font-size: 15px;
  background-size: 20px 8px;
  -webkit-appearance: none;
  display: inline-block;
  text-align: start;
  border: none;
  cursor: default;
}
.error_next_box {
  margin-top: 9px;
  font-size: 12px;
  color: red;
  display: none;
}
#alertTxt {
  position: absolute;
  top: 19px;
  right: 38px;
  font-size: 12px;
  color: red;
  display: none;
}
/* 버튼 */
.btn_area {
  margin: 30px 0 91px;
}
#btnJoin {
  width: 100%;
  padding: 21px 0 17px;
  border: 0;
  cursor: pointer;
  color: white;
  background-color: #52a3ef;
  font-size: 20px;
  font-weight: 400;
}
</style>
