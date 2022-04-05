package cn.ch3nnn.adminblog.service.impl;

import cn.ch3nnn.adminblog.entity.Comments;
import cn.ch3nnn.adminblog.mapper.CommentsMapper;
import cn.ch3nnn.adminblog.service.CommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author chentong
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments>
        implements CommentsService {

    @Autowired
    CommentsMapper commentsMapper;

    @Override
    public boolean save(Comments entity) {
        if (entity.getCreatedTime() == null) {
            entity.setCreatedTime(new Date());
        }
        return super.save(entity);
    }

    @Override
    public List<Comments> selectAll() {
        return commentsMapper.selectAll();
    }

    @Override
    public boolean updateByComments(Comments comments) {
        return retBool(commentsMapper.updateByComments(comments));
    }
}




