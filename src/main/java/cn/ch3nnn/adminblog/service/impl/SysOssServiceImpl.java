package cn.ch3nnn.adminblog.service.impl;

import cn.ch3nnn.adminblog.config.CloudStorageConfig;
import cn.ch3nnn.adminblog.service.SysOssService;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author ChenTong
 * @Date 2021/10/9 13:22
 */
@Service
public class SysOssServiceImpl implements SysOssService {


    /**
     * 七牛文件上传管理器
     */
    private final UploadManager uploadManager;
    private final String token;
    /**
     * 七牛认证管理
     */
    private final Auth auth;
    protected CloudStorageConfig cloudStorageConfig;

    public SysOssServiceImpl(CloudStorageConfig cloudStorageConfig) {
        this.cloudStorageConfig = cloudStorageConfig;
        // 构造一个带指定Zone对象的配置类, 注意这里的Zone.zone0需要根据主机选择
        Configuration cfg = new Configuration(Region.region0());
        this.uploadManager = new UploadManager(cfg);
        this.auth = Auth.create(cloudStorageConfig.getQiniuAccessKey(), cloudStorageConfig.getQiniuSecretKey());
        // 根据命名空间生成的上传token
        this.token = auth.uploadToken(cloudStorageConfig.getQiniuBucketName());
    }

    @Override
    public String uploadQnFile(FileInputStream file) {
        try {
            // 上传图片文件
            Response res = uploadManager.put(file, null, token, null, null);
            if (!res.isOK()) {
                throw new RuntimeException("上传七牛出错：" + res.toString());
            }
            // 解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(res.bodyString(), DefaultPutRet.class);
            // 这个returnPath是获得到的外链地址,通过这个地址可以直接打开图片
            return cloudStorageConfig.getQiniuDomain() + "/" + putRet.key;
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public List<FileInfo> list(String prefix) {
        final List<FileInfo> fileInfos = new ArrayList<>();
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        Auth auth = Auth.create(cloudStorageConfig.getQiniuAccessKey(), cloudStorageConfig.getQiniuSecretKey());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        // 列举空间文件列表
        // limit 每次迭代的长度限制，最大1000，推荐值 1000
        // delimiter 指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(
                cloudStorageConfig.getQiniuBucketName(), prefix, 1000, "");
        while (fileListIterator.hasNext()) {
            // 处理获取的file list结果
            final FileInfo[] fileInfoArrays = fileListIterator.next();
            // 将七牛云返回时间戳改成正常时间戳 按修改时间排序
            final List<FileInfo> collect = Arrays.stream(fileInfoArrays)
                    .peek(fileInfo -> fileInfo.putTime = (fileInfo.putTime / 10000))
                    .sorted(Comparator.comparing(fileInfo -> fileInfo.putTime))
                    .collect(Collectors.toList());

            fileInfos.addAll(collect);
        }
        // 因为FileInfo没有get方法 所以将整个list反转
        Collections.reverse(fileInfos);

        return fileInfos;


    }

    @Override
    public boolean delete(String[] keyList) {
        // 构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        Auth auth = Auth.create(cloudStorageConfig.getQiniuAccessKey(), cloudStorageConfig.getQiniuSecretKey());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
            batchOperations.addDeleteOp(cloudStorageConfig.getQiniuBucketName(), keyList);
            Response response = bucketManager.batch(batchOperations);
            BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
            // 判断删除是否成功
            List<Integer> resultCode = new LinkedList<>();
            for (BatchStatus batchStatus : batchStatusList) {
                resultCode.add(batchStatus.code);
            }
            final HashSet<Integer> hashSet = new HashSet<>(resultCode);
            return hashSet.size() == 1;
        } catch (QiniuException ex) {
            System.err.println(ex.response.toString());
        }
        return false;
    }

}
