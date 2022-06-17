<template>
  <el-container>
<!--    侧边导航栏-->
    <el-aside :width="sideWidth+'px'" style="min-height: 100% ;box-shadow: 2px 0 6px rgb(0 21 41 / 35%)">
      <Aside :isCollapse="isCollapse" />
    </el-aside>
    <el-container style="min-height: 100vh">

<!--      头部-->
      <el-header style="border-bottom: 1px solid #ccc;">
        <Header :collapseBtnClass="collapseBtnClass" :collapse="scollapse"/>
      </el-header>

<!--      主体内容-->
      <el-main>
        <router-view />
      </el-main>


    </el-container>
  </el-container>
</template>

<script>


// import request from "@/utils/request";

import Aside from '@/components/Aside'
import Header from '@/components/Header'

export default {
  name: 'HomeView',
  components:{
    Aside,
    Header
  },

  data(){
    return{
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200
    }
  },


  methods: {

    scollapse(){
      this.isCollapse=!this.isCollapse
      if(this.isCollapse){
        this.sideWidth=64
        this.collapseBtnClass='el-icon-s-unfold'
      }
      else{
        this.sideWidth=200
        this.collapseBtnClass='el-icon-s-fold'
      }
    }
  }
}
</script>


<style>

</style>