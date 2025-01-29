package com.mycompany.donezodraft.InternalFrames;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

class TaskSorter {
    public TaskSorter() {}
    
    /**
     * WARNING: sortTasks modifies your original list, be careful!
     * Please copy the array first before passing it here.
     */
    public ArrayList<ArrayList<Task>> sortTasks(ArrayList<Task> taskList) {
        // Sort tasks by priority points (highest first)
        taskList.sort((a, b) -> {
            int priorityA = calculatePriorityPoints(a);
            int priorityB = calculatePriorityPoints(b);
            return Integer.compare(priorityB, priorityA);
        });

        // Group tasks by priority points
        ArrayList<ArrayList<Task>> groupedTasks = new ArrayList<>();
        Queue<Task> taskQueue = new LinkedList<>(taskList);

        final int POINT_THRESHOLD = 70; // Example threshold for a group

        boolean removedAny = false;

        while (!taskQueue.isEmpty()) {
            ArrayList<Task> group = new ArrayList<>();
            int groupPoints = 0;
        
            Iterator<Task> iterator = taskQueue.iterator();
            while (iterator.hasNext()) {
                Task task = iterator.next();
                int taskPoints = calculatePriorityPoints(task);
        
                if (groupPoints + taskPoints <= POINT_THRESHOLD) {
                    group.add(task);
                    groupPoints += taskPoints;
                    iterator.remove();
                    removedAny = true;
        
                    if (groupPoints >= POINT_THRESHOLD) {
                        break;
                    }
                }
            }
        
            // If no tasks were removed, break to avoid an infinite loop
            if (!removedAny) {
                break;
            }
        
            groupedTasks.add(group);
            removedAny = false;
        }

        return groupedTasks;
    }

    private int calculatePriorityPoints(Task task) {
        int difficultyPoints = calculateDifficultyPoints(task.getDifficulty());
    
        int daysLeft = Period.between(LocalDate.now(), task.getDueDate()).getDays();
        int urgencyPoints = Math.max(0, 30 - daysLeft);
    
        int timeFitPoints = task.getTimeAllotted() < 5 ? 5 : 2;
        int timeRemainingScore = Math.max(0, daysLeft);
    
        return difficultyPoints + urgencyPoints + timeFitPoints - timeRemainingScore;
    }

    private int calculateDifficultyPoints(String difficulty) {
        switch (difficulty) {
            case "Easy":
                return 5;
            case "Medium":
                return 10;
            case "Hard":
                return 20;
            default:
                throw new AssertionError();
        }
    }
}