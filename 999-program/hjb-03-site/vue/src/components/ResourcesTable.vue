<template>
  <div>
<!--  <div style="margin-top: 10px;display: flex;justify-content: center">-->
<!--    <el-input-->
<!--      placeholder="根据资源名称搜索资源..."-->
<!--      prefix-icon="el-icon-search"-->
<!--      v-model="keywords" style="width: 400px" size="small">-->
<!--    </el-input>-->
<!--    <el-button type="primary" icon="el-icon-search" size="small" style="margin-left: 3px" @click="searchClick">搜索-->
<!--    </el-button>-->
<!--  </div>-->
    <el-button type="primary" icon="el-icon-search" size="mini" style="margin-left: 3px" @click="searchClick">搜索
    </el-button>
    <el-button type="primary" @click="isShowResourcesCreate=true">创建资源</el-button>
    <el-dialog width="800px" :visible.sync="isShowResourcesCreate" top="8vh">
      <resources_create></resources_create>
    </el-dialog>

    <el-table
      ref="multipleTable"
      :data="resourcesList"
      tooltip-effect="dark"
      style="width: 100%;overflow-x: hidden; overflow-y: hidden;"
      max-height="390"
      v-loading="loading">

      <el-table-column
        type="selection"
        width="35" align="left" >
      </el-table-column>

      <el-table-column
        label="资源名称"
        width="100" align="left">
        <template slot-scope="scope"><span style="color: #409eff;cursor: pointer" @click="itemClick(scope.row)">{{ scope.row.name}}</span>
        </template>
      </el-table-column>


      <el-table-column
        label="照片"
        width="100" align="left">
        <template slot-scope="scope"><img height="100%" width="50"
                                          :src="requestUrl+'/sys/downloads?filename='+scope.row.photo" style="color: #409eff;cursor: pointer" @click="itemClick(scope.row)"></img>
        </template>
      </el-table-column>

      <el-table-column
        label="备注" prop="remark" width="140" align="left">
      </el-table-column>

      <el-table-column
        label="购入时间"  width="140" align="left">
        <template slot-scope="scope">{{ scope.row.account_date | formatDateTime}}</template>
      </el-table-column>

      <el-table-column
        prop="model"
        label="型号"
        width="120" align="left">
      </el-table-column>

      <el-table-column
        label="是否被删除"
        width="120" align="left">
        <template slot-scope="scope">{{ scope.row.del_flag?"是":"否"}}</template>
      </el-table-column>

      <el-table-column
        prop="address"
        label="地址"
        width="120" align="left">
      </el-table-column>

      <el-table-column
        label="状态"
        width="120" align="left">
        <template slot-scope="scope">{{ scope.row.disable?"禁用":"活跃"}}</template>
      </el-table-column>

      <el-table-column
        prop="value"
        label="价值"
        width="120" align="left">
      </el-table-column>

      <el-table-column
        prop="number"
        label="数量"
        width="120" align="left">
      </el-table-column>
      <el-table-column
        prop="unit"
        label="计量单位"
        width="120" align="left">
      </el-table-column>
      <el-table-column
        prop="use_year"
        label="使用年限（年）"
        width="120" align="left">
      </el-table-column>
      <el-table-column
        prop="surplus"
        label="剩余数量"
        width="120" align="left">
      </el-table-column>
      <el-table-column
        prop="damage_rate"
        label="损坏率（%）"
        width="120" align="left">
      </el-table-column>

      <el-table-column  width="200"  label="操作" align="left">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)" >编辑
          </el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="blog_table_footer">
      <!--      <el-button type="danger" size="mini" style="margin: 0px;" v-show="this.articles.length>0"
                       :disabled="this.selItems.length==0" @click="deleteMany">批量删除
            </el-button>-->
      <span></span>
<!--      <el-pagination-->
<!--        background-->
<!--        :page-size="pageSize"-->
<!--        layout="prev, pager, next"-->
<!--        :total="totalCount" @current-change="currentChange" v-show="this.resourcesList.length>0">-->
<!--      </el-pagination>-->
    </div>

  </div>
</template>
<script>
  import {getRequest} from '../utils/api'
  import ResourcesCreate from "./ResourcesCreate";
  import {requestUrl} from "../utils/api";

  export default{
    components:{
      resources_create:ResourcesCreate
    },
    mounted: function () {
      //this.loading = true;
      this.loadResourcesList(1,10)
    },
    methods: {
      loadResourcesList(current,size){
        console.log("加载resources=================")
        const _this = this;
        getRequest(`/sys/resources/getResourcesPage/${current}/${size}`).then(
          res =>{
            const {data} = res
            console.log("资源信息============",data);
            if(data.code==200){
              _this.loading = false;
              console.log("资源信息============",data);
              this.resourcesList=data.data.records;
            }
          }
        ).catch(
          res =>{
            _this.loading = false;
            console.log(res)
          }
        );
      },

      searchClick(){

      }
    },
    data(){
      return {
        isShowResourcesCreate:false,
        requestUrl:requestUrl,
        loading: false,
        eploading: [],
        resourcesList: [],
        keywords: '',
        users: [],
        allRoles: [],
        roles: [],
        cpRoles: []
      }
    }
  }
</script>
