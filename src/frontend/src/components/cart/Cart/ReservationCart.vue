<template>
  <h2 style="margin-left: 4%; margin-top: 4%">장바구니(예약)</h2>
  <div class="reservation-cart">

    <table class="reservation-cart-item-info">
      <tr>
        <td class="reservation-cart-checkbox"><input type="checkbox"
                                                     v-model="allChecked"
                                                     @click="checkedAll($event.target.checked)"
        >전체선택</td>
        <td>상품/옵션 정보</td>
      </tr>
      <tr  v-for="(resCart, index) in resCart" :key="index">
        <td class="reservation-cart-checkbox"><input type="checkbox"
                                                     :id="'check_' + resCart.cartCode"
                                                     :value="resCart.cartCode"
                                                     v-model="this.selected[index]"
                                                     @change="selectedMethod($event)" ></td>
        <td>
          <div class="card mb-3" style="width: 100%;" @click="resDetailPage(resCart.camping.campingId)">
            <div class="row g-0">
              <div class="col-md-4">
                <img :src="'/api/product_detail_images/' + resCart.camping.filename" class="img-fluid rounded-start" alt="...">
              </div>
              <div class="col-md-8">
                <div class="card-body">
                  <h5 class="card-title" style="padding: 4%">{{resCart.camping.campingName}}</h5>
                </div>
              </div>
            </div>
          </div>
        </td>
      </tr>
    </table>
  </div>
  <div class="res-cart-delete-div">
    <button class="res-cart-delete" @click="resCartDelete()">장바구니에서 삭제</button>
  </div>

  <div class="reservation-cart-btn-list">
    <button class="reservation-cart-btn">계속 쇼핑하기</button>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: 'ReservationCart',
  data () {
    return {
      content: [],
      resCart: [],
      selected: [],
      allChecked: false,
      selectedList: []
    }
  },
  created() {
    this.content = this.$store.state.loginState
    this.resCartList();
  },
  methods: {
    resCartList(){
      axios.get('http://localhost:9002/api/cartList/reservationCart/' + this.content.mcode)
          .then(res => {
            this.resCart = res.data
            console.log(res.data)
          })
          .catch(e => {
            console.log(e)
          })
    },
    checkedAll(checked){
      this.allChecked = checked
      for(let i in this.resCart){
        this.selected[i] = this.allChecked;
      }
    },
    selectedMethod(e){
      for(let i in this.resCart){
        if(! this.selected[i]){
          this.allChecked = false
          return;
        }else{
          this.allChecked = true;
        }
      }
    },
    getSelected(){
      for(let i in this.resCart){
        if(this.selected[i]){
          this.selectedList.push(this.resCart[i].cartCode)
        }
      }
      console.log(this.selectedList)
      return this.selectedList
    },
    resDetailPage(campingId){
      window.location.href = 'http://localhost:8081/infoter/infoterList/'+ campingId
    },
    resCartDelete(){
      this.getSelected();
      for(let i=0; i < this.selectedList.length; i++){
        this.axios.post('http://localhost:9002/api/resCartDelete', {
          cartCode: this.selectedList[i]
        })
            .then((res)=>{
              console.log(res.data);
              this.refreshAll();
            })
            .catch((err)=>{
              console.log(err)
            });
      }
    },
    refreshAll() {
      this.$router.go();
    },
  }
}
</script>

<style scoped>
.reservation-cart{
  margin-top: 4%;
  width: 100%;
  height: 100%;
  justify-content: center;
  align-items: center;
  display: flex;
  font-size: 1.5em;
}
.reservation-cart .reservation-cart-item-info {
  margin-top: 2%;
  width: 70%;
  border: 1px solid #444444;
  border-collapse: collapse;
}
.reservation-cart td {
  border: 1px solid #444444;
  padding: 1%;
}
.reservation-cart-checkbox{
  width: 12%;
}
.reservation-cart-btn-list{
  text-align: center;
  margin-top: 7%;
  width: 100%;
}
.reservation-cart-btn{
  margin-left: 2%;
  margin-right: 3%;
  width: 8%;
  padding: 1%;
  background-color: #ffffff;
  color: #00a3de;
  font-weight: bolder;
  border-color: #00a3de;
  border-radius: 1em;
}
.reservation-cart-btn:hover{
  color: white;
  background-color: #b2e2fd;
}
.res-cart-delete{
  padding: 2%;
  background-color: #ffffff;
  color: #00a3de;
  font-weight: bolder;
  border-color: #00a3de;
  border-radius: 1em;
  font-size: 1.5em;
}
.res-cart-delete:hover{
  color: white;
  background-color: #b2e2fd;
}
.res-cart-delete-div{
  margin-top: 3%;
  margin-left: 75%;
}
</style>