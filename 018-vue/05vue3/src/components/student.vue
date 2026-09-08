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

<script lang="ts">
export default {
  name: "student",
  data(){
    return {
      d: this.name //1、vue2里的data是可以读到setup返回的那么的（旧可以用新，新不可以用旧）
    }
  },
  setup(){
    // 2、setup函数里的this是undefined,vue3弱化了this。
    let name = '黄家宾'
    let age = 25
    let tel = '188....9328'
    function changeName() {
      name += '对' //3、这里的值不是响应式的，修改也没用，显示并没变
    }
    function addAge(){
      age++
    }
    function checkTel(){
      alert(tel)
    }
    return {name,age,changeName,addAge,checkTel}
    //4、setup的返回值可以是渲染函数，会直接渲染haha，template都不重要了
    // return function () {return '哈哈'}
  }
}
</script>

<style scoped>

</style>
