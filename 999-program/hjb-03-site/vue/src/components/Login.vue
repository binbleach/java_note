<template>
  <div   style="width:100%; height: 100%" >
    <el-form v-if="!isShowRegister"  :model="loginForm" :rules="rules" class="login-container" label-position="left"
             label-width="0px" v-loading="loading">
      <h3 class="login_title">系统登录</h3>
      <el-form-item prop="username">
        <el-input type="text" v-model="loginForm.username"  placeholder="账号"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input type="password" v-model="loginForm.password" placeholder="密码"></el-input>
      </el-form-item>
  <!--    <el-checkbox class="login_remember" v-model="checked" label-position="left">记住密码</el-checkbox>-->
      <el-form-item style="width: 100%">
      <a @click.stop.prevent="isShowRegister=true"style="color:dodgerblue;cursor:pointer; margin-bottom: 5px">没有账号注册</a>
      </el-form-item>
      <el-form-item style="width: 100%">
        <el-button type="primary" @click.native.prevent="submitClick" style="width: 100%">登录</el-button>
      </el-form-item>
    </el-form>

    <el-form v-if="isShowRegister" :model="registerForm" :rules="rules" class="login-container" label-position="left"
             label-width="0px" v-loading="loading">
      <h3 class="login_title">系统注册</h3>
      <el-form-item prop="username">
        <el-input type="text" v-model.trim="registerForm.username" placeholder="登录账号"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input type="password" v-model.trim="registerForm.password" auto-complete="off" placeholder="登录密码"></el-input>
      </el-form-item>
      <el-form-item prop="nickname">
        <el-input type="text" v-model="registerForm.nickname" placeholder="昵称"></el-input>
      </el-form-item>
      <el-form-item >
        <el-input type="text" v-model.trim="registerForm.phone" auto-complete="off" placeholder="电话"></el-input>
      </el-form-item>
      <el-form-item >
        <el-input type="text" v-model="registerForm.address" auto-complete="off" placeholder="家庭地址"></el-input>
      </el-form-item>
      <el-form-item label-width="50px" label="性别">
        <el-col :span="9">
        <el-radio-group  v-model="registerForm.gender">
          <el-radio :label="16">男</el-radio>
          <el-radio :label="17">女</el-radio>
        </el-radio-group>
        </el-col>
      </el-form-item>
      <el-form-item label-width="50px" label="身份">
        <el-col :span="11">
          <el-radio-group v-model="registerForm.title">
            <el-radio :label="28">学生</el-radio>
            <el-radio :label="29">教师</el-radio>
  <!--            <el-radio :label="30">外来人员</el-radio>-->
          </el-radio-group>
        </el-col>
      </el-form-item>
      <el-form-item prop="email" >
        <el-input type="text" v-model.trim="registerForm.email" placeholder="邮箱"></el-input>
      </el-form-item>

      <el-form-item >
        <el-col :span="7">
        <el-input type="text" v-model.trim="registerForm.code" auto-complete="off" placeholder="验证码"></el-input>
        </el-col>
        <el-col :span="7">
          <el-button type="primary"  @click="codeClick" style="width: 100%">获取验证码</el-button>
        </el-col>
      </el-form-item>

      <el-form-item style="width: 100%">
      <a @click.stop.prevent="isShowRegister=false " style="color:dodgerblue;cursor:pointer;height: 20px">已有账号登录</a>
      </el-form-item>
      <el-form-item style="width: 100%">
        <el-button type="primary" v-if="isShowRegister" @click.native.prevent="registerClick" style="width: 100%">注册</el-button>
      </el-form-item>
    </el-form>

  </div>
</template>
<script>
import {postRequest, postRequestRow} from '../utils/api'
  import store from "../store";
  import {putRequest} from '../utils/api'
import {removeToken, setToken} from "../utils/utils";
  export default{
    name:"Login",
    data(){
      return {
        isShowRegister:false,
        checked: true,
        loginForm: {
          username: 'admin',
          password: '1'
        },
        registerForm:{
          username:"",
          password:"",
          title:28,
          address:"",
          phone:"",
          gender:16,
          email:"",
          photo:"",
          code:"",
          nickname:""
        },
        rules:{
          username: [{required: true, message: '用户名不能为空', trigger: 'blur'}],
          password: [{required: true, message: '密码不能为空', trigger: 'blur'}],
          nickname: [{required: true, message: '昵称不能为空', trigger: 'blur'}],
          phone: [{required: true, message: '电话不能为空', trigger: 'blur'}],
          address: [{required: true, message: '地址不能为空', trigger: 'blur'}],
          email:[
            {required: true, message: '邮箱不能为空', trigger: 'blur' },
            {
              pattern: /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/,    //正则校验不用字符串
              message: "邮箱格式错误", trigger: "blur"
            }
          ]
        },
        loading: false
      }
    },
    methods: {
      submitClick: function () {
        var _this = this;
        this.loading = true;
        const username=this.loginForm.username
        const password=this.loginForm.password
        const loginForm = {username,password}
        this.$store.dispatch('login',loginForm).then(data=> {
          _this.loading = false;
          console.log("登录结果=========",data)
          console.log("token========",this.$store.state.token)

          _this.$router.replace({path: '/home'});
        }, resp=> {
          _this.loading = false;
          _this.$alert('登录失败');
        })
      },

      registerClick(){
        var _this = this;
        console.log("register==========",_this.registerForm)
        postRequestRow("/sys/user/createUser",_this.registerForm).then(
          res=>{
            if(res.data.code==200){
              _this.$message.success("账号创建成功，正在登录系统")
              _this.$store.commit("REMOVE_TOKEN")
              _this.loading = true;
              this.$store.dispatch('login',_this.registerForm).then(data=> {
                _this.loading = false;
                console.log("登录结果=========",data)
                console.log("token========",this.$store.state.token)
                _this.$router.replace({path: '/home'});
              })

            }else {
              this.$message.error(res.data.message);
            }
            console.log(res)

          }
        )
      },
      codeClick(){
          if(this.registerForm.email!=''){
            console.log("成功",this.registerForm.email)
            removeToken();
            this.$store.commit("REMOVE_TOKEN")
            postRequest("/sys/sendEmail/"+this.registerForm.email).then(
              res=>{
                const {token} = res.data.data
                this.$store.commit("SET_TOKEN",token)
              }
            )
          }else {
            this.$message.error('请先输入邮箱');
          }
      }
    }

  }
</script>
<style>
  .login-container {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }

  .login_title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }

  .login_remember {
    margin: 0px 0px 35px 0px;
    text-align: left;
  }
</style>
