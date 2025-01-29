package com.mycompany.donezodraft.InternalFrames;

import com.mycompany.donezodraft.animations.MyButton;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import com.mycompany.donezodraft.LoginSignUpForms.AccountsFileH;
import com.mycompany.donezodraft.LoginSignUpForms.User;

public class Dashboard extends javax.swing.JInternalFrame {
    private static final ArrayList<Task> tasks = new ArrayList<>();
    static int intTaskCounter = 0;
    static int intRemainingTask = 0;
    static float flTimeAllotted = 0;
    static int intCompleted = 0;
    static int intDoImmediately = 0;
    static int intStartEarly = 0;
    static int intScheduleLater = 0;
    private ArrayList<User> currentUser = AccountsFileH.funcReadUsersFromFile("currentUsersDatabase.txt");
    private String NAMEUSER = currentUser.get(0).getName();
    private String FILE_PATH = NAMEUSER + "database.txt";
    private JPanel pnlDashboard;
    private JPanel pnlTaskList;
    private JPanel pnlWorkflow;
    private JPanel pnlSettings;
    private Color DefaultColor;
    private Color ClickedColor;

    public Dashboard(JPanel pnlDashboard, JPanel pnlTaskList, JPanel pnlWorkflow, JPanel pnlSettings, Color DefaultColor, Color ClickedColor) {
        this.pnlDashboard = pnlDashboard;
        this.pnlTaskList = pnlTaskList;
        this.pnlWorkflow = pnlWorkflow;
        this.pnlSettings = pnlSettings;
        this.DefaultColor = DefaultColor;
        this.ClickedColor = ClickedColor;
        loadTasks();
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI UI = (BasicInternalFrameUI) this.getUI();
        UI.setNorthPane(null);

        loadFont("/fontStyles/Outfit-ExtraBold.ttf", 28f, new Color(15, 26, 43), lblHeading);
        loadFont("/fontStyles/Outfit-ExtraBold.ttf", 40f, new Color(240, 237, 237), lblGreeting);
        loadFont("/fontStyles/Montserrat-Medium.ttf", 16f, new Color(240, 237, 237), lblMessage);
        loadFont("/fontStyles/Outfit-Bold.ttf", 20f, new Color(15, 26, 43), lblTasks, lblTime, lblProgress, lblPriorities);
        loadFont("/fontStyles/Outfit-Regular.ttf", 16f, new Color(15, 26, 43), lblTasksRemaining, lblTimeRemaining, lblProgressDone);
        loadFont("/fontStyles/Outfit-SemiBold.ttf", 14f, new Color(112, 21, 21), lblImmediate );
        loadFont("/fontStyles/Outfit-SemiBold.ttf", 14f, new Color(21, 47, 112), lblStartEarly );
        loadFont("/fontStyles/Outfit-SemiBold.ttf", 14f, new Color(21, 112, 27), lblScheduleLater );
        loadFont("/fontStyles/Outfit-Regular.ttf", 16f, new Color(15, 26, 43), lblCountImmediate, lblCountStart, lblCountLater);
        
        String imagePath = "src\\main\\resources\\IconImages\\may box.png";
        ImageIcon icon = new ImageIcon(imagePath);
        lblMascot.setIcon(new ImageIcon(icon.getImage().getScaledInstance(lblMascot.getWidth(), lblMascot.getHeight(), java.awt.Image.SCALE_SMOOTH)));
        lblMascot.setText("");
        
        initializeListeners();
        
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

    public void scaleImage() {
        // Scale and set images for all labels
        setScaledImage(iconTasks, "/IconImages/tasks.png");
        setScaledImage(iconTime, "/IconImages/time.png");
        setScaledImage(iconProgress, "/IconImages/progress.png");
    }

    private void setScaledImage(JLabel label, String resourcePath) {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(resourcePath));
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaledImg));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addResizeListenerToLabels(JLabel... labels) {
        ComponentAdapter resizeListener = new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                scaleImage();
            }
        };

        for (JLabel label : labels) {
            label.addComponentListener(resizeListener);
        }
    }

    private void initializeListeners() {
        // Add the resize listener to all relevant labels
        addResizeListenerToLabels(iconTasks, iconTime, iconProgress);
    }
    private void loadTasks() {
        ArrayList<Task> loadedTasks = FileH.funcReadFile(FILE_PATH);
        intCompleted = 0;
        if (loadedTasks != null) {
            tasks.clear();
            tasks.addAll(loadedTasks);
            dashboardFunctions(loadedTasks);
        } else {
            System.out.println("No saved tasks found.");
        }
    }
    private void dashboardFunctions(ArrayList<Task> tasks){
            intTaskCounter = tasks.size();
            
        intRemainingTask = 0;
        for (Task task : tasks) {
                if(task.getProgress().equals("Completed")) //For Completed
                {
                    intCompleted++; 
                }
                else{
                    flTimeAllotted += task.getTimeAllotted();//For Time Remaining
                    intRemainingTask++;
                }
            }
        flTimeAllotted = flTimeAllotted / 60; //For minutes to hours
        intStartEarly = 0;
        intDoImmediately = 0;
        intScheduleLater = 0;
        LocalDate currentDate = LocalDate.now();
        for (Task task : tasks) {
            LocalDate dueDate = task.getDueDate();
            long daysLeft = ChronoUnit.DAYS.between(currentDate, dueDate);
            if(!(task.getProgress().equals("Completed"))){
                if(task.getDifficulty().equals("Easy")){
                    if(daysLeft <= 1)
                        intDoImmediately++;
                    else if(daysLeft <= 6 && daysLeft >= 2)
                        intStartEarly++;
                    else
                        intScheduleLater++;
                }
                else if(task.getDifficulty().equals("Medium")){
                    if(daysLeft <= 4)
                        intDoImmediately++;
                    else if(daysLeft <= 5 && daysLeft >= 14)
                        intStartEarly++;
                    else
                        intScheduleLater++;
                }
                else if(task.getDifficulty().equals("Hard")){
                    if(daysLeft <= 7)
                        intDoImmediately++;
                    else
                        intStartEarly++;
                }
            }
            
        }
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        ArrayList<User> currentUser = AccountsFileH.funcReadUsersFromFile("currentUsersDatabase.txt");
        String name = "Name";
        if(!(currentUser.isEmpty())){
            name = currentUser.get(0).getName();
        }
            

        jPasswordField1 = new javax.swing.JPasswordField();
        lblMascot = new javax.swing.JLabel();
        lblHeading = new javax.swing.JLabel();
        pnlGreeting = new RoundedPanel();
        lblGreeting = new javax.swing.JLabel();
        lblMessage = new javax.swing.JLabel();
        lblSummary = new javax.swing.JLabel();
        pnlProgress = new RoundedPanel();
        iconProgress = new javax.swing.JLabel();
        lblProgressDone = new javax.swing.JLabel();
        lblProgress = new javax.swing.JLabel();
        progressBar1 = new com.mycompany.donezodraft.ProgressBar();
        pnlPriorities = new RoundedPanel();
        lblPriorities = new javax.swing.JLabel();
        lblImmediate = new javax.swing.JLabel();
        lblCountImmediate = new javax.swing.JLabel();
        lblStartEarly = new javax.swing.JLabel();
        lblCountStart = new javax.swing.JLabel();
        lblScheduleLater = new javax.swing.JLabel();
        lblCountLater = new javax.swing.JLabel();
        pnlTime = new RoundedPanel();
        iconTime = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        lblTimeRemaining = new javax.swing.JLabel();
        pnlTaskRemaining = new RoundedPanel();
        iconTasks = new javax.swing.JLabel();
        lblTasks = new javax.swing.JLabel();
        lblTasksRemaining = new javax.swing.JLabel();

        jPasswordField1.setText("jPasswordField1");

        setBackground(new java.awt.Color(231, 231, 231));
        setPreferredSize(new java.awt.Dimension(979, 693));
        setRequestFocusEnabled(false);
        getContentPane().setLayout(null);
        getContentPane().add(lblMascot);
        lblMascot.setBounds(520, 60, 340, 170);

        lblHeading.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblHeading.setText("Dashboard");
        getContentPane().add(lblHeading);
        lblHeading.setBounds(28, 17, 183, 48);

        pnlGreeting.setBackground(new java.awt.Color(82, 103, 125));
        pnlGreeting.setForeground(new java.awt.Color(0, 0, 102));

        lblGreeting.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblGreeting.setForeground(new java.awt.Color(255, 255, 255));
        lblGreeting.setText("Hello, " + name +"!");

        lblMessage.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMessage.setForeground(new java.awt.Color(255, 255, 255));
        lblMessage.setText("Are you ready to start your day with me?");

        javax.swing.GroupLayout pnlGreetingLayout = new javax.swing.GroupLayout(pnlGreeting);
        pnlGreeting.setLayout(pnlGreetingLayout);
        pnlGreetingLayout.setHorizontalGroup(
            pnlGreetingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGreetingLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlGreetingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlGreetingLayout.createSequentialGroup()
                        .addComponent(lblMessage)
                        .addGap(0, 136, Short.MAX_VALUE))
                    .addComponent(lblGreeting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(482, 482, 482))
        );
        pnlGreetingLayout.setVerticalGroup(
            pnlGreetingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGreetingLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(lblGreeting, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        getContentPane().add(pnlGreeting);
        pnlGreeting.setBounds(28, 77, 895, 137);

        lblSummary.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblSummary.setForeground(new java.awt.Color(102, 102, 102));
        lblSummary.setText("Task Summary");
        getContentPane().add(lblSummary);
        lblSummary.setBounds(28, 232, 137, 25);

        pnlProgress.setBackground(new java.awt.Color(255, 255, 255));

        lblProgressDone.setForeground(new java.awt.Color(1, 33, 66));
        lblProgressDone.setText(intCompleted + " / " + intTaskCounter +" Tasks Completed! "
        );

        lblProgress.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblProgress.setForeground(new java.awt.Color(1, 33, 66));
        lblProgress.setText("Your Progress");
        progressBar1.setForeground(new java.awt.Color(0, 0, 102));
        progressBar1.setMaximum(intTaskCounter);
        progressBar1.setValue(intCompleted);

        javax.swing.GroupLayout pnlProgressLayout = new javax.swing.GroupLayout(pnlProgress);
        pnlProgress.setLayout(pnlProgressLayout);
        pnlProgressLayout.setHorizontalGroup(
            pnlProgressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProgressLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(iconProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlProgressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlProgressLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnlProgressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblProgress)
                            .addComponent(lblProgressDone)))
                    .addGroup(pnlProgressLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(progressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        pnlProgressLayout.setVerticalGroup(
            pnlProgressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProgressLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnlProgressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlProgressLayout.createSequentialGroup()
                        .addComponent(lblProgress)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblProgressDone)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(progressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(iconProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        getContentPane().add(pnlProgress);
        pnlProgress.setBounds(28, 509, 584, 131);

        pnlPriorities.setBackground(new java.awt.Color(255, 255, 255));

        lblPriorities.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblPriorities.setForeground(new java.awt.Color(1, 33, 66));
        lblPriorities.setText("Tasks Priorities");

        lblImmediate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblImmediate.setForeground(new java.awt.Color(119, 19, 19));
        lblImmediate.setText("Do Immediately:");

        lblCountImmediate.setForeground(new java.awt.Color(1, 33, 66));
        lblCountImmediate.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCountImmediate.setText(intDoImmediately + " task/s remaining");

        lblStartEarly.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblStartEarly.setForeground(new java.awt.Color(21, 41, 124));
        lblStartEarly.setText("Start Early:");

        lblCountStart.setForeground(new java.awt.Color(1, 33, 66));
        lblCountStart.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCountStart.setText(intStartEarly + " task/s remaining");

        lblScheduleLater.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblScheduleLater.setForeground(new java.awt.Color(43, 117, 24));
        lblScheduleLater.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblScheduleLater.setText("Schedule Later:");

        lblCountLater.setForeground(new java.awt.Color(1, 33, 66));
        lblCountLater.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCountLater.setText(intScheduleLater + " task/s remaining");

        javax.swing.GroupLayout pnlPrioritiesLayout = new javax.swing.GroupLayout(pnlPriorities);
        pnlPriorities.setLayout(pnlPrioritiesLayout);
        pnlPrioritiesLayout.setHorizontalGroup(
            pnlPrioritiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrioritiesLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnlPrioritiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPriorities)
                    .addGroup(pnlPrioritiesLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(pnlPrioritiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblImmediate)
                            .addComponent(lblStartEarly)
                            .addComponent(lblScheduleLater)
                            .addGroup(pnlPrioritiesLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(pnlPrioritiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCountImmediate)
                                    .addComponent(lblCountStart)
                                    .addComponent(lblCountLater))))))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        pnlPrioritiesLayout.setVerticalGroup(
            pnlPrioritiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrioritiesLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lblPriorities)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblImmediate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCountImmediate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStartEarly)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCountStart)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblScheduleLater)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCountLater)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(pnlPriorities);
        pnlPriorities.setBounds(390, 263, 222, 233);

        pnlTime.setBackground(new java.awt.Color(255, 255, 255));

        lblTime.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTime.setForeground(new java.awt.Color(1, 33, 66));
        lblTime.setText("Time");

        lblTimeRemaining.setForeground(new java.awt.Color(1, 33, 66));
        lblTimeRemaining.setText(String.format("%.1f", flTimeAllotted) + " Hours Remaining");

        javax.swing.GroupLayout pnlTimeLayout = new javax.swing.GroupLayout(pnlTime);
        pnlTime.setLayout(pnlTimeLayout);
        pnlTimeLayout.setHorizontalGroup(
            pnlTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTimeLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(iconTime, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTime)
                    .addComponent(lblTimeRemaining))
                .addContainerGap(172, Short.MAX_VALUE))
        );
        pnlTimeLayout.setVerticalGroup(
            pnlTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTimeLayout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(pnlTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTimeLayout.createSequentialGroup()
                        .addComponent(lblTime)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTimeRemaining))
                    .addComponent(iconTime, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        getContentPane().add(pnlTime);
        pnlTime.setBounds(28, 389, 344, 107);

        pnlTaskRemaining.setBackground(new java.awt.Color(255, 255, 255));

        lblTasks.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTasks.setForeground(new java.awt.Color(1, 33, 66));
        lblTasks.setText("Tasks");

        lblTasksRemaining.setForeground(new java.awt.Color(1, 33, 66));
        lblTasksRemaining.setText(intRemainingTask + " Task/s Remaining");

        javax.swing.GroupLayout pnlTaskRemainingLayout = new javax.swing.GroupLayout(pnlTaskRemaining);
        pnlTaskRemaining.setLayout(pnlTaskRemainingLayout);
        pnlTaskRemainingLayout.setHorizontalGroup(
            pnlTaskRemainingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTaskRemainingLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(iconTasks, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlTaskRemainingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTasks)
                    .addComponent(lblTasksRemaining))
                .addContainerGap(167, Short.MAX_VALUE))
        );
        pnlTaskRemainingLayout.setVerticalGroup(
            pnlTaskRemainingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTaskRemainingLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(pnlTaskRemainingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(iconTasks, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlTaskRemainingLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(lblTasks)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTasksRemaining)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        getContentPane().add(pnlTaskRemaining);
        pnlTaskRemaining.setBounds(28, 263, 344, 108);
        
        CalendarPanel customCalendar = new CalendarPanel();
        getContentPane().add(customCalendar);
        customCalendar.setBounds(640, 263, 270, 280);

        MyButton btnWorkflow = new MyButton();
        btnWorkflow.setColor(new Color(28, 35, 74)); 
        btnWorkflow.setColorOver(new Color(53, 97, 167)); 
        btnWorkflow.setColorClick(new Color(28, 35, 74));  
        btnWorkflow.setRadius(50);         
        btnWorkflow.setText("Workflow");
        btnWorkflow.setBorderPainted(false);  
        btnWorkflow.setFocusPainted(false);
        String imagePath = "src\\main\\resources\\Icon\\imgWorkflow.png";
        ImageIcon icon = new ImageIcon(imagePath);
        btnWorkflow.setIcon(icon);
        btnWorkflow.setIcon(new ImageIcon(icon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH)));
        btnWorkflow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWorkflowActionPerformed(evt);
            }
        });
        loadFont("/fontStyles/Montserrat-ExtraBold.ttf", 12f, Color.WHITE, btnWorkflow);
        getContentPane().add(btnWorkflow);
        btnWorkflow.setBounds(645, 560, 130, 60); 

        MyButton btnAddTask = new MyButton();
        btnAddTask.setColor(new Color(28, 35, 74)); 
        btnAddTask.setColorOver(new Color(53, 97, 167)); 
        btnAddTask.setColorClick(new Color(28, 35, 74));  
        btnAddTask.setRadius(50); 
        btnAddTask.setText("TaskList");
        btnAddTask.setBorderPainted(false);  
        btnAddTask.setFocusPainted(false);
        String imagePath2 = "src\\main\\resources\\Icon\\imgTasks.png";
        ImageIcon icon2 = new ImageIcon(imagePath2);
        btnAddTask.setIcon(icon2);
        btnAddTask.setIcon(new ImageIcon(icon2.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH)));
        btnAddTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTaskActionPerformed(evt);
            }
        });
        loadFont("/fontStyles/Montserrat-ExtraBold.ttf", 12f, Color.WHITE, btnAddTask);
        getContentPane().add(btnAddTask);
        btnAddTask.setBounds(780, 560, 130, 60);

        setBounds(0, 0, 992, 699);
    }// </editor-fold>//GEN-END:initComponents

    private void btnWorkflowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWorkflowActionPerformed
        JDesktopPane desktopPane = getDesktopPane();
        pnlDashboard.setBackground(DefaultColor);
        pnlTaskList.setBackground(DefaultColor);
        pnlWorkflow.setBackground(ClickedColor);
        pnlSettings.setBackground(DefaultColor);
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            frame.dispose();
        }

        Workflow workflow = new Workflow();
        desktopPane.add(workflow);
        workflow.setVisible(true); 
        
    }//GEN-LAST:event_btnWorkflowActionPerformed

    private void btnAddTaskActionPerformed(java.awt.event.ActionEvent evt) {                                           
        JDesktopPane desktopPane = getDesktopPane();
        pnlDashboard.setBackground(DefaultColor);
        pnlTaskList.setBackground(ClickedColor);
        pnlWorkflow.setBackground(DefaultColor);
        pnlSettings.setBackground(DefaultColor);
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            frame.dispose();
        }

        TaskList tasks = new TaskList();
        desktopPane.add(tasks);
        tasks.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel iconProgress;
    private javax.swing.JLabel iconTasks;
    private javax.swing.JLabel iconTime;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JLabel lblCountImmediate;
    private javax.swing.JLabel lblCountLater;
    private javax.swing.JLabel lblCountStart;
    private javax.swing.JLabel lblGreeting;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblImmediate;
    private javax.swing.JLabel lblMascot;
    private javax.swing.JLabel lblMessage;
    private javax.swing.JLabel lblPriorities;
    private javax.swing.JLabel lblProgress;
    private javax.swing.JLabel lblProgressDone;
    private javax.swing.JLabel lblScheduleLater;
    private javax.swing.JLabel lblStartEarly;
    private javax.swing.JLabel lblSummary;
    private javax.swing.JLabel lblTasks;
    private javax.swing.JLabel lblTasksRemaining;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblTimeRemaining;
    private javax.swing.JPanel pnlGreeting;
    private javax.swing.JPanel pnlPriorities;
    private javax.swing.JPanel pnlProgress;
    private javax.swing.JPanel pnlTaskRemaining;
    private javax.swing.JPanel pnlTime;
    private com.mycompany.donezodraft.ProgressBar progressBar1;
    // End of variables declaration//GEN-END:variables
}
