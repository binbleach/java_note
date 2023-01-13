package Interface;

public class phone implements photo {
    public void take(){
        System.out.println("我是照片");
    }


    public int num(int b){
        return b+a;  //可以直接调用接口里的属性
    }
}
