package com.hy.demo.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sun.rmi.runtime.Log;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터 -> 스프링 필터체인 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() // authenticated는 인증해야한다는것 즉 로그인필요
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or  hasRole('ROLE_MANAGER')") //access는 권한이필요하다는것 (로그인포함)
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll() // 위의 페이지 3개 이외는 아무나 접근하게 해주는 체인.
                .and() //만약에 권한이 없는 페이지로 들어갈때 로그인페이지로 가게해주는 체인.
                .formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/login") //login 주소가 호출되면 시큐리티가 낚아채서 대신 로그인 진행해줌 즉 컨트롤러에 /login필요없음
                .defaultSuccessUrl("/"); //로그인 완료됬을대 페이지

    }
}
