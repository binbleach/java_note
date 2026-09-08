<!--
  关于用ref绑定的事件里的回调函数里this:
    1)是触发事件的组件，也就是student组件，
    2)若是this.当前组件存在的方法,则this是当前组件：如this.method4()这里的this就是app
    3)若回调函数是箭头函数，则this就是当前组件的this，也就是app
-->
<template>
  <div class="app">
    <h2>APP</h2>
    <School :receive="method1"/>

    <!--事件名：event1  事件触发方法：method2 注不能加括号-->
    <!--也可以给子组件绑定原生事件，如点击事件不过得加 .native ，不然vue会认位他是自定义事件-->
    <Student @event1.once="method2" @event2="method3" @click.native="show()"/>

    <Student ref="student"/>
    学生信息：{{studentName}}

    <h2 v-text="msg"></h2>
  </div>
</template>

<script>
import School from "./components/School";
import Student from "./components/Student";
export default {
  name: "App",
  components: {
    Student,
    School,
  },
  data(){
    return{
      msg:"你好呀傻逼09src",
      studentName:"",
    }
  },
  methods: {
    method1(schoolObj){
      console.log(JSON.stringify(schoolObj));
    },
    method2(studentObj){
      console.log(JSON.stringify(studentObj));
    },
    method3(){
      console.log("test事件触发")
    },
    method4(studentObj){
      console.log(JSON.stringify(studentObj))
      this.studentName=studentObj.name;
    },
    show(){
      alert("show");
    }
  },
  mounted(){
    //this.$refs.student.$once("event3",this.method4)
    /*关于用ref绑定的事件里的回调函数里this:
    1)是触发事件的组件，也就是student组件，
    2)若是this.当前组件存在的方法,则this是当前组件：如this.method4()这里的this就是app
    3)若回调函数是箭头函数，则this就是当前组件的this，也就是app*/
    this.$refs.student.$on("event3", (studentObj) => {
      this.studentName=studentObj.name;
    })
  }
}
</script>

<style scoped>
.app{
  background-color: #cccccc;
}
</style>