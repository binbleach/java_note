<!--概念-->
<!--
    普通插槽：
    1、功能，在父级可以定义插入子级的标签
    2、默认插槽：没有名字，对应没有名字的。
      具名插槽：有名字的相互对应。
    3、用法：
      1）插入内容写在要传入目标组件的双目标签里，如：<Category>插入内容</Category>
      2）插入位置：在目标组件的<slot/>标签处
    作用域插槽：
      场景：数据在子组件这，但是由父组件决定结构样式。可以把数据传到父组件，再让父组件把结构样式传过来
      使用：
          1）子：<slot :girl1="girl2"></slot>
          2）父：<template scope="ta">
                    <ul><li v-for="(t,index) in ta.girl1" :key="index">{{t}}</li></ul>
                </template>
          解释：girl1为传过去内容的名字。girl2为传过去内容。ta为接收内容的名字。ta.girl1取得传过来的内容。
              如果传多个内容可以ta.xxx出来。es6技巧结构赋值，直接scope="{games}"，直接用games。
      注：作用域插槽必须用template,也可以写名字
-->

<template>
  <div class="app">
    <!--美食插槽-->
    <Category title="美食">
      <img slot="center" src="https://tse1-mm.cn.bing.net/th/id/R-C.3c90c00330b36c5dbffcb5ccdd336ba9?rik=fVOZBkk%2fywhcfw&riu=http%3a%2f%2fpic141.huitu.com%2fres%2f20200423%2f1124748_20200423222715480070_1.jpg&ehk=CJOjP9ykMbuiRyZX8mFJvr0F%2bTY5mC9z5J52oADzeOQ%3d&risl=&pid=ImgRaw&r=0" alt="海南炒粉">
      <span slot="footer">海南炒粉，天下第一</span>
    </Category>

    <!--游戏插槽-->
    <Category title="游戏">
      <ul slot="center">
        <li v-for="(item,index) in games" :key="index">{{item}}</li>
      </ul>
    </Category>

    <!--好剧插槽-->
    <Category title="好剧">
      <iframe slot="center"  style="height: 400px;width: 100%"
              src="http://player.bilibili.com/player.html?aid=935872076&bvid=BV1mT4y1C7no&cid=487273044&page=1&high_quality=1"
              scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" >
      </iframe>
      <!--
        新写法 v-slot:footer，只能在template标签处使用
        下面template作用：包裹div和p都传到footer组件，但是又不影响结构
      -->
      <template v-slot:footer>
        <div>
          <p>死神</p>
          <p>四谎</p>
          <p>小埋</p>
        </div>
        <p>无语</p>
      </template>
    </Category>

    <!--作用域插槽-->
    <Category>
<!--      slot-scope = scope-->
      <template slot-scope="ta">
        <ul>
          <li v-for="(t,index) in ta.girl" :key="index">{{t}}</li>
        </ul>
      </template>
    </Category>

  </div>
</template>

<script>
import Category from "./components/Category";
export default {
  name: "App",
  components: {
    Category
  },
  data(){
    return{
      foods:['火锅','烧烤','炒粉'],
      games:['王者荣耀','中国象棋','我的世界'],
      films:['《鬼灭之刃》','《开端》','《雪中悍刀行》']
    }
  },
}
</script>

<style scoped>
.app{
  display: flex;
  justify-content: space-around;
}
span{
  font-size: 20px;
  color: #da4f49;
  text-align: center;
}
p{
  float: left;
  margin-right: 10px;
}
</style>