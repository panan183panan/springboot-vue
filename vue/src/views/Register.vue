<template>
  <div id="wrapper">
    <div id="login-item">
      <el-form :rules="rules" ref="loginForm" :model="loginForm" class="logincontainer">
        <h3 class="loginTitle">用户注册</h3>

        <el-form-item prop="nickname">
          <el-input type="text" auto-complete="false" v-model="loginForm.nickname" placeholder="请输入您的昵称"/>
        </el-form-item>

        <el-form-item prop="username">
          <el-input type="text" auto-complete="false" v-model="loginForm.username" placeholder="请输入用户名"/>
        </el-form-item>

        <el-form-item prop="password">
          <el-input type="password" auto-complete="false" v-model="loginForm.password" placeholder="请输入密码"/>
        </el-form-item>
        <el-form-item prop="confirm">
          <el-input type="password" auto-complete="false" v-model="loginForm.confirm" placeholder="请再次输入密码"/>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitLogin" style="width: 45%" >注册</el-button>

          <el-button type="success" @click="loginInfo" style="width: 45%">返回登录</el-button>
        </el-form-item>

      </el-form>
    </div>
  </div>
</template>

<script>

export default {

  name: "register",
  data(){
    return{
      loginForm: {
       username: '',
       nickname: '',
       password: '',
       confirm: '',
      },

      rules:{
        username: [{required:true,message:'请输入用户名',trigger:'blur'}],
        nickname: [{required:true,message:'请输入您的昵称',trigger:'blur'}],
        password: [
            {required:true,message:'请输入密码',trigger:'blur'},
            {min:6,max:20,message:'请输入6-20个字符',trigger:'blur'}
        ],
        confirm: [
            {required:true,message:'请再次输入密码',trigger:'blur'},
          {min:6,max:20,message:'请输入6-20个字符',trigger:'blur'}
        ],
      }
    }
  },
  methods: {
    loginInfo(){
      this.$router.push('/login')
    },

    submitLogin(){
      this.$refs.loginForm.validate((valid)=>{
        if(valid){
          if(this.loginForm.password != this.loginForm.confirm){
            this.$message.error("两次密码输入不一致")
            return false
          }
          this.request.post("/user/register",this.loginForm).then(res=>{
            if(res.code === '200'){
              this.$message.success("注册成功")
            }
            else{
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
  }
}
</script>

<style scoped>

.logincontainer{
  border-radius: 15px;
  background-clip: padding-box;
  margin: 180px auto;
  width: 350px;
  padding: 15px 35px 15px 35px;
  background-color: white;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.loginTitle{
  text-align: center;
  margin: 0px auto 40px auto;
}

.loginRemember{
  text-align: left;
  margin: 0px 0px 15px 0px;
}

.el-form-item__content{
  display: flex;
  align-items: center;
}


#wrapper{
  height: 100vh;
  background-image: linear-gradient(to bottom right, #3F5EFB,#Fc466B,#3F5EFB);
  overflow: hidden;
}

</style>