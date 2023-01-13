<template>
  <div class="demo">
  <h2>{{name}}</h2>
  <h2>{{address}}</h2>
  </div>
</template>

<script>
  export default {
    name:"School",
    data(){
      return {
        name: "电子科技大学成都学院",
        address: "百叶路一号"
      }
    }
  }
</script>

<style>
  .demo{
    background-color: beige;
  }
</style>