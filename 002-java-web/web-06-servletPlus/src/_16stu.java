/*
* 这里的学生对象没写setget 方法调不了，报错属性找不到，气死
* */

public class _16stu {
        String age;
        String name;

        public _16stu(){

        }
        public _16stu(String age, String name) {
            this.age = age;
            this.name = name;
        }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
