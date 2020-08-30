<template>
  <div class="commentBox">
    <div class="aaa">
      <h3>评论</h3>
      <p v-if="comment.length==0">暂无评论，我来发表第一篇评论！</p>
      <div v-else>
        <div v-for="(item, index) in comment" :key="index" class="comment">
          <b>{{ item.name }}<span>{{ item.time }}</span><span class="link-type" @click="openDialog(index)">回复</span></b>
          <p @click="changeCommenter(item.name, index)">{{ item.text }}</p>
          <!-- <div v-if="item.reply.length > 0">
            <div class="reply" v-for="(reply, index) in item.reply" :key = "index">
              <b>{{ reply.responder }}&nbsp;&nbsp;回复&nbsp;&nbsp;{{ reply.reviewers }}<span>{{ reply.time }}</span></b>
              <p @click="changeCommenter(reply.responder, index)">{{ reply.content }}</p>
            </div>
          </div> -->
        </div>
      </div>
    </div>
    <el-dialog :visible.sync="dialogFormVisible">
      <b_comment
        ref="my_comment"
        :placeholder="placeholder2"
        :if_not_logined="if_not_logined"
        :emoji_list="emoji_list"
        :verify_once="verify_once"
        @submit_click="reply"
      />
    </el-dialog>
    <!-- <p v-if="comment.length==0">暂无评论，我来发表第一篇评论</p>
    <div v-else>
      <div v-for="(item,index) in comment" :key="index">
        <b>{{ item.name }}<span>{{ item.time }}</span></b>
      </div>
    </div> -->
    <b_comment
      ref="my_comment"
      :placeholder="placeholder"
      :if_not_logined="if_not_logined"
      :emoji_list="emoji_list"
      :verify_once="verify_once"
      @submit_click="submit_click"
    />
  </div>
</template>

<script>
import b_comment from './vue_comment/b_comment.vue'
import { comment, getComments, rp } from '@/api/comment'
export default {
  inject: ['reload'],
  name: 'HelloWorld',
  components: {
    'b_comment': b_comment
  },
  data() {
    return {
      parentId: 0,
      dialogFormVisible: false,
      placeholder2: '想对ta说什么?',
      comment: [],
      placeholder: '想说点什么？', // 默认文字提示。
      if_not_logined: true, // 用户是否没有登录。
      emoji_list: ['(⌒▽⌒)', '（￣▽￣）', '(=・ω・=)', '(｀・ω・´)', '(〜￣△￣)〜', '(･∀･)', '(￣3￣)', '╮(￣▽￣)╭', '( ´_ゝ｀)', '←_←', '→_→', '(<_<)', '(>_>)', '(;¬_¬)', '("▔□▔)/', '(ﾟДﾟ≡ﾟдﾟ)!?', 'Σ(ﾟдﾟ;)', '(｡･ω･｡)', '(´･_･`)', '（￣へ￣）', '(╯°口°)╯(┴—┴', '_(:3」∠)_'],
      verify_once: false// 未登录时，每次评论都需输入验证码。
    }
  },
  computed: {
    comment_text() { // 获取子组件的评论内容。
      return this.$refs.my_comment.insert_comment.comment_text
    },
    comment_name() { // 获取子组件的评论昵称。
      return this.$refs.my_comment.insert_comment.comment_name
    }
  },
  created() {
    this.initComment()
  },
  methods: {
    reply() {
      rp(this.parentId, '商家', this.comment_text).then(res => {
        this.open('回复成功')
        this.reload()
      }, res => {
        this.open('回复失败')
      })
    },
    openDialog(i) {
      this.dialogFormVisible = true
      this.parentName = this.comment[i].name
      this.parentId = this.comment[i].id
    },
    renderTime(date) {
      var dateee = new Date(date).toJSON()
      return new Date(+new Date(dateee) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '')
    },
    initComment() {
      var m = new Map()
      getComments().then(res => {
        this.comment = []
        var b = res.data.list
        for (var j = 0; j < b.length; j++) {
          m.set(b[j].id, b[j].name)
        }
        var n = ''
        for (var i = 0; i < b.length; i++) {
          var t = this.renderTime(b[i].time)
          if (b[i].parent !== null) {
            if (b[i].name === m.get(b[i].parent)) {
              n = b[i].name + '回复了自己'
            } else {
              n = b[i].name + '回复了' + m.get(b[i].parent)
            }
          } else {
            n = b[i].name
          }
          this.comment.push({
            id: b[i].id,
            parent: b[i].parent,
            name: n,
            text: b[i].text,
            time: t
          })
        }
      }, res => {
        this.open('查询失败')
      })
    },
    open(val) {
      this.$message(val)
    },
    submit_click() {
      comment('商家', this.comment_text).then(res => {
        this.open('成功')
        this.reload()
      }, res => {
        this.open('失败')
      })
      // this.$http.post('http://127.0.0.1:9999/comment', {
      //   name: getPhone(),
      //   text: this.comment_text
      // }, { emulateJSON: true })
      //   .then(function(res) {
      //     alert(1)
      //   }, function(res) {
      //     alert(2)
      //   })
    // console.log('用户输入的评论内容是：' + this.comment_text)
    // if (this.comment_name !== '') {
    // 		console.log('用户输入的昵称是：' + this.comment_name)
    // }

    // 你可以在这里验证用户输入的格式。
    // 若格式错误可调用此函数：
      // this.$refs.my_comment.err_submitFn("格式错误",1500)

    // 你可以在这儿请求AJAX
    // 失败回调：
    // this.$refs.my_comment.err_submitFn("评论失败",1500)
    // 成功回调
      // this.$refs.my_comment.success_submit('评论成功', 1500)
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
  .aaa {
    height : 400px
  }
  .comment {
    margin-bottom:15px;
  }
  .comment b {
    color:#01553D;
    font-size: 16px;
    display: block;
    margin: 5px;
  }
  .comment b span {
    color:#333;
    font-size:14px;
    margin-left:20px;
  }
  .comment p {
    font-size:16px;
    color:#333;
  }
  .commentBox {
    margin:20px;
  }
  .commentBox h3 {
    color: #634322;
    background: #e9e5df;
    padding: 8px 15px;
    text-align: left;
    font-size: 16px;
  }
  .noComment {
    display:none;
  }
  .commentBox textarea {
    overflow: auto;
    width: 100%;
    height: 95px;
    outline: none;
    resize: none;
  }
  .commentBox button {
    float:right;
    margin-left:20px;
    padding:5px 30px;
    background:#01553D;
    border-radius:5px;
    color:#fff;
    font-size:16px;
  }
  .commentBox button:hover {
    color:#fff;
    background:#047857
  }
</style>
