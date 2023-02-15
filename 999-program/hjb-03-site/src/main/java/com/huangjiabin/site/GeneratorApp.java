package com.huangjiabin.site;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
    mybatis-plus代码生成器
    可以在官网查看其他配置
*/
public class GeneratorApp {
    public static void main(String[] args) {
        String moduleName = scanner("模块名");
        String tableName = scanner("请输入表明（多个用逗号分割，或者按前缀sys*）");
        //代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        //获得当前项目路径:D:\Java\IDEA_WorkSpece\SpringBoot-Project
        String projectPath = System.getProperty("user.dir")+"/38-springboot-site";
        //设置生成路径
        gc.setOutputDir(projectPath + "/src/main/java");
        //作者
        gc.setAuthor("huangjiabin");
        //代码生成是不是要打开所在文件夹
        gc.setOpen(false);
        //生成 Swagger2 注解
        gc.setSwagger2(true);
        //会在mapper.xml生成一个基础的<ResultMap>映射所有的字段
        gc.setBaseResultMap(true);
        //每次生成同文件覆盖
        gc.setFileOverride(true);
        //这里可以设置date的类型只有日期还是只有时间
        //gc.setDateType(DateType.ONLY_DATE);
        //设置实体类包名
        //设置实体类的名字，默认 %sEntity 生成 UserEntity，这里设置成%s生成User
        gc.setEntityName("%s");
        //设置mapper接口名，默认是 %sDao 生成UserDao
        gc.setMapperName("%sMapper");
        //设置.xml映射文件名，默认是 %sDao 生成 UserDao.xml
        gc.setXmlName("%sMapper");
        //设置Service接口名，默认是%sBusiness 生成 UserBusiness
        gc.setServiceName("%sService");
        //设置ServiceImpl接口实现类名，默认是 %sBusinessImpl，生成一样的，自己推算吧
        gc.setServiceImplName("%sServiceImpl");
        //默认%sAction
        gc.setControllerName("%sController");

        //将全局配置设置到AutoGenerator
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://127.0.0.1/activity_place?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("1220.dbggl.mysql");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        //设置包名
        pc.setParent("com.huangjiabin.site");
        //单模块哈哈哈没有模块名
        //pc.setModuleName(scanner("模块名"));
        pc.setModuleName(moduleName);
        pc.setEntity("model");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker，我的不是
        //String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";


        // 自定义输出配置，xml文件 输出到resource
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录，自定义目录用");
                if (fileType == FileType.MAPPER) {
                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                    return !new File(filePath).exists();
                }
                // 允许生成模板文件
                return true;
            }
        });
        */
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        //把已有的xml生成失效，不然会生成两个xml一个在resource一个在mapper
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);


        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        //生成表名策略：这里设置是 下划线转驼峰
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //生成列名策略：下划线转驼峰
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //这里咱没有父类
        //strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        //生成lombok注解
        strategy.setEntityLombokModel(true);
        //controller是否用RestController
        strategy.setRestControllerStyle(true);
        // Controller公共父类
        //strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
        //strategy.setSuperEntityColumns("id");
        //strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        if(tableName.indexOf('*')>0){
            strategy.setLikeTable(new LikeTable(tableName.replace('*','_')));
        }else {
            String[] tableName1=tableName.split(",");
            for (String s : tableName1) {
                strategy.setInclude(s);
            }
        }
        //要生成的表名，多个要逗号分开
        //strategy.setInclude("dict_list","dict_data");
        //如果要前缀生成
        //strategy.setLikeTable(new LikeTable("sys_"));
        //拦截路径驼峰转连接符false下划线转驼峰，ture下划线转横杠-：比如true--> @RequestMapping("/模块名/sys-role")
        strategy.setControllerMappingHyphenStyle(false);
        //生成表替换前缀，生成的表不带sys_
        //strategy.setTablePrefix(pc.getModuleName() + "_");
        strategy.setTablePrefix("sys_");
        mpg.setStrategy(strategy);
        //默认用velocity模板引擎，这里不设置
        //mpg.setTemplateEngine(new FreemarkerTemplateEngine());


        //进行生成
        mpg.execute();

    }
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        //判断用户是否输入
        if (scanner.hasNext()) {
            //拿到输入内容
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

}
