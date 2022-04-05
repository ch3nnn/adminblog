package cn.ch3nnn.adminblog.mapper;

import cn.ch3nnn.adminblog.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity cn.ch3nnn.adminblog.entity.TbTag
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 根据 ID 选择修改
     *
     * @param tag 实体对象
     * @param ids 标签id
     * @return
     */
    int updateTagBatchById(@Param("tag") Tag tag, @Param("ids") List<Integer> ids);


}




