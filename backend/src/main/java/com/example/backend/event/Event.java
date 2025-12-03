package com.example.backend.event;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

/**
 * Event entity representing a calendar event.
 */
@Entity
@Table(name = "events")
@Schema(description = "Calendar Event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Primary key ID", example = "1")
    private Long id;

    @NotBlank
    @Schema(description = "Title of the event", example = "Team Meeting")
    private String title;

    @Schema(description = "Detailed description of the event", example = "Discuss Q3 roadmap")
    @Column(columnDefinition = "text")
    private String description;

    @NotNull
    @Schema(description = "Start time (ISO-8601 with timezone)", example = "2025-01-01T09:00:00Z")
    private OffsetDateTime startTime;

    @NotNull
    @Schema(description = "End time (ISO-8601 with timezone)", example = "2025-01-01T10:00:00Z")
    private OffsetDateTime endTime;

    @Schema(description = "Whether the event spans the entire day", example = "false")
    private boolean allDay;

    @Schema(description = "Location of the event", example = "Conference Room A")
    private String location;

    @Schema(description = "Color tag of the event (hex or named)", example = "#2563EB")
    private String colorTag;

    public Event() {}

    // PUBLIC_INTERFACE
    public Long getId() {
        /** Returns event id */
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // PUBLIC_INTERFACE
    public String getTitle() {
        /** Returns event title */
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // PUBLIC_INTERFACE
    public String getDescription() {
        /** Returns event description */
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // PUBLIC_INTERFACE
    public OffsetDateTime getStartTime() {
        /** Returns event start time */
        return startTime;
    }

    public void setStartTime(OffsetDateTime startTime) {
        this.startTime = startTime;
    }

    // PUBLIC_INTERFACE
    public OffsetDateTime getEndTime() {
        /** Returns event end time */
        return endTime;
    }

    public void setEndTime(OffsetDateTime endTime) {
        this.endTime = endTime;
    }

    // PUBLIC_INTERFACE
    public boolean isAllDay() {
        /** Returns whether event is all-day */
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    // PUBLIC_INTERFACE
    public String getLocation() {
        /** Returns event location */
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // PUBLIC_INTERFACE
    public String getColorTag() {
        /** Returns event color tag */
        return colorTag;
    }

    public void setColorTag(String colorTag) {
        this.colorTag = colorTag;
    }
}
