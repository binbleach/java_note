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

<style>
  .demo{
    background-color: beige;
  }
</style>