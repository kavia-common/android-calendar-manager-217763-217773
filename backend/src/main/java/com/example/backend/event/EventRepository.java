package com.example.backend.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * PUBLIC_INTERFACE
 * Repository for Event entities.
 */
public interface EventRepository extends JpaRepository<Event, Long> {

    /**
     * PUBLIC_INTERFACE
     * Find events that overlap with the provided time range.
     * An event overlaps when NOT (event.endTime < :from OR event.startTime > :to)
     * @param from start of the query window
     * @param to end of the query window
     * @return list of overlapping events
     */
    @Query("""
            SELECT e FROM Event e
            WHERE NOT (e.endTime < :from OR e.startTime > :to)
            ORDER BY e.startTime ASC
            """)
    List<Event> findOverlapping(OffsetDateTime from, OffsetDateTime to);
}
