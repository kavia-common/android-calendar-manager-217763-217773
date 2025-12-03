package com.calendar.app.events

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.OffsetDateTime
import java.util.UUID

// PUBLIC_INTERFACE
data class EventRequest(
    /** Title of the event. */
    @field:NotBlank(message = "Title is required")
    val title: String,
    /** Optional description of the event. */
    val description: String? = null,
    /** Start time in ISO-8601 with timezone. */
    @field:NotNull(message = "startTime is required")
    val startTime: OffsetDateTime,
    /** End time in ISO-8601 with timezone. */
    @field:NotNull(message = "endTime is required")
    val endTime: OffsetDateTime,
    /** Optional location where the event is held. */
    val location: String? = null,
    /** True if event lasts all day. */
    val allDay: Boolean = false,
    /** Optional recurrence rule in RFC-5545 RRULE string (placeholder). */
    val recurrenceRule: String? = null
)

// PUBLIC_INTERFACE
data class EventResponse(
    /** Unique identifier of the event. */
    val id: UUID,
    /** Event title. */
    val title: String,
    /** Event description. */
    val description: String?,
    /** Start time with timezone. */
    val startTime: OffsetDateTime,
    /** End time with timezone. */
    val endTime: OffsetDateTime,
    /** Event location. */
    val location: String?,
    /** Whether event is all day. */
    val allDay: Boolean,
    /** Optional recurrence rule. */
    val recurrenceRule: String?,
    /** Timestamps */
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime
)

fun Event.toResponse() = EventResponse(
    id, title, description, startTime, endTime, location, allDay, recurrenceRule, createdAt, updatedAt
)
