/* eslint-disable */

<template>
	<h1>아래 정보는 AI추천기능을 위해 사용됩니다</h1>
	<h1>날짜 선택</h1>
	<Datepicker
		v-model="shareDate"
		:enable-time-picker="false"
		:min-date="today"
		range
		placeholder="Select share date range"
		v-on="toString()"
		format="yyyy/MM/dd"
		autoApply
		:closeOnAutoApply="false"
	></Datepicker>
	<h2>{{ this.$store.state.diff - 1 }}박{{ this.$store.state.diff }}일</h2>
	<input
		type="text"
		@keyup="checkPlanName"
		placeholder="플랜이름"
		v-model="planName"
	/>

	<p>{{ checkResult }}</p>
	<h3>
		공개여부
		<select v-model="planOpen">
			<option disabled value="">공개설정</option>
			<option>전체공개</option>
			<option>비공개</option>
		</select>
	</h3>
	<h3>
		장소
		<select v-model="planDestination">
			<option disabled value="">지역선택</option>
			<option>강원</option>
			<option>경기</option>
			<option>경남</option>
			<option>경북</option>
			<option>광주</option>
			<option>대구</option>
			<option>대전</option>
			<option>부산</option>
			<option>서울</option>
			<option>인천</option>
			<option>전남</option>
			<option>전북</option>
			<option>제주</option>
			<option>충북</option>
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
			shareDate: [],
			today: new Date(),
			planStart: '',
			planEnd: '',
			planDestination: '',
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
		};
	},
	methods: {
		addTags: function (value) {
			if (this.TagContentList.indexOf(value) !== -1) {
				alert('중복된 Tag설정은 불가능합니다');
			} else {
				this.TagContentList.push(value);
			}
			this.tag = '';
		},
		deleteTag: function (index) {
			console.log(index);
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
			const oldPlanCode = this.$store.state.planCode;
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
			};
			console.log(oldPlanCode);
			console.log(oldPlanCode);
			console.log(oldPlanCode);
			console.log(oldPlanCode);
			console.log(oldPlanCode);
			console.log(oldPlanCode);
			console.log(data);

			const form = { planDto: data, oldPlanDto: oldPlanCode };

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
					const url = '/api/createCopyPlan';
					axios
						.put(url, form)
						.then((response) => {
							this.planCode = response.data;
							this.$store.commit('updatePlanCode', response.data);
							console.log(this.$store.state.planCode);
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
<style></style>
