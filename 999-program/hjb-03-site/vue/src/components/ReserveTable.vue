<template>
  <div>
<!--    <el-button type="primary" icon="el-icon-search" size="mini" style="margin-left: 3px" @click="searchClick">搜索-->
<!--    </el-button>-->
    <el-button type="primary" @click="isShowReserveCreate=true">申请预约</el-button>
    <el-dialog width="800px" :visible.sync="isShowReserveCreate" top="8vh">
      <eserve_create></eserve_create>
    </el-dialog>

    <el-table
      ref="multipleTable"
      :data="reserveList"
      tooltip-effect="dark"
      style="width: 100%;overflow-x: hidden; overflow-y: hidden;"
      max-height="390"
      v-loading="loading">

      <el-table-column
        type="selection"
        width="35" align="left" >
      </el-table-column>

      <el-table-column
        label="id"
        width="100" align="left">
        <template slot-scope="scope"><span style="color: #409eff;cursor: pointer" @click="itemClick(scope.row)">{{ scope.row.id}}</span>
        </template>
      </el-table-column>


      <el-table-column
        label="目标id" prop="target_id" width="140" align="left">
      </el-table-column>

      <el-table-column
        prop="user_id"
        label="预定人id"
        width="120" align="left">
      </el-table-column>

      <el-table-column
        label="创建时间"  width="140" align="left">
        <template slot-scope="scope">{{ scope.row.create_time | formatDateTime}}</template>
      </el-table-column>

      <el-table-column
        label="开始时间"  width="140" align="left">
        <template slot-scope="scope">{{ scope.row.start_time | formatDateTime}}</template>
      </el-table-column>

      <el-table-column
        label="结束时间"  width="140" align="left">
        <template slot-scope="scope">{{ scope.row.end_time | formatDateTime}}</template>
      </el-table-column>


      <el-table-column
        label="是否被删除"
        width="120" align="left">
        <template slot-scope="scope">{{ scope.row.del_flag?"是":"否"}}</template>
      </el-table-column>

      <el-table-column
        label="预定目标"
        width="120" align="left">
        <template slot-scope="scope">{{ scope.row.reserve_target==49?"场地":"资源"}}</template>
      </el-table-column>

      <el-table-column
        label="预定状态"
        width="120" align="left">
        <template slot-scope="scope">{{ scope.row.reserve_status}}</template>
      </el-table-column>

      <el-table-column  width="200"  label="操作" align="left">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleReserve(scope.$index, scope.row)" >预定
          </el-button>
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

  </div>
</template>

<script>
import {getRequest, requestUrl} from "../utils/api";
import ReserveCreate from "./ReserveCreate";

export default {
  name: "ReserveTable",
  components:{
    reserve_create:ReserveCreate
  },
  data(){
    return {
      isShowReserveCreate:false,
      requestUrl:requestUrl,
      reserveList:[],
      loading:false
    }
  },
  mounted() {
    this.loadReserveList(1,10);
  },
  methods:{
    loadReserveList(current,size){
      console.log("加载reserve=================")
      const _this = this;
      getRequest(`/sys/reserve/selectAllReserveForPage/${current}/${size}`).then(
        res =>{
          const {data} = res
          console.log("预定信息============",data);
          if(data.code==200){
            _this.loading = false;
            console.log("预定信息============",data);
            this.reserveList=data.data.records;
          }
        }
      ).catch(
        res =>{
          _this.loading = false;
          console.log(res)
        }
      );
    },

    handleReserve(index,row){
      console.log("预定index===========",index)
      console.log("预定row===========",row)
    }
  }
}
</script>

<style scoped>

</style>
