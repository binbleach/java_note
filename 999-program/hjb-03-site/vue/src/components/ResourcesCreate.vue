<template>
  <div>
    <el-form ref="form" :inline="true" :model="sizeForm" label-width="100px" size="medium ">
      <el-form-item  label="资源名称">
        <el-input  v-model="sizeForm.name"></el-input>
      </el-form-item>
      <el-form-item label="存放位置">
        <el-input  v-model="sizeForm.address"></el-input>
      </el-form-item>
      <el-form-item label="购入时间">
        <el-col :span="11">
          <el-date-picker type="date" placeholder="选择日期" v-model="sizeForm.accountDate" style="width: 100%;"></el-date-picker>
        </el-col>
      </el-form-item>
      <el-form-item  label="型号">
        <el-input  v-model="sizeForm.model"></el-input>
      </el-form-item>

      <el-form-item  label="价值（元）" placeholder="1~10">
        <el-input type="number"  v-model.number="sizeForm.value"></el-input>
      </el-form-item>
      <el-form-item  label="数量" placeholder="1~10">
        <el-input type="number"  v-model.number="sizeForm.number"></el-input>
      </el-form-item>
      <el-form-item  label="计量单位" >
        <el-input  v-model="sizeForm.unit"></el-input>
      </el-form-item>
      <el-form-item  label="使用年限（年）" >
        <el-input type="number" v-model.number="sizeForm.useYear"></el-input>
      </el-form-item>
      <el-form-item  label="剩余数量" >
        <el-input type="number" v-model.number="sizeForm.surplus"></el-input>
      </el-form-item>
      <el-form-item  label="损坏率（%）" >
        <el-input type="number" v-model.number="sizeForm.damageRate"></el-input>
      </el-form-item>
      <el-form-item label="是否禁用" >
        <el-select v-model="sizeForm.disable" placeholder="是否禁用" >
          <el-option label="否" :value="0"></el-option>
          <el-option label="是" :value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="是否被删除">
        <el-select v-model="sizeForm.isDelete" placeholder="是否被删除" >
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

      <el-form-item label="备注"  prop="desc">
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
  name: "ResourcesCreate",
  data() {
    return {
      sizeForm: {
        accountDate: "",
        address: "",
        damageRate: 0,
        isDelete: "",
        model: "",
        name: "",
        number: 0,
        photo: "",
        remark: "",
        surplus: 0,
        unit: "",
        useYear: 0,
        value: 0
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
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleSuccess(response, file, fileList){
      this.sizeForm.photo=response.data.filename;
    },
    onSubmit() {
      this.sizeForm.accountDate=this.formatDate(this.sizeForm.accountDate);
      console.log("这是表单",this.sizeForm);
      postRequestRow("/sys/resources/createResources",this.sizeForm).then(res=>{
        console.log("创建场地结果======res",res)
      })
    }
  }
}
</script>

<style scoped>

</style>
