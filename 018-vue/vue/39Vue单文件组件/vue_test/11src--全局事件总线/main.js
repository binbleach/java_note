
import Vue from 'vue'
import App from './App.vue'
Vue.config.productionTip = false
/*全局事件总线
  1、功能：可以实现子组件之间相互通信
  2、要求：
         1）所有组件都能访问得到
         2）有$on,$off,$emit等方法
  3、实现：
        1）vc:     const vc = Vue.extend({})
                  const  d = new vc();
                  Vue.prototype.$bus = d
        2）vm:实现如下：
*/
new Vue({

  render: h => h(App),
  //在vm初始化之前给Vue原型对象加一个$bus属性内容是vm本身，$bus是属性，也是组件，供其他组件访问
  beforeCreate() {
    Vue.prototype.$bus = this;
  }
}).$mount('#app')
/*
  关于vc回顾：extend()你得有，然后你写了<Student/>标签在APP组件中，vue才帮你new，然后才出来vc
*/
