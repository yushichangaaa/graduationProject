<template>
  <div class="app-container">
    <div v-if="hide">
      <span>输入您的手机号</span>
      <el-input v-model="phone" placeholder="phone number" />
      <span>输入您的姓名</span>
      <el-input v-model="name" placeholder="name" />
      <el-button :disabled="disabled" @click="send()">{{ buttonName }}</el-button>
      <el-input v-model="outlineCode" placeholder="validate number" />
      <el-button @click="ok()">确认</el-button>
    </div>
    <div v-if="!hide">
      <h2>用户中心</h2>
      <h4>用户:{{ name2 }}</h4>
      <h4>手机号: {{ phone2 }}</h4>
      <h4>订单情况:</h4>
      <el-button @click="zhuxiao()">点此注销</el-button>
    </div>
  </div>
</template>

<script>
import { getPhone, setPhone, removePhone, getName, setName } from '@/utils/auth'
export default {
  inject: ['reload'],
  data() {
    return {
      hide: true,
      phone: '',
      name: '',
      buttonName: '发送验证码',
      disabled: false,
      onlineCode: '',
      outlineCode: '',
      phone2: getPhone(),
      name2: getName()
    }
  },
  watch: {
  },
  created() {
    console.log('^^^^^^^^^' + getPhone())
    if ((typeof getPhone()) !== 'undefined') {
      this.hide = false
    } else {
      this.hide = true
    }
  },
  methods: {
    zhuxiao() {
      removePhone()
      this.reload()
    },
    open(val) {
      this.$message(val)
    },
    ok() {
      if (this.phone === '') {
        this.open('手机号不能为空')
      } else {
        var b = false
        if (this.onlineCode.toString() === this.outlineCode) {
          this.$http.post('http://127.0.0.1:7788/user2/findUserByPhone', {
            phone: this.phone
          }, { emulateJSON: true }).then(function(res) {
            b = res.data.exist
            this.open('成功')
            setPhone(this.phone)
            setName(this.name)
            if (b === true) {
              this.reload()
            }
          }, function(res) {
            this.open('失败')
          })
          setTimeout(() => {
            if (!b) {
              alert(1)
              this.$http.post('http://127.0.0.1:7788/user2/create', {
                phone: this.phone,
                name: this.name
              }, { emulateJSON: true }).then(function(res) {
                console.log(this.phone)
                setPhone(this.phone)
                setName(this.name)
                this.open('成功')
                this.outlineCode = ''
                this.phone = ''
                this.name = ''
                this.reload()
              }, function(res) {
                this.open('失败')
              })
            }
          }, 500)
        } else {
          this.open('失败')
        }
      }
    },
    sendMsg() {
      this.$http.post('http://127.0.0.1:9999/sendMsg', { emulateJSON: true })
        .then(function(res) {
          console.log(res.data.data.validateCode)
          this.onlineCode = res.data.data.validateCode
        }, function(res) {
          alert(res.status)
        })
    },
    send() {
      if (this.phone === '') {
        this.open('手机号不能为空')
      } else {
        this.sendMsg()
        let count = 5
        const countDown = setInterval(() => {
          if (count === 0) {
            this.disabled = false
            this.buttonName = '重新发送'
            clearInterval(countDown)
          } else {
            this.disabled = true
            this.buttonName = count + '秒后可重新发送'
          }
          count--
        }, 1000)
      }
    }
  }
}
</script>
