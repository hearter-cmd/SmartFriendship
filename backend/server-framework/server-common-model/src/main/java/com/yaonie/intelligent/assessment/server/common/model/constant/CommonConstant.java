package com.yaonie.intelligent.assessment.server.common.model.constant;

/**
 * 通用常量
 *
 * @author yaonie
 */
public interface CommonConstant {

    /**
     * 校验是否唯一的返回标识
     */
    boolean UNIQUE = true;
    boolean NOT_UNIQUE = false;

    /**
     * 升序
     */
    String SORT_ORDER_ASC = "ascend";

    /**
     * 降序
     */
    String SORT_ORDER_DESC = " descend";

    /**
     * Redis验证码前缀
     */
    String REDIS_CAPTCHA_PREFIX = "user:captcha:";

    /**
     * 禁用
     */
    Character IS_DISABLE = '0';
    /**
     * 启动
     */
    Character IS_ENABLE = '1';

    /**
     * 已经删除
     */
    String IS_DELETED = "1";
    /**
     * 未删除
     */
    String IS_NOT_DELETED = "0";

    /**
     * 菜单路径, 通过path匹配菜单对象
     */
    String MENU_PATH = "menu::path::obj";

    /**
     * 删除
     */
    String IS_DEL = "1";
    /**
     * 还在
     */
    String IS_NOT_DEL = "0";

}
