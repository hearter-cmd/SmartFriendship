package com.yaonie.intelligent.assessment.server.chat_server.user.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * 用户人申请表 数据库操作接口
 */
public interface UserContactApplyMapper<T,P> extends BaseMapper<T,P> {

	/**
	 * 根据ApplyId更新
	 */
	 Integer updateByApplyId(@Param("bean") T t,@Param("applyId") Integer applyId);


	/**
	 * 根据ApplyId删除
	 */
	 Integer deleteByApplyId(@Param("applyId") Integer applyId);


	/**
	 * 根据ApplyId获取对象
	 */
	 T selectByApplyId(@Param("applyId") Integer applyId);


	/**
	 * 根据ApplyUserIdAndReceiveUserIdAndConcatId更新
	 */
	 Integer updateByApplyUserIdAndReceiveUserIdAndConcatId(@Param("bean") T t,@Param("applyUserId") Long applyUserId,@Param("receiveUserId") Long receiveUserId,@Param("concatId") Long concatId);


	/**
	 * 根据ApplyUserIdAndReceiveUserIdAndConcatId删除
	 */
	 Integer deleteByApplyUserIdAndReceiveUserIdAndConcatId(@Param("applyUserId") Long applyUserId,@Param("receiveUserId") Long receiveUserId,@Param("concatId") Long concatId);


	/**
	 * 根据ApplyUserIdAndReceiveUserIdAndConcatId获取对象
	 */
	 T selectByApplyUserIdAndReceiveUserIdAndConcatId(@Param("applyUserId") Long applyUserId,@Param("receiveUserId") Long receiveUserId,@Param("concatId") Long concatId);


}
