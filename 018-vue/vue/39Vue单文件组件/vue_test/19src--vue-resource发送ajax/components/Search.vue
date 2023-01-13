<template>
  <section class="jumbotron">
    <h3 class="jumbotron-heading">Search Github Users</h3>
    <div>
      <input type="text" placeholder="输入名字搜索" v-model="keyWord"/>&nbsp;
      <button @click="searchUser">Search</button>
    </div>
  </section>
</template>

<script>
export default {
  name: "Search",
  data(){
    return{
      keyWord:"",
    }
  },
  methods:{
    searchUser(){
      //请求前更新List组件信息
      this.$bus.$emit("updateListData",{isFirst:false,isLoading:true,errMsg:'',users:[]});
      //resource请求其实跟axios一毛一样，也是promise风格的。还是vue插件，现在已经年仅失修了，原来是vue团队维护，现在交给别的团队维护了
      this.$http.get(`http://api.github.com/search/users?q=${this.keyWord}`).then(
          response => {
            console.log("请求成功",response.data);
            this.$bus.$emit("updateListData",{isLoading:false,errMsg:'',users:response.data.items});
          },
          error => {
            console.log("请求失败",error.message);
            this.$bus.$emit("updateListData",{isLoading:false,errMsg:error.message,users:[]});
          }
      )
    }
  }
}
</script>

<style scoped>

</style>