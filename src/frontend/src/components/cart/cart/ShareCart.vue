<template>
  <div class="share-cart">
    <h2>장바구니(대여)</h2>

    <table class="share-cart-item-info">
      <tr>
        <td class="share-cart-checkbox"><input type="checkbox" @click="checkAll" v-model="allChecked">전체선택</td>
        <td>상품/옵션 정보</td>
        <td>수량</td>
        <td>대여상품 금액</td>
        <td>배송비</td>
      </tr>
      <tr  v-for="(mx, index) in list" :key="index">
        <td class="share-cart-checkbox"><input type="checkbox" :checked="mx.checked" ></td>
        <td>
          <div class="card mb-3" style="max-width: 540px;">
            <div class="row g-0">
              <div class="col-md-4">
                <img src="" class="img-fluid rounded-start" alt="...">
              </div>
              <div class="col-md-8">
                <div class="card-body">
                  <h5 class="card-title">상품이름: {{ mx.name }}</h5>
                  <h5 class="card-text">옵션</h5>
                  <h5 class="card-text">대여기간</h5>
                </div>
              </div>
            </div>
          </div>
        </td>
        <td class="count-td"><button class="share-count-sub" @click="subCount(index)"> - </button>{{mx.count}}<button class="share-count-add" @click="addCount(index)"> + </button></td>
        <td>{{mx.price}}</td>
        <td>{{mx.delivery}}</td>
      </tr>
    </table>
  </div>
  <div class="share-cart-all">
    <p>상품금액 {{priceAll}} + 배송비 {{deliveryAll}} = 주문금액 {{ priceAll+deliveryAll}} </p>
  </div>

  <div class="share-cart-btn-list">
    <button class="share-cart-btn">계속 쇼핑하기</button>
    <button class="share-cart-btn">대여하기</button>
  </div>
</template>

<script>
export default {
  name: 'ShareCart',
  data () {
    return {
      allChecked: false,
      list: [
        { name: '동그라미', count: 1, price: 50000, delivery: 0, checked: false },
        { name: '세모', count: 5, price: 12000, delivery: 5000, checked: false },
        { name: '네모', count: 3, price: 32000, delivery: 20000, checked: false }
      ]
    }
  },
  setup () {
    return {
      check: {
        check1: false,
        check2: false
      }
    }
  },
  computed: {
    priceAll () {
      let priceAllAdd = 0
      for (let i = 0; i < this.list.length; i++) {
        priceAllAdd += this.list[i].count * this.list[i].price
      }
      return priceAllAdd
    },
    deliveryAll () {
      let deliveryAllAdd = 0
      for (let i = 0; i < this.list.length; i++) {
        deliveryAllAdd += this.list[i].delivery
      }
      return deliveryAllAdd
    }
  },
  methods: {
    checkAll () {
      if (this.allChecked === false) {
        this.allChecked = true
        this.list[0].checked = true
        this.list[1].checked = true
        this.list[2].checked = true
      } else {
        this.allChecked = false
        this.list[0].checked = false
        this.list[1].checked = false
        this.list[2].checked = false
      }
    },
    subCount (index) {
      if (this.list[index].count === 1) {
        alert('더 이상 뺄 수 없습니다.')
      } else {
        this.list[index].count--
      }
    },
    addCount (index) {
      this.list[index].count++
    }
  }
}
</script>

<style scoped>
.share-cart{
  margin: 4%;
  width: 100%;
  height: 100%;
}
.share-cart .share-cart-item-info {
  margin-left: 10%;
  margin-top: 6%;
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
.count-td{
  width: 10%;
}
.share-count-sub{
  margin-right: 10%;
}
.share-count-add{
  margin-left: 10%;
}
.share-cart-all{
  padding-top: 2%;
  text-align: center;
  padding-bottom: 1.5%;
  border: 1px solid black;
  width: 70%;
  margin-left: 14%;
  margin-top: 6%;
}
.share-cart-all p{
  font-size: 1.5em;
}
.share-cart-btn-list{
  text-align: center;
  margin-top: 7%;
  width: 100%;
}
.share-cart-btn{
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
.share-cart-btn:hover{
  color: white;
  background-color: #b2e2fd;
}
</style>