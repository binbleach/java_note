
/*
    01、main.js是整个项目的入口文件
    vue核心：vm = new Vue() -> App.vue -> 其他子组件.vue
    声明渲染的方式：
      1、template：必须完整版可用（参考”没有脚手架的demo“）
      2、render：残缺版也可以用
      3、<template></template>：只能vue组件使用，vue-loader会把<template>里的模板提前编译成 render 函数
    注：组件不声明渲染会报错，使用残缺版vue，new Vue()时不声明渲染会报错
*/
//02这里引入的Vue是个残缺版，缺少模板编译器（生产环境是打包成js、html的，不需要模板解析，用这个可以瘦身）。
import Vue from 'vue' //引入的是node_modules/vue/package.json里第六行module参数指向的dist/vue.runtime.esm.js文件
// import Vue from 'vue/dist/vue'  //03、这里引入完整版vue：核心+模板解析器
//04引入App组件，他是所有组件的父组件
import App from './App.vue'

Vue.config.productionTip = false

new Vue({

  //05render是个函数，vue帮你掉的。他会传一个参数createElement可以用来创建元素
  /*
      //下面是render函数的写法的由来，参数createElement用来可以创建元素
      render:function(createElement){
        return createElement('h1','你好呀'); //相当于template:`<h1>你好啊</h1>`
      }
      //语法糖，就是简写
      render(createElement){
        return createElement('h1','你好呀');
      }
      //没用到this可以写成箭头函数
      render:(createElement)=>{
        return createElement('h1','你好呀');
      }
      // 单个参数简写
      render:createElement=>{
        return createElement('h1','你好呀');
      }
      // 单个返回简写
      render:createElement=> createElement('h1','你好呀');
      // 变更参数名后得到
      render:q=>q('h1','你好呀');
  */
  render: h => h(App),  // 传的是vue组件不是上面那种html标签名，所以直接写成App就行
}).$mount('#app')
/*
    关于不同版本Vue:
      1、vue.js与vue.runtime.xxx.js的区别：
        （1）vue.js是完整版的Vue。包含：核心功能+模板解析器
        （2）vue.runtime.xxx.js是运行版的Vue，只包含核心功能，没有模板解析器
      2、因为vue.runtime.xxx.js没有模板解析器，所以不能使用template配置项，需要使用
        render函数接收到createElement函数去指定具体内容
*/
