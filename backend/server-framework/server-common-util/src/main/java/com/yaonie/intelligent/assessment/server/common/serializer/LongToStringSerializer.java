package com.yaonie.intelligent.assessment.server.common.serializer;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-13
 */
public class LongToStringSerializer extends JsonSerializer<Long> {
    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.toString());
    }
}
