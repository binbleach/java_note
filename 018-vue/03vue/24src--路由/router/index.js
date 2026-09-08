//该文件专门用于创建整个应用的路由器，和store类似

import VueRouter from "vue-router"
import Vue from "vue"
import About from "../components/About";
import Home from "../components/Home";
import News from "../pages/News";
import Message from "../pages/Message";
import Detail from "../pages/Detail";
Vue.use(VueRouter);
const router=new VueRouter({
    routes:[
        {
            path:'/about',
            component:About
        },
        {
            path:'/home',   //一级路由
            component: Home,
            children:[
                {
                    path:'news',    //二级路由，前面不加/
                    component: News
                },
                {
                    path: 'message',
                    component: Message,
                    children:[          //三级路由
                        {
                            //设置路由明显可以，跳转时直接用，就不用/home/message/detail写这么长
                            name:'xiangqing',
                            path:'detail/:id/:message',
                            component:Detail,
                            //props的第一种写法，值为对象，该对象中的所有key-value都会以peops的形式传给Detail组件
                            //props:{a:1,b:"hello"}  //用的少，因为写的都是死数据

                            /*props的第二种写法，值为布尔值，若布尔值为真，就会把该路由组件
                            收到的所以params参数,以props的形式传给Detail*/
                            //props:true,

                            //props的第三种写法，弥补第一种死数据，弥补第二种只能转params传来的数据
                            props($route){
                                return {id:$route.params.id,message:$route.params.message}
                            }
                            /*第三种的简写，连续结构赋值
                            props( { query:{id,message} } ){
                                return {id,message}
                            }*/

                        }
                    ]
                }
            ]
        },
    ]
})
export default router;