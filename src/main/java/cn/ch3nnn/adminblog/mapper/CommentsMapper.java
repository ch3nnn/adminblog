package cn.ch3nnn.adminblog.mapper;

import cn.ch3nnn.adminblog.entity.Comments;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author chentong
 * @Entity cn.ch3nnn.adminblog.entity.Comments
 */
@Mapper
public interface CommentsMapper extends BaseMapper<Comments> {

    /**
     * 查询所有评论数据
     *
     * @return
     */
    List<Comments> selectAll();

    Integer updateByComments(Comments comments);
}




