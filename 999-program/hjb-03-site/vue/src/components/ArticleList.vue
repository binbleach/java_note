<template>
  <el-container class="article_list">
    <el-main class="main">
      <el-tabs v-model="activeName" @tab-click="handleClick" type="card">
<!--        <el-tab-pane label="全部场地" name="all">-->
<!--          <blog_table :showEdit="false" :showDelete="true" :activeName="activeName"></blog_table>-->
<!--        </el-tab-pane>-->
        <place_table></place_table>
      </el-tabs>
    </el-main>
  </el-container>
</template>
<script>
  import BlogTable from '@/components/BlogTable'
  import PlaceTable from '@/components/PlaceTable'
  import {getRequest} from '../utils/api'
  export default {
    mounted: function () {
      // var _this = this;
      // getRequest("/isAdmin").then(resp=> {
      //   if (resp.status == 200) {
      //     _this.isAdmin = resp.data;
      //   }
      // })
    },
    data() {
      return {
        activeName: 'post',
        isAdmin: false
      };
    },
    methods: {
      handleClick(tab, event) {
//        console.log(tab, event);
      }
    },
    components: {
      'blog_table': BlogTable,
      'place_table':PlaceTable
    }
  };
</script>
<style>
  .article_list > .header {
    background-color: #ececec;
    margin-top: 10px;
    padding-left: 5px;
    display: flex;
    justify-content: flex-start;
  }

  .article_list > .main {
    /*justify-content: flex-start;*/
    display: flex;
    flex-direction: column;
    padding-left: 0px;
    background-color: #fff;
    padding-top: 0px;
    margin-top: 8px;
  }
</style>
