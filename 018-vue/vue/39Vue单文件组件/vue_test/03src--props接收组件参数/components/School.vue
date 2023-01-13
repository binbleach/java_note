<!--
    功能：让组件接收外部传过来的数据
        （1）传递数据：
            <School name="科城" :age="100" />     #age前加冒号是动态绑定让它识别数字

        （2）接收数据：
            第一种方式（只接收）：
                props:['name']

            第二种方式（限制类型）：
                props:{
                    name:String,
                    age:Number
                }

            第三种方式（限类型、限必要条件、指定默认值）
                props:{
                    name:{
                        type:String,
                        required:true,
                        default:'本科学校'
                    }
                    age:{
                        type:Number,
                        required:false
                    }
                }
    备注：props是只读的，Vue底层会监测你对props的修改，如果进行了修改，就会发出警告，
        若业务需求确实需要修改，那么请复制props的内容道data中一份，然后去修改data中的数据。
-->
<template>
  <div>
    <h1>前途</h1>
    <div class="demo">
      <h2>{{name}}</h2>
      <h2>{{address}}</h2>
      <h2>{{myAge+1}}</h2>
      <button @click="myAge++">点我年龄加1</button>
    </div>
  </div>
</template>

<script>
  export default {
    name:"School",
    data(){
      return {
        /*myAge: this.age,*/
        myAge:this.$props.age //同时上两种都行,props优先级更高，所以才能这样获取
      }
    },
    //props:["name","age","address"]
    /*props:{
      name:String,
      age:Number,
      address:String,
    }*/
    props:{
      name:{
        type:String,
        default:"前途无量",
      },
      age:{
        type:Number,
        required:true,
      },
      address:{
        type:String,
      }
    }
  }
</script>

<style>
  .demo{
    background-color: beige;
  }
</style>