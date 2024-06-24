package com.dashboard.dashboard.model;

import java.util.Date;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "financial_goals")
public class FinancialGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String description;

    private BigDecimal amount;

    private LocalDate limitDate;

    @Column(name = "is_completed")
    private Boolean isCompleted;

    @Column(name = "is_overdue")
    private Boolean isOverdue;

    @Column(name = "created_at")
    @CreatedDate
    private Instant createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Date updatedAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    public FinancialGoal() {
    }

    public FinancialGoal(User user, String description, BigDecimal amount, LocalDate limitDate, Boolean isCompleted,
            Boolean isOverdue,
            Date updatedAt, Date deletedAt) {
        this.user = user;
        this.description = description;
        this.amount = amount;
        this.limitDate = limitDate;
        this.isCompleted = isCompleted;
        this.isOverdue = isOverdue;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Boolean getIsOverdue() {
        return isOverdue;
    }

    public void setIsOverdue(Boolean isOverdue) {
        this.isOverdue = isOverdue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(LocalDate limitDate) {
        this.limitDate = limitDate;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date today) {
        this.updatedAt = today;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    @PrePersist
    public void PrePersist() {
        this.createdAt = Instant.now();
    }

    public void updateOverdueStatus(LocalDate currentDate) {
        if (this.limitDate != null && currentDate.isAfter(this.limitDate) && !this.isCompleted) {
            this.isOverdue = true;
        } else {
            this.isOverdue = false;
        }
    }

}
