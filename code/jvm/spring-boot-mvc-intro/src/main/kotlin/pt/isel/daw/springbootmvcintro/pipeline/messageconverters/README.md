# Message converters

Illustrates how to define custom `HttpMessageConverter`s, which convert handler return values into response
bodies (and request bodies into handler arguments). Spring MVC selects the converter based on the value's type and
on the response media type, which is negotiated using the request's `Accept` header.

## Contents

* [`UriToQrCodeMessageConverter.kt`](UriToQrCodeMessageConverter.kt) - writes `URI` values as a PNG image
  (`image/png`) containing a QR code. It only supports writing, not reading.
* [`CustomOutputModelMessageConverter.kt`](CustomOutputModelMessageConverter.kt) - a Jackson based converter that
  only writes `CustomOutputModel` values, using the custom `application/vnd.custom+json` media type.
* [`DefaultMessageConverter.kt`](DefaultMessageConverter.kt) - a general purpose Jackson based JSON converter,
  using the Spring Boot managed `ObjectMapper`.

## Observations

* See [`MessageConversionController`](../../controllers/MessageConversionController.kt) for the handlers returning
  these types (e.g. `GET /examples/ret/3` returns a QR code).
* Message converter beans are automatically registered by Spring Boot, before the default converters.
* Spring Boot only auto-configures its JSON converter if there is no other `MappingJackson2HttpMessageConverter` bean.
  Since `CustomOutputModelMessageConverter` is one, `DefaultMessageConverter` is needed to keep a general purpose
  JSON converter that uses the Spring Boot `ObjectMapper` (and therefore the `snake_case` customization).
