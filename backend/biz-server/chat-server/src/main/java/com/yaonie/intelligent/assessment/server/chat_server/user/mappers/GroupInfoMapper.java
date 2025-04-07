package com.yaonie.intelligent.assessment.server.chat_server.user.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.chat.GroupInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 群聊存档 数据库操作接口
 *
 * @author 77160
 */
public interface GroupInfoMapper extends BaseMapper<GroupInfo> {

    /**
     * 根据GroupId更新
     */
    Integer updateByGroupId(@Param("bean") GroupInfo t, @Param("groupId") Long groupId);


    /**
     * 根据GroupId删除
     */
    Integer deleteByGroupId(@Param("groupId") Long groupId);


    /**
     * 根据GroupId获取对象
     */
    GroupInfo selectByGroupId(@Param("groupId") Long groupId);


}
