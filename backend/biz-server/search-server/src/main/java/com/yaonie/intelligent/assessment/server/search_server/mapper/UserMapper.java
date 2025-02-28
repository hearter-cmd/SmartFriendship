package com.yaonie.intelligent.assessment.server.search_server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-05 20:15
 * @Author 武春利
 * @CreateTime 2024-09-05
 * @ClassName UserMapper
 * @Project backend
 * @Description : 用户数据库映射
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
