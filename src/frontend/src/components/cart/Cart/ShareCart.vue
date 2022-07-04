<template>
  <div class="back">
    <br>
    <div class="frame">
      <h2 style="padding: 4%; font-weight: bold">장바구니(대여)</h2>
      <div class="share-cart">

        <table class="share-cart-item-info">
          <tr>
            <td class="share-cart-checkbox"><input type="checkbox"
                                                   v-model="allChecked"
                                                   @click="checkedAll($event.target.checked)"
            >전체선택</td>
            <td>상품/옵션 정보</td>
          </tr>
          <tr  v-for="(shareCart, index) in shareCart" :key="index">
            <td class="share-cart-checkbox"><input type="checkbox"
                                                   :id="'check_' + shareCart.cartCode"
                                                   :value="shareCart.cartCode"
                                                   v-model="this.selected[index]"
                                                   @change="selectedMethod($event)" ></td>
            <td>
              <div class="card mb-3" style="width: 100%;" @click="rentalDetailPage(shareCart.rental.rentalId)">
                <div class="row g-0">
                  <div class="col-md-4">
                    <img :src="'/api/product_detail_images/' + shareCart.rental.filename" class="img-fluid rounded-start" alt="...">
                  </div>
                  <div class="col-md-8">
                    <div class="card-body">
                      <h5 class="card-title" style="padding: 4%">{{shareCart.rental.rentalName}}</h5>
                    </div>
                  </div>
                </div>
              </div>
            </td>
          </tr>
        </table>
      </div>
      <div class="share-cart-delete-div">
        <button class="share-cart-delete" @click="rentalCartDelete()">장바구니에서 삭제</button>
      </div>

      <div class="share-cart-btn-list">
        <button class="share-cart-btn">계속 쇼핑하기</button>
      </div>
    </div>
    <br>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: 'ShareCart',
  data () {
    return {
      content: [],
      shareCart: [],
      selected: [],
      allChecked: false,
      selectedList: []
    }
  },
  created() {
    this.content = this.$store.state.loginState
    this.shareCartList();
  },
  methods: {
    shareCartList(){
      axios.get('http://localhost:9002/api/cartList/shareCart/' + this.content.mcode)
          .then(res => {
            this.shareCart = res.data
            console.log(res.data)
          })
          .catch(e => {
            console.log(e)
          })
    },
    checkedAll(checked){
      this.allChecked = checked
      for(let i in this.shareCart){
        this.selected[i] = this.allChecked;
      }
    },
    selectedMethod(e){
      for(let i in this.shareCart){
        if(! this.selected[i]){
          this.allChecked = false
          return;
        }else{
          this.allChecked = true;
        }
      }
    },
    getSelected(){
      for(let i in this.shareCart){
        if(this.selected[i]){
          this.selectedList.push(this.shareCart[i].cartCode)
        }
      }
      console.log(this.selectedList)
      return this.selectedList
    },
    rentalDetailPage(rentalId){
      window.location.href = 'http://localhost:8081/itemShare/rentalList/'+ rentalId
    },
    rentalCartDelete(){
      this.getSelected();
      for(let i=0; i < this.selectedList.length; i++){
        this.axios.post('http://localhost:9002/api/rentalCartDelete', {
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
.share-cart{
  margin-top: 4%;
  width: 100%;
  height: 100%;
  justify-content: center;
  align-items: center;
  display: flex;
  font-size: 1.5em;
}
.share-cart .share-cart-item-info {
  margin-top: 2%;
  width: 70%;
  border: 1px solid #444444;
  border-collapse: collapse;
}
.share-cart td {
  border: 1px solid #444444;
  padding: 1%;
}
.share-cart-checkbox{
  width: 12%;
}
.share-cart-btn-list{
  text-align: center;
  margin-top: 7%;
  width: 100%;
  padding-bottom: 3%;
}
.share-cart-btn{
  margin-left: 2%;
  margin-right: 3%;
  width: 10%;
  padding: 1%;
  background-color: #ffffff;
  color: #00a3de;
  font-weight: bolder;
  border-color: #00a3de;
  border-radius: 1em;
}
.share-cart-btn:hover{
  color: white;
  background-color: #b2e2fd;
}
.share-cart-delete{
  padding: 2%;
  background-color: #ffffff;
  color: #00a3de;
  font-weight: bolder;
  border-color: #00a3de;
  border-radius: 1em;
  font-size: 1.5em;
}
.share-cart-delete:hover{
  color: white;
  background-color: #b2e2fd;
}
.share-cart-delete-div{
  margin-top: 3%;
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