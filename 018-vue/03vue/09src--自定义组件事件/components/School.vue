<!--普通组件事件将school对象传给app：子给父传东西-->
<template>
  <div>
    <div class="demo">
      <h2>{{school.name}}</h2>
      <h2>{{school.address}}</h2>
      <h2>{{school.age}}</h2>
      <button @click="sendSchool">点我将对象传给app</button>
    </div>
  </div>
</template>

<script>

  export default {
    name:"School",
    props:["receive"],
    data(){
      return {
        school:{
          name:"电子科技大学成都学院",
          address:"百叶路一号",
          age:100}
      }
    },
    methods:{
      sendSchool(){
        this.receive(this.school)
      }
    }
  }
</script>

<style>
  .demo{
    background-color: beige;
  }
</style>