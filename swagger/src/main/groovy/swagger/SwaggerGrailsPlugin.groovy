package swagger

import grails.plugins.Plugin
import io.swagger.converter.ModelConverters
import io.swagger.models.*
import org.apache.commons.lang.StringUtils

class SwaggerGrailsPlugin extends Plugin {

    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "3.2.0 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = "Swagger" // Headline display name of the plugin
    def author = "Bin Le"
    def authorEmail = "bin.le.code@gmail.com"
    def description = '''\
Grails Plugin For Swagger API Documentation
'''
    def profiles = ['web']

    // URL to the plugin's documentation
    def documentation = "https://github.com/binlecode/swagger"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
//    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.codehaus.org/grails-plugins/" ]

    def observe = ['controllers', 'services']
    def loadAfter = ['controllers', 'services']

    Closure doWithSpring() { {->
        swagger(Swagger) {
            Map swaggerConfig = (config.swagger as Map) ?: [:]
            Map infoConfig = swaggerConfig.info ?: [:]
            Info swaggerInfo = new Info(
                    description: infoConfig.description ?: StringUtils.EMPTY,
                    version: infoConfig.version ?: StringUtils.EMPTY,
                    title: infoConfig.title ?: StringUtils.EMPTY,
                    termsOfService: infoConfig.termsOfServices ?: StringUtils.EMPTY
            )
            Map contactConfig = infoConfig.contact ?: [:]
            swaggerInfo.setContact(new Contact(
                    name: contactConfig.name ?: StringUtils.EMPTY,
                    url: contactConfig.url ?: StringUtils.EMPTY,
                    email: contactConfig.email ?: StringUtils.EMPTY)
            )
            Map licenseConfig = infoConfig.license ?: [:]
            swaggerInfo.license(new License(
                    name: licenseConfig.name ?: StringUtils.EMPTY,
                    url: licenseConfig.url ?: StringUtils.EMPTY)
            )
            info = swaggerInfo
//                host = swaggerAsMap.host ?: "localhost:8080"
            schemes = swaggerConfig.schemes ?: [Scheme.HTTP]
            consumes = swaggerConfig.consumes ?: ["application/json"]
        }
    } }

    void doWithDynamicMethods() {
        // TODO Implement registering dynamic methods to classes (optional)
    }

    void doWithApplicationContext() {
        // add custom model converters
        ModelConverters.instance.addConverter(new GormPropertyConverter())
    }

    void onChange(Map<String, Object> event) {
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    void onConfigChange(Map<String, Object> event) {
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    void onShutdown(Map<String, Object> event) {
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
