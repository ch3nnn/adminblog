package cn.ch3nnn.adminblog.service.impl;

import cn.ch3nnn.adminblog.entity.Tag;
import cn.ch3nnn.adminblog.mapper.TagMapper;
import cn.ch3nnn.adminblog.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chentong
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
        implements TagService {

    @Autowired
    TagMapper tagMapper;


    @Override
    public boolean updateTagBatchById(Tag tag, List<Integer> ids) {
        tagMapper.updateTagBatchById(tag, ids);

        return false;
    }
}




