<template>
  <div class="back">
    <br>
    <div class="frame">
      <h2 style="padding: 4%; font-weight: bold">장바구니(구매)</h2>

      <div class="buy-cart">
        <table class="buy-cart-item-info">
          <tr>
            <td class="buy-cart-checkbox"><input type="checkbox"
                                                 v-model="allChecked"
                                                 @click="checkedAll($event.target.checked)"
            >전체선택</td>
            <td>상품/옵션 정보</td>
            <td>수량</td>
            <td>상품 금액</td>
            <td>배송비</td>
          </tr>
          <tr  v-for="(buyCart, index) in buyCartLists" :key="index">
            <td class="buy-cart-checkbox"><input type="checkbox"
                                                 :id="'check_' + buyCart.cartCode"
                                                 :value="buyCart.cartCode"
                                                 v-model="this.selected[index]"
                                                 @change="selectedMethod($event)"
            ></td>
            <td>
              <div class="card mb-3" style="width: 100%;">
                <div class="row g-0">
                  <div class="col-md-4">
                    <img :src="'/api/product_detail_images/' + buyCart.menuBuy.filename" class="img-fluid rounded-start" alt="...">
                  </div>
                  <div class="col-md-8">
                    <div class="card-body">
                      <h3 class="card-title" style="padding: 4%">{{ buyCart.menuBuy.buyName }}</h3>
                    </div>
                  </div>
                </div>
              </div>
            </td>
            <td class="count-td"><button class="buy-count-sub" @click="subCount(index)"> - </button>{{this.cartItemCounts[index]}}<button class="buy-count-add" @click="addCount(index)"> + </button></td>
            <td style="width: 15%">{{ buyCart.menuBuy.buyPrice }}</td>
            <td style="width: 10%">10000</td>
          </tr>
        </table>
      </div>
      <div class="buy-cart-delete-div">
        <button class="buy-cart-delete" @click="buyCartDelete()">장바구니에서 삭제</button>
      </div>
      <div class="buy-cart-all">
        <p>상품금액 {{priceAll}} + 배송비 10000 = 주문금액 {{ priceAll+10000}} </p>
      </div>

      <div class="buy-cart-btn-list">
        <button class="buy-cart-btn" @click="continueBuy()">계속 쇼핑하기</button>
        <button class="buy-cart-btn" @click="AllMenuOrder()">구매하기</button>
      </div>
    </div>
    <br>
  </div>
</template>

<script>
import axios from "axios";
import store from "@/store";
export default {
  name: 'BuyCart',
  data () {
    return {
      // allChecked: false,
      content: [],
      buyCartLists: [],
      cartItemCounts: [],
      selected: [],
      allChecked: false,
      selectedList: []
    }
  },
  created() {
    this.content = this.$store.state.loginState
    this.buyCartList();
  },
  setup () {
  },
  computed: {
    priceAll() {
      let priceAllAdd = 0
      for (let i = 0; i < this.buyCartLists.length; i++) {
        priceAllAdd += this.buyCartLists[i].cartItemCount * this.buyCartLists[i].menuBuy.buyPrice
      }
      return priceAllAdd
    }
  },
  methods: {
    subCount (index) {
      if (this.cartItemCounts[index] === 1) {
        alert('더 이상 뺄 수 없습니다.')
      } else {
        this.cartItemCounts[index]--
        axios.post('http://localhost:9002/api/cartItem/count', {
          cartItemCount: this.cartItemCounts[index],
          cartMenuBuy: this.buyCartLists[index].menuBuy.buyId,
          mid: this.content.mcode
        })
            .then(res => {
              console.log(res.data)
              this.cartItemCounts[index] = res.data
            })
            .catch(err =>{
              console.log(err)
            })
        this.refreshAll();
      }
    },
    addCount (index) {
      if(this.cartItemCounts[index] === this.buyCartLists[index].menuBuy.buyStock) {
        alert("재고 부족: 더 이상 올릴 수 없습니다.")
      }else{
        this.cartItemCounts[index]++
        axios.post('http://localhost:9002/api/cartItem/count', {
          cartItemCount: this.cartItemCounts[index],
          cartMenuBuy: this.buyCartLists[index].menuBuy.buyId,
          mid: this.content.mcode
        })
            .then(res => {
              console.log(res.data)
              this.cartItemCounts[index] = res.data
            })
            .catch(err =>{
              console.log(err)
            })
        this.refreshAll();
      }
    },
    buyCartList(){
      axios.get('http://localhost:9002/api/cartList/buyCart/' + this.content.mcode)
          .then(res =>{
            this.buyCartLists = res.data
            console.log(res.data)
            for(let i=0; i < res.data.length; i++){
              this.cartItemCounts[i] = res.data[i].cartItemCount
            }
          })
          .catch(e => {
            console.log(e)
          })
    },
    refreshAll() {
      this.$router.go();
    },
    AllMenuOrder(){
      if (confirm('구매 하시겠습니까?')) {
        store.commit("setSelectedList", this.getSelected())
        this.$router.push({
          path: `/itemBuy/buyCartNow/${this.content.mcode}`
        })
      }else{
        alert('취소')
      }
    },
    checkedAll(checked){
      this.allChecked = checked
      for(let i in this.buyCartLists){
        this.selected[i] = this.allChecked;
      }
    },
    selectedMethod(e){
      for(let i in this.buyCartLists){
        if(! this.selected[i]){
          this.allChecked = false
          return;
        }else{
          this.allChecked = true;
        }
      }
    },
    getSelected(){
      for(let i in this.buyCartLists){
        if(this.selected[i]){
          this.selectedList.push(this.buyCartLists[i].cartCode)
        }
      }
      console.log(this.selectedList)
      return this.selectedList
    },
    buyCartDelete(){
      this.getSelected();
      for(let i=0; i < this.selectedList.length; i++){
        this.axios.post('http://localhost:9002/api/buyCartDelete', {
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
    }
  }
}
</script>

<style scoped>
.buy-cart{
  margin-top: 4%;
  width: 100%;
  height: 100%;
  justify-content: center;
  align-items: center;
  display: flex;
  font-size: 1.5em;
}
.buy-cart .buy-cart-item-info {
  margin-top: 2%;
  width: 70%;
  border: 1px solid #444444;
  border-collapse: collapse;
}

.buy-cart td {
  border: 1px solid #444444;
  padding: 1%;
}
.buy-cart-checkbox{
  width: 12%;
}
.count-td{
  width: 15%;
}
.buy-count-sub{
  margin-right: 10%;
}
.buy-count-add{
  margin-left: 10%;
}
.buy-cart-all{
  padding-top: 2%;
  text-align: center;
  padding-bottom: 1.5%;
  border: 1px solid black;
  width: 70%;
  margin-left: 15%;
  margin-top: 2%;
  font-size: 1.5em;
}
.buy-cart-all p{
  font-size: 1.5em;
}
.buy-cart-btn-list{
  text-align: center;
  margin-top: 7%;
  width: 100%;
  padding-bottom: 3%;
}
.buy-cart-btn{
  margin-left: 2%;
  margin-right: 3%;
  width: 12%;
  padding: 1%;
  background-color: #ffffff;
  color: #00a3de;
  font-weight: bolder;
  border-color: #00a3de;
  border-radius: 1em;
  font-size: 1.5em;
}
.buy-cart-btn:hover{
  color: white;
  background-color: #b2e2fd;
}
.buy-cart-delete{
  padding: 2%;
  background-color: #ffffff;
  color: #00a3de;
  font-weight: bolder;
  border-color: #00a3de;
  border-radius: 1em;
  font-size: 1.5em;
}
.buy-cart-delete:hover{
  color: white;
  background-color: #b2e2fd;
}
.buy-cart-delete-div{
  margin-top: 7%;
  margin-left: 75%;
}
.back {
  position: absolute;
  background-image: url(@/assets/campwall2.webp);
  background-size: 100%;
  width: 100%;
  height: auto;
  background-repeat: repeat-y;
  padding: auto;
}
.frame {
  border: none;
  border-radius: 50px;
  background-color: rgb(247, 246, 230);
  width: 86%;
  height: auto;
  margin: auto;
}
</style>