import org.junit.jupiter.api.Test;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-13
 */
public class Tessss {
    @Test
    void test1() {
        int threadCount = 0;
        try {
            while (threadCount < 100000) {
                new Thread(() -> {
                    try {
                        Thread.sleep(Long.MAX_VALUE);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
                threadCount++;
            }
            System.out.println("最多可创建线程数: " + threadCount);
        } catch (Throwable e) {
            System.out.println("最多可创建线程数: " + threadCount);
            e.printStackTrace();
        }
    }
}
