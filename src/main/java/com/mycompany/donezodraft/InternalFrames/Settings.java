package com.mycompany.donezodraft.InternalFrames;

import com.mycompany.donezodraft.LoginSignUpForms.AccountsFileH;
import com.mycompany.donezodraft.LoginSignUpForms.User;
import com.mycompany.donezodraft.animations.MyButton;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.swing.table.JTableHeader;
import com.mycompany.donezodraft.LoginSignUpForms.AccountsFileH;
import com.mycompany.donezodraft.LoginSignUpForms.User;

public class Settings extends JInternalFrame {
    private JTable taskTable;
    private DefaultTableModel tableModel;
    private JPanel addEditPanel;
    private JComboBox<String> dayComboBox, startTimeHourComboBox, startTimeMinuteComboBox, startTimeAmPmComboBox;
    private JComboBox<String> endTimeHourComboBox, endTimeMinuteComboBox, endTimeAmPmComboBox;
    private JTextArea reasonTextArea;
    private JComboBox<String> frequencyComboBox;
    private JButton saveButton;
    private boolean isEditMode = false;
    private int editingRowIndex = -1;

    private static final String[] columnNames = {"Day", "Start Time", "End Time", "Reason", "Frequency"};
    private static final String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private static final String[] frequencies = {"Everyday", "Weekly", "Once"};
    private ArrayList<User> currentUser = AccountsFileH.funcReadUsersFromFile("currentUsersDatabase.txt");
    private String NAMEUSER = currentUser.get(0).getName();

    private String SCHEDFILE_PATH = NAMEUSER + "Scheddatabase.txt";

    private ArrayList<Schedule> schedules = new ArrayList<>();

    public Settings() {
        super("Settings", false, false, false, false);
        setSize(1000, 700);
        setLayout(new BorderLayout());
        setBorder(null);

        BasicInternalFrameUI internalFrameUI = (BasicInternalFrameUI) this.getUI();
        internalFrameUI.setNorthPane(null);

        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setBorder(new EmptyBorder(20, 20, 10, 10));
        titlePanel.setBackground(new Color(240, 237, 237)); 

        JLabel titleLabel = new JLabel("Settings", SwingConstants.LEFT);
        titlePanel.add(titleLabel);

        JLabel subTitleLabel = new JLabel("Customize Availability", SwingConstants.LEFT);
        titlePanel.add(subTitleLabel);

        add(titlePanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        taskTable = new JTable(tableModel);
        taskTable.setRowHeight(40);
        taskTable.setShowGrid(false);
        taskTable.setIntercellSpacing(new Dimension(0, 0));
        taskTable.getTableHeader().setBackground(new Color(240, 237, 237));
        taskTable.getTableHeader().setBorder(new EmptyBorder(10,10,10,10));

        taskTable.getColumnModel().getColumn(0).setPreferredWidth(80); 
        taskTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        taskTable.getColumnModel().getColumn(2).setPreferredWidth(80); 
        taskTable.getColumnModel().getColumn(3).setPreferredWidth(300); 
        taskTable.getColumnModel().getColumn(4).setPreferredWidth(80); 

        taskTable.setShowHorizontalLines(false);
        taskTable.setShowVerticalLines(false);
        taskTable.setBorder(BorderFactory.createEmptyBorder());
        taskTable.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel headerLabel = new JLabel(value.toString());
                headerLabel.setOpaque(true);
                headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
                headerLabel.setBackground(new Color(240, 237, 237)); 
                headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
                loadFont("/fontStyles/Outfit-Bold.ttf", 14f, new Color(28, 35, 74), headerLabel);
                return headerLabel;
            }
        });

        loadFont("/fontStyles/Outfit-ExtraBold.ttf", 28f, new Color(28,35,74), subTitleLabel);
        loadFont("/fontStyles/Outfit-Bold.ttf", 35f, new Color(12,26,43), titleLabel);
        loadFontTable("/fontStyles/Montserrat-SemiBold.ttf", 12f, new Color(28, 35, 74), taskTable);

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new FlowLayout(FlowLayout.CENTER)); 
        JScrollPane scrollPane = new JScrollPane(taskTable);
        scrollPane.setPreferredSize(new Dimension(950, 500)); 
        tablePanel.add(scrollPane);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        add(tablePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 20, 30));

        MyButton addScheduleButton = new MyButton();
        addScheduleButton.setText("Add Schedule");
        
        String imagePath = "C:\\Users\\jaspe\\OneDrive\\Desktop\\Jan14DoneZo-main\\Jan14UpdateDoneZo\\src\\main\\resources\\IconImages\\add white.png";
        ImageIcon icon = new ImageIcon(imagePath);
        addScheduleButton.setIcon(icon);
        addScheduleButton.setIcon(new ImageIcon(icon.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));

        addScheduleButton.addActionListener(e -> showAddEditPanel(false, -1));
        addScheduleButton.setColor(new Color(28, 35, 74)); 
        addScheduleButton.setColorOver(new Color(53, 97, 167)); 
        addScheduleButton.setColorClick(new Color(28, 35, 74));  
        addScheduleButton.setRadius(50); 
        addScheduleButton.setPreferredSize(new Dimension(190, 60)); 
        addScheduleButton.setBorderPainted(false); 
        addScheduleButton.setFocusPainted(false);
        loadFont("/fontStyles/Montserrat-ExtraBold.ttf", 14f, Color.WHITE, addScheduleButton);

        MyButton editScheduleButton = new MyButton();
        editScheduleButton.setText("Edit Schedule");
        
        String imagePath2 = "C:\\Users\\jaspe\\OneDrive\\Desktop\\Jan14DoneZo-main\\Jan14UpdateDoneZo\\src\\main\\resources\\IconImages\\edit white.png";
        ImageIcon icon2 = new ImageIcon(imagePath2);
        editScheduleButton.setIcon(icon2);
        editScheduleButton.setIcon(new ImageIcon(icon2.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));
        
        editScheduleButton.addActionListener(e -> {
            int selectedRow = taskTable.getSelectedRow();
            if (selectedRow != -1) {
                showAddEditPanel(true, selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        editScheduleButton.setColor(new Color(28, 35, 74)); 
        editScheduleButton.setColorOver(new Color(53, 97, 167)); 
        editScheduleButton.setColorClick(new Color(28, 35, 74));  
        editScheduleButton.setRadius(50); 
        editScheduleButton.setPreferredSize(new Dimension(190, 60)); 
        editScheduleButton.setBorderPainted(false); 
        editScheduleButton.setFocusPainted(false);
        loadFont("/fontStyles/Montserrat-ExtraBold.ttf", 14f, Color.WHITE, editScheduleButton);

        MyButton deleteScheduleButton = new MyButton();
        deleteScheduleButton.setText("Delete Schedule");
        String imagePath3 = "C:\\Users\\jaspe\\OneDrive\\Desktop\\Jan14DoneZo-main\\Jan14UpdateDoneZo\\src\\main\\resources\\IconImages\\delete white.png";
        ImageIcon icon3 = new ImageIcon(imagePath3);
        deleteScheduleButton.setIcon(icon3);
        deleteScheduleButton.setIcon(new ImageIcon(icon3.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));
        deleteScheduleButton.addActionListener(e -> {
            int selectedRow = taskTable.getSelectedRow();
            if (selectedRow != -1) {
                schedules.remove(selectedRow);
                tableModel.removeRow(selectedRow);
                ScheduleFileHandler.funcClearFile(SCHEDFILE_PATH);
                for (Schedule schedule : schedules) {
                    ScheduleFileHandler.funcAddScheduleToFile(SCHEDFILE_PATH, schedule);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        deleteScheduleButton.setColor(new Color(28, 35, 74)); 
        deleteScheduleButton.setColorOver(new Color(53, 97, 167)); 
        deleteScheduleButton.setColorClick(new Color(28, 35, 74));  
        deleteScheduleButton.setRadius(50); 
        deleteScheduleButton.setPreferredSize(new Dimension(190, 60)); 
        deleteScheduleButton.setBorderPainted(false); 
        deleteScheduleButton.setFocusPainted(false);
        loadFont("/fontStyles/Montserrat-ExtraBold.ttf", 14f, Color.WHITE, deleteScheduleButton);

        buttonPanel.add(addScheduleButton);
        buttonPanel.add(editScheduleButton);
        buttonPanel.add(deleteScheduleButton);

        add(buttonPanel, BorderLayout.SOUTH);

        initializeAddEditPanel();
        loadDataFromFile();
    }

    private void loadFontHeader(String path, float size, Color color) {
        try {
            InputStream fontStream = getClass().getResourceAsStream(path);
            if (fontStream == null) {
                System.err.println("Font not found: " + path);
                return;
            }
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(size);
            taskTable.getTableHeader().setFont(font);
            taskTable.getTableHeader().setForeground(color);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    private void loadFontTable(String path, float size, Color color, JTable table) {
        try {
            InputStream fontStream = getClass().getResourceAsStream(path);
            if (fontStream == null) {
                System.err.println("Font not found: " + path);
                return;
            }
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(size);
            table.setFont(font);

            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setForeground(color);
            renderer.setHorizontalAlignment(SwingConstants.CENTER);

            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(renderer);
            }
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
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
                } else if (component instanceof JTable) {
                    JTable table = (JTable) component;
                    table.setFont(font);

                    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                    renderer.setForeground(color);
                    renderer.setHorizontalAlignment(SwingConstants.CENTER);

                    for (int i = 0; i < table.getColumnCount(); i++) {
                        table.getColumnModel().getColumn(i).setCellRenderer(renderer);
                    }
                } else if (component instanceof JTableHeader) {
                    JTableHeader header = ((JTable) component).getTableHeader();
                    header.setFont(font);
                    header.setForeground(color);
                }
            }
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    private void initializeAddEditPanel() {
        addEditPanel = new JPanel();
        addEditPanel.setLayout(new GridLayout(7, 2, 20, 20)); 
        addEditPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        addEditPanel.setBackground(new Color(240, 237, 237)); 

        dayComboBox = new JComboBox<>(daysOfWeek);
        customizeAdvancedComboBox(dayComboBox);

        startTimeHourComboBox = new JComboBox<>(generateNumberStrings(1, 12));
        startTimeMinuteComboBox = new JComboBox<>(generateNumberStrings(0, 59));
        startTimeAmPmComboBox = new JComboBox<>(new String[]{"AM", "PM"});

        endTimeHourComboBox = new JComboBox<>(generateNumberStrings(1, 12));
        endTimeMinuteComboBox = new JComboBox<>(generateNumberStrings(0, 59));
        endTimeAmPmComboBox = new JComboBox<>(new String[]{"AM", "PM"});

        // Apply custom styling to all combo boxes
        customizeAdvancedComboBox(startTimeHourComboBox);
        customizeAdvancedComboBox(startTimeMinuteComboBox);
        customizeAdvancedComboBox(startTimeAmPmComboBox);
        customizeAdvancedComboBox(endTimeHourComboBox);
        customizeAdvancedComboBox(endTimeMinuteComboBox);
        customizeAdvancedComboBox(endTimeAmPmComboBox);

        reasonTextArea = new JTextArea(5, 20); // Increased rows for vertical expansion
        reasonTextArea.setLineWrap(true);
        reasonTextArea.setWrapStyleWord(true);
        reasonTextArea.setFont(new Font("Montserrat", Font.PLAIN, 14)); // Custom font for JTextArea
        reasonTextArea.setBackground(new Color(240, 240, 240)); // Light gray background for TextArea
        JScrollPane reasonScrollPane = new JScrollPane(reasonTextArea);

        frequencyComboBox = new JComboBox<>(frequencies);
        customizeAdvancedComboBox(frequencyComboBox);

        MyButton save = new MyButton();
        save.setText("Save");
        save.addActionListener(e -> saveSchedule());
        save.setBackground(new Color(140, 174, 210)); 
        save.setForeground(Color.WHITE);
        save.setFont(new Font("Montserrat", Font.BOLD, 16)); 
        save.setFocusPainted(false); // Remove focus paint when clicked
        save.setPreferredSize(new Dimension(120, 50)); // Adjust size of the button
        save.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding inside the button

        addEditPanel.add(new JLabel("Day:"));
        addEditPanel.add(dayComboBox);

        addEditPanel.add(new JLabel("Start Time:"));
        addEditPanel.add(createTimePanel(startTimeHourComboBox, startTimeMinuteComboBox, startTimeAmPmComboBox));

        addEditPanel.add(new JLabel("End Time:"));
        addEditPanel.add(createTimePanel(endTimeHourComboBox, endTimeMinuteComboBox, endTimeAmPmComboBox));

        addEditPanel.add(new JLabel("Reason:"));
        addEditPanel.add(reasonScrollPane);

        addEditPanel.add(new JLabel("Frequency:"));
        addEditPanel.add(frequencyComboBox);

        addEditPanel.add(new JLabel()); 
        addEditPanel.add(save);

        add(addEditPanel, BorderLayout.EAST);
        addEditPanel.setVisible(false);
  
        loadFont("/fontStyles/Montserrat-SemiBold.ttf", 14f, new Color(28,35,74), save);
        applyFontToPanel(addEditPanel);
    }

    public void applyFontToPanel(JPanel panel) {
        for (Component component : panel.getComponents()) {
            if (component instanceof JLabel jLabel) {
                loadFont("/fontStyles/Montserrat-SemiBold.ttf", 14f, Color.BLACK, jLabel);
            } else if (component instanceof JComboBox) {
                loadFont("/fontStyles/Montserrat-SemiBold.ttf", 14f, Color.BLACK, (JComboBox<?>) component);
            } else if (component instanceof JScrollPane scrollPane) {
                // If there is a component inside the JScrollPane (e.g., JTextArea or JTextField), apply the font to it
                Component innerComponent = scrollPane.getViewport().getView();
                if (innerComponent instanceof JTextArea jTextArea) {
                    loadFont("/fontStyles/Montserrat-SemiBold.ttf", 14f, Color.BLACK, jTextArea);
                }
            }
        }
    }
    
    public void customizeAdvancedComboBox(JComboBox<?> comboBox) {
        loadFont("/fontStyles/Montserrat-SemiBold.ttf", 14f, Color.WHITE, comboBox);

        comboBox.setBackground(new Color(240, 240, 240));
        comboBox.setPreferredSize(new Dimension(145, 30));

        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                loadFont("/fontStyles/Montserrat-SemiBold.ttf", 14f, isSelected ? Color.WHITE : Color.BLACK, label);

                label.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
                label.setOpaque(true);

                if (isSelected) {
                    label.setBackground(new Color(53, 97, 167));
                } else {
                    label.setBackground(Color.WHITE);
                }

                return label;
            }
        });
    }

    private JPanel createTimePanel(JComboBox hourComboBox, JComboBox minuteComboBox, JComboBox amPmComboBox) {
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        timePanel.add(hourComboBox);
        timePanel.add(new JLabel(":"));
        timePanel.add(minuteComboBox);
        timePanel.add(amPmComboBox);
        return timePanel;
    }

    private String[] generateNumberStrings(int start, int end) {
        String[] numbers = new String[end - start + 1];
        for (int i = start; i <= end; i++) {
            numbers[i - start] = String.format("%02d", i);
        }
        return numbers;
    }

    private void showAddEditPanel(boolean editMode, int rowIndex) {
        isEditMode = editMode;
        editingRowIndex = rowIndex;

        if (editMode && rowIndex != -1) {
            Schedule schedule = schedules.get(rowIndex);

            dayComboBox.setSelectedItem(schedule.getDay());
            int startHour = schedule.getStartTime() / 100;
            int startMinute = schedule.getStartTime() % 100;
            startTimeHourComboBox.setSelectedItem(String.format("%02d", startHour));
            startTimeMinuteComboBox.setSelectedItem(String.format("%02d", startMinute));
            startTimeAmPmComboBox.setSelectedItem(startHour < 12 ? "AM" : "PM");

            int endHour = schedule.getEndTime() / 100;
            int endMinute = schedule.getEndTime() % 100;
            endTimeHourComboBox.setSelectedItem(String.format("%02d", endHour));
            endTimeMinuteComboBox.setSelectedItem(String.format("%02d", endMinute));
            endTimeAmPmComboBox.setSelectedItem(endHour < 12 ? "AM" : "PM");

            reasonTextArea.setText(schedule.getReason());
            frequencyComboBox.setSelectedItem(schedule.getFrequency());
        } else {
            dayComboBox.setSelectedIndex(0);
            startTimeHourComboBox.setSelectedIndex(0);
            startTimeMinuteComboBox.setSelectedIndex(0);
            startTimeAmPmComboBox.setSelectedIndex(0);
            endTimeHourComboBox.setSelectedIndex(0);
            endTimeMinuteComboBox.setSelectedIndex(0);
            endTimeAmPmComboBox.setSelectedIndex(0);
            reasonTextArea.setText("");
            frequencyComboBox.setSelectedIndex(0);
        }

        addEditPanel.setVisible(true);
    }

    private void saveSchedule() {
        if (dayComboBox.getSelectedItem() == null ||
            startTimeHourComboBox.getSelectedItem() == null ||
            startTimeMinuteComboBox.getSelectedItem() == null ||
            startTimeAmPmComboBox.getSelectedItem() == null ||
            endTimeHourComboBox.getSelectedItem() == null ||
            endTimeMinuteComboBox.getSelectedItem() == null ||
            endTimeAmPmComboBox.getSelectedItem() == null ||
            reasonTextArea.getText().trim().isEmpty() ||
            frequencyComboBox.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(
                this, 
                "Please fill in all fields before saving.", 
                "Input Error", 
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String day = (String) dayComboBox.getSelectedItem();
        int startHour = Integer.parseInt((String) startTimeHourComboBox.getSelectedItem());
        int startMinute = Integer.parseInt((String) startTimeMinuteComboBox.getSelectedItem());
        String startAmPm = (String) startTimeAmPmComboBox.getSelectedItem();
        int startTime = (startHour % 12 + (startAmPm.equals("PM") ? 12 : 0)) * 100 + startMinute;

        int endHour = Integer.parseInt((String) endTimeHourComboBox.getSelectedItem());
        int endMinute = Integer.parseInt((String) endTimeMinuteComboBox.getSelectedItem());
        String endAmPm = (String) endTimeAmPmComboBox.getSelectedItem();
        int endTime = (endHour % 12 + (endAmPm.equals("PM") ? 12 : 0)) * 100 + endMinute;

        String reason = reasonTextArea.getText().trim();
        String frequency = (String) frequencyComboBox.getSelectedItem();

        Schedule schedule = new Schedule(day, startTime, endTime, reason, frequency);

        if (isEditMode && editingRowIndex != -1) {
            schedules.set(editingRowIndex, schedule);
            tableModel.setValueAt(schedule.getDay(), editingRowIndex, 0);
            tableModel.setValueAt(String.format("%02d:%02d", startTime / 100, startTime % 100), editingRowIndex, 1);
            tableModel.setValueAt(String.format("%02d:%02d", endTime / 100, endTime % 100), editingRowIndex, 2);
            tableModel.setValueAt(schedule.getReason(), editingRowIndex, 3);
            tableModel.setValueAt(schedule.getFrequency(), editingRowIndex, 4);
        } else {
            schedules.add(schedule);
            tableModel.addRow(new Object[]{
                schedule.getDay(),
                String.format("%02d:%02d", startTime / 100, startTime % 100),
                String.format("%02d:%02d", endTime / 100, endTime % 100),
                schedule.getReason(),
                schedule.getFrequency()
            });
        }

        // Save data to file
        saveDataToFile();

        // Hide the panel after saving
        addEditPanel.setVisible(false);
    }


    private void saveDataToFile() {
        try {
            ScheduleFileHandler.funcClearFile(SCHEDFILE_PATH);
            for (Schedule schedule : schedules) {
                ScheduleFileHandler.funcAddScheduleToFile(SCHEDFILE_PATH, schedule);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDataFromFile() {
        schedules.clear();
        tableModel.setRowCount(0);
    
        try {
            schedules = ScheduleFileHandler.funcReadFile(SCHEDFILE_PATH);
            for (Schedule schedule : schedules) {
                tableModel.addRow(new Object[]{
                        schedule.getDay(),
                        String.format("%02d:%02d", schedule.getStartTime() / 100, schedule.getStartTime() % 100),
                        String.format("%02d:%02d", schedule.getEndTime() / 100, schedule.getEndTime() % 100),
                        schedule.getReason(),
                        schedule.getFrequency()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
