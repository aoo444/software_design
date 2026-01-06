<template>
  <div class="card-container">
    <div class="page-header">
      <h3>告警记录</h3>
    </div>

    <!-- 设备选择 -->
    <el-form :inline="true" :model="searchForm" class="demo-form-inline" style="margin-bottom: 20px">
      <el-form-item label="设备ID">
        <el-input v-model="searchForm.meterId" placeholder="请输入设备ID" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getAlarmList">查询告警记录</el-button>
      </el-form-item>
    </el-form>

    <!-- 告警列表 -->
    <el-table :data="alarmList" border stripe style="width: 100%">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="meterId" label="设备ID" width="100" />
      <el-table-column prop="alarmType" label="告警类型" width="120">
        <template #default="scope">
          <el-tag type="danger">
            {{ scope.row.alarmType === 'OVERLOAD' ? '功率超限' : '电压异常' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="alarmValue" label="告警数值" width="120" />
      <el-table-column prop="alarmDetail" label="告警详情" min-width="200" />
      <el-table-column prop="triggerTime" label="触发时间" width="200" />
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import request from '@/utils/request'

// 搜索表单
const searchForm = ref({
  meterId: ''
})
// 告警列表
const alarmList = ref([])

// 获取告警列表
const getAlarmList = async () => {
  if (!searchForm.value.meterId) {
    ElMessage.warning('请输入设备ID')
    return
  }
  try {
    const res = await request.get(`/alarm/list/${searchForm.value.meterId}`)
    alarmList.value = res.data || []
  } catch (error) {
    ElMessage.error('获取告警记录失败')
    console.error('获取告警记录失败：', error)
  }
}

// 页面加载
onMounted(() => {})
</script>

<style scoped>
</style>