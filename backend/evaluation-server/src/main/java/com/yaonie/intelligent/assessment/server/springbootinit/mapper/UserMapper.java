package com.yaonie.intelligent.assessment.server.springbootinit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 77160
* @description 针对表【user(用户)】的数据库操作Mapper
* @createDate 2024-08-18 00:16:57
* @Entity com.yaonie.intelligent.assessment.server.springbootinit.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




