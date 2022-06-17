<template>
<div>
  <div style="padding: 10px 0">
    <el-input v-model="username" type="text" style="width: 200px;margin-right: 5px" suffix-icon="el-icon-search" placeholder="请输入名称"></el-input>
    <el-input v-model="email" type="text" style="width: 200px;margin-right: 5px" suffix-icon="el-icon-message" placeholder="请输入邮件"></el-input>
    <el-input v-model="location" type="text" style="width: 200px;margin-right: 5px" suffix-icon="el-icon-position" placeholder="请输入地址"></el-input>
    <el-button type="primary" @click="save">搜索</el-button>
    <el-button type="warning" @click="reset">重置</el-button>
  </div>

  <div style="padding: 10px 0">
    <el-button type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>
    <el-popconfirm
        style="margin-left: 5px;margin-right: 5px"
        confirm-button-text='确定'
        cancel-button-text='我在想想'
        icon="el-icon-info"
        icon-color="red"
        title="您确定要批量删除这些信息吗？"
        @confirm="delBatch"
    >
      <el-button type="danger" slot="reference">批量删除<i class="el-icon-remove-outline"></i></el-button>
    </el-popconfirm>
    <el-upload
        action="http://localhost:9090/user/import"
        :show-file-list="false"
        :on-success="handleimportSuccess"
        accept="xlsx"
        style="display: inline-block;margin-right: 10px">
    <el-button type="primary">导入<i class="el-icon-bottom"></i></el-button>
    </el-upload>
    <el-button type="primary" @click="exp">导出<i class="el-icon-top"></i></el-button>
  </div>

  <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="40"></el-table-column>
    <el-table-column prop="id" label="ID" width="60"></el-table-column>
    <el-table-column prop="username" label="用户名" width=""></el-table-column>
    <el-table-column prop="nickname" label="昵称" width=""></el-table-column>
    <el-table-column prop="email" label="邮件" width="200"></el-table-column>
    <el-table-column prop="phone" label="电话" width=""></el-table-column>
    <el-table-column prop="location" label="地址" width=""></el-table-column>
    <el-table-column label="操作" width="220" align="center">
      <template  slot-scope="scope">
        <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
        <el-popconfirm
            style="margin-left: 5px"
            confirm-button-text='确定'
            cancel-button-text='我在想想'
            icon="el-icon-info"
            icon-color="red"
            title="您确定要删除这条信息吗？"
            @confirm="handleDelete(scope.row.id)"
        >
          <el-button type="danger" slot="reference">删除<i class="el-icon-delete"></i></el-button>
        </el-popconfirm>
      </template>
    </el-table-column>

  </el-table>
  <div style="padding: 10px 0">
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
  </div>
  <!--        新增修改弹出框-->
  <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
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
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="saveAdd">确 定</el-button>
    </div>
  </el-dialog>
</div>
</template>

<script>
export default {
  name: "User",
  data(){
    return{
      headerBg: 'headerBg',
      tableData: [],
      pageNum: 1,
      pageSize: 5,
      total: 0,
      username: '',
      email: '',
      location: '',
      formLabelWidth: '100',
      multipleSelection: [],
      dialogFormVisible: false,
      form:{
        username: "",
        nickname: "",
        email: "",
        phone: "",
        location: ""
      }

    }
  },
  created() {
    this.load()
  },
  methods:{
    handleimportSuccess(){
      this.$message.success("导入成功！")
      this.load()
    },

    delBatch(){
      let ids = this.multipleSelection.map(v => v.id)
      this.request.post("/user/del/batch", ids).then(res=>{
        if(res.data){
          // this.$message.success("添加成功！！！")
          this.$notify({
            title: '删除',
            message: '批量删除成功！！！',
            type: 'success'
          })
          this.load()
        }
        else{
          this.$notify({
            title: '删除',
            message: '批量删除失败！！！',
            type: 'error'
          })
        }
      })
    },

    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection = val
    },

    load(){
      this.request.get("/user/page",{
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
          email: this.email,
          location: this.location
        }
      }).then(res=>{
        // console.log(res)

        this.tableData = res.data.records
        this.total = res.data.total
      })

    },

    handleAdd(){
      this.dialogFormVisible=true
      this.form={}

    },

    handleSizeChange(pageSize){
      this.pageSize=pageSize
      this.load()
    },

    handleCurrentChange(pageNum){
      this.pageNum=pageNum
      this.load()
    },
    save(){
      this.load()
    },

    saveAdd(){
      this.request.post("/user",this.form).then(res=>{
        // console.log(res)
        console.log(this.form)
        if(res.data){
          // this.$message.success("添加成功！！！")
          this.$notify({
            title: '成功',
            message: '添加成功！！！',
            type: 'success'
          })
          this.load()
        }
        else{
          this.$message.error("")
          this.$notify({
            title: '失败',
            message: '添加失败！！！',
            type: 'error'
          })
        }
      })
      this.dialogFormVisible = false
    },

    handleEdit(row){
      console.log(row)
      this.dialogFormVisible=true
      this.form=row
    },

    handleDelete(id){
      this.request.delete("/user/"+id).then(res=>{
        if(res.data){
          // this.$message.success("添加成功！！！")
          this.$notify({
            title: '删除',
            message: '删除成功！！！',
            type: 'success'
          })
          this.load()
        }
        else{
          this.$notify({
            title: '删除',
            message: '删除失败！！！',
            type: 'error'
          })
        }
      })
      this.dialogFormVisible = false
    },

    reset(){
      this.username=""
      this.email=""
      this.location=""
      this.load()
    },
    exp(){
      window.open("http://localhost:9090/user/export");
    }
  }
}
</script>

<style scoped>
.headerBg{
  background-color: #eee !important;
}
</style>