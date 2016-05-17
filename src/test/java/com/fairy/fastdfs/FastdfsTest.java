package com.fairy.fastdfs;

import com.fairy.fastdfs.util.Config;
import com.github.tobato.fastdfs.conn.FdfsConnectionPool;
import com.github.tobato.fastdfs.conn.PooledConnectionFactory;
import com.github.tobato.fastdfs.conn.TrackerConnectionManager;
import com.github.tobato.fastdfs.service.AppendFileStorageClient;
import com.github.tobato.fastdfs.service.DefaultAppendFileStorageClient;
import com.github.tobato.fastdfs.service.DefaultTrackerClient;
import com.github.tobato.fastdfs.service.TrackerClient;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * 测试驱动类
 *
 * @author andongxu
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {FastdfsTest.class})
@Configuration
public class FastdfsTest {

    @Bean
    public FdfsConnectionPool fdfsConnectionPool() {
        PooledConnectionFactory factory = new PooledConnectionFactory();
        factory.setConnectTimeout(Config.connectTimeout);
        factory.setSoTimeout(Config.soTimeout);
        return new FdfsConnectionPool(factory);
    }

    @Bean
    public TrackerConnectionManager trackerConnectionManager(FdfsConnectionPool fdfsConnectionPool ) {
        TrackerConnectionManager trackerConnectionManager = new TrackerConnectionManager(fdfsConnectionPool);
        List<String> trackerList = new ArrayList();
        trackerList.add(Config.address);
        trackerConnectionManager.setTrackerList(trackerList);
        trackerConnectionManager.initTracker();
        return trackerConnectionManager;
    }

    @Bean
    public TrackerClient trackerClient() {
        DefaultTrackerClient trackerClient =  new DefaultTrackerClient();
        return trackerClient;
    }

    @Bean
    public AppendFileStorageClient storageClient(TrackerClient trackerClient) {
        DefaultAppendFileStorageClient storageClient =  new DefaultAppendFileStorageClient();
        storageClient.setTrackerClientService(trackerClient);
        return storageClient;
    }

    @Autowired
    protected ApplicationContext context;

    @Autowired
    protected AppendFileStorageClient storageClient;

    @Autowired
    protected TrackerClient trackerClient;
}
