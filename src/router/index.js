import { createRouter, createWebHistory } from 'vue-router'
import Cookies from 'js-cookie'
import { ElMessage } from 'element-plus'

// 导入页面组件
import Login from '@/views/Login.vue'
import Home from '@/views/Home.vue'
import BuildingManage from '@/views/BuildingManage.vue'
import MeterManage from '@/views/MeterManage.vue'
import EnergyData from '@/views/EnergyData.vue'
import AlarmRecord from '@/views/AlarmRecord.vue'

const routes = [
  {
    path: '/',
    redirect: '/home'
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/home',
    name: 'Home',
    component: Home,
    meta: { requireAuth: true }, // 需要登录
    children: [
      {
        path: 'building',
        name: 'BuildingManage',
        component: BuildingManage,
        meta: { title: '建筑管理' }
      },
      {
        path: 'meter',
        name: 'MeterManage',
        component: MeterManage,
        meta: { title: '电表设备管理' }
      },
      {
        path: 'energy',
        name: 'EnergyData',
        component: EnergyData,
        meta: { title: '能耗数据监测' }
      },
      {
        path: 'alarm',
        name: 'AlarmRecord',
        component: AlarmRecord,
        meta: { title: '告警记录' }
      }
    ]
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/home'
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫：验证登录状态
router.beforeEach((to, from, next) => {
  const token = Cookies.get('token')
  // 需要登录但未登录
  if (to.meta.requireAuth && !token) {
    ElMessage.warning('请先登录')
    next('/login')
  } else {
    next()
  }
})

export default router
