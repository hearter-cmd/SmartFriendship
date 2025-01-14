package com.yaonie.intelligent.assessment.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import com.yaonie.intelligent.assessment.system.domain.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-10
 */
@Mapper
public interface AuthMapper extends BaseMapper<User> {
    SysMenu selectMenuByPath(String requestURI);
}
