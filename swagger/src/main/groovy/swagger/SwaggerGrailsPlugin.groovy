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
//            "grails-app/controller/testResources/**",
//            "**/testResources**",
            "**/logback.groovy",
            "**/logback.xml",
            "**/logback**",
            "**/logback/**",
//            "**/co/**",
//            "**/dto/**",
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
            // TODO Implement runtime spring config (optional)

            swagger(Swagger) {
                Map swaggerAsMap = (config.swagger as Map) ?: [:]
                Map infoAsMap = swaggerAsMap.info ?: [:]
                Info swaggerInfo = new Info(
                        description: infoAsMap.description ?: StringUtils.EMPTY,
                        version: infoAsMap.version ?: StringUtils.EMPTY,
                        title: infoAsMap.title ?: StringUtils.EMPTY,
                        termsOfService: infoAsMap.termsOfServices ?: StringUtils.EMPTY)

                Map contact = infoAsMap.contact ?: [:]
                swaggerInfo.setContact(new Contact(
                        name: contact.name ?: StringUtils.EMPTY,
                        url: contact.url ?: StringUtils.EMPTY,
                        email: contact.email ?: StringUtils.EMPTY))

                Map license = infoAsMap.license ?: [:]
                swaggerInfo.license(new License(
                        name: license.name ?: StringUtils.EMPTY,
                        url: license.url ?: StringUtils.EMPTY))
                info = swaggerInfo
//                host = swaggerAsMap.host ?: "localhost:8080"
                schemes = swaggerAsMap.schemes ?: [Scheme.HTTP]
                consumes = swaggerAsMap.consumes ?: ["application/json"]
            }
        }
    }
}
