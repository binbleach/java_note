package map.HashMap;

import java.util.Objects;
/*如果equals方法重写了必须重写hashcode
* 放在HashMap的key部分和HasHSet集合中的元素必须重写equals和hashCode
* */
public class student {  //主要是同时重写equals和hashCode
    int score;
    String name;

    public student() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        student student = (student) o;
        return score == student.score &&
                Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, name);
    }
}

