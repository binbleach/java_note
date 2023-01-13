
import VueRouter from "vue-router"
import Vue from "vue"
import About from "../components/About";
import Home from "../components/Home";
import News from "../pages/News";
import Message from "../pages/Message";
import Detail from "../pages/Detail";
Vue.use(VueRouter);
const router=new VueRouter({
    mode:"history",//开启history模式，则路径不再显示#（#后面的不会交给服务器），默认是hash模式。
    routes:[
        {
            name:'guanyu',
            path:'/about',
            component:About,
            meta:{title:"关于"}
        },
        {
            meta:{title:"主页"},
            name:'zhuye',
            path:'/home',   //一级路由
            component: Home,
            children:[
                {
                    name:'xinwen',
                    path:'news',    //二级路由，前面不加/
                    component: News,
                    //程序员自定义属性,可配合鉴定权限使用
                    meta:{isAuth:true,title:"新闻"}
                },
                {
                    meta:{isAuth:true,title:"消息"},
                    name:'xiaoxi',
                    path: 'message',
                    component: Message,
                    children:[          //三级路由
                        {
                            meta:{title:"详情"},
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