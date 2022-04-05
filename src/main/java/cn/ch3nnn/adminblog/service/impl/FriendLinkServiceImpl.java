package cn.ch3nnn.adminblog.service.impl;

import cn.ch3nnn.adminblog.entity.FriendLink;
import cn.ch3nnn.adminblog.mapper.FriendLinkMapper;
import cn.ch3nnn.adminblog.service.FriendLinkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chentong
 */
@Service
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink>
        implements FriendLinkService {

    @Autowired
    FriendLinkMapper friendLinkMapper;


    @Override
    public boolean updateByFriendLink(FriendLink friendLink) {
        return retBool(friendLinkMapper.updateByFriendLink(friendLink));
    }
}




