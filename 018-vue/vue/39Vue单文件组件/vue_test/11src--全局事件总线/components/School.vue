<!--普通组件事件将school对象传给app：子给父传东西-->
<template>
  <div>
    <div class="demo">
    </div>
  </div>
</template>

<script>

  export default {
    name:"School",
    data(){
      return {
        school:{
          name:"电子科技大学成都学院",
          address:"百叶路一号",
          age:100
        }
      }
    },
    methods:{
    },
    //注：$bus是在vm初始化，给Vue原型对象添加的属性vm本身$bus=vm
    //利用$bus，绑定事件event。谁想给我传递参数，那谁就利用$bus.$emit()触发事件
    mounted() {
      this.$bus.$on("event1",(data)=>{
        console.log(JSON.stringify(data));
      })
    },
    //细节：在当前组件销毁前，解除绑定事件，$bus没销毁
    destroyed() {
      this.$bus.$off("event1");
    }
  }
</script>

<style>
  .demo{
    background-color: beige;
  }
</style>