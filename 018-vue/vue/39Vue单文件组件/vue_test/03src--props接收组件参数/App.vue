<template>
  <div>
    <School name="电子科技大学" address="电子科大路" :age="200"/>
    <School name="电子科技大学成都学院" address="百叶路一号" :age="100"/>
    <School name="电子厂" address="深圳" :age="500"/>
    <h2 v-text="msg"></h2>
  </div>
</template>

<script>
import School from "./components/School";
export default {
  name: "App",
  components: {
    School,
  },
  data(){
    return{
      msg:"你好呀傻逼03src"
    }
  },
  methods: {
  }
}
</script>

<style scoped>

</style>