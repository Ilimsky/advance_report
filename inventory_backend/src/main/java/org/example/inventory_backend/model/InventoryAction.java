package org.example.inventory_backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
class InventoryAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    @ManyToOne
    private InventoryItem item;

    @ManyToOne
    private Department fromDepartment;

    @ManyToOne
    private Department toDepartment;

    private LocalDateTime date;
    private String performedBy;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public ActionType getActionType() { return actionType; }
    public void setActionType(ActionType actionType) { this.actionType = actionType; }
    public InventoryItem getItem() { return item; }
    public void setItem(InventoryItem item) { this.item = item; }
    public Department getFromDepartment() { return fromDepartment; }
    public void setFromDepartment(Department fromDepartment) { this.fromDepartment = fromDepartment; }
    public Department getToDepartment() { return toDepartment; }
    public void setToDepartment(Department toDepartment) { this.toDepartment = toDepartment; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public String getPerformedBy() { return performedBy; }
    public void setPerformedBy(String performedBy) { this.performedBy = performedBy; }
}
