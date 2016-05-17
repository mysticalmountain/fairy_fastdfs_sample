# fastdfs使用客户端使用说明
由于多种原因fastdfs客户端并未自己开发，并未使用happyfish100官方提供的fastdfs java client；最终而是选择了tobato写的一个版本。经过了解他的这个版本已经经过生产的验证。为什么呢会选择则个版本呢？原因由几点，１．他的设计比官方的结构清晰合理易扩展；２．已经做好了连接池支持；３．测试用例完善。
后续章节主要介绍两部分内容，１．设计说明；２．使用说明

# 设计说明
分成一下几个模块
- 通讯链接模块
- 报文协议模块
- 操作命令模块
- 对外服务模块

# 使用说明
## 基于spring的程序
1. pom.xml中增肌fastdfs-client依赖
```xml
<dependency>
    <groupId>com.github.tobato</groupId>
    <artifactId>fastdfs-client</artifactId>
    <version>1.25.1-RELEASE</version>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>*</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```
> 该依赖maven中央仓库有

2. 测试上传文件
参考StorageClientBasicTest.java upload方法
> 文件上传成功后客户端需要保存文件存储路径，用户文件下载和删除。

3. 测试下载文件
参考StorageClientBasicTest.java download方法
> 此客户端依赖了spring core和spring boot；如果不使用spring boot可以将该依赖排除，但spring core必须依赖，因为源码内使用了spring的注解，

