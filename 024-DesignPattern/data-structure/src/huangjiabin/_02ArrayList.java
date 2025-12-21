package huangjiabin;

import java.util.ArrayList;

/*
    动态数组所需方法：
        size()
        isEmpty()
        contains()
        add()
        get()
        set()
        remove()
        indexOf()
        clear()
*/
public class _02ArrayList {
    private int size;
    private String[] elements ;
    private static final int DEFAULT_CAPACITY = 10;

    public static void main(String[] args) {
        _02ArrayList arrayList = new _02ArrayList();
        arrayList.add("a");
        arrayList.add("b");
        arrayList.add("c");
        arrayList.add("d");
        arrayList.add(1,"e");
        arrayList.add("f");
        System.out.println(arrayList);
        System.out.println(arrayList.remove(1));
        System.out.println(arrayList);
    }

    public _02ArrayList(int capaticy){
        if(capaticy<0){
            capaticy = DEFAULT_CAPACITY;
        }
        size = 0;
        elements = new String[capaticy];
    }

    public _02ArrayList(){
        this(DEFAULT_CAPACITY);
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public boolean contains(String element){
        if(indexOf(element) != -1){
            return true;
        }
        return false;
    }

    public void add(String element){
        elements[size++] = element;
    }
    public void add(int index,String element){
        if(index < 0 || index >size){
            throw new IndexOutOfBoundsException("index："+index+"，size："+size);
        }
        for(int i =size;i>index-1;i--){
            elements[i] = elements[i-1];
        }
        elements[index] = element;
        size++;
    }
    public String get(int index){
        if(index < 0 || index >=size){
            throw new IndexOutOfBoundsException("index："+index+"，size："+size);
        }

        return elements[index];
    }
    public String set(int index,String element){
        String oldElement = elements[index];
        elements[index] = element;
        return oldElement;
    }
    public String remove(int index){
        if(index < 0 || index >=size){
            throw new IndexOutOfBoundsException("index："+index+"，size："+size);
        }
        String old = elements[index];
        for(int i =index;i<=size-2;i++){
            elements[i] = elements[i+1];
        }
        size--;
        return old;
    }
    public int indexOf(String element){
        for(int i = 0;i<size ;i++){
            if(elements[i] == element){
                return i;
            }
        }
        return -1;
    }
    public void clear(){
        size = 0;
    }
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("[");
        for(int i =0;i<size;i++){
            s.append(elements[i] + "，");
        }
        s.deleteCharAt(s.length()-1);
        s.append("]");
        s.append("，size="+size);
        return s.toString();
    }
}
