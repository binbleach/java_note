<template>
  <div>
    <div style="display: flex;justify-content: flex-start">
      <el-input
        placeholder="通过场地名称搜索场地..."
        prefix-icon="el-icon-search"
        v-model="keywords" style="width: 400px" size="mini">
      </el-input>
      <el-button type="primary" icon="el-icon-search" size="mini" style="margin-left: 3px" @click="searchClick">搜索
      </el-button>
      <el-button type="primary" @click="isShowPlaceCreate=true">创建场地</el-button>
      <el-dialog width="800px" :visible.sync="isShowPlaceCreate" top="8vh">
        <place_create ></place_create>
      </el-dialog>
    </div>
    <!--<div style="width: 100%;height: 1px;background-color: #20a0ff;margin-top: 8px;margin-bottom: 0px"></div>-->
    <el-table
      ref="multipleTable"
      :data="placeList"
      tooltip-effect="dark"
      style="width: 100%;overflow-x: hidden; overflow-y: hidden;"
      max-height="390"
      @selection-change="handleSelectionChange" v-loading="loading">

      <el-table-column
        type="selection"
        width="35" align="left" >
      </el-table-column>
      <el-table-column
        label="场所名称"
        width="200" align="left">
        <template slot-scope="scope"><span style="color: #409eff;cursor: pointer" @click="itemClick(scope.row)">{{ scope.row.name}}</span>
        </template>
      </el-table-column>


      <el-table-column
        label="照片"
        width="200" align="left">
        <template slot-scope="scope"><img height="100%" width="50"
                                          :src="requestUrl+'/sys/downloads?filename='+scope.row.photo" style="color: #409eff;cursor: pointer" @click="itemClick(scope.row)"></img>
        </template>
      </el-table-column>

      <el-table-column
        label="备注" prop="remark" width="140" align="left">
      </el-table-column>

      <el-table-column
        label="创建时间"  width="140" align="left">
        <template slot-scope="scope">{{ scope.row.create_time | formatDateTime}}</template>
      </el-table-column>

      <el-table-column
        prop="hygiene"
        label="卫生情况"
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
        prop="capacity"
        label="容纳人数"
        width="120" align="left">
      </el-table-column>

      <el-table-column
        prop="security"
        label="安全评级"
        width="120" align="left">
      </el-table-column>

      <el-table-column  width="200"  label="操作" align="left">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="isShowPlaceReserveCreate=true" >预定
          </el-button>
          <el-dialog width="800px" :visible.sync="isShowPlaceReserveCreate" top="8vh">
            <reserve_create :row="scope.row"></reserve_create>
          </el-dialog>
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
      <el-pagination
        background
        :page-size="pageSize"
        layout="prev, pager, next"
        :total="totalCount" @current-change="currentChange" v-show="this.placeList.length>0">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import {getRequest} from "../utils/api";
import PlaceCreate from "./PlaceCreate";
import {requestUrl} from "../utils/api";
import ReserveCreate from "./ReserveCreate";

export default {
  name: "PlaceTable",
  components:{
    "place_create":PlaceCreate,
    "reserve_create":ReserveCreate
  },
  data() {
    return {
      requestUrl:requestUrl,
      isShowPlaceCreate:false,
      placeList: [],
      selItems: [],
      loading: false,
      currentPage: 1,
      totalCount: -1,
      pageSize: 6,
      keywords: '',
      dustbinData: [],
      isShowPlaceReserveCreate:false,
    }
  },
  mounted: function () {
    var _this = this;
    this.loading = true;
    _this.loadBlogs(1,10)
  },
  methods:{
    handleReserve(index,row){
      console.log("预定index===========",index)
      console.log("预定row===========",row)
    },
    searchClick(){
      this.loadBlogs(1, this.pageSize);
    },
    handleSelectionChange(val) {
      this.selItems = val;
    },
    deleteMany(){
      var selItems = this.selItems;
      for (var i = 0; i < selItems.length; i++) {
        this.dustbinData.push(selItems[i].id)
      }
      this.deleteToDustBin(selItems[0].state)
    },
    currentChange(currentPage){
      this.currentPage = currentPage;
      this.loading = true;
      this.loadBlogs(currentPage, this.pageSize);
    },
    loadBlogs(current, size){
      console.log("干啥子=================")
      var _this = this;
      getRequest(`/sys/place/getPlacePage/${current}/${size}`).then(
        res =>{
          const {data} = res
          console.log("场地信息============",data);
          if(data.code==200){
            _this.loading = false;
            console.log("场地信息============",data);
            this.placeList=data.data.records;
          }
        }
      ).catch(
        res =>{
          _this.loading = false;
          console.log(res)
        }
      );
    },
  }
}
</script>

<style scoped>

</style>
