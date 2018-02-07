package swagger.example

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel
@JsonIgnoreProperties(['dirtyPropertyNames', 'dirty', 'attached', 'properties'])  // this affects global json marshalling
class Book {

    @ApiModelProperty(position = 1, required = true, value = "title of the book, must be provided")
    String title

    @ApiModelProperty(position = 2, required = false)
    String isbn

    String author

    static constraints = {
        title blank: false
        isbn nullable: true
        author nullable: true
    }

}
