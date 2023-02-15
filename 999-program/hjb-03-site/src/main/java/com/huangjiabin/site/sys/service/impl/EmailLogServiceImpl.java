package com.huangjiabin.site.sys.service.impl;

import com.huangjiabin.site.sys.model.EmailConstants;
import com.huangjiabin.site.sys.model.EmailLog;
import com.huangjiabin.site.sys.mapper.EmailLogMapper;
import com.huangjiabin.site.sys.model.Reserve;
import com.huangjiabin.site.sys.service.EmailLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-26
 */
@Service
public class EmailLogServiceImpl extends ServiceImpl<EmailLogMapper, EmailLog> implements EmailLogService {
    @Resource
    private EmailLogMapper emailLogMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Override
    public Boolean sendEmail(Reserve reserve) {
        EmailLog emailLog = new EmailLog();
        emailLog.setReserveId(reserve.getId());
        emailLog.setUserId(reserve.getUserId());
        emailLog.setStatus(0);
        emailLog.setRoutingKey(EmailConstants.EMAIL_ROUTING_KEY_NAME);
        emailLog.setExchange(EmailConstants.EMAIL_EXCHANGE_NAME);
        emailLog.setCount(EmailConstants.MAX_TRY_COUNT);
        emailLog.setTryTime(LocalDateTime.now().plusMinutes(EmailConstants.MSG_TIMEOUT));
        emailLog.setCreateTime(LocalDateTime.now());
        emailLog.setUpdateTime(LocalDateTime.now());
        int result = emailLogMapper.insert(emailLog);//消息入库
        if(result==1){
            //rabbitmq发送消息
//            rabbitTemplate.convertAndSend(EmailConstants.EMAIL_EXCHANGE_NAME,EmailConstants.EMAIL_ROUTING_KEY_NAME,
//                    reserve,new CorrelationData(String.valueOf(emailLog.getEmailLogId())));
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean sendTTLEmail(Reserve reserve, Integer delay) {
        //其实这里跟邮件没关系，主要是看操作是否成功
        EmailLog emailLog = new EmailLog();
        emailLog.setReserveId(reserve.getId()); //设置预定id
        emailLog.setUserId(reserve.getUserId());
        emailLog.setStatus(0);
        emailLog.setRoutingKey(EmailConstants.EMAIL_ROUTING_KEY_NAME);
        emailLog.setExchange(EmailConstants.EMAIL_EXCHANGE_NAME);
        emailLog.setCount(0);
        emailLog.setTryTime(LocalDateTime.now().plusSeconds(delay/1000+5000));
        emailLog.setCreateTime(LocalDateTime.now());
        emailLog.setUpdateTime(LocalDateTime.now());
        int result = emailLogMapper.insert(emailLog);//消息入库
        if(result==1){
            //发送延迟消息
//            rabbitTemplate.convertAndSend("delayed.exchange", "delayed.routingkey", emailLog, msg ->{
//                msg.getMessageProperties().setDelay(delay);
//                return msg;},new CorrelationData(String.valueOf(reserve.getId())));
            return true;
        }else {
            return false;
        }
    }
}
