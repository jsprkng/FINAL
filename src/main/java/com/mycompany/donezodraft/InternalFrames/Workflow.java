package com.mycompany.donezodraft.InternalFrames;

import com.mycompany.donezodraft.animations.MyButton;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import com.mycompany.donezodraft.LoginSignUpForms.AccountsFileH;
import com.mycompany.donezodraft.LoginSignUpForms.User;

public class Workflow extends JInternalFrame {

    private static final int ROWS = 24 * 4; // 24 hours * 4 intervals per hour to support 15mins interval
    private static final int COLUMNS = 8;
    private static final int CELL_WIDTH = 115;
    private static final int CELL_HEIGHT = 60 / 4; // 60 minutes / 4 intervals
    private static final int PANEL_WIDTH = COLUMNS * CELL_WIDTH;
    private static final int PANEL_HEIGHT = ROWS * CELL_HEIGHT;
    private static final ArrayList<Schedule> scheduleList = new ArrayList<>();
    private ArrayList<User> currentUser = AccountsFileH.funcReadUsersFromFile("currentUsersDatabase.txt");
    private String NAMEUSER = currentUser.get(0).getName();

    private String SCHEDFILE_PATH = NAMEUSER + "Scheddatabase.txt";
    private String TASKFILE_PATH = NAMEUSER + "database.txt";
    private final ArrayList<Task> taskList = new ArrayList<>();
    private final boolean[][] taskConflict = new boolean[ROWS][COLUMNS - 1];

    private JPanel container;

    public Workflow() {
        super("Weekly Schedule", false, false, false, false);
        setSize(979, 693);
        setLayout(new BorderLayout());
        setBorder(null);
        setBackground(new Color(240, 237, 237));

        BasicInternalFrameUI internalFrameUI = (BasicInternalFrameUI) this.getUI();
        internalFrameUI.setNorthPane(null);

        JPanel northPanel = createNorthPanel();
        JPanel timeLabelsPanel = createTimeLabelsPanel();
        
        container = new JPanel(null);
        container.setBackground(new Color(240, 237, 237));
        container.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        JScrollPane timeScrollPane = createScrollPane(timeLabelsPanel, false);
        JScrollPane scheduleScrollPane = createScrollPane(container, true);
        linkScrollBars(timeScrollPane, scheduleScrollPane);

        add(northPanel, BorderLayout.NORTH);
        add(timeScrollPane, BorderLayout.WEST);
        add(scheduleScrollPane, BorderLayout.CENTER);
        
        scheduleList.clear();
        scheduleList.addAll(ScheduleFileHandler.funcReadFile(SCHEDFILE_PATH));
        taskList.clear();
        taskList.addAll(FileH.funcReadFile(TASKFILE_PATH));
        loadSched();

        setVisible(true);
    }
    
    class RoundedPanel1 extends JPanel {
        private int cornerRadius = 20; // Adjust for more/less rounding

        public RoundedPanel1() {
            super();
            setOpaque(false); 
        }

        public RoundedPanel1(int radius) {
            super();
            this.cornerRadius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
            g2.dispose();
        }
    }
    
    private JPanel createNorthPanel() {
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(new Color(240, 237, 237));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(240, 237, 237));

        JLabel titleLabel = new JLabel("Workflow", SwingConstants.LEFT);
        loadFont("/fontStyles/Outfit-ExtraBold.ttf", 26f, new Color(12, 26, 43), titleLabel);
        titleLabel.setBorder(new EmptyBorder(20, 20, 0, 0));
        titlePanel.add(titleLabel, BorderLayout.WEST);

        RoundedPanel1 weekPanel = new RoundedPanel1(20);
        weekPanel.setBackground(new Color(12, 26, 43));
        weekPanel.setPreferredSize(new Dimension(300, 50)); 

        JLabel weekLabel = new JLabel(getCurrentWeekRange(), SwingConstants.CENTER);
        loadFont("/fontStyles/Outfit-Bold.ttf", 20f, Color.WHITE, weekLabel);
        weekLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
        weekPanel.add(weekLabel);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        centerPanel.setBackground(new Color(240, 237, 237));
        centerPanel.add(weekPanel);

        RoundedPanel1 headerPanel = new RoundedPanel1(40);
        headerPanel.setLayout(new GridLayout(1, 8, 5, 0));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        headerPanel.setPreferredSize(new Dimension(30,50));
        headerPanel.setBackground(Color.WHITE);

        String[] days = {"", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (int i = 0; i < days.length; i++) {
            JLabel label = new JLabel(days[i], SwingConstants.CENTER);
            label.setOpaque(true);
            label.setBackground(Color.WHITE);
            label.setFont(new Font("SansSerif", Font.BOLD, 14));
            headerPanel.add(label);
        }

        northPanel.add(titlePanel, BorderLayout.NORTH);
        northPanel.add(centerPanel, BorderLayout.CENTER);
        northPanel.add(headerPanel, BorderLayout.SOUTH);

        return northPanel;
    }
    
    private JPanel createTimeLabelsPanel() {
        RoundedPanel1 timeLabelsPanel = new RoundedPanel1(40);
        timeLabelsPanel.setLayout(new GridLayout(ROWS / 4, 1));
        timeLabelsPanel.setPreferredSize(new Dimension(100, PANEL_HEIGHT));
        timeLabelsPanel.setBackground(Color.WHITE);
        timeLabelsPanel.setBorder(BorderFactory.createEmptyBorder(20, 5, 5, 5));


        for (int i = 0; i < ROWS / 4; i++) {
            String timeLabel;

            if (i == 0) {
                timeLabel = "12 MN";
            } else if (i < 12) {
                timeLabel = i + " AM";
            } else if (i == 12) {
                timeLabel = "12 NN";
            } else {
                timeLabel = (i - 12) + " PM";
            }

            JLabel label = new JLabel(timeLabel, SwingConstants.CENTER);
            label.setOpaque(true);
            label.setBackground(Color.WHITE);
            label.setBorder(null);
            label.setFont(new Font("SansSerif", Font.BOLD, 12));
            timeLabelsPanel.add(label);
        }

        return timeLabelsPanel;
    }

    private JScrollPane createScrollPane(JPanel panel, boolean verticalScroll) {
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(verticalScroll ? JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED : JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        return scrollPane;
    }

    private void linkScrollBars(JScrollPane timeScrollPane, JScrollPane scheduleScrollPane) {
        scheduleScrollPane.getVerticalScrollBar().addAdjustmentListener(e -> {
            timeScrollPane.getVerticalScrollBar().setValue(e.getValue());
        });
    }

    private String getCurrentWeekRange() {
        LocalDate now = LocalDate.now();
        LocalDate monday = now.with(java.time.DayOfWeek.MONDAY);
        LocalDate sunday = monday.plusDays(6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d");
        return monday.format(formatter) + " - " + sunday.format(formatter);
    }

    private void loadSched() {
        for (Schedule schedule : scheduleList) {
            String reason = schedule.getReason();
            int column = schedule.getIntDay() - 1; 
    
            if (column < 0 || column > 7) {
                continue; 
            }
    
            int startTime = schedule.getStartTime();
            int endTime = schedule.getEndTime();
    
            if (startTime > endTime) {
                int startRow1 = (startTime / 100) * 4 + ((startTime % 100) / 15);
                int endRow1 = 95;
    
                int startRow2 = 0; 
                int endRow2 = (endTime / 100) * 4 + ((endTime % 100) / 15);
    
                if(column == 7){
                    for (int i = 0; i < 7; i++){
                        if (!hasConflict(i, startRow1, endRow1)) {
                            addPanel(i, startRow1, endRow1, reason, 4);
                            markTimeSlots(i, startRow1, endRow1, true);
                        }

                        int nextDayColumn = (i + 1) % 7; 
                        if (!hasConflict(nextDayColumn, startRow2, endRow2)) {
                            addPanel(nextDayColumn, startRow2, endRow2, reason, 4);
                            markTimeSlots(nextDayColumn, startRow2, endRow2, true);
                        }
                    }
                }
                else {
                    if (!hasConflict(column, startRow1, endRow1)) {
                        addPanel(column, startRow1, endRow1, reason, 4);
                        markTimeSlots(column, startRow1, endRow1, true);
                    }
        
                    int nextDayColumn = (column + 1) % 7; 
                    if (!hasConflict(nextDayColumn, startRow2, endRow2)) {
                        addPanel(nextDayColumn, startRow2, endRow2, reason, 4);
                        markTimeSlots(nextDayColumn, startRow2, endRow2, true);
                    }
                }
                
            } else {
                int startRow = (startTime / 100) * 4 + ((startTime % 100) / 15);
                int endRow = (endTime / 100) * 4 + ((endTime % 100) / 15);
    
                if (startRow < 0 || startRow >= ROWS || endRow < 0 || endRow >= ROWS || startRow > endRow) {
                    continue; 
                }
    
                if (column == 7) {
                    for (int i = 0; i < 7; i++) {
                        if (!hasConflict(i, startRow, endRow)) {
                            addPanel(i, startRow, endRow, reason, 4);
                            markTimeSlots(i, startRow, endRow, true);
                        }
                    }
                } else {
                    
                    if (!hasConflict(column, startRow, endRow)) { 
                        addPanel(column, startRow, endRow, reason, 4);
                        markTimeSlots(column, startRow, endRow, true);
                    }
                }
            }
        }
    
        TaskSorter taskSorter = new TaskSorter(); 
        taskSorter.sortTasks(taskList); 
        LocalDate currentDate = LocalDate.now();
        int intDayofTheWeek = (currentDate.getDayOfWeek().getValue() - 1);
        // Load tasks
        for (Task task : taskList) {
            if(task.getProgress().equals("Completed")){ 
                continue;
            }
            int taskColor = 0;
            LocalDate dueDate = task.getDueDate();
            long daysLeft = ChronoUnit.DAYS.between(currentDate, dueDate);
            if(task.getDifficulty().equals("Easy")){
                if(daysLeft <= 1)
                    taskColor = 1; //Red for do immediately
                else if(daysLeft <= 6 && daysLeft >= 2)
                    taskColor = 2; //Yellow for start early
                else
                    taskColor = 3; //Green for sched later
            }
            else if(task.getDifficulty().equals("Medium")){
                if(daysLeft <= 4)
                    taskColor = 1;  
                else if(daysLeft <= 5 && daysLeft >= 14)
                    taskColor = 2;
                else
                    taskColor = 3;
            }
            else if(task.getDifficulty().equals("Hard")){
                if(daysLeft <= 7)
                    taskColor = 1;
                else
                    taskColor = 2;
            }
            String reason = task.getName();
            
            int timeAllotted = task.getTimeAllotted(); 
            int intervals = (int) Math.ceil(timeAllotted / 15.0); 
            boolean taskAdded = false;
    
            for (int column = intDayofTheWeek; column < 7; column++) {
                int intervalsChecker = 0; 
                int startRow = 0;
    
                for (int row = 0; row < ROWS; row++) {
                    if (!taskConflict[row][column]) {
                        if (intervalsChecker == 0) {
                            startRow = row; 
                        }
                        intervalsChecker++;
    
                        if (intervalsChecker == intervals) {
                            int endRow = startRow + intervals - 1;
                            if (endRow >= ROWS) {
                                break; 
                            }
    
                            addPanel(column, startRow, endRow, reason, taskColor);
                            markTimeSlots(column, startRow, endRow, true);
                            taskAdded = true;
                            break;
                        }
                    } else {
                        intervalsChecker = 0; 
                    }
                }
    
                if (taskAdded) {
                    break; 
                }
            }
        }
    }
    private boolean hasConflict(int column, int startRow, int endRow) {
        for (int row = startRow; row <= endRow; row++) {
            if (taskConflict[row][column]) {
                return true;
            }
        }
        return false; 
    }
    private void markTimeSlots(int column, int startRow, int endRow, boolean occupied) {
        for (int row = startRow; row <= endRow; row++) {
            taskConflict[row][column] = occupied;
        }
    }

    private void addPanel(int column, int startRow, int endRow, String reason, int color) {
        int x = 15 + (column * (CELL_WIDTH + 5)); 
        int y = (2 + startRow) * CELL_HEIGHT;
        int height = (endRow - startRow + 1) * CELL_HEIGHT;
        RoundedPanel panel = new RoundedPanel();
        panel.setLayout(new BorderLayout());
        switch (color) {
            case 1: panel.setBackground(new Color(255, 218, 218)); break;
            case 2: panel.setBackground(new Color(220, 218, 255)); break;
            case 3: panel.setBackground(new Color(218, 255, 218)); break;
            case 4: panel.setBackground(new Color(255, 253, 218)); break;
            default: break;
        }
        
        panel.setBounds(x, y, CELL_WIDTH, height); 
        JLabel label = new JLabel("<html>" + reason + "</html>", SwingConstants.CENTER);
        panel.add(label);

        container.add(panel);
        container.repaint();
    }

    private void loadFont(String path, float size, Color color, Component... components) {
        try {
            InputStream fontStream = getClass().getResourceAsStream(path);
            if (fontStream == null) {
                System.err.println("Font not found: " + path);
                return;
            }
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(size);

            for (Component component : components) {
                if (component instanceof JLabel) {
                    JLabel label = (JLabel) component;
                    label.setFont(font);
                    label.setForeground(color);
                } else if (component instanceof MyButton) {
                    MyButton button = (MyButton) component;
                    button.setFont(font);
                    button.setForeground(color);
                } 
            }
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
}