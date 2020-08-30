<template>
  <div class="success-container">
    <span>激活成功，还有{{ count }} 秒跳转界面</span>
    <span class="link-type" @click="jump()">点此立即跳转</span>
  </div>
</template>

<script>
export default {
  name: 'Success',
  data() {
    return {
      count: 10
    }
  },
  watch: {
    count: function(val, oldVal) {
      if (val === 0) {
        this.$router.push({ path: '/dashboard' })
      }
    }
  },
  created() {
    setInterval(() => {
      this.count--
    }, 1000)
  },
  methods: {
    jump() {
      this.$router.push({ path: '/dashboard' })
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
.link-type,
.link-type:focus {
  color: #337ab7;
  cursor: pointer;
  &:hover {
    color: rgb(32, 160, 255);
  }
}
</style>
