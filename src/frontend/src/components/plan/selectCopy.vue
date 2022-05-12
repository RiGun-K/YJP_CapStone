<template>
	<h2>다른 유저들의 계획을 참조할 수 있습니다.</h2>
	<button @click="reference">참조하기</button>

	<h2>자신만의 플랜을 만들 수 있습니다.</h2>
	<button @click="individual">참조하지 않음</button>
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
			this.$router.push({ name: 'basicPlan' });
		},
	},
};
</script>

<style></style>
