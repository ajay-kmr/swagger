package swagger.dto

import grails.validation.Validateable
import org.springframework.web.multipart.MultipartFile

class TestimonialDTO implements Validateable {
    Boolean isPaginate = true
    Long id
    String submittedBy
    String emailId
    String message
    String place
    String approvedBy
    String lastUpdatedBy
    String createdBy
    Boolean isApproved
    Boolean isDeleted
    Date approvedDate
    Date lastUpdatedDate
    Date createdDate
    Rating websiteExperience
    Rating customerService
    Rating availability
    Rating rates
    String messageLanguage

    String userImage
    String bgImage
    String testimonialImage
    String customerType
    Boolean testimonialImageRemoved

    Date createdDateTo
    Date createdDateFrom
    Date approvedDateTo
    Date approvedDateFrom
    Date lastUpdatedDateTo
    Date lastUpdatedDateFrom

    Boolean createdByBackendUser = false
    List<Long> testimonialIds
    Boolean testimonialStatus

    String currentUserId
    String currentUserName
    MultipartFile testimonialImageFile
    String approvalStatus

    TestimonialDTO() {}

    String getApprovalStatus() {
        isApproved ? "APPROVED" : "PENDING"
    }

    public Map toMap() {
        return [
                "Submitted By"     : submittedBy,
                "Submitted On"     : createdDate,
                "Email ID"         : emailId,
                "Message"          : message,
                "Status"           : isApproved,
                "Approved By"      : approvedBy ?: '',
                "Approved Date"    : approvedDate ?: '',
                "Last Updated Date": lastUpdatedDate
        ]
    }

    static LinkedHashMap<String, String> csv = [
            "Submitted By"     : 'submittedBy',
            "Submitted On"     : 'createdDate',
            "Email ID"         : 'emailId',
            "Message"          : 'message',
            "Customer Type"    : 'customerType',
            "Status"           : 'approvalStatus',
            "Approved By"      : 'approvedBy',
            "Approved Date"    : 'approvedDate',
            "Last Updated Date": 'lastUpdatedDate'
    ]

    static constraints = {
        submittedBy nullable: false, blank: false
        place nullable: false, blank: false
        emailId nullable: false, blank: false, email: true
        websiteExperience nullable: true
        customerService nullable: true
        rates nullable: true
        availability nullable: true
        message nullable: false, blank: false, maxSize: 1000
        messageLanguage nullable: true

        id nullable: true
        approvedBy nullable: true, blank: false
        lastUpdatedBy nullable: true, blank: false
        createdBy nullable: true, blank: false
        lastUpdatedDate nullable: true
        isApproved nullable: true
        isDeleted nullable: true
        isPaginate nullable: true
        approvedDate nullable: true
        createdDate nullable: true
        approvedDate nullable: true
        userImage nullable: true, blank: true
        bgImage nullable: true, blank: true

        createdDateTo nullable: true
        createdDateFrom nullable: true
        approvedDateTo nullable: true
        approvedDateFrom nullable: true
        lastUpdatedDateTo nullable: true
        lastUpdatedDateFrom nullable: true
        testimonialImage nullable: true, blank: true
        createdByBackendUser nullable: true, blank: true
        testimonialIds nullable: true
        testimonialStatus nullable: true

        currentUserId nullable: true, blank: true
        currentUserName nullable: true, blank: true
        testimonialImageFile nullable: true
        testimonialImageRemoved nullable: true
        customerType nullable: true
        approvalStatus nullable: true
    }
}