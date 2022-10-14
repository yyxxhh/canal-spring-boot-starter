package com.haoyu.common.canal.client.factory;


import com.haoyu.common.canal.client.handler.EntryHandler;

import java.util.Set;

/**
 * @author yang peng
 * @date 2019/3/2916:45
 */
public interface IModelFactory<T> {


    <R> R newInstance(EntryHandler entryHandler, T t) throws Exception;


    default <R> R newInstance(EntryHandler entryHandler, T t, Set<String> updateColumn) throws Exception {
        return null;
    }
}
