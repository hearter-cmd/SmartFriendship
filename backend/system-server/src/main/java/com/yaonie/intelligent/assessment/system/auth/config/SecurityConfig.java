package com.yaonie.intelligent.assessment.system.auth.config;


import com.yaonie.intelligent.assessment.server.common.model.constant.UserConstant;
import com.yaonie.intelligent.assessment.system.auth.filter.SecurityFilter;
import com.yaonie.intelligent.assessment.system.auth.handler.FailHandler;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.DigestUtils;

/**
 * <p>
 * SpringSecurity配置
 * </p>
 * 用户认证 ===> 授权
 *
 * @author 武春利
 * @since 2025-01-10
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /**
     * 自定义用户认证
     */
    @Lazy
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private SecurityFilter securityFilter;
    @Resource
    private FailHandler failHandler;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 登录失败处理器
        http
                // 关闭csrf防护
                .csrf(AbstractHttpConfigurer::disable)
                // 关闭表单登录功能
                .formLogin(AbstractHttpConfigurer::disable)
                // 使用自定义的userDetailsService
                .userDetailsService(userDetailsService)
                .exceptionHandling(e
                        ->
                        e.authenticationEntryPoint(failHandler)
                )
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        // 放行静态资源
                        .requestMatchers(
                                "/css/**", "/fonts/**", "/images/**",
                                "/js/**", "/v2/**", "/v3/**",
                                "/swagger-ui/**", "/swagger-resources/**",
                                "/api-docs/**", "/webjars/**", "/favicon.ico"
                        ).permitAll() // 直接放行
                        // 放行登录接口
                        .requestMatchers(
                                "/auth/**", "/user/login", "/doc.html",
                                "/user/captcha", "/user/register"
                        ).permitAll()
                        //其他所有路径 都需认证
                        .anyRequest().authenticated() // 都需要认证
                )
                .logout(logout -> {logout
                            .invalidateHttpSession(true)
                            .deleteCookies("SESSION");
                })
//                .rememberMe(rememberMe -> {
//                    rememberMe.rememberMeServices();
//                })
                .sessionManagement(manager -> {
                    manager
                            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                            .sessionFixation()
                            .migrateSession();
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * 配置认证管理器
     * @return AuthenticationManager对象
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        // 创建一个身份验证管理器
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        // 设置用户详细信息服务
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);

        // 设置密码编码器
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        // 创建一个提供程序管理器
        return new ProviderManager(daoAuthenticationProvider);
    }

    /**
     * 返回一个用于密码编码的PasswordEncoder Bean。
     *
     * @return 一个BCryptPasswordEncoder实例，用于对密码进行加密。
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return DigestUtils.md5DigestAsHex((UserConstant.SALT + rawPassword.toString()).getBytes());
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                String encode = encode(rawPassword);
                return encode.equals(encodedPassword);
            }
        };
    }

}
