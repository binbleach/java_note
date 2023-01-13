<template>
  <div id="root">
    <div class="todo-container">
      <div class="todo-wrap">
        <MyHeader :addTodos="addTodos"/>
        <MyList :deleteTodo="deleteTodo" :todos="todos" :checkTodo="checkTodo"/>
        <MyFooter :clearAllTodo="clearAllTodo" :checkAllTodo="checkAllTodo" :todos="todos"/>
      </div>
    </div>
  </div>
</template>

<!--
    1、组件化编码流程：
      （1）拆分静态组件：组件要按照功能点拆分，命令不要与html元素冲突。
      （2）实现动态组件：考虑好数据的存放位置，数据是一个组件再用还是一堆组件在用。
          1）一个组件在用：放在组件自身即可
          2）一堆组件在用，放在他们的共同父组件即可，称为状态提升
      （3）实现交互：从绑定时间开始
    2、props适用于
      （1）父组件 ==> 子组件 通信
      （2）子组件 ==> 父组件 通信（要求父亲先给子一个函数）
    3、使用v-model时要切记，v-model绑定的值不能是props传过来的值，因为props是不可以被修改的！
    4、props传过来的若是对象类型的值，修改对象中的属性时Vue不会报错但不准推荐这样做。

-->

<script>
import MyHeader from './components/MyHeader'
import MyFooter from './components/MyFooter'
import MyList from './components/MyList'
export default {
  name: "App",
  components: {
    MyHeader,
    MyFooter,
    MyList,
  },
  data(){
    return {
      todos:JSON.parse(localStorage.getItem('todos')) || []
    }
  },
  methods: {
    addTodos(todoObj){
      this.todos.unshift(todoObj);
    },
    checkTodo(id){
      this.todos.forEach((todo)=>{
        if(todo.id===id) todo.done = !todo.done
      })
    },
    deleteTodo(id){
      this.todos=(this.todos.filter((todo)=>{
        return todo.id !== id
      }))
    },
    //全选or全不选
    checkAllTodo(done){
      this.todos.forEach((todo)=>{
        todo.done=done
      })
    },
    clearAllTodo(){
      this.todos = this.todos.filter((todo)=>{
        return !todo.done
      })
    }
  },
  watch:{
    todos:{
      //开启深度监视
      deep:true,
       handler(value){
          localStorage.setItem('todos',JSON.stringify(value));
       }
    }
  }
}
</script>

<style >
    body {
      background: #fff;
    }

    .btn {
      display: inline-block;
      padding: 4px 12px;
      margin-bottom: 0;
      font-size: 14px;
      line-height: 20px;
      text-align: center;
      vertical-align: middle;
      cursor: pointer;
      box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
      border-radius: 4px;
    }

    .btn-danger {
      color: #fff;
      background-color: #da4f49;
      border: 1px solid #bd362f;
    }

    .btn-danger:hover {
      color: #fff;
      background-color: #bd362f;
    }

    .btn:focus {
      outline: none;
    }

    .todo-container {
      width: 600px;
      margin: 0 auto;
    }
    .todo-container .todo-wrap {
      padding: 10px;
      border: 1px solid #ddd;
      border-radius: 5px;
    }

</style>