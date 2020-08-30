<template>
  <div class="ShoppingCar">
    <el-editable
      ref="editable"
      :data.sync="foodList"
      :height="tableHeight"
      element-loading-text="Loading"
      class="manual-table1"
      border
      fit
      highlight-current-row
      style="width: 100%">
      <el-editable-column align="center" label="菜品图片">
        <template slot-scope="scope">
          <div><img :src="foodList[scope.$index].picture" class="picture"></div>
        </template>
      </el-editable-column>
      <el-editable-column prop="name" align="center" label="菜品名称" min-width="120" show-overflow-tooltip/>
      <el-editable-column prop="describe" align="center" label="菜品描述" min-width="145" show-overflow-tooltip/>
      <el-editable-column prop="price" align="center" label="菜品价格" min-width="235" show-overflow-tooltip/>
      <el-editable-column align="center" label="已经购买的数量" min-width="235">
        <template slot-scope="scope">
          <el-input-number v-model="foodList[scope.$index].count" :min="0" :max="10000" @change="(val) => handleChange(val, scope.$index)"/>
        </template>
      </el-editable-column>
      <el-editable-column align="center" label="操作" min-width="320">
        <template slot-scope="scope">
          <el-button :plain="true" type="danger" size="small" @click="open(scope.$index)">取消购买</el-button>
        </template>
      </el-editable-column>
    </el-editable>
    <div>
      <el-dialog :visible.sync="visible" title="支付订单">
        <el-dialog
          :visible.sync="visible2"
          width="30%"
          title="请选择使用优惠券"
          append-to-body>
          <el-editable
            ref="editable"
            :data.sync="manjianList"
            :height="tableHeight"
            element-loading-text="Loading"
            class="manual-table1"
            border
            fit
            highlight-current-row
            style="width: 100%">
            <el-editable-column prop="manjian" align="center" label="优惠券" min-width="225" show-overflow-tooltip/>
            <el-editable-column align="center" label="操作" min-width="100">
              <template slot-scope="scope">
                <el-button :plain="true" type="primary" size="small" @click="userCoupon(scope.$index)">使用</el-button>
              </template>
            </el-editable-column>
          </el-editable>
        </el-dialog>
        <h4 align="center">您需要支付{{ total - jian }}￥</h4>
        <h5 v-if="visible3" align="center">您用优惠券优惠了{{ jian }}￥</h5>
        <el-button style="margin-left: 43%" type="primary" @click="createOrder()">立即支付</el-button>
        <el-button @click="showCoupons()">使用优惠券</el-button>
      </el-dialog>
    </div>
    <h4 style="margin-left: 25%">合计: {{ total }}￥</h4>
    <el-button type="primary" style="margin-left: 75%" @click="pay()">结账</el-button>
  </div>
</template>

<script>
import { getPhone, getName } from '@/utils/auth'
export default {
  inject: ['reload'],
  name: 'ShoppingCar',
  data() {
    return {
      list: JSON.parse(localStorage.getItem('list')),
      countList: (!JSON.parse(localStorage.getItem('foodCountList')) && typeof (JSON.parse(localStorage.getItem('foodCountList'))) !== 'undefined' && JSON.parse(localStorage.getItem('foodCountList')) !== 0) ? [] : JSON.parse(localStorage.getItem('foodCountList')),
      tableHeight: 500,
      total: 0,
      foodList: [],
      allList: [],
      visible: false,
      manjianList: [],
      visible2: false,
      manList: [],
      jianList: [],
      visible3: false,
      jian: 0
    }
  },
  watch: {
  },
  created() {
    // localStorage.removeItem('uidList')
    var b = this.list
    for (var i = 0; i < this.list.length; i++) {
      this.allList.push({
        count: this.countList[i].foodCount,
        id: b[i].id,
        picture: '..\\..\\' + b[i].picture,
        name: b[i].name,
        describe: b[i].describe,
        price: b[i].price
      })
      if (this.countList[i].foodCount === 0) {
        continue
      }
      this.foodList.push({
        count: this.countList[i].foodCount,
        id: b[i].id,
        picture: '..\\..\\' + b[i].picture,
        name: b[i].name,
        describe: b[i].describe,
        price: b[i].price
      })
    }
    this.getTotal()
  },
  methods: {
    userCoupon(index) {
      this.visible3 = true
      this.jian = this.jianList[index].jian
      this.visible2 = false
    },
    showCoupons() {
      this.manjianList = []
      this.manList = []
      this.jianList = []
      this.$http.post('http://127.0.0.1:7788/user2/getCoupon', {
        phone: getPhone()
      }, { emulateJSON: true }).then(function(res) {
        var b = res.data.coupons
        for (var i = 0; i < b.length; i++) {
          if (this.total > b[i][0]) {
            this.manjianList.push({
              manjian: '满' + b[i][0] + '减' + b[i][1]
            })
            this.manList.push({
              man: b[i][0]
            })
            this.jianList.push({
              jian: b[i][1]
            })
          }
        }
        // var map = new Map()
        // map = b[0]
        // map.forEach(function(value, index) {
        // })
      }, function(res) {
      })
      this.visible2 = true
    },
    createOrder() {
      var p = getPhone()
      this.$http.post('http://127.0.0.1:8764/order/createOrder', {
        name: getName(),
        phone: p,
        jsonList: JSON.stringify(this.foodList),
        price: this.total - this.jian,
        address: localStorage.getItem('localAddress')
      }, { emulateJSON: true }).then(function(res) {
        if (res.data.code === 20000) {
          var uidList = JSON.parse(localStorage.getItem('uidList'))
          if (!uidList && typeof (uidList) !== 'undefined' && uidList !== 0) {
            alert(2)
            var list = []
            list.push({
              uid: res.data.uid
            })
            localStorage.setItem('uidList', JSON.stringify(list))
          } else {
            uidList.push({
              uid: res.data.uid
            })
            localStorage.setItem('uidList', JSON.stringify(uidList))
          }
          localStorage.removeItem('foodCountList')
          this.jumpPage('/order/index')
        }
      }, function(res) {
      })
    },
    pay() {
      if (typeof getPhone() === 'undefined') {
        this.$message('您还没有登陆啊，请登陆之后再进行其他操作')
      } else {
        this.visible = true
      }
    },
    jumpPage(page) {
      this.$router.push({ path: page })
    },
    handleChange(val, index) {
      if (val === 0) {
        this.delete(index)
      }
      var n = this.foodList[index].name
      var b = this.allList
      for (var i = 0; i < this.allList.length; i++) {
        if (b[i].name === n) {
          this.countList[i].foodCount = val
          localStorage.setItem('foodCountList', JSON.stringify(this.countList))
        }
      }
      this.getTotal()
      this.reload()
    },
    open(index) {
      this.$confirm('是否删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.delete(index)
        setTimeout(() => {
          this.open2()
        }, 500)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    open2() {
      this.$message('成功')
    },
    delete(index) {
      var n = this.foodList[index].name
      var b = this.allList
      for (var i = 0; i < this.allList.length; i++) {
        if (b[i].name === n) {
          this.countList[i].foodCount = 0
          localStorage.setItem('foodCountList', JSON.stringify(this.countList))
        }
      }
      this.reload()
    },
    getTotal() {
      this.total = 0
      var b = JSON.parse(localStorage.getItem('foodCountList'))
      for (var i = 0; i < this.list.length; i++) {
        if (b[i].foodCount !== 0) {
          this.total = this.total + b[i].foodCount * this.list[i].price
        }
      }
    }
  }
}
</script>
