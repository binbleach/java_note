<template>
  <div>
    <Student></Student>
    <School ref="sch"/>
    <button @click="showDOM">点我获取School实例对象就是vc</button>
  </div>
</template>

<script>
import School from "./01School";
import Student from "./02Student";
export default {
  name: "App",
  components:{
    School,
    Student
  },
  methods:{
    showDOM(){
      console.log(this.$refs.sch)
    }
  }
}
</script>

<style scoped>

</style>