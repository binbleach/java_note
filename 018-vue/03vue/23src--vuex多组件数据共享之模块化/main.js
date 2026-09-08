
/*1、先创建vuex文件夹然后船舰store.js文件
    或者store文件夹，index.js文件（导入时自动找index.js，就不用导那么到位了）
    在创建的js文件里，创建store.
*/
import Vue from 'vue'
import App from './App.vue'
import store from "./store";
Vue.config.productionTip = false
new Vue({
  store,    //2、使用store
  render: h => h(App),
}).$mount('#app')
