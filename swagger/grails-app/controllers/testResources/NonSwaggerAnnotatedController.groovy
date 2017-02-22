package testResources

import dto.CityDTO
import dto.ResponseDTO
import grails.converters.JSON

class NonSwaggerAnnotatedController {
    static namespace = 'v1'

    def getCity(String cityId) {
        render(new ResponseDTO(status: true, message: "New Delhi", data: ["key1": "value1", "key2": "value2"]) as JSON)
    }

    def getCityList(Integer offset, Integer limit) {
        render(new ResponseDTO(status: true, message: "City List fetched successfully", data: ["key1": "value1", "key2": "value2"]) as JSON)
    }

    def createOrUpdateCity(CityDTO cityDTO) {
        render(new ResponseDTO(status: true, message: "City updated successfully", data: cityDTO) as JSON)
    }

    def deleteCity(String cityId) {
        render(new ResponseDTO(status: true, message: "City deleted successfully") as JSON)
    }

}
