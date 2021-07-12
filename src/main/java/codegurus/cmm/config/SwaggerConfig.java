package codegurus.cmm.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

/**
 * springfox swagger 환경설정 클래스
 *
 * @author 이프로
 * @version 2021.06
 */
@Configuration
@EnableSwagger2
//@EnableWebMvc // 최초 여기에 선언했었으나, CustomWebMvcConfig 에서 사용하기로 함.
//@Import(BeanValidatorPluginsConfiguration.class) // springfox validation이 가능할까 해서 추가한 부분. 아직 별 효과는 없다.
public class SwaggerConfig {

    /**
     * api 설정
     *
     * @return
     */
    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("groupName")
                .apiInfo(apiInfo())
                .select()
//                .apis(RequestHandlerSelectors.basePackage("codegurus"))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)) // 어차피 컨트롤러 메서드 이름은 명시적으로 필요할테니
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()));
    }

    /**
     * api 정보
     *
     * @return
     */
    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("한솔 플라톤RC 사용자앱")
                .description("사용자앱 화면 개발 및 테스트를 위한 페이지")
//                .termsOfServiceUrl("https://www.egovframe.go.kr/wiki/doku.php?id=egovframework:hyb:gate_page")
//                .license("Apache License Version 2.0")
//                .licenseUrl("https://www.egovframe.go.kr")
//                .version("3.10")
                .build();
    }


    //============ swagger ui로 api 테스트시, access token을 ui에 미리 바인딩 할 수 있도록 하는 부분을 위한 코드 - start ============
    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return springfox.documentation.spi.service.contexts.SecurityContext
                .builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }
    //============ swagger ui로 api 테스트시, access token을 ui에 미리 바인딩 할 수 있도록 하는 부분을 위한 코드 - end ==============


    // spring validator custom .. 을 할 때 이걸 사용한다고 하는데..
//    @Bean
//    public MethodValidationPostProcessor methodValidationPostProcessor() {
//        return new MethodValidationPostProcessor();
//    }



//    private static final String API_NAME = "OO API";
//    private static final String API_VERSION = "0.0.1";
//    private static final String API_DESCRIPTION = "OO API 명세서";

//    @Bean
//    public Docket api() {
//
//        Parameter parameterBuilder = new ParameterBuilder()
//            .name(HttpHeaders.AUTHORIZATION)
//                .description("Access Token")
//                .modelRef(new ModelRef("string"))
//                .parameterType("header")
//                .required(false)
//                .build();
//
//        List<Parameter> globalParamters = new ArrayList<>();
//        globalParamters.add(parameterBuilder);
//
//        return new Docket(DocumentationType.SWAGGER_2)
//                .globalOperationParameters(globalParamters)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("codegurus"))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    public ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title(API_NAME)
//                .version(API_VERSION)
//                .description(API_DESCRIPTION)
//                .build();
//    }



}