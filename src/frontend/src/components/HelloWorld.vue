<template>
	<link
		rel="canonical"
		href="https://getbootstrap.com/docs/5.2/examples/blog/"
	/>

	<main class="container d-flex flex-wrap">
		<div
			id="carouselExampleDark"
			class="carousel carousel-dark slide"
			data-bs-ride="carousel"
		>
			<div class="carousel-indicators">
				<button
					type="button"
					data-bs-target="#carouselExampleDark"
					data-bs-slide-to="0"
					class="active"
					aria-current="true"
					aria-label="Slide 1"
				></button>
				<button
					type="button"
					data-bs-target="#carouselExampleDark"
					data-bs-slide-to="1"
					aria-label="Slide 2"
				></button>
				<button
					type="button"
					data-bs-target="#carouselExampleDark"
					data-bs-slide-to="2"
					aria-label="Slide 3"
				></button>
			</div>
			<div class="carousel-inner">
				<div class="carousel-item active" data-bs-interval="4000">
					<a href="/TeamManagementPage" class="text-white fw-bold">
						<img
							:src="require('@/assets/img_4.png')"
							class="d-block w-100"
						/>
						<div
							class="carousel-caption d-none d-md-block"
							style="height: 300px"
						>
							<h1
								class="display-4 fst-italic"
								style="color: black"
							>
								Camping Planner
							</h1>
							<p class="lead my-3" style="color: black">
								Why don't we go camping in the suburbs for a
								night?
							</p>
						</div>
					</a>
				</div>
				<div class="carousel-item" data-bs-interval="4000">
					<a href="/storageView" class="text-white fw-bold">
						<img
							:src="require('@/assets/img_2.png')"
							class="d-block w-100"
						/>
						<div
							class="carousel-caption d-none d-md-block"
							style="height: 300px"
						>
							<div
								style="
									background-color: rgba(20, 20, 40, 0.5);
									padding: 10px;
								"
							>
								<h1
									class="display-4 fst-italic"
									style="color: white"
								>
									Camping Storage
								</h1>
								<p class="lead my-3" style="color: white">
									We packed equipment for our camping trip
								</p>
							</div>
						</div>
					</a>
				</div>
				<div class="carousel-item" data-bs-interval="4000">
					<a href="/infoter" class="text-white fw-bold">
						<img
							:src="require('@/assets/img_1.png')"
							class="d-block w-100"
						/>
						<div
							class="carousel-caption d-none d-md-block"
							style="height: 300px"
						>
							<h1
								class="display-4 fst-italic"
								style="color: white"
							>
								Camping Reservation
							</h1>
							<p class="lead my-3" style="color: white">
								Are there any good places to visit around there?
							</p>
						</div>
					</a>
				</div>
			</div>
			<button
				class="carousel-control-prev"
				type="button"
				data-bs-target="#carouselExampleDark"
				data-bs-slide="prev"
			>
				<span
					class="carousel-control-prev-icon"
					aria-hidden="true"
				></span>
				<span class="visually-hidden">Previous</span>
			</button>
			<button
				class="carousel-control-next"
				type="button"
				data-bs-target="#carouselExampleDark"
				data-bs-slide="next"
			>
				<span
					class="carousel-control-next-icon"
					aria-hidden="true"
				></span>
				<span class="visually-hidden">Next</span>
			</button>
		</div>

		<div class="row mb-2" style="margin-top: 50px; width: 1500px">
			<div class="col-md-6">
				<div
					class="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative"
				>
					<div class="col p-4 d-flex flex-column position-static">
						<strong class="d-inline-block mb-2 text-primary"
							>인기 캠핑플랜</strong
						>
						<h3 class="mb-0" style="padding-bottom: 10px">
							The best of plan Top 5
						</h3>

						<div class="table-responsive">
							<table class="table text-left">
								<thead
									style="background-color: rgb(230, 230, 230)"
								>
									<tr>
										<th>Plan Name</th>
										<th>Area</th>
										<th>Copy</th>
										<th>Team Name</th>
									</tr>
								</thead>
								<tbody>
									<tr
										style="text-align: center"
										v-for="(
											value, index
										) in planRankingList"
										:key="index"
									>
										<th scope="row" class="text-start">
											{{ value.planName }}
										</th>
										<th scope="row" class="text-start">
											{{ value.planDestination }}
										</th>
										<th scope="row" class="text-start">
											{{ value.planUsedCount }}
										</th>
										<th scope="row" class="text-start">
											{{ value.teamCode.teamName }}
										</th>
									</tr>
								</tbody>
							</table>
						</div>

						<a href="#" class="stretched-link">more about</a>
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div
					class="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative"
				>
					<div class="col p-4 d-flex flex-column position-static">
						<strong class="d-inline-block mb-2 text-primary"
							>인기캠핑장</strong
						>
						<h3 class="mb-0" style="padding-bottom: 10px">
							The best of campsite Top 5
						</h3>

						<div class="table-responsive">
							<table class="table text-left">
								<thead
									style="background-color: rgb(230, 230, 230)"
								>
									<tr>
										<th>Camping Name</th>
										<th>Address</th>
										<th>Available Room</th>
									</tr>
								</thead>
								<tbody>
									<tr
										style="text-align: center"
										v-for="(
											value, index
										) in campingRankingList"
										:key="index"
									>
										<th scope="row" class="text-start">
											{{ value.campingName }}
										</th>
										<th scope="row" class="text-start">
											{{ value.address }}
										</th>
										<th scope="row" class="text-start">
											{{ value.campingDetailState }}
										</th>
									</tr>
								</tbody>
							</table>
						</div>

						<a href="#" class="stretched-link">more about</a>
					</div>
				</div>
			</div>
		</div>
	</main>
	<div>
		<p v-for="(value, index) in planRankingList" :key="index">
			{{ value.planName }}
		</p>
	</div>
	<div>
		<p v-for="(value, index) in campingRankingList" :key="index">
			{{ value.campingName }}
		</p>
	</div>
</template>

<script>
import axios from 'axios';

export default {
	name: 'HelloWorld',
	props: {
		msg: String,
	},
	data() {
		return {
			planRankingList: [],
			campingRankingList: [],
		};
	},
	created() {
		this.planRanking();
		this.campingRanking();
	},
	methods: {
		planRanking: function () {
			const url = 'api/planRanking';
			axios
				.get(url)
				.then((response) => {
					response.data.map((item) => {
						this.planRankingList.push(item);
					});
				})
				.catch((error) => {});
		},
		campingRanking: function () {
			const url = 'api/campingRanking';
			axios
				.get(url)
				.then((response) => {
					console.log(response);
					response.data.map((item) => {
						this.campingRankingList.push(item);
					});
				})
				.catch((error) => {});
		},
	},
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.bd-placeholder-img {
	font-size: 1.125rem;
	text-anchor: middle;
	-webkit-user-select: none;
	-moz-user-select: none;
	user-select: none;
}

@media screen and (min-width: 576px) {
	.container {
		max-width: 540px;
	}
}

@media screen and (min-width: 768px) {
	.container {
		max-width: 720px;
	}
}

@media screen and (min-width: 992px) {
	.container {
		max-width: 960px;
	}
}

@media screen and (min-width: 1200px) {
	.container {
		max-width: 1140px;
	}
}

@media screen and (min-width: 1400px) {
	.container {
		max-width: 1320px;
	}
}
</style>
