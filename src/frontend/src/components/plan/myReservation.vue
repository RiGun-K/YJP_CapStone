<template>
	<div class="buy-orders">
		<h1>Reservation Orders</h1>
		<div class="order-card-list">
			<div
				class="card border-info mb-3"
				v-for="(order, index) in orders"
				:key="order.orderCode"
			>
				<div class="card-header">
					{{ order.paymentDate[0] }}년 {{ order.paymentDate[1] }}월
					{{ order.paymentDate[2] }}일
				</div>
				<div class="card-body">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>주문코드</th>
								<th>캠핑장</th>
								<th>객실명</th>
								<th>예약일자</th>
								<th>주문금액</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>{{ order.orderCode }}</td>
								<td>
									{{ this.menus[index].camping.campingName }}
								</td>
								<td>
									{{
										this.menus[index].campingDetail
											.detailName
									}}
								</td>
								<td>
									{{
										this.menus[index].orders.startDate[0]
									}}년
									{{
										this.menus[index].orders.startDate[1]
									}}월
									{{
										this.menus[index].orders.startDate[2]
									}}일 ~
									{{ this.menus[index].orders.endDate[0] }}년
									{{ this.menus[index].orders.endDate[1] }}월
									{{ this.menus[index].orders.endDate[2] }}일
								</td>
								<td>{{ order.orderPrice }}</td>
								<td>
									<button
										@click="selectMyOrder(order, index)"
									>
										선택
									</button>
								</td>
							</tr>
							<!-- PathVariable 을 위해서는 router-link 작성 -->
							<!--      <router-link :to="{name: 'productDetail', params: { menuid:product.menuid }}"></router-link>-->
						</tbody>
					</table>
				</div>
			</div>
			<button @click="skip">건너뛰기</button>
		</div>
	</div>
</template>

<script>
import axios from 'axios';
export default {
	name: 'ReservationOrders',
	data() {
		return {
			content: [],
			orders: [],
			menus: [],
			startDate: '',
			endDate: '',
		};
	},
	created() {
		this.content = this.$store.state.loginState;
		this.DataList();
	},
	methods: {
		skip: function () {
			const confirmData = confirm(
				'예약된 캠핑장을 선택하지 않고 계획을 작성합니다',
			);
			if (confirmData) {
				this.$store.state.myReservation = new Object();
				this.$store.state.camping.camping.address = '';
				this.$store.state.camping.camping.areaId = '';
				this.$store.state.camping.camping.campingName = '';
				this.$store.commit(
					'setMyReservation',
					this.$store.state.myReservation,
				);
				this.$store.commit('setCamping', this.$store.state.camping);
				this.$router.push({ name: 'basicPlan' });
			}
		},
		selectMyOrder(myOrder, index) {
			this.$store.commit('setCamping', this.menus[index]);
			this.endDate =
				myOrder.endDate[0].toString() +
				'/' +
				myOrder.endDate[1].toString() +
				'/' +
				myOrder.endDate[2].toString() +
				'/';
			this.startDate =
				myOrder.startDate[0].toString() +
				'/' +
				myOrder.startDate[1].toString() +
				'/' +
				myOrder.startDate[2].toString() +
				'/';
			myOrder.endDate = this.endDate;
			myOrder.startDate = this.startDate;
			this.$store.commit('setMyReservation', myOrder);
			const confirmData = confirm(
				'해당 캠핑장으로 일정작성을 진행하시겠습니까?',
			);
			if (confirmData) {
				this.$router.push({ name: 'basicPlan' });
			}
		},
		DataList() {
       console.log("asdasdasdas");
			axios
				.get(
					'http://localhost:9002/api/ordersList/reservationCamping/' +
						this.content.mcode,
				)
				.then((res) => {
					axios
						.get(
							'http://localhost:9002/api/ordersList/reservationDetail/' +
								this.content.mcode,
						)
						.then((res2) => {
							this.orders = res.data;
							this.menus = res2.data;
              console.log("tjdthd");
						})
						.catch((e2) => {
							console.log(e2);
						});
				})
				.catch((e) => {
					console.log(e);
				});
		},
	},
};
</script>

<style scoped>
.buy-orders {
	width: 100%;
	height: 100%;
	margin-top: 2%;
	margin-left: 3%;
}
.table {
	padding: 0;
	margin: 0;
	text-align: center;
	border: 1px solid silver;
	border-collapse: collapse;
}
.table th,
td {
	border: 1px solid silver;
	padding: 1%;
}
.table th:first-child,
td:first-child {
	border-left: none;
}
.order-card-list {
	width: 70%;
	height: 100%;
	margin-top: 2%;
	margin-left: 3%;
}
.card-header {
	font-size: 1.5em;
}
</style>
