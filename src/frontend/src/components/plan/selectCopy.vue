<template>

   	<div class="background">
		<!-- <img class="imgbackground1" src="@/assets/campwall2.webp" /> -->
		<div class="MenuPosition">
			<div class="SelectMenu">
				<button class="SelectBtn" @click="reference">
					<img src="@\assets\diversity.png" alt="" />
				</button>
				<div class="MenuInfoBlock">
					<p>다른 유저들의 계획을 참조합니다.</p>
				</div>
			</div>
			<div class="SelectMenu">
				<button class="SelectBtn" @click="individual">
					<img src="@\assets\idea.png" alt="" />
				</button>
				<div class="MenuInfoBlock">
					<p>자신만의 플랜을 만들 수 있습니다.</p>
				</div>
			</div>
		</div>
	</div>


</template>

<script>
export default {
	data() {
		return {};
	},
	methods: {
		reference: function () {
			this.$router.push({ name: 'planList' });
		},
		individual: function () {
			const mcode = this.$store.getters.getLoginState.mcode;
			this.$router.push('/myReservation/' + mcode);
		},
	},
};
</script>

<style>


.imgbackground1 {
	width: 100%;
	object-fit: cover;
}
.background{
	width: 500px;
	height: 500px;
}

.MenuPosition {
	position: absolute;
	width: 100%;
	left: 50%;
	top: calc(50% + 150px);
	transform: translate(-50%, -50%);
	text-align: center;
  margin-top: -220px;

}

.SelectMenu {
	position: relative;
	display: inline-block;
	width: 220px;
	margin: 60px;
	background: white;
	padding: 15px;
	border-radius: 20px;
	box-shadow: 2px 2px 10px rgb(58, 58, 58);
}

.SelectBtn {
	color: yellow;
	background-color: rgb(160, 233, 193);
	border: none;
	width: 100%;
	padding: 40px;
	border-radius: 20px;
}

.SelectBtn img {
	width: 100%;
}

.MenuInfoBlock {
	width: 100%;
	margin-top: 10px;
	height: 40px;
	/* background: #525252; */
	border-radius: 20px;
	border: none;
	text-align: center;
}

.MenuInfoBlock p {
	position: relative;
	left: 50%;
	top: 50%;
	transform: translate(-50%, -50%);
	white-space: nowrap;
	font-weight: 700;
	font-size: 13px;
}
    .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
</style>
