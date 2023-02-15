package com.huangjiabin.springboot;

import com.huangjiabin.springboot.domain.Student;
import com.huangjiabin.springboot.service.StudentService;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/*
	内容讲解：
		1、切换 druid：springboot默认采用比druid更快的HikariCp数据源。想要druid怎么切换：
			1）在pom文件导入mybatis依赖时排除数据源。 2）导入druid依赖。 3）非druid-spring-boot-starter还需要再yml文件中配置
		2、使用 jasypt：用于加密信息的使用：
			1）导入依赖。 2）在yml中配置密钥信息。 3）注入stringEncryptor后即可加密解密，密文要配合配置的前缀后缀使用。
*/
public class Application implements ApplicationRunner {

	@Autowired
	private StringEncryptor stringEncryptor;

	@Autowired
	private StudentService studentService;

	//main方法的类也是配置类，也是ioc容器里的bean对象
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		//用jdk13，jdk8的话会报错提示JCE缺少的让你安装
		//加密
		String username = stringEncryptor.encrypt("root");
		System.out.println("username:"+username);
		//解密，在配置文件中这样写防止别人看到数据库密码啥的，解密写:
		// spring.datasource.username=ENC(Nvq13J34VccYl+4+WJUrlbg2ZhwWmy9tWwkcj6XVC9EJzknxeHqrHc5NCkSOzkid)
		//也可以以不写ENC，自己在配置文件中配置，前缀后缀
		String decusername=stringEncryptor.decrypt(username);
		System.out.println(decusername);

		String password = stringEncryptor.encrypt("1220.dbggl.mysql");
		System.out.println("password:"+password);
		String decpassword=stringEncryptor.decrypt(password);
		System.out.println(decpassword);

		Student student = studentService.queryStudentById(1);
		System.out.println(student.toString());
	}
}
