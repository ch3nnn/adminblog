package cn.ch3nnn.adminblog.mapper;

import cn.ch3nnn.adminblog.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chentong
 * @Entity cn.ch3nnn.adminblog.entity.Category
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    Integer selectCount();
}




