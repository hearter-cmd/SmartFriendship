package com.yaonie.intelligent.assessment.ai.service.impl;


import com.yaonie.intelligent.assessment.ai.domain.AiConstants;
import com.yaonie.intelligent.assessment.ai.domain.model.po.CustomDocument;
import com.yaonie.intelligent.assessment.ai.service.GroupVectorService;
import io.milvus.client.MilvusClient;
import io.milvus.client.MilvusServiceClient;
import io.milvus.common.clientenum.ConsistencyLevelEnum;
import io.milvus.grpc.DataType;
import io.milvus.grpc.DescribeIndexResponse;
import io.milvus.grpc.SearchResults;
import io.milvus.param.ConnectParam;
import io.milvus.param.IndexType;
import io.milvus.param.MetricType;
import io.milvus.param.R;
import io.milvus.param.RpcStatus;
import io.milvus.param.collection.CreateCollectionParam;
import io.milvus.param.collection.FieldType;
import io.milvus.param.collection.FlushParam;
import io.milvus.param.collection.HasCollectionParam;
import io.milvus.param.collection.LoadCollectionParam;
import io.milvus.param.dml.InsertParam;
import io.milvus.param.dml.SearchParam;
import io.milvus.param.index.CreateIndexParam;
import io.milvus.param.index.DescribeIndexParam;
import io.milvus.response.QueryResultsWrapper;
import io.milvus.response.SearchResultsWrapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.ai.vectorstore.MilvusVectorStore.CONTENT_FIELD_NAME;
import static org.springframework.ai.vectorstore.MilvusVectorStore.DOC_ID_FIELD_NAME;
import static org.springframework.ai.vectorstore.MilvusVectorStore.EMBEDDING_FIELD_NAME;
import static org.springframework.ai.vectorstore.MilvusVectorStore.METADATA_FIELD_NAME;
import static org.springframework.ai.vectorstore.MilvusVectorStore.SEARCH_OUTPUT_FIELDS;


/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-03-12
 */
@Service
public class GroupVectorServiceImpl implements GroupVectorService {
    private final MilvusClient milvusClient;

    @Resource
    private EmbeddingModel embeddingModel;

    {
        ConnectParam build = ConnectParam.newBuilder()
                .withHost("localhost")
                .withPort(19530)
//                .withAuthorization("root", "Milvus")
                .withDatabaseName(AiConstants.DATABASE_NAME)
                .build();
        milvusClient = new MilvusServiceClient(build);
    }

    @PostConstruct
    public void init() throws Exception {
        if (!isDatabaseCollectionExists()) {

            FieldType docIdFieldType = FieldType.newBuilder()
                    .withName(DOC_ID_FIELD_NAME)
                    .withDataType(DataType.VarChar)
                    .withMaxLength(36)
                    .withPrimaryKey(true)
                    .withAutoID(false)
                    .build();
            FieldType contentFieldType = FieldType.newBuilder()
                    .withName(CONTENT_FIELD_NAME)
                    .withDataType(DataType.VarChar)
                    .withMaxLength(65535)
                    .build();
            FieldType metadataFieldType = FieldType.newBuilder()
                    .withName(METADATA_FIELD_NAME)
                    .withDataType(DataType.JSON)
                    .build();
            FieldType embeddingFieldType = FieldType.newBuilder()
                    .withName(EMBEDDING_FIELD_NAME)
                    .withDataType(DataType.FloatVector)
                    .withDimension(1024)
                    .build();

            CreateCollectionParam createCollectionReq = CreateCollectionParam.newBuilder()
                    .withDatabaseName(AiConstants.DATABASE_NAME)
                    .withCollectionName(AiConstants.COLLECTION_NAME)
                    .withDescription("Custom Spring AI Vector Store")
                    .withConsistencyLevel(ConsistencyLevelEnum.STRONG)
                    .withShardsNum(2)
                    .addFieldType(docIdFieldType)
                    .addFieldType(contentFieldType)
                    .addFieldType(metadataFieldType)
                    .addFieldType(embeddingFieldType)
                    .build();

            R<RpcStatus> collectionStatus = this.milvusClient.createCollection(createCollectionReq);
            if (collectionStatus.getException() != null) {
                throw new RuntimeException("Failed to create collection", collectionStatus.getException());
            }
        }

        R<DescribeIndexResponse> indexDescriptionResponse = this.milvusClient
                .describeIndex(DescribeIndexParam.newBuilder()
                        .withDatabaseName(AiConstants.DATABASE_NAME)
                        .withCollectionName(AiConstants.COLLECTION_NAME)
                        .build());

        if (indexDescriptionResponse.getData() == null) {
            R<RpcStatus> indexStatus = this.milvusClient.createIndex(CreateIndexParam.newBuilder()
                    .withDatabaseName(AiConstants.DATABASE_NAME)
                    .withCollectionName(AiConstants.COLLECTION_NAME)
                    .withFieldName(EMBEDDING_FIELD_NAME)
                    .withIndexType(IndexType.IVF_FLAT)
                    .withMetricType(MetricType.COSINE)
                    .withExtraParam("{\"nlist\":1024}")
                    .withSyncMode(Boolean.FALSE)
                    .build());

            if (indexStatus.getException() != null) {
                throw new RuntimeException("Failed to create Index", indexStatus.getException());
            }
        }

        R<RpcStatus> loadCollectionStatus = this.milvusClient.loadCollection(LoadCollectionParam.newBuilder()
                .withDatabaseName(AiConstants.DATABASE_NAME)
                .withCollectionName(AiConstants.COLLECTION_NAME)
                .build());

        if (loadCollectionStatus.getException() != null) {
            throw new RuntimeException("Collection loading failed!", loadCollectionStatus.getException());
        }
    }

    @Override
    public void insert(String contentId, List<String> tags) {
        String join = String.join(",", tags);
        List<Double> embed = embeddingModel.embed(join);

        ArrayList<InsertParam.Field> fields = new ArrayList<>();
        fields.add(new InsertParam.Field(DOC_ID_FIELD_NAME, List.of(contentId)));
        fields.add(new InsertParam.Field(CONTENT_FIELD_NAME, List.of(join)));
        fields.add(new InsertParam.Field(EMBEDDING_FIELD_NAME, embed));

        InsertParam param = InsertParam.newBuilder()
                .withCollectionName(AiConstants.COLLECTION_NAME)
                .withDatabaseName(AiConstants.DATABASE_NAME)
                .withFields(fields)
                .build();
        milvusClient.insert(param);
        milvusClient.flush(FlushParam.newBuilder().withCollectionNames(List.of(AiConstants.COLLECTION_NAME)).build());
    }

    @Override
    public List<CustomDocument> similaritySearch(String keywords) {
        // 获取关键词的向量
        List<Double> embed = embeddingModel.embed(keywords);

        // 进行搜索
        var searchParamBuilder = SearchParam.newBuilder()
                .withCollectionName(AiConstants.COLLECTION_NAME)
                .withConsistencyLevel(ConsistencyLevelEnum.STRONG)
                .withMetricType(MetricType.COSINE)
                .withOutFields(SEARCH_OUTPUT_FIELDS)
                .withTopK(4)
                .withVectors(List.of(embed.stream().map(Number::floatValue).toList()))
                .withVectorFieldName(EMBEDDING_FIELD_NAME)
                .build();
        R<SearchResults> respSearch = milvusClient.search(searchParamBuilder);

        if (respSearch.getException() != null) {
            throw new RuntimeException("Search failed!", respSearch.getException());
        }

        SearchResultsWrapper wrapperSearch = new SearchResultsWrapper(respSearch.getData().getResults());

        List<CustomDocument> documents = wrapperSearch.getRowRecords(0)
                .stream()
                .filter(rowRecord -> getResultSimilarity(rowRecord) >= 0.0)
                .map(rowRecord -> {
                    String docId = (String) rowRecord.get(DOC_ID_FIELD_NAME);
                    String content = (String) rowRecord.get(CONTENT_FIELD_NAME);
                    return new CustomDocument(docId, content);
                })
                .toList();

        return documents;
    }

    // 通过返回的结果，获取相似度
    private float getResultSimilarity(QueryResultsWrapper.RowRecord rowRecord) {
        Float distance = (Float) rowRecord.get("distance");
        return distance;
    }

    // 判断数据库中是否存在集合
    private boolean isDatabaseCollectionExists() {
        return milvusClient
                .hasCollection(HasCollectionParam.newBuilder()
                        .withDatabaseName(AiConstants.DATABASE_NAME)
                        .withCollectionName(AiConstants.COLLECTION_NAME)
                        .build())
                .getData();
    }
}
