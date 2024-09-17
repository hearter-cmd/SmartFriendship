package com.yaonie.intelligent.assessment.server.chat_server.user.mappers;

import com.yaonie.intelligent.assessment.server.chat_server.user.entity.query.UserQuery;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import org.apache.ibatis.annotations.Param;

import javax.management.Query;

/**
 * 用户 数据库操作接口
 * @author 武春利
 */
public interface UserMapper extends BaseMapper<User, UserQuery> {

	/**
	 * 根据Id更新
	 */
	 Integer updateById(@Param("bean") User User,@Param("id") Long id);


	/**
	 * 根据Id删除
	 */
	 Integer deleteById(@Param("id") Long id);


	/**
	 * 根据Id获取对象
	 */
	 User selectById(@Param("id") Long id);


	/**
	 * 根据Email更新
	 */
	 Integer updateByEmail(@Param("bean") User User,@Param("email") String email);


	/**
	 * 根据Email删除
	 */
	 Integer deleteByEmail(@Param("email") String email);


	/**
	 * 根据Email获取对象
	 */
	 User selectByEmail(@Param("email") String email);


	 User selectByMpOpenId(@Param("mpOpenId") String mpOpenId);
}
