package com.yaonie.intelligent.assessment.ai.domain.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-14
 */
@AllArgsConstructor
@Getter
public enum AiConfigTypeEnum {
    ZHI_PU_CHAT("ZhiPuChat"),
    OLLAMA_CHAT("OllamaChat");

    private final String type;

    List<AiConfigTypeEnum> getValues() {
        AiConfigTypeEnum[] values = values();
        return List.of(values);
    }

    AiConfigTypeEnum getByType(String type) {
        AiConfigTypeEnum[] values = values();
        for (AiConfigTypeEnum value : values) {
            if (value.getType().equals(type)) {
                return value;
            }
        }
        return null;
    }
}
