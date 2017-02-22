package swagger

import org.springframework.http.HttpStatus

class ApiDocController {

    static responseFormats = ['json']
    static namespace = 'v1'
    static allowedMethods = [getDocuments: ["GET"]]
    SwaggerService swaggerService

    def getDocuments() {
        if (request.getHeader('accept') && request.getHeader('accept').indexOf('application/json') > -1) {
            String swaggerJson = "Some error occurred."
            try {
                swaggerJson = swaggerService.generateSwaggerDocument()
            } catch (Exception e) {
                e.printStackTrace();
                response.status = HttpStatus.INTERNAL_SERVER_ERROR.value()
            }
            response.contentType = 'application/json'
            render swaggerJson
        } else {
            String apiLink = g.createLink(uri: '/apidoc')
            redirect uri: "/webjars/swagger-ui/2.2.5/index.html?url=${apiLink}"
        }
    }
}
