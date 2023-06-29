package com.huangjiabin.seata.service.impl;

import com.huangjiabin.seata.domain.Order;
import com.huangjiabin.seata.mapper.OrderMapper;
import com.huangjiabin.seata.service.AccountService;
import com.huangjiabin.seata.service.OrderService;
import com.huangjiabin.seata.service.StorageService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/*
    内容讲解：
        一、分布式事务的解决方案-2PC：
            2PC即两段提交协议，是将整个事务流程分为两个阶段，P是指准备阶段，C是指提交阶段。
            整个事务过程是由事务管理器和参与者组成的，事务管理器决策整个分布式事务在计算机中关系数据库支持的两阶段提交协议:
                • 准备阶段(Prepare phase): 事务管理器给每个参与者发送Prepare消息，每个数据库参与者在本地执行事务，
                  并写本地的Undo/Redo日志，此时事务没有提交。
                • (Undo日志是记录修改前的数据，用于数据库回滚，Redo日志是记录修改后的数据，用于提交事务后写入数据文件)
                • 提交阶段(commit phase): 如果事务管理器收到了参与者的执行失败或者超时消息时，直接给每个参与者发送回滚(Rollback)消息;
                  否则，发送提交(Commit)消息;参与者根据事务管理器的指令执行提交或者回滚操作，并释放事务处理过程中使用的资源。
        一、AT模式：
            1、概念：是一种无侵入的分布式事务解决方案，在AT 模式下，用户只需关注自己的“业务SQL“，用户的“业务SQL”作为一阶段。
                Seata框架会自动生成事务的二阶段提交和回滚操作。
                注：官网 http://seata.io/zh-cn/docs/overview/what-is-seata.html
            2、整体机制：其实是2PC的演变
                • 一阶段：业务数据和回滚日志记录在同一个本地事务中提交，释放本地锁和连接资源。
                • 二阶段：
                    ◦ 提交异步化，非常快速地完成
                    ◦ 回滚通过一阶段地回滚日志进行反向补偿
            3、seata术语:
                • TC：事务的协调者，就是seata-server
                • TM：事务管理器，就是开启事务的携带@GlobalTransactional的
                • RM：资源管理器，就是连接数据库的事务参与者
                • beforeImage：前置镜像，在数据更新前镜像   • afterImage：后置镜像，数据更新后的镜像
                • distributed_lock表：用于 seata-server 异步任务调度，是seata 1.5.x新增的
                • undo_log表：在每个client端数据库中，存在rollback_info字段记录着beforeImage和afterImage
                • lock_table：锁表  • global_table：全局事务表  • branch_table：分支事务表
            4、流程：
                1、TM开启分布式事务（TM向TC注册全局事务记录）
                2、按业务场景，编排数据库、服务等事务内资源（RM 向 TC 汇报资源准备状态 ）
                3、TM 结束分布式事务，事务一阶段结束（TM 通知 TC 提交/回滚分布式事务）
                4、TC 汇总事务信息，决定分布式事务是提交还是回滚
                5、TC 通知所有 RM 提交/回滚 资源，事务二阶段结束
        三、搭建seata-server：
            1、seata-server整合nacos，重要配置放到nacos中,服务配置整合nacos即可：seata.config、seata.registry。
            2、搭建seata-server所需数据库：seata
                模板：seata/script/server/db/mysql.sql
                或者官网：https://github.com/seata/seata/blob/1.5.2/script/server/db/mysql.sql
            3、在nacos中新增配置，其中包含seata-server和seata-client的配置。
                1）模板：seata/script/config-center/config.txt
                   或者官网：https://github.com/seata/seata/blob/1.5.2/script/config-center/config.txt
                2）data id：seataServer.properties、Group：SEATA_GROUP、配置格式：properties
            4、启动seata-server：双击seata/bin/seata-server.bat 即可
        四、搭建seata-client:
            1、seata-client包含服务：order、account、storage。
            2、表每个服务都拥有独立的数据库，在每个数据库中加上undo_log表
                • 模板：https://github.com/seata/seata/blob/1.5.2/script/client/at/db/mysql.sql
            3、配置连接seata-server：
                • 也是配置seata.config、seata.registry
                • 模板：https://github.com/seata/seata/blob/1.5.2/script/client/spring/application.yml
        五、seata的使用：
            在服务接口出加上@GlobalTransactional即可
        六：seata的测试：
            在account服务中做一个延迟造成feign调用超时，测试结果如果order表没添加新的数据，且account表和storage表数据没变即成功
        七：注意事项：
            1、seata-server1.5.2版本要对应添加2.2.9版本的alibaba,具体请看order服务的pom文件有降解。
            2、在order服务的mapper.xml里的insert里，对于自增的id,不能写成null,否则会使得回滚记录成id_id的形式，
               造成脏数据，使得回滚失败。解决：insert的时候忽略id字段即可。
            3、必须要配置代理数据源。seata可以手动配置和自动配置（二者不能并存）
               手动配置时需要取消数据源的自动创建：@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
            4、注意nacos的命名空间
            5、default_tx_group、default 这两个属性值可以改变但必须保证server和client端一致。
                server中：
                    service.vgroupMapping.default_tx_group=default
                    service.default.grouplist=127.0.0.1:8091
                    seata.registry.nacos.cluster=default
                client中：
                    seata.tx-service-group =default_tx_group
                    seata.service.vgroup-mapping.default_tx_group =default
*/
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    OrderMapper orderMapper;
    @Resource
    StorageService storageService;
    @Resource
    AccountService accountService;

    /**
     * 创建订单->调用库存服务扣减库存->调用账户服务扣减账户余额->修改订单状态
     * 简单说：
     * 下订单->减库存->减余额->改状态
     */
    @Override
    @GlobalTransactional
    public void create(Order order) {
        log.info("Seata全局事务id=================>{}", RootContext.getXID());
        log.info("------->下单开始");
        //本应用创建订单
        orderMapper.create(order);

        //远程调用库存服务扣减库存
        log.info("------->order-service中扣减库存开始");
        storageService.decrease(order.getProductId(),order.getCount());
        log.info("------->order-service中扣减库存结束");

        //远程调用账户服务扣减余额
        log.info("------->order-service中扣减余额开始");
        accountService.decrease(order.getUserId(),order.getMoney());
        log.info("------->order-service中扣减余额结束");

        //修改订单状态为已完成
        log.info("------->order-service中修改订单状态开始");
        orderMapper.update(order.getUserId(),0);
        log.info("------->order-service中修改订单状态结束");

        log.info("------->下单结束");
        log.info("Seata全局事务id=================>{}",RootContext.getXID());
    }
}
