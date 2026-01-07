<template>
  <div class="card-container">
    <div class="page-header">
      <h3>电表设备管理</h3>
    </div>

    <!-- 新增按钮 -->
    <el-button type="primary" @click="openAddDialog" style="margin-bottom: 20px">
      <el-icon><Plus /></el-icon>新增设备
    </el-button>

    <!-- 表格 -->
    <el-table :data="meterList" border stripe style="width: 100%">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="name" label="设备名称" width="180" />
      <el-table-column prop="sn" label="设备编号" width="180" />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="scope">
          <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
            {{ scope.row.status === 1 ? '在线' : '离线' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="ratedPower" label="额定功率(W)" width="120" />
      <el-table-column prop="buildingId" label="所属建筑ID" width="120" />
      <el-table-column prop="roomNumber" label="房间号" width="100" />
      <el-table-column prop="isValid" label="是否有效" width="100">
        <template #default="scope">
          <el-tag :type="scope.row.isValid === 1 ? 'success' : 'danger'">
            {{ scope.row.isValid === 1 ? '有效' : '无效' }}
          </el-tag>
        </template>
      </el-table-column>
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
        <el-form-item label="设备名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入设备名称" />
        </el-form-item>
        <el-form-item label="设备编号" prop="sn">
          <el-input v-model="form.sn" placeholder="请输入设备编号" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option label="在线" value="1" />
            <el-option label="离线" value="0" />
          </el-select>
        </el-form-item>
        <el-form-item label="额定功率(W)" prop="ratedPower">
          <el-input v-model="form.ratedPower" type="number" placeholder="请输入额定功率" />
        </el-form-item>
        <el-form-item label="所属建筑ID" prop="buildingId">
          <el-input v-model="form.buildingId" type="number" placeholder="请输入所属建筑ID" />
        </el-form-item>
        <el-form-item label="房间号" prop="roomNumber">
          <el-input v-model="form.roomNumber" placeholder="请输入房间号" />
        </el-form-item>
        <el-form-item label="是否有效" prop="isValid">
          <el-select v-model="form.isValid" placeholder="请选择是否有效">
            <el-option label="有效" value="1" />
            <el-option label="无效" value="0" />
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
// 1. 导入 ElMessageBox（删除警告弹窗依赖）
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import request from '@/utils/request'

// 设备列表
const meterList = ref([])
// 弹窗相关
const dialogVisible = ref(false)
const dialogTitle = ref('新增设备')
const formRef = ref(null)
// 表单数据
const form = ref({
  id: '',
  name: '',
  sn: '',
  status: '',
  ratedPower: '',
  buildingId: '',
  roomNumber: '',
  isValid: ''
})
// 表单校验规则
const rules = ref({
  name: [{ required: true, message: '请输入设备名称', trigger: 'blur' }],
  sn: [{ required: true, message: '请输入设备编号', trigger: 'blur' }],
  status: [{ required: true, message: '请选择状态', trigger: 'change' }],
  ratedPower: [{ required: true, message: '请输入额定功率', trigger: 'blur' }],
  buildingId: [{ required: true, message: '请输入所属建筑ID', trigger: 'blur' }],
  roomNumber: [{ required: true, message: '请输入房间号', trigger: 'blur' }],
  isValid: [{ required: true, message: '请选择是否有效', trigger: 'change' }]
})

// 加载设备列表
const getMeterList = async () => {
  try {
    const res = await request.get('/meter/list')
    meterList.value = res.data
  } catch (error) {
    console.error('获取设备列表失败：', error)
  }
}

// 新增弹窗
const openAddDialog = () => {
  dialogTitle.value = '新增设备'
  dialogVisible.value = true
}

// 编辑
const handleEdit = (row) => {
  dialogTitle.value = '编辑设备'
  form.value = { ...row }
  dialogVisible.value = true
}

// 2. 删除方法：添加二次确认警告弹窗 + 软删除提示
const handleDelete = async (id) => {
  try {
    // 弹出二次确认警告弹窗
    await ElMessageBox.confirm(
        '是否确认删除？', // 软删除提示语
        '删除警告', // 弹窗标题
        {
          confirmButtonText: '确认删除',
          cancelButtonText: '取消',
          type: 'warning', // 黄色警告图标
          draggable: true // 弹窗可拖拽（提升体验）
        }
    )

    // 用户确认后执行删除请求
    await request.delete(`/meter/delete/${id}`)
    ElMessage.success('删除成功') // 适配软删除的提示
    getMeterList() // 刷新列表
  } catch (error) {
    // 区分“用户取消”和“真正的错误”
    if (error.type === 'cancel') {
      ElMessage.info('已取消删除') // 取消操作的友好提示
    } else {
      ElMessage.error('删除失败：' + (error.response?.data?.msg || '服务器错误'))
      console.error('删除设备失败：', error)
    }
  }
}

// 提交表单（新增/编辑）
const handleSubmit = async () => {
  try {
    await formRef.value.validate()
    if (form.value.id) {
      // 编辑
      await request.put('/meter/update', form.value)
      ElMessage.success('编辑成功')
    } else {
      // 新增
      await request.post('/meter/add', form.value)
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
    getMeterList()
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
    sn: '',
    status: '',
    ratedPower: '',
    buildingId: '',
    roomNumber: '',
    isValid: ''
  }
}

// 页面加载时获取列表
onMounted(() => {
  getMeterList()
})
</script>

<style scoped>
</style>