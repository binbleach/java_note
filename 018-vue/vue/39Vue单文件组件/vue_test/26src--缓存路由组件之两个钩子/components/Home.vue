<template>
  <div>
    <h2>Home组件内容</h2>
    <div>
      <ul class="nav nav-tabs">
        <li>
          <router-link replace class="list-group-item" active-class="active" to="/home/news">News</router-link>
        </li>
        <li>
          <router-link class="list-group-item" active-class="active" to="/home/message">Message</router-link>
        </li>
      </ul>
      <!--keep-alive标签会把包裹的数据缓存到浏览器里，其实就是不走销毁流程，组件不会被销毁
          include=”News“表示只会保存News组件里的数据，News是组件名
          :include="[News,Message]" ,指定多个用数组写法，前面得佳冒号
      -->
      <keep-alive include="News">
        <router-view></router-view>
      </keep-alive>
    </div>
  </div>
</template>

<script>
import News from "../pages/News";
import Message from "../pages/Message";
export default {
  name: "Home",
  components:{
    News,
    Message
  }
}
</script>

<style scoped>

</style>