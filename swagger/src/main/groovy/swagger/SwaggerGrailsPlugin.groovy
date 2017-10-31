package swagger

import grails.plugins.Plugin
import io.swagger.models.*
import org.apache.commons.lang.StringUtils

class SwaggerGrailsPlugin extends Plugin {

    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "3.1.9 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp",
            "**/services.swaggerResources**",
            "**/logback.groovy",
            "**/logback.xml",
            "**/logback**",
            "**/logback/**",
            "**/co/**",
            "**/dto/**",
            "**/schema_v2_0.json"
    ]

    def title = "Grails Swagger"
    def author = "Ajay Kumar"
    def authorEmail = "ajay.kumar@totthenew.com"
    def description = '''\
Grails Plugin For Swagger Documentation
'''
    def profiles = ['web']

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/swagger"

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

    Closure doWithSpring() {
        { ->
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
        }
    }
}
