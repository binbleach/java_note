<template>
  <div>

    <el-form ref="form" :model="reserveForm" label-width="100px" size="medium ">

      <el-form-item label="开始时间">
        <el-col :span="7">
          <el-date-picker type="date" placeholder="选择日期" v-model="reserveForm.startTime" style="width: 100%;"></el-date-picker>
        </el-col>
        <el-col :span="7">
          <el-time-picker placeholder="选择时间" v-model="reserveForm.startTime" style="width: 100%;"></el-time-picker>
        </el-col>
      </el-form-item>
      <el-form-item label="结束时间">
        <el-col :span="7">
          <el-date-picker type="date" placeholder="选择日期" v-model="reserveForm.endTime" style="width: 100%;"></el-date-picker>
        </el-col>
        <el-col :span="7">
          <el-time-picker placeholder="选择时间" v-model="reserveForm.endTime" style="width: 100%;"></el-time-picker>
        </el-col>
      </el-form-item>

      <el-form-item label="包含活动">
        <el-switch v-model="isContainActivity"
                   :active-value="true"
                   :inactive-value="false"></el-switch>
      </el-form-item>

      <el-form-item v-if="isContainActivity"  label="活动信息标题">
        <el-col :span="11">
        <el-input  v-model="reserveForm.title"></el-input>
        </el-col>
      </el-form-item>

      <el-form-item  v-if="isContainActivity" label="活动信息内容">
        <el-col :span="20">
        <el-input type="textarea"  v-model="reserveForm.content"></el-input>
        </el-col>
      </el-form-item>

      <el-form-item  v-if="isContainActivity" label="活动人数">
        <el-col :span="5">
        <el-input type="number"  v-model.number="reserveForm.peopleNumber"></el-input>
        </el-col>
      </el-form-item>

      <el-form-item  v-if="isContainActivity" label="是否公开">
        <el-switch v-model="reserveForm.isPublish"
                   :active-value="1"
                   :inactive-value="0"></el-switch>
      </el-form-item>

      <el-form-item  v-if="isContainActivity" v-show="reserveForm.isPublish==1"  label="参加码">
        <el-col :span="5">
        <el-input  v-model.number="reserveForm.code"></el-input>
        </el-col>
      </el-form-item>


      <el-form-item label="申请理由" >
        <el-input  type="textarea" v-model="reserveForm.reserveReason"></el-input>
      </el-form-item>

      <el-form-item  size="large">
        <el-button type="primary" @click="onSubmit">立即预定</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {postRequestRow} from "../utils/api";
import moment from 'moment';
moment.locale('zh-cn');
export default {
  name: "ReserveCreate",
  props:["row"],
  data(){
    return{
      reserveForm:{
      startTime: "",
      endTime: "",
      reserveTarget: 45,
      reserveReason: "",
      userId:0,
      targetId:0,
      title:"",
      content:"",
      peopleNumber:2,
      isPublish:1,
      code:0,
      },
      isContainActivity:false,
    }
  },
  mounted() {
    console.log("row====",this.$props.row)
  },
  methods:{
    onSubmit(){
      const _this=this
      _this.reserveForm.targetId=_this.$props.row.id
      console.log(localStorage.getItem("userInfo"));
      _this.reserveForm.userId = JSON.parse(localStorage.getItem("userInfo")).id
      _this.reserveForm.startTime=moment(_this.reserveForm.startTime).format("yyyy-MM-DD HH:mm:ss")
      _this.reserveForm.endTime=moment(_this.reserveForm.endTime).format("yyyy-MM-DD HH:mm:ss")
      if(_this.isContainActivity){
        postRequestRow("/sys/reserve/placeAndActivityReserve",_this.reserveForm).then(
          success=>{
            console.log("成功===============")
            console.log(success)
          }
        )
      }else {
        postRequestRow("/sys/reserve/createReserve",this.reserveForm).then(res=>{
          console.log("预定结果======res",res)
        })
      }
    }
  }
}
</script>

<style scoped>

</style>
