package com.calendar.app.events

import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(name = "events")
data class Event(
    @Id
    @Column(columnDefinition = "uuid")
    var id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    var title: String = "",

    @Column(columnDefinition = "text")
    var description: String? = null,

    @Column(name = "start_time", nullable = false, columnDefinition = "timestamptz")
    var startTime: OffsetDateTime = OffsetDateTime.now(),

    @Column(name = "end_time", nullable = false, columnDefinition = "timestamptz")
    var endTime: OffsetDateTime = OffsetDateTime.now(),

    var location: String? = null,

    @Column(name = "all_day", nullable = false)
    var allDay: Boolean = false,

    @Column(name = "recurrence_rule", columnDefinition = "text")
    var recurrenceRule: String? = null,

    @Column(name = "created_at", nullable = false, columnDefinition = "timestamptz")
    var createdAt: OffsetDateTime = OffsetDateTime.now(),

    @Column(name = "updated_at", nullable = false, columnDefinition = "timestamptz")
    var updatedAt: OffsetDateTime = OffsetDateTime.now()
)
