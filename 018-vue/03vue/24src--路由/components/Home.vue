<template>
  <div>
    <h2>Home组件内容</h2>
    <div>
      <ul class="nav nav-tabs">
        <li>
          <!--replace，全写：:replace="true" ,控制路由跳转时操作浏览器历史记录的模式，true为覆盖前一个跳转-->
          <router-link replace class="list-group-item" active-class="active" to="/home/news">News</router-link>
        </li>
        <li>
          <router-link class="list-group-item" active-class="active" to="/home/message">Message</router-link>
        </li>
      </ul>
      <router-view></router-view>
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