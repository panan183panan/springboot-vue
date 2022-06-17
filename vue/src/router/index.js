import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/Manage.vue'
import store from "@/store"

Vue.use(VueRouter)

const routes = [
  {
    path: '/main',
    component: ()=> import('../views/Manage'),
    redirect :"/main/home",
    children : [
      {
        path: 'home', name: '首页', component: ()=> import('../views/Home')
      },
      {
        path: 'user', name: '用户管理', component: ()=> import('../components/User')
      },
      {
        path: 'person', name: '个人信息', component: ()=> import('../views/Person')
      }

    ]
  },
  {
    path: '/login',
    // redirect: "/login",
    name: 'Login',
    component : () => import('../views/Login')
  },
  {
    path: '/register',
    name: 'register',
    component : () => import('../views/Register')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  localStorage.setItem("currentPathName",to.name) //设置当前路由名称
  store.commit("setPath")
  next();
})

export default router
