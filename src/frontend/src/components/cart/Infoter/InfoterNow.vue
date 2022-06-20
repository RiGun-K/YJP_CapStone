<template>
	<div class="infoterNow">
		<h2>예약/결제</h2>

		<h3>구매자 정보</h3>
		<table>
			<tr>
				<td class="buy-now-td">이름</td>
				<td>{{ this.user.mnick }}</td>
			</tr>
			<tr>
				<td class="buy-now-td">이메일</td>
				<td>{{ this.user.mmail }}</td>
			</tr>
			<tr>
				<td class="buy-now-td">휴대폰 번호</td>
				<td>{{ this.user.mph }}</td>
			</tr>
		</table>

		<h3>예약 정보</h3>
		<table>
			<tr>
				<td class="infoter-now-td">이름</td>

				<td><input type="text" v-model="reservationName" /></td>
			</tr>
			<tr>
				<td class="infoter-now-td">연락처</td>
				<td>
					<input
						type="text"
						v-model="reservationTel"
						maxlength="12"
					/>
				</td>
			</tr>
			<tr>
				<td class="infoter-now-td">예약 요청사항</td>
				<td>
					<input size="40" type="text" v-model="reservationRequest" />
				</td>
			</tr>
			<tr>
				<td class="infoter-now-td">예약일</td>
				<td>
					{{ this.$route.query.startDate }} ~
					{{ this.$route.query.endDate }}
				</td>
			</tr>
		</table>

		<h3>결제 정보</h3>
		<table>
			<tr>
				<td class="infoter-now-td">객실 이름</td>
				<td>{{ this.content.detailName }}</td>
			</tr>
			<tr>
				<td class="buy-now-td">객실 금액</td>
				<td>{{ this.content.detailPrice }} 원</td>
			</tr>
			<tr>
				<td class="infoter-now-td">총 결제 금액</td>
				<td>
					{{ this.content.detailPrice * this.$route.query.period }} 원
				</td>
			</tr>
		</table>

		<h5>
			구매조건 확인 및 결제대행 서비스 약관 동의
			<button @click="checkBuy()">보기</button>
		</h5>

		<h5>개인정보 제3자 제공 동의<button>보기</button></h5>
		<h5>개인정보 수집 및 이용 동의<button>보기</button></h5>

		<h5 class="infoter-now-info-check">
			위 주문 내용을 확인하였으며, 회원 본인은 개인정보 이용 및
			제공(해외직구의 경우 국외제공) 및 결제에 동의합니다.
		</h5>
		<button class="pay-infoter-now" @click="paymentBtn()">결제하기</button>
		<button class="cancel-infoter-now" @click="cancelBtn()">취소</button>
	</div>
</template>

<script>
import Datepicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css';
import axios from 'axios';
import store from '@/store';
import dayjs from 'dayjs';
export default {
	name: 'InfoterNow',
	components: { Datepicker },
	created() {
		this.DataList();
		console.log('예약 시작기간');
		console.log(this.$route.query.startDate);
		console.log('예약 종료기간');
		console.log(this.$route.query.endDate);
		console.log('예약 총 기간');
		console.log(this.$route.query.period);
	},
	data() {
		return {
			price: this.$route.query.period * 1000,
			reservationName: '',
			reservationTel: '',
			reservationRequest: '',
			onlyNumber: true,
			paymentDate: new Date(),
			reservationDate: [],
			content: [],
			user: [],
			startDate: new Date(),
			endDate: new Date(),
		};
	},
	setup() {
		const today = new Date();
		const todayEnd = new Date();
		const end = new Date(todayEnd.setDate(todayEnd.getDate() + 21));
		return {
			today,
			end,
		};
	},
	mounted() {
		const IMP = window.IMP;
		IMP.init('imp35975601');
	},
	methods: {
		paymentBtn() {
			if (confirm('결제 하시겠습니까?')) {
				const IMP = window.IMP;
				console.log(this.startDate);
				console.log(this.endDate);
				IMP.init('imp35975601');
				IMP.request_pay(
					{
						pg: 'html5_inicis',
						pay_method: 'card',
						merchant_uid: 'merchant_' + new Date().getTime(),
						name: this.content.detailName,
						amount:
							this.content.detailPrice * this.$route.query.period,
						buyer_tel: this.reservationTel,
						buyer_name: this.user.mid,
						buyer_email: this.user.mmail,
						confirm_url: '',
					},
					(rsp) => {
						if (true) {
							let msg = '결제가 완료되었습니다.';
							msg += '고유ID : ' + rsp.imp_uid;
							msg += '상점 거래 ID : ' + rsp.merchant_uid;
							msg += '결제 금액 : ' + rsp.paid_amount;
							msg += '카드 승인번호 : ' + rsp.apply_num;
							alert(msg);
							console.log(this.content);
							this.axios
								.post(
									'http://localhost:9002/api/CampingRoomData',
									{
										MID: this.user.mid,
										reservationName: this.reservationName,
										reservationTel: this.reservationTel,
										reservationRequest:
											this.reservationRequest,
										// orderPrice: this.price,
										orderPrice:
											this.content.detailPrice *
											this.$route.query.period,
										orderType: rsp.pay_method,

										paymentCode: rsp.merchant_uid,
										orderState: '2',
										orderMenuCount: 1,
										startDate: this.startDate,
										endDate: this.endDate,
										roomId: this.content.detailId,
										campingId: this.content.campingId,
									},
								)
								.then((res) => {
									console.log(res.data);
								})
								.catch((err) => {
									console.log(err);
								});
							this.$router.push({
								name: 'InfoterComplete',
								params: {
									orderMenuCount: 1,
									menuName: this.content.detailName,
									orderPrice:
										this.content.detailPrice *
										this.$route.query.period,
									reservationDate: this.reservationDate,
									orderType: rsp.pay_method,
								},
							});
						} else {
							let msg = '결제에 실패하였습니다.';
							msg += '에러 내용 : ' + rsp.error_msg;
							alert(msg);
						}
					},
				);
			}
		},
		cancelBtn() {
			window.location.href = 'http://localhost:8081/infoter';
		},
		DataList() {
			this.campingId = this.$route.params.campingId;
			this.id = this.$route.params.detailId;
			this.user = store.getters.getLoginState.mcode;
			console.log(this.id);
			console.log(this.user);
			axios
				.get('http://localhost:9002/api/product_detailR/' + this.id)
				.then((res) => {
					console.log(res.data);
					this.content = res.data;
					//
				})
				.catch((e) => {
					console.log(e);
				});
			//
			axios
				.get('http://localhost:9002/api/product_detailU/' + this.user)
				.then((res) => {
					console.log(res.data);
					this.user = res.data;
					//
				})
				.catch((e) => {
					console.log(e);
				});
		},
		toString() {
			const start = dayjs(this.reservationDate[0]);
			this.startDate = start.format('YYYYMMDD');
			const end = dayjs(this.reservationDate[1]);
			this.endDate = end.format('YYYYMMDD');
		},
	},
	watch: {
		reservationTel(val) {
			const reg = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
			if (reg.exec(val) !== null) {
				this.reservationTel = this.reservationTel.slice(0, -1);
				alert('숫자만 입력해주세요');
			}
			return (this.reservationTel = this.reservationTel.replace(
				/[^-\.0-9]/g,
				'',
			));
		},
	},
};
</script>

<style scoped>
.infoterNow {
	margin: 1% 2%;
	width: 100%;
	height: 100%;
}
.infoterNow h3 {
	margin: 1% 10%;
	width: 100%;
	height: 100%;
}
.infoter-now-td {
	text-align: center;
	width: 20%;
}
.infoterNow table {
	margin: 1.5% 15%;
	width: 35%;
	border: 1px solid #444444;
	border-collapse: collapse;
}
.infoterNow td {
	border: 1px solid #444444;
	padding: 2%;
}
.infoterNow h5 {
	margin: 1% 10%;
}
.infoterNow h5 button {
	margin: 0% 2%;
}
.pay-infoter-now {
	margin-left: 27%;
}
.infoter-now-info-check {
	margin: 3% 30%;
	padding: 1.5%;
}
.cancel-infoter-now {
	margin-left: 5%;
}
</style>
