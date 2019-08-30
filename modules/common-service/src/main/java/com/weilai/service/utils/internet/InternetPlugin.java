package com.weilai.service.utils.internet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: wangzhen
 * @Date: 2018/11/18 22:02
 */
public interface InternetPlugin {
    Logger logger = LoggerFactory.getLogger(InternetPlugin.class);

    /**
     * 获取网络链接方式
     * @return
     */
    String getInternetLinkWay();

    /**
     * 发送消息报文
     * @param requestMethod 请求方式
     * @param messagePackets 消息报文
     * @return
     */
    String sendMessagePackets(String requestMethod, Object messagePackets);

    /**
     * 关闭链接对象
     */
    void close();
}
