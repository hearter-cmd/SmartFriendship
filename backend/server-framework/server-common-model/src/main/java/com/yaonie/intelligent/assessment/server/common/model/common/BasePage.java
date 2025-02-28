package com.yaonie.intelligent.assessment.server.common.model.common;


import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-13
 */
@Data
public class BasePage<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private List<T> pageData;
    private Long total;

    public BasePage(List<T> pageData, Long total) {
        this.pageData = pageData;
        this.total = total;
    }

    public static <T> BasePage<T> build(List<T> pageData, Long total) {
        return new BasePage<T>(pageData, total);
    }
}
