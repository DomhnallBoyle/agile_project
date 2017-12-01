package uk.ac.qub.csc3045.api.model;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class TaskEstimate {

    @NotNull
    private Long taskId;

    @NotNull
    private LocalDateTime date;

    private Integer estimate = 0;

    public TaskEstimate() {

    }

    public TaskEstimate(Long taskId, LocalDateTime date, Integer estimate) {
        this.taskId = taskId;
        this.date = date;
        this.estimate = estimate;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getEstimate() {
        return estimate;
    }

    public void setEstimate(Integer estimate) {
        this.estimate = estimate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskEstimate that = (TaskEstimate) o;

        if (!taskId.equals(that.taskId)) return false;
        return date.equals(that.date);
    }
}
