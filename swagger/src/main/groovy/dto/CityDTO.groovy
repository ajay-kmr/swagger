package dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel
class CityDTO {

    Long id
    String name
    String areaPhoneCode
    Long provinceId
    String provinceName
    String countryName
    Long countryId
    String code
    String createdBy
    String lastUpdatedBy
    Date lastUpdatedDate
    String sortColumn = "lastUpdatedDate"
    String order = "desc"
    String iataCode
    String nearestAirport
    Date lastUpdatedDateFrom
    Date lastUpdatedDateTo

    @ApiModelProperty(value = "Inner Object Details", dataType = "dto.CategoryDTO")
    CategoryDTO categoryDTO

}
