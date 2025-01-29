/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.donezodraft.InternalFrames;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class ScheduleFileHandler {
    static String delimiter = "a36f9a45416c";

    /**
     * Reads the file and parses Schedule objects.
     * filePath is absolute, not relative.
     */
    public static ArrayList<Schedule> funcReadFile(String filePath) {
        ArrayList<Schedule> schedules = new ArrayList<>();

        try {
            String line;
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            while ((line = br.readLine()) != null) { 
                String[] columns = line.split(delimiter);
                int columnNo = 0;
                String day = "";
                int startTime = 0;
                int endTime = 0;
                String reason = "";
                String frequency = "";

                for (String column : columns) {
                    switch (columnNo) {
                        case 0: // day
                            day = column;
                            break;
                        case 1: // startTime
                            startTime = Integer.parseInt(column);
                            break;
                        case 2: // endTime
                            endTime = Integer.parseInt(column);
                            break;
                        case 3: // reason
                            reason = column;
                            break;
                        case 4: // frequency
                            frequency = column;
                            break;
                        default:
                            throw new AssertionError("Unexpected column in schedule data.");
                    }
                    columnNo++;
                }

                if (day.length() > 0) {
                    schedules.add(new Schedule(day, startTime, endTime, reason, frequency));
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace(); // Log error
        }
        return schedules;
    }

    /**
     * Clears the file content.
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
     * Adds a Schedule to the file.
     * filePath is absolute, not relative.
     */
    public static void funcAddScheduleToFile(String filePath, Schedule schedule) {
        StringBuilder sb = new StringBuilder();
        sb.append(schedule.getDay());
        sb.append(delimiter);
        sb.append(schedule.getStartTime());
        sb.append(delimiter);
        sb.append(schedule.getEndTime());
        sb.append(delimiter);
        sb.append(schedule.getReason());
        sb.append(delimiter);
        sb.append(schedule.getFrequency());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(sb.toString());
            writer.newLine(); // Add a newline to separate schedules
            System.out.println("Schedule appended successfully.");
        } catch (Exception e) {
            e.printStackTrace(); // Log error
        }
    }
}

