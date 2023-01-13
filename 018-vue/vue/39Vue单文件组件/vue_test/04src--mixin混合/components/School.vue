<!--
    功能：可以把多个组件共用的配置提取成一个混入对象
    使用方式：
        第一步定义混合，例如：
        export const a={
                data(){...},
                methods:{ addAge(){} }
            }
        第二部导入混合，例如
         import {a} from "../hunhe.js"

        第三部配置混合，例如：
          （1）全局混入：Vue.mixin(a)
          （2）局部混入：mixin:[a]

        第四步使用混合，例如直接使用混合中定义的方法：
        <button @click="addAge">点我年龄加1</button>
-->

<template>
  <div>
    <h1>前途</h1>
    <div class="demo">
      <h2>{{name}}</h2>
      <h2>{{address}}</h2>
      <h2>{{age}}</h2>

      <!--04使用混合-->
      <button @click="addAge">点我年龄加1</button>

    </div>
  </div>
</template>

<script>
//02导入混合  01在hunhe.js文件里是定义混合
import {a,b} from '../hunhe.js'

  export default {
    name:"School",
    data(){
      return {
        name:"电子科技大学成都学院",
        address:"百叶路一号",
        age:100
      }
    },

    //03定义混合
    mixins:[a,b],

  }
</script>

<style>
  .demo{
    background-color: beige;
  }
</style>