package com.calendar.app.events

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.OffsetDateTime
import java.util.*

@RestController
@RequestMapping("/api/events")
@Validated
class EventController(private val service: EventService) {

    /**
     * PUBLIC_INTERFACE
     * Create an event.
     * POST /api/events
     * Body: EventRequest JSON
     * Returns: EventResponse
     */
    @PostMapping
    fun create(@RequestBody req: EventRequest): ResponseEntity<EventResponse> {
        val e = service.create(req)
        return ResponseEntity.ok(e.toResponse())
    }

    /**
     * PUBLIC_INTERFACE
     * Get event by ID.
     * GET /api/events/{id}
     * Returns: EventResponse or 404
     */
    @GetMapping("/{id}")
    fun get(@PathVariable id: UUID): ResponseEntity<EventResponse> {
        val opt = service.get(id)
        return if (opt.isPresent) ResponseEntity.ok(opt.get().toResponse())
        else ResponseEntity.notFound().build()
    }

    /**
     * PUBLIC_INTERFACE
     * List events overlapping a date range.
     * GET /api/events?start=ISO&end=ISO
     * Returns: List<EventResponse>
     */
    @GetMapping
    fun listInRange(
        @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) start: OffsetDateTime,
        @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) end: OffsetDateTime
    ): ResponseEntity<List<EventResponse>> {
        val list = service.listInRange(start, end).map { it.toResponse() }
        return ResponseEntity.ok(list)
    }

    /**
     * PUBLIC_INTERFACE
     * Update an event by ID.
     * PUT /api/events/{id}
     * Body: EventRequest
     * Returns: EventResponse or 404
     */
    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody req: EventRequest): ResponseEntity<EventResponse> {
        val opt = service.update(id, req)
        return if (opt.isPresent) ResponseEntity.ok(opt.get().toResponse())
        else ResponseEntity.notFound().build()
    }

    /**
     * PUBLIC_INTERFACE
     * Delete an event by ID.
     * DELETE /api/events/{id}
     * Returns: 204
     */
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }
}
