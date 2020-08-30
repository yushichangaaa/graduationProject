<template>
  <div class="activate-container">
    <h4>用户: {{ name }} ， 您好 您的账户还未激活 请<em class="link-type" @click="dialogFormVisible = true">点此激活</em></h4>
    <el-button @click="open">主页</el-button>
    <el-dialog :visible.sync="dialogFormVisible" title="激活用户">
      <div><h4>您的电话号码为: {{ phone }}</h4></div>
      <div><span class="link-type" @click="send()">点此发送邮件</span></div>
      <div class="demo-input-suffix">
        <h4>输入手机验证码:</h4>
        <el-input v-model="inputCode" size="mini"/>
      </div>
      <el-button type="primary" @click="validate()">验证</el-button>
    </el-dialog>
  </div>
</template>

<script>
import { sendMsg, activeUser } from '@/api/login'
import { getToken, removeToken, getPhone, getName, setActivate } from '@/utils/auth'
export default {
  name: 'Activate',
  data() {
    return {
      dialogFormVisible: false,
      phone: getPhone(),
      name: getName(),
      aCode: '',
      inputCode: ''
    }
  },
  watch: {
  },
  methods: {
    cancelDialog(a) {
      this.dialogFormVisible = false
    },
    open() {
      removeToken(getToken())
      this.$router.push({ path: '/' })
    },
    send() {
      sendMsg().then(res => {
        this.aCode = res.data.activeCode
        console.log(res.data.activeCode)
      }).catch(error => {
        alert(error)
      })
    },
    validate() {
      if (this.aCode === parseInt(this.inputCode)) {
        activeUser(this.name).then(res => {
          if (res.code === 20000) {
            setActivate('1')
            this.$router.push({ path: '/activeSuccess' })
          } else {
            this.$router.push({ path: '/activeFail' })
          }
        })
      } else {
        alert('激活失败')
      }
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
