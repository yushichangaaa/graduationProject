<template>
  <div class="login-container">
    <el-form ref="user" :model="user" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">
      <h3 class="title">欢迎登陆</h3>
      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input v-model="user.username" name="username" type="text" auto-complete="on" placeholder="username" />
      </el-form-item>
      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          :type="pwdType"
          v-model="user.password"
          name="password"
          auto-complete="on"
          placeholder="password"
          @keyup.enter.native="handleLogin" />
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="pwdType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>
      <div style="width:50%;margin-bottom:30px;">
        <el-button :loading="loading" type="primary" @click.native.prevent="handleLogin">
          log
        </el-button>
        <el-button type="primary" @click="dialogFormVisible = true">
          regist
        </el-button>
      </div>
    </el-form>
    <div class="aaa">
      <el-dialog :visible.sync="dialogFormVisible" title="注册商家">
        <el-form :model="registForm" :ref="registForm" label-width="140px">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="registForm.username"/>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="registForm.password"/>
          </el-form-item>
          <el-form-item label="电话号码" prop="phone">
            <el-input v-model="registForm.phone"/>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancelDialog(registForm)">取 消</el-button>
          <el-button type="primary" @click="add(registForm)">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { regist } from '@/api/user'
export default {
  name: 'Login',
  data() {
    const validateUsername = (rule, value, callback) => {
      callback()
    }
    const validatePass = (rule, value, callback) => {
      callback()
    }
    return {
      dialogFormVisible: false,
      registForm: {
        username: '',
        password: '',
        phone: ''
      },
      user: {
        username: 'admin',
        password: 'admin'
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePass }]
      },
      loading: false,
      pwdType: 'password',
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  methods: {
    open(val) {
      this.$message(val)
    },
    add(a) {
      regist(this.registForm.username, this.registForm.password, this.registForm.phone)
        .then(res => {
          if (res.code === 20000) {
            this.open('注册成功')
          }
          this.cancelDialog(a)
        })
    },
    cancelDialog(a) {
      this.$refs[a].resetFields()
      this.registForm.username = ''
      this.registForm.password = ''
      this.registForm.phone = ''
      this.dialogFormVisible = false
    },
    showPwd() {
      if (this.pwdType === 'password') {
        this.pwdType = ''
      } else {
        this.pwdType = 'password'
      }
    },
    handleLogin() {
      this.$refs.user.validate(valid => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('Login', this.user).then(() => {
            this.loading = false
            this.$router.push({ path: this.redirect || '/' })
          }).catch(() => {
            this.loading = false
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
$bg:#2d3a4b;
$light_gray:#eee;

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;
    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      &:-webkit-autofill {
        -webkit-box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: #fff !important;
      }
    }
  }
  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}

</style>

<style rel="stylesheet/scss" lang="scss" scoped>
$bg:#2d3a4b;
$dark_gray:#889aa4;
$light_gray:#eee;
.aaa {
  height: 100%;
  width: 100%;
}
.login-container {
  position: fixed;
  height: 100%;
  width: 100%;
  background-color: $bg;
  .login-form {
    position: absolute;
    left: 0;
    right: 0;
    width: 520px;
    max-width: 100%;
    padding: 35px 35px 15px 35px;
    margin: 120px auto;
  }
  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;
    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }
  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }
  .title {
    font-size: 26px;
    font-weight: 400;
    color: $light_gray;
    margin: 0px auto 40px auto;
    text-align: center;
    font-weight: bold;
  }
  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
</style>
