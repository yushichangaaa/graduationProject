<template>
  <div class="dashboard-container">
    <div class="dashboard-text">您好:{{ name }}</div>
    <!-- <el-button @click="abc()">倒计时</el-button>
    <span>剩余{{ time }}</span> -->
  </div>
</template>

<script>
import { getName } from '@/utils/auth'
var min = 0
var seconds = 0
export default {
  name: 'Dashboard',
  data() {
    return {
      dialogFormVisible: false,
      name: getName(),
      count: 100,
      time: ''
    }
  },
  methods: {
    abc() {
      setInterval(() => {
        this.count--
        min = Math.floor(this.count / 60)
        min = min < 10 ? '0' + min : min
        seconds = this.count % 60
        this.time = min + ':' + seconds
      }, 1000)
    }
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
