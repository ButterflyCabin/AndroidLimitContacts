package com.yehu.androidlimitcontacts.bean;

import java.io.Serializable;

/**
 * 创建日期：2017/12/20 16:26
 *
 * @author yehu
 *         类说明：
 */
public class Page<T> implements Serializable {

    private static final long serialVersionUID = -5782425897390028949L;
    public long firstTimeStamp;
    public int count;
    public int page;
    public int limit;
    public int pages;
    public T data;

    @Override
    public String toString() {
        return "Page{" +
                "firstTimeStamp=" + firstTimeStamp +
                ", count=" + count +
                ", page=" + page +
                ", limit=" + limit +
                ", pages=" + pages +
                ", data=" + data +
                '}';
    }
}
