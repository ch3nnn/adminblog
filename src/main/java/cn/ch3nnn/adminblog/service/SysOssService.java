package cn.ch3nnn.adminblog.service;

import com.qiniu.storage.model.FileInfo;

import java.io.FileInputStream;
import java.util.List;

/**
 * @Author ChenTong
 * @Date 2021/10/9 13:20
 */
public interface SysOssService {

    /**
     * 上传七牛云
     *
     * @param file 文件输入流
     * @return
     */
    String uploadQnFile(FileInputStream file);


    /**
     * 查询oss 所有文件
     *
     * @param prefix 文件名前缀
     * @return
     */
    List<FileInfo> list(String prefix);


    /**
     * 批量删除oss文件
     *
     * @param keyList 文件名称  单次批量请求的文件数量不得超过1000
     * @return
     */
    boolean delete(String[] keyList);


}
