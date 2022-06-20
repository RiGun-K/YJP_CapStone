<template>
  <div class="divBody">
    <div><h5>보관소추가</h5></div>
    <div class="bodydiv">
      <label class="labelWidth">보관소명</label>
      <input type="text" v-model="form.storageName" placeholder="보관소명">
    </div>
    <div class="bodydiv">
      <label class="labelWidth">우편주소</label>
      <input type="text" v-model="form.storageZipcode" placeholder="우편번호" readonly>
      <button @click="showApi()" class="addressBtn">검색</button>
    </div>
    <div class="bodydiv">
      <label class="labelWidth">도로명주소</label>
      <input type="text" v-model="form.storageAddress" placeholder="도로명주소" readonly>
    </div>
    <div class="bodydiv">
      <label class="labelWidth">상세주소</label>
      <input type="text" v-model="form.storageDetailAddress" placeholder="상세주소">
    </div>
    <div class="bodydiv">
      <label class="labelWidth">전화번호</label>
      <input type="text" v-model="form.storageTel" placeholder="전화번호">
    </div>
    <div class="bodydiv">
      <form>
        <label class="labelWidth">이미지</label>
        <input type="file"
               id="file"
               @change="handleImage"
               enctype="multipart/form-data"
               aria-describedby="inputGroupFileAddon04"
               aria-label="Upload"
               placeholder="보관소 이미지 파일을 업로드하세요."
               multiple
               accept="image/*"
               drop-placeholder="Drop file here..." >
        <div id="image_container"/>
      </form>
      <button @click="clearFile()">파일 취소</button>
    </div>
    <div class="storage-box" v-if="boxC">
      <p>보관함 추가</p>
      <div class="storage-box-add">
        <table>
          <tbody>
          <tr>
            <td>small</td>
            <td>medium</td>
            <td>large</td>
          </tr>
          <tr>
            <td>
              <button @click="subS" class="storage-add-sub">─</button>
              {{ box.small }}
              <button @click="addS" class="storage-add-sub">+</button>
            </td>
            <td>
              <button @click="subM" class="storage-add-sub">─</button>
              {{ box.medium }}
              <button @click="addM" class="storage-add-sub">+</button>
            </td>
            <td>
              <button @click="subL" class="storage-add-sub">─</button>
              {{ box.large }}
              <button @click="addL" class="storage-add-sub">+</button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
      <div class="storageBtnBody">
        <button class="storageBtn" @click="postStorage()">ADD</button>
      </div>
    </div>
    <div class="btnDivBody">
      <button class="storage-add-btn" @click="next()" v-if="!boxC">NEXT</button>
      <button class="homeBtn" @click="this.$emit('back')" >돌아가기</button>
    </div>
    <div id="map"></div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "StorageAdd",
  data() {
    return {
      boxC:false,
      box: {
        small: 0,
        medium: 0,
        large: 0
      },
      form: {
        storageName: '',
        storageZipcode: '',
        storageAddress: '',
        storageDetailAddress: '',
        storageTel:'',
        latitude:'',
        longitude:'',
      },
      a : {},
      errorCheck: false,
      addStorage: false,
      file: '',
      map: null
    }
  },
  mounted() {
    this.a = undefined || {}
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
          "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=8a536388b1cc33e00ae2dbf18b8509ba&libraries=services";
      document.head.appendChild(script);
    }
  },
  methods: {
    next(){
      if (this.form.storageName == ''){
        alert('보관소명을 입력하세요')
      }else if (this.form.storageZipcode == ''){
        alert('주소를 입력하세요')
      }else if (this.form.storageAddress == ''){
        alert('주소를 입력하세요')
      }else{
        this.boxC = true
      }
      this.geo(this.form.storageAddress)
    },
    clearFile(){
      this.file = ''
    },
    geo(index){
      // 주소-좌표 변환 객체를 생성합니다
      var geocoder = new kakao.maps.services.Geocoder();
      // 주소로 좌표를 검색합니다
      geocoder.addressSearch(index,
          (result, status) => {
            // 정상적으로 검색이 완료됐으면
            if (status === kakao.maps.services.Status.OK) {
              this.a.latitude = result[0].x
              this.a.longitude = result[0].y
            }
          }
      );
    },
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(35.89527721605076, 128.62277217540984),
        level: 1,
      };
      this.map = new kakao.maps.Map(container, options);
    },
    showApi() {
      new window.daum.Postcode({
        oncomplete: (data) => {
          this.form.storageZipcode = data.zonecode
          this.form.storageAddress = data.roadAddress
        }
      }).open({popupKey: '주소검색'})
      this.geo(this.form.storageAddress)
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
      }
      else {
        return false
      }

    },
    subS() {
      if (this.box.small == 0) {
        this.box.small = 0
      } else {
        this.box.small--
      }
    },
    subM() {
      if (this.box.medium == 0) {
        this.box.medium = 0
      } else {
        this.box.medium--
      }
    },
    subL() {
      if (this.box.large == 0) {
        this.box.large = 0
      } else {
        this.box.large--
      }

    },
    addS() {
      this.box.small++
    },
    addM() {
      this.box.medium++
    },
    addL() {
      this.box.large++
    },
    inputCheck() {

      if (this.box.small == 0 && this.box.medium == 0 && this.box.large == 0) {
        alert('보관함 추가하세요')
        return
      }
      if (!this.form.storageName) {
        alert('보관소 명을 입력하세요')
        return
      } else if (!this.form.storageZipcode) {
        alert('보관소 우편주소을 입력하세요')
        return
      } else if (!this.form.storageAddress) {
        alert('보관소 주소을 입력하세요')
        return
      } else {
        this.errorCheck = true
      }
    },
    clearInput() {
      this.form.storageName = ''
      this.form.storageZipcode = ''
      this.form.storageAddress = ''

    },
    claerBox() {
      this.box.small = 0
      this.box.medium = 0
      this.box.large = 0
    },
    postStorage() {
      this.inputCheck()
      this.form.latitude = this.a.latitude
      this.form.longitude = this.a.longitude
      const data = {
        box: this.box,
        storage: this.form
      }
      const formData = new FormData()
      formData.append('small',this.box.small)
      formData.append('medium',this.box.medium)
      formData.append('large',this.box.large)
      formData.append('storageName',this.form.storageName)
      formData.append('storageZipcode',this.form.storageZipcode)
      formData.append('storageAddress',this.form.storageAddress)
      formData.append('storageDetailAddress',this.form.storageDetailAddress)
      formData.append('latitude',this.form.latitude)
      formData.append('longitude',this.form.longitude)
      formData.append('file',this.file)


      if (this.errorCheck) {
        axios.post('/api/postStorage', formData,{headers: {'Content-Type': 'multipart/form-data'}})
            .then((res) => {
              console.log(this.form)
              console.log(res.data.result)
              if (res.data.result === 'ok') {
                alert('추가되었습니다')
                this.clearInput()
                this.addStorage = true
                this.claerBox()
                this.addStorage = false
                alert('보관소와 보관함이 추가되었습니다.')
                this.$emit('back')
              } else {
                console.log('중복')
              }
            }).catch((err) => {
          console.log(err)
        })
      }
    },
  }
}
</script>

<style scoped>
.divBody{
  margin-left: 25%;
  margin-top: 2%;
  text-align: left;
  width: 100%;
}

.labelWidth{
  width: 90px;
}
input{
  width: 150px;
}

.bodydiv{
  width: 100%;
  margin-top: 1%;
}

.addressBtn{
  width: 50px;
  height: 100%;
  margin-left: 1%;
  text-align: center;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
  position: relative;
}
.storageBtn{
  margin-top: 1%;
  padding: 1%;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
}

.storageBtnBody{
  width: 100%;
  text-align: right;
}
.btnDivBody{
  margin-top: 10px;
  width: 50%;
  text-align: right;
}

.storage-add-btn {
  width: 100px;
  height: 30px;
  text-align: center;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
}
.homeBtn{
  margin-left: 10px;
  width: 100px;
  height: 30px;
  text-align: center;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
}

.storage-add-btn:hover {
  color: white;
  background-color: #b2e2fd;
}

.storage-box {
  margin-top: 2%;
  border: solid 3px #000a69;
  width: 50%;
  justify-content: center;
  align-items: center;
  padding-bottom: 1%;
  padding-left: 1%;
  padding-right: 1%;
  padding-top: 1%;
}
.storage-add-sub {
  background-color: #ffffff;
  color: #00a3de;
  border-color: #00a3de;
  border-radius: 1em;
}

.storage-add-sub:hover {
  color: white;
  background-color: #b2e2fd;
}

</style>
