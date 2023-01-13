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
                            name:'xiangqing',
                            path:'detail/:id/:message',
                            component:Detail,
                            props($route){
                                return {id:$route.query.id,message:$route.query.message}
                            }

                        }
                    ]
                }
            ]
        },
    ]
})
export default router;