package com.haoyu.common.canal.client.handler.impl;

import com.haoyu.common.canal.client.handler.AbstractFlatMessageHandler;
import com.haoyu.common.canal.client.handler.EntryHandler;
import com.haoyu.common.canal.client.handler.RowDataHandler;
import com.alibaba.otter.canal.protocol.FlatMessage;

import java.util.List;
import java.util.Map;

public class SyncFlatMessageHandlerImpl extends AbstractFlatMessageHandler {



    public SyncFlatMessageHandlerImpl(List<? extends EntryHandler> entryHandlers, RowDataHandler<List<Map<String, String>>> rowDataHandler) {
        super(entryHandlers, rowDataHandler);
    }

    @Override
    public void handleMessage(FlatMessage flatMessage) {
        super.handleMessage(flatMessage);
    }
}
