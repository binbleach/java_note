package stack;

public class Stack {
    private Object elements[];

    public Object[] getElements() {
        return elements;
    }

    public void setElements(Object[] elements) {
        this.elements = elements;
    }

    Stack(){
        elements = new Object[10];
    }
    int index = -1;

    public void push (Object obj) throws StackException {
        if( index >= elements.length-1){
            throw new StackException("栈已满，堆栈失败");
        }
        index++;
        elements[index]=obj;
        System.out.println("堆栈成功"+elements[index]+"栈帧在"+index);
    }

    public void bod() throws StackException {
        if(index < 0){
            throw new StackException("栈已空，弹栈失败");
        }
        index--;
        System.out.println("弹栈成功"+"栈帧在"+index);
    }

}
