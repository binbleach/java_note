
import Vue from 'vue'
import  Vuex from'vuex'
import axios from "axios";
Vue.use(Vuex);

const countOptions = {
    namespaced:true,
    actions:{
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
    },
    mutations:{
        JIA(state,value){
            state.sum +=value;
        },
        JIAN(state,value){
            state.sum -=value;
        },
    },
    state:{
        sum:0,
        name:"黄家宾",
        status:"没想法没思考"
    },
    getters:{
        bigSum(state){
            return state.sum*10;
        }
    }
}
const personOptions = {
    namespaced:true,
    actions:{
        /*这里的context是本对象context*/
        addPersonHuang(context,value) {
            if(value.indexOf('黄')===0){
                console.log("aaaaaa",context)
                let id =parseInt(context.state.personList[0].id)+1;
                let personObj={id,name:value}
                context.commit('ADD_PERSON',personObj)
            }
        },
        addPersonServer(context){
            axios.get('https://api.uixsj.cn/hitokoto/get?type=social').then(
                response => {
                    let id =parseInt(context.state.personList[0].id)+1;
                    context.commit("ADD_PERSON",{id,name:response.data})
                },
                error=>{
                    alert(error.message)
                }
            )
        }
    },
    mutations:{
        ADD_PERSON(state,personObj){
            state.personList.unshift(personObj)
        }
    },
    state:{

        personList:[
            {id:"2",name:"张可可"},
            {id:"1",name:"杨意昕"},
        ]
    },
    getters:{
        firstPersonName(state){
            //这里的state是本对象里的state
            return state.personList[0].name
        }
    }
}

const store = new Vuex.Store({
    modules:{
        a:countOptions,
        b:personOptions
    }
});
export default store