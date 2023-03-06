package Lambda.stream;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.*;
/*
    总结：
        1、惰性求值 (如果没有终结操作，没有中间操作是不会得到执行的)
        2、流是一次性的(一旦一个流对象经过一个终结操作后。这个流就不能再被使用)
        3、不会影响原数据(我们在流中可以多数据做很多处理。但是正常情况下是不会影响原来集合中的元素的。这往往也是我们期望的)
*/
public class StreamMain {
    public static void main(String[] args) {
        test01();
        test02();
        test03();
        test04();
        test05();
        test06();
        test07();
        test08();
        test09();
        test10();
        test11();
        try {
            test12();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        test13();
        test14();
        test15();
    }
    //快速入门，打印所有年龄小于18的作家并去重
    public static void test01(){
        System.out.print("test01（快速入门）: ");
        List<Author> authors = Author.getAuthors();
        authors.stream()    //将集合转为流
                .distinct() //去重
                .filter(author -> author.getAge() < 18)    //过滤
                .forEach(author -> System.out.print(author.getName()+"，")); //循环（是终结操作必须要有，还要其他的）
    }

    //下面是流转换
    //数组转为流
    public static void test02(){
        System.out.print("\n"+"test02（数组转流）: ");
        Integer [] arr = {1,2,3,4,5,4};
        //数组转stream的第一种方式
        Arrays.stream(arr).distinct().forEach(v -> System.out.print(v+"，"));
        System.out.print("\n"+"test02（数组转流）: ");
        //数组转stream的第二种方式
        Stream.of(arr).distinct().forEach(System.out::print);   //方法引用
    }
    //map转为流
    public static void test03(){
        System.out.print("\n"+"test03（map转流）: ");
        Map map = new HashMap<>();
        map.put("id","1");
        map.put("name","徐凤年");
        map.put("age",23);
        Set<Map.Entry<String,Object>> set = map.entrySet();
        set.stream().forEach(v -> System.out.print(v.getKey()+"="+v.getValue()+"，"));
    }

    //下面是中间操作
    //map计算或转换
    public static void test04(){
        System.out.print("\n"+"test04（计算转换）: ");
        //让所有作家age为18，并打印出姓名和年龄
        List<Author> authors = Author.getAuthors();
        authors.stream()
                .map(m->{m.setAge(18);return m;})   //计算
                .map(m->m.getName()+"="+ m.getAge())    //转换
                .forEach(v-> System.out.print(v+"，"));
    }
    //distinct去重、sorted排序、skip略过、limit限量
    public static void test05(){
        System.out.print("\n"+"test05（去重排序略过限量）: ");
        //按年龄降序，去除重复，只取第二位和第三位
        List<Author> authors = Author.getAuthors();
        authors.stream()
                .distinct()   //去重调用的是equals，自定义对象要重写equals
                .sorted()     //排序如果要传空参，自定义对象要实现Comparable接口，重写compareTo方法
                //.sorted((v1,v2)->v2.getAge()-v1.getAge())    //不传空参就不需要重写Comparable
                .skip(1)
                .limit(2)
                .forEach(v-> System.out.print(v.getName()+"="+v.getAge()+"，"));
    }
    //flatmap 计算或转换后展开（map无法将返回结果展开，即使返回是Stream类型）
    public static void test06(){
        System.out.print("\n"+"test06（计算或转换后打散）: ");
        //打印所有书籍的名字，并去重
        List<Author> authors = Author.getAuthors();
        //map去做
        authors.stream()
                .map(m->m.getBooks())
                .distinct()
                .forEach(new Consumer<List<Book>>() {   //从这可以看到，map过后的流是一个个List<Book>
                    @Override
                    public void accept(List<Book> books) {
                        books.forEach(v-> System.out.print(v.getName()+"，"));
                    }
                });
        System.out.print("\n"+"test06（计算或转换后打散）: ");
        //flatmap去做
        authors.stream()
                .flatMap(m->m.getBooks().stream())
                .distinct()
                .forEach(new Consumer<Book>() {
                    @Override
                    public void accept(Book book) { //从这可以看到，flatmap过后的流是一个个Book
                        System.out.print(book.getName()+"，");
                    }
                });
    }

    //下面是终结操作
    //count计数、max最大值，min最小值
    public static void test07(){
        System.out.print("\n"+"test07（计数）: ");
        List<Author> authors = Author.getAuthors();
        long count = authors.stream()
                .flatMap(m -> m.getBooks().stream())
                .distinct()
                .count();   //计数
        System.out.print(count);

        System.out.print("\n"+"test07（最大值）: ");
        Optional<Book> max = authors.stream()
                .flatMap(m -> m.getBooks().stream())
                .distinct()
                //.max((v1,v2)->v1.getScore()-v2.getScore());   //最大值
                .max(comparingInt(Book::getScore));             //最大值，另一种写法
                //.min((v1,v2)->v2.getScore()-v1.getScore());   //最小值反过来写也行
        max.ifPresent(v-> System.out.print(v.getName()));   //ifPresent当Optional有值的时候执行，防止为空

        System.out.print("\n"+"test07（最小值）: ");
        Optional<Book> min = authors.stream()
                .flatMap(m -> m.getBooks().stream())
                .distinct()
                //.min((v1,v2)->v1.getScore()-v2.getScore());     //最小值
                .min(comparingInt(Book::getScore));                  //最小值，另一种写法
                //.max((v1,v2)->v2.getScore()-v1.getScore());   //最大值反过来写也行
        min.ifPresent(v-> System.out.print(v.getName()));
    }
    //collect集合
    public static void test08(){
        System.out.print("\n"+"test08（集合list）: ");
        List<Author> authors = Author.getAuthors();
        List<String> list = authors.stream()
                .map(m -> m.getName())
                .collect(Collectors.toList());
        System.out.print(list);

        System.out.print("\n"+"test08（集合set）: ");
        Set<String> set = authors.stream()
                .map(Author::getName)
                .collect(Collectors.toSet());
        System.out.print(set);

        System.out.print("\n"+"test08（集合map）: ");
        Map<String,Author> map = authors.stream().distinct()    //必须去重否则报错重复key
            .collect(Collectors.toMap(author -> author.getName(), author -> author));//前面取key后面取value
        System.out.print(map);
    }
    //anyMatch任意匹配，allMatch所有匹配，所有不匹配
    public static void test09(){
        List<Author> authors = Author.getAuthors();
        System.out.print("\n"+"test09（任意匹配）: ");
        boolean b = authors.stream()
                .anyMatch(author -> author.getAge()>50);    //任意一位作家年龄大于50
        System.out.print(b);
        System.out.print("\n"+"test09（所有匹配）: ");
        boolean b1 = authors.stream()
                .allMatch(author ->  author.getAge()>10);   //所有年龄作家年龄大于10
        System.out.print(b1);
        System.out.print("\n"+"test09（所有不匹配）: ");
        boolean b2 = authors.stream()
                .noneMatch(author ->  author.getAge()>60);   //所有年龄作家年龄都不大于60
        System.out.print(b2);
    }
    //findAnd查找任意一位，findAnd查找第一位
    public static void test10(){
        List<Author> authors = Author.getAuthors();
        //查找任意一位大于18岁的作者
        System.out.print("\n"+"test10（查找任意）: ");
        Optional<Author> any = authors.stream()
                .filter(m -> m.getAge() > 18)
                .findAny();
        any.ifPresent(v-> System.out.print(v.getName()+"="+v.getAge()));

        //查找年龄最大作者
        System.out.print("\n"+"test10（查找第一）: ");
        Optional<Author> first = authors.stream()
                .distinct().sorted((v1,v2)->v2.getAge()- v1.getAge())
                .findFirst();
        first.ifPresent(v-> System.out.print(v.getName()+"="+v.getAge()));
    }
    //reduce归并（求最大值最小值都是对其进行封装）
    public static void test11() {
        List<Author> authors = Author.getAuthors();
        /*
             reduce 内部操作一：
                T result = identity;
                for(T e : this.stream){
                    result = accumulator.apply(result,element);
                }
                return result;
        */
        //求所有作者年龄和
        System.out.print("\n" + "test11（归并求和）: ");
        Integer reduce = authors.stream()
                .map(m -> m.getAge())   //通常都会配置map使用
                .reduce(0, (p1, p2) -> p1 + p2);     //第一种传参
        System.out.print(reduce);

        //求所有作者年龄最大的
        System.out.print("\n" + "test11（归并求最大值）: ");
        Integer max = authors.stream()
                .map(m -> m.getAge())
                .reduce(0, (p1, p2) -> p1 > p2 ? p1 : p2);    //第一种传参
        System.out.print(max);

        /*
            reduce 内部操作二
            boolean foundAny = false;
            T result = null;
            for (T element : this stream){
                if (!foundAny) {
                    foundAny = true;
                    result = element;
                }e1se{
                    result = accumulator.apply(result， element);
                }
            }
            return foundAny ? optional.of(result) : optional.empty() ;
        */
        //求所有作者年龄最小的
        System.out.print("\n" + "test11（归并求最小值）: ");
        Optional<Integer> min = authors.stream()
                .map(m -> m.getAge())
                .reduce((p1,p2)-> p1<p2?p1:p2); //第二种传参
        min.ifPresent(v-> System.out.print(v));

    }

    //optional
    public static void test12() throws Throwable {
        System.out.print("\n" + "test12（if做安全消费）: ");
        Author nullAuthor = getOneAuthorNull();
        //System.out.println(author.getName());   //1、空的author不能直接getName，NullPointerException
        if(nullAuthor!=null){                         // 2、通常我们会做非空判断，不过每次都要判断太不优雅了。
            System.out.print("\n" + "test12（if做安全消费）: "+nullAuthor.getName());
        }
        Optional<Author> nullOptional= getOneOptionalNull();
        Optional<Author> oneOptional = getOneOptional();
        //nullOptional.get();//nullAuthor.getName();//3、空的optional不能直接get或getName,会NoSuchElementException

        //4、ifPresent：消费值，为空不执行
        nullOptional.ifPresent(v-> System.out.print("\n" + "test12（optional为空消费）: "+v.getName()));

        //5、orElseGet：取值，为空则取自定义的值
        Author author = nullOptional.orElseGet(() -> new Author(9L));
        System.out.print("\n" + "test12（optional为空取值）: "+author);
        Author author2 = oneOptional.orElseGet(() -> new Author(9L));
        System.out.print("\n" + "test12（optional非空取值）: "+author2);

        //6、orElseThrow：取值，为空抛异常
//        Author author3 = nullOptional.orElseThrow(() -> new RuntimeException("值为空"));
//        System.out.print("\n" + "test12（optional为空取值或抛异常）: "+author3);

        //7、filter：过滤，不符合的话optional会为空
        Optional<Author> filter = oneOptional.filter(author1 -> author1.getAge() > 60);
        System.out.print("\n" + "test12（optional过滤）: "+filter);

        //8、isPresent：判断optional是否为空
        if(oneOptional.isPresent()){
            System.out.print("\n" + "test12（optional判断是否存在）: "+oneOptional.get().getName());
        }

        //9、map：计算或转换optional
        Optional<Long> aLong = oneOptional.map(m -> m.getId());
        aLong.ifPresent(v-> System.out.print("\n" + "test12（optional计算或转换）: "+v));


    }

    //模拟获得的对象为 null
    public static Author getOneAuthorNull(){
        Author author = new Author(1L,"蒙多",33,"一个从菜刀中明悟哲理的祖安人",null);
        return null;
    }

    //模拟获得null的 optional
    public static Optional<Author> getOneOptionalNull(){
        Author author = new Author(1L,"蒙多",33,"一个从菜刀中明悟哲理的祖安人",null);
        //对象转为optional
        //return Optional.of(null);        //Optional.of()         如果传null会报异常
        return Optional.ofNullable(null);//Optional.ofNullable() 就算传null也不会异常 值为：Optional.empty()
    }
    //模拟获得的 optional
    public static Optional<Author> getOneOptional(){
        Author author = new Author(1L,"蒙多",33,"一个从菜刀中明悟哲理的祖安人",null);
        return Optional.ofNullable(author);
    }

    //函数是接口predicate的方法使用（关于函数式接口和predicate的介绍请看LambdaMain）
    public static void test13(){
        List<Author> authors = Author.getAuthors();
        System.out.print("\n" + "test13（predicate的and方法）: ");
        //获取年龄大于20，且姓名长度大于1的作家。
        authors.stream()
                //predicate.and()：短路与，使用场景在我们自定义方法传predicate的时候，需要两个条件判断，就可以使用and。参考LambdaMain.test04()
                .filter(((Predicate<Author>) author -> author.getAge() > 20).and(author -> author.getName().length()>1))
                .forEach(v-> System.out.print(v.getName()+"="+v.getAge()+"，"));

        System.out.print("\n" + "test13（predicate的or方法）: ");
        //获取年龄大于50，或者等于20的作家
        authors.stream()
                //predicate.or()：短路或
                .filter(((Predicate<Author>) author -> author.getAge() > 50).or(author -> author.getAge() ==20))
                .forEach(v-> System.out.print(v.getName()+"="+v.getAge()+"，"));

        System.out.print("\n" + "test13（predicate的negate方法）: ");
        //获取年龄不大于25的作家
        authors.stream()
                //predicate.negate()：取反
                .filter(((Predicate<Author>) author -> author.getAge() > 25).negate())
                .forEach(v-> System.out.print(v.getName()+"="+v.getAge()+"，"));

    }

    //基本数据类型优化 mapToInt、mapToLong、mapToDouble,flatMapToInt,flatMapToDouble等
    public static void test14(){
        List<Author> authors = Author.getAuthors();
        System.out.print("\n" + "test14（基本数据类型优化）: ");
        authors.stream()
                .map(m->m.getAge())         //这样写会大量的进行装箱拆箱，性能较差
                .map(m->m+18)
                .filter(m->m>18)
                .map(m->m-2)
                .forEach(v-> System.out.print(v+"，"));

        System.out.print("\n" + "test14（基本数据类型优化）: ");
        authors.stream()
                .mapToInt(m->m.getAge())         //mapToInt直接将类型改为int只会拆箱一次，有效优化
                .map(m->m+18)
                .filter(m->m>18)
                .map(m->m-2)
                .forEach(v-> System.out.print(v+"，"));
    }

    // 并行流。高并发可用，将流分给不同线程处理最后再合并
    public static void test15(){
        List<Author> authors = Author.getAuthors();
        System.out.print("\n" + "test15（串行流）: ");
        Optional<Integer> reduce = authors.stream()
                .peek(author -> System.out.print(author.getName()+"="+Thread.currentThread().getName()+" / "))  //用于调试
                .map(m -> m.getAge())
                .filter(m -> m > 18)
                .reduce((v1, v2) -> v1 + v2);
        System.out.print("result="+reduce.get());

        System.out.print("\n" + "test15（并行流）: ");
        Optional<Integer> reduce2 = authors
            //.parallelStream()     //等等与 .stream() + .parallel()
            .stream()
            .parallel()         //串行流转换为并行流
            .peek(author -> System.out.print(author.getName()+"="+Thread.currentThread().getName()+" / "))  //用于调试
            .map(m -> m.getAge())
            .filter(m -> m > 18)
            .reduce((v1, v2) -> v1 + v2);
        System.out.print("result="+reduce2.get());
    }

}
