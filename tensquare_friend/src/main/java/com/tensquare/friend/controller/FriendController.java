package com.tensquare.friend.controller;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private FriendService friendService;
    @Autowired
    private UserClient userClient;

    /**
     * 添加好友或者添加非好友
     *
     * @param friendid
     * @param type
     * @return
     */
    @GetMapping("/like/{friendid}/{type}")
    public Result addFriend(@PathVariable("friendid") String friendid,
                            @PathVariable("type") String type) {
        // 1.验证是否登录,并且拿到当前登录的用户id
        Claims claims = (Claims) request.getAttribute("claims_user");
        if (null == claims) {
            return new Result(false, StatusCode.LOGINERROR, "权限不足");
        }
        // 得到用户id
        String userid = claims.getId();
        // 2.判断是添加好友还是添加非好友
        if (StringUtils.isNotEmpty(type)) {
            if (type.equals("1")) {
                // 添加好友
                int flag = friendService.addFriend(userid, friendid);
                if (flag == 0) {
                    return new Result(false, StatusCode.ERROR, "不能重复添加好友");
                } else if (flag == 1) {
                    userClient.updateFanscountAndFollowcount(userid, friendid, 1);
                    return new Result(true, StatusCode.OK, "添加成功");
                }
            } else if (type.equals("2")) {
                // 添加非好友
                int flag = friendService.addUnFriend(userid, friendid);
                if (0 == flag) {
                    return new Result(false, StatusCode.ERROR, "不能重复添加非好友");
                } else if (flag == 1) {
                    return new Result(true, StatusCode.OK, "添加成功");
                }
            }
            return new Result(false, StatusCode.ERROR, "参数异常");
        } else {
            return new Result(false, StatusCode.ERROR, "参数异常");
        }
    }

    @DeleteMapping("/{friendid}")
    public Result deleteFriend(@PathVariable("friendid") String friendid){
        // 1.验证是否登录,并且拿到当前登录的用户id
        Claims claims = (Claims) request.getAttribute("claims_user");
        if (null == claims) {
            return new Result(false, StatusCode.LOGINERROR, "权限不足");
        }
        // 得到用户id
        String userid = claims.getId();
        friendService.deleteFriend(userid, friendid);
        userClient.updateFanscountAndFollowcount(userid, friendid, -1);
        return new Result(true, StatusCode.OK, "删除成功");
    }
}
