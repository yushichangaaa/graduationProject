<template>
  <div class="app-container">
    <el-editable
      ref="editable"
      :data.sync="foodList"
      :height="tableHeight"
      :edit-config="{trigger: 'click', mode: 'row',isTabKey: true}"
      element-loading-text="Loading"
      class="manual-table1"
      border
      fit
      highlight-current-row
      style="width: 100%">
      <el-editable-column prop="picture" align="center" label="菜品图片" min-width="225" show-overflow-tooltip/>
      <el-editable-column prop="name" align="center" label="菜品名称" min-width="120" show-overflow-tooltip/>
      <el-editable-column prop="describe" align="center" label="菜品描述" min-width="145" show-overflow-tooltip/>
      <el-editable-column prop="price" align="center" label="菜品价格" min-width="235" show-overflow-tooltip/>
      <el-editable-column align="center" label="操作" min-width="320">
        <template slot-scope="scope">
          <el-button :plain="true" type="primary" size="small" @click="add(scope.$index)">添加购物车</el-button>
        </template>
      </el-editable-column>
    </el-editable>
    <el-pagination
      :current-page.sync="page"
      :page-sizes="[5, 10, 20, 50]"
      :page-size="rows"
      :total="total"
      layout="total, sizes, prev, pager, next, jumper"
      @current-change="handleCurrentChange"
      @size-change="handleSizeChange"/>
    <el-button @click="jumpPage('/aaa/shoppingCar')">查看购物车</el-button>
  </div>
</template>
<script>
// var map = new Map()
import { getFoodList } from '@/api/master'
import { getShoppingCar } from '@/utils/auth'
export default {
  data() {
    return {
      tableHeight: 500,
      foodList: [],
      code: 404,
      myListener: 0,
      msg: '',
      page: 1,
      rows: 5,
      total: 0,
      // shoppingCar: getShoppingCar(),
      // initMap: map,
      countList: localStorage.getItem('foodCountList') // 此为  每个食物的添加购物车的数量
    }
  },
  watch: {
    myListener: function() {
      this.getFoodlist()
      // setTimeout(() => {
      //   this.getFoodlist()
      // }, 500)
      // 刷新表格用
    },
    deep: true
  },
  created() {
    this.getFoodList()
    console.log(this.countList)
    if (typeof this.countList === 'undefined') {
      var array = []
      for (var i = 0; i < this.total; i++) {
        array.push({
          foodCount: 0
        })
      }
      localStorage.setItem('foodCountList', JSON.stringify(array))
    }
    if (this.countList.length <= this.total) {
      var n = this.total - this.countList.length
      var array2 = JSON.parse(localStorage.getItem('foodCountList'))
      for (var y = 0; y < n; y++) {
        array2.push({
          foodCount: 0
        })
      }
    }
  },
  methods: {
    add(index) {
      // var map = getShoppingCar()
      // map.set(this.foodList[index].name, )
      console.log('*****' + getShoppingCar())
    },
    jumpPage(page) {
      this.$router.push({ path: page })
    },
    handleSizeChange(pageSize) {
      this.rows = pageSize
      this.getFoodlist()
    },
    handleCurrentChange(currentPage) {
      this.page = currentPage
      this.getFoodlist()
    },
    addMyListener() {
      this.myListener++
    },
    open3(msg) {
      if (this.code === 20000) {
        this.$message({
          type: 'success',
          message: '操作成功!'
        })
        this.addMyListener()
      } else {
        this.$message({
          type: 'info',
          message: '操作失败!' + this.msg
        })
      }
    },
    getFoodlist() {
      getFoodList(this.page, this.rows).then(res => {
        this.total = res.data.total
        var b = res.data.list
        for (var i = 0; i < b.length; i++) {
          this.foodList.push({
            count: this.countList[i].count,
            id: b[i].id,
            picture: b[i].picture,
            name: b[i].name,
            describe: b[i].describe,
            price: b[i].price
          })
        }
      })
    }
  }
}
</script>

