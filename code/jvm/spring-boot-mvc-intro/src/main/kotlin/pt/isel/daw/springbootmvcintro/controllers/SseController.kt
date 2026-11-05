package pt.isel.daw.springbootmvcintro.controllers

import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.time.Instant
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@RestController
class SseController {

    @GetMapping("sse")
    fun get(): SseEmitter {
        log.info("GET request received.")
        val sseEmitter = SseEmitter()
        var nextId = 0
        val scheduledFuture = scheduledExecutor.scheduleAtFixedRate(
            {
                log.info("Sending event.")
                val now = Instant.now()
                val data = EventData(
                    currentTime = now.toString(),
                    epoch = now.epochSecond
                )
                try {
                    sseEmitter.send(
                        SseEmitter.event()
                            .name("event-name")
                            .data(data)
                            .id(nextId++.toString())
                    )
                    if(nextId == 10) {
                        sseEmitter.complete()
                    }
                } catch (th: Exception) {
                    log.info("Exception when trying to send an event: {}", th.message)
                }
            },
            1000L,
            1000L,
            TimeUnit.MILLISECONDS
        )
        sseEmitter.onError {
            log.info("onError called.")
        }
        sseEmitter.onCompletion {
            log.info("onCompletion called")
            scheduledFuture.cancel(true)
        }

        log.info("handler is returning.")
        return sseEmitter
    }

    data class EventData(
        val currentTime: String,
        val epoch: Long
    )

    companion object {
        private val log = LoggerFactory.getLogger(SseController::class.java)
        private val scheduledExecutor = Executors.newSingleThreadScheduledExecutor()
    }
}