<template>
  <div>
    <h2>姓名：{{name}}</h2>
    <h2>年龄：{{age}}</h2>
    <button @click="addAge">过生日</button>
  </div>
  <div>
    <h2>车牌：{{car.brand}}</h2>
    <h2>车价：{{car.price}}</h2>
    <button @click="bargain">砍价</button>
  </div>
  <div>
    <h2>车牌：{{car2.brand}}</h2>
    <h2>车价：{{car2.price}}</h2>
    <button @click="bargain2">砍价</button>
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
  3、如何在setup里设置响应式数据：
    1）ref：可以作用于基本类型/对象类型（底层还是reactive），会生成RefImpl对象。
    2）reactive：只能作用于对象类型，会生成Proxy对象。
    注意：
    1）ref需要.value去调用属性，可以设置Volar插件自动添加
    2）reactive不能直接改对象，可以整体改属性Object.assign(car,newCar)；ref可以：car.value = newCar
      同样的ref不能：age = ref(9)
-->
<script lang="ts" setup name="随便叫啥">
  import {ref,reactive} from 'vue'
  defineOptions({ name: '随便叫啥2' })
  let name = '黄家宾'
  let age = ref(25)
  function addAge(){
    age.value++ //4、这里的值是响应式的，可以修改，但是要加.value（template不用加.value，模板展示会自动加）
  }
  let car = reactive({brand:'奔驰',price:200000})
  function bargain(){
    car.price -=500
  }
  let car2 = ref({brand:'宝马',price:300000})
  function bargain2(){
    car2.value.price -=1000 //5.必须要.value去调用
  }
</script>

<style scoped>

</style>
