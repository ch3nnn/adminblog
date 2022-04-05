package cn.ch3nnn.adminblog.service.impl;

import cn.ch3nnn.adminblog.dto.ArticleParam;
import cn.ch3nnn.adminblog.entity.Article;
import cn.ch3nnn.adminblog.entity.ArticleTag;
import cn.ch3nnn.adminblog.entity.Category;
import cn.ch3nnn.adminblog.entity.Comments;
import cn.ch3nnn.adminblog.mapper.*;
import cn.ch3nnn.adminblog.service.ArticleService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.github.dadiyang.equator.Equator;
import com.github.dadiyang.equator.FieldInfo;
import com.github.dadiyang.equator.GetterBaseEquator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author ChenTong
 * @Date 2021/7/28 16:07
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    TagMapper tagMapper;

    @Autowired
    ArticleTagMapper articleTagMapper;

    @Autowired
    CommentsMapper commentsMapper;

    @Autowired
    CategoryMapper categoryMapper;


    @Override
    public Map<String, Object> index() {
        final HashMap<String, Object> infoMap = new LinkedHashMap<>();

        final List<Article> articleList = this.list();
        final List<Comments> commentsList = commentsMapper.selectAll();
        final List<Category> categoryList = categoryMapper.selectList(null);
        final Map<Category, Long> categoryCollect = articleList.stream().collect(Collectors.groupingBy(Article::getCategory, Collectors.counting()));

        final int readArticleNum = articleList.stream().mapToInt(Article::getViews).sum();
        infoMap.put("articleList", articleList);
        infoMap.put("readArticleNum", readArticleNum);
        infoMap.put("articleListNum", articleList.size());
        infoMap.put("commentsListNum", commentsList.size());
        infoMap.put("categoryListNum", categoryList.size());
        infoMap.put("categoryMap", categoryCollect);
        // 评论只获取5条
        infoMap.put("commentsList", commentsList.stream().limit(5).collect(Collectors.toList()));

        return infoMap;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(ArticleParam articleParam, List<Integer> tagsIdList) {
        // 保存文章基本信息数据
        final Article article = new Article();
        BeanUtils.copyProperties(articleParam, article);
        retBool(articleMapper.insertArticle(article));
        // 保存标签中间表
        final List<ArticleTag> articleTagList = new ArrayList<>();
        for (Integer tagId : tagsIdList) {
            final ArticleTag articleTag = new ArticleTag();
            articleTag.setPostId(article.getId());
            articleTag.setTagId(tagId);
            articleTagList.add(articleTag);
        }
        retBool(articleTagMapper.insertArticleTag(articleTagList));

        return true;
    }

    @Override
    public List<Article> list(Wrapper<Article> queryWrapper) {
        return super.list(queryWrapper);
    }

    @Override
    public Article getByIdArticle(Integer id) throws ParseException {
        return articleMapper.selectByIdArticle(id);
    }

    @Override
    public boolean updateByArticleId(ArticleParam articleParam, List<Integer> tagsIdList) {
        try {
            // 先删除已有标签
            articleTagMapper.deleteByArticleId(articleParam.getId());
            // 再重新写入新标签
            for (Integer integer : tagsIdList) {
                final ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(integer);
                articleTag.setPostId(articleParam.getId());
                articleTagMapper.insert(articleTag);
            }
            final Article article = new Article();
            BeanUtils.copyProperties(articleParam, article);
            articleMapper.updateByArticleId(article);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    @Override
    public List<Article> search(String searchField, String keyword) {
        return articleMapper.searchByTitleOrBody(searchField, keyword);
    }

    @Override
    public boolean removeById(Serializable id) {
        return SqlHelper.delBool(articleMapper.logicDeleteById(id));
    }

    @Override
    public String getDiffArticleFields(ArticleParam articleParam, int articleId) {
        /* [{"changed": {"fields": ["body", "created_time", "modified_time", "views"]}}]  */

        Equator equator = new GetterBaseEquator();
        final Article article = articleMapper.selectByIdArticle(articleId);
        // 获取不同的属性
        List<FieldInfo> diffFieldList = equator.getDiffFields(article, articleParam);

        final List<String> fieldNameList = new ArrayList<>();
        final List<Map<String, Object>> resultList = new ArrayList<>();
        HashMap<String, Object> fieldMap = new HashMap<>(10);
        HashMap<String, Object> changedMap = new HashMap<>(10);
        for (FieldInfo field : diffFieldList) {
            fieldNameList.add(field.getFieldName());
        }
        fieldMap.put("fields", fieldNameList);
        changedMap.put("changed", fieldMap);
        resultList.add(changedMap);

        return JSON.toJSONString(resultList);
    }
}
