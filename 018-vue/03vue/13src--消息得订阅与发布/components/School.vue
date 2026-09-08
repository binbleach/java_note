<template>
  <div>
    <div class="demo">
    </div>
  </div>
</template>

<script>
import pubsub from 'pubsub-js'
  export default {
    name:"School",
    data(){
      return {
        school:{
          name:"电子科技大学成都学院",
          address:"百叶路一号",
          age:100
        }
      }
    },
    methods:{
    },
    mounted() {
      this.subid=pubsub.subscribe('event1',(eventName,data1)=>{
        console.log(eventName+JSON.stringify(data1));
      })
    },
    destroyed() {
      pubsub.unsubscribe(this.subid);
    }
  }
</script>

<style>
  .demo{
    background-color: beige;
  }
</style>