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
import axios from "axios";
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
      axios.get(`http://api.github.com/search/users?q=${this.keyWord}`).then(
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