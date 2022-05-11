<template>
  <div id="content">
    <p>내게시글페이지</p>
  </div>
</template>

<script>
export default {
  name: "MyWritter"
}
</script>

<style scoped>

body {
  margin: 0;
  height: 100%;
  background-color: #E6E6FA;
}
#wrapper {
  position: relative;
  height: 100%;
}
#content {
  position: absolute;
  left: 50%;
  transform: translate(-50%);
  width: 560px;
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

