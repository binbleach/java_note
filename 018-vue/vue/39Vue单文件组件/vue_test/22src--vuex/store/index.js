//一、该文件用于创建Vuex中最核心的store

//1、引入vue
import Vue from 'vue'
//2、引入Vuex
import  Vuex from'vuex'
//3、使用Vuex。必须在store创建之前。所以导入vue在这一步了，main就可以不用导入了
Vue.use(Vuex);

//准备actions用于响应组件中的动作,接收的第一个参数是mini版的store,里面有dispatch可以让你在action里再调actipn
const actions={
    /*jia(context,value){
        //一般这里的JIA为大写，因为是mutations里的，mutations很牛呀
        context.commit('JIA',value);
    },*/
    /*jian(context,value){  这个加和减没什么业务逻辑，直接调用commit就好了，跳过服务员
        context.commit('JIAN',value);
    },*/
    jiaOdd(context,value){
        if(context.state.sum%2){
            context.commit('JIA',value);
        }
    },
    jiaWait(context,value){
        setTimeout(()=>{
            context.commit('JIA',value)
        },500)
    }
}
//准备mutations用于操作数据（state）
const mutations={
    //第一个参数为vuex 里的state
    JIA(state,value){
        state.sum +=value;
    },
    JIAN(state,value){
        state.sum -=value;
    }
}

//准备state用于存储数据
const state ={
    sum:0,
    name:"黄家宾",
    status:"没有思想，没有决定"
}
//准备getters,参数为state对象
const getters ={
    bigSum(state){
        return state.sum*10;
    }
}

//创建store
const store = new Vuex.Store({
    actions:actions,
    mutations, //因为key和value相等，可以简写
    state,
    getters

});
//暴露store
export default store