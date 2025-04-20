package me.practice.springbootproject2.config;

import lombok.RequiredArgsConstructor;
import me.practice.springbootproject2.service.UserDetailService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// IoC 컨테이너: 스프링에서 객체를 만들고 관리하는 컨테이너

// @Component
// - 내부에서 직접 접근 가능한 클래스에 사용
// - class에 선언
// - @Component의 종류: configuration - 설정, controller - 컨트롤러 등

// @Configuration + @Bean
// - 스프링 IoC 컨테이너에 등록된 객체, 스프링은 @Configuration이 선언된 클래스에서 @Bean을 찾아서 등록
// - 보통 외부 라이브러리가 제공하는 객체를 활용할 때 @Bean 사용, @Bean 싱글톤 패턴으로 관리
// - method에 선언

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailService userService;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    
    // JWT 사용 코드
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(management ->
                        management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 헤더 확인용 커스텀 필터
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll());
        return http.build();
    }

    // 세션 사용 코드
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/css/**", "/", "/login", "/join").permitAll()
//                        .anyRequest().authenticated())
//                .formLogin(login -> login
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/board", true))
//                .logout(logout -> logout
//                        .logoutSuccessUrl("/"))
//                .sessionManagement(sessionManagement ->
//                        sessionManagement
//                                .maximumSessions(1)
//                                .maxSessionsPreventsLogin(true));
//         return http.build();
//    }

//    @Bean
//    public AuthenticationManager authenticationManager() throws Exception {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setUserDetailsService(userService);
//        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
//        return new ProviderManager(daoAuthenticationProvider);
//    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
