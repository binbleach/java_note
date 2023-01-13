<template>
  <div>
    <p style="font-size: 20px;color: #da4f49">Count组件求和数是：{{$store.state.a.sum}}</p>
    <h1>人员列表</h1>
    <p style="font-size: 20px;color: #da4f49">列表中第一个人的名字是{{firstPersonName}}</p>
    <input type="text" placeholder="请输入名字" v-model="name"/>
    <button @click="addPerson(name)">添加</button>
    <button @click="addPersonHuang(name)">姓黄才可添加</button>
    <button @click="addPersonServer">添加通过ajax</button>
    <ul>
      <li v-for="person in personList" :key="person.id">{{person.name}}</li>
    </ul>
  </div>
</template>

<script>
import {mapState,mapMutations} from "vuex"
export default {
  name: "Person",
  data(){
    return {
      name:'',
    }
  },
  computed:{
     personList(){
       return this.$store.state.b.personList;
     },
    firstPersonName(){
      return this.$store.getters['b/firstPersonName']
    },
  },
  methods:{
    addPerson(){
      let id =parseInt(this.$store.state.b.personList[0].id)+1;
      let personObj={id,name:this.name}
      //模块化的特殊之处，Count组件用了四大方法和这个不一样
      this.$store.commit('b/ADD_PERSON',personObj);
      this.name=''
    },
    addPersonHuang(name){
      this.$store.dispatch('b/addPersonHuang',name)
    },
    addPersonServer(){
      this.$store.dispatch('b/addPersonServer')
    }
  }
}
</script>

<style scoped>

</style>