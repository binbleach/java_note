# vue_test笔记：

###一、安装
```
npm install
```

### 二、启动项目
```
npm run serve
```

### 三、打包
```
npm run build
```

### 四、语法检查
```
npm run lint
```

### 五、自定义配置
```
See [配置参考](https://cli.vuejs.org/config/).
```
### 六、ref属性
```
    1、被用来给元素或子组件注册引用信息（id得替代者）
    2、应用在html标签上获取的是真实DOM元素，应用在组件标签上是组件实例对象（vc）
    3、使用方式：
        （1）打标识：<h1 ref="xxx">......</h1> 或 <School ref="xxx"/>
        （2）获取：this.$refs.xxx
```
### 七、配置项props
```
    功能：让组件接收外部传过来的数据
        （1）传递数据：
            <School name="科城" :age="100" />     #age前加冒号是动态绑定让它识别数字

        （2）接收数据：
            第一种方式（只接收）：
                props:['name']

            第二种方式（限制类型）：
                props:{
                    name:String,
                    age:Number
                }

            第三种方式（限类型、限必要条件、指定默认值）
                props:{
                    name:{
                        type:String,
                        required:true,
                        default:'本科学校'
                    }
                    age:{
                        type:Number,
                        required:false
                    }
                }
    备注：props是只读的，Vue底层会监测你对props的修改，如果进行了修改，就会发出警告，
        若业务需求确实需要修改，那么请复制props的内容道data中一份，然后去修改data中的数据。
```

### 八、mixin(混入、混合)
```
    功能：可以把多个组件共用的配置提取成一个混入对象
    使用方式：
        第一步定义混合，例如：
            {
                data(){...},
                methods:{...}
            }
        第二部使用混合，例如：
          （1）全局混入：Vue.mixin(xxx)
          （2）局部混入：mixin:['xxx']
```
### 九、插件
```
    功能：用于增强Vue
    本质：包含install方法的一个对象，install的第一个参数是Vue，第二个参数是插件使用者传递的数据
    定义插件：
        export default {
            //1、添加全局过滤器
                Vue.filter(...)
            //2、添加全局指令
            Vue.directive(...)
            //3、配置全局混入
            Vue.mixin(...)
            //4、添加实例方法
            Vue.prototype.$myMethod = function(){...}
        }
    导入插件：import plugins from "./plugins";
    使用插件：Vue.use(plugins)
```
### 十、scoped样式、lang作用
```
        scoped作用：让样式直在当前布局生效，防止冲突，一般不在App.vue文件写scoped
            样式冲突：当两个组件有同一个样式时会发生冲突。后引入的样式会覆盖先引入的
            写法：<style scoped>
        lang作用：标识style的语言可以十css也可以是less
```
### 十一、TodoList案例
```
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
```   

### WebStorage
```
    1、存储内容大小一般支持5MB左右（不同浏览器可能还不一样）
    2、浏览器端通过Window.sessionStorage 和 Window.localStorage属性来实现本地存储机制
    3、相关API：
        1、xxxxxStorage.setItem("key","value");  // 存储，内容会覆盖
        2、xxxxxStorage.getItem("key");
        3、xxxxxStorage.removeItem("key");
        4、xxxxxStorage.clear()      //清空所有
    4、备注：
        1、SessionStorage存储的内容会随着浏览器窗口关闭而消失
        2、LocalStorage存储的内容，需要手动消除才会消失
        3、xxxxxStorage.getItem(xxx)如果xxx对应的value获取不到，那么返回的值十null
        4、JSON.parse(null)的结果依然是null       
```
### 自定义事件1
```
    1、自定义组件事件可以传递student.vue的数据给app.uve：子给父传东西
    2、我们学过可用props实现子到父的传递，共同点是都要父app处写回调函数。
    3、区别是：
          props：父app传了方法，子school用props接收了
          自定义事件：父app给子绑定了事件，子通过$emit(“事件名”,参数)自己触发事件，触发事件同时可传参数
    4、自定义事件的第二种写法：
        1）给子组件student注册信息：
            <Student ref="student"/>
        2）获取studnet组件并绑定事件
            mounted(){
                this.$ref.student.$on("事件名",触发的方法)
            }
        注：好处，更灵活，如不是一开始就给组件绑定事件而是等三秒
    5、自定义事件只触发一次：
        1）<Student @receiveStudent.once="receiveStudentMethod"/>
        2）this.$ref.student.$once("receiveStudent",this.receiveStudentMethod)
```
### 自定义事件2
```
    1、一种组件间通信得方式，适用于：子组件===>父组件
    2、使用场景：A是父组件，B是子组件，B想给A传数据，那么要在A中给B绑定自定义事件（事件得回调在A中）。
    3、绑定自定义事件：
        1、第一种方式，在父组件中：<Dome @event="method1"/>或<Dome v-on:event="method2"/>
        2、第二种方式，在父组件中：
            <Demo ref="dome"/>
            mounted(){
                this.$refs.dome.$on("event",this.method1)
            }
        3、若想让自定义事件只触发一次，可以使用once修饰符，或$once方法
    4、触发自定义事件：在子组件处：this.$emit("event",B数据)
    5、解绑自定义事件：在子组件处：this.$off("event"),解绑多个this.$off(["event1","event2"])
    6、组件上也可以绑定原生DOM事件，需要使用native修饰符
    7、注意：通过this.$refs.xxx.$on("event",回调方法)，绑定自定义事件时，回调要么配置在methods中，要么使用箭头函数，
            否则this会指向触发事件的子组件。    
        
    
```
###Vue封装的过度与动画
```
    1、作用：在插入、更新或者一处DOM元素时，在适合的时候给元素添加样式类名
    2、写法：
        元素进入屏幕的六个场景：
              1、进入起点：.tran2-enter
              2、进入终点：.tran2-enter-to
              3、进入整个：.tran2-enter-active
              4、离开起点：.tran2-leave
              5、离开终点：.tran2-leave-to
              6、离开整个：.tran2-leave-active.
              注：1和5、2和4、3和6，相互对应
    注：多个标签样式得用transition-group标签
```
###17AJAX跨域
```
    1、xhr：new XMLHttpRequest() xhr.open() xhr.send()
    2、jQuery：封装了xhr更多的还是对DOM的操作封装
    3、axios：比jQuery小，也封装了jQuery
    4、fetch：和xhr平级
    跨域问题：浏览器因为同源限制，不给从后端返回请求到前端的数据。服务器（后端）之间没有跨域问题
    跨域问题解决:
    1、cors：需要后端
    2、jsonp：需要前后端配合
    3、代理服务器：nginx、Vue-cli

    Vue-cli解决跨域问题：
    方式一：
        1）配置文件上配置
        module.exports={
            devServer:{
                proxy:'访问三方接口服务器的url'
            }
        }
        2）写ajax时访问代理服务器，也就是本机服务器就好了。
            注：如果代理服务器就有资源则不再去三方接口服务器拿
            缺点：不能配置多个三方接口服务器，不能配置到底走不走代理
    方式二：
        1）配置文件上配置
        module.exports={
            devServer:{
                proxy:{
                    '/myweb':{        //代理服务器拦截前端带myweb的请求。如：http:localhost:8080/myweb/student
                        target:'http://localhost:5000',    //拦截后代替前端去访问三方服务器。如：http:localhost:5000/myweb/student
                        pathRewrite:{'^/myweb':''}  //重写源路径，比如去掉myweb。http://loccalhost:5000/student
                        ws:true         //websocket
                        changeOrigin:true   //控制请求头中host值。谎报后三方服务器就会以为是localhost:5000的ip端口请求。默认为true
                    }
                    '/myweb2':{       
                        target:'http://localhost:5000',    
                        pathRewrite:{'^/myweb2':''}  
                        ws:true       
                        changeOrigin:true  
                    }
                }
            }
        }
        2）没有方式一的缺点
        
```
###插槽
```
    1、作用：让父组件可以向子组件指定位置插入html结构，也是一种组件间通信的方式，适用于 父组件 ===>子组件
    2、分类：默认插槽、具名插槽、作用域插槽
    3、使用方式：
       
```




