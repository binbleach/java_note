
<template>
  <div>
    <div class="row">
      <Banner/>
    </div>
    <div class="row">
      <div class="col-xs-2 col-xs-offset-2">
        <div class="list-group">
          <!--原始html中，我们用a标签使页面跳转
            <a class="list-group-item" href="./about.html">About</a>
            <a class="list-group-item active" href="./home.html">Home</a>-->
          <!--
            vue路由模式，实现页面跳转,展示过的组件将会被销毁
            每个组件的$router相同，$route不同,一个vue一个路由器，多个路由的意思
            路由组件（Home.About）一般放在pages文件夹，一般组件（Banner）一般放在components文件夹
          -->
          <router-link class="list-group-item" active-class="active" to="/about">About</router-link>
          <router-link class="list-group-item" active-class="active" to="/home">Home</router-link>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="panel">
          <div class="panel-body">
            <!--展示-->
            <router-view></router-view>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Home from "./components/Home";
import About from "./components/About";
import Banner from "./components/Banner";
export default {
  name: "App",
  components: {
    Home,
    About,
    Banner
  },
  data(){
    return{

    }
  },
}
</script>

<style scoped>

</style>