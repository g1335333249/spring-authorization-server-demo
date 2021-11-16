package com.g1335333249.spring.authorization.server.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @Author: guanpeng
 * @Date: Create at 2021/11/16 15:33
 * @Description:
 * @Modified By:
 */
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(withDefaults()).authorizeRequests()
                .antMatchers("/h2-console/**").permitAll() // 放行 H2 的请求
                .and().csrf().ignoringAntMatchers("/h2-console/**") // 禁用 H2 控制台的 CSRF 防护
                .and().headers().frameOptions().sameOrigin();

        return http.build();
    }


    @Autowired
    private DataSource dataSource;

    // @formatter:off
    @Bean
    UserDetailsService users() {
        // 官方demo使用inmemory方式，本文使用jdbc方式。更直观
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user1")
//                .password("password")
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
        return new JdbcUserDetailsManager(dataSource);
    }
}
