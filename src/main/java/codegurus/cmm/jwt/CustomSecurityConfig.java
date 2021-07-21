package codegurus.cmm.jwt;

import codegurus.auth.AuthService;
import codegurus.cmm.aop.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * context-security.xml 을 대체하는 spring security 메인 설정 파일
 * <p>
 *  - JWT 구성을 하던 중에 생성 필요 발생
 *  - context-security.xml 과 함께 사용하려 시도했으나 서버 기동시 에러 발생 (최종적으로 context-security.xml 는 사용하지 않음)
 *  - context-security.xml 로만으로는 JWT 에서 요구하는 동적 설정 구성을 다 만족할 수 없어서 context-security.xml 의 내용을 일단 그대로 옮겨 오고 JWT에 필요한 것들을 추가하는 방향으로 작업
 *      - context-security.xml 에서 egov 관련 항목들은 옮겨올 수 없었으나, 기존에도 사용하지 않았던 것으로 판단되어 SKIP.
 *
 * @author 이프로
 * @version 2021.06
 */
@EnableWebSecurity
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Autowired
    private AuthService authService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    public static final String[] ANT_MATCHERS_WEB_IGNORE = { "/css/**", "/html/**", "/images/**", "/js/**", "/resource/**", "/resources/**", "" +
            "/v2/**", "/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/download/**", "/index.jsp" }; // TODO: 파일 다운로드(/download)에 대한 권한제어 필요 여부 검토

    public static final String[] REGEX_MATCHERS_WEB_IGNORE = { "\\A/WEB-INF/jsp/.*\\Z" };

    public static final String[] ANT_MATCHERS_HTTP_PERMIT_ALL = { "/auth/login", "/auth/checkUserDup", "/auth/register", "/auth/trialRegister" };

    /**
     * WebSecurity configure
     * <p>
     * - 주로 ignore 할 목적으로 정의 (e.g. 정적 자원들)
     *
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {

        web
            .ignoring()
                .antMatchers(ANT_MATCHERS_WEB_IGNORE)
            .and().ignoring()
                .regexMatchers(REGEX_MATCHERS_WEB_IGNORE);
    }

    /**
     * HttpSecurity configure
     * <p>
     * - security 설정의 주된 내용을 구성하는 메서드
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            // CSRF 설정 Disable                                                                                                                                                                  
            .csrf().disable()

            // exception handling 할 때 우리가 만든 클래스를 추가
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//            .accessDeniedHandler(jwtAccessDeniedHandler) // 현 시점에서는 얘까지는 필요 없어 보인다. (일단 response json 처리도 안함)

//            // h2-console 을 위한 설정을 추가
//            .and()
//            .headers()
//            .frameOptions()
//            .sameOrigin()

            // 시큐리티는 기본적으로 세션을 사용
            // 여기서는 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            // 로그인, 회원가입 API 는 토큰이 없는 상태에서 요청이 들어오기 때문에 permitAll 설정
            .and()
            .authorizeRequests()
            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll() // cors setting 1
            .antMatchers(ANT_MATCHERS_HTTP_PERMIT_ALL).permitAll()
            .anyRequest().authenticated()   // 나머지 API 는 전부 인증 필요

             // JwtFilter 를 addFilterBefore 로 등록했던 JwtSecurityConfig 클래스를 적용
            .and()
            .apply(new JwtSecurityConfig(tokenProvider))

            .and().cors(); // cors setting 2
    }

     // cors setting 3
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        // configuration.addAllowedOrigin("*");
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedOrigin("http://codegurus.iptime.org:3002");

        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    /**
     * AuthenticationManagerBuilder configure
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
            .userDetailsService(authService)
            .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }



}