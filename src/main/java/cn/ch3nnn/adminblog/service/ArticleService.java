package cn.ch3nnn.adminblog.service;

import cn.ch3nnn.adminblog.dto.ArticleParam;
import cn.ch3nnn.adminblog.entity.Article;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @Author ChenTong
 * @Date 2021/7/28 16:00
 */
public interface ArticleService extends IService<Article> {

    /**
     * @return
     */
    Map<String, Object> index();


    /**
     * 保存文章
     *
     * @param articleParam 文章对象
     * @param tagsIdList   标签id
     * @return
     */
    boolean save(ArticleParam articleParam, List<Integer> tagsIdList);

    /**
     * 获取所有文章列表
     *
     * @param queryWrapper Article
     * @return 文章列表
     */
    @Override
    List<Article> list(Wrapper<Article> queryWrapper);

    /**
     * 根据文章id获取 文章对象
     *
     * @param id 文章id
     * @return
     * @throws ParseException
     */
    Article getByIdArticle(Integer id) throws ParseException;


    /**
     * 根据id 修改
     *
     * @param articleParam article对象
     * @param tagsIdList   标签id
     * @return 是否修改成功
     */
    boolean updateByArticleId(ArticleParam articleParam, List<Integer> tagsIdList);

    /**
     * 搜索文章
     *
     * @param searchField 搜索类型 标题 内容
     * @param keyword     关键字
     * @return 文章列表
     */
    List<Article> search(@Param("search_field") String searchField, @Param("keyword") String keyword);

    /**
     * 根据文章id 逻辑删除
     *
     * @param id 文章id
     * @return 是否更改成功
     */
    @Override
    boolean removeById(Serializable id);


    /**
     * 获取更新文章字段名称
     *
     * @param articleVo 修改文章vo对象
     * @param articleId 原文章对象
     * @return
     */
    String getDiffArticleFields(ArticleParam articleParam, int articleId);

}
