package swagger

/**
 * Since Grails 3+, urlMappings from plugin is merged to host application.
 */
class UrlMappings {

    static mappings = {
        "/apidoc/$action?/$id?"(controller: "apiDoc", action: "getDocuments")
    }
}
