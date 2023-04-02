<template>
  <div class="app-container">
    <!--查询表单-->
    <div class="search-div">
      <el-form label-width="70px" size="small" :inline="true">
        <el-form-item label="角色名称">
          <el-input v-model="searchObj.roleName" placeholder="角色名称" />
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
    <!-- 工具条 -->
    <div class="tools-div">
      <el-button
        type="success"
        icon="el-icon-plus"
        size="small"
        @click="add"
      >添加角色</el-button>
      <el-button
        class="btn"
        icon="el-icon-delete"
        type="danger"
        size="small"
        @click="batchRemove()"
      >批量删除</el-button>
    </div>
    <!-- 表格 -->
    <el-table
      :data="list"
      stripe
      border
      style="width: 100%; margin-top: 10px"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" />
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="roleName" label="角色名称" />
      <el-table-column prop="roleCode" label="角色编码" />
      <el-table-column prop="createTime" label="创建时间" width="160" />
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button
            type="primary"
            icon="el-icon-edit"
            size="mini"
            title="修改"
            @click="edit(scope.row.id)"
          >编辑</el-button>
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            title="删除"
            @click="removeDataById(scope.row.id)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <el-pagination
      :current-page="page"
      :total="total"
      :page-size="limit"
      style="padding: 30px 0; text-align: center"
      layout="total, prev, pager, next, jumper"
      @current-change="fetchData"
    />

    <!-- 添加或修改模态框 -->
    <el-dialog title="添加/修改" :visible.sync="dialogVisible" width="40%">
      <el-form
        ref="dataForm"
        :model="sysRole"
        label-width="150px"
        size="small"
        style="padding-right: 40px"
      >
        <el-form-item label="角色名称">
          <el-input v-model="sysRole.roleName" />
        </el-form-item>
        <el-form-item label="角色编码">
          <el-input v-model="sysRole.roleCode" />
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
import sysRoleApi from '@/api/system/systemRole/sysRole'
export default {
  // 定义数据模型
  data() {
    return {
      list: [], // 数据列表
      total: 0, // 数据总数
      page: 1, // 页码
      limit: 5, // 每页显示条数
      searchObj: {}, // 查询条件
      multipleSelection: [], // 批量删除选择的数据id

      dialogVisible: false,
      sysRole: {},
      saveBtnDisabled: false
    }
  },
  // 页面渲染成功后在创建时获取取数
  created() {
    this.fetchData()
  },
  // 方法
  methods: {
    // 重置表单
    resetData() {
      this.searchObj = {
        roleName: ''
      }
      this.fetchData()
    },
    // 添加按钮点击事件
    add() {
      this.dialogVisible = true
    },
    // 编辑按钮点击事件
    edit(id) {
      this.dialogVisible = true
      this.fetchDataById(id)
    },
    // 选择复选框的方法
    handleSelectionChange(selection) {
      this.multipleSelection = selection
    },
    // 批量删除按钮点击事件
    batchRemove() {
      // 判断
      if (this.multipleSelection.length <= 0) {
        this.$message.warning('请选择要删除的记录')
      } else {
        this.$confirm('此操作将永久删除该记录，是否继续？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            var idList = []
            this.multipleSelection.forEach(item => {
              idList.push(item.id)
            })
            return sysRoleApi.batchRemove(idList)
          })
          .then((response) => {
            this.fetchData()
            this.$message.success(response.message || '删除成功！')
          })
      }
    },
    // 根据id查询角色信息
    fetchDataById(id) {
      sysRoleApi.getById(id).then((response) => {
        this.sysRole = response.data
      })
    },
    // 判断是添加还是修改
    saveOrUpdate() {
      this.saveBtnDisabled = true // 防止表单重复提交
      if (!this.sysRole.id) {
        this.saveData()
      } else {
        this.updateData()
      }
    },
    // 添加数据
    saveData() {
      sysRoleApi.save(this.sysRole).then((response) => {
        this.$message.success(response.message || '操作成功')
        this.dialogVisible = false
        this.fetchData(this.page)
      })
    },
    // 更新数据
    updateData() {
      sysRoleApi.updateById(this.sysRole).then((response) => {
        this.$message.success(response.message || '操作成功')
        this.dialogVisible = false
        this.fetchData(this.page)
      })
    },
    // 获取数据
    fetchData(current = 1) {
      this.page = current
      sysRoleApi
        .getPageList(this.page, this.limit, this.searchObj)
        .then((response) => {
          this.list = response.data.records
          this.total = response.data.total
        })
    },

    // 删除单个
    removeDataById(id) {
      this.$confirm('此操作将永久删除该记录，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          return sysRoleApi.removeById(id)
        })
        .then((response) => {
          this.fetchData(this.page)
          this.$message.success(response.message || '删除成功！')
        })
    }
  }
}
</script>
