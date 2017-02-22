package dto

import io.swagger.annotations.ApiModel

/**
 * Created by intelligrape on 23/9/16.
 */
@ApiModel
class CategoryDTO {

    Long id
    Boolean isPaginate = true
    Boolean isFilter = false
    String categoryName
    String externalReferenceCode
    String currentUserId
    String currentUserName

    Date lastUpdatedDateFrom
    Date lastUpdatedDateTo

    Date createdDateFrom
    Date createdDateTo
    Date lastUpdatedDate = new Date()
    Date createdDate
    Boolean isDeleted
    Boolean isEnabled
    String lastUpdatedBy
    String createdBy
}