package com.yaonie.intelligent.assessment.server.match;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-02-04
 */
@SpringBootApplication(scanBasePackages = {
        "com.yaonie.intelligent.assessment",
})
public class MatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(MatchApplication.class, args);
    }
}
