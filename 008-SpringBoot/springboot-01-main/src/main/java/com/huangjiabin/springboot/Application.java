package com.huangjiabin.springboot;
import com.huangjiabin.springboot.config.MyConfig;
import com.huangjiabin.springboot.domain.Student;
import com.huangjiabin.springboot.domain.Teacher;
import com.huangjiabin.springboot.service.StudentService;
import com.huangjiabin.springboot.service.TeacherService;
import com.huangjiabin.springboot.service.impl.TeacherServiceImpl;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.Resource;

/*
    三种创建springboot工程方法
    1、new project -> spring initializr default -> https://start.spring.io/
     ->group:com.huangjibin.springboot、artifact:001-springboot-first、package:com.huangjibin.springboot
    2、new project -> spring initializr custom -> https://start.aliyun.com
    阿里云创建springboot项目会用多模块管理引入springboot项目,还多了一个maven官方编译插件
    3、创建一个maven工程，父项目继承springboot就好了
*/
//@SpringBootApplication//springboot核心注解，主要用于开启spring自动配置
//@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
//@Import(TeacherServiceImpl.class)   //导入TeacherServiceImpl类到容器中使成为bean对象
/*
    1、@SpringBootApplication = @SpringBootConfiguration + @EnableAutoConfiguration + @ComponentScan
    2、@SpringBootConfiguration ：
        1）包含@Configuration 标识配置类，主启动类并不靠它标识配置类，所以看不出来有啥用。
        2）配置类本身又是bean对象：是spring所管理的，可以被注入的。因为@Configuration中包含@Component有声明bean的作用
        3）springboot2中引入了一个重要的属性，用来解决bean之间的依赖问题：
            Full模式：@Configuration(proxyBeanMethods = true) 表示开启代理bean方发，每次调用bean中方发会去容器中寻找bean对象。
            Lite模式：@Configuration(proxyBeanMethods = false) 表示不开启代理bean方发，每次调用bean中方发会重新创建对象。
    3、@EnableAutoConfiguration 声明了 AutoConfigurationImportSelector bean，它借助 SpringFactoriesLoader.loadFactoryNames()
       把 spring-boot-autoconfigure.jar/META-INF/spring.factories 中每一个xxxAutoConfiguration文件都加载到容器中
    4、@ComponentScan 扫描主启动类同包或同包下子包的类
    5、@Import(xxx.class) 用于导入类使其成为bean对象。可导入普通类、配置类、ImportSelector和ImportBeanDefinitionRegistrar的实现类。
       可导入到bean对象声明的地方，如：@Configuration、@Service、@Component等注释的地方。
    6、@ImportResource(location={"classpath:xxx.xml"})导入.xml文件使类成为容器中bean对象，用于新老项目结合
    7、@SpringBootApplication(exclude={DataSourceAutoConfiguration.class}) 禁止某些自动化配置。
       在配置文件中加：spring.autoconfigure.exclude=org.spring...DataSourceAutoConfiguration 也可以禁止，达到优化程序的效果。
    8、继承CommandLineRunner和ApplicationRunner重写的run方法都会在bean创建后回调，可以做一些初始化操作（先回调ApplicationRunner.run）
    0、ApplicationContext context = SpringApplication.run(Application.class, args)/new ClassPathXmlApplicationContext("beans.xml");

*/
public class Application implements CommandLineRunner {
    @Autowired
    StudentService studentService;

    @Autowired
    StringEncryptor stringEncryptor;    //用于测试@EnableAutoConfiguration的自动配置功能，结果：没有它引入的依赖会注入失败。

    public static void main(String[] args) {
        //启动spring ioc容器第一种写法，会返回一个ApplicationContext，可以用来调getBean()拿注册到容器里的对象，然后掉对象方法
        //ApplicationContext context= SpringApplication.run(Application.class, args);

        //第二种写法，是第一种拆分了两步。好处是可以配置app属性（其中配置文件也可以配置，且配置文件优先级更高/有些地方如mvc配置是配置类高）
        /*
        //可以在配置文件中配置spring.main.sources=com.huangjiabin.springboot.Application，就不用传Application.class了
        SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(Banner.Mode.OFF); //设置logo关闭
        ApplicationContext context = app.run();
        */

        //第三种，用流模式启动（Fluent Builder Api）
        ApplicationContext context = new SpringApplicationBuilder()
                /*注：我这里传了配置类，然而我配置文件也设置了配置类，两个配置类重名，
                然后报错 “无法注册 bean“应用程序”。已经定义了具有该名称的 bean，并且禁用了覆盖”
                然后，我又在配置文件中配置了，允许bean覆盖，所以报错解决*/
                .sources(Application.class)
                .lazyInitialization(false) //延迟初始化，也可以在配置文件中设置，还可以在类上加@Lazy(value="false")，表示单个类不延迟初始化，就算配置了所有延迟
                .bannerMode(Banner.Mode.OFF)
                .run(args);


        //第一种，调用spring创建的bean对象的方式
        StudentService studentService=(StudentService) context.getBean("studentServiceImpl");
        System.out.println("second... "+studentService.query());
        //第二种，调用spring创建的bean对象的方式
        TeacherService teacherService = context.getBean(TeacherServiceImpl.class);
        System.out.println("third... "+teacherService.query());


        //测试proxyBeanMethods
        Student student = (Student) context.getBean("getStudent");
        Teacher teacher = (Teacher) context.getBean("getTeacher");
        System.out.println(student ==teacher.getStudents().get(0));


    }

    //这是继承了CommandLineRunner接口重写的方法，此方法在bean创建完之后回调
    @Override
    public void run(String... args) {
        System.out.println("bean defined"); //可以打个断点看
        //第三种，调用spring创建的bean对象的方式
        System.out.println("first... "+studentService.query());
    }

    //测试 @SpringBootConfiguration对主启动类的影响，结果没任何影响，不用它主启动类也可以配置bean，@import(TeacherServiceImpl.class)也可以
    @Bean
    public TeacherService teacherService(){
        return new TeacherServiceImpl();
    }
}
