package com.yaonie.intelligent.assessment.ai.utils;


import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingOptions;
import org.springframework.ai.embedding.EmbeddingRequest;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-31
 */
public class EmbeddingUtils {
    public static List<Double> getEmbedding(
            EmbeddingModel embeddingModel,
            List<String> inputs,
            EmbeddingOptions embeddingOptions) {
        EmbeddingRequest embeddingRequest = new EmbeddingRequest(
                inputs,
                embeddingOptions
        );
        Embedding result = embeddingModel.call(embeddingRequest).getResult();
        List<Double> output = result.getOutput();
        return output;
    }
}
