<template>
<!--场景：用户在输入框输入了一堆信息，点击页面跳转后，组件被销毁了，重新点回来重新渲染，输入的信息丢失了-->
  <ul>
    <li :style="{opacity}">我是一个漂亮的组件</li>
    <li>news001 <input type="text"></li>
    <li>news002  <input type="text"></li>
    <li>news003  <input type="text"></li>
  </ul>
</template>

<script>
export default {
  name: "News",
  data(){
    return {
      opacity:1,
    }
  },
  beforeDestroy() {
    console.log("New组件将被销毁")
  },
  /*激活调用，路由组件特有钩子,配合定时器用既可以保存数据，
  又可以在页面失活状态关闭定时器。*/
  activated() {
    console.log("News组件被激活了");
    this.timer = setInterval(()=>{
      this.opacity -=0.01;
      if(this.opacity<=0) this.opacity=1
    },16)
  },
  //失活后调用
  deactivated() {
    console.log("News组件被失活了");
      clearInterval(this.timer)
  }

}
</script>

<style scoped>

</style>