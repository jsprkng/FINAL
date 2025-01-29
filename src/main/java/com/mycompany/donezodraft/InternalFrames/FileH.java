package com.mycompany.donezodraft.InternalFrames;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class FileH {
    static String delimiter = "a36f9a45416c";
    
   
    public static ArrayList<Task> funcReadFile(String filePath) {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            String line;
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            while ((line = br.readLine()) != null) { 
                String[] columns = line.split(delimiter);
                int columnNo = 0;
                String name = "";
                String description = "";
                LocalDate dueDate = LocalDate.MIN;
                int timeAllotted = 0;
                String progress = "";
                String difficulty = "";
                
                for (String column : columns) {
                    // name, description, dueDate, timeAllotted, progress, difficulty
                    switch (columnNo) {
                        case 0: // name
                            name = column;
                            break;
                        case 1:
                            description = column;
                            break;
                        case 2:
                            dueDate = LocalDate.parse(column);
                            break;
                        case 3:
                            timeAllotted = Integer.parseInt(column);
                            break;
                        case 4:
                            progress = column;
                            break;
                        case 5:
                            difficulty = column;
                            break;
                        default:
                            throw new AssertionError();
                    }
                    columnNo++;
                }

                if (name.length() > 0) {
                    tasks.add(new Task(name, description, dueDate, timeAllotted, progress, difficulty));
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace(); // Log error
        }
        return tasks;
    }

    /**
     * filePath is absolute, not relative.
     */
    public static void funcClearFile(String filePath) {
        try {
            FileWriter writer = new FileWriter(filePath, false);
            writer.write(""); // Clear file
            writer.close();
        } catch (Exception e) {
            e.printStackTrace(); // Log error
        }
    }
    /**
     * adds a Task to the file
     * 
     * filePath is absolute, not relative.
     */
    public static void funcAddTaskToFile(String filePath, Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(task.getName());
        sb.append(delimiter);
        sb.append(task.getDescription());
        sb.append(delimiter);
        sb.append(task.getDueDate().toString());
        sb.append(delimiter);
        sb.append(task.getTimeAllotted());
        sb.append(delimiter);
        sb.append(task.getProgress());
        sb.append(delimiter);
        sb.append(task.getDifficulty());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(sb.toString());
            writer.newLine(); // Add a newline to separate tasks
            System.out.println("Content appended successfully.");
        } catch (Exception e) {
            e.printStackTrace(); // Log error
        }
    }
    public static void funcWriteAllTasksToFile(String filePath, ArrayList<Task> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : tasks) {
                writer.write(String.join(delimiter,
                    task.getName(),
                    task.getDescription(),
                    task.getDueDate().toString(),
                    String.valueOf(task.getTimeAllotted()),
                    task.getProgress(),
                    task.getDifficulty()
                ));
                writer.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log error
        }
    }
    public static void funcSaveTasksToFile(String filePath, ArrayList<Task> tasks) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}