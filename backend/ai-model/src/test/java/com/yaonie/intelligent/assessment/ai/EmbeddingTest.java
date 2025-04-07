package com.yaonie.intelligent.assessment.ai;

import cn.hutool.core.lang.generator.UUIDGenerator;
import io.milvus.client.MilvusServiceClient;
import io.milvus.param.ConnectParam;
import io.milvus.param.IndexType;
import io.milvus.param.LogLevel;
import io.milvus.param.MetricType;
import io.milvus.param.collection.LoadCollectionParam;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.impl.internal.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.MilvusVectorStore;
import org.springframework.ai.zhipuai.ZhiPuAiEmbeddingModel;
import org.springframework.ai.zhipuai.api.ZhiPuAiApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-31
 */
@Slf4j
public class EmbeddingTest {
    private static final int EMBEDDING_DIMENSION = 1024;
    private static UUIDGenerator uuidGenerator = new UUIDGenerator();

    @Test
    public void test1() {
        // 1. 初始化ZhiPuAiApi对象
        ZhiPuAiApi zhiPuAiApi = new ZhiPuAiApi("2b15fd23bbf4471b995ba1c40638f907.vjzXvYQeuVJ4wNNu");
        // 2. 创建ZhiPuAiEmbeddingModel对象
        ZhiPuAiEmbeddingModel embeddingModel = new ZhiPuAiEmbeddingModel(
                zhiPuAiApi);

        ConnectParam milvusConnectParam = ConnectParam.newBuilder()
                .withHost("127.0.0.1")
                .withPort(19530)
                .withDatabaseName("userMatch")
                .build();
        MilvusServiceClient milvusClient = new MilvusServiceClient(milvusConnectParam);
//        if (milvusClient.hasCollection(HasCollectionParam.newBuilder()
//                        .withCollectionName()
//                        .withDatabaseName()
//                .build()).getData())
        milvusClient.loadCollection(LoadCollectionParam
                .newBuilder()
                .withDatabaseName("userMatch")
                .withCollectionName("user_match")
                .build());
        MilvusVectorStore.MilvusVectorStoreConfig vectorStoreConfig = MilvusVectorStore.MilvusVectorStoreConfig
                .builder()
                .withDatabaseName("userMatch")
                .withCollectionName("user_match")
                .withEmbeddingDimension(1024)
                .withIndexType(IndexType.IVF_FLAT)
                .withMetricType(MetricType.COSINE)
                .build();

        MilvusVectorStore milvusVectorStore = new MilvusVectorStore(
                milvusClient, embeddingModel, vectorStoreConfig,
                true);

//        insertVector(milvusVectorStore);
        milvusClient.setLogLevel(LogLevel.Debug);
        // 查询向量库
        List<Document> documents = milvusVectorStore.similaritySearch("唱歌,打豆豆");
        System.out.println(documents);
    }

    @Test
    public void test2() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("唱歌,跳舞,打豆豆");
        strings.add("唱歌,跳舞,打豆豆");
        System.out.println(strings);
    }

    private static void insertVector(MilvusVectorStore milvusVectorStore) {
        // 添加文档到向量库
        List<Document> documents1 = new ArrayList<>();
        Document document1 = new Document(uuidGenerator.next().replaceAll("-", ""), "唱歌,跳舞,打游戏", new HashMap<>());
        documents1.add(document1);
        documents1.add(new Document(uuidGenerator.next().replaceAll("-", ""), "吃饭,睡觉,打豆豆", new HashMap<>()));
        documents1.add(new Document(uuidGenerator.next().replaceAll("-", ""), "唱歌,跳舞,打豆豆", new HashMap<>()));
        milvusVectorStore.add(documents1);
    }

    @Test
    public void test3() {
        testCollection(
                () -> new ConcurrentHashMap<String, Integer>(),
                (map, list) -> {
                    for (String s : list) {
                        map.compute(s, (s1, integer) -> integer == null ? 1 : integer + 1);
                    }
                }
        );
    }

    private <V> void testCollection(Supplier<Map<String, V>> supplier,
                                    BiConsumer<Map<String, V>, List<String>> consumer) {
        Map<String, V> counterMap = supplier.get();
        ArrayList<Thread> ts = new ArrayList<>();
        for (int i = 1; i <= 200; i++) {
            int idx = i;
            Thread thread = new Thread(() -> {
                List<String> list = readFromFile(idx);
                consumer.accept(counterMap, list);
            });
            ts.add(thread);
        }

        ts.forEach(Thread::start);

        ts.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println(counterMap);
    }

    private List<String> readFromFile(int idx) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    @Test
    void testSyncClass() throws ExecutionException, InterruptedException {
        CyclicBarrier cb = new CyclicBarrier(20, () -> {
            System.out.println("执行完毕");
        });
        List<Thread> list = new ArrayList<>(20);
        for (int i = 0; i < 20; i++) {
            Thread thread = new Thread(() -> {
                try {
                    toConsumer(cb);
                } catch (BrokenBarrierException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            list.add(thread);
        }
        list.forEach(Thread::start);
        list.forEach(item -> {
            try {
                item.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        FutureTask<String> ft = new FutureTask<>(() -> {
            TimeUnit.SECONDS.sleep(20);
            return "测试";
        });
        ft.run();
        System.out.println(ft.get());

    }

    private void toConsumer(CyclicBarrier cb) throws BrokenBarrierException, InterruptedException {
        try {
            TimeUnit.SECONDS.sleep((long) (Math.random() * 5 + 1));
            System.out.println(Thread.currentThread().getName() + "消费完毕");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            cb.await();
        }
    }

    @Test
    void testCompletableFuture() throws ExecutionException, InterruptedException {
        // 1. 小明进店
        System.out.println("小明来啦~~~~");
        // 2. 小明点菜
        System.out.println("小明开始点菜~~~~\n");
        List<String> foods = List.of("鸡蛋炒西红柿");
        // 3.1 厨师做菜的同时，小明玩游戏等菜
        CompletableFuture<String> cf = CompletableFuture
                .supplyAsync(new MakeFood(foods))
                .thenApply(dist -> {
                            try {
                                TimeUnit.SECONDS.sleep(2);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            return dist + "打好了" + ",服务员的米饭也打好了";
                        }
                );
        // 3.2 小明玩游戏
        System.out.println("小明开始玩游戏~~~~,等饭");
        System.out.println(cf.join());
        System.out.println("开始上菜~~~\n");
        TimeUnit.SECONDS.sleep(2);
        System.out.println("小明开始吃饭~~~~");
        System.out.println("小明结账走人~~~~");
        System.out.println();
        System.out.println("----------------------------------------------------------------------");
        System.out.println();
        CompletableFuture<String> cf1 = CompletableFuture
                .supplyAsync(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("700路到了");
                    return "700路到了";
                })
                .applyToEitherAsync(CompletableFuture.supplyAsync(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return "800路到了";
                }), dist -> dist);
        System.out.println(cf1.get() + ",小明坐它走了~~~~~~~");

    }

    public static class MakeFood implements Supplier<String> {

        List<String> foods;

        public MakeFood(List<String> foods) {
            this.foods = foods;
        }

        @SneakyThrows
        @Override
        public String get() {
            for (String food : foods) {
                System.out.println("开始做" + food);
                TimeUnit.SECONDS.sleep(1);
                System.out.println(food + "做好了");
            }
            return String.join(",", foods) + "都做好了,通知服务员~~~~\n";
        }
    }
}
