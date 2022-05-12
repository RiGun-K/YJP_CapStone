<template>
  <div class="storages-add">
    <!--    <div class="storage-add">-->

    <!--      <div class="form-floating mb-3">-->
    <!--        <input type="text" v-model="form.storageName" placeholder="보관소명" class="form-control" id="floatingName">-->
    <!--        <label for="floatingName">보관소명</label>-->
    <!--      </div>-->
    <!--      <div class="form-floating mb-3">-->
    <!--        <input type="text" v-model="form.storageZipcode" placeholder="우편번호" class="form-control" id="floatingZipcode">-->
    <!--        <label for="floatingZipcode">우편번호</label>-->
    <!--      </div>-->
    <!--      <div class="form-floating">-->
    <!--        <input type="text" v-model="form.storageAddress" placeholder="주소" class="form-control" id="floatingAddress">-->
    <!--        <label for="floatingAddress">주소</label>-->
    <!--      </div>-->
    <!--      <button class="storage-add-btn" @click="postStorage()">ADD</button>-->
    <!--    </div>-->

    <table>
      <tr>
        <td>보관소명</td>
        <td><input type="text" v-model="form.storageName" placeholder="보관소명"></td>
      </tr>
      <tr>
        <td>우편주소</td>
        <td>
          <input type="text" v-model="form.storageZipcode" placeholder="우편번호">
          <button @click="showApi()">우편번호 찾기</button>
        </td>
      </tr>
      <tr>
        <td>도로명주소</td>
        <td><input type="text" v-model="form.storageAddress" placeholder="주소"></td>
      </tr>
      <tr>
        <td>상세주소</td>
        <td><input type="text" v-model="form.storageDetailAddress" placeholder="주소"></td>
      </tr>
      <tr>
        <td>이미지</td>
        <td>
          <form>
            <input type="file"
                   id="file"
                   @change="handleImage"
                   enctype="multipart/form-data"
                   aria-describedby="inputGroupFileAddon04"
                   aria-label="Upload"
                   placeholder="상품을 설명할 이미지 파일을 업로드하세요."
                   drop-placeholder="Drop file here...">
          </form>
        </td>
      </tr>
    </table>
    <button class="storage-add-btn" @click="next()">NEXT</button>
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
      <button class="storage-add-btn" @click="postStorage()">ADD</button>
    </div>
  </div>
  <div id="map" ></div>
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

    },
    geo(index){
      // 주소-좌표 변환 객체를 생성합니다
      var geocoder = new kakao.maps.services.Geocoder();
      // 주소로 좌표를 검색합니다
      geocoder.addressSearch(index,
          (result, status) => {
            // 정상적으로 검색이 완료됐으면
            if (status === kakao.maps.services.Status.OK){
              this.a.latitude = result[0].y
              this.a.longitude = result[0].x
            }else{
              this.a.latitude = ''
              this.a.longitude = ''
            }
          }
      );
      console.log('this.a')
      console.log(this.a)
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
      if (e.target.files[0]) {
        // 파일 읽는 라이브러리
        const reader = new FileReader();

        // 파일 읽기가 완료되는 시점
        reader.addEventListener('load', function (e1) {
          // 완료되는 시점!!!!!!!!!!!!!!!
          self.imgsrc = e1.target.result;
          // 지금 reader 안에서는 this 못 씀. 그래서 35줄에 this를 self로 변수지정함
        });

        // 파일 읽기 시작
        reader.readAsDataURL(e.target.files[0]);
      } else {
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
      if (this.errorCheck) {
        axios.post('/api/postStorage', data)
            .then((res) => {
              console.log(this.form)
              console.log(res.data.result)
              if (res.data.result === 'ok') {
                alert('추가되었습니다')
                console.log('중복없음')
                this.clearInput()
                this.addStorage = true
                this.claerBox()
                this.addStorage = false
                alert('보관소와 보관함이 추가되었습니다.')
                this.$router.push('/storageAdmin')
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
.storages-add {
  width: 100%;
  height: 100%;
  text-align: center;
}

.storage-add {
  margin-top: 10%;
  width: 50%;
  display: flex;
  justify-content: center;
  margin-left: 25%;

}

.form-floating {
  margin-right: 5%;
}

.storage-add-btn {
  width: 10%;
  text-align: center;
  margin-left: 2%;
  margin-right: 3%;
  margin-bottom: 20%;
  padding: 1.5%;
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
  padding-top: 1%;
  border: solid 3px #000a69;
  width: 50%;
  justify-content: center;
  align-items: center;
  margin-left: 25%;
  padding-bottom: 1%;
}

.storage-box-p {
  padding-bottom: 3%;
}

.storage-btn {
  text-align: center;
  margin-left: 2%;
  margin-right: 3%;
  margin-top: 10%;
  width: 45%;
  padding: 1.5%;
  background-color: #ffffff;
  font-weight: bolder;
  color: #00a3de;
  border-color: #00a3de;
}

.storage-btn:hover {
  color: white;
  background-color: #b2e2fd;
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