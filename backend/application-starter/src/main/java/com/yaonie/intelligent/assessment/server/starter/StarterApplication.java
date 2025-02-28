package com.yaonie.intelligent.assessment.server.starter;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-02-08
 */
@SpringBootApplication(
        scanBasePackages = {
                "com.yaonie.intelligent.assessment",
        }
)
public class StarterApplication {
    public static void main(String[] args) {
        SpringApplication.run(StarterApplication.class, args);
    }
}
