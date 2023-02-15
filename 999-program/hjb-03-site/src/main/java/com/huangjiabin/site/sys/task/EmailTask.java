package com.huangjiabin.site.sys.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.huangjiabin.site.sys.model.EmailConstants;
import com.huangjiabin.site.sys.model.EmailLog;
import com.huangjiabin.site.sys.model.Reserve;
import com.huangjiabin.site.sys.service.EmailLogService;
import com.huangjiabin.site.sys.service.ReserveService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

//@Component
public class EmailTask {
    @Autowired
    private EmailLogService emailLogService;
    @Autowired
    private ReserveService reserveService;
    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Scheduled(cron = "0/20 * * * * ?")
    public void emailTask(){
        //获取未成功任务
        List<EmailLog> list = emailLogService.list(new QueryWrapper<EmailLog>().eq("status", 0).lt("try_time", LocalDateTime.now()));
        list.forEach(emailLog -> {
            System.out.println("进入任务重新做");
            //如果重复次数超过最大次数，更新状态为投递失败，不再重复
            if(EmailConstants.MAX_TRY_COUNT<=emailLog.getCount()){
                emailLogService.update(new UpdateWrapper<EmailLog>().set("status",2).eq("email_log_id",emailLog.getEmailLogId()));
            }
            emailLogService.update(new UpdateWrapper<EmailLog>().set("count",emailLog.getCount()+1).set("update_time",LocalDateTime.now())
                    .set("try_time",LocalDateTime.now().plusMinutes(EmailConstants.MSG_TIMEOUT)));
            Reserve reserve = reserveService.getById(emailLog.getReserveId());
//            rabbitTemplate.convertAndSend("delayed.exchange","delayed.routingkey",reserve,msg ->{
//                msg.getMessageProperties().setDelay(0);
//                return msg;},new CorrelationData(String.valueOf(emailLog.getEmailLogId())));
        });
    }

}
