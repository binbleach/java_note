<template>
  <div>
    <h2>姓名：{{name}}</h2>
    <h2>年龄：{{age}}</h2>
    <button @click="changeName">修改名字</button>
    <button @click="addAge">添加年龄</button>
    <button @click="checkTel">查看电话</button>
  </div>
</template>

<!--
  1、setup简写（语法糖），可以自动return
    1）<script> 标签里加 setup
    2）无需在 export default 里写 setup()
    3）无需写 return，会自动返回
  2、简写后，如何设置组件名（默认是文件名，可以用原写法，但是不优雅）：
    2.1 插件：
      1）安装插件：npm i vite-plugin-vue-setup-extend -D
      2）vite.config.ts 里引入插件vite-plugin-vue-setup-extend
      3）在<script>标签里设置name
    2.2 vue升级（优先级比插件的高）：
      1）Vue 3.3+支持设置，无需下载插件，直接用：defineOptions({ name: '随便叫啥2' })
  3、如何在setup里设置响应式数据
-->
<script lang="ts" setup name="随便叫啥">
  import {ref} from 'vue'
  defineOptions({ name: '随便叫啥2' })
  let name = ref('黄家宾')
  let age = ref(25)
  let tel = '188....9328'
  function changeName() {
    name.value += '对' //4、这里的值是响应式的，可以修改，但是要加.value（template用写，模板展示会自动加）
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
