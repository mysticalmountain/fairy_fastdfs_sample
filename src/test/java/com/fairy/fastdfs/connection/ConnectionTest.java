package com.fairy.fastdfs.connection;

import com.fairy.fastdfs.util.Config;
import com.github.tobato.fastdfs.conn.Connection;
import com.github.tobato.fastdfs.conn.DefaultConnection;
import org.junit.Assert;
import org.junit.Test;

import java.net.InetSocketAddress;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * socket连接测试
 * 
 * @author andongxu
 *
 */
public class ConnectionTest  {

     @Test
    public void testClose() {
        // 创建连接
        Connection conn = createConnection();
        // 正常连接
        assertFalse(conn.isClosed());
        conn.close();
        Assert.assertTrue(conn.isClosed());
    }

     @Test
    public void testCheck() {
        // 创建连接测试
        Connection conn = createConnection();
        System.out.println("当前连接状态" + conn.isValid());
        conn.close();
    }

    /**
     * 创建连接
     * 
     * @return
     */
    private Connection createConnection() {
        InetSocketAddress address = new InetSocketAddress(Config.host, Config.port);
        return new DefaultConnection(address, Config.soTimeout, Config.connectTimeout, null);
    }

}
