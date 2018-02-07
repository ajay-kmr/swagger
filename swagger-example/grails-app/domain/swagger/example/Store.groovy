package swagger.example

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.swagger.annotations.ApiModelProperty

@JsonIgnoreProperties(['dirtyPropertyNames', 'dirty', 'attached', 'properties', 'version'])  // this affects global json marshalling
class Store {

    @ApiModelProperty(required = true, value = "name of the store, must be provided")
    String name

    String owner

    static constraints = {
        name blank: false
        owner nullable: true
    }

}
