<template>
<div>
  <el-form ref="form" :inline="true" :model="sizeForm" label-width="100px" size="medium ">
    <el-form-item  label="场地名称">
      <el-input  v-model="sizeForm.name"></el-input>
    </el-form-item>
    <el-form-item label="场地位置">
      <el-input  v-model="sizeForm.address"></el-input>
    </el-form-item>
    <el-form-item label="建立时间">
      <el-col :span="11">
        <el-date-picker type="date" placeholder="选择日期" v-model="sizeForm.createTime" style="width: 100%;"></el-date-picker>
      </el-col>
    </el-form-item>
    <el-form-item  label="场地容纳人数">
      <el-input type="number"  v-model.number="sizeForm.capacity"></el-input>
    </el-form-item>
    <el-form-item label="卫生情况">
      <el-select v-model="sizeForm.hygiene" placeholder="请选择卫生情况">
        <el-option label="优" :value="25"></el-option>
        <el-option label="良" :value="26"></el-option>
        <el-option label="差" :value="27"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item  label="安全等级" placeholder="1~10">
      <el-input type="number"  v-model.number="sizeForm.security"></el-input>
    </el-form-item>
    <el-form-item label="是否禁用" >
      <el-select v-model="sizeForm.disable" placeholder="是否禁用" >
      <el-option label="否" :value="0"></el-option>
      <el-option label="是" :value="1"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="是否被删除">
      <el-select v-model="sizeForm.delFlag" placeholder="是否被删除" >
        <el-option label="否" :value="0"></el-option>
        <el-option label="是" :value="1"></el-option>
      </el-select>
    </el-form-item>
    <el-upload
      action="/sys/upload"
      list-type="picture-card"
      :on-preview="handlePictureCardPreview"
      :on-remove="handleRemove"
      :on-success="handleSuccess"
    >
      <i class="el-icon-plus"></i>
    </el-upload>
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>

    <el-form-item label="备注"  prop="remark">
      <el-input  type="textarea" v-model="sizeForm.remark"></el-input>
    </el-form-item>
    <el-form-item  size="large">
      <el-button type="primary" @click="onSubmit">立即创建</el-button>
      <el-button>取消</el-button>
    </el-form-item>
  </el-form>
</div>
</template>

<script>
import {postRequestRow} from "../utils/api";

export default {
  name: "PlaceCreate",
  data() {
    return {
      sizeForm: {
        name: '',
        address: "",
        capacity: "",
        createTime: "",
        delFlag: "",
        disable: "",
        hygiene: "",
        remark: "",
        security: "",
        photo:"",
      },
      // dialogImageUrl: '',
      // dialogVisible: false,
      // disabled: false
      dialogImageUrl: '',
      dialogVisible: false
    };
  },
  methods: {
     formatDate(date) {
        console.log(date);
        // date = new Date();
        date = new Date(Date.parse(date.toString().replace(/-/g, "/"))); //转换成Data();
        console.log(date);
        var y = date.getFullYear();
        console.log(y);
        var m = date.getMonth() + 1;
        m = m < 10 ? '0' + m : m;
        var d = date.getDate();
        d = d < 10 ? ('0' + d) : d;
        return y + '-' + m + '-' + d;
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePictureCardPreview(file) {
      console.log("aaaaaaaaaaa")
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleSuccess(response, file, fileList){
      this.sizeForm.photo=response.data.filename;
    },
    onSubmit() {
      this.sizeForm.createTime=this.formatDate(this.sizeForm.createTime);
      console.log("这是表单",this.sizeForm);
      postRequestRow("/sys/place/createPlace",this.sizeForm).then(res=>{
        console.log("创建场地结果======res",res)
      })
    }
  }
}
</script>

<style scoped>

</style>
