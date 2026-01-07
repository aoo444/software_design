<template>
  <div class="card-container">
    <div class="page-header">
      <h3>能耗数据监测</h3>
    </div>

    <!-- 设备选择 -->
    <el-form :inline="true" :model="searchForm" class="demo-form-inline" style="margin-bottom: 20px">
      <el-form-item label="设备ID">
        <el-input v-model="searchForm.meterId" placeholder="请输入设备ID" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getLatestData">查询最新数据</el-button>
        <el-button type="success" @click="getLatest10Data">查询最近10条数据</el-button>
      </el-form-item>
    </el-form>

    <!-- 最新数据展示 -->
    <el-card v-if="latestData" style="margin-bottom: 20px">
      <template #header>
        <div class="card-header">
          <span>设备 {{ latestData.meterName }} 最新能耗数据</span>
        </div>
      </template>
      <el-descriptions :column="3" border>
        <el-descriptions-item label="设备ID">{{ latestData.meterId }}</el-descriptions-item>
        <el-descriptions-item label="电压(V)">{{ latestData.voltage }}</el-descriptions-item>
        <el-descriptions-item label="电流(A)">{{ latestData.current }}</el-descriptions-item>
        <el-descriptions-item label="实时功率(W)">{{ latestData.realPower }}</el-descriptions-item>
        <el-descriptions-item label="总能耗(kWh)">{{ latestData.totalEnergy }}</el-descriptions-item>
        <el-descriptions-item label="采集时间">{{ latestData.collectTime }}</el-descriptions-item>
      </el-descriptions>
    </el-card>

    <!-- 最近10条数据表格 -->
    <el-card v-if="latest10Data.length > 0">
      <template #header>
        <div class="card-header">
          <span>设备 {{ searchForm.meterId }} 最近10条能耗数据</span>
        </div>
      </template>
      <el-table :data="latest10Data" border stripe style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="meterId" label="设备ID" width="100" />
        <el-table-column prop="voltage" label="电压(V)" width="100" />
        <el-table-column prop="current" label="电流(A)" width="100" />
        <el-table-column prop="realPower" label="实时功率(W)" width="120" />
        <el-table-column prop="totalEnergy" label="总能耗(kWh)" width="120" />
        <el-table-column prop="collectTime" label="采集时间" width="200" />
      </el-table>
    </el-card>

    <!-- 图表容器 -->
    <div style="margin-top: 30px; width: 100%; height: 400px;">
      <h4 style="margin-bottom: 10px;">功率趋势图</h4>
      <!-- 用Vue ref绑定，避免id选择器的挂载问题 -->
      <div ref="chartRef" style="width: 100%; height: 350px;"></div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import * as echarts from 'echarts'
import request from '@/utils/request'

// 搜索表单
const searchForm = ref({
  meterId: ''
})
// 最新数据
const latestData = ref(null)
// 最近10条数据
const latest10Data = ref([])
// 图表Ref
const chartRef = ref(null)
// 图表实例（全局）
let myChart = null

// 获取最新数据
const getLatestData = async () => {
  if (!searchForm.value.meterId) {
    ElMessage.warning('请输入设备ID')
    return
  }
  try {
    const res = await request.get(`/energy-data/latest/${searchForm.value.meterId}`)
    latestData.value = res.data
  } catch (error) {
    ElMessage.error('获取最新数据失败')
    console.error('获取最新数据失败：', error)
  }
}

// 获取最近10条数据
const getLatest10Data = async () => {
  if (!searchForm.value.meterId) {
    ElMessage.warning('请输入设备ID')
    return
  }

  try {
    const res = await request.get(`/energy-data/latest10/${searchForm.value.meterId}`)
    latest10Data.value = res.data || []

    // 基础数据处理：只过滤空数据，不做严格校验
    const chartData = latest10Data.value
        .filter(item => item.collectTime && item.realPower)
        .map(item => ({
          time: item.collectTime.split('T')[1] || item.collectTime, // 提取时间
          power: Number(item.realPower) || 0 // 转数字，空则设0
        }))

    // 初始化图表（不管数据多少，都渲染）
    initChart(chartData)
  } catch (error) {
    ElMessage.error('获取数据失败')
    console.error('获取最近10条数据失败：', error)
  }
}

// 初始化图表：ECharts自动计算刻度/布局
const initChart = (chartData) => {
  // 确保容器存在
  if (!chartRef.value) return

  // 实例不存在则创建，存在则复用
  if (!myChart) {
    myChart = echarts.init(chartRef.value)
  }

  // 提取轴数据
  const xData = chartData.map(item => item.time)
  const yData = chartData.map(item => item.power)

  // ECharts自动处理布局/刻度
  const option = {
    title: { text: '实时功率趋势', left: 'center' },
    xAxis: { type: 'category', data: xData, axisLabel: { rotate: 30 } },
    yAxis: { type: 'value', name: '功率(W)', min: 0 },
    series: [{ type: 'line', data: yData, smooth: true }],
    tooltip: { trigger: 'axis' },
    grid: { left: '5%', right: '5%', bottom: '15%', top: '10%' }
  }

  // 设置配置
  myChart.setOption(option, true)

  // 绑定窗口缩放
  window.addEventListener('resize', () => {
    if (myChart) myChart.resize()
  })
}

</script>

<style scoped>
.card-container { padding: 20px; box-sizing: border-box; }
.page-header { margin-bottom: 20px; font-size: 16px; font-weight: 600; }
.card-header { font-weight: 600; }
</style>