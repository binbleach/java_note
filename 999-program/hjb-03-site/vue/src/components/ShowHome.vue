<template>
  <div>
    <Home></Home>
    <HomeBack></HomeBack>
  </div>
</template>

<script>
import Home from "./Home";
import HomeBack from "./HomeBack";
import {getRequest} from "../utils/api";
export default {
  name: "ShowHome",
  components: {HomeBack, Home},
  mounted: function () {
    // this.$alert('为了确保所有的小伙伴都能看到完整的数据演示，数据库只开放了查询权限和部分字段的更新权限，其他权限都不具备，完整权限的演示需要大家在自己本地部署后，换一个正常的数据库用户后即可查看，这点请大家悉知!', '友情提示', {
    //   confirmButtonText: '确定',
    //   callback: action => {
    //   }
    // });
    var _this = this;
    getRequest("/sys/getLoginInfo").then(function (res) {
      const {data} = res.data
      console.log("用户信息============",data)
      _this.currentUserName = data.user.nickname
      localStorage.setItem("userInfo",JSON.stringify(data.user))
    });
  },
}

</script>

<style scoped>

</style>
