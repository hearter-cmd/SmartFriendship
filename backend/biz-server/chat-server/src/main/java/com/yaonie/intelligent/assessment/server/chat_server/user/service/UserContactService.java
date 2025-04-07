package com.yaonie.intelligent.assessment.server.chat_server.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.query.UserContactQuery;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.vo.PaginationResultVO;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.chat.UserContact;

import java.util.List;


/**
 * 联系人关系表 业务接口
 */
public interface UserContactService extends IService<UserContact> {

    /**
     * 根据条件查询列表
     */
    List<UserContact> findListByParam(UserContactQuery param);

    /**
     * 根据条件查询列表
     */
    Integer findCountByParam(UserContact param);

    /**
     * 分页查询
     */
    PaginationResultVO<UserContact> findListByPage(UserContactQuery param);

    /**
     * 新增
     */
    Integer add(UserContact bean);

    /**
     * 批量新增
     */
    Integer addBatch(List<UserContact> listBean);

    /**
     * 批量新增/修改
     */
    Integer addOrUpdateBatch(List<UserContact> listBean);

    /**
     * 根据UserIdAndContactId查询对象
     */
    UserContact getUserContactByUserIdAndContactId(Long userId, Long contactId);


    /**
     * 根据UserIdAndContactId修改
     */
    Integer updateUserContactByUserIdAndContactId(UserContact bean, Long userId, Long contactId);


    /**
     * 根据UserIdAndContactId删除
     */
    Integer deleteUserContactByUserIdAndContactId(Long userId, Long contactId);

    List<UserContact> findList();

    boolean checkIsFriend(Long currentUserId, String userId);

    void joinGroup(String groupId);
}