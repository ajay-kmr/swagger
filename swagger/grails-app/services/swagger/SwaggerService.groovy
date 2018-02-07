package swagger

import com.fasterxml.jackson.core.JsonProcessingException
import grails.web.mapping.LinkGenerator
import io.swagger.annotations.Api
import io.swagger.models.Swagger
import io.swagger.servlet.Reader
import io.swagger.util.Json
import org.apache.commons.lang.StringUtils
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware

import java.lang.annotation.Annotation

class SwaggerService implements ApplicationContextAware {
    Swagger swagger

    ApplicationContext applicationContext

    String generateSwaggerDocument() {
        return getJsonDocument(scanSwaggerResources())
    }

    String generateSwaggerGroupDocument(String groupName) {
        return getJsonDocument(scanSwaggerGroupResources(groupName))
    }

    Swagger scanSwaggerGroupResources(String groupName) {
        Swagger groupSwagger = new Swagger()
                .info(swagger.info)
                .consumes(swagger.consumes)
                .schemes(swagger.schemes)
                .host(getSwaggerHost())

        Map<String, Object> swaggerResourcesAsMap =
                applicationContext.getBeansWithAnnotation(SwaggerApiGroup.class).findAll { name, bean ->
            // it must have Api annotation and matching swagger group name
            bean.class.getAnnotation(Api.class) != null &&
                    bean.class.getAnnotation(SwaggerApiGroup.class).value() == groupName
        }

        List<Class> swaggerResources = swaggerResourcesAsMap.collect { it.value?.class }
        if (swaggerResources) {
            Reader.read(groupSwagger, new HashSet<Class<?>>(swaggerResources))
        }
        return groupSwagger
    }

    Swagger scanSwaggerResources() {
        swagger.setHost(getSwaggerHost())
        Map<String, Object> swaggerResourcesAsMap = applicationContext.getBeansWithAnnotation(Api.class)

//        List<Class> swaggerResources = swaggerResourcesAsMap.collect { it.value?.class }

        // exclude @SwaggerApiGroup annotated ones with excludeFromDefault = true
        List<Class> swaggerResources = swaggerResourcesAsMap.findAll { name, bean ->
            !(bean.class.getAnnotation(SwaggerApiGroup.class)?.excludeFromDefault())
        }.collect { it.value?.class }

        if (swaggerResources) {
            Reader.read(swagger, new HashSet<Class<?>>(swaggerResources))
        }
        return swagger
    }

    /**
     * utility to support multi-module project.
     * @return host string
     */
    protected String getSwaggerHost() {
        // Below code is written to support multi-module project.
        LinkGenerator linkGenerator = applicationContext.getBean(LinkGenerator.class)
        String host = linkGenerator.getServerBaseURL()
        host = host.replace($/http:///$, StringUtils.EMPTY)
        host = host.replace($/https:///$, StringUtils.EMPTY)
        return host
    }

    static String getJsonDocument(Swagger swagger) {
        String swaggerJson = null
        if (swagger != null) {
            try {
                swaggerJson = Json.mapper().writeValueAsString(swagger)
            } catch (JsonProcessingException e) {
                e.printStackTrace()  //todo: better error bubbling is needed here
            }
        }
        return swaggerJson
    }
}

