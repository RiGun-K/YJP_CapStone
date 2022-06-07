/* eslint-disable */

<template>
	<h1>아래 정보는 AI추천기능을 위해 사용됩니다</h1>
	<div class="date">
		<p>날짜선택: &nbsp;</p>
		<p>{{ this.$store.state.diff - 1 }}박{{ this.$store.state.diff }}일</p>
		<Datepicker
			class="datePicker"
			v-model="shareDate"
			:enable-time-picker="false"
			:min-date="today"
			range
			placeholder="Select camping  date range"
			v-on="toString()"
			format="yyyy/MM/dd"
			autoApply
			:closeOnAutoApply="false"
		></Datepicker>
	</div>
	<p>캠핑장:<input type="text" v-model="campingName" /></p>
	<div class="addressDiv">
		<span class="input-group mb-3">
			<input
				type="text"
				v-model="postalAddress"
				class="form-control"
				placeholder="우편주소 입력"
				aria-label="Recipient's username"
				aria-describedby="button-addon2"
				readonly
			/>
			<button
				class="btn btn-outline-secondary"
				type="button"
				id="button-addon2"
				@click="zcGet"
			>
				우편주소검색
			</button>
		</span>
		<div>
			<span class="input-group mb-3">
				<input
					type="text"
					v-model="address"
					id="email"
					class="form-control"
					maxlength="100"
					placeholder="도로명입력"
					readonly
				/>
			</span>
		</div>
		<div>
			<span class="input-group mb-3">
				<input
					type="text"
					v-model="detailAddress"
					id="email"
					class="form-control"
					maxlength="100"
					placeholder="상세주소"
				/>
			</span>
		</div>
	</div>
	<input type="text" placeholder="플랜이름" v-model="planName" />
	<h6>{{ checkResult }}</h6>
	<button @click="checkPlanName">중복확인</button>

	<h3>
		공개여부
		<select v-model="planOpen">
			<option disabled value="">공개설정</option>
			<option>전체공개</option>
			<option>비공개</option>
		</select>
	</h3>
	<h3>
		지역
		<select v-model="planDestination">
			<option disabled value="">지역선택</option>
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
		</select>
	</h3>

	<h4>
		종류
		<select v-model="planType">
			<option disabled value="">캠핑종류선택</option>
			<option>오토캠핑</option>
			<option>글램핑</option>
			<option>백패킹</option>
		</select>
	</h4>
	<h3>인원</h3>
	<button v-on:click="planNumber += 1">+</button>
	<p>{{ planNumber }}</p>
	<button v-on:click="planNumber -= 1">-</button>
	<h3>예상 경비</h3>
	<input type="number" placeholder="0" v-model="planBudget" />
	<h3>TAG 설정</h3>
	<input type="text" v-model="tag" />
	<button @click="addTags(this.tag)">추가</button>
	<button
		title="더블클릭하면 삭제됩니다"
		v-for="(value, index) in TagContentList"
		:key="index"
		@dblclick="deleteTag(index)"
	>
		{{ value }}
	</button>
	<div>
		<button @click="createPlan()">다음</button>
	</div>
</template>

<script>
import axios from 'axios';
import Datepicker from '@vuepic/vue-datepicker';
import dayjs from 'dayjs';
import '@vuepic/vue-datepicker/dist/main.css';
export default {
	components: { Datepicker },
	data() {
		return {
			shareDate: [
				this.$store.state.myReservation.startDate,
				this.$store.state.myReservation.endDate,
			],
			today: new Date(),
			planStart: '',
			planEnd: '',
			planDestination: this.$store.state.camping.camping.areaId.areaName,
			planType: '',
			planNumber: 0,
			planBudget: '0',
			planName: '',
			checkResult: '',
			planCode: '',
			diff: '',
			planOpen: '전체공개',
			tag: '',
			TagContentList: [],
			campingName: this.$store.state.camping.camping.campingName,
			address: '',
			postalAddress: '',
			detailAdress: '',
			address: this.$store.state.camping.camping.address,
		};
	},
	methods: {
		zcGet() {
			new window.daum.Postcode({
				oncomplete: (data) => {
					this.postalAddress = data.zonecode;
					this.address = data.roadAddress;
				},
			}).open({ popupKey: '주소검색' });
		},
		addTags: function (value) {
			if (this.TagContentList.indexOf(value) !== -1) {
				alert('중복된 Tag설정은 불가능합니다');
			} else {
				this.TagContentList.push(value);
			}
			this.tag = '';
		},
		deleteTag: function (index) {
			this.TagContentList.splice(index, 1);
		},
		checkPlanName: function () {
			const url = 'api/checkPlanName';
			this.checkResult = '';
			axios
				.post(url, {
					teamCode: this.$store.state.teamCode.teamCode,
					planName: this.planName,
				})
				.then((response) => {
					if (response.data === 'y') {
						this.checkResult = '사용가능한 플랜이름입니다';
					} else {
						this.checkResult =
							'사용불가능한 플랜이름입니다. 다시 시도하세요';
						this.planName = '';
					}
				})
				.catch((error) => {
					console.log(error);
				});
		},
		toString: function () {
			const firstValue = dayjs(this.shareDate[0]);
			this.planStart = firstValue.format('YYYYMMDD');
			const secondValue = dayjs(this.shareDate[1]);
			this.planEnd = secondValue.format('YYYYMMDD');
			this.diff = secondValue.diff(firstValue, 'd') + 1;
			this.$store.commit('updateDiff', this.diff);
		},

		createPlan: function () {
			const data = {
				planName: this.planName,
				teamCode: this.$store.state.teamCode.teamCode,
				planStart: this.planStart,
				planEnd: this.planEnd,
				planDestination: this.planDestination,
				planType: this.planType,
				planNumber: this.planNumber,
				planBudget: this.planBudget,
				planTotalDate: this.diff,
				planOpen: this.planOpen,
				address: this.address,
				detailAddress: this.detailAddress,
				campingName: this.campingName,
			};
			const form = { plan: data, tagContentList: this.TagContentList };
			if (
				this.planName !== '' &&
				this.planBudget !== '' &&
				this.planNumber !== '' &&
				this.planType !== '' &&
				this.planDestination !== '' &&
				this.planEnd !== '' &&
				this.planStart !== '' &&
				this.planOpen !== ''
			) {
				if (this.TagContentList.length < 3) {
					alert('테그를 3개 이상 입력해야합니다');
				} else {
					const url = '/api/createPlan';
					axios
						.post(url, form)
						.then((response) => {
							this.planCode = response.data;
							this.$store.commit('updatePlanCode', response.data);
							this.$router.push({
								name: 'detailPlan',
							});
						})
						.catch((error) => {
							alert('에러');
							console.log(error);
						});
				}
			} else {
				alert('모든 입력값을 입력하세요');
			}
		},
	},
};
</script>
<style>
.datePicker {
	margin-left: 10px;
	width: 250px;
}
.date {
	display: flex;
}
.addressDiv {
	width: 500px;
}
</style>
