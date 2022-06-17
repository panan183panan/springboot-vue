<template>
  <div id="wrapper">
    <div id="login-item">
      <el-form :rules="rules" ref="loginForm" :model="loginForm" class="logincontainer">
        <h3 class="loginTitle">登录系统</h3>
        <el-form-item prop="username">
          <el-input type="text" auto-complete="false" v-model="loginForm.username" placeholder="请输入用户名"/>
        </el-form-item>

        <el-form-item prop="password">
          <el-input type="password" auto-complete="false" v-model="loginForm.password" placeholder="请输入密码"/>
        </el-form-item>

        <el-checkbox v-model="checked" class="loginRemember">记住我</el-checkbox>

        <el-form-item>
          <el-button type="primary" @click="submitLogin" style="width: 45%" >登录</el-button>

          <el-button type="success" @click="registerinfo" style="width: 45%">注册</el-button>
        </el-form-item>

      </el-form>
    </div>
  </div>
</template>

<script>

export default {

  name: "login",
  data(){
    return{
      catpchaUrl: '',
      loginForm: {
       username: '',
       password: '',
      },
      checked:true,
      rules:{
        username: [{required:true,message:'请输入用户名',trigger:'blur'}],
        password: [
          {required:true,message:'请输入密码',trigger:'blur'},
          {min:3,max:20,message:'密码长度在3-20之间',trigger:'blur'}
        ],
      }
    }
  },
  methods: {

    submitLogin(){
      this.$refs.loginForm.validate((valid)=>{
        if(valid){
          // this.$message.success("登录成功")
          this.request.post("/user/login",this.loginForm).then(res=>{
            console.log(res);
            if(res.code === '200'){
              this.$message.success("登录成功")
              this.$router.push("/main")
              localStorage.setItem("user",JSON.stringify(res.data))
            }
            else{
              this.$message.error("用户名或密码错误")
            }
          })
        }
        else{
          this.$message.error(res.msg)
          return false;
        }
      })
    },
    registerinfo(){
      this.$router.push("/register")
    }
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