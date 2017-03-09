package swagger

import com.fasterxml.jackson.core.JsonProcessingException
import grails.web.mapping.LinkGenerator
import io.swagger.annotations.Api
import io.swagger.models.Swagger
import io.swagger.servlet.Reader
import io.swagger.util.Json
import io.swagger.util.Yaml
import org.apache.commons.lang.StringUtils
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware

class SwaggerService implements ApplicationContextAware {
    Swagger swagger

    ApplicationContext applicationContext

    String generateSwaggerDocument() {
        return getJsonDocument(scanSwaggerResources())
    }

    Swagger scanSwaggerResources() {
        // Below code is written to support multi-module project.
        LinkGenerator linkGenerator = applicationContext.getBean(LinkGenerator.class)
        String host = linkGenerator.getServerBaseURL()
        host = host.replace($/http:///$, StringUtils.EMPTY)
        host = host.replace($/https:///$, StringUtils.EMPTY)
        swagger.setHost(host)
        Map<String, Object> swaggerResourcesAsMap = applicationContext.getBeansWithAnnotation(Api.class)
        List<Class> swaggerResources = swaggerResourcesAsMap.collect { it?.value?.class }
        if (swaggerResources) {
            Reader.read(swagger, new HashSet<Class<?>>(swaggerResources));
        }
        return swagger;
    }

     static String getJsonDocument(Swagger swagger) {
        String resultantJSON = null;
        if (swagger != null) {
            try {
                resultantJSON = Json.mapper().writeValueAsString(swagger);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return resultantJSON;
    }

     static String getYamlDocument(Swagger swagger) {
        String resultantJSON = null;
        if (swagger != null) {
            try {
                resultantJSON = Yaml.mapper().writeValueAsString(swagger);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return resultantJSON;
    }
}

