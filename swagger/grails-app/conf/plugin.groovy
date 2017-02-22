import io.swagger.models.Scheme

swagger {
    info {
        description = "Move your app forward with the Swagger API Documentation"
        version = "ttn-swagger-1.0.0"
        title = "Swagger API"
        termsOfServices = "http://swagger.io/"
        contact {
            name = "Contact Us"
            url = "http://swagger.io"
            email = "contact@gmail.com"
        }
        license {
            name = "licence under http://www.tothenew.com/"
            url = "http://www.tothenew.com/"
        }
    }
    schemes = [Scheme.HTTP]
    consumes = ["application/json"]
}
