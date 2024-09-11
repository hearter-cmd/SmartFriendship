package com.yaonie.intelligent_assessment_server.search_server.model.enums;


import com.yaonie.intelligent_assessment_server.common.ErrorCode;
import com.yaonie.intelligent_assessment_server.exception.BusinessException;
import com.yaonie.intelligent_assessment_server.exception.ThrowUtils;
import lombok.Data;
import lombok.Getter;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-08 1:42
 * @Author 武春利
 * @CreateTime 2024-09-08
 * @ClassName SearchTypeEnum
 * @Project backend
 * @Description : 搜索类型枚举
 */
@Getter
public enum SearchTypeEnum {
    USER("userSearchDataSourceImpl"),
    QUESTION("questionSearchDataSourceImpl"),
    APP_DESC("appSearchDataSourceImpl");

    private final String dataSource;

    SearchTypeEnum (String dataSource) {
        this.dataSource = dataSource;
    }

    public static SearchTypeEnum getByValue(String name) {
        ThrowUtils.throwIf(name == null, new BusinessException(ErrorCode.PARAMS_ERROR));
        for (SearchTypeEnum type : values()) {
            if (type.getDataSource().equals(name)) {
                return type;
            }
        }
        return null;
    }

}
