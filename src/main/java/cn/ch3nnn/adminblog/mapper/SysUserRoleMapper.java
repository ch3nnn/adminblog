package cn.ch3nnn.adminblog.mapper;

import cn.ch3nnn.adminblog.entity.SysRole;
import cn.ch3nnn.adminblog.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Entity cn.ch3nnn.adminblog.entity.SysUserRole
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    Integer deleteByUserId(int userId);


    List<SysRole> selectByUserId(Integer userId);

}




