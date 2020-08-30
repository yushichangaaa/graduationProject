<template>
  <div class="app-container">
    <el-form ref="formData" :inline="true" :model="formData" label-width="78px" size="mini">
      <el-form-item label="模糊查询" prop="name">
        <el-input v-model="formData.name"/>
      </el-form-item>
      <el-form-item style="margin-left: 85%">
        <el-button type="primary" @click="selectByCondition()">查询</el-button>
      </el-form-item>
    </el-form>
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
      <el-editable-column :edit-render="{name: 'ElInput'}" prop="name" align="center" label="菜品名称" min-width="120" show-overflow-tooltip/>
      <el-editable-column :edit-render="{name: 'ElInput'}" prop="describe" align="center" label="菜品描述" min-width="145" show-overflow-tooltip/>
      <el-editable-column :edit-render="{name: 'ElInput'}" prop="price" align="center" label="菜品价格" min-width="235" show-overflow-tooltip/>
      <el-editable-column align="center" label="操作" min-width="320">
        <template slot-scope="scope">
          <el-button :plain="true" type="warning" size="small" @click="edit(scope.$index)">修改</el-button>
          <el-button type="danger" size="small" @click="open(scope.$index)">删除</el-button>
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
  </div>
</template>
<script>
import { getFoodList, editFood, deleteFood } from '@/api/master'
export default {
  data() {
    return {
      tableHeight: 500,
      foodList: [],
      formData: {
        name: ''
      },
      code: 404,
      myListener: 0,
      msg: '',
      page: 1,
      rows: 5,
      total: 0
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
    this.getFoodlist()
  },
  methods: {
    selectByCondition() {
      this.foodList = []
      this.$http.post('http://127.0.0.1:8763/findByLikeName', {
        name: this.formData.name
      }, { emulateJSON: true }).then(function(res) {
        var b = res.body.list
        for (var i = 0; i < b.length; i++) {
          this.foodList.push({
            id: b[i].id,
            picture: '..\\..\\' + b[i].picture,
            name: b[i].name,
            describe: b[i].describe,
            price: b[i].price
          })
        }
      }, function(res) {
        alert(res.status)
      })
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
    deleteFood(index) {
      deleteFood(index).then(res => {
        this.code = res.code
      })
    },
    edit(index) {
      this.code = 404
      var b = this.foodList[index]
      editFood(b.id, b.name, b.describe, b.price)
        .then(res => {
          this.code = res.code
          this.open3()
          this.addMyListener()
        })
      // this.$http.post('http://127.0.0.1:9999/editFood', {
      //   id: b.id,
      //   picture: b.picture,
      //   name: b.name,
      //   describe: b.describe,
      //   price: b.price
      // }, {
      //   emulateJSON: true
      // }).then(function(res) {
      //   console.log(res.data)
      // }, function(res) {
      //   alert(res.status)
      // })
    },
    open(index) {
      this.code = 404
      this.$confirm('是否删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteFood(this.foodList[index].id)
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
      if (this.code === 20000) {
        this.$message({
          type: 'success',
          message: '删除成功!'
        })
        this.addMyListener()
      } else {
        this.$message({
          type: 'info',
          message: '删除失败!' + this.msg
        })
      }
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
        this.total = res.total
        this.foodList = []
        var b = res.list
        for (var i = 0; i < b.length; i++) {
          this.foodList.push({
            id: b[i].id,
            picture: '..\\..\\' + b[i].picture,
            name: b[i].name,
            describe: b[i].describe,
            price: b[i].price
          })
        }
      })
      // setTimeout(() => {
      // }, 500)
    }
  }
}
</script>

