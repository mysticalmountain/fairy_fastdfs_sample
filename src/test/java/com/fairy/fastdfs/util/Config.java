package com.fairy.fastdfs.util;

import java.nio.charset.Charset;

/**
 * Created by andongxu on 16-5-12.
 */
public class Config {

    public static final String address = "127.0.0.1:22122";

    //设置socket调用InputStream读数据的超时时间，以毫秒为单位，如果超过这个时候，会抛出java.net.SocketTimeoutException
    public  static final int soTimeout = 500;

    //java.net.Socket connect 方法设置超时时间，以毫秒为单位
    public static final int connectTimeout = 500;

    public static final String host = "127.0.0.1";

    public static final int port = 22122;

    public static final String group = "group1";

    public static final String DEFAULT_GROUP = "group1";

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

}
