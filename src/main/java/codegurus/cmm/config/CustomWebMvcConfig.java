package codegurus.cmm.config;

import codegurus.cmm.aop.AuthInterceptor;
import codegurus.cmm.jwt.CustomSecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * web mvc 에 대한 설정이 필요할 경우 사용하자.
 *
 * @author 이프로
 * @version 2021.06
 */
@Slf4j
@Configuration
@EnableWebMvc
public class CustomWebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

//        String[] excludePatterns = (CustomSecurityConfig.ANT_MATCHERS_WEB_IGNORE +", "+ CustomSecurityConfig.ANT_MATCHERS_HTTP_PERMIT_ALL).split(",");
        String[] excludePatterns = ArrayUtils.addAll(CustomSecurityConfig.ANT_MATCHERS_WEB_IGNORE, CustomSecurityConfig.ANT_MATCHERS_HTTP_PERMIT_ALL);
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludePatterns);
    }
}
