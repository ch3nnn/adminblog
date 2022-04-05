package cn.ch3nnn.adminblog.mapper;

import cn.ch3nnn.adminblog.entity.FriendLink;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chentong
 * @Entity cn.ch3nnn.adminblog.entity.FriendLink
 */
@Mapper
public interface FriendLinkMapper extends BaseMapper<FriendLink> {

    /**
     * 修改友链
     *
     * @param friendLink 修改友链数据对象
     * @return
     */
    int updateByFriendLink(FriendLink friendLink);

}




