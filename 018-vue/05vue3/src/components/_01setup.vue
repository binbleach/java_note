<template>
  <div>
    <h2>姓名：{{name}}</h2>
    <h2>年龄：{{age}}</h2>
    <h2>data：{{d}}</h2>
    <button @click="changeName">修改名字</button>
    <button @click="addAge">添加年龄</button>
    <button @click="checkTel">查看电话</button>
  </div>
</template>
<!--
  vue2是选项式API写法：功能拆散在data()、methods、watch里，不便维护和复用
  vue3是组合式API写法：将功能从data()、methods、watch抽取成一个function，功能独立好维护。（vue3也支持选项式）
  1、setup()是vue3组合式 API 的入口函数，可以在setup()里定义数据和方法
-->
<script lang="ts">
export default {
  name: "student",
  data(){
    return {
      d: this.name //2、vue2里的data()是可以读到setup()返回的数据的（旧可以用新，新不可以用旧）
    }
  },
  setup(){
    // 3、setup函数里的this是undefined，vue3弱化了this。不能用this调用vue2里的data()数据
    let name = '黄家宾'
    let age = 25
    let tel = '188....9328'
    function changeName() {
      name += '对' //4、这里的值不是响应式的，修改也没用，显示并没变
    }
    function addAge(){
      age++
    }
    function checkTel(){
      alert(tel)
    }
    return {name,age,changeName,addAge,checkTel}
    //5、setup()的返回值可以是渲染函数，会直接渲染'哈哈'，<template>都不重要了
    // return function () {return '哈哈'}
  }
}
</script>

<style scoped>

</style>
