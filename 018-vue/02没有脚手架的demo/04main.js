import Vue from "../js/vue"
import App from "./03App";
new Vue({
    el:"#app",
    template:`<App></App>`, //告诉 Vue 根实例要渲染什么内容，这里就是渲染已注册的 App 组件
    components:{
        App
    }
})
