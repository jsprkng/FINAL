package com.mycompany.donezodraft.InternalFrames;

import com.mycompany.donezodraft.LoginSignUpForms.AccountsFileH;
import com.mycompany.donezodraft.LoginSignUpForms.User;
import com.mycompany.donezodraft.animations.MyButton;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.*;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Date;
import java.time.ZoneId;
import com.mycompany.donezodraft.LoginSignUpForms.AccountsFileH;
import com.mycompany.donezodraft.LoginSignUpForms.User;

public class TaskList extends JInternalFrame {
    private JTable taskTable;
    private DefaultTableModel tableModel;
    private JPanel inputPanel;
    private String[] columnNames = { "Task Name", "Description", "Due Date", "Time", "Status", "Difficulty" };
    private static final ArrayList<Task> tasks = new ArrayList<>();
    private static final String[] statuses = { "Not Yet Started", "In Progress", "Completed" };
    private static final String[] difficulties = { "Easy", "Medium", "Hard" };
    private ArrayList<User> currentUser = AccountsFileH.funcReadUsersFromFile("currentUsersDatabase.txt");
    private  String NAMEUSER = currentUser.get(0).getName();
    private  String FILE_PATH = NAMEUSER + "database.txt";
    
    public TaskList() {
        super("My Tasks", false, false, false, false); 
        setSize(979, 693);
        setLayout(new BorderLayout());
        setBorder(null); 
        
        BasicInternalFrameUI internalFrameUI = (BasicInternalFrameUI) this.getUI();
        internalFrameUI.setNorthPane(null); 

        JLabel titleLabel = new JLabel("My Tasks", SwingConstants.LEFT);
        try (InputStream fontStream = getClass().getResourceAsStream("/fontStyles/Outfit-ExtraBold.ttf")) {
            titleLabel.setFont(Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(26f));
        } catch (Exception e) {
            e.printStackTrace();
        }
        titleLabel.setForeground(new Color(15, 26, 43));
        titleLabel.setBorder(new EmptyBorder(20, 20, 10, 10));
        add(titleLabel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 4 || column == 5) {
                    return true;
                }
                return super.isCellEditable(row, column); 
            }
        };


        taskTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                if (c instanceof JLabel) {
                    ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
                }
                if (isRowSelected(row)) {
                    c.setBackground(new Color(140, 174, 210));
                    c.setForeground(Color.BLACK); 
                } 
                return c;
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        taskTable.setRowHeight(40);
        taskTable.setShowGrid(false);
        taskTable.setIntercellSpacing(new Dimension(0, 0));
        taskTable.getColumnModel().getColumn(0).setPreferredWidth(120); 
        taskTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        taskTable.getColumnModel().getColumn(2).setPreferredWidth(30); 
        taskTable.getColumnModel().getColumn(3).setPreferredWidth(30); 
        taskTable.getColumnModel().getColumn(4).setPreferredWidth(40); 
        taskTable.getColumnModel().getColumn(5).setPreferredWidth(30);
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

        taskTable.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(createColoredDropdown(statuses)));
        taskTable.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(createColoredDropdown(difficulties)));
        taskTable.getModel().addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();

            if (column == 4 || column == 5) {
                String newValue = (String) taskTable.getValueAt(row, column);
                Task task = tasks.get(row);

                if (column == 4) {
                    task.setProgress(newValue);
                } else if (column == 5) {
                    task.setDifficulty(newValue);
                }

                FileH.funcSaveTasksToFile(FILE_PATH, tasks);
            }
        });
               
        JScrollPane scrollPane = new JScrollPane(taskTable);
        scrollPane = new JScrollPane(taskTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(scrollPane, BorderLayout.CENTER);

        inputPanel = createInputPanel();
        inputPanel.setVisible(false);

        JPanel buttonPanel = createButtonPanel();

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        loadTasks();

        String[] sortOptions = { "Sort By:", "Chronological", "By Due Date", "By Status", "By Difficulty", "By Hours Needed" };
        JComboBox<String> sortDropdown = new JComboBox<>(sortOptions);
        customizeAdvancedComboBox(sortDropdown);  

        sortDropdown.setPreferredSize(new Dimension(150, 30));
        sortDropdown.setSelectedIndex(0);
        sortDropdown.addActionListener(e -> sortTasks((String) sortDropdown.getSelectedItem()));

        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sortPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        sortPanel.add(sortDropdown);
        add(sortPanel, BorderLayout.NORTH);
       
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(sortPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);
        
        applyColumnCustomRendering(0, "/fontStyles/Montserrat-ExtraBold.ttf", 16f, new Color(15, 26, 43));
        applyColumnCustomRendering(1, "/fontStyles/Montserrat-Medium.ttf", 12, Color.BLACK);  
        applyColumnCustomRendering(2, "/fontStyles/Montserrat-Medium.ttf", 12f, Color.BLACK); 
        applyColumnCustomRendering(3, "/fontStyles/Montserrat-Medium.ttf", 12f, Color.BLACK);
        loadFontHeader("/fontStyles/Outfit-Bold.ttf", 14f, new Color(28, 35, 74));
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
    
    private void applyColumnCustomRendering(int columnIndex, String fontPath, float fontSize, Color fontColor) {
        try (InputStream fontStream = getClass().getResourceAsStream(fontPath)) {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(fontSize);

            taskTable.getColumnModel().getColumn(columnIndex).setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                    label.setFont(customFont);
                    label.setForeground(fontColor);
                    label.setHorizontalAlignment(SwingConstants.CENTER);

                    if ("Completed".equals(value) || "Easy".equals(value)) {
                        label.setBackground(new Color(198, 253, 202)); 
                        label.setForeground(new Color(21, 112, 27));   
                    } else if ("In Progress".equals(value) || "Medium".equals(value)) {
                        label.setBackground(new Color(201, 209, 255));
                        label.setForeground(new Color(28, 35, 74));    
                    } else if ("Not Yet Started".equals(value)) {
                        label.setBackground(new Color(207, 170, 255)); 
                        label.setForeground(new Color(94, 10, 203));    
                    } else if ("Hard".equals(value)) {
                        label.setBackground(new Color(255, 189, 189)); 
                        label.setForeground(new Color(214, 0, 0));     
                    } else {
                        label.setBackground(Color.WHITE); 
                        label.setForeground(Color.BLACK);  
                    }

                    label.setOpaque(true); 
                    return label;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sortTasks(String sortOption) {
        switch (sortOption) {
            case "Chronological":
            case "By Due Date":
                tasks.sort((t1, t2) -> {
                    if (t1.getDueDate() == null && t2.getDueDate() != null) return 1;
                    if (t1.getDueDate() != null && t2.getDueDate() == null) return -1;
                    if (t1.getDueDate() == null) return 0;
                    return t1.getDueDate().compareTo(t2.getDueDate());
                });
                break;
            case "By Status":           
                tasks.sort((t1, t2) -> {
                    int status1 = mapStatusToRank(t1.getProgress());
                    int status2 = mapStatusToRank(t2.getProgress());
                    return Integer.compare(status1, status2);
                });
                break;
            case "By Difficulty":
                tasks.sort((t1, t2) -> {
                    int difficulty1 = mapDifficultyToRank(t1.getDifficulty());
                    int difficulty2 = mapDifficultyToRank(t2.getDifficulty());
                    return Integer.compare(difficulty1, difficulty2);
                });
                break;
            case "By Hours Needed (Biggest to Smallest)":
                tasks.sort((t1, t2) -> Integer.compare(t2.getTimeAllotted(), t1.getTimeAllotted()));
                break;
        }
        updateTaskList();
    }
    
    private int mapStatusToRank(String status) {
        switch (status) {
            case "Not Yet Started": return 1;
            case "In Progress": return 2;
            case "Completed": return 3;
            default: return Integer.MAX_VALUE; 
        }
    }

    private int mapDifficultyToRank(String difficulty) {
        switch (difficulty) {
            case "Easy": return 1;
            case "Medium": return 2;
            case "Hard": return 3;
            default: return Integer.MAX_VALUE;
        }
    }


    private JComboBox<String> createColoredDropdown(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fontStyles/Montserrat-SemiBold.ttf")).deriveFont(14f);
            comboBox.setFont(customFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        applyColumnCustomRendering(4, "/fontStyles/Montserrat-SemiBold.ttf", 14f, new Color(15, 26, 43));
        applyColumnCustomRendering(5, "/fontStyles/Montserrat-SemiBold.ttf", 14f, new Color(15, 26, 43));

        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (c instanceof JLabel) {
                    JLabel label = (JLabel) c;
                    label.setHorizontalAlignment(SwingConstants.CENTER);

                    try {
                        Font customFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fontStyles/Montserrat-SemiBold.ttf")).deriveFont(14f);
                        label.setFont(customFont);
                    } catch (FontFormatException | IOException e) {
                        e.printStackTrace();
                    }

                    // Color based on status or difficulty
                    if ("Completed".equals(value) || "Easy".equals(value)) {
                        label.setBackground(new Color(198, 253, 202)); 
                        label.setForeground(new Color(21, 112, 27));    
                    } else if ("In Progress".equals(value) || "Medium".equals(value)) {
                        label.setBackground(new Color(201, 209, 255));
                        label.setForeground(new Color(28, 35, 74));     
                    } else if ("Not Yet Started".equals(value)) {
                        label.setBackground(new Color(207, 170, 255)); 
                        label.setForeground(new Color(94, 10, 203));    
                    } else if ("Hard".equals(value)) {
                        label.setBackground(new Color(255, 189, 189)); 
                        label.setForeground(new Color(214, 0, 0));     
                    }
                    label.setOpaque(true);
                }
                return c;
            }
        });

        return comboBox;
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(1, columnNames.length, 10, 0));
        panel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JTextField taskNameField = new JTextField();
        JTextField taskDescriptionField = new JTextField();

        JDateChooser dueDateChooser = new JDateChooser();
        dueDateChooser.setDateFormatString("yyyy-MM-dd");

        JTextField timeField = new JTextField();
        JComboBox<String> statusField = new JComboBox<>(statuses);
        JComboBox<String> difficultyField = new JComboBox<>(difficulties);

        panel.add(taskNameField);
        panel.add(taskDescriptionField);
        panel.add(dueDateChooser); 
        panel.add(timeField);
        panel.add(statusField);
        panel.add(difficultyField);

        MyButton addbutton = new MyButton();
        addbutton.setText("Add Task");
        addbutton.addActionListener(e -> {
            try {
                LocalDate dueDate = dueDateChooser.getDate() != null ? LocalDate.ofInstant(dueDateChooser.getDate().toInstant(), ZoneId.systemDefault()) : null;

                Task newTask = new Task(
                    taskNameField.getText(),
                    taskDescriptionField.getText(),
                    dueDate,
                    Integer.parseInt(timeField.getText()),
                    (String) statusField.getSelectedItem(),
                    (String) difficultyField.getSelectedItem()
                );

                tasks.add(newTask);
                tableModel.addRow(new Object[] {
                    newTask.getName(),
                    newTask.getDescription(),
                    newTask.getDueDate() != null ? newTask.getDueDate().toString() : "",
                    newTask.getTimeAllotted(),
                    newTask.getProgress(),
                    newTask.getDifficulty()
                });
                FileH.funcAddTaskToFile(FILE_PATH, newTask); 
                clearFields(taskNameField, taskDescriptionField, dueDateChooser, timeField, statusField, difficultyField); 
                inputPanel.setVisible(false); 
                revalidate(); 
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(addbutton);
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
     
        MyButton addTaskButton = new MyButton();
        addTaskButton.setText("Add Task");
        addTaskButton.setColor(new Color(28, 35, 74)); 
        addTaskButton.setColorOver(new Color(53, 97, 167)); 
        addTaskButton.setColorClick(new Color(28, 35, 74));  
        addTaskButton.setRadius(50); 
        addTaskButton.setPreferredSize(new Dimension(150, 60));
        addTaskButton.addActionListener(e -> {
            JTextField taskNameField = new JTextField();
            JTextField taskDescriptionField = new JTextField();
            JDateChooser dueDateChooser = new JDateChooser();
            JTextField timeField = new JTextField();
            JComboBox<String> statusField = new JComboBox<>(statuses);
            JComboBox<String> difficultyField = new JComboBox<>(difficulties);
            clearFields(taskNameField, taskDescriptionField, dueDateChooser, timeField, statusField, difficultyField);
         
            MyButton addbutton = new MyButton();
            addbutton.setText("Add Task");
            addbutton.setColor(new Color(28, 35, 74)); 
            addbutton.setColorOver(new Color(53, 97, 167)); 
            addbutton.setColorClick(new Color(28, 35, 74));  
            addbutton.setRadius(50); 
            loadFont("/fontStyles/Montserrat-ExtraBold.ttf", 14f, Color.WHITE, addbutton);
            addbutton.addActionListener(addTaskActionListener(taskNameField, taskDescriptionField, dueDateChooser, timeField, statusField, difficultyField));

            inputPanel.removeAll();
            inputPanel.setLayout(new GridLayout(1, columnNames.length, 10, 0));
            inputPanel.add(taskNameField);
            inputPanel.add(taskDescriptionField);
            inputPanel.add(dueDateChooser);
            inputPanel.add(timeField);
            inputPanel.add(statusField);
            inputPanel.add(difficultyField);
            inputPanel.add(addbutton);

            inputPanel.setVisible(true);  
            revalidate(); 
        });
        addTaskButton.setBorderPainted(false); 
        addTaskButton.setFocusPainted(false);
        String imagePath = "C:\\Users\\jaspe\\OneDrive\\Desktop\\Jan14DoneZo-main\\Jan14UpdateDoneZo\\src\\main\\resources\\IconImages\\add white.png";
        ImageIcon icon = new ImageIcon(imagePath);
        addTaskButton.setIcon(icon);
        addTaskButton.setIcon(new ImageIcon(icon.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));
        loadFont("/fontStyles/Montserrat-ExtraBold.ttf", 14f, Color.WHITE, addTaskButton);
        panel.add(addTaskButton);

        MyButton editTaskButton = new MyButton();
        editTaskButton.setText("Edit Task");
        editTaskButton.setColor(new Color(28, 35, 74)); 
        editTaskButton.setColorOver(new Color(53, 97, 167)); 
        editTaskButton.setColorClick(new Color(28, 35, 74));  
        editTaskButton.setRadius(50); 
        editTaskButton.setPreferredSize(new Dimension(150, 60));
        editTaskButton.addActionListener(e -> editTask());  
        editTaskButton.setBorderPainted(false); 
        editTaskButton.setFocusPainted(false);
        String imagePath2 = "C:\\Users\\jaspe\\OneDrive\\Desktop\\Jan14DoneZo-main\\Jan14UpdateDoneZo\\src\\main\\resources\\IconImages\\edit white.png";
        ImageIcon icon2 = new ImageIcon(imagePath2);
        editTaskButton.setIcon(icon2);
        editTaskButton.setIcon(new ImageIcon(icon2.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));
        loadFont("/fontStyles/Montserrat-ExtraBold.ttf", 14f, Color.WHITE, editTaskButton);
        panel.add(editTaskButton);
        
        MyButton deleteTaskButton = new MyButton();
        deleteTaskButton.setText("Delete Task");
        deleteTaskButton.setColor(new Color(28, 35, 74)); 
        deleteTaskButton.setColorOver(new Color(53, 97, 167)); 
        deleteTaskButton.setColorClick(new Color(28, 35, 74));  
        deleteTaskButton.setRadius(50); 
        deleteTaskButton.setPreferredSize(new Dimension(160, 60));
        deleteTaskButton.addActionListener(e -> deleteTask());
        deleteTaskButton.addActionListener(e -> inputPanel.setVisible(true)); 
        deleteTaskButton.setBorderPainted(false); 
        deleteTaskButton.setFocusPainted(false);
        String imagePath3 = "C:\\Users\\jaspe\\OneDrive\\Desktop\\Jan14DoneZo-main\\Jan14UpdateDoneZo\\src\\main\\resources\\IconImages\\delete white.png";
        ImageIcon icon3 = new ImageIcon(imagePath3);
        deleteTaskButton.setIcon(icon3);
        deleteTaskButton.setIcon(new ImageIcon(icon3.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));
        loadFont("/fontStyles/Montserrat-ExtraBold.ttf", 14f, Color.WHITE, deleteTaskButton);
        panel.add(deleteTaskButton);

        return panel;
    }
    
    private ActionListener addTaskActionListener(JTextField taskNameField, JTextField taskDescriptionField, 
                                                JDateChooser dueDateChooser, JTextField timeField, 
                                                JComboBox<String> statusField, JComboBox<String> difficultyField) {
      return e -> {
          try {
              // Validation: Check if all fields are filled
              if (taskNameField.getText().trim().isEmpty()) {
                  JOptionPane.showMessageDialog(this, "Task name cannot be empty.", "Input Error", JOptionPane.WARNING_MESSAGE);
                  return;
              }
              if (taskDescriptionField.getText().trim().isEmpty()) {
                  JOptionPane.showMessageDialog(this, "Task description cannot be empty.", "Input Error", JOptionPane.WARNING_MESSAGE);
                  return;
              }
              if (dueDateChooser.getDate() == null) {
                  JOptionPane.showMessageDialog(this, "Due date must be selected.", "Input Error", JOptionPane.WARNING_MESSAGE);
                  return;
              }
              if (timeField.getText().trim().isEmpty()) {
                  JOptionPane.showMessageDialog(this, "Time allotted cannot be empty.", "Input Error", JOptionPane.WARNING_MESSAGE);
                  return;
              }
              if (statusField.getSelectedItem() == null) {
                  JOptionPane.showMessageDialog(this, "Please select a status.", "Input Error", JOptionPane.WARNING_MESSAGE);
                  return;
              }
              if (difficultyField.getSelectedItem() == null) {
                  JOptionPane.showMessageDialog(this, "Please select a difficulty.", "Input Error", JOptionPane.WARNING_MESSAGE);
                  return;
              }

              // Validation: Check if time input is a non-negative integer
              int timeAllotted;
              try {
                  timeAllotted = Integer.parseInt(timeField.getText().trim());
                  if (timeAllotted < 0) {
                      throw new NumberFormatException("Time must be a non-negative integer.");
                  }
              } catch (NumberFormatException ex) {
                  JOptionPane.showMessageDialog(this, "Invalid time input. Please enter a non-negative integer.", "Input Error", JOptionPane.WARNING_MESSAGE);
                  return;
              }

              // Get the due date
              LocalDate dueDate = LocalDate.ofInstant(dueDateChooser.getDate().toInstant(), ZoneId.systemDefault());

              // Create a new task
              Task newTask = new Task(
                  taskNameField.getText(),
                  taskDescriptionField.getText(),
                  dueDate,
                  timeAllotted,
                  (String) statusField.getSelectedItem(),
                  (String) difficultyField.getSelectedItem()
              );

              // Add task to list and table
              tasks.add(newTask);
              tableModel.addRow(new Object[] {
                  newTask.getName(),
                  newTask.getDescription(),
                  newTask.getDueDate() != null ? newTask.getDueDate().toString() : "",
                  newTask.getTimeAllotted(),
                  newTask.getProgress(),
                  newTask.getDifficulty()
              });

              // Save task to file
              FileH.funcAddTaskToFile(FILE_PATH, newTask);

              // Clear fields and hide input panel
              clearFields(taskNameField, taskDescriptionField, dueDateChooser, timeField, statusField, difficultyField); 
              inputPanel.setVisible(false); 
              revalidate(); 

          } catch (Exception ex) {
              JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
          }
      };
  }

    private void loadTasks() {
        ArrayList<Task> loadedTasks = FileH.funcReadFile(FILE_PATH);
        if (loadedTasks != null) {
            tasks.clear();
            tasks.addAll(loadedTasks);
            updateTaskList();
        } else {
            System.out.println("No saved tasks found.");
        }
    }

    private void updateTaskList() {
        tableModel.setRowCount(0); 
        for (Task task : tasks) {
            tableModel.addRow(new Object[] {
                task.getName(),
                task.getDescription(),
                task.getDueDate() != null ? task.getDueDate().toString() : "",
                task.getTimeAllotted(),
                task.getProgress(),
                task.getDifficulty()
            });
        }
    }


    private void editTask() {
        int selectedRow = taskTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a task to edit!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String currentTaskName = (String) tableModel.getValueAt(selectedRow, 0);
        String currentDescription = (String) tableModel.getValueAt(selectedRow, 1);
        String currentDueDate = (String) tableModel.getValueAt(selectedRow, 2);
        String currentTime = String.valueOf(tableModel.getValueAt(selectedRow, 3));
        String currentStatus = (String) tableModel.getValueAt(selectedRow, 4);
        String currentDifficulty = (String) tableModel.getValueAt(selectedRow, 5);

        JTextField taskNameField = new JTextField(currentTaskName);
        JTextField taskDescriptionField = new JTextField(currentDescription);

        JDateChooser dueDateChooser = new JDateChooser();
        try {
            if (currentDueDate != null && !currentDueDate.isEmpty()) {
                dueDateChooser.setDate(Date.valueOf(currentDueDate));
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        }

        JTextField timeField = new JTextField(currentTime);
        JComboBox<String> statusField = new JComboBox<>(statuses);
        statusField.setSelectedItem(currentStatus);
        JComboBox<String> difficultyField = new JComboBox<>(difficulties);
        difficultyField.setSelectedItem(currentDifficulty);

        inputPanel.removeAll();  
        inputPanel.setLayout(new GridLayout(1, columnNames.length, 10, 0)); // Adjust layout if necessary
        inputPanel.add(taskNameField);
        inputPanel.add(taskDescriptionField);
        inputPanel.add(dueDateChooser);
        inputPanel.add(timeField);
        inputPanel.add(statusField);
        inputPanel.add(difficultyField);

        MyButton submitButton = new MyButton();
        submitButton.setText("Update");
        submitButton.setColor(new Color(28, 35, 74)); 
        submitButton.setColorOver(new Color(53, 97, 167)); 
        submitButton.setColorClick(new Color(28, 35, 74));  
        submitButton.setRadius(50); 
        loadFont("/fontStyles/Montserrat-ExtraBold.ttf", 14f, Color.WHITE, submitButton);
        submitButton.addActionListener(e -> {
            try {
                if (taskNameField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Task name cannot be empty.", "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (taskDescriptionField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Task description cannot be empty.", "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (dueDateChooser.getDate() == null) {
                    JOptionPane.showMessageDialog(this, "Due date must be selected.", "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (timeField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Time allotted cannot be empty.", "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (statusField.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(this, "Please select a status.", "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (difficultyField.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(this, "Please select a difficulty.", "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int timeAllotted;
            try {
                timeAllotted = Integer.parseInt(timeField.getText().trim());
                if (timeAllotted < 0) {
                    throw new NumberFormatException("Time must be a non-negative integer.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid time input. Please enter a non-negative integer.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
                LocalDate dueDate = dueDateChooser.getDate() != null ? LocalDate.ofInstant(dueDateChooser.getDate().toInstant(), ZoneId.systemDefault()) : null;
                Task updatedTask = tasks.get(selectedRow);

                updatedTask.setName(taskNameField.getText());
                updatedTask.setDescription(taskDescriptionField.getText());
                updatedTask.setDueDate(dueDate);
                updatedTask.setTimeAllotted(Integer.parseInt(timeField.getText()));
                updatedTask.setProgress((String) statusField.getSelectedItem());
                updatedTask.setDifficulty((String) difficultyField.getSelectedItem());

                tableModel.setValueAt(updatedTask.getName(), selectedRow, 0);
                tableModel.setValueAt(updatedTask.getDescription(), selectedRow, 1);
                tableModel.setValueAt(updatedTask.getDueDate() != null ? updatedTask.getDueDate().toString() : "", selectedRow, 2);
                tableModel.setValueAt(updatedTask.getTimeAllotted(), selectedRow, 3);
                tableModel.setValueAt(updatedTask.getProgress(), selectedRow, 4);
                tableModel.setValueAt(updatedTask.getDifficulty(), selectedRow, 5);

                FileH.funcWriteAllTasksToFile(FILE_PATH, tasks);

                inputPanel.setVisible(false);  
                revalidate();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error updating task: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        inputPanel.add(submitButton);
        inputPanel.setVisible(true);  
        revalidate();  
    }


    private void deleteTask() {
        int selectedRow = taskTable.getSelectedRow();
            if (selectedRow != -1) {
                tasks.remove(selectedRow);
                tableModel.removeRow(selectedRow);
                FileH.funcWriteAllTasksToFile(FILE_PATH, tasks);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a task to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            }
    }

    private void clearFields(JTextField taskNameField, JTextField taskDescriptionField, 
                          JDateChooser dueDateChooser, JTextField timeField, 
                          JComboBox<String> statusField, JComboBox<String> difficultyField) {
        taskNameField.setText("");
        taskDescriptionField.setText("");
        dueDateChooser.setDate(null); // Clears the date
        timeField.setText("");
        statusField.setSelectedIndex(0); 
        difficultyField.setSelectedIndex(0);
    }
}