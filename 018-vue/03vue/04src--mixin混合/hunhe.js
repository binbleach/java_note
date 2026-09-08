//分别暴露，引用是花括号形式{}
export const a={
    methods:{
        addAge(){
            this.age++;
        }
    }
}
export const b={
    data(){
        return {
            name: "没用，用自身，因为自身也有name数据",
            x:"用了因为自身没有"
        }
    },
    mounted() {
        console.log("混合和自身的挂载都用了，而且混合先执行")
    }
}
export const c = {
    data(){
        return{
            y:"为全局效力"
        }
    },
    mounted() {
        console.log("为全局效力");
    }
}