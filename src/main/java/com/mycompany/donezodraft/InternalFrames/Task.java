package com.mycompany.donezodraft.InternalFrames;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task {
    private String name;
    private String description;
    private LocalDate dueDate;
    private int timeAllotted;
    private String progress;
    private String difficulty;

    public Task(String name, String description, LocalDate dueDate, int timeAllotted, String progress, String difficulty) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.timeAllotted = timeAllotted;
        this.progress = progress;
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public int getTimeAllotted() {
        return timeAllotted;
    }

    public void setTimeAllotted(int timeAllotted) {
        this.timeAllotted = timeAllotted;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return "Task Name: " + name + ", Description: " + description + ", Due Date: " + dueDate.format(formatter) + 
               ", Time Allotted: " + timeAllotted + " minutes, Progress: " + progress + ", Difficulty: " + difficulty;
    }
}
