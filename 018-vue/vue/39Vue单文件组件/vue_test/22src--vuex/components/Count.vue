<template>
  <div >
    <h1>当前大求和为：{{$store.getters.bigSum}}</h1>
    <h1>当前求和为：{{$store.state.sum}}</h1>
    <select v-model="n">
      <option :value="1">1</option>
      <option :value="2">2</option>
      <option :value="3">3</option>
    </select>
    <button @click="increment">+</button>
    <button @click="decrement">-</button>
    <button @click="incrementOdd">当前为奇数再加</button>
    <button @click="incrementWait">等一等再加</button>
    <hr>
    <h1>当前求大和：{{bigSum}}</h1>
    <h1>当前求和：{{sum}}</h1>
    <h1>名字：{{name}}</h1>
    <h1>状态：{{status}}</h1>
    <button @click="JIA(n)">+</button>
    <button @click="JIAN(n)">-</button>
    <button @click="jiaOdd(n)">和为奇则1加</button>
    <button @click="jiaWait(n)">过一会加</button>
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
    increment(){
      //没有业务逻辑,和网络请求直接调commit,对话mutations
      this.$store.commit("JIA",this.n);
    },
    decrement(){
      this.$store.commit("JIAN",this.n);
    },
    incrementOdd(){
      //有业务逻辑的调dispatch,对话actions
      this.$store.dispatch("jiaOdd",this.n);
    },
    incrementWait(){
      this.$store.dispatch("jiaWait",this.n)
    },
    //用mapMutations生成对应方法，方法中会调用commit去联系mutations
    ...mapMutations({JIA:'JIA',JIAN:'JIAN'}),
    /*...mapMutations(['JIA','JIAN']) //数组的写法*/

    //用mapActions生成对应方法，方法中会调用dispatch去联系mctions
    ...mapActions({jiaOdd:'jiaOdd',jiaWait:'jiaWait'}),
    /*...mapActions(["jiaOdd","jiaWait"]) //数组写法*/
    /*注：mapMutations和mapActions生成代码时若传参数得现在模板传好参数*/
  },
  computed:{
    //靠自己写计算属性，来简便调用$store.state.sum等过长的属性调用方式
    /*sum(){
      return this.$store.state.sum;
    },
    name(){
      return this.$store.state.name;
    },
    status(){
      return this.$store.state.status;
    }*/

    //用mapState成计算属性，从state中读取数据，首先要导入mapState哈，还有就是前面的...是展开对象的意思
    ...mapState({sum:'sum',name:'name',status:'status'}), //这里不能用对象简写形式，因为值sum等不是变量
    //...mapState(['sum','name','status']),  //可以用这种简写形式，不过生成的计算属性的名字和state的名字要一致
    ...mapGetters({bigSum:'bigSum'}),    //这个和mapState类似不过读的是getters里的数据生成计算属性
  }
}
</script>

<style scoped>
  button{
    margin-left: 5px;
  }
</style>