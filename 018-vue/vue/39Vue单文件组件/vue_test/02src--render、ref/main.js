
/*
    01该文件是整个项目的入口文件
*/
//02引入Vue
import Vue from 'vue' //03这个vue不全，是残缺版的，少了模板解析器（开发完后不需要了），不能解析模板，用这个得将app交给render就是05，否则用完整版
//import Vue from 'vue/dist/vue'  //引入完整版：核心+模板解析器
//04引入App组件，他是所有组件的父组件
import App from './App.vue'

Vue.config.productionTip = false

new Vue({
  //05将app组件放入容器吗，render是个函数，vue帮你掉的
  /*
      //下面是render的写法变异，参数createElement用来可以创建元素
      render(createElement){
        return createElement('h1','你好呀');
      }
      render:function(createElement){
        return createElement('h1','你好呀');
      }
      render:(createElement)=>{
        return createElement('h1','你好呀');
      }
      render:createElement=>{
        return createElement('h1','你好呀');
      }
      render:createElement=> createElement('h1','你好呀');
      render:q=>q('h1','你好呀');
  */
  render: h => h(App),
}).$mount('#app')
/*
    关于不同版本Vue:
      1、vue.js与vue.runtime.xxx.js的区别：
        （1）vue.js是完整版的Vue。包含：核心功能+模板解析器
        （2）vue.runtime.xxx.js是运行版的Vue，只包含核心功能，没有模板解析器
      2、因为vue.runtime.xxx.js没有模板解析器，所以不能使用template配置项，需要使用
        render函数接收到createElement函数去指定具体内容
*/
