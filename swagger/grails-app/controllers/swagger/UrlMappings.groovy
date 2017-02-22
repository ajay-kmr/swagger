package swagger

class UrlMappings {

    static mappings = {
        "/apidoc/$action?/$id?"(controller: "apiDoc", action: "getDocuments")
    }
}
