<template>
  <div>
    <h2>{{name}}</h2>
    <h2>{{age}}</h2>
  </div>
</template>

<script>
  export default {
    name:"Student",
    data(){
      return {
        name: "黄家宾",
        age: "18"
      }
    },
  }
</script>
