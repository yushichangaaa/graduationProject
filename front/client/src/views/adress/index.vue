<template >
  <div style="padding:30px;">
    <h4>地址详情</h4>
    <el-button type="primary" @click="openDialog()">添加地址</el-button>
    <el-editable
      ref="editable"
      :data.sync="addressList"
      :height="tableHeight"
      element-loading-text="Loading"
      class="manual-table1"
      border
      fit
      highlight-current-row
      style="width: 100%">
      <el-editable-column prop="address" align="center" label="地址" min-width="225" show-overflow-tooltip/>
      <el-editable-column align="center" min-width="320">
        <template slot-scope="scope">
          <h4 v-if="addressList[scope.$index].address === localAddress">当前地址</h4>
          <el-button v-else :plain="true" type="warning" size="small" @click="setAddress(scope.$index)">设置为当前地址</el-button>
        </template>
      </el-editable-column>
    </el-editable>
    <div>
      <el-dialog :visible.sync="visible" title="添加地址">
        <el-form :model="myForm">
          <el-form-item label="您的地址: ">
            <el-input v-model="myForm.address"/>
          </el-form-item>
          <el-form-item style="margin-left: 75%">
            <el-button type="primary" @click="add()">立即添加</el-button>
            <el-button @click="cencal()">取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { getPhone } from '@/utils/auth'
export default {
  inject: ['reload'],
  data() {
    return {
      visible: false,
      myForm: {
        address: ''
      },
      addressList: [],
      tableHeight: 500,
      localAddress: localStorage.getItem('localAddress')
    }
  },
  created() {
    // var tmp = JSON.parse(localStorage.getItem('addressList'))
    // // console.log('&&&&&' + tmp[0].address)
    // if (!tmp && typeof (tmp) !== 'undefined' && tmp !== 0) {
    //   alert(1)
    //   var list = []
    //   localStorage.setItem('addressList', JSON.stringify(list))
    // }
    this.getAddressList()
  },
  methods: {
    open(val) {
      if (val === 20000) {
        this.$message('操作成功')
      } else {
        this.$message('操作失败')
      }
    },
    open2(val) {
      this.$message(val)
    },
    getAddressList() {
      this.$http.post('http://127.0.0.1:7788/user2/findAddressList', {
        phone: getPhone()
      }, { emulateJSON: true })
        .then(function(res) {
          if (res.data.code === 20000) {
            var b = res.data.list
            for (var i = 0; i < b.length; i++) {
              this.addressList.push({
                address: b[i]
              })
            }
          } else {
            this.open2('查询失败')
          }
        }, function(res) {
          alert(res.status)
        })
    },
    setAddress(index) {
      // console.log('&&&&&' + this.addressList[index].address)
      localStorage.setItem('localAddress', this.addressList[index].address)
      this.reload()
    },
    add() {
      // var adrList = JSON.parse(localStorage.getItem('addressList'))
      // adrList.push({
      //   address: this.myForm.address
      // })
      // localStorage.setItem('addressList', JSON.stringify(adrList))
      this.$http.post('http://127.0.0.1:7788/user2/addAddress', {
        address: this.myForm.address,
        phone: getPhone()
      }, { emulateJSON: true }).then(function(res) {
        var cd = res.data.code
        this.open(cd)
      }, function(res) {
        alert(res.status)
      })
      this.reload()
    },
    openDialog() {
      this.visible = true
    },
    cencal() {
      console.log('1111' + this.myForm.address)
      this.myForm.address = ''
      console.log('2222' + this.myForm.address)
      this.visible = false
    }
  }
}
</script>

