package swagger.co

import swagger.dto.Rating
import grails.validation.Validateable
import org.springframework.web.multipart.MultipartFile

class TestimonialCO implements Validateable {
    String submittedBy
    String emailId
    String message
    String place
    Rating websiteExperience
    Rating customerService
    Rating availability
    Rating rates
    String messageLanguage
    MultipartFile testimonialImageFile
    String customerType

    static constraints = {
        submittedBy blank: false
        emailId email: true, blank: false
        message blank: false
        place blank: false
        testimonialImageFile nullable: true
        websiteExperience nullable: true
        customerService nullable: true
        availability nullable: true
        rates nullable: true
        messageLanguage nullable: true
        customerType nullable: true
    }
}
