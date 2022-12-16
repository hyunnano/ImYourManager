package net.manager.iym.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.manager.iym.security.handler.Custom403Handler;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import javax.sql.DataSource;

@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true) //prePostEnabled = true는 원하는 곳에
// @PreAuthorize, @PostAuthorize 어노테이션을 이용하여 사전 또는 사후 체크 설정한다.
public class CustomSecurityConfig {//로그인을 안하면 보드에 접근을 못하게한다.
    //자동로그인을 위한 주입 필요
    private final DataSource dataSource;
    private final UserDetailsService userDetailsService;
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {//
        return new BCryptPasswordEncoder();

    }

//    @Bean
//    public AuthenticationSuccessHandler authenticationSuccessHandler() {
//        return new CustomSocialLoginSuccessHandler(passwordEncoder());
//    }//소셜로그인 관련 빈생성

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        log.info("------------로그인 환경설정중(configure)-------");

        //로그인 페이지
        http.formLogin()
                .loginPage("/member/login")
                .passwordParameter("pass")
                .usernameParameter("id")
                .defaultSuccessUrl("/index1");
        //CSRF 토큰 비활성화
        http.csrf().disable();


//        http.rememberMe()//아이디 기억사용 옵션이다.
//
//                .key("12345678")
//                .tokenRepository(persistentTokenRepository())
//                .userDetailsService(userDetailsService)
//                .tokenValiditySeconds(606024 * 30);

        http.logout() // 로그아웃 기능 작동함
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/index1")
                .invalidateHttpSession(true);

//        http.authorizeRequests()
//                        .antMatchers("notice/register").hasRole("ADMIN"); //ADMIN권한이 있는 사람만 접근 가능

        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler()); //403

     //   http.oauth2Login().loginPage("/member/login").successHandler(authenticationSuccessHandler());

        return http.build();
    }
//rememberme 기능은 쿠키를 생성할 때 쿠키의 값을 인코딩하기 위한 키값과 필요한 정보를 저장하는 tokenRepository를 지정하고
// persistentTokenRepository() 이용하여 처리함


    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        //로그인 오류 페이지 대신 로그인 페이지로 이동시킴
        return new Custom403Handler();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        log.info("------------web configure-------------------");

        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());

    }
}