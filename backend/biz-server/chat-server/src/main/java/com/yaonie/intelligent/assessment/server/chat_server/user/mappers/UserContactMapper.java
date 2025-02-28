package com.yaonie.intelligent.assessment.server.chat_server.user.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.po.UserContact;
import org.apache.ibatis.annotations.Param;

/**
 * 联系人关系表 数据库操作接口
 * @author 77160
 */
public interface UserContactMapper extends BaseMapper<UserContact> {

	/**
	 * 根据UserIdAndContactId更新
	 */
	 Integer updateByUserIdAndContactId(@Param("bean") UserContact t,@Param("userId") Long userId,@Param("contactId") Long contactId);


	/**
	 * 根据UserIdAndContactId删除
	 */
	 Integer deleteByUserIdAndContactId(@Param("userId") Long userId,@Param("contactId") Long contactId);


	/**
	 * 根据UserIdAndContactId获取对象
	 */
	UserContact selectByUserIdAndContactId(@Param("userId") Long userId,@Param("contactId") Long contactId);


}
