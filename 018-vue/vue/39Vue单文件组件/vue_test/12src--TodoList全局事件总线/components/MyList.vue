<template>
  <ul class="todo-main">
    <MyItem :deleteTodo="deleteTodo" :checkTodo="checkTodo" v-for="todoObj in todos" :key="todos.id" :todo="todoObj"/>
  </ul>
</template>

<scr11src--全局事件总线ipt>
import MyItem from "./MyItem";
export default {
  name: "MyList",
  props:["todos","checkTodo","deleteTodo"],
  components:{
    MyItem,
  }
}
</scr11src--全局事件总线ipt>

<style scoped>
/*main*/
.todo-main {
  margin-left: 0px;
  border: 1px solid #ddd;
  border-radius: 2px;
  padding: 0px;
}

.todo-empty {
  height: 40px;
  line-height: 40px;
  border: 1px solid #ddd;
  border-radius: 2px;
  padding-left: 5px;
  margin-top: 10px;
}
</style>