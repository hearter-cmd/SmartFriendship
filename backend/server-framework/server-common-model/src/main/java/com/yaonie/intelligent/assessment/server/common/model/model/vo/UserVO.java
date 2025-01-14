package com.yaonie.intelligent.assessment.server.common.model.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户视图（脱敏）
 *
 * @author 77160
 */
@Data
public class UserVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    /**
     * 用户昵称
     */
    private String userName;
    /**
     * 用户头像
     */
    private String userAvatar;
    /**
     * 性别
     */
    private Character sex;
    /**
     * 用户简介
     */
    private String userProfile;
    /**
     * 用户角色：user/admin/ban
     */
    private String userRole = "user";
    /**
     * 用户角色对象表
     */
    private List<RoleVO> roles;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 用户session
     */
    private String session;

    @JsonSerialize(using = CharacterToBooleanSerializer.class)
    private Character enable;

    private static class CharacterToBooleanSerializer extends JsonSerializer<Character> {

        @Override
        public void serialize(Character character, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeBoolean(character.equals('1'));
        }
    }


}