<template>
  <div class="category">
    <h2>{{title}}分类</h2>
    <slot name="center"></slot>
    <slot name="footer"></slot>
    <!--
    不用再准备了，再App处传了
    <ul>
      <li v-for="(item,index) in listData" :key="index">{{item}}</li>
    </ul>
    -->
    <slot :girl="girl"></slot>
  </div>
</template>

<script>
export default {
  name: "Category",
  props:['title'],
  data(){
    return{
      girl:["她","他喜欢的她","喜欢他的她"]
    }
  }
}
</script>

<style scoped>
  .category{
    background-color: skyblue;
    width: 400px;
    height: 500px;
  }
  h2{
    text-align: center;
    background-color: orange;

  }
  img{
    width: 100%;
  }
</style>