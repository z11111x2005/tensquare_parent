package com.tensquare.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient("tensquare-user")
public interface UserClient {

    /**
     * 更新粉丝和关注数
     * @param userid
     * @param friendid
     * @param operation
     */
    @PutMapping("/user/{userid}/{friendid}/{operation}")
    void updateFanscountAndFollowcount(@PathVariable("userid") String userid,
                                              @PathVariable("friendid") String friendid,
                                              @PathVariable("operation") Integer operation);
}
