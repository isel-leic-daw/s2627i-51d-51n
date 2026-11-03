package pt.isel.daw.servlets

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

data class Line(
    val value: String = "",
    val delay: Duration = 0.seconds,
)

/**
 * Sequence of text lines to send.
 */
val sseLines = listOf(
    Line(":comment"),
    Line(),
    Line("data: this is an event"),
    Line(delay = 1.seconds),
    Line("data: this is an event", 1.seconds),
    Line("data: with a multi line value"),
    Line(delay = 2.seconds),
    Line("event: foo"),
    Line("data: this is an event with a specific type"),
    Line(delay = 1.seconds),
    Line("event: message"),
    Line("data: this is an event with a specific id"),
    Line("id: event-3"),
    Line(),
)