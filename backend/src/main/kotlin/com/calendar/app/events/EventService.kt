package com.calendar.app.events

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime
import java.util.*

@Service
class EventService(private val repo: EventRepository) {

    // PUBLIC_INTERFACE
    @Transactional
    fun create(req: EventRequest): Event {
        val now = OffsetDateTime.now()
        val e = Event(
            id = UUID.randomUUID(),
            title = req.title,
            description = req.description,
            startTime = req.startTime,
            endTime = req.endTime,
            location = req.location,
            allDay = req.allDay,
            recurrenceRule = req.recurrenceRule,
            createdAt = now,
            updatedAt = now
        )
        return repo.save(e)
    }

    // PUBLIC_INTERFACE
    fun get(id: UUID): Optional<Event> = repo.findById(id)

    // PUBLIC_INTERFACE
    fun listInRange(start: OffsetDateTime, end: OffsetDateTime): List<Event> = repo.findOverlapping(start, end)

    // PUBLIC_INTERFACE
    @Transactional
    fun update(id: UUID, req: EventRequest): Optional<Event> {
        val opt = repo.findById(id)
        if (opt.isEmpty) return Optional.empty()
        val e = opt.get()
        e.title = req.title
        e.description = req.description
        e.startTime = req.startTime
        e.endTime = req.endTime
        e.location = req.location
        e.allDay = req.allDay
        e.recurrenceRule = req.recurrenceRule
        e.updatedAt = OffsetDateTime.now()
        return Optional.of(repo.save(e))
    }

    // PUBLIC_INTERFACE
    @Transactional
    fun delete(id: UUID) {
        repo.deleteById(id)
    }
}
