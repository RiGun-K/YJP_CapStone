<template>
	<div class="background">
		<div class="MenuPosition">
			<div class="SelectMenu">
				<button class="SelectBtn" @click="reference">
					<img src="@\assets\diversity.png" alt="" />
				</button>
				<div class="MenuInfoBlock">
					<p>다른 유저들의 계획을 참조할 수 있습니다.</p>
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
.background {
	width: 100vw;
	height: 100vh;
	background-color: #84bdff;
}

.MenuPosition {
	position: absolute;
	width: 100%;
	left: 50%;
	top: calc(50% + 150px);
	transform: translate(-50%, -50%);
	text-align: center;
}

.SelectMenu {
	position: relative;
	display: inline-block;
	width: 320px;
	margin: 30px;
	background: white;
	padding: 15px;
	border-radius: 10px;
}

.SelectBtn {
	color: yellow;
	background-color: rgb(160, 233, 193);
	border: none;
	width: 100%;
	padding: 20px;
	border-radius: 20px;
}

.SelectBtn img {
	width: 100%;
}

.MenuInfoBlock {
	width: 100%;
	margin-top: 20px;
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
}
</style>
