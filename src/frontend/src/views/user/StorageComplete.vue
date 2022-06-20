<template>
<div>
  <h3>결제가 완료 되었습니다.</h3>
  <button @click="$router.push('/')">홈으로</button>
  <button @click="$router.push({name:'myBox'})">내 보관함</button>
</div>
</template>

<script>
export default {
  name: "StorageComplete"
}
</script>

<style scoped>

</style>