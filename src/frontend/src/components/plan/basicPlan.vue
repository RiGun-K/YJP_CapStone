<template>
	<div class="back">
		<div class="frame">
			<div class="contents">
				<div class="date">
					<p>* 날짜선택: &nbsp;</p>
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
				<div class="campingName">
					<p>캠핑장:<input type="text" v-model="campingName" /></p>
				</div>
				<div class="planName">
					<p>
						* 플랜이름:
						<input
							type="text"
							placeholder="플랜이름"
							v-model="planName"
						/>
						<button class="w-btn w-btn-blue" @click="checkPlanName">
							중복확인
						</button>
					</p>

					<h6>{{ checkResult }}</h6>
				</div>
				<div class="planOpen">
					<p>
						* 공개여부
						<select v-model="planOpen">
							<option disabled value="">공개설정</option>
							<option>전체공개</option>
							<option>비공개</option>
						</select>
					</p>
				</div>
				<div class="planDestination">
					<p>
						* 지역
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
							<option>경북</option>
						</select>
					</p>
				</div>
				<div class="planType">
					<p>
						* 종류
						<select v-model="planType">
							<option disabled value="">캠핑종류선택</option>
							<option>오토캠핑</option>
							<option>글램핑</option>
							<option>백패킹</option>
						</select>
					</p>
				</div>
				<div class="planBudget">
					<p>
						예상 경비
						<input
							type="number"
							placeholder="0"
							v-model="planBudget"
						/>원
					</p>
				</div>

				<div class="planNumber">
					<p>인원</p>
					<button
						class="w-btn w-btn-blue"
						v-on:click="planNumber += 1"
					>
						+
					</button>
					<p>{{ planNumber }}</p>
					<button
						class="w-btn w-btn-blue"
						v-on:click="planNumber -= 1"
					>
						-
					</button>
				</div>
				<hr />
				<h3>TAG 설정</h3>
				<input type="text" v-model="tag" placeholder="ex) 커플여행" />

				<button class="w-btn w-btn-blue" @click="addTags(this.tag)">
					추가
				</button>
				<div class="tags">
					<button
						class="tagButton"
						title="더블클릭하면 삭제됩니다"
						v-for="(value, index) in TagContentList"
						:key="index"
						@dblclick="deleteTag(index)"
					>
						# {{ value }}
					</button>
				</div>

				<div class="input-group">
					<form>
						<input
							multiple
							type="file"
							id="file"
							name="profile_files"
							ref="serveyImage"
							@change="handleImage"
							enctype="multipart/form-data"
							aria-describedby="inputGroupFileAddon04"
							aria-label="Upload"
							placeholder="상품을 설명할 이미지 파일을 업로드하세요."
							accept="image/*"
							drop-placeholder="Drop file here..."
						/>
						<div id="image_container" />
					</form>
				</div>
				<div>
					<button
						class="w-btn-outline w-btn-blue-outline"
						@click="createPlan()"
					>
						다음
					</button>
				</div>
			</div>
		</div>
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
			planDestination:
				this.$store.state.camping.areaId.parentcampingarea.areaName,
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
			campingName: this.$store.state.camping.campingName,
			address: '',
			postalAddress: '',
			detailAdress: '',
			address: this.$store.state.camping.address,
			file: '',
		};
	},
	methods: {
		handleImage(e) {
			this.file = e.target.files[0];
			let self = this;
			if (e.target.files[0]) {
				// 파일 읽는 라이브러리
				const reader = new FileReader();
				console.log(this.file);
				// 파일 읽기가 완료되는 시점
				reader.addEventListener('load', function (e1) {
					// 완료되는 시점!!!!!!!!!!!!!!!
					self.imgsrc = e1.target.result;
					// 지금 reader 안에서는 this 못 씀. 그래서 35줄에 this를 self로 변수지정함

					let img = document.createElement('img');
					img.setAttribute('src', e1.target.result);
					document
						.querySelector('div#image_container')
						.appendChild(img);
				});

				// 파일 읽기 시작
				reader.readAsDataURL(e.target.files[0]);
				this.stateCheck = true;
			} else {
				return false;
			}
		},
		zcGet() {
			new window.daum.Postcode({
				oncomplete: (data) => {
					this.postalAddress = data.zonecode;
					this.address = data.roadAddress;
				},
			}).open({ popupKey: '주소검색' });
		},
		addTags: function (value) {
			if (this.tag == '') {
				alert('값을 입력하세요');
			} else {
				if (this.TagContentList.indexOf(value) !== -1) {
					alert('중복된 Tag설정은 불가능합니다');
				} else {
					this.TagContentList.push(value);
				}
				this.tag = '';
			}
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
			const formData = new FormData();
			// const data = {
			// 	'planName': this.planName,
			// 	'teamCode':this.$store.state.teamCode.teamCode,
			// 	'planStart': this.planStart,
			// 	'planEnd':this.planEnd,
			// 	'planDestination':this.planDestination,
			// 	'planType':this.planType,
			// 	'planNumber': this.planNumber,
			// 	'planBudget':this.planBudget,
			// 	'planTotalDate': this.diff,
			// 	'planOpen': this.planOpen,
			// 	'address': this.address,
			// 	'detailAddress': this.detailAddress,
			// 	'campingName':this.campingName,
			// };

			//	formData.append('plan', data);
			formData.append('planName', this.planName);
			formData.append(
				'teamCode',
				this.$store.state.teamCode.teamCode.teamCode,
			);
			formData.append('planStart', this.planStart);
			formData.append('planEnd', this.planEnd);
			formData.append('planDestination', this.planDestination);
			formData.append('planType', this.planType);
			formData.append('planNumber', this.planNumber);
			formData.append('planBudget', this.planBudget);
			formData.append('planTotalDate', this.diff);
			formData.append('planOpen', this.planOpen);
			formData.append('address', this.address);
			formData.append('detailAddress', this.detailAddress);
			formData.append('campingName', this.campingName);
			formData.append('file', this.file);
			formData.append('tagContentList', this.TagContentList);

			console.log(this.planName);
			console.log(formData.get('planName'));
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
						.post(url, formData, {
							headers: { 'Content-Type': 'multipart/form-data' },
						})
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
				alert('필수 입력 항목을 입력하세요');
			}
		},
	},
};
</script>
<style>
.back {
	text-align: center;
	background-image: url(@/assets/campwall2.webp);
	background-size: 100%;
}

.frame {
	background-color: rgb(247, 246, 230);
	width: 1000px;
	height: 800px;
	text-align: center;
	margin: auto;
	border-radius: 20px;
	padding-top: 50px;
}
.datePicker {
	width: 530px;
	float: left;
}
.date {
	display: inline-block;
}
.addressDiv {
	width: 500px;
}
.date p {
	float: left;
}
.campingName input {
	width: 550px;
}
.addressDiv {
	margin: auto;

	width: 50%;
}

.planName input {
	width: 455px;
}
.planOpen select {
	width: 540px;
	height: 30px;
}
.planDestination select {
	width: 570px;
	height: 30px;
}
.planType select {
	width: 570px;
	height: 30px;
}
.planNumber {
	font-size: 30px;
	display: inline-block;
}
.planNumber p {
	float: left;
}
.planNumber button {
	float: left;
}
.planBudget input {
	width: 535px;
}

.w-btn-outline {
	position: relative;
	padding: 12px 23px;
	border-radius: 15px;
	font-family: 'paybooc-Light', sans-serif;
	box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
	text-decoration: none;
	font-weight: 600;
	transition: 0.25s;
	float: right;
	margin-right: 30px;
}
.w-btn-blue-outline {
	border: 3px solid #6aafe6;
	color: #6e6e6e;
}
.w-btn-blue-outline:hover {
	background-color: #6aafe6;
	color: #d4dfe6;
}
.w-btn-outline:hover {
	letter-spacing: 2px;
	transform: scale(1.2);
	cursor: pointer;
}
.w-btn-outline:active {
	transform: scale(1.5);
}
.w-btn {
	position: relative;
	border: none;
	display: inline-block;
	border-radius: 5px;
	text-align: center;
	font-family: 'paybooc-Light', sans-serif;
	box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
	text-decoration: none;
	font-weight: 600;
	transition: 0.25s;
	margin-left: 10px;
	margin-right: 5px;
}
.w-btn-blue {
	background-color: #d3dfe9;
	color: #d4dfe6;
}
.w-btn:hover {
	letter-spacing: 2px;
	transform: scale(1.2);
	cursor: pointer;
}
.w-btn:active {
	transform: scale(1.5);
}

.tagButton {
	background-color: rgb(198, 217, 233);
	border: none;
	border-radius: 5px;
	margin-right: 10px;
}
.tagButton:hover {
	letter-spacing: 2px;
	transform: scale(1.5);
	cursor: pointer;
	border-radius: 5px;
}
</style>
