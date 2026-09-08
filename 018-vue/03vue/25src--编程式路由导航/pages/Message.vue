<template>
  <div>
  <ul>
    <li v-for="mesg in messageList" :key="mesg.id">
      <router-link :to="`/home/message/detail/${mesg.id}/${mesg.message}`">{{mesg.title}}</router-link>&nbsp;&nbsp;
      <button @click="pushShow(mesg)">push查看</button>
      <button @click="replaceShow(mesg)">replace查看</button>
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
  },
  methods:{
    /*这种写法比上面更灵活*/
    pushShow(m){
      this.$router.push({
        name:'xiangqing',
        query:{
          id:m.id,
          message:m.message
        }
      })
    },
    replaceShow(m){
      this.$router.replace({
        name:'xiangqing',
        query:{
          id:m.id,
          message:m.message
        }
      })
    }
  }
}
</script>

<style scoped>

</style>