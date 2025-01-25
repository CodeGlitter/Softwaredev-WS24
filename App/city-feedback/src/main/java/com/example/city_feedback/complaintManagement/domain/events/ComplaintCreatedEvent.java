package com.example.city_feedback.complaintManagement.domain.events;

/**
 * Event triggered when a new complaint is created.
 * This can be used for sending notifications or logging actions.
 */
public class ComplaintCreatedEvent {

    private final Long complaintId;
    private final String complaintTitle;
    private final String createdBy;

    /**
     * Constructs a new ComplaintCreatedEvent.
     *
     * @param complaintId   the ID of the created complaint
     * @param complaintTitle the title of the created complaint
     * @param createdBy      the user who created the complaint
     */
    public ComplaintCreatedEvent(Long complaintId, String complaintTitle, String createdBy) {
        this.complaintId = complaintId;
        this.complaintTitle = complaintTitle;
        this.createdBy = createdBy;
    }

    public Long getComplaintId() {
        return complaintId;
    }

    public String getComplaintTitle() {
        return complaintTitle;
    }

    public String getCreatedBy() {
        return createdBy;
    }
}
