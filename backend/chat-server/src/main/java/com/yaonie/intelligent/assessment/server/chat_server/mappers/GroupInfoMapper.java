package com.yaonie.intelligent.assessment.server.chat_server.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * 群聊存档 数据库操作接口
 */
public interface GroupInfoMapper<T,P> extends BaseMapper<T,P> {

	/**
	 * 根据GroupId更新
	 */
	 Integer updateByGroupId(@Param("bean") T t,@Param("groupId") Long groupId);


	/**
	 * 根据GroupId删除
	 */
	 Integer deleteByGroupId(@Param("groupId") Long groupId);


	/**
	 * 根据GroupId获取对象
	 */
	 T selectByGroupId(@Param("groupId") Long groupId);


}
