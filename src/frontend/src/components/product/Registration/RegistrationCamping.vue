<template>
  <ProductPage></ProductPage>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
  <br>
  <h1>캠핑장 등록 페이지 입니다.</h1>
  <br>
  <div id="wrapper">
    <div id="content">
      <form v-on:submit.prevent="formSubmit" method="post" enctype="multipart/form-data">

      <div>
        <h3 class="join_title">
          <label for="id">상품분류</label>
        </h3>
        <select v-model="infoterid" placeholder="메뉴명을 입력하세요." class="form-select" aria-label="Default select example">
          <option v-for="(option, index) in options" :key="index" :value="option">
            {{option.text}}
          </option>
        </select>
        <div class="mt-3">선택유형 : <strong>{{ infoterid.text }}</strong></div>

        <br>
        <h3 class="join_title">
          <label for="id">지역분류</label>
        </h3>
        <select v-model="bigPick" @change="bigCheck(bigPick)">
          <option value="0">전국</option>
          <option v-for="big in bigRound" :value="big.areaId">{{ big.areaName }}</option>
        </select>
        <br>
        <select v-model="smallPick">
          <option value="0">전체</option>
          <option v-for="small in smallRound" :value="small.areaId">{{ small.areaName }}</option>
        </select>
<!--        <select v-model="areaid" placeholder="지역명을 입력하세요." class="form-select" aria-label="Default select example">-->
<!--          <option v-for="(area, index) in ares" :key="index" :value="area">-->
<!--            {{area.text}}-->
<!--          </option>-->
<!--        </select>-->
<!--        <div class="mt-3">선택유형 : <strong>{{ areaid.text }}</strong></div>-->

        <br>
        <span class="error_next_box"></span>
        <h3 class="join_title">
          <label for="id">캠핑장 명</label>
        </h3>
        <span class="box int_id">
                        <input type="text" v-model="campingName" id="id" placeholder="캠핑장 명을 입력하세요" class="int" maxlength="20">
                    </span>
        <span class="error_next_box"></span>
      </div>

        <br>
      <div>
        <h3 class="join_title">
          <label for="pswd1" >캠핑장 정보</label>
        </h3>

        <span class="box int_id">
          <textarea type="text" v-model="campingInfo" id="id" placeholder="캠핑장 정보를 입력하세요" class="int" maxlength="100"></textarea>
                    </span>
        <span class="error_next_box"></span>

      </div>

        <br>
        <div>
          <h3 class="join_title">
            <label for="email">객실 수 </label>
          </h3>
          <span class="box int_email">
            <input type="text" v-model="campingDetailState" id="email" class="int" maxlength="100" placeholder="객실 수를 입력하세요">
          </span>
        </div>

        <br>
      <div>
        <h3 class="join_title">
          <label for="pswd2">주소</label>
        </h3>
        <span class="input-group mb-3">
        <input type="text" v-model="postalAddress" class="form-control" placeholder="우편주소 입력" aria-label="Recipient's username" aria-describedby="button-addon2" readonly>
        <button class="btn btn-outline-secondary" type="button" id="button-addon2" @click="zcGet">우편주소검색</button>
        </span>

      </div>
      <div>
        <span class="input-group mb-3">
                        <input type="text" v-model="address" id="email" class="form-control" maxlength="100" placeholder="도로명입력" readonly>
        </span>
      </div>
      <div>
        <span class="input-group mb-3">
                        <input type="text" v-model="detailAddress" id="email" class="form-control" maxlength="100" placeholder="상세주소">
        </span>
        <span class="error_next_box">상세주소를 다시 확인해주세요.</span>
      </div>

        <br>
        <h3 class="join_title">
          <label for="pswd2">썸네일 이미지 등록</label>
        </h3>
        <div class="input-group">
          <form>
            <input type="file"
                   id="file"
                   @change="handleImage"
                   enctype="multipart/form-data"
                   aria-describedby="inputGroupFileAddon04"
                   aria-label="Upload"
                   placeholder="상품을 설명할 이미지 파일을 업로드하세요."
                   multiple
                   accept="image/*"
                   drop-placeholder="Drop file here..." >
            <div id="image_container"/>
          </form>
        </div>
        <br>

        <div v-if="stateCheck">
          <h3 class="join_title">
            <label for="pswd2">내부 이미지 등록</label>
          </h3>
          <div class="input-group">
            <form>
              <input multiple
                     type="file"
                     id="files"
                     name="profile_files"
                     ref="serveyImage"
                     @change="handleImages"
                     enctype="multipart/form-data"
                     aria-describedby="inputGroupFileAddon04"
                     aria-label="Upload"
                     placeholder="상품을 설명할 이미지 파일을 업로드하세요."
                     accept="image/*"
                     drop-placeholder="Drop file here..."
              >
              <div id="image_container"/>
            </form>
          </div>
        </div>

      <br>
      <div class="btn_area">
        <button type="submit" class="btn btn-outline-primary" @click="BuySubmit" id="btnJoin">
          <span>등록하기</span>
        </button>

      </div>
      </form>
    </div>
  </div>
</template>

<script>
import ProductPage from "@/components/product/ProductPage";
import axios from 'axios'
import store from "@/store";


export default {
  name: "RegistrationBuy",
  components: { ProductPage },
  created() {
    axios.get('/api/campingRound')
        .then(res => {
          console.log(res.data)
          this.bigRound = res.data
        })
        .catch(err => {
          console.log(err)
        })
  },
  data() {
    return {
      infoterid: '',
      areaid: '',
      campingName: '',
      campingInfo: '',
      campingDetailState: '',
      file: '',
      postalAddress: '',
      address: '',
      // 직접치는 상세주소
      detailAddress: '',

      mid: store.getters.getLoginState.loginState,

      id: '',
      myContent: [],
      camlist: [],
      files: '',
      stateCheck: false,


      bigRound: [],
      smallRound: [],
      bigPick: 0,
      smallPick: 0,


      options: [
        { value: '1', text: '오토캠핑'},
        { value: '2', text: '카라반'},
        { value: '3', text: '글램핑'},
        { value: '4', text: '펜션'},
        { value: '5', text: '차박'},
        { value: '6', text: '당일피크닉'},
        { value: '7', text: '기타'},
      ],
    }
  },
  methods: {
    bigCheck(index) {
      if (index == '0') {
        this.smallRound = []
      } else {
        axios.get('/api/campingSmallRound/' + index)
            .then(res => {
              console.log(res.data)
              this.smallRound = res.data
              this.smallPick = 0
            })
            .catch(err => {
              console.log(err)
            })
      }

    },
    handleImage(e) {
      this.file = e.target.files[0];
      let self = this;
      if(e.target.files[0]) {
        // 파일 읽는 라이브러리
        const reader = new FileReader();

        // 파일 읽기가 완료되는 시점
        reader.addEventListener('load', function(e1){
          // 완료되는 시점!!!!!!!!!!!!!!!
          self.imgsrc = e1.target.result;
          // 지금 reader 안에서는 this 못 씀. 그래서 35줄에 this를 self로 변수지정함


          let img = document.createElement("img");
          img.setAttribute("src", e1.target.result);
          document.querySelector("div#image_container").appendChild(img);
        });


        // 파일 읽기 시작
        reader.readAsDataURL(e.target.files[0]);
        this.stateCheck = true;
      }
      else {
        return false
      }

    },

    handleImages(e) {
      this.files = e.target.files
      for(let i = 0; i < e.target.files.length; i++) {
        const reader = new FileReader();
        reader.readAsDataURL(this.files[i]);
        // 파일 읽기가 완료되는 시점
        reader.addEventListener('load', function(e1){
          // 완료되는 시점!!!!!!!!!!!!!!!
          self.imgsrc = e1.target.result;
          // 지금 reader 안에서는 this 못 씀. 그래서 35줄에 this를 self로 변수지정함


          let img = document.createElement("img");
          img.setAttribute("src", e1.target.result);
          document.querySelector("div#image_container").appendChild(img);


        });
      }

    },

    zcGet() {
      console.log(this.smallPick);
      new window.daum.Postcode({
        oncomplete: (data) => {
          this.postalAddress = data.zonecode;
          this.address = data.roadAddress;
        }
      }).open({popupKey: '주소검색'})
    },
    BuySubmit: function () {

      const formData = new FormData();

      // const photoFile = document.getElementById("file_load");

      formData.append('infoterId', this.infoterid.value);
      // formData.append('areaId', this.areaid.value);
      formData.append('areaId', this.smallPick);
      formData.append('campingName', this.campingName);
      formData.append('campingInfo', this.campingInfo);
      formData.append('campingDetailState', this.campingDetailState);
      formData.append('postalAddress', this.postalAddress);
      formData.append('address', this.address);
      formData.append('detailAddress', this.detailAddress);
      formData.append('file', this.file);
      formData.append('mid', this.mid);



        axios.post('/api/Camping_Signup', formData, {headers: {'Content-Type': 'multipart/form-data'}})
            .then(res => {
              console.log("성공" + res);
              this.camlist = res.data;
              this.campingId = this.camlist.campingId;

              // 구매 상품 등록처리 후
              // 다중 이미지 업로드
              const formDatas = new FormData();

              for (let i = 0; i < this.files.length; i++) {
                formDatas.append('files', this.files[i]);
              }

              formDatas.append('campingId', this.campingId);

              console.log(this.campingId);


              if (confirm("캠핑장을 등록하시겠습니까?")) {
                axios.post('/api/Camping_Signup_Files/', formDatas, {headers: {'Content-Type': 'multipart/form-data'}})
                    .then(res => {
                      console.log("성공" + res);
                      alert("캠핑장이 등록되었습니다. 객실을 등록해주세요!");
                      this.$router.push({
                        path: `/RegistrationCampingDetail/${this.campingName}`
                      });
                    })
                    .catch(function (error) {
                      console.log("에러" + error);
                      alert("상품이 등록되지않았습니다.");
                    })
              }

            })
            .catch(function (error) {
              console.log("에러" + error);
              alert("캠핑장이 등록되지않았습니다.");
            })


    },
  }
}
</script>

<style scoped>
h1{
  text-align: center;
}
textarea {
  width: 100%;
  height: 6.25em;
  border: none;
  resize: none;
}
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
  width: 460px;
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

#btnJoin {
  margin: auto;
  display: block;
}
</style>