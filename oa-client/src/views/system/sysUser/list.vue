<!-- eslint-disable vue/valid-v-model -->
<!--
 * @Description: 用户管理页面
 * @Author: 执手天涯
 * @Date: 2023-03-30 21:27:59
 * @LastEditors: 执手天涯
 * @LastEditTime: 2023-03-30 22:19:18
 * @version: 1.0
-->
<template>
  <div class="app-container">
    <!-- 顶部搜索栏 -->
    <div class="search-div">
      <el-form label-width="70px" size="small" :inline="true">
        <el-form-item label="关 键 字">
          <el-input
            v-model="searchObj.keyword"
            placeholder="用户名/姓名/手机号码"
          />
        </el-form-item>

        <el-form-item label="操作时间">
          <el-date-picker
            v-model="createTimes"
            type="datetimerange"
            range-separator="-"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            style="margin-right: 10px; width: 100%"
          />
        </el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="small"
          @click="fetchData()"
        >搜索</el-button>
        <el-button
          icon="el-icon-refresh"
          size="small"
          @click="resetData"
        >重置</el-button>
      </el-form>
    </div>

    <!--工具栏 -->
    <div class="tools-div">
      <el-button
        type="success"
        icon="el-icon-plus"
        size="small"
        @click="add"
      >添加用户</el-button>
    </div>
    <!-- 用户数据列表 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      stripe
      border
      style="width: 100%; margin-top: 10px"
    >
      <el-table-column label="序号" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="name" label="姓名" />
      <el-table-column prop="phone" label="手机" />
      <el-table-column prop="postName" label="岗位" />
      <el-table-column prop="deptName" label="部门" />
      <el-table-column label="所属角色" width="130">
        <template slot-scope="scope">
          <span
            v-for="item in scope.row.roleList"
            :key="item.id"
            style="margin-right: 10px"
          >{{ item.roleName }}</span>
        </template>
      </el-table-column>

      <el-table-column label="状态" width="80">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status === 1"
            @change="switchStatus(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="160" />

      <el-table-column label="操作" width="180" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button
            type="primary"
            icon="el-icon-edit"
            size="mini"
            title="修改"
            @click="edit(scope.row.id)"
          >修改
          </el-button>
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            title="删除"
            @click="removeDataById(scope.row.id)"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <el-pagination
      :current-page="page"
      :total="total"
      :page-size="limit"
      :page-sizes="[5, 10, 20, 30, 40, 50, 100]"
      style="padding: 30px 0; text-align: center"
      layout="sizes, prev, pager, next, jumper, ->, total, slot"
      @current-change="fetchData"
      @size-change="changeSize"
    />

    <el-dialog title="添加/修改" :visible.sync="dialogVisible" width="40%">
      <el-form
        ref="dataForm"
        :model="sysUser"
        label-width="100px"
        size="small"
        style="padding-right: 40px"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="sysUser.username" />
        </el-form-item>
        <el-form-item v-if="!sysUser.id" label="密码" prop="password">
          <el-input v-model="sysUser.password" type="password" />
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="sysUser.name" />
        </el-form-item>
        <el-form-item label="手机" prop="phone">
          <el-input v-model="sysUser.phone" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button
          size="small"
          icon="el-icon-refresh-right"
          @click="dialogVisible = false"
        >取 消</el-button>
        <el-button
          type="primary"
          icon="el-icon-check"
          size="small"
          @click="saveOrUpdate()"
        >确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import sysUserApi from '@/api/system/sysUser/user'
const defaultForm = {
  id: '',
  username: '',
  password: '',
  name: '',
  phone: '',
  status: 1
}
export default {
  data() {
    return {
      listLoading: true, // 数据是否正在加载
      list: null, // banner列表
      total: 0, // 数据库中的总记录数
      page: 1, // 默认页码
      limit: 10, // 每页记录数
      searchObj: {}, // 查询表单对象
      createTimes: [],
      dialogVisible: false,
      sysUser: defaultForm,
      saveBtnDisabled: false
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    // 当页码发生改变的时候
    changeSize(size) {
      this.limit = size
      this.fetchData(1)
    },

    // 加载用户列表数据
    fetchData(page = 1) {
      this.page = page
      // 封装条件参数
      if (this.createTimes && this.createTimes.length === 2) {
        this.searchObj.createTimeBegin = this.createTimes[0]
        this.searchObj.createTimeEnd = this.createTimes[1]
      }
      sysUserApi
        .getPageList(this.page, this.limit, this.searchObj)
        .then((response) => {
          this.list = response.data.records
          this.total = response.data.total
          // 数据加载并绑定成功
          this.listLoading = false
        })
    },

    // 重置查询表单
    resetData() {
      console.log('重置查询表单')
      this.searchObj = {}
      this.createTimes = []
      this.fetchData()
    },

    // 根据id删除数据
    removeDataById(id) {
      // debugger
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          // promise
          // 点击确定，远程调用ajax
          return sysUserApi.removeById(id)
        })
        .then((response) => {
          this.fetchData(this.page)
          this.$message.success(response.message || '删除成功')
        })
        .catch(() => {
          this.$message.info('取消删除')
        })
    },

    // 添加按钮的点击事件
    add() {
      this.dialogVisible = true
      this.sysUser = Object.assign({}, defaultForm)
    },
    // 编辑按钮的点击事件
    edit(id) {
      this.dialogVisible = true
      this.fetchDataById(id)
    },

    // 根据id获取用户信息
    fetchDataById(id) {
      sysUserApi.getById(id).then((response) => {
        this.sysUser = response.data
      })
    },

    // 判断操作是保存还是修改
    saveOrUpdate() {
      this.$refs.dataForm.validate((valid) => {
        if (valid) {
          this.saveBtnDisabled = true // 防止表单重复提交
          if (!this.sysUser.id) {
            this.saveData()
          } else {
            this.updateData()
          }
        }
      })
    },

    // 新增
    saveData() {
      sysUserApi.save(this.sysUser).then((response) => {
        this.$message.success('操作成功')
        this.dialogVisible = false
        this.fetchData(this.page)
      })
    },

    // 根据id更新记录
    updateData() {
      sysUserApi.updateById(this.sysUser).then((response) => {
        this.$message.success(response.message || '操作成功')
        this.dialogVisible = false
        this.fetchData(this.page)
      })
    }
  }
}
</script>

