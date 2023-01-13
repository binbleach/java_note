//1、先写router目录的index.js文件
import App from './App.vue'
import Vue from 'vue'
//2、导入router文件
import router from "./router";
Vue.config.productionTip = false
new Vue({
  render: h => h(App),
  //4、配置router
  router
}).$mount('#app')
