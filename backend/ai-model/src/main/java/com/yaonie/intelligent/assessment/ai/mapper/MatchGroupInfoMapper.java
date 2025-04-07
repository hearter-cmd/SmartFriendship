package com.yaonie.intelligent.assessment.ai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.chat.GroupInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 群聊存档 数据库操作接口
 *
 * @author 77160
 */
@Mapper
public interface MatchGroupInfoMapper extends BaseMapper<GroupInfo> {
    List<GroupInfo> getGroupInfoListTop10();
}
