/*默认暴露*/
export default {
    install(Vue) {
        console.log('install', Vue);
        //全局过滤器
        Vue.filter('mySlice',function (value){
                return value.substring(0,4);
        });
        //全局定义指令
        Vue.directive('fbind',{
            bind(element,binding){
                element.value=binding.value
            },
            inserted(element,binding){
                element.focus();
            },
            update(element,binding){
                element.value=binding.value
            }
        })
        //定义全局混入
        Vue.mixin({
            data(){
                return {
                    x:100,
                    y:200,
                }
            }
        })
        //给Vue原型对象添加属性
        Vue.prototype.hello=()=>{
            alert("你好呀")
        }

    }
}