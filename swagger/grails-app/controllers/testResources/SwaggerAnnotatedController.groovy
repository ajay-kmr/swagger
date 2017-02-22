package testResources

import dto.CityDTO
import dto.ResponseDTO
import grails.converters.JSON
import io.swagger.annotations.*
import org.springframework.web.multipart.MultipartFile

@Api(value = "/api/v1", tags = ["City"], description = "City Api's")
class SwaggerAnnotatedController {
    static namespace = 'v1'

    @ApiOperation(
            value = "List Cities",
            nickname = "city/{cityId}",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = "GET",
            response = ResponseDTO.class
    )
    @ApiResponses([
            @ApiResponse(code = 405, message = "Method Not Allowed. Only GET is allowed"),
            @ApiResponse(code = 404, message = "Method Not Found")]
    )
    @ApiImplicitParams([
            @ApiImplicitParam(name = "cityId", paramType = "path", required = true, value = "City Id", dataType = "string"),
            @ApiImplicitParam(name = "applicationType", paramType = "header", required = true, defaultValue = "web", value = "Application Types", dataType = "string"),
            @ApiImplicitParam(name = "Accept-Language", paramType = "header", required = true, defaultValue = "en", value = "Accept-Language", dataType = "string")
    ])
    def getCity(String cityId) {
        render(new ResponseDTO(status: true, message: "New Delhi", data: ["key1": "value1", "key2": "value2"]) as JSON)
    }

    @ApiOperation(
            value = "List Cities",
            nickname = "city/list",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = "GET",
            response = ResponseDTO.class
    )
    @ApiResponses([
            @ApiResponse(code = 405, message = "Method Not Allowed. Only GET is allowed"),
            @ApiResponse(code = 404, message = "Method Not Found")]
    )
    @ApiImplicitParams([
            @ApiImplicitParam(name = "offset", paramType = "query", required = true, value = "Offset", dataType = "integer"),
            @ApiImplicitParam(name = "limit", paramType = "query", required = true, value = "Max size", dataType = "integer"),
            @ApiImplicitParam(name = "applicationType", paramType = "header", required = true, defaultValue = "web", value = "Application Types", dataType = "string"),
            @ApiImplicitParam(name = "Accept-Language", paramType = "header", required = true, defaultValue = "en", value = "Accept-Language", dataType = "string")
    ])
    def getCityList(Integer offset, Integer limit) {
        render(new ResponseDTO(status: true, message: "City List fetched successfully", data: ["key1": "value1", "key2": "value2"]) as JSON)
    }

    @ApiOperation(
            value = "Create City",
            notes = "Creates a new City. Accepts a City json.",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = "POST",
            nickname = "/city/createUpdate",
            response = ResponseDTO.class
    )
    @ApiResponses([
            @ApiResponse(code = 405, message = "Method Not Allowed. Only POST is allowed"),
            @ApiResponse(code = 404, message = "Method Not Found")
    ])
    @ApiImplicitParams([
            @ApiImplicitParam(name = "body", paramType = "body", required = true, value = "Requires City Details", dataType = "dto.CityDTO"),
            @ApiImplicitParam(name = "applicationType", paramType = "header", required = true, defaultValue = "web", value = "Application Types", dataType = "string"),
            @ApiImplicitParam(name = "Accept-Language", paramType = "header", required = true, defaultValue = "en", value = "Accept-Language", dataType = "string")
    ])
    def createOrUpdateCity(CityDTO cityDTO) {
        render(new ResponseDTO(status: true, message: "City updated successfully", data: cityDTO) as JSON)
    }

    @ApiOperation(
            value = "Delete City",
            notes = "Deletes a City.Accepts a City ID .",
            produces = "application/json",
            consumes = "application/json",
            nickname = "/city/{cityId}",
            response = ResponseDTO.class,
            httpMethod = "DELETE")
    @ApiResponses([
            @ApiResponse(code = 405, message = "Method Not Allowed. Only Delete is allowed"),
            @ApiResponse(code = 404, message = "Method Not Found")])
    @ApiImplicitParams([
            @ApiImplicitParam(name = 'cityId', paramType = 'path', required = true, value = "Requires City id for delete", dataType = "string"),
            @ApiImplicitParam(name = "applicationType", paramType = "header", required = true, defaultValue = "web", value = "Application Types", dataType = "string"),
            @ApiImplicitParam(name = "Accept-Language", paramType = "header", required = true, defaultValue = "en", value = "Accept-Language", dataType = "string")
    ])
    def deleteCity(String cityId) {
        render(new ResponseDTO(status: true, message: "City deleted successfully") as JSON)
    }

    @ApiOperation(value = "Upload File Example",
            notes = "Upload File Example",
            nickname = "/city/upload",
            response = ResponseDTO.class,
            httpMethod = "POST")
    @ApiResponses(value = [
            @ApiResponse(code = 405, message = "Method Not Allowed. Only POST is allowed"),
            @ApiResponse(code = 404, message = "Method Not Found")
    ])
    @ApiImplicitParams([
            @ApiImplicitParam(name = 'cityFile', paramType = 'form',
                    required = true,
                    value = "Requires File Containing City Information",
                    dataType = "java.io.File")])
    ResponseDTO uploadCityData() {
        MultipartFile file = request.getFile('cityFile')
        //Do with file
        render(new ResponseDTO(status: true, message: "File with name ${file?.originalFilename} uploaded successfully") as JSON)
    }

}
