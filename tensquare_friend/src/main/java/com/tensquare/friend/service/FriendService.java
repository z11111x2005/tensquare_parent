package com.tensquare.friend.service;

import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.UnFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.UnFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDao friendDao;
    @Autowired
    private UnFriendDao unFriendDao;

    public int addFriend(String userid, String friendid) {
        // 1.先判断userid到friendid是否有数据,有就是重复添加好友,返回0
        Friend friend = friendDao.findByUseridAndFriendid(userid, friendid);
        if (null != friend) {
            return 0;
        }
        // 2.直接添加,让好友表中userid到friendid方向的type为0
        friend = new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);
        // 3.判断从friendid到userid是否有数据,如果有,把双方状态改为1
        if (friendDao.findByUseridAndFriendid(friendid, userid) != null) {
            friendDao.updateIslike("1", userid, friendid);
            friendDao.updateIslike("1", friendid, userid);
        }
        return 1;
    }

    public int addUnFriend(String userid, String friendid) {
        // 1.判断是否已经是非好友
        UnFriend unFriend = unFriendDao.findByUseridAndFriendid(userid, friendid);
        if(null != unFriend){
            return 0;
        }
        // 2.添加非好友
        unFriend = new UnFriend();
        unFriend.setUserid(userid);
        unFriend.setFriendid(friendid);
        unFriendDao.save(unFriend);
        return 1;
    }
}
