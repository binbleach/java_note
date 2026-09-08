<template>
  <div id="root">

    <button @click="isShow1=!isShow1">点我显示</button>
    <h1 v-show="isShow1" class="come c1">你好呀</h1>

    <!--
        vue的动画效果标签：
        name属性：可以区分transition标签，没有则名为v。
        appear 或 :appear="true"：则一开始就执行动画
        注：
            1、transition（单个标签效果）
            2、transition-group（多个标签，每个标签必须加key值区分，否则不渲染）
            3、更多样式可以引入第三方库 如：animate
              使用：1）安装库
                   2）导入import "xxx.css"
                   3）在transition标签里加三个属性：
                      name=""
                      enter-active-class=""
                      leave-active-class=""
    -->
    <button @click="isShow2=!isShow2">点我显示</button>
    <transition name="tran1" appear>
      <h1 v-show="isShow2" class="c2">你好呀</h1>
    </transition>

    <button @click="isShow3=!isShow3">点我显示</button>
    <transition name="tran2" appear>
      <h1 v-show="isShow3" class="c3">你好呀</h1>
    </transition>

  </div>
</template>

<script>
export default {
  name: "App",
  data(){
    return{
      isShow1:true,
      isShow2:true,
      isShow3:true,
    }
  },
  methods:{
    btn1(){

    }
  }
}
</script>

<style scoped>
  /*
    原
  */
  .c1{
    background-color: orange;
  }
  .come{
    animation:huang 1s;
  }
  .go{
    animation: huang 1s reverse;
  }
  @keyframes huang {
    from{
      transform:translateX(-100%);
    }
    to{
      transform:translateX(0px);
    }
  }
  /*
    动画
  */
  .c2{
    background-color: #68a3cb;
  }

  .tran1-enter-active{
    animation: jia 1s;
  }
  .tran1-leave-active{
    animation: jia 1s reverse;
  }
  @keyframes jia {
    from{
      transform:translateX(-100%);
    }
    to{
      transform:translateX(0px);
    }
  }
  /*
    过度:
      vue提供六个场景：
      1、进入起点：.tran2-enter
      2、进入终点：.tran2-enter-to
      3、进入整个：.tran2-enter-active
      4、离开起点：.tran2-leave
      5、离开终点：.tran2-leave-to
      6、离开整个：.tran2-leave-active.
      注：1和5、2和4、3和6，相互对应

  */
  .c3{
    background-color: #86cb68;
  }

  .tran2-enter-active,.tran2-leave-active{
    transition: 1s linear
  }
  .tran2-enter,.tran2-leave-to{
    transform: translateX(-100%);
  }
  .tran2-leave,.tran2-enter-to{
    transform: translateX(0);
  }


</style>