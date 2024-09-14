package com.yaonie.intelligent.assessment.server.chat_server.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * 联系人关系表 数据库操作接口
 */
public interface UserContactMapper<T,P> extends BaseMapper<T,P> {

	/**
	 * 根据UserIdAndContactId更新
	 */
	 Integer updateByUserIdAndContactId(@Param("bean") T t,@Param("userId") Long userId,@Param("contactId") Long contactId);


	/**
	 * 根据UserIdAndContactId删除
	 */
	 Integer deleteByUserIdAndContactId(@Param("userId") Long userId,@Param("contactId") Long contactId);


	/**
	 * 根据UserIdAndContactId获取对象
	 */
	 T selectByUserIdAndContactId(@Param("userId") Long userId,@Param("contactId") Long contactId);


}
