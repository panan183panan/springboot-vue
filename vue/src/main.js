import Vue from 'vue'
import App from './App.vue'
import store from "./store"
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import "./assets/globle.css"
import request from "@/utils/request"

Vue.use(ElementUI,{size: "mini"});
Vue.prototype.request=request

Vue.config.productionTip = false

new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app')
