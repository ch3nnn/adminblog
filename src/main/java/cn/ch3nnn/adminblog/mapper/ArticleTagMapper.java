package cn.ch3nnn.adminblog.mapper;

import cn.ch3nnn.adminblog.entity.ArticleTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chentong
 * @Entity cn.ch3nnn.adminblog.entity.ArticleTag
 */
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {


    /**
     * 根据文章id删除
     *
     * @param articleId 文章id
     * @return
     */
    int deleteByArticleId(@Param("articleId") int articleId);


    /**
     * 插入文章标签中间表数据
     *
     * @param articleTagList 文章标签中间表对象list
     * @return
     */
    int insertArticleTag(@Param("articleTagList") List<ArticleTag> articleTagList);

}




