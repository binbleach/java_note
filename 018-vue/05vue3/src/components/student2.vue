<template>
  <div>
    <h2>姓名：{{name}}</h2>
    <h2>年龄：{{age}}</h2>
    <button @click="changeName">修改名字</button>
    <button @click="addAge">添加年龄</button>
    <button @click="checkTel">查看电话</button>
  </div>
</template>

<script lang="ts" setup name="随便叫啥">
import {ref} from 'vue'
  /*
    1、setup语法糖，可以自动return
    2、语法糖后设置组件名：
        1）安装插件：pm i vite-plugin-vue-setup-extend -D
        2）vite.config.ts 里引入插件vite-plugin-vue-setup-extend并使用
        3）Vue 3.3+支持设置，无需下载插件：defineOptions({ name: '随便叫啥2' })
  */
  defineOptions({ name: '随便叫啥2' }) //优先级比插件的高
  let name = ref('黄家宾')
  let age = ref(25)
  let tel = '188....9328'
  function changeName() {
    name.value += '对' //4、这里的值是响应式的，可以修改，但是要加.value（模板展示会自动加）
  }
  function addAge(){
    age.value++
  }
  function checkTel(){
    alert(tel)
  }
</script>

<style scoped>

</style>
