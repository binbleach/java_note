<template>
  <div>
  <ul>
    <li v-for="mesg in messageList" :key="mesg.id">
      <!--1、跳转路由并携带query参数，to字符串写法，也就是将参数加在路径后，接收是对应组件$route.query.xxx-->
      <!--<router-link :to="`/home/message/detail?id=${mesg.id}&message=${mesg.message}`">{{mesg.title}}</router-link>&nbsp;&nbsp;-->
      <!--2、跳转路由并携带query参数，to对象写法，也就是to值写成对象-->
      <!--<router-link
          :to="{
            //因为给路由命名了，可以用这个代替写很长的path路径，用name的话必须to写成对象
            name:'xiangqing',
            query:{
              id:mesg.id,
              message:mesg.message
            }
          }"
      >
        {{mesg.title}}
      </router-link>-->

      <!--3、路由跳转并携带params参数，to的字符串写法,的现在router.index文件中配置path:detail/:id/:message，冒号代表占位-->
      <router-link :to="`/home/message/detail/${mesg.id}/${mesg.message}`">{{mesg.title}}</router-link>&nbsp;&nbsp;
      <!--4、路由跳转并携带params参数 to的对象写法-->
      <!--<router-link
          :to="{
            //params to对象形式不能用path,只能用name
            name:'xiangqing',
            params:{
              id:mesg.id,
              message:mesg.message
            }
          }"
      >
        {{mesg.title}}
      </router-link>-->
    </li>
  </ul>
  <hr>
  <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "Message",
  data(){
    return{
      messageList:[
        {id:"001",title:'炎柱牺牲了',message:'在剧场版无限列车中，炎柱牺牲了，碳治郎刀丢失'},
        {id:"002",title:"坛之狼不会打",message:"在打碳治郎时，不会打，打成坛之狼"},
        {id:"003",title:"你都子变成大美女",message:"在郭游篇，妮豆子对战上弦第六的妹妹，变成大美女"}
      ]
    }
  }
}
</script>

<style scoped>

</style>