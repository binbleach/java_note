package com.huangjiabin.springboot.service;

import com.huangjiabin.springboot.model.Msg;

import java.util.List;
import java.util.concurrent.Future;

public interface AsyncMsgService {
    Future<Msg> asyncSendMsg();
    void asyncSendMsg2();
    Object asyncSendMsg3();
}
