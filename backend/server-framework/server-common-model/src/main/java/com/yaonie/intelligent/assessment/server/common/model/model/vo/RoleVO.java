package com.yaonie.intelligent.assessment.server.common.model.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
public class RoleVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private String code;
    private String name;
    private Boolean enable;

    public RoleVO(String id, String code, String name, Boolean enable) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.enable = enable;
    }
}