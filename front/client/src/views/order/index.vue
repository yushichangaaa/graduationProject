<template>
  <div class="app-container">
    <h4>订单详情页</h4>
    <el-editable
      ref="editable"
      :data.sync="orderList"
      :height="550"
      element-loading-text="Loading"
      class="manual-table1"
      border
      fit
      highlight-current-row
      style="width: 100%">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="用户姓名">
              <span>{{ props.row.data.name }}</span>
            </el-form-item>
            <el-form-item label="电话号码">
              <span>{{ props.row.data.phone }}</span>
            </el-form-item>
            <el-form-item label="购买信息">
              <!-- <div v-for="(key,index) in props.row.data.foodlist" :key="index">
                <span>菜品图片  {{ props.row.data.foodlist[index].picture }}</span>
                <span>菜品名称  {{ props.row.data.foodlist[index].name }}</span>
                <span>菜品描述  {{ props.row.data.foodlist[index].describe }}</span>
                <span>菜品价格  {{ props.row.data.foodlist[index].price }}</span>
                <span>购买数量  {{ props.row.data.foodlist[index].count }}</span>
              </div> -->
              <el-table
                :data="props.row.data.foodlist"
                border
                fit
                style="width: 1000px">
                <!-- <el-table-column prop="picture" label="菜品图片"/> -->
                <el-editable-column align="center" label="菜品图片">
                  <template slot-scope="scope">
                    <div><img :src="props.row.data.foodlist[scope.$index].picture" class="picture"></div>
                  </template>
                </el-editable-column>
                <el-table-column prop="name" label="菜品名称"/>
                <el-table-column prop="describe" label="菜品描述"/>
                <el-table-column prop="price" label="菜品价格"/>
                <el-table-column prop="count" label="购买数量"/>
              </el-table>
            </el-form-item>
            <el-form-item label="下单时间">
              <span>{{ props.row.data.date }}</span>
            </el-form-item>
            <el-form-item label="地址">
              <span>{{ props.row.data.address }}</span>
            </el-form-item>
            <el-form-item label="是否支付">
              <span>{{ props.row.data.isPay }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-editable-column prop="name" align="center" label="用户姓名" min-width="125" show-overflow-tooltip/>
      <el-editable-column prop="phone" align="center" label="电话号码" min-width="145" show-overflow-tooltip/>
      <el-editable-column prop="date" align="center" label="下单时间" min-width="180" show-overflow-tooltip/>
      <el-editable-column prop="address" align="center" label="地址" min-width="120" show-overflow-tooltip/>
      <el-editable-column prop="price" align="center" label="应付金钱" min-width="225"/>
      <el-editable-column prop="isPay" align="center" label="是否支付" min-width="145" show-overflow-tooltip/>
      <el-editable-column align="center" label="操作" min-width="320">
        <template slot-scope="scope">
          <el-button :plain="true" type="primary" size="small" @click="open(scope.$index)">立即支付</el-button>
          <el-button :plain="true" type="danger" size="small" @click="open2(scope.$index)">取消订单</el-button>
        </template>
      </el-editable-column>
    </el-editable>
  </div>
</template>

<script>
export default {
  inject: ['reload'],
  data() {
    return {
      uidList: (!JSON.parse(localStorage.getItem('uidList')) && typeof (JSON.parse(localStorage.getItem('uidList'))) !== 'undefined' && JSON.parse(localStorage.getItem('uidList')) !== 0) ? [] : JSON.parse(localStorage.getItem('uidList')),
      orderList: [],
      code: 404
    }
  },
  created() {
    console.log('abc' + this.uidList[0].uid)
    this.getOrderList()
    // localStorage.removeItem('uidList')
    // var uidList = JSON.parse(localStorage.getItem('uidList'))
    // for (var i = 0; i < uidList.length; i++) {
    //   console.log(uidList[i].uid)
    // }
    setTimeout(() => {
      console.log('$$' + this.orderList)
    }, 500)
  },
  methods: {
    open2(index) {
      this.$confirm('是否取消订单?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteOrder(index)
        setTimeout(() => {
          this.open3()
        }, 500)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    open3() {
      if (this.code === 20000) {
        this.$message('操作成功')
      } else {
        this.$message('操作失败')
      }
    },
    deleteOrder(index) {
      this.code = 404
      this.$http.post('http://127.0.0.1:8764/order/deleteOrder', {
        uid: this.orderList[index].uid
      }, { emulateJSON: true }).then(function(res) {
        this.code = res.data.code
      }, function(res) {
        this.code = res.data.code
      })
      this.reload()
    },
    renderTime(date) {
      var dateee = new Date(date).toJSON()
      return new Date(+new Date(dateee) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '')
    },
    getOrderList() {
      if (this.uidList.length > 0) {
        this.$http.post('http://127.0.0.1:8764/order/getOrderByUids', {
          uids: JSON.stringify(this.uidList)
        }, { emulateJSON: true }).then(function(res) {
          console.log(res.data)
          var b = res.data.list
          for (var i = 0; i < b.length; i++) {
            var arr = JSON.parse(b[i].jsonList)
            this.orderList.push({
              uid: b[i].uid,
              name: b[i].name,
              phone: b[i].phone,
              foodlist: arr,
              price: b[i].price,
              date: b[i].date,
              isPay: b[i].isPay,
              address: b[i].address
            })
          }
        }, function(res) {
        })
      }
    }
  }
}
</script>

<style>
  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
</style>

