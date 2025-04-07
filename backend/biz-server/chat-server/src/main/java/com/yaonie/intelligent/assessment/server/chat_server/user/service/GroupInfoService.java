package com.yaonie.intelligent.assessment.server.chat_server.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.chat.GroupInfo;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;


/**
 * 群聊存档 业务接口
 *
 * @author 77160
 */
public interface GroupInfoService extends IService<GroupInfo> {

    /**
     * 新增
     */
    Integer add(GroupInfo bean);

    /**
     * 批量新增
     */
    Integer addBatch(List<GroupInfo> listBean);

    /**
     * 批量新增/修改
     */
    Integer addOrUpdateBatch(List<GroupInfo> listBean);

    /**
     * 根据GroupId查询对象
     */
    GroupInfo getGroupInfoByGroupId(Long groupId);


    /**
     * 根据GroupId修改
     */
    Integer updateGroupInfoByGroupId(GroupInfo bean, Long groupId);


    /**
     * 根据GroupId删除
     */
    Integer deleteGroupInfoByGroupId(Long groupId);

    Object saveGroup(HttpServletRequest request, GroupInfo groupInfo);

    List<GroupInfo> searchGroup(String keyword);
}