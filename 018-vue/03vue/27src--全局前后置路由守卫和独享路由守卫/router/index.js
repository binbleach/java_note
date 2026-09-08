
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
                            //独享路由守卫
                            beforeEnter:(to,from,next)=> {
                                if(localStorage.getItem("student")==="黄家宾"){
                                    next();
                                }else {
                                    alert("你不是黄家宾，你无权查看")
                                }
                            },
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
//场景特殊页面得登录后才可查看
//创建全局前置路由守卫，初始化前或每一次路由切换之前，都调用写的函数
//三个参数第一个是目的组件，第二个来来自组件，第三个放行
router.beforeEach((to,from,next)=>{
   /* if(to.path === '/home/news'||to.path==='/home/message'){*/
    if(to.meta.isAuth){
        console.log("to")
        console.log(+to);
        console.log(from)
        //这里自己去浏览器的开发中选项里设置school=laji才能查看
        if(localStorage.getItem('school')==='laji'){
            next();
        }else {
            alert("学校名不对，无权查看")
        }
    }else {
        next();
    }
});
//后置路由守卫，初始化前或切换路由后调用,后置路由守卫没有next()
//用的不多，但是有点作用，比如可以转换title页签
router.afterEach((to,from)=>{
    document.title=to.meta.title?to.meta.title:"无"
})
export default router;