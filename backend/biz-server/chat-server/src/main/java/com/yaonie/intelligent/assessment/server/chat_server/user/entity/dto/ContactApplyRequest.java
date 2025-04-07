package com.yaonie.intelligent.assessment.server.chat_server.user.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ContactApplyRequest {

    @NotNull(message = "接收用户ID不能为空")
    private String receiveUserId;

    @NotBlank(message = "申请信息不能为空")
    @Length(max = 100, message = "申请信息不能超过100字")
    private String applyInfo;
}