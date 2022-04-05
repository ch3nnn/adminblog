package cn.ch3nnn.adminblog.service.impl;

import cn.ch3nnn.adminblog.entity.Category;
import cn.ch3nnn.adminblog.mapper.CategoryMapper;
import cn.ch3nnn.adminblog.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author chentong
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {

}




