package cn.ch3nnn.adminblog.service;

import cn.ch3nnn.adminblog.entity.Comments;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author chentong
 */
public interface CommentsService extends IService<Comments> {


    /**
     * 查询所有评论数据
     *
     * @return list Comments
     */
    List<Comments> selectAll();

    boolean updateByComments(Comments comments);
}
