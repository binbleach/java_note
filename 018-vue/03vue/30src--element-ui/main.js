//一、注释掉的是完整引入
import Vue from 'vue'
import App from './App.vue';
//二、引入element-ui组件库
//import ElementUI from 'element-ui';
//三、引入element-ui全部样式
//import 'element-ui/lib/theme-chalk/index.css';
//四、应用element-ui
//Vue.use(ElementUI);

//1、按需引入、最新的脚手架babel.config.js文件跟官网的也有所不同
import {Button,Row,DatePicker} from "element-ui";
//2、注册全局组件
Vue.component(Button.name,Button);
Vue.component(Row.name,Row)
Vue.component(DatePicker.name,DatePicker)

Vue.config.productionTip = false
new Vue({
  render: h => h(App),
}).$mount('#app')
