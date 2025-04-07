package com.yaonie.intelligent.assessment.server.chat_server.user.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.chat.UserContactApply;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户人申请表 数据库操作接口
 *
 * @author 77160
 */
@Mapper
public interface UserContactApplyMapper extends BaseMapper<UserContactApply> {
}
