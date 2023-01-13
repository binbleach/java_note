<template>
    <div class="todo-footer" v-show="total">
      <label>
        <input type="checkbox" v-model="isAll"/>
      </label>
      <span>
          <span>已完成{{doneTotal}}</span> / 全部{{todos.length}}
        </span>
      <button class="btn btn-danger" @click="clearAll">清除已完成任务</button>
    </div>
</template>

<script>
export default {
  name: "MyFooter",
  props:["todos","checkAllTodo","clearAllTodo"],
  computed:{
    total(){
      return this.todos.length
    },
    doneTotal(){
      //reduce:第一个参数是前一次调用的返回值，第一次调用的前一次是设置的，这里初始值设置是0,
      //      第二个参数是当前元素项
      const  x=this.todos.reduce((pre,current)=>{
        return pre+(current.done?1:0)
      },0)
      return x;
    },
    isAll:{
      get(){
        return this.doneTotal === this.total && this.total > 0;
      },
      set(value){
        this.checkAllTodo(value);
      }
    }
  },
  methods:{
    clearAll(){
      this.clearAllTodo();
    }
  }

}
</script>

<style scoped>
/*footer*/
.todo-footer {
  height: 40px;
  line-height: 40px;
  padding-left: 6px;
  margin-top: 5px;
}

.todo-footer label {
  display: inline-block;
  margin-right: 20px;
  cursor: pointer;
}

.todo-footer label input {
  position: relative;
  top: -1px;
  vertical-align: middle;
  margin-right: 5px;
}

.todo-footer button {
  float: right;
  margin-top: 5px;
}
</style>