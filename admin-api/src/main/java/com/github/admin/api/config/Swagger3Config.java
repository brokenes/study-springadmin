//package com.github.admin.api.config;
//
//import io.swagger.v3.oas.annotations.Operation;
//import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
//import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
//import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
//import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
//import org.springframework.boot.actuate.endpoint.web.*;
//import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
//import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
//import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.env.Environment;
//import org.springframework.http.HttpMethod;
//import springfox.documentation.builders.*;
//import springfox.documentation.schema.ScalarType;
//import springfox.documentation.service.*;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
////@Configuration
////@EnableOpenApi
//public class Swagger3Config {
//    @Bean
//    public Docket createRestApi(){
//        //返回文档概要信息
//        return new Docket(DocumentationType.OAS_30)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.withMethodAnnotation(Operation.class))
//                .paths(PathSelectors.any())
//                .build()
//                .globalRequestParameters(getGlobalRequestParameters())
//                .globalResponses(HttpMethod.GET,getGlobalResponseMessage())
//                .globalResponses(HttpMethod.POST,getGlobalResponseMessage());
//    }
//
//    /*
//    生成接口信息，包括标题，联系人等
//     */
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("后台管理系统")
//                .description("后台管理系统")
//                .contact(new Contact("admin","http://localhost:8179/swagger-ui/index.html","xxxx@gmail.com"))
//                .version("1.0")
//                .build();
//    }
//
//
//    /*
//    封装全局通用参数
//     */
//    private List<RequestParameter> getGlobalRequestParameters() {
//        List<RequestParameter> parameters=new ArrayList<>();
//        parameters.add(new RequestParameterBuilder()
//                .name("uuid")
//                .description("设备uuid")
//                .required(true)
//                .in(ParameterType.QUERY)
//                .query(q->q.model(m->m.scalarModel((ScalarType.STRING))))
//                .required(false)
//                .build());
//
//        return parameters;
//    }
//    /*
//    封装通用相应信息
//     */
//    private List<Response> getGlobalResponseMessage() {
//        List<Response> responseList=new ArrayList<>();
//        responseList.add(new ResponseBuilder().code("404").description("未找到资源").build());
//        return responseList;
//    }
//
//
//    @Bean
//    public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(
//            WebEndpointsSupplier webEndpointsSupplier, ServletEndpointsSupplier servletEndpointsSupplier,
//            ControllerEndpointsSupplier controllerEndpointsSupplier, EndpointMediaTypes endpointMediaTypes,
//            CorsEndpointProperties corsProperties, WebEndpointProperties webEndpointProperties,
//            Environment environment) {
//        List<ExposableEndpoint<?>> allEndpoints = new ArrayList<>();
//        Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
//        allEndpoints.addAll(webEndpoints);
//        allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
//        allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
//        String basePath = webEndpointProperties.getBasePath();
//        EndpointMapping endpointMapping = new EndpointMapping(basePath);
//        boolean shouldRegisterLinksMapping = webEndpointProperties.getDiscovery().isEnabled() &&
//                (org.springframework.util.StringUtils.hasText(basePath) || ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
//        return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints, endpointMediaTypes, corsProperties.toCorsConfiguration(), new EndpointLinksResolver(allEndpoints, basePath), shouldRegisterLinksMapping, null);
//    }
//}
