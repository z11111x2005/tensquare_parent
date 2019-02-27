package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.UnFriend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnFriendDao extends JpaRepository<UnFriend, String> {
    /**
     * 通过userid和friendid查询非好友信息
     * @param userid
     * @param friendid
     * @return
     */
    UnFriend findByUseridAndFriendid(String userid, String friendid);
}
