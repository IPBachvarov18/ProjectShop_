package org.example.models;

import java.io.Serializable;
import java.time.LocalTime;

public class WorkTime implements Serializable {
    private LocalTime startTime;
    private LocalTime endTime;

    public WorkTime(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return startTime + " - " + endTime;
    }
}
