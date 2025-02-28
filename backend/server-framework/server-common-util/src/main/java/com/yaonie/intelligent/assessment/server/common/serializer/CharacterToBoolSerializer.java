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
public class CharacterToBoolSerializer extends JsonSerializer<Character> {
    @Override
    public void serialize(Character c, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeBoolean(c.equals('1'));
    }
}
