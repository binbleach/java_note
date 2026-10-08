import { createApp } from 'vue' //1、引入createApp用于创建应用
import App from './App.vue'

createApp(App).mount('#app')    //2、将根组件App挂载到html里

//1、入口文件是index.html，然后才是main.ts
//2、这是一个用vue-create创建的vue3的支持ts的项目
