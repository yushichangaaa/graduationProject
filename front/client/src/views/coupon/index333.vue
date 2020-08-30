<template>
  <div class="app-container">
    <el-switch
      v-model="value3"
      style="display: block"
      active-color="#13ce66"
      inactive-color="#ff4949"
      active-text="安全"
      inactive-text="危险"/>
    <span>当前浓度</span>
    <input v-model="value">
    <span>当前温度</span>
    <input v-model="template">
  </div>
</template>
<script>
export default {
  data() {
    return {
      value3: true,
      value: 0,
      template: 0
    }
  },
  created() {
    setInterval(() => {
      this.getValue()
      this.getTemplate()
    }, 2000)
  },
  methods: {
    getValue() {
      this.$http.post('调用后端单片机拿到数据',
        { emulateJSON: true }).then(function(res) {
        console.log(res.data)
        this.value = res.data // 将单片机拿到的数据展示到页面
        if (res.data >= 0.4) { // 判断浓度是否大于0.4  大于 将value3赋值为false  页面按钮报红
          this.value3 = false
        }
      }, function(res) {
      })
    },
    getTemplate() {
      this.$http.post('调用后端单片机拿到数据',
        { emulateJSON: true }).then(function(res) {
        console.log(res.data)
        this.template = res.data // 将单片机拿到的数据展示到页面
      }, function(res) {
      })
    }
  }
}
</script>

