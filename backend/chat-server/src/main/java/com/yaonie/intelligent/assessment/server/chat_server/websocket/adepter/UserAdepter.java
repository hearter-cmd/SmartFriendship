package com.yaonie.intelligent.assessment.server.chat_server.websocket.adepter;


import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;

import java.util.Date;
import java.util.UUID;


/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-16 17:15
 * @Author 武春利
 * @CreateTime 2024-09-16
 * @ClassName UserAdepter
 * @Project backend
 * @Description : 
 */
public class UserAdepter {
    public static User buildUserSave(String mpOpenId) {
        User user = new User();
        user.setUserAccount(mpOpenId);
        user.setUserPassword(UUID.randomUUID().toString().replace("-",""));
        user.setMpOpenId(mpOpenId);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsDelete(0);
        return user;
    }
    
    public static User buildAuthorizeUser(WxOAuth2UserInfo userInfo) {
        // 更新对象
        User user = new User();
        user.setSex(0);
        user.setPersonSignature("这是一条个性的签名~~");
        user.setUnionId(userInfo.getUnionId());
        user.setUserName(userInfo.getNickname());
        user.setUserAvatar(userInfo.getHeadImgUrl());
        // TODO 解析用户IP所属地
        user.setAreaName("");
        user.setAreaCode("");
        user.setLastLoginTime(new Date());
        user.setUpdateTime(new Date());
        return user;
    }
}
