package com.yaonie.intelligent.assessment.ai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaonie.intelligent.assessment.ai.mapper.MatchGroupInfoMapper;
import com.yaonie.intelligent.assessment.ai.service.GroupInfoService;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.chat.GroupInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 群聊存档 业务接口实现
 *
 * @author 77160
 */
@Service
@Slf4j
public class GroupInfoServiceImpl extends ServiceImpl<MatchGroupInfoMapper, GroupInfo> implements GroupInfoService {

    @Resource
    MatchGroupInfoMapper matchGroupInfoMapper;

    @Override
    public List<GroupInfo> getGroupInfoListTop10() {
        return matchGroupInfoMapper.getGroupInfoListTop10();
    }
}