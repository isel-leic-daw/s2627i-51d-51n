package pt.isel.daw.springbootmvcintro.pipeline.messageconverters

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpOutputMessage
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.stereotype.Component
import pt.isel.daw.springbootmvcintro.controllers.CustomOutputModel
import java.lang.reflect.Type

@Component
class CustomOutputModelMessageConverter(
    objectMapper: ObjectMapper,
) : MappingJackson2HttpMessageConverter(objectMapper) {
    override fun canWrite(
        clazz: Class<*>,
        mediaType: MediaType?,
    ) = (mediaType == null || mediaType == MEDIA_TYPE) && CustomOutputModel::class.java.isAssignableFrom(clazz)

    override fun canWrite(mediaType: MediaType?) = mediaType == null || mediaType == MEDIA_TYPE

    override fun getSupportedMediaTypes() = listOf(MEDIA_TYPE)

    override fun writeInternal(
        `object`: Any,
        type: Type?,
        outputMessage: HttpOutputMessage,
    ) {
        super.writeInternal(`object`, type, outputMessage)
    }

    override fun writeInternal(
        t: Any,
        outputMessage: HttpOutputMessage,
    ) {
        super.writeInternal(t, outputMessage)
    }

    companion object {
        val MEDIA_TYPE = MediaType("application", "vnd.custom+json")
    }
}
