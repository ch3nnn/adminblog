package cn.ch3nnn.adminblog.mapper;

import cn.ch3nnn.adminblog.dto.UserUserDto;
import cn.ch3nnn.adminblog.entity.UserUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity cn.ch3nnn.adminblog.entity.UserUser
 */
@Mapper
public interface UserUserMapper extends BaseMapper<UserUser> {

    /**
     * 获取所有用户对象
     *
     * @return 用户对象
     */
    List<UserUser> findAllUserUser();

    /**
     * 根据用户名称查找用户对象
     *
     * @param username 用户名
     * @return 用户对象
     */
    UserUser getByUsername(@Param("username") String username);


    List<UserUserDto> listAll();

    int updateByUserUser(UserUser userUser);

    /**
     * 根据用户 id 修改密码
     *
     * @param password 密码
     * @param userId   用户id
     * @return
     */
    int updatePasswordByUserId(@Param("password") String password, @Param("id") Integer userId);


}




