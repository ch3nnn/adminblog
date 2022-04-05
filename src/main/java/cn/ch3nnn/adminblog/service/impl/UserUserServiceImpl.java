package cn.ch3nnn.adminblog.service.impl;

import cn.ch3nnn.adminblog.dto.UserUserDto;
import cn.ch3nnn.adminblog.entity.SecurityUser;
import cn.ch3nnn.adminblog.entity.SysRole;
import cn.ch3nnn.adminblog.entity.SysUserRole;
import cn.ch3nnn.adminblog.entity.UserUser;
import cn.ch3nnn.adminblog.mapper.SysUserRoleMapper;
import cn.ch3nnn.adminblog.mapper.UserUserMapper;
import cn.ch3nnn.adminblog.service.UserUserService;
import cn.ch3nnn.adminblog.utils.DjangoPasswordUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author chentong
 */
@Service
public class UserUserServiceImpl extends ServiceImpl<UserUserMapper, UserUser>
        implements UserUserService {

    @Autowired
    UserUserMapper userUserMapper;

    @Autowired
    SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<UserUser> list() {
        return userUserMapper.findAllUserUser();
    }

    @Override
    public List<UserUserDto> listAll() {
        return userUserMapper.listAll();
    }

    @Override
    public UserUser getByUsername(String username) {
        return userUserMapper.getByUsername(username);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public boolean updateByUserUser(UserUserDto userUserDto) {
        final String password = userUserMapper.selectById(userUserDto.getId()).getPassword();
        // 校验是否修改密码
        if (userUserDto.getPassword() != null && !(password.equals(userUserDto.getPassword()))) {
            userUserDto.setPassword(new DjangoPasswordUtils().encode(userUserDto.getPassword(), DjangoPasswordUtils.SALT));
        }
        // 判断是否修改用户角色
        if (userUserDto.getRoleIds() != null && userUserDto.getRoleIds().size() != 0) {
            sysUserRoleMapper.deleteByUserId(userUserDto.getId());
            for (Integer roleId : userUserDto.getRoleIds()) {
                final SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(userUserDto.getId());
                sysUserRole.setRoleId(roleId);
                sysUserRoleMapper.insert(sysUserRole);
            }
        } else if (userUserDto.getRoleIds() != null && userUserDto.getRoleIds().size() == 0) {
            // role传空list删除所有该用户下角色
            sysUserRoleMapper.deleteByUserId(userUserDto.getId());
        }

        final UserUser userUser = new UserUser();
        BeanUtils.copyProperties(userUserDto, userUser);

        return retBool(userUserMapper.updateByUserUser(userUser));
    }

    @SneakyThrows
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public boolean save(UserUser entity) {

        entity.setPassword(new DjangoPasswordUtils().encode(entity.getPassword(), DjangoPasswordUtils.SALT));
        entity.setAvatarUrl("avatar/user/default_" + new Random().nextInt(3) + ". jpeg ");
        entity.setIsActive(true);
        entity.setIsStaff(entity.getIsSuperuser());
        entity.setDateJoined(new Date());
        // 判断对象属性null改为空
        // https://blog.csdn.net/weixin_41997225/article/details/103281294?spm=1001.2014.3001.5501
        // https://blog.csdn.net/qq_27657429/article/details/104497062
        final Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
            String type = field.getGenericType().toString();
            if ("class java.lang.String".equals(type)) {
                final Method method = entity.getClass().getMethod("get" + name);
                String value = (String) method.invoke(entity);
                // 设置set方法
                if (value == null) {
                    final Method setMethod = entity.getClass().getDeclaredMethod("set" + name, field.getType());
                    setMethod.invoke(entity, "");
                }
            }
        }
        return super.save(entity);
    }

    @Override
    public SecurityUser getSecurityByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中取出用户信息
        final UserUser byUsername = userUserMapper.getByUsername(username);
        // 判断用户是否存在
        if (byUsername != null) {
            // 返回UserDetails实现类
            return new SecurityUser(byUsername);
        } else {
            throw new UsernameNotFoundException("用户名不存在！");
        }
    }

    @Override
    public boolean updatePasswordByUserId(String password, Integer userId) {
        return retBool(userUserMapper.updatePasswordByUserId(password, userId));
    }

    @Override
    public List<SysRole> getSysRoleByUserId(Integer userId) {

        return sysUserRoleMapper.selectByUserId(userId);
    }
}




