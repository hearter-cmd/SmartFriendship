import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-08-27 12:10
 * @Author 武春利
 * @CreateTime 2024-08-27
 * @ClassName RxJavaTest
 * @Project backend
 * @Description : 
 */
public class RxJavaTest {
    @Test
    void test1() throws InterruptedException {
        Flowable<Long> flowable = Flowable.interval(1, TimeUnit.SECONDS)
                .map(item -> item + 1)
                .subscribeOn(Schedulers.io());
        flowable
                .doOnNext(item -> {
                    System.out.println(item);
                })
                .observeOn(Schedulers.io())
                .subscribe();
        Thread.sleep(30000);
    }
}
