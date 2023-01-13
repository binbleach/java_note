<template>
  <div class="app">
    <School/>
    <Student/>
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
}
</script>

<style scoped>
.app{
  background-color: #cccccc;
}
</style>