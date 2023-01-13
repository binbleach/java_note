<template>
<!--还可以在MyList组件上加，不过得用transition-group-->
  <transition name="tran1" appear>
  <li>
    <label>
      <input type="checkbox" :checked="todo.done" @click="handleCheck(todo.id)"/>
      <!--第二种实现选中被选中方式-->
      <!--<input type="checkbox" v-model="todo.done"/>-->

      <span v-show="!todo.isEdit">{{todo.title}}</span>
      <input ref="huoqu" v-show="todo.isEdit" type="text" :value="todo.title" @blur="handleBlur($event)"/>
    </label>
    <button class="btn btn-danger" @click="handleDelete(todo.id)" >删除</button>
    <button class="btn btn-edit" @click="handleEdit(todo)" >编辑</button>
  </li>
  </transition>
</template>


<!--
    关于选中和被选中还有第二种实现方案，就是直接v-model="todo.done"
    v-model是双向数据绑定，虽说todo是props传过来的只读的值但是todo地址没变，
    只是todo里的done数据改变了。不建议，因为vue不建议
-->

<script>
export default {
  name: "MyItem",
  props:["todo","checkTodo","deleteTodo"],
  methods:{
    //勾选或者取消勾选
    handleCheck(id){
      this.checkTodo(id)
    },
    handleDelete(id){
      if(confirm("确定删除吗?")){
        this.deleteTodo(id);
      }
    },
    //编辑
    handleEdit(todo){
      if(todo.hasOwnProperty("isEdit")){
        todo.isEdit=true;
      }else {
        this.$set(todo,"isEdit",true);
      }
      //this.$nextTick(function(){}) 会在走完此方法节点更新后，执行指定回调
      this.$nextTick(function () {
        //让输入框获取焦点
        this.$refs.huoqu.focus();
      })
     /*
      定时器不给时间也能解决获取焦点问题，因为就算不给时间也要在队列里，会在走完更新才执行
      setTimeout(()=>{
        this.$refs.huoqu.focus();
      })*/
    },
    //失去焦点后修改数据
    handleBlur(e){
      this.todo.isEdit=false;
      console.log(this.todo.id+e.target.value);
      this.$bus.$emit("dataEdit",this.todo.id,e.target.value);
    }
  },
  //妙呀，解决点击编辑后获取焦点，有弊端
  /*updated() {
    this.$refs.huoqu.focus();
  }*/
}
</script>

<style scoped>
/*item*/
li {
  list-style: none;
  height: 36px;
  line-height: 36px;
  padding: 0 5px;
  border-bottom: 1px solid #ddd;
}

li label {
  float: left;
  cursor: pointer;
}

li label li input {
  vertical-align: middle;
  margin-right: 6px;
  position: relative;
  top: -1px;
}

li button {
  float: right;
  display:none;
  margin-top: 3px;
}

li:before {
  content: initial;
}

li:last-child {
  border-bottom: none;
}
li:hover{
  background-color: #cccccc;
}
li:hover button{
  display: block;
}
.tran1-enter-active{
  animation: active1 1s;
}
.tran1-leave-active{
  animation: active1 1s reverse;
}
@keyframes active1 {
  from{
    transform: translateX(100%);
  }
  to{
    transform: translateX(0);
  }
}
</style>