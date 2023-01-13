<!--
    消息得发布与订阅也能实现组件之间通信
    1、安装库 cnpm i pubsub-js
    2、导入库 import pubsub from 'pubsub-js'
    2、订阅消息
        this.subid=pubsub.subscribe('event1',(eventName,data1)=>{
        console.log(eventName+JSON.stringify(data1));
      })
    3、组件销毁时取消订阅:
       pubsub.unsubscribe(this.subid);
    3、发送消息:
        pubsub.publish("event1",this.student);


-->
<template>
  <div>
    <button @click="btn1()">翼年代记</button>
  </div>
</template>
<script >
  import pubsub from 'pubsub-js'
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
        pubsub.publish("event1",this.student);
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
