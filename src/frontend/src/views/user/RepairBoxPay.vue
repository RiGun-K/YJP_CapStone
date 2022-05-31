<template>
  <div>
    <h5>pay</h5>
    <div>
      <h5>user</h5>
      <div>

      </div>
    </div>
    <div>
      <h5>storage</h5>
      <div>

      </div>
    </div>
    <div>
      <h5>item</h5>
      <div>

      </div>
    </div>
    <div>
      <h5>option</h5>
      <div>

      </div>
    </div>
    <div>
      <h5>price</h5>
      <div>
        <button>pay</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "RepairBoxPay",
  mounted() {
    console.log(this.$store.state.careItemInfo)
  },
  data(){
    return{

    }
  },
  methods:{

  },
}
</script>

<style scoped>

</style>