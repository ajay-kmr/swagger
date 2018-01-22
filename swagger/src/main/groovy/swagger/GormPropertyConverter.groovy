package swagger

import com.fasterxml.jackson.databind.JavaType
import io.swagger.converter.ModelConverter
import io.swagger.converter.ModelConverterContext
import io.swagger.models.Model
import io.swagger.models.properties.Property
import io.swagger.util.Json

import java.lang.annotation.Annotation
import java.lang.reflect.Type


/**
 * Provide GORM property custom converter that filters out Grails GORM traits introduced properties.
 *
 * @see 'https://github.com/swagger-api/swagger-core/blob/master/modules/swagger-core/src/test/java/io/swagger/model/override/CustomConverterTest.java'
 */
class GormPropertyConverter implements ModelConverter {
    @Override
    Property resolveProperty(Type type, ModelConverterContext context, Annotation[] annotations, Iterator<ModelConverter> chain) {

        JavaType _type = Json.mapper().constructType(type)
        if (_type != null) {
            Class<?> cls = _type.getRawClass()

            // remove Spring Errors property added by Grails Domain traits
            if (org.springframework.validation.Errors.class.isAssignableFrom(cls)) {
                return null
            }
        }
        if (chain.hasNext()) {
            return chain.next().resolveProperty(type, context, annotations, chain)
        } else {
            return null
        }
    }

    @Override
    Model resolve(Type type, ModelConverterContext context, Iterator<ModelConverter> chain) {
//        if (chain.hasNext()) {
            return chain.next()?.resolve(type, context, chain)
//        } else {
//            return null
//        }
    }
}
