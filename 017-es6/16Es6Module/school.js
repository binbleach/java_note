import student, {name,age as age1} from  './student.js' /*默认导入+命名导入*/
import defualtStudent from  './student.js' /*默认导入*/
import * as allData from  './student.js'    /*统一导入*/


console.log('-----------school输出--------------')
console.log(student)
console.log(name)
console.log(age1)
console.log(defualtStudent)
console.log(allData)

const btn = document.getElementById("btn")
const btn1 = document.getElementById("btn1")
//第一种promise接收方法
btn.onclick = async() => {
    const result = await import('./student.js')
    console.log(result)
}
//第二种promise接收方法
btn1.onclick = function (){
    const result = import('./student.js')
    result.then((data)=>{console.log(data)}).catch((err) => {console.log(err)});
}

