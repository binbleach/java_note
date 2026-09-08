const name = '陈平安'
const age = '18'

function getName() {
    return this.name
}

function getCities(){
    return ['倒悬山','落魄山']
}
/*  模块化导入不污染全局
    1、CommonJS 是 Node.js 默认模块规范，浏览器原生不支持，需要打包转换（Browserify或Webpack）
    2、导出数据的方式：
        1）module.exports
        2）exports
        3）this
    3、三个都指向一个空对象{}，最终取的是module.exports的值
    4、导入数据方式：const student = require('./student')
*/
console.log(this)   //{}
console.log(exports)    //{}
console.log(module.exports) //{}
console.log(this === exports && exports === module.exports) //true
// 4、module.exports = 对象的操作会改变引用，导致exports和this会失效
module.exports = {
    name,
    age
}
exports.name='不好'

