package com.yaonie.intelligent.assessment.system.domain.vo;


import lombok.Data;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-09
 */
@Data
public class AdminUserInfoVO {
    private Long id;
    private String username;
    private Boolean enable;
    private String createTime;
    private String updateTime;
    private Profile profile;
    private List<Role> roles;
    private Role currentRole;

    @Data
    public static class Profile {
        private Long id;
        private String nickName;
        private Character gender;
        private String avatar;
        private String address;
        private String email;
        private Long userId;
    }

    @Data
    public static class Role {
        private String id;
        private String code;
        private String name;
        private Boolean enable;
    }
}
