import axios from 'axios'
import { ElMessage, ElMessageBox } from 'element-plus'
import Cookies from 'js-cookie'
import router from '@/router'

// 创建axios实例
const service = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api', // 接口基础路径
    timeout: 5000,
    headers: {
        'Content-Type': 'application/json;charset=utf-8'
    }
})

// 请求拦截器：添加Token
service.interceptors.request.use(
    (config) => {
        const token = Cookies.get('token')
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`
        }
        return config
    },
    (error) => {
        return Promise.reject(error)
    }
)

// 响应拦截器：统一处理结果
service.interceptors.response.use(
    (response) => {
        const res = response.data
        // 后端返回code≠200则视为错误
        if (res.code !== 200) {
            ElMessage.error(res.msg || '请求失败')
            // Token失效/未登录
            if (res.code === 401) {
                ElMessageBox.confirm('登录已过期，请重新登录', '提示', {
                    confirmButtonText: '确定',
                    type: 'warning'
                }).then(() => {
                    Cookies.remove('token')
                    router.push('/login')
                })
            }
            return Promise.reject(res)
        } else {
            return res
        }
    },
    (error) => {
        ElMessage.error(error.message || '服务器错误')
        return Promise.reject(error)
    }
)

export default service