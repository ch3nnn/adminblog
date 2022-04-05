package cn.ch3nnn.adminblog.service;

import cn.ch3nnn.adminblog.entity.FriendLink;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author chentong
 */
public interface FriendLinkService extends IService<FriendLink> {


    /**
     * 更新数据
     *
     * @param friendLink 修改数据对象
     * @return
     */
    boolean updateByFriendLink(FriendLink friendLink);
}
