<template>
  <div>
    <School/>
    <Student/>
    <h2 v-text="msg"></h2>
  </div>
</template>

<script>
import School from "./components/School";
import Student from "./components/Student";
export default {
  name: "App",
  components: {
    Student,
    School,
  },
  data(){
    return{
      msg:"你好呀傻逼05src"
    }
  },
  methods: {
  }
}
</script>

<style scoped>

</style>