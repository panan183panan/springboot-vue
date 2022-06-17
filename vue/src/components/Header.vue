<template>
<div style="font-size: 12px;line-height: 60px;display: flex">

  <div style="flex: 1;">
    <span :class="collapseBtnClass" style="cursor: pointer;font-size: 18px" @click="collapse"></span>


    <el-breadcrumb separator="/" style="display: inline-block;margin-left: 10px">
      <el-breadcrumb-item :to= "'/main'">首页</el-breadcrumb-item>
      <el-breadcrumb-item>{{currentPathName}}</el-breadcrumb-item>
    </el-breadcrumb>
  </div>

  <el-dropdown style="cursor: pointer; text-align: right">
    <span>{{ user.nickname }}</span> <i class="el-icon-arrow-down" style="margin-left: 5px"></i>
    <el-dropdown-menu slot="dropdown" style="width: 100px;">
      <el-dropdown-item>
        <router-link to="/main/person" style="text-decoration: none">用户信息</router-link>
      </el-dropdown-item>
      <el-dropdown-item>
        <span style="text-decoration: none" @click="logout">退出</span>
      </el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</div>
</template>

<script>
export default {
  name: "Header",
  props:{
    collapseBtnClass:String,
    collapse: Boolean,
  },
  computed: {
    currentPathName(){
      return this.$store.state.currentPathName
    }
  },
  data(){
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  watch:{
    currentPathName(newVal,oldVal){
      console.log(newVal)
    }
  },
  methods:{
    quit(){
      this.$router.push("/login")
    },
    logout(){
      this.$router.push("/login")
      localStorage.removeItem("user")
      this.$message.success("退出成功")
    }
  }

}
</script>

<style scoped>

</style>