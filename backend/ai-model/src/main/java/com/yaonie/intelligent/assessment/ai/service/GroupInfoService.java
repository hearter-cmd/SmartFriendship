package com.yaonie.intelligent.assessment.ai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.chat.GroupInfo;

import java.util.List;


/**
 * 群聊存档 业务接口
 *
 * @author 77160
 */
public interface GroupInfoService extends IService<GroupInfo> {
    List<GroupInfo> getGroupInfoListTop10();
}