<template>
    <div class="container">
      <Search/>
      <List/>
    </div>
</template>

<script>
//import "./assets/css/bootstrap.css" 通过import导入会有严格检查，有没用到的资源会保存。解决，css放在public下的css目录下，再index引入
import List from "./components/List";
import Search from "./components/Search";
export default {
  name: "App",
  components:{
    List,
    Search
  },
  data(){
    return{

    }
  },
  methods:{

  }
}
</script>

<style scoped>

</style>