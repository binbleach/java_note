<template>
  <div>
    <button @click="btn1()">翼年代记</button>
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
        this.$bus.$emit("event1",this.student);
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
