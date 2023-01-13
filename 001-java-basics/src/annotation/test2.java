package annotation;

import java.lang.reflect.Field;

public class test2 {
    public static void main(String[] args) throws ClassNotFoundException {
        Class studentClass = Class.forName("annotation.Student");
        Field[] field =studentClass.getDeclaredFields();

        if(studentClass.isAnnotationPresent(id.class)){
            boolean isOk = false;
            for (Field field1 : field) {
                if("id".equals(field1.getName())&&"int".equals(field1.getType().getSimpleName())){
                    isOk=true;
                    break;
                }
            }
            if(!isOk){
                    throw new MyException("必须要有一个int属性");
            }
        }
    }
}
