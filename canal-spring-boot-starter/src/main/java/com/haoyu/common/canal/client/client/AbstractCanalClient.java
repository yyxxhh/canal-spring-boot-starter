package com.haoyu.common.canal.client.client;

import com.haoyu.common.canal.client.handler.MessageHandler;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.Message;
import com.alibaba.otter.canal.protocol.exception.CanalClientException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author yang peng
 * @date 2019/3/2619:11
 */

public abstract class AbstractCanalClient implements CanalClient {


    protected volatile boolean flag;


    private Logger log = LoggerFactory.getLogger(AbstractCanalClient.class);


    private Thread workThread;


    private CanalConnector connector;


    protected String filter = StringUtils.EMPTY;


    protected Integer batchSize = 1;


    protected Long timeout = 1L;


    protected TimeUnit unit = TimeUnit.SECONDS;



    private MessageHandler messageHandler;



    @Override
    public void start() {
        log.info("start canal client");
        workThread = new Thread(this::process);
        workThread.setName("canal-client-thread");
        flag = true;
        workThread.start();
    }

    @Override
    public void stop() {
        log.info("stop canal client");
        flag = false;
        if (null != workThread) {
            workThread.interrupt();
        }

    }

    @Override
    public void process() {
        while (flag) {
            try {
                connector.connect();
                connector.subscribe(filter);
                while (flag) {
                    Message message = connector.getWithoutAck(batchSize, timeout, unit);
                    log.info("获取消息 {}", message);
                    long batchId = message.getId();
                    if (message.getId() != -1 && message.getEntries().size() != 0) {
                        try {
                            messageHandler.handleMessage(message);
                            connector.ack(batchId);
                        } catch (Exception e) {
                            log.error("处理消息出现异常，请检查消息处理逻辑", e);
                        }
                    }

                }
            } catch (CanalClientException e) {
                log.error("链接canal server异常", e);
                try {
                    Thread.sleep(1000 * 5);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            } finally {
                connector.disconnect();
            }

        }
    }


    public void setConnector(CanalConnector connector) {
        this.connector = connector;
    }


    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }


    public CanalConnector getConnector() {
        return connector;
    }


    public MessageHandler getMessageHandler() {
        return messageHandler;
    }
}
