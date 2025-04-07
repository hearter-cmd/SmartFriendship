package com.yaonie.intelligent.assessment.server.chat_server.chat.mappers;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.chat.Message;
import org.apache.ibatis.annotations.Mapper;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Author 武春利
 * @CreateTime 2024-09-22 22:28
 * @Description : Message Mapper映射
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {
}
