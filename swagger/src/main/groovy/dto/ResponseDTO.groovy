package dto

class ResponseDTO {
    String message
    Integer id
    boolean status
    Integer code
    def data

    void populateDataInApiResponseDto(def responseData) {
        data ? (data = data + responseData) : (data = responseData)
    }

    void populateFlashObjectWithResponseDto(Map flash) {
        if (this.status) {
            flash.success = this.message
        } else {
            flash.error = this.message
        }
    }

    void populateResponseDto(String message, Integer code = 0, Boolean status = false) {
        this.status = status
        this.message = message
        this.code = code
    }
}