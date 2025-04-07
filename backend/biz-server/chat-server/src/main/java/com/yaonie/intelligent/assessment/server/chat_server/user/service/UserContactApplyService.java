package com.yaonie.intelligent.assessment.server.chat_server.user.service;

import com.yaonie.intelligent.assessment.server.chat_server.user.entity.dto.ContactApplyRequest;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.vo.UserContactApplyVO;

import java.util.List;


/**
 * 用户人申请表 业务接口
 *
 * @author 77160
 */
public interface UserContactApplyService {
    void sendContactApply(ContactApplyRequest request);

    List<UserContactApplyVO> getOwnContactApplyList();

    void acceptContactApply(Long id);

    void rejectContactApply(Long id);
}