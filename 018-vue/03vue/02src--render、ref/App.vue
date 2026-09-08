
<!--
      ref属性：
        1、被用来给元素或子组件注册引用信息（id得替代者）
        2、应用在html标签上获取的是真实DOM元素，应用在组件标签上是组件实例对象（vc）
        3、使用方式：
        （1）打标识：<h1 ref="xxx">......</h1> 或 <School ref="xxx"/>
        （2）获取：this.$refs.xxx
-->
<template>
  <div>
    <Student></Student>
    <School ref="sch"/>
    <h2 v-text="msg" ref="text"></h2>
    <button @click="showDOM">点我获取School实例对象就是vc,和<h2>DOM</h2></button>
  </div>
</template>

<script>
import School from "./components/School";
import Student from "./components/Student";
export default {
  name: "App",
  components: {
    School,
    Student
  },
  data(){
    return{
      msg:"你好呀傻逼02src"
    }
  },
  methods: {
    showDOM() {
      console.log(this.$refs.sch);
      console.log(this.$refs.text);
    }

  }
}
</script>

<style scoped>

</style>