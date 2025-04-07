package com.yaonie.intelligent.assessment.server.chat_server.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaonie.intelligent.assessment.server.chat_server.common.service.GroupMemberService;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.dto.ContactApplyRequest;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.enums.UserContactApplyStatus;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.enums.UserContactTypeEnum;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.vo.UserContactApplyVO;
import com.yaonie.intelligent.assessment.server.chat_server.user.mappers.UserContactApplyMapper;
import com.yaonie.intelligent.assessment.server.chat_server.user.service.UserContactApplyService;
import com.yaonie.intelligent.assessment.server.common.model.common.ErrorCode;
import com.yaonie.intelligent.assessment.server.common.model.event.JoinGroupEvent;
import com.yaonie.intelligent.assessment.server.common.model.exception.BusinessException;
import com.yaonie.intelligent.assessment.server.common.model.exception.ThrowUtils;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.chat.GroupInfo;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.chat.GroupMember;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.chat.UserContact;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.chat.UserContactApply;
import com.yaonie.intelligent.assessment.server.common.util.SecurityUtils;
import com.yaonie.intelligent.assessment.system.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * 用户人申请表 业务接口实现
 *
 * @author 77160
 */
@Service
@Slf4j
public class UserContactApplyServiceImpl extends ServiceImpl<UserContactApplyMapper, UserContactApply> implements UserContactApplyService {

    @Resource
    private UserContactApplyMapper userContactApplyMapper;
    @Resource
    private UserService userService;
    @Resource
    private GroupInfoServiceImpl groupInfoService;
    @Resource
    private UserContactServiceImpl userContactService;
    @Resource
    private GroupMemberService groupMemberService;
    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendContactApply(ContactApplyRequest request) {
        // 判断申请类型
        String receiveUserIdStr = String.valueOf(request.getReceiveUserId());
        ThrowUtils.throwIf(Long.valueOf(request.getReceiveUserId()).equals(SecurityUtils.getUserId()), ErrorCode.OPERATION_ERROR, "请不要自己加自己！");
        int length = receiveUserIdStr.length();

        ThrowUtils.throwIf(length != 11 && length != 19, ErrorCode.PARAMS_ERROR, "接收用户ID格式不正确");

        Long count = lambdaQuery().eq(UserContactApply::getReceiveUserId, request.getReceiveUserId())
                .eq(UserContactApply::getApplyUserId, SecurityUtils.getUserId())
                .in(UserContactApply::getStatus, List.of(UserContactApplyStatus.UNTREATED, UserContactApplyStatus.ACCEPTED))
                .count();

        ThrowUtils.throwIf(count > 0, ErrorCode.PARAMS_ERROR, "已经发送过申请，请勿重复发送");

        UserContactApply apply = new UserContactApply();
        apply.setApplyUserId(SecurityUtils.getUserId().toString());
        apply.setReceiveUserId(request.getReceiveUserId());
        apply.setApplyInfo(request.getApplyInfo());
        apply.setLastApplyTime(new Date());
        // 未处理状态
        apply.setStatus(0);

        // 设置申请类型和联系人ID
        apply.setConcatType(length == 11 ? UserContactTypeEnum.GROUP.getType() : UserContactTypeEnum.USER.getType());

        apply.setConcatId(request.getReceiveUserId());

        try {
            userContactApplyMapper.insert(apply);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "已经发送过申请，请勿重复发送");
        }
    }

    @Override
    public List<UserContactApplyVO> getOwnContactApplyList() {
        Long userId = SecurityUtils.getUserId();
        ThrowUtils.throwIf(Objects.isNull(userId), ErrorCode.PARAMS_ERROR, "用户ID为空");
        List<GroupInfo> ownerGroup = groupInfoService.lambdaQuery()
                .eq(GroupInfo::getGroupOwnerId, userId)
                .eq(GroupInfo::getStatus, 1)
                .list();
        List<Long> ownerGroupIds = ownerGroup.stream().map(GroupInfo::getGroupId).toList();
        // 查询记录
        Page<UserContactApply> page = lambdaQuery().and(wrapper -> {
                    wrapper.eq(UserContactApply::getReceiveUserId, userId)
                            .or().eq(UserContactApply::getApplyUserId, userId)
                            .or().in(!ownerGroupIds.isEmpty(), UserContactApply::getReceiveUserId, ownerGroupIds)
                    ;
                })
                .gt(UserContactApply::getLastApplyTime, new Date(System.currentTimeMillis() - 3600000L * 24 * 30))
                .page(new Page<>(1, 10));
        if (page.getRecords().isEmpty()) {
            return List.of();
        }

        // 取出用户
        List<String> applyUserIds = new ArrayList<>();
        List<String> receiveUserIds = new ArrayList<>();
        List<String> finalApplyUserIds = applyUserIds;
        List<String> finalReceiveUserIds = receiveUserIds;
        page.getRecords().forEach(userContactApply -> {
            finalApplyUserIds.add(userContactApply.getApplyUserId());
            finalReceiveUserIds.add(userContactApply.getReceiveUserId());
        });
        if (finalApplyUserIds.isEmpty() || finalReceiveUserIds.isEmpty()) {
            return List.of();
        }

        applyUserIds = applyUserIds.stream().distinct().toList();
        receiveUserIds = receiveUserIds.stream().distinct().toList();
        List<User> applyUsers = userService.listByIds(applyUserIds);
        List<User> receiveUsers = userService.listByIds(receiveUserIds);
        List<GroupInfo> receiveGroups = groupInfoService.listByIds(receiveUserIds);
        if (applyUsers.isEmpty() || (receiveUsers.isEmpty() && receiveGroups.isEmpty())) {
            return List.of();
        }

        // 封装返回
        List<UserContactApplyVO> userContactApplyVOS = page.getRecords().stream().map(userContactApply -> {
            UserContactApplyVO userContactApplyVO = new UserContactApplyVO();
            userContactApplyVO.setApplyId(userContactApply.getApplyId());
            userContactApplyVO.setConcatId(userContactApply.getConcatId());
            userContactApplyVO.setConcatType(userContactApply.getConcatType());
            userContactApplyVO.setLastApplyTime(userContactApply.getLastApplyTime());
            userContactApplyVO.setStatus(userContactApply.getStatus());
            userContactApplyVO.setApplyInfo(userContactApply.getApplyInfo());
            applyUsers.forEach(item -> {
                if (String.valueOf(item.getId()).equals(userContactApply.getApplyUserId())) {
                    userContactApplyVO.setApplyUser(userService.getUserVO(item));
                }
            });
            if (Objects.equals(userContactApply.getConcatType(), UserContactTypeEnum.USER.getType())) {
                // 如果是用户类型
                receiveUsers.forEach(item -> {
                    if (String.valueOf(item.getId()).equals(userContactApply.getReceiveUserId())) {
                        userContactApplyVO.setReceiveUser(userService.getUserVO(item));
                    }
                });
            } else {
                // 如果是群组类型
                receiveGroups.forEach(item -> {
                    if (String.valueOf(item.getGroupId()).equals(userContactApply.getReceiveUserId())) {
                        userContactApplyVO.setReceiveGroup(item);
                    }
                });
            }
            return userContactApplyVO;
        }).toList();
        return userContactApplyVOS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void acceptContactApply(Long id) {
        lambdaUpdate()
                .eq(UserContactApply::getApplyId, id)
                .set(UserContactApply::getStatus, UserContactApplyStatus.ACCEPTED)
                .update();
        // 接受后，创建联系人
        UserContactApply userContactApply = lambdaQuery()
                .eq(UserContactApply::getApplyId, id)
                .one();
        UserContact userContact = new UserContact();
        userContact.setUserId(userContactApply.getApplyUserId());
        userContact.setContactId(Long.valueOf(userContactApply.getReceiveUserId()));
        userContact.setContactType(userContactApply.getConcatType());
        userContact.setStatus(UserContactTypeEnum.USER.getType());
        userContact.setCreateTime(new Date());
        userContactService.save(userContact);
        if (userContact.getContactId().toString().equals(userContact.getUserId())) {
            return;
        }
        userContact.setUserId(userContactApply.getReceiveUserId());
        userContact.setContactId(Long.valueOf(userContactApply.getApplyUserId()));
        userContactService.save(userContact);

        // 如果是群组，则将用户加入到群里
        if (Objects.equals(userContactApply.getConcatType(), UserContactTypeEnum.GROUP.getType())) {
            GroupMember groupMember = new GroupMember();
            groupMember.setUserId(Long.valueOf(userContactApply.getApplyUserId()));
            groupMember.setGroupId(Long.valueOf(userContactApply.getReceiveUserId()));
            groupMember.setCreateTime(new Date());
            groupMemberService.save(groupMember);
            // 让它加入channel
            publisher.publishEvent(new JoinGroupEvent(groupMember, groupMember));
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rejectContactApply(Long id) {
        lambdaUpdate()
                .eq(UserContactApply::getApplyId, id)
                .set(UserContactApply::getStatus, UserContactApplyStatus.REJECTED)
                .update();
    }
}