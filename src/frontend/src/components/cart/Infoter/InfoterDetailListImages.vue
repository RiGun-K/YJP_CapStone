<template>
  <br>
  <div class="listBody" style="margin-left: 45%">
    <div class="card-body">
      객실명 - {{ detail.detailName }}
    </div>
    <div class="card-body">
      설명 - {{ detail.detailFunction }}
    </div>
    <div class="card-body">
      최대인원 - {{ detail.maximumNumber }}
    </div>
    <div class="card-body">
      1 박 가격 - {{ detail.detailPrice }}
    </div>
  </div>

  <br>
  <ul class="slides">
    <input type="radio" name="radio-btn" id="img-1" checked />
    <li class="slide-container">
      <div class="slide">
        <img :src="'/api/product_detail_images/' + this.images[0].filename"/>
      </div>
      <div class="nav">
        <label for="img-6" class="prev">&#x2039;</label>
        <label for="img-2" class="next">&#x203a;</label>
      </div>
    </li>



    <li class="nav-dots">
      <label for="img-1" class="nav-dot" id="img-dot-1"></label>
      <label for="img-2" class="nav-dot" id="img-dot-2"></label>
      <label for="img-3" class="nav-dot" id="img-dot-3"></label>
      <label for="img-4" class="nav-dot" id="img-dot-4"></label>
    </li>
  </ul>


<!--  <div v-for="(room,index) in images" :key="index">-->
<!--    <img :src="'/api/product_detail_images/' + room.filename" alt="...">-->
<!--  </div>-->

  <br>
  <button class="w-btn-neon2" @click="beforeList()" style="margin-top: 8%">뒤로가기</button>
  <br>
</template>
<script>
import axios from "axios";

export default {
  name: "InfoterDetailListImages",
  created() {
    this.DataList();
  },
  data() {
    return {
      images: [],
      detail: '',
    }
  },
  methods: {
    DataList() {
      this.cam = this.$route.params.campingId
      this.id = this.$route.params.detailId;
      console.log(this.id);
      axios.get('/api/product_RoomImages/' + this.id)
        .then(res => {
          console.log(res.data);
          this.detail = res.data;
          this.images = res.data.images;
        })
        .catch(e => {
          console.log(e)
        })
    },
    beforeList() {
      this.$router.push({
        path: `/infoter/infoterList/${this.cam}`
      })
    }
  },


}
</script>

<style scoped>
.slides {
  padding: 0;
  width: 609px;
  height: 420px;
  right: 2.5%;
  display: block;
  margin: 0 auto;
  position: relative;
}
/*.slides * {*/
/*  user-select: none;*/
/*  -ms-user-select: none;*/
/*  -moz-user-select: none;*/
/*  -khtml-user-select: none;*/
/*  -webkit-user-select: none;*/
/*  -webkit-touch-callout: none;*/
/*}*/
.slides input { display: none; }
.slide-container { display: block; }
.slide {
  top: 0;
  opacity: 0;
  width: 609px;
  height: 420px;
  display: block;
  position: absolute;
  transform: scale(0);
  transition: all .7s ease-in-out;
}
.slide img {
  width: 150%;
  height: 120%;
}
.nav label {
  width: 150px;
  height: 100%;
  display: none;
  position: absolute;
  opacity: 0;
  z-index: 9;
  cursor: pointer;
  transition: opacity .2s;
  color: #FFF;
  font-size: 156pt;
  text-align: center;
  line-height: 380px;
  font-family: "Varela Round", sans-serif;
  background-color: rgba(255, 255, 255, .3);
  text-shadow: 0px 0px 15px rgb(119, 119, 119);
}
.slide:hover + .nav label { opacity: 0.5; }
.nav label:hover { opacity: 1; }
.nav .next { right: -20%; }
input:checked + .slide-container  .slide {
  opacity: 1;
  transform: scale(1);
  transition: opacity 1s ease-in-out;
}
input:checked + .slide-container .nav label { display: block; }
.nav-dots {
  width: 100%;
  bottom: 9px;
  height: 11px;
  display: block;
  position: absolute;
  text-align: center;
  left: 10%;
}
.nav-dots .nav-dot {
  top: -5px;
  width: 11px;
  height: 11px;
  margin: 0 4px;
  position: relative;
  border-radius: 100%;
  display: inline-block;
  background-color: rgba(0, 0, 0, 0.6);
}
.nav-dots .nav-dot:hover {
  cursor: pointer;
  background-color: rgba(0, 0, 0, 0.8);
}
input#img-1:checked ~ .nav-dots label#img-dot-1,
input#img-2:checked ~ .nav-dots label#img-dot-2,
input#img-3:checked ~ .nav-dots label#img-dot-3,
input#img-4:checked ~ .nav-dots label#img-dot-4,
input#img-5:checked ~ .nav-dots label#img-dot-5,
input#img-6:checked ~ .nav-dots label#img-dot-6 {
  background: rgba(0, 0, 0, 0.8);
}

@import url("https://fonts.googleapis.com/css?family=Poppins:200,300,400,500,600,700,800,900&display=swap");

@keyframes ring {
  0% {
    width: 30px;
    height: 30px;
    opacity: 1;
  }
  100% {
    width: 300px;
    height: 300px;
    opacity: 0;
  }
}

.w-btn-neon2 {
  position: relative;
  margin-left: 45%;
  border: none;
  min-width: 200px;
  min-height: 50px;
  background: linear-gradient(
      90deg,
      rgba(129, 230, 217, 1) 0%,
      rgba(79, 209, 197, 1) 100%
  );
  border-radius: 1000px;
  color: darkslategray;
  cursor: pointer;
  box-shadow: 12px 12px 24px rgba(79, 209, 197, 0.64);
  font-weight: 700;
  transition: 0.3s;
}

.w-btn-neon2:hover {
  transform: scale(1.2);
}

.w-btn-neon2:hover::after {
  content: "";
  width: 30px;
  height: 30px;
  border-radius: 100%;
  border: 6px solid #00ffcb;
  position: absolute;
  z-index: -1;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  animation: ring 1.5s infinite;
}
</style>