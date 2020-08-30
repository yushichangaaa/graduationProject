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
      <el-editable-column align="center" label="菜品图片">
        <template slot-scope="scope">
          <div><img :src="foodList[scope.$index].picture" class="picture"></div>
        </template>
      </el-editable-column>
      <el-editable-column prop="name" align="center" label="菜品名称" min-width="120" show-overflow-tooltip/>
      <el-editable-column prop="describe" align="center" label="菜品描述" min-width="145" show-overflow-tooltip/>
      <el-editable-column prop="price" align="center" label="菜品价格" min-width="235" show-overflow-tooltip/>
      <el-editable-column align="center" label="操作" min-width="100">
        <template slot-scope="scope">
          <el-button v-if="just(scope.$index)" :plain="true" type="primary" size="small" @click="add(scope.$index)">添加购物车</el-button>
          <el-input-number v-else v-model="countList[scope.$index + rows * (page - 1) ].foodCount" :min="0" :max="10000" @change="(val) => handleChange(val, scope.$index)"/>
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
import { getFoodList, getFoodList2 } from '@/api/master'
export default {
  data() {
    return {
      tableHeight: 500,
      foodList: [],
      foodList2: [],
      code: 404,
      myListener: 0,
      msg: '',
      page: 1,
      rows: 5,
      total: 0,
      countList: JSON.parse(localStorage.getItem('foodCountList'))
    }
  },
  watch: {
    countList: function() {
      alert(1)
    },
    myListener: function() {
      this.getFoodlist()
    },
    deep: true
  },
  created() {
    // var tmp = JSON.parse(localStorage.getItem('list'))
    // console.log('8888888888   ' + tmp[0].name)
    // localStorage.removeItem('foodCountList')
    // localStorage.removeItem('list')
    this.getFoodlist2()
    this.getFoodlist()
    console.log(this.countList[1])
    console.log(JSON.parse(localStorage.getItem('foodCountList')))
  },
  methods: {
    just(index) {
      index = index + this.rows * (this.page - 1)
      return this.countList[index].foodCount === 0
    },
    handleChange(val, index) {
      // 修改localStorage 里面的foodCount
      index = index + this.rows * (this.page - 1)
      console.log('$$$' + val + '^^^' + index)
      var arr = JSON.parse(localStorage.getItem('foodCountList'))
      arr[index].foodCount = val
      localStorage.setItem('foodCountList', JSON.stringify(arr))
    },
    add(index) {
      index = index + this.rows * (this.page - 1)
      console.log('wwwwwwwwwwwwww' + index)
      var arr = JSON.parse(localStorage.getItem('foodCountList'))
      arr[index].foodCount = arr[index].foodCount + 1
      localStorage.setItem('foodCountList', JSON.stringify(arr))
      console.log('******' + JSON.parse(localStorage.getItem('foodCountList'))[index].foodCount)
      this.countList[index].foodCount = this.countList[index].foodCount + 1
      // var carMap = localStorage.getItem('map')
      // console.log('%%%%%' + carMap.get(this.foodList[index].name))
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
    getFoodlist2() {
      getFoodList2().then(res => {
        console.log('@@@@@@@@' + res.data.list.length)
        var b = res.data.list
        for (var i = 0; i < b.length; i++) {
          this.foodList2.push({
            id: b[i].id,
            picture: b[i].picture,
            name: b[i].name,
            describe: b[i].describe,
            price: b[i].price
          })
        }
        setTimeout(() => {
          var tmp = JSON.parse(localStorage.getItem('list'))
          if (!tmp && typeof (tmp) !== 'undefined' && tmp !== 0) {
            // alert(1)
            localStorage.setItem('list', JSON.stringify(this.foodList2))
          }
        }, 500)
      })
    },
    getFoodlist() {
      getFoodList(this.page, this.rows).then(res => {
        var ar = JSON.parse(localStorage.getItem('foodCountList'))
        this.total = res.data.total
        this.foodList = []
        // console.log(res)
        var b = res.data.list
        console.log('*******' + ar)
        if (!ar && typeof (ar) !== 'undefined' && ar !== 0) {
          // alert(1)
          console.log('*******' + this.total)
          var array = []
          for (var j = 0; j < this.total; j++) {
            array.push({
              foodCount: 0
            })
          }
          localStorage.setItem('foodCountList', JSON.stringify(array))
        }
        // 服务端添加菜品之后 countList的设置还是有问题 还是需要 localStorage.remove一下
        if (ar.length < this.total) {
          var n = this.total - ar.length
          var array2 = ar
          for (var y = 0; y < n; y++) {
            array2.push({
              foodCount: 0
            })
          }
          localStorage.setItem('foodCountList', JSON.stringify(array2))
        }
        for (var i = 0; i < b.length; i++) {
          if (this.page !== 1) {
            var w = i
            w = w + this.rows * (this.page - 1)
            this.foodList.push({
              count: ar[w].foodCount,
              id: b[i].id,
              picture: '..\\..\\' + b[i].picture,
              name: b[i].name,
              describe: b[i].describe,
              price: b[i].price
            })
          } else {
            this.foodList.push({
              count: ar[i].foodCount,
              id: b[i].id,
              picture: '..\\..\\' + b[i].picture,
              name: b[i].name,
              describe: b[i].describe,
              price: b[i].price
            })
          }
        }
      })
      this.reload()
    }
  }
}
</script>

