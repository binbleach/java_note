<template>
  <div>
    <School/>
    <Student/>
    <h2 v-text="msg"></h2>
  </div>
</template>

<script>
import Student from "./components/Student";
import School from "./components/School";
export default {
  name: "App",
  components: {
    Student,
    School,
  },
  data(){
    return{
      msg:"你好呀傻逼06src"
    }
  },
  methods: {
  }
}
</script>

<style scoped>

</style>