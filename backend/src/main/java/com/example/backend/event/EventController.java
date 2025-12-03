package com.example.backend.event;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * PUBLIC_INTERFACE
 * REST controller for Event resources.
 */
@RestController
@RequestMapping("/api/events")
@Tag(name = "Events", description = "CRUD operations for calendar events")
public class EventController {

    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    /**
     * PUBLIC_INTERFACE
     * Create an event.
     * @param event event payload
     * @return created event
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create event", description = "Creates a new calendar event")
    public Event create(@Valid @RequestBody Event event) {
        return service.create(event);
    }

    /**
     * PUBLIC_INTERFACE
     * List all events.
     * @return list of events
     */
    @GetMapping
    @Operation(summary = "List events", description = "Returns all events")
    public List<Event> list() {
        return service.getAll();
    }

    /**
     * PUBLIC_INTERFACE
     * Get event by id.
     * @param id event id
     * @return event
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get event", description = "Returns an event by id")
    public Event get(@PathVariable Long id) {
        return service.getById(id);
    }

    /**
     * PUBLIC_INTERFACE
     * Update event by id.
     * @param id event id
     * @param event incoming payload
     * @return updated event
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update event", description = "Updates an existing event")
    public Event update(@PathVariable Long id, @Valid @RequestBody Event event) {
        return service.update(id, event);
    }

    /**
     * PUBLIC_INTERFACE
     * Delete event by id.
     * @param id event id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete event", description = "Deletes an event by id")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    /**
     * PUBLIC_INTERFACE
     * Find events overlapping the provided time range [from, to].
     * @param from ISO-8601 start timestamp with zone, e.g., 2025-01-01T00:00:00Z
     * @param to ISO-8601 end timestamp with zone
     * @return list of overlapping events
     */
    @GetMapping("/range")
    @Operation(summary = "Find by time range", description = "Returns events overlapping the provided time window")
    public List<Event> findByRange(
            @RequestParam
            @Parameter(description = "Start of range (ISO-8601 with timezone)")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime from,
            @RequestParam
            @Parameter(description = "End of range (ISO-8601 with timezone)")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime to) {
        return service.findByRange(from, to);
    }
}
