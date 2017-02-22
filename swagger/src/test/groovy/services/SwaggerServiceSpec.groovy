package services

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonNode
import com.github.fge.jackson.JsonLoader
import com.github.fge.jsonschema.core.exceptions.ProcessingException
import com.github.fge.jsonschema.core.report.ProcessingMessage
import com.github.fge.jsonschema.core.report.ProcessingReport
import com.github.fge.jsonschema.main.JsonSchema
import com.github.fge.jsonschema.main.JsonSchemaFactory
import grails.test.mixin.TestFor
import grails.web.mapping.LinkGenerator
import io.swagger.annotations.Api
import io.swagger.models.*
import org.springframework.context.ApplicationContext
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import spock.lang.Shared
import spock.lang.Specification
import swagger.SwaggerService
import testResources.NonSwaggerAnnotatedController
import testResources.SwaggerAnnotatedController

@TestFor(SwaggerService)
class SwaggerServiceSpec extends Specification {

    def @Shared
    String jsonSchema_V2_0

    def setupSpec() {
        Resource schemaResource = new ClassPathResource("schema_v2_0.json")
        jsonSchema_V2_0 = schemaResource.getFile().getText('UTF-8')
    }

    def setup() {
        LinkGenerator linkGenerator = Mock(LinkGenerator.class)
        linkGenerator.getServerBaseURL() >> "localhost:8080"
        service.applicationContext = Mock(ApplicationContext.class)
        service.applicationContext.getBean(LinkGenerator.class) >> linkGenerator

        Info swaggerInfo = new Info(
                description: "Test Description",
                version: "Test Version",
                title: "Test Title",
                termsOfService: "Terms and services")

        swaggerInfo.setContact(new Contact(
                name: "Test Contact name",
                url: "www.test.com",
                email: "test@test.com"))

        swaggerInfo.license(new License(
                name: "License Name",
                url: "www.test.com"))

        service.swagger = new Swagger(info: swaggerInfo, host: "localhost:8080", schemes: [Scheme.HTTP], consumes: ["application/json"])
    }

    def cleanup() {
        service.applicationContext = null
    }

    void "test swagger JSON document generation as per JSON Schema for Swagger 2.0 API"() {

        given: "class annotated with valid swagger annotation"
        service.applicationContext.getBeansWithAnnotation(Api.class) >> [(SwaggerAnnotatedController.name): (new SwaggerAnnotatedController())]

        when: "service is called to generate swagger document as per Swagger 2.0 API"
        String jsonDocument = service.generateSwaggerDocument()

        then: "generated document should be as per schema corresponding to Swagger 2.0 API"
        validate(jsonDocument, jsonSchema_V2_0) == Boolean.TRUE
    }

    void "test invalid swagger JSON document generation as per JSON Schema for Swagger 2.0 API"() {

        given: "class not annotated with valid swagger annotation"
        service.applicationContext.getBeansWithAnnotation(Api.class) >> [(NonSwaggerAnnotatedController.name): (new NonSwaggerAnnotatedController())]

        when: "service is called to generate swagger document as per Swagger 2.0 API"
        String jsonDocument = service.generateSwaggerDocument()

        then: "generated document should not be as per schema corresponding to Swagger 2.0 API"
        validate(jsonDocument, jsonSchema_V2_0) == Boolean.FALSE
    }


    Boolean validate(String jsonData, String jsonSchema) {
        ProcessingReport report = null
        Boolean result = Boolean.FALSE
        try {
//            println("Applying schema: ${jsonSchema}")
            println("data: ${jsonData}")
            JsonNode schemaNode = JsonLoader.fromString(jsonSchema)
            JsonNode data = JsonLoader.fromString(jsonData)
            JsonSchemaFactory factory = JsonSchemaFactory.byDefault()
            JsonSchema schema = factory.getJsonSchema(schemaNode)
            report = schema.validate(data)
        } catch (JsonParseException jpex) {
            println("Error. Something went wrong trying to parse json data: ${jsonData} \n or json schema: ${jsonSchema} \n Are the double quotes included? " + jpex.getMessage())
            jpex.printStackTrace()
        } catch (ProcessingException pex) {
            println("Error. Something went wrong trying to process json data: ${jsonData}\n with json schema: ${jsonSchema}" + pex.getMessage())
            pex.printStackTrace()
        } catch (IOException e) {
            println("Error. Something went wrong trying to read json data: ${jsonData}\n or json schema: ${jsonSchema}")
            e.printStackTrace()
        }
        if (report != null) {
            Iterator<ProcessingMessage> iter = report.iterator()
            while (iter.hasNext()) {
                ProcessingMessage pm = iter.next()
                println("Processing Message: " + pm.getMessage())
            }
            result = report.isSuccess()
        }
        println(" Result=" + result)
        return result
    }
}
