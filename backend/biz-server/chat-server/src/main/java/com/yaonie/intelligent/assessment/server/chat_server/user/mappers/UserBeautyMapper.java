package com.yaonie.intelligent.assessment.server.chat_server.user.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * 用户 数据库操作接口
 *
 * @author 77160
 */
public interface UserBeautyMapper<T, P> extends BaseMapper<T, P> {

    /**
     * 根据Id更新
     */
    Integer updateById(@Param("bean") T t, @Param("id") Long id);


    /**
     * 根据Id删除
     */
    Integer deleteById(@Param("id") Long id);


    /**
     * 根据Id获取对象
     */
    T selectById(@Param("id") Long id);


    /**
     * 根据Email更新
     */
    Integer updateByEmail(@Param("bean") T t, @Param("email") String email);


    /**
     * 根据Email删除
     */
    Integer deleteByEmail(@Param("email") String email);


    /**
     * 根据Email获取对象
     */
    T selectByEmail(@Param("email") String email);


}
