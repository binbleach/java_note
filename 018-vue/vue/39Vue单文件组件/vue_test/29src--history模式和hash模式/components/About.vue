<template>
  <h2>我是About的内容</h2>
</template>

<script>
export default {
  name: "About",

  //通过路由规则进入该组件时被调用
  beforeRouteEnter(to,from,next){
    console.log("组件已经进入");
    console.log(to);
    console.log(from);
    next();
  },
  //通过路由规则离开该组件时被调用
  beforeRouteLeave(to,from,next){
    console.log("组件已经切换");
    console.log(to);
    console.log(from);
    next();
  }
}
</script>

<style scoped>

</style>