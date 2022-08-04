<template>
  <div class="container">
  <div class="search">
    <div class="infoter">

      <div class="searchDiv" style="display: flex; width: 800px; margin-left: 250px;">
        <select class="form-select" aria-label="Default select example" v-model="planDestination" @click="placeFilter()">
          <option>전체</option>
          <option>강원도</option>
          <option>경기도</option>
          <option>경상도</option>
          <option>대구시</option>
          <option>부산시</option>
          <option>서울시</option>
          <option>인천시</option>
          <option>전라도</option>
          <option>제주도</option>
          <option>충청도</option>
          <option>울산시</option>
          <option v-for="big in bigRound" :value="big.areaId">{{ big.areaName }}</option>
        </select>

        <input type="text" class="form-control" v-model="searchPlan" placeholder="키워드를 입력하세요" style="margin-left: 30px; width: 800px;" />
          <option v-for="small in smallRound" :value="small.areaId">{{ small.areaName }}</option>

        <button class="btn btn-outline-secondary" @click="tagFilter(this.searchPlan)" style="width: 110px; margin-left: 20px">검색</button>
      </div>
    </div>
  </div>
  <hr />

  <div class="sort">
    <button class="btn btn-outline-success" @click="orderBy('planViews')">
      조회순
    </button>
    <button class="btn btn-outline-success" @click="orderBy('planUsedCount')" style="margin-left: 10px; margin-right: 60px;">
      카피순
    </button>
  </div>
  <div>
    <div class="serch" style="display: flex; margin-left: 50px; margin-top: 50px; font-size: 20px;"><p>지역: {{ planDestination }}</p> <p style="margin-left: 50px;">검색: {{ searchPlan }}</p></div>
     <div class="paging" >
  <div class="col" style="width: 30%; display:inline-block; margin-left:5%; margin-top: 40px;"  @click="intoPlan(value)"
          v-for="(value, index) in filteredPlanList"
          :key="index"  >
          <div class="card shadow-sm">
             <img :src="'/api/product_detail_image/' + value.filename" class="imgbackground"  />
            <!-- <svg class="bd-placeholder-img card-img-top" width="100%" height="225" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Thumbnail" preserveAspectRatio="xMidYMid slice" focusable="false"><title>Placeholder</title><rect width="100%" height="100%" fill="#55595c"/><text x="50%" y="50%" fill="#eceeef" dy=".3em">Thumbnail</text></svg> -->

            <div class="card-body">
              <p class="card-text">  지역:{{ value.planDestination }}</p> <p class="card-text">조회수:
          {{ value.planViews }}</p> <p class="card-text"> 카피수:
          {{ value.planUsedCount }}</p>
              <div class="d-flex justify-content-between align-items-center">
                <div class="btn-group">
                  <button type="button" class="btn btn-sm btn-outline-secondary" style="margin-left: 300px;">View</button>
                </div>
              </div>
            </div>
            
          </div>
        </div>  <div class="pages" style="margin-top: 60px;">
        <p>
          페이지
          <button id="p" v-for="i in pageSize" @click="selectedPage(i)">
            {{ i }}
          </button>
        </p>
      </div>
        </div>
    <!-- <div class="paging">
      <div class="box">
        <div
          class="w-btn-outline w-btn-red-outline"
          @click="intoPlan(value)"
          v-for="(value, index) in filteredPlanList"
          :key="index"
        >
         <img :src="'/api/product_detail_image/' + value.filename" class="imgbackground"  />
          {{ value.teamCode.teamName }}팀의{{ value.planName }} 플랜
          <br />조회수:
          {{ value.planViews }}
          <br />
          카피수:
          {{ value.planUsedCount }}
          <br />
          지역:{{ value.planDestination }}
        </div>
      </div>
      <div class="pages">
        <p>
          페이지
          <button id="p" v-for="i in pageSize" @click="selectedPage(i)">
            {{ i }}
          </button>
        </p>
      </div>
    </div> -->
  </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  created() {
    this.loadAllPlans();
  },
  data() {
    return {
      searchPlan: "",
      planDestination: "전체",
      allPlanList: [],
      placeFiltered: [],
      tagFiltered: [],
      filteredPlanList: [],
      pageSize: "",
      pageNumber: 0,
    };
  },
  methods: {
    selectedPage(i) {
      this.pageNumber = i - 1;

      this.loadAllPlans();
    },
    intoPlan: function (value) {
      this.$store.commit("updatePlanCode", value);
      this.$router.push({
        name: "usersPlan",
      });
      const url = "/api/countView";

      axios
        .put(url, value)
        .then((response) => {})
        .catch((error) => {});
    },
    placeFilter: function () {
      if (this.planDestination === "전체") {
      } else {
        const placeFiltered = this.tagFiltered.filter(
          (a) => a.planDestination === this.planDestination
        );
        this.filteredPlanList.length = "";
        this.filteredPlanList = placeFiltered;
      }
    },
    tagFilter: function (tag) {
      if (this.searchPlan === "" && this.planDestination === "전체") {
        this.loadAllPlans();
      } else {
        const url = "api/tagFilter";
        this.filteredPlanList.length = 0;
        this.tagFiltered.length = 0;
        axios
          .get(url, { params: { tagContent: tag } })
          .then((response) => {
            response.data.map((item) => {
              this.filteredPlanList.push(item);
              this.tagFiltered.push(item);
            });
            if (this.planDestination === "전체") {
            } else {
              const placeFiltered = this.filteredPlanList.filter(
                (a) => a.planDestination === this.planDestination
              );
              this.filteredPlanList = placeFiltered;
            }
          })
          .catch((error) => {});
      }
    },
    orderBy: function (orderBy) {
      if (orderBy == "planViews") {
        this.filteredPlanList.sort(function (a, b) {
          return b.planViews - a.planViews;
        });
      } else if (orderBy == "planUsedCount") {
        this.filteredPlanList.sort(function (a, b) {
          return b.planUsedCount - a.planUsedCount;
        });
      }
    },
    loadAllPlans: function () {
      this.filteredPlanList.length = 0;
      const url = "/api/loadAllPlans";
      this.tagFiltered.length = 0;
      this.allPlanList.length = 0;
      this.placeFiltered.length = 0;
      axios
        .get(url, {
          params: {
            page: this.pageNumber,
            size: 4,
          },
        })
        .then((response) => {
          this.pageSize = response.data.totalPages;
          response.data.content.map((item) => {
            this.allPlanList.push(item);
            this.filteredPlanList.push(item);
            this.tagFiltered.push(item);
            return console.log(item);
          });
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style>
.w-btn-outline {
  position: relative;
  padding: 15px 30px;
  border-radius: 15px;
  font-family: "paybooc-Light", sans-serif;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
  text-decoration: none;
  font-weight: 600;
  transition: 0.25s;
}

.w-btn-red-outline {
  border: 3px solid #ff5f2e;
  color: #6e6e6e;
  width: 380px;
  height: 450px;
  margin: 20px;
  font-size: 25px;
  float: left;
}

.w-btn-red-outline:hover {
  background-color: #ff5f2e;
  color: #e1eef6;
}

.w-btn-outline:hover {
  letter-spacing: 2px;
  transform: scale(1.2);
  cursor: pointer;
}

.w-btn-outline:active {
  transform: scale(1.5);
}

.optionTag {
  text-align: center;
  width: 100px;
  font-weight: 600;
}

.sort {
  float: right;
  margin: 10px;
}

.w-btn1 {
  position: relative;
  border: none;
  display: inline-block;
  padding: 15px 30px;
  border-radius: 15px;
  font-family: "paybooc-Light", sans-serif;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
  text-decoration: none;
  font-weight: 600;
  transition: 0.25s;
  margin-right: 50px;
}

.w-btn1:hover {
  letter-spacing: 2px;
  transform: scale(1.2);
  cursor: pointer;
}

.w-btn1:active {
  transform: scale(1.5);
}

.w-btn {
  position: relative;
  border: none;
  display: inline-block;
  padding: 15px 30px;
  border-radius: 15px;
  font-family: "paybooc-Light", sans-serif;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
  text-decoration: none;
  font-weight: 600;
  transition: 0.25s;
}

.w-btn:hover {
  letter-spacing: 2px;
  transform: scale(1.2);
  cursor: pointer;
}

.w-btn-indigo {
  background-color: aliceblue;
  color: #1e6b7b;
  margin-bottom: 30px;
}

.search {
  height: 100px;
  text-align: center;
}

.search input {
  width: 500px;
  height: 40px;
  margin-top: 30px;
}

.search select {
  height: 40px;
  margin-top: 30px;
}

.search button {
  position: relative;
  display: inline-block;
  margin-top: 30px;
}
.pages button {
  border: none;
  background-color: transparent;
}
.pages {
  margin-left: 50%;
}
.paging {
  display: block;
}
.box {
  display: inline-block;
}
.imgbackground{

height: 200px;
width: 300px;

}     .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
</style>
