<!--自定义组件事件传递student对象给app：子给父传东西
    和第一种区别，都要父app处写回调函数，第一种父app传了方法子school用props接收了
    第二种父app给子绑定了事件，子通过$emit(“事件方法”,参数)自己触发事件，触发事件同时可传参数
    关于自定义事件：在生命周期到destroy后会所有自定义事件都会被解除
-->
<template>
  <div class="student">
    <h2>{{student.name}}</h2>
    <h2>{{student.age}}</h2>
    <button @click="btn1">点我触发事件</button>
    <button @click="btn2">点我解除接收学生信息事件</button>
    <button @click="btn3">点我解除所有事件</button>
    <button @click="btn4">点我生命周期到销毁</button>
    <button @click="btn5">点我触发ref绑定的事件</button>
  </div>
</template>

<script >
  export default {
    name:"Student",
    data(){
      return {
        student:{
          name: "黄家宾",
          age: "18"
        }
      }
    },
    methods:{
      btn1(){
        //触发绑定事件
        this.$emit("event1",this.student);
        this.$emit("event2")
      },
      //解除绑定事件
      btn2(){
        this.$off("event1");
      },
      btn3(){
        //解绑多个参数是数组形式
        this.$off(["event1","event2"]);
        //不带参数则解绑所有事件
        this.$off();
      },
      btn4(){
        //可查看生命周期图，此方法vm进入销毁，进入销毁后随之所有自定义事件解绑
        this.$destroy();//
      },
      btn5(){
        this.$emit("event3",this.student);
      }
    }
  }
</script>
<style>
.student{
  background-color: #da4f49;
  padding: 5px;
  margin-top: 30px;
}
</style>
