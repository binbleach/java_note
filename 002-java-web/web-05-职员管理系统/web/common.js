window.onload=function main(){
    var tops=document.getElementsByTagName("input");
    for(var i=1;i<tops.length;i++){
        tops[i].onclick=fun2;
    }
    tops=document.getElementsByTagName("tr");
    for(var i=1;i<tops.length;i++){
        tops[i].onmouseover=fun3;
    }
    tops=document.getElementsByTagName("tr");
    for(var i=1;i<tops.length;i++){
        tops[i].onmouseout=fun4;
    }
}
function fun1(){
    var tops=document.getElementsByTagName("input");
    var flag=tops[0].checked;
    if(flag){
        for(var i=1;i<tops.length;i++){
            tops[i].checked=flag;
        }
    }else {
        for(var i=1;i<tops.length;i++){
            tops[i].checked=false;
        }
    }

}
function fun2() {
    var tops=document.getElementsByTagName("input");
    var flag=0;
    for(var i=1;i<tops.length;i++){
        if(tops[i].checked==true) {
            flag++;
        }
    }
    if(flag==tops.length-1){
        tops[0].checked=true;
    }else {
        tops[0].checked=false;
    }
}
function fun3() {
    this.style.backgroundColor="red";
}
function fun4() {
    this.style.backgroundColor="white";
}