package com.fairy.fastdfs.service;

import com.fairy.fastdfs.util.Config;
import com.fairy.fastdfs.FastdfsTest;
import com.github.tobato.fastdfs.domain.GroupState;
import com.github.tobato.fastdfs.domain.StorageNode;
import com.github.tobato.fastdfs.domain.StorageState;
import com.github.tobato.fastdfs.exception.FdfsServerException;
import com.github.tobato.fastdfs.proto.ErrorCodeConstants;
import com.github.tobato.fastdfs.service.TrackerClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.*;

/**
 * unit test for TrackerClientService
 * 
 * @author andongxu
 *
 */

public class TrackerClientTest extends FastdfsTest{

    /** 日志 */
    private static Logger LOGGER = LoggerFactory.getLogger(TrackerClientTest.class);


    @Test
    public void testGetStoreStorage() {
        LOGGER.info("testGetStoreStorage..");
        StorageNode client = trackerClient.getStoreStorage();
        assertNotNull(client.getInetSocketAddress());
        LOGGER.info("result={}", client);

    }

    @Test
    public void testGetStoreStorageByGroup() {
        LOGGER.info("testGetStoreStorageByGroup..");
        StorageNode client = trackerClient.getStoreStorage(Config.group);
        assertNotNull(client.getInetSocketAddress());
        LOGGER.info("result={}", client);
    }

    @Test
    public void testListGroups() {
        LOGGER.info("testListGroups..");
        List<GroupState> list = trackerClient.listGroups();
        assertNotNull(list);
        LOGGER.info("result={}", list);
    }

    @Test
    public void testListStoragesByGroup() {
        LOGGER.info("testListStoragesByGroup..");
        List<StorageState> list = trackerClient.listStorages(Config.group);
        assertNotNull(list);
        for (StorageState ss :  list) {
            LOGGER.info("result={}", ss);
        }
//        LOGGER.info("result={}", list);
    }

    @Test
    public void testListStoragesByGroupAndIp() {
        LOGGER.info("testListStoragesByGroupAndIp..");
        List<StorageState> list = trackerClient.listStorages(Config.group, "192.168.1.24");
        assertNotNull(list);
        LOGGER.info("result={}", list);
    }

    @Test
    public void testDeleteStorage() {
        LOGGER.info("testDeleteStorage..");
        try {
            trackerClient.deleteStorage(Config.group, "192.168.0.102");
            fail("No exception thrown.");
        } catch (Exception e) {
            assertTrue(e instanceof FdfsServerException);
            assertTrue(((FdfsServerException) e).getErrorCode() == ErrorCodeConstants.ERR_NO_EBUSY);
        }
        LOGGER.info("testDeleteStorage..done");
    }

}
