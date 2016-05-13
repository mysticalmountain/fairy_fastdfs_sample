package com.fairy.fastdfs.service;

import com.fairy.fastdfs.FastdfsTest;
import com.fairy.fastdfs.util.Config;
import com.fairy.fastdfs.util.RandomTextFile;
import com.github.tobato.fastdfs.domain.MateData;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.exception.FdfsServerException;
import com.github.tobato.fastdfs.proto.ErrorCodeConstants;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Metadata操作演示
 * 
 * @author andongxu
 *
 */
public class StorageClientMetadataTest extends FastdfsTest {

    private static Logger LOGGER = LoggerFactory.getLogger(StorageClientBasicTest.class);

    @Test
    public void testMetadataOperator() {
        LOGGER.info("##上传文件..##");
        RandomTextFile file = new RandomTextFile();
        StorePath path = storageClient.uploadFile(Config.DEFAULT_GROUP, file.getInputStream(),
                file.getFileSize(), file.getFileExtName());
        assertNotNull(path);
        LOGGER.info("上传文件 result={}", path);

        LOGGER.info("##生成Metadata##");
        Set<MateData> firstMateData = new HashSet<MateData>();
        firstMateData.add(new MateData("Author", "wyf"));
        firstMateData.add(new MateData("CreateDate", "2016-01-05"));
        firstMateData.add(new MateData("extend1", "extend filed"));
        storageClient.overwriteMetadata(path.getGroup(), path.getPath(), firstMateData);

        LOGGER.info("##获取Metadata##");
        Set<MateData> fetchMateData = storageClient.getMetadata(path.getGroup(), path.getPath());
        assertEquals(fetchMateData, firstMateData);
        LOGGER.info("Metadata={}", fetchMateData);

        LOGGER.info("##合并Metadata##");
        Set<MateData> secendMateData = new HashSet<MateData>();
        secendMateData.add(new MateData("Author", "tobato"));
        secendMateData.add(new MateData("CreateDate", "2016-01-08"));
        storageClient.mergeMetadata(path.getGroup(), path.getPath(), secendMateData);

        LOGGER.info("##第二次获取Metadata##");
        fetchMateData = storageClient.getMetadata(path.getGroup(), path.getPath());
        assertEquals(fetchMateData, secendMateData);
        LOGGER.info("Metadata={}", fetchMateData);

        LOGGER.info("##删除主文件..##");
        storageClient.deleteFile(path.getGroup(), path.getPath());

        LOGGER.info("##第三次获取Metadata##");
        try {
            fetchMateData = storageClient.getMetadata(path.getGroup(), path.getPath());
            fail("No exception thrown.");
        } catch (Exception e) {
            assertTrue(e instanceof FdfsServerException);
            assertTrue(((FdfsServerException) e).getErrorCode() == ErrorCodeConstants.ERR_NO_ENOENT);
        }
        LOGGER.info("文件删除以后Metadata会自动删除，第三次就获取不到了");
    }

}
