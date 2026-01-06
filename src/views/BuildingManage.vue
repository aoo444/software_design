<template>
  <div class="card-container">
    <div class="page-header">
      <h3>建筑管理</h3>
    </div>

    <!-- 新增按钮 -->
    <el-button type="primary" @click="openAddDialog" style="margin-bottom: 20px">
      <el-icon><Plus /></el-icon>新增建筑
    </el-button>

    <!-- 表格 -->
    <el-table :data="buildingList" border stripe style="width: 100%">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="name" label="建筑名称" width="180" />
      <el-table-column prop="locationCode" label="位置编码" width="180" />
      <el-table-column prop="floorCount" label="楼层数" width="100" />
      <el-table-column prop="useType" label="使用类型" width="120" />
      <el-table-column prop="createTime" label="创建时间" width="200" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button type="primary" size="small" @click="handleEdit(scope.row)">
            编辑
          </el-button>
          <el-button type="danger" size="small" @click="handleDelete(scope.row.id)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 新增/编辑弹窗 -->
    <el-dialog
        v-model="dialogVisible"
        :title="dialogTitle"
        width="500px"
        @close="resetForm"
    >
      <el-form
          ref="formRef"
          :model="form"
          :rules="rules"
          label-width="100px"
      >
        <el-form-item label="建筑名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入建筑名称" />
        </el-form-item>
        <el-form-item label="位置编码" prop="locationCode">
          <el-input v-model="form.locationCode" placeholder="请输入位置编码" />
        </el-form-item>
        <el-form-item label="楼层数" prop="floorCount">
          <el-input v-model="form.floorCount" type="number" placeholder="请输入楼层数" />
        </el-form-item>
        <el-form-item label="使用类型" prop="useType">
          <el-select v-model="form.useType" placeholder="请选择使用类型">
            <el-option label="宿舍" value="宿舍" />
            <el-option label="实验室" value="实验室" />
            <el-option label="办公楼" value="办公楼" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
// 1. 新增导入 ElMessageBox（删除警告弹窗依赖）
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import request from '@/utils/request'

// 建筑列表
const buildingList = ref([])
// 弹窗相关
const dialogVisible = ref(false)
const dialogTitle = ref('新增建筑')
const formRef = ref(null)
// 表单数据
const form = ref({
  id: '',
  name: '',
  locationCode: '',
  floorCount: '',
  useType: ''
})
// 表单校验规则
const rules = ref({
  name: [{ required: true, message: '请输入建筑名称', trigger: 'blur' }],
  locationCode: [{ required: true, message: '请输入位置编码', trigger: 'blur' }],
  floorCount: [{ required: true, message: '请输入楼层数', trigger: 'blur' }],
  useType: [{ required: true, message: '请选择使用类型', trigger: 'change' }]
})

// 加载建筑列表
const getBuildingList = async () => {
  try {
    const res = await request.get('/building/list')
    buildingList.value = res.data
  } catch (error) {
    console.error('获取建筑列表失败：', error)
  }
}

// 新增弹窗
const openAddDialog = () => {
  dialogTitle.value = '新增建筑'
  dialogVisible.value = true
}

// 编辑
const handleEdit = (row) => {
  dialogTitle.value = '编辑建筑'
  form.value = { ...row }
  dialogVisible.value = true
}

// 2. 重构删除方法：添加二次确认警告弹窗 + 软删除提示
const handleDelete = async (id) => {
  try {
    // 弹出二次确认警告弹窗（适配软删除，提示可恢复）
    await ElMessageBox.confirm(
        '是否确认删除？',
        '删除警告',
        {
          confirmButtonText: '确认删除',
          cancelButtonText: '取消',
          type: 'warning', // 黄色警告图标
          draggable: true // 弹窗可拖拽，提升体验
        }
    )

    // 用户确认后执行删除请求（软删除）
    await request.delete(`/building/delete/${id}`)
    ElMessage.success('删除成功')
    getBuildingList() // 刷新建筑列表
  } catch (error) {
    // 区分“用户取消删除”和“接口调用失败”两种场景
    if (error.type === 'cancel') {
      ElMessage.info('已取消删除') // 取消操作的友好提示
    } else {
      // 错误提示包含后端返回的具体信息，便于排查
      ElMessage.error('删除失败：' + (error.response?.data?.msg || '服务器错误'))
      console.error('删除建筑失败：', error)
    }
  }
}

// 提交表单（新增/编辑）
const handleSubmit = async () => {
  try {
    await formRef.value.validate()
    if (form.value.id) {
      // 编辑
      await request.put('/building/update', form.value)
      ElMessage.success('编辑成功')
    } else {
      // 新增
      await request.post('/building/add', form.value)
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
    getBuildingList()
  } catch (error) {
    console.error('提交失败：', error)
  }
}

// 重置表单
const resetForm = () => {
  formRef.value.resetFields()
  form.value = {
    id: '',
    name: '',
    locationCode: '',
    floorCount: '',
    useType: ''
  }
}

// 页面加载时获取列表
onMounted(() => {
  getBuildingList()
})
</script>

<style scoped>
</style>