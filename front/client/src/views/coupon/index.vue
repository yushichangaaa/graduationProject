<template>
  <div class="app-container">
    <el-editable
      ref="editable"
      :data.sync="manjianList"
      :height="tableHeight"
      :edit-config="{trigger: 'click', mode: 'row',isTabKey: true}"
      element-loading-text="Loading"
      class="manual-table1"
      border
      fit
      highlight-current-row
      style="width: 100%">
      <el-editable-column prop="manjian" align="center" label="优惠券" min-width="225" show-overflow-tooltip/>
      <el-editable-column align="center" label="操作" min-width="100">
        <template>
          <el-button :plain="true" type="primary" size="small" @click="jumpPage('/aaa/dish')">立即使用</el-button>
        </template>
      </el-editable-column>
    </el-editable>
  </div>
</template>
<script>
import { getPhone } from '@/utils/auth'
export default {
  data() {
    return {
      manjianList: []
    }
  },
  watch: {
  },
  created() {
    this.getCouponList()
    console.log(this.manjianList)
  },
  methods: {
    jumpPage(page) {
      this.$router.push({ path: page })
    },
    getCouponList() {
      this.manList = []
      this.jianList = []
      this.$http.post('http://127.0.0.1:7788/user2/getCoupon', {
        phone: getPhone()
      }, { emulateJSON: true }).then(function(res) {
        var b = res.data.coupons
        for (var i = 0; i < b.length; i++) {
          this.manjianList.push({
            manjian: '满' + b[i][0] + '减' + b[i][1]
          })
        }
        // var map = new Map()
        // map = b[0]
        // map.forEach(function(value, index) {
        // })
      }, function(res) {
      })
    }
  }
}
</script>

