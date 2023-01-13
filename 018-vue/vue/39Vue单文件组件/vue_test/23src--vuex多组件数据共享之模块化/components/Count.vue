<template>
  <div >
    <h1>当前求大和：{{bigSum}}</h1>
    <h1>当前求和：{{sum}}</h1>
    <h1>名字：{{name}}</h1>
    <h1>状态：{{status}}</h1>
    <button @click="JIA(n)">+</button>
    <button @click="JIAN(n)">-</button>
    <button @click="jiaOdd(n)">和为奇则1加</button>
    <button @click="jiaWait(n)">过一会加</button>
    <p>Person组件总人数是{{personList.length}}</p>
  </div>
</template>

<script>
import {mapState,mapGetters,mapMutations,mapActions} from 'vuex'
export default {
  name: "Category",
  props:['title'],
  data(){
    return{
      n:1,  //用户选择数字
    }
  },
  methods:{
    ...mapMutations('a',{JIA:'JIA',JIAN:'JIAN'}),
    ...mapActions('a',{jiaOdd:'jiaOdd',jiaWait:'jiaWait'}),
  },
  computed:{
    //这样写就可以用a.sum,a.name,a,status,b.personList.length
    /*...mapState(['a','b']),*/
    //这种写法更加简化，不过记得加namespaced:true,
    ...mapState('a',['sum','name','status']),
    ...mapState('b',['personList']),
    ...mapGetters('a',{bigSum:'bigSum'}),
  },
  mounted() {
    console.log(this.$store)
  }
}
</script>

<style scoped>
  button{
    margin-left: 5px;
  }
  p{
    color: red;
    font-size: 20px;
  }
</style>