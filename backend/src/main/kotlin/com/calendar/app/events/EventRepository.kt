package com.calendar.app.events

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.OffsetDateTime
import java.util.UUID

interface EventRepository : JpaRepository<Event, UUID> {

    @Query(
        """
        SELECT e FROM Event e 
        WHERE (e.startTime <= :end) AND (e.endTime >= :start)
        ORDER BY e.startTime ASC
        """
    )
    fun findOverlapping(@Param("start") start: OffsetDateTime, @Param("end") end: OffsetDateTime): List<Event>
}
