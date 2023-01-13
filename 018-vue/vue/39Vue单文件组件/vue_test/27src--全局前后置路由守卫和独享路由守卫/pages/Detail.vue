<template>
  <ul>
<!-- query参数获取方法
    <li>消息编号：{{$route.query.id}}</li>
    <li>消息内容:{{$route.query.message}}</li>-->

    <!--params参数获取方法-->
<!--    <li>消息编号：{{$route.params.id}}</li>
    <li>消息内容:{{$route.params.message}}</li>-->

    <!--props获取参数方法-->
    <li>消息编号：{{id}}</li>
    <li>消息内容:{{message}}</li>
  </ul>
</template>

<script>
export default {
  name: "Detail",
  props:['id','message'],
  mounted() {
    console.log(this.$route)

  }
}
</script>

<style scoped>

</style>