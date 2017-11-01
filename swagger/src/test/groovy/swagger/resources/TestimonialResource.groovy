package swagger.resources

import swagger.co.TestimonialCO
import swagger.dto.ResponseDTO
import swagger.dto.TestimonialDTO
import grails.converters.JSON
import io.swagger.annotations.*

@Api(value = "/api/v1", tags = ["Testimonial"], description = "Testimonial Api's")
class TestimonialResource {
    
    @ApiOperation(
            value = "List Testimonial",
            notes = "List Testimonial",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = "GET",
            nickname = "testimonial",
            response = ResponseDTO.class)
    @ApiResponses([
            @ApiResponse(code = 405,
                    message = "Method Not Allowed. Only GET is allowed"),
            @ApiResponse(code = 404,
                    message = "Method Not Found")])
    @ApiImplicitParams([
            @ApiImplicitParam(name = "id",
                    value = "Requires integer Testimonial Id",
                    defaultValue = "1",
                    required = false,
                    paramType = "query",
                    dataType = "string"),
            @ApiImplicitParam(name = "applicationType",
                    paramType = "header",
                    required = true,
                    defaultValue = "web",
                    value = "Application Types",
                    dataType = "string"),
            @ApiImplicitParam(name = "Accept-Language",
                    paramType = "header",
                    required = true,
                    defaultValue = "en",
                    value = "Accept-Language",
                    dataType = "string")
    ])
    def getTestimonial(TestimonialDTO testimonialDTO) {
        (new ResponseDTO(status: Boolean.TRUE, data: testimonialDTO))
    }

    @ApiOperation(
            value = "Save/Update Testimonial",
            notes = "Save/Update testimonial",
            produces = "application/json",
            consumes = "multipart/form-data",
            httpMethod = "POST",
            nickname = "testimonial",
            response = ResponseDTO.class
    )
    @ApiResponses([@ApiResponse(code = 405,
            message = "Method Not Allowed. Only POST is allowed"),
            @ApiResponse(code = 404,
                    message = "Method Not Found")
    ])
    @ApiImplicitParams([
            @ApiImplicitParam(name = 'body',
                    paramType = 'body',
                    required = true,
                    value = "Requires Testimonial Multipart form data",
                    dataType = "com.dt.dtcore.dto.TestimonialDTO"),
            @ApiImplicitParam(name = "applicationType",
                    paramType = "header",
                    required = true,
                    defaultValue = "web",
                    value = "Application Types",
                    dataType = "string"),
            @ApiImplicitParam(name = "Accept-Language",
                    paramType = "header",
                    required = true,
                    defaultValue = "en",
                    value = "Accept-Language",
                    dataType = "string")
    ])
    def saveOrUpdateTestimonial(TestimonialDTO testimonialDTO) {
        (new ResponseDTO(status: Boolean.TRUE, data: testimonialDTO))
    }

    @ApiOperation(
            tags = ["Public"],
            value = "Create Testimonial",
            notes = "Create testimonial",
            produces = "application/json",
            consumes = "multipart/form-data",
            httpMethod = "POST",
            nickname = "public/testimonial",
            response = ResponseDTO.class
    )
    @ApiResponses([
            @ApiResponse(code = 405, message = "Method Not Allowed. Only POST is allowed"),
            @ApiResponse(code = 404, message = "Method Not Found")]
    )
    @ApiImplicitParams([
            @ApiImplicitParam(name = 'submittedBy', paramType = 'form', required = true, value = "Submitted By", example = "Sample User 1", dataType = "string"),
            @ApiImplicitParam(name = 'emailId', paramType = 'form', required = true, value = "Valid Email ID", example = "blah.blah1@blah1.com", dataType = "string"),
            @ApiImplicitParam(name = 'place', paramType = 'form', required = true, value = "Place", example = "Dubai", dataType = "string"),
            @ApiImplicitParam(name = 'websiteExperience', paramType = 'form', required = false, value = "Website Experience Rating", example = "GOOD", dataType = "string", allowableValues = "EXCELLENT,GOOD,AVERAGE,POOR"),
            @ApiImplicitParam(name = 'customerService', paramType = 'form', required = false, value = "Customer Service Rating", example = "GOOD", dataType = "string", allowableValues = "EXCELLENT,GOOD,AVERAGE,POOR"),
            @ApiImplicitParam(name = 'availability', paramType = 'form', required = false, value = "Availability Rating", example = "GOOD", dataType = "string", allowableValues = "EXCELLENT,GOOD,AVERAGE,POOR"),
            @ApiImplicitParam(name = 'rates', paramType = 'form', required = false, value = "Rates Rating", example = "GOOD", dataType = "string", allowableValues = "EXCELLENT,GOOD,AVERAGE,POOR"),
            @ApiImplicitParam(name = 'customerType', paramType = 'form', required = false, value = "Customer type", example = "GUEST", dataType = "string", allowableValues = "GUEST,REGISTERED_CUSTOMER"),
            @ApiImplicitParam(name = 'messageLanguage', paramType = 'form', required = false, value = "Optional Message language", example = "ENGLISH", dataType = "string", allowableValues = "ENGLISH,ARABIC"),
            @ApiImplicitParam(name = 'testimonialImageFile', paramType = 'form', required = false, value = "Image file", dataType = "java.io.File"),
            @ApiImplicitParam(name = 'message', paramType = 'form', required = true, value = "Message for the testimonial", dataType = "string"),

            @ApiImplicitParam(name = "applicationType", paramType = "header", required = true, defaultValue = "web", value = "Application Types", dataType = "string"),
            @ApiImplicitParam(name = "Accept-Language", paramType = "header", required = true, defaultValue = "en", value = "Accept-Language", dataType = "string"),
            @ApiImplicitParam(name = "X-Sales-Type", paramType = "header", required = true, defaultValue = "b2c", value = "X-Sales-Type", dataType = "string"),
            @ApiImplicitParam(name = "X-Sales-Channel", paramType = "header", required = true, defaultValue = "web", value = "X-Sales-Channel", dataType = "string")
    ])
    def newTestimonial(TestimonialCO testimonialCO) {
        (new ResponseDTO(status: Boolean.TRUE, message: "New testimonial uploaded successfully with name- ${testimonialCO?.testimonialImageFile?.getOriginalFilename()}"))
    }

    @ApiOperation(
            value = "Delete Testimonial",
            notes = "Deletes testimonials. Excepts a testimonial id as json.",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = "DELETE",
            nickname = "testimonial")
    @ApiResponses([@ApiResponse(code = 405, message = "Method Not Allowed. Only Delete is allowed"),
            @ApiResponse(code = 404, message = "Method Not Found")])
    @ApiImplicitParams([
            @ApiImplicitParam(name = "id",
                    value = "Requires integer Testimonial Id for delete",
                    defaultValue = "1",
                    required = false,
                    paramType = "query",
                    dataType = "string"),
            @ApiImplicitParam(name = "applicationType",
                    paramType = "header",
                    required = true,
                    defaultValue = "web",
                    value = "Application Types",
                    dataType = "string"),
            @ApiImplicitParam(name = "Accept-Language",
                    paramType = "header",
                    required = true,
                    defaultValue = "en",
                    value = "Accept-Language",
                    dataType = "string")
    ])
    def deleteTestimonial(Long id, String lastUpdatedBy) {
        (new TestimonialDTO(id: id, lastUpdatedBy: lastUpdatedBy) as JSON)
    }

    def fetchTestimonialAggregateScore() {
        (new ResponseDTO(status: Boolean.TRUE, data: 5))
    }

    @ApiOperation(
            tags = ["Public"],
            value = "List Testimonial",
            notes = "List Testimonial",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = "GET",
            nickname = "public/testimonial",
            response = ResponseDTO.class)
    @ApiResponses([
            @ApiResponse(code = 405,
                    message = "Method Not Allowed. Only GET is allowed"),
            @ApiResponse(code = 404,
                    message = "Method Not Found")])
    @ApiImplicitParams([
            @ApiImplicitParam(name = "applicationType",
                    paramType = "header",
                    required = true,
                    defaultValue = "web",
                    value = "Application Types",
                    dataType = "string"),
            @ApiImplicitParam(name = "Accept-Language",
                    paramType = "header",
                    required = true,
                    defaultValue = "en",
                    value = "Accept-Language",
                    dataType = "string"),
            @ApiImplicitParam(name = "X-Sales-Type",
                    paramType = "header",
                    required = true,
                    defaultValue = "b2c",
                    value = "X-Sales-Type",
                    dataType = "string"),
            @ApiImplicitParam(name = "X-Sales-Channel",
                    paramType = "header",
                    required = true,
                    defaultValue = "web",
                    value = "X-Sales-Channel",
                    dataType = "string")
    ])
    def fetchTestimonials(TestimonialDTO testimonialDTO) {
        (new ResponseDTO(status: Boolean.TRUE, data: testimonialDTO))
    }
}