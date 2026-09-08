<template>
  <li>
    <label>
      <input type="checkbox" :checked="todo.done" @click="handleCheck(todo.id)"/>
      <!--第二种实现选中被选中方式-->
      <!--<input type="checkbox" v-model="todo.done"/>-->
      <span>{{todo.title}}</span>
    </label>
    <button class="btn btn-danger" @click="handleDelete(todo.id)" >删除</button>
  </li>
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
    }
  },
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
</style>