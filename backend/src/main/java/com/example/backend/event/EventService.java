package com.example.backend.event;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * PUBLIC_INTERFACE
 * Service layer for Event domain including validation and CRUD operations.
 */
@Service
@Transactional
public class EventService {

    private final EventRepository repository;

    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    /**
     * PUBLIC_INTERFACE
     * Create a new event after validating fields.
     * @param event Event to create
     * @return saved Event
     */
    public Event create(Event event) {
        validate(event);
        return repository.save(event);
    }

    /**
     * PUBLIC_INTERFACE
     * Get all events.
     * @return list of events
     */
    @Transactional(readOnly = true)
    public List<Event> getAll() {
        return repository.findAll();
    }

    /**
     * PUBLIC_INTERFACE
     * Get event by id.
     * @param id event id
     * @return Event found
     * @throws NoSuchElementException if not found
     */
    @Transactional(readOnly = true)
    public Event getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Event not found: " + id));
    }

    /**
     * PUBLIC_INTERFACE
     * Update event by id.
     * @param id id to update
     * @param incoming new data
     * @return updated Event
     */
    public Event update(Long id, Event incoming) {
        Event existing = getById(id);
        existing.setTitle(incoming.getTitle());
        existing.setDescription(incoming.getDescription());
        existing.setStartTime(incoming.getStartTime());
        existing.setEndTime(incoming.getEndTime());
        existing.setAllDay(incoming.isAllDay());
        existing.setLocation(incoming.getLocation());
        existing.setColorTag(incoming.getColorTag());
        validate(existing);
        return repository.save(existing);
    }

    /**
     * PUBLIC_INTERFACE
     * Delete event by id.
     * @param id event id
     */
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Event not found: " + id);
        }
        repository.deleteById(id);
    }

    /**
     * PUBLIC_INTERFACE
     * Find events overlapping a given time window.
     * @param from start
     * @param to end
     * @return list of events
     */
    @Transactional(readOnly = true)
    public List<Event> findByRange(OffsetDateTime from, OffsetDateTime to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("from and to must be provided");
        }
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("from must be <= to");
        }
        return repository.findOverlapping(from, to);
    }

    private void validate(Event e) {
        if (e.getStartTime() == null || e.getEndTime() == null) {
            throw new IllegalArgumentException("startTime and endTime are required");
        }
        if (e.getStartTime().isAfter(e.getEndTime())) {
            throw new IllegalArgumentException("startTime must be <= endTime");
        }
        if (!StringUtils.hasText(e.getTitle())) {
            throw new IllegalArgumentException("title is required");
        }
    }
}
