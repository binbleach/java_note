import Vue from 'vue'
import Vuex from 'vuex'
import {postRequest} from "../utils/api";
import {getToken,setToken,removeToken} from "../utils/utils";

Vue.use(Vuex)

//准备actions用于响应组件中的动作,接收的第一个参数是mini版的store,里面有dispatch可以让你在action里再调actipn
const actions={
  // user login
  login({ commit }, userInfo) {
    const { username, password } = userInfo
    return new Promise((resolve, reject) => {
      postRequest(`/sys/login/${username}/${password}/aaaa`).then(success=> {
        const {data} = success
        if (data.code == 200) {
          //成功
          commit('SET_TOKEN', data.data.token)
          setToken(data.data.token)
          resolve(data)
        } else {
          reject(data)
        }
      }, error=> {
          reject(error)
      })
    })
  },
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
  //设置token
  SET_TOKEN: (state, token) => {
    state.token = token
    setToken(token)
  },
  REMOVE_TOKEN:(state) => {
    state.token = null
    removeToken();
  }
}

//准备state用于存储数据
const state ={
  token: getToken(),
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
