<template>
  <div class="app-container">
    <el-form :ref="form" :model="form" label-width="120px">
      <el-form-item label="食物名称">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="食物图片">
        <el-upload
          :on-success="handleSuccess"
          :on-remove="handleRemove"
          class="upload-demo"
          name="picture"
          list-type="picture"
          drag
          action="http://127.0.0.1:8763/upload"
        >
          <i class="el-icon-upload" />
          <div class="el-upload__text" @click="submitUpload">将文件拖到此处，或<em>点击上传</em></div>
          <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
        </el-upload>
      </el-form-item>
      <el-form-item label="食物描述">
        <el-input v-model="form.description" />
      </el-form-item>
      <el-form-item label="食物价格">
        <el-input v-model="form.price" />
      </el-form-item>
      <el-form-item>
        <el-button @click="resetForm(form)">重置</el-button>
        <el-button type="primary" @click="submitUpload()">提交</el-button>
      </el-form-item>
    </el-form>
    <el-button @click="getFood()">open</el-button>
    <div>{{ price }}</div>
    <div><img :src="image" class="picture"></div>
  </div>
</template>

<script>
export default {
  inject: ['reload'],
  data() {
    return {
      form: {
        name: '',
        picture: '',
        description: '',
        price: ''
      },
      image: ''
    }
  },
  created() {
    this.price = this.$route.params.price
  },
  methods: {
    getFood() {
      this.$http.post('http://127.0.0.1:8763/getFoods', { emulateJSON: true })
        .then(function(res) {
          console.log(res.data.list[0].picture)
          this.image = '..\\..\\' + res.data.list[0].picture
        }, function(res) {
          alert(res)
        })
    },
    handleRemove(file, fileList) {
      this.$message({
        type: 'info',
        message: '已删除原有图片',
        duration: 6000
      })
    },
    handleSuccess(res, file) {
      this.$message({
        type: 'info',
        message: '图片上传成功',
        duration: 6000
      })
      if (file.response.success) {
        this.form.picture = file.response.message // 将返回的文件储存路径赋值picture字段
      }
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
      this.form.name = ''
      this.form.picture = ''
      this.form.description = ''
      this.form.price = ''
    },
    submitUpload() {
      this.$http.post('http://127.0.0.1:8763/add', {
        name: this.form.name,
        describe: this.form.description,
        price: this.form.price,
        picture: this.form.picture
      }, { emulateJSON: true })
        .then(function(res) {
          this.open('上传成功')
          this.reload()
        }, function(res) {
          this.open('上传失败')
        })
    },
    open(msg) {
      this.$message(msg)
    },
    onSubmit() {
      this.$message('submit!')
    },
    onCancel() {
      this.$message({
        message: 'cancel!',
        type: 'warning'
      })
    }
  }
}
</script>

<style scoped>
  .line{
    text-align: center;
  }
</style>

