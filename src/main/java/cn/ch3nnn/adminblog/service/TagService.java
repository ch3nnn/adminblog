package cn.ch3nnn.adminblog.service;

import cn.ch3nnn.adminblog.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author chentong
 */
public interface TagService extends IService<Tag> {

    /**
     * 根据 ID 选择修改
     *
     * @param tag 实体对象
     * @param ids 标签id
     * @return
     */
    boolean updateTagBatchById(Tag tag, List<Integer> ids);
}

