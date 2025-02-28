package com.yaonie.intelligent.assessment.ai.initializer;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.ai.vectorstore.MilvusVectorStore;
import org.springframework.stereotype.Component;

/**
 * @author 77160
 */
@Component
public class MilvusInitializer {

    @Resource
    private MilvusVectorStore milvusVectorStore;

    @PostConstruct
    public void init() throws Exception {
        milvusVectorStore.afterPropertiesSet();
    }
}