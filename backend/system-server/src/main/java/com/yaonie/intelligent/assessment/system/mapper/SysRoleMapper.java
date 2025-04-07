package com.yaonie.intelligent.assessment.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaonie.intelligent.assessment.system.domain.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * 针对表【sys_role(角色)】的数据库操作Mapper
 *
 * @author 77160
 * @createDate 2024-08-18 00:16:57
 * @Entity domain.entity.SysRole
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    @Select("SELECT roleId FROM sys_user_role WHERE userId = #{userId}")
    List<Long> selectRoleIdsByUserId(Long userId);
} 