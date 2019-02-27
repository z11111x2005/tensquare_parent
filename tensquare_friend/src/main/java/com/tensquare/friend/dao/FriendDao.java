package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaRepository<Friend, String> {

    /**
     * 通过userid和friendid查询好友信息
     * @param userid
     * @param friendid
     * @return
     */
    Friend findByUseridAndFriendid(String userid, String friendid);

    /**
     * 通过userid和friendid修改islike
     * @param islike
     * @param userid
     * @param friendid
     */
    @Modifying
    @Query(value = "UPDATE tb_friend SET islike=? WHERE userid=? AND friendid=?", nativeQuery = true)
    void updateIslike(String islike, String userid, String friendid);
}
