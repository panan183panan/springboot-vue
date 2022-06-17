<template>
<el-card style="width: 500px">
  <el-form label-width="80px" size="small">
    <el-form-item label="用户名" >
      <el-input v-model="form.username" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="昵称" >
      <el-input v-model="form.nickname" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="邮件" >
      <el-input v-model="form.email" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="电话" >
      <el-input v-model="form.phone" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="地址" >
      <el-input v-model="form.location" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item >
      <el-button type="primary" @click="saveAdd">保存</el-button>
    </el-form-item>
  </el-form>
</el-card>
</template>

<script>

export default {
  name: "Person",
  data(){
    return{
      form:{},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}

    }
  },
  created() {
    this.request.get("/user/username/"+this.user.username).then(res=>{
      if(res.code==='200'){
        console.log(res.data)
        this.form=res.data
      }
    })
  },
  methods:{
    saveAdd(){
      this.request.post("/user",this.form).then(res=>{
        // console.log(res)
        console.log(this.form)
        if(res.data){
          // this.$message.success("添加成功！！！")
          this.$notify({
            title: '成功',
            message: '保存成功！！！',
            type: 'success'
          })
        }
        else{
          this.$message.error("")
          this.$notify({
            title: '失败',
            message: '保存失败！！！',
            type: 'error'
          })
        }
      })
    },

  }
}
</script>

<style scoped>

</style>