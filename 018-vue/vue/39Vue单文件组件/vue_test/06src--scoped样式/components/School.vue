<template>
  <div>
    <h1>前途</h1>
    <div class="demo">
      <h2>{{name|mySlice}}</h2>
      <h2>{{address}}</h2>
      <h2>{{age}}</h2>
      <input type="text" v-fbind="age">

    </div>
  </div>
</template>

<script>

  export default {
    name:"School",
    data(){
      return {
        name:"电子科技大学成都学院",
        address:"百叶路一号",
        age:100
      }
    },
  }
</script>

<!--
scoped作用：让样式直在当前布局生效，防止冲突，一般不在App.vue文件写scoped
            样式冲突：当两个组件有同一个样式时会发生冲突。后引入的样式会覆盖先引入的
            写法：<style scoped>
        lang作用：标识style的语言可以十css也可以是les
-->

<style scoped lang="css">
  .demo{
    background-color: beige;
  }
</style>