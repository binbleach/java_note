package com.huangjiabin.site.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.huangjiabin.site.sys.mapper.EmailLogMapper;
import com.huangjiabin.site.sys.mapper.ReserveMapper;
import com.huangjiabin.site.sys.model.*;
import com.huangjiabin.site.sys.mapper.HandleMapper;
import com.huangjiabin.site.sys.service.EmailLogService;
import com.huangjiabin.site.sys.service.HandleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huangjiabin.site.sys.service.ReserveService;
import com.huangjiabin.site.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-28
 */
@Service
public class HandleServiceImpl extends ServiceImpl<HandleMapper, Handle> implements HandleService {
    @Resource
    private ReserveMapper reserveMapper;
    @Autowired
    private ReserveService reserveService;
    @Resource
    private HandleMapper handleMapper;
    @Resource
    private UserService userService;
    @Resource
    private JavaMailSender javaMailSender;
    @Resource
    private EmailLogService emailLogService;
    @Value("${spring.mail.username}")
    private String emailUsername;


    public static LocalDateTime createTime=LocalDateTime.now();

    //操作
    @Override
    @Transactional
    public RespBean handle(Handle handle) {
        //更新操作信息
        handle.setHandleTime(createTime);
        int insert = handleMapper.insert(handle);
        if(insert>0){
            //判断操作是否是对预定的，是的话更新预定状态
            if(handle.getHandleTarget()==55){   //55操作预定
                Reserve reserve = reserveMapper.selectById(handle.getTargetId());
                //更新预定状态
                if(handle.getHandleResult()==45){   // 45 通过 46拒绝
                    reserve.setReserveStatus(52);   //52预定成功 53预定失败
                }else if(handle.getHandleResult()==46){
                    reserve.setReserveStatus(53);
                }
                reserveMapper.updateById(reserve);

                //判断是否发送邮箱
                if(handle.getIsEmail()==1){
                    User user = userService.getById(reserve.getUserId());
                    //发邮件
                    SimpleMailMessage message = new SimpleMailMessage();
                    //发件人
                    message.setFrom(emailUsername);
                    //接收人
                    message.setTo(user.getEmail());
                    //邮箱主题
                    message.setSubject("校园公共活动场所管理系统！！！");
                    //邮件正文
                    message.setText("您在"+reserve.getCreateTime()+"预定的"+(reserve.getReserveTarget()==49?"场地":"资源")+"预定"+(handle.getHandleResult()==45?"通过":"被拒绝"));//！！！
                    //发送邮箱
                    javaMailSender.send(message);

                    //更新操作信息，邮箱发送成功
                    handle.setIsEmailSuccess(1);
                    handleMapper.updateById(handle);
                    //设置任务状态为成功
                    UpdateWrapper<EmailLog> uw = new UpdateWrapper<>();
                    uw.set("status",EmailConstants.SUCCESS);
                    uw.eq("reserve_id",reserve.getId());
                    emailLogService.update(uw);
                    return RespBean.success("操作成功",handle);
                }
            }
            return RespBean.success("操作成功");
        }else {
            return RespBean.error("操作失败");
        }
    }

}
