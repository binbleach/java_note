/*
    es6导出最好是const，防止修改。因为commonJS导出的是复制的值，es6导出的是引用
    amd和cmd模块化都是define(...)，AMD还需要在模块化入口配置的requirejs.config({})，CMD里面有commonjs的元素。分得清就行
    1、ES6 Module 是 JavaScript 官方标准模块化方案，浏览器、Node.js、打包工具（Webpack/Vite/Rollup）均支持。
        注：node.js中使用需要，项目根目录 package.json 添加{"type":"module"}。html页面引入时也需要加上。
    2、导出数据的方式：
        1）单个导出：export const name = '陈平安'
        2）统一导出：export { name, age } （这里的{}不是对象，不能键值对的写）
        3）默认导出：export default { name, age } （这里的{}是个对象，导入时会多一层default，只能有一个默认导出）
    3、导入数据方式（必须加.js，commonjs里可以加可不加）：
        1）统一导入：import * as student from  './student.js'
        2）默认导入（默认导出适用）：import 任意变量名称 from  './student.js'
        3）命名导入（单个导出和统一导出适用）：import {name, age} from  './student.js'
        4）混合使用：import student, {name,age as age1} from  './student.js'
        5）动态导入：js里用到的时候再执行 import('./student') 返回的是一个Promise函数（可以用async/await接收）
*/
export const name = '陈平安'
export const age = '18'

function getName() {
    return this.name
}

function getCities(){
    return ['倒悬山','落魄山']
}
export { getName, getCities }

export default { 'name1':'666', 'age1':'19' }
