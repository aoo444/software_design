<template>
  <el-container style="height: 100%">
    <!-- 侧边栏 -->
    <el-aside width="200px" style="background-color: #2e3b4e">
      <el-menu
          default-active="1"
          class="el-menu-vertical-demo"
          background-color="#2e3b4e"
          text-color="#fff"
          active-text-color="#ffd04b"
          @select="handleMenuSelect"
      >
        <el-menu-item index="1">
          <el-icon><House /></el-icon>
          <template #title>建筑管理</template>
        </el-menu-item>
        <el-menu-item index="2">
          <el-icon><Monitor /></el-icon>
          <template #title>电表设备管理</template>
        </el-menu-item>
        <el-menu-item index="3">
          <el-icon><DataAnalysis /></el-icon>
          <template #title>能耗数据监测</template>
        </el-menu-item>
        <el-menu-item index="4">
          <el-icon><Warning /></el-icon>
          <template #title>告警记录</template>
        </el-menu-item>
        <el-menu-item index="5" @click="handleLogout">
          <el-icon><SwitchButton /></el-icon>
          <template #title>退出登录</template>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <!-- 主内容区 -->
    <el-container>
      <el-header style="text-align: right; font-size: 12px">
        <el-dropdown>
          <i class="el-icon-setting" style="margin-right: 15px"></i>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item>查看</el-dropdown-item>
              <el-dropdown-item>新增</el-dropdown-item>
              <el-dropdown-item>删除</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
        <span>能耗监测平台</span>
      </el-header>
      <el-main>
        <router-view class="main-content" />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import Cookies from 'js-cookie'
import {
  House,
  Monitor,
  DataAnalysis,
  Warning,
  SwitchButton
} from '@element-plus/icons-vue'

const router = useRouter()

// 菜单选择跳转
const handleMenuSelect = (index) => {
  const pathMap = {
    '1': '/home/building',
    '2': '/home/meter',
    '3': '/home/energy',
    '4': '/home/alarm'
  }
  if (pathMap[index]) {
    router.push(pathMap[index])
  }
}

// 退出登录
const handleLogout = () => {
  ElMessageBox.confirm('确定退出登录？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    Cookies.remove('token')
    ElMessage.success('退出成功')
    router.push('/login')
  })
}
</script>

<style scoped>
.el-header {
  background-color: #fff;
  color: #333;
  line-height: 60px;
  border-bottom: 1px solid #e6e6e6;
}

.el-aside {
  color: #333;
}
</style>