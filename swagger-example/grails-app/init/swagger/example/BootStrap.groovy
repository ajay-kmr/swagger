package swagger.example

import grails.core.GrailsApplication
import grails.web.mapping.UrlMapping
import groovy.util.logging.Slf4j

@Slf4j
class BootStrap {

    GrailsApplication grailsApplication

    def init = { servletContext ->

        Book.withTransaction {
            10.times { idx ->
                log.info "inserting book $idx"
                new Book(title: "sample book title #${idx}").save(failOnError: true)
            }
        }

        // println url mappings
        log.info " ** url mappings **"
        grailsApplication.mainContext.grailsUrlMappingsHolder.urlMappings.each { UrlMapping urlMapping ->
            log.info " => ${urlMapping.properties['mappingName', 'controllerName', 'actionName', 'httpMethod', 'viewName']} "

        }

    }
    def destroy = {
    }
}
