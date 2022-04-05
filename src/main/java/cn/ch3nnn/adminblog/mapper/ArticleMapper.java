package cn.ch3nnn.adminblog.mapper;

import cn.ch3nnn.adminblog.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;
import java.util.List;

/**
 * @author chentong
 * @Entity cn.ch3nnn.adminblog.entity.TbContent
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {


    /**
     * 插入一条记录
     *
     * @param article Article对象
     * @return
     */
    int insertArticle(Article article);

    /**
     * 查询所有文章阅读量
     *
     * @return 所有文章阅读量
     */
    int selectReadArticleNum();


    /**
     * 获取所有文章评论量
     *
     * @return: 文章评论量
     */
    int selectCommentNum();


    /**
     * 根据文章id获取 文章对象
     *
     * @param id 文章id
     * @return
     */
    Article selectByIdArticle(Integer id);


    /**
     * 搜索文章
     *
     * @param searchField 搜索类型 标题 内容
     * @param keyword     关键字
     * @return 文章列表
     */
    List<Article> searchByTitleOrBody(String searchField, String keyword);


    /**
     * 根据文章id 逻辑删除
     *
     * @param id 文章id
     * @return
     */
    int logicDeleteById(Serializable id);


    /**
     * 根据文章id修改数据
     *
     * @param article article对象
     * @return
     */
    <T> boolean updateByArticleId(Article article);

}





