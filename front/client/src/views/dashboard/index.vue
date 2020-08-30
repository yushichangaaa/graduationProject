<template>
  <div class="dashboard-container">
    <div class="dashboard-text">您好 {{ phone }}</div>
  </div>
</template>

<script>
import { getPhone } from '@/utils/auth'
export default {
  name: 'Dashboard',
  data() {
    return {
      phone: getPhone()
    }
  },
  methods: {
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
