package com.fairy.fastdfs.service;

import com.fairy.fastdfs.FastdfsTest;
import com.github.tobato.fastdfs.domain.FileInfo;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.AppendFileStorageClient;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import static org.junit.Assert.assertNotNull;

/**
 * 文件基础操作测试演示
 * 
 * @author tobato
 *
 */
public class StorageClientBasicTest extends FastdfsTest {

    private static Logger LOGGER = LoggerFactory.getLogger(StorageClientBasicTest.class);


    /**
     * 上传
     *
     * @throws Exception
     */
    @Test
    public void upload() throws Exception {
        LOGGER.info("##上传文件..##");
        File file = new File("pig.jpg");
        InputStream is = new FileInputStream(file);
        //获取文件扩展名
        String extendName  = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length());
        //第一个参数group,告诉fdfs将文件上传到那个volume,不传由fdfs自动分配
        //第二个参数文件字节数，fdfs读到这个字节数就结束了
        //第三个参数文件扩展名
        //上传成功后必须将文件所属group和文件名称存储，文件下载时需要使用
        StorePath path = storageClient.uploadFile(null, is, file.length(), extendName);
        is.close();
        assertNotNull(path);
        LOGGER.info("上传文件 result={}", path);

        LOGGER.info("##查询文件信息..##");
        //第一个参数上传文件方法返回的group
        //第二各参数上传文件方法返回的文件名
        FileInfo fileInfo = storageClient.queryFileInfo(path.getGroup(), path.getPath());
        LOGGER.info("查询文件信息 result={}", fileInfo);

    }

    /**
     * 下载
     *
     * @throws Exception
     */
    public void download() throws Exception {
        LOGGER.info("##上传文件..##");
        File file = new File("pig.jpg");
        InputStream is = new FileInputStream(file);
        String extendName  = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length());
        StorePath path = storageClient.uploadFile(null, is, file.length(), extendName);
        is.close();
        assertNotNull(path);
        LOGGER.info("上传文件 result={}", path);

        LOGGER.info("##下载文件..##");
        DownloadByteArray callback = new DownloadByteArray();
        byte[] content = storageClient.downloadFile(path.getGroup(), path.getPath(), callback);
        File dFile = new File("download_" + file.getName());
        LOGGER.info("下载文件={}", dFile.getPath());
        //把文件写到本地
        OutputStream os = new FileOutputStream(dFile);
        os.write(content);
        os.flush();
        os.close();
    }

    /**
     * 删除
     *
     * @throws Exception
     */
    @Test
    public void delete() throws Exception{
        LOGGER.info("##上传文件..##");
        File file = new File("pig.jpg");
        InputStream is = new FileInputStream(file);
        String extendName  = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length());
        StorePath path = storageClient.uploadFile(null, is, file.length(), extendName);
        is.close();
        assertNotNull(path);
        LOGGER.info("上传文件 result={}", path);

        LOGGER.info("##删除主文件..##");
        storageClient.deleteFile(path.getGroup(), path.getPath());
        LOGGER.info("##删除从文件..##");
    }
}
