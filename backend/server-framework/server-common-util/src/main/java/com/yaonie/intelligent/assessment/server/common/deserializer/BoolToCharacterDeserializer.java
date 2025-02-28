package com.yaonie.intelligent.assessment.server.common.deserializer;


import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-13
 */
public class BoolToCharacterDeserializer extends JsonDeserializer<Character> {

    @Override
    public Character deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        boolean value = p.getBooleanValue();
        return value ? '1' : '0';
    }
}
