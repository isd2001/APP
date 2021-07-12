package codegurus.cmm.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
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


}
