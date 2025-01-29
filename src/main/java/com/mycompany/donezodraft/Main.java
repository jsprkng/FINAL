package com.mycompany.donezodraft;

import com.mycompany.donezodraft.InternalFrames.Dashboard;
import com.mycompany.donezodraft.InternalFrames.FileH;
import com.mycompany.donezodraft.InternalFrames.RoundedPanel;
import com.mycompany.donezodraft.InternalFrames.Workflow;
import com.mycompany.donezodraft.LoginSignUpForms.AccountsFileH;
import com.mycompany.donezodraft.LoginSignUpForms.LoginForm;
import com.mycompany.donezodraft.LoginSignUpForms.User;
import com.mycompany.donezodraft.InternalFrames.TaskList;
import com.mycompany.donezodraft.InternalFrames.Settings;
import com.mycompany.donezodraft.InternalFrames.Task;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.swing.*;

public class Main extends javax.swing.JFrame {
    Color DefaultColor, ClickedColor;
    private static final ArrayList<Task> tasks = new ArrayList<>();
    static int intTaskCounter = 0;
    static int intRemainingTask = 0;
    static float flTimeAllotted = 0;
    static int intCompleted = 0;
    static int intDoImmediately = 0;
    static int intStartEarly = 0;
    static int intScheduleLater = 0;
    private String DIRECTORY = "src/main/resources/databases/";
    private ArrayList<User> currentUser = AccountsFileH.funcReadUsersFromFile(DIRECTORY + "currentUsersDatabase.txt");
    private String NAMEUSER = currentUser.get(0).getName();
    private String FILE_PATH = DIRECTORY + NAMEUSER + "database.txt";
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

    public Main() {
        loadTasks();
        initComponents();
        DefaultColor = new Color(189, 196, 212);
        ClickedColor = new Color(28, 35, 74);
        
        pnlDashboard.setBackground(ClickedColor);
        pnlTaskList.setBackground(DefaultColor);
        pnlWorkflow.setBackground(DefaultColor);
        pnlSettings.setBackground(DefaultColor);
        
        setBounds(350,120,1285,708);
                
        try {
        InputStream outfitFontStream = getClass().getResourceAsStream("/fontStyles/Montserrat-ExtraBold.ttf");
        Font outfitFont = Font.createFont(Font.TRUETYPE_FONT, outfitFontStream).deriveFont(22f);
        lblDashboard.setFont(outfitFont);
        lblSettings.setFont(outfitFont);
        lblWorkflow.setFont(outfitFont);
        lblTaskList.setFont(outfitFont);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try {
        InputStream outfitFontStream = getClass().getResourceAsStream("/fontStyles/Outfit-ExtraBold.ttf");
        Font outfitFont = Font.createFont(Font.TRUETYPE_FONT, outfitFontStream).deriveFont(25f);
        lblLogo.setFont(outfitFont);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Dashboard dashboard = new Dashboard(pnlDashboard, pnlTaskList, pnlWorkflow, pnlSettings, DefaultColor, ClickedColor);
        pnlMain.removeAll();
        pnlMain.add(dashboard);
        dashboard.setVisible(true);
        
        String basePath = "src\\main\\resources\\IconImages\\";
        setIcon(basePath + "with bg.png", lblLogo, 50, 50, 5);
        setIcon("src\\main\\resources\\Icon\\imgDashboard.png", lblDashboard, 30, 30, 10);
        setIcon("src\\main\\resources\\Icon\\imgTasks.png", lblTaskList, 30, 30, 18);
        setIcon("src\\main\\resources\\Icon\\imgWorkflow.png", lblWorkflow, 30, 30, 15);
        setIcon("src\\main\\resources\\Icon\\imgSettings.png", lblSettings, 30, 30, 25);
        setIcon("src\\main\\resources\\Icon\\imgLogout.png", lblLogout, 30, 30, 25);

  
        pnlMain.revalidate();
        pnlMain.repaint();
    }
    
        private void setIcon(String imagePath, JLabel label, int width, int height, int gap) {
            ImageIcon icon = new ImageIcon(imagePath);
            label.setIcon(new ImageIcon(icon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH)));
            label.setIconTextGap(gap); 
            label.setHorizontalTextPosition(SwingConstants.RIGHT); 
        }
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMenu = new javax.swing.JPanel();
        pnlDashboard = new javax.swing.JPanel();
        lblDashboard = new javax.swing.JLabel();
        pnlTaskList = new javax.swing.JPanel();
        lblTaskList = new javax.swing.JLabel();
        pnlWorkflow = new javax.swing.JPanel();
        lblWorkflow = new javax.swing.JLabel();
        pnlSettings = new javax.swing.JPanel();
        lblSettings = new javax.swing.JLabel();
        lblLogo = new javax.swing.JLabel();
        lblLogout = new javax.swing.JLabel();
        pnlMain = new javax.swing.JDesktopPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlMenu.setBackground(new java.awt.Color(189, 196, 212));
        pnlMenu.setForeground(new java.awt.Color(189, 196, 212));
        pnlMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlDashboard.setBackground(new java.awt.Color(189, 196, 212));
        pnlDashboard.setForeground(new java.awt.Color(189, 196, 212));
        pnlDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlDashboardMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlDashboardMousePressed(evt);
            }
        });

        lblDashboard.setBackground(new java.awt.Color(194, 204, 214));
        lblDashboard.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblDashboard.setForeground(new java.awt.Color(255, 255, 255));
        lblDashboard.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDashboard.setText("Dashboard");

        javax.swing.GroupLayout pnlDashboardLayout = new javax.swing.GroupLayout(pnlDashboard);
        pnlDashboard.setLayout(pnlDashboardLayout);
        pnlDashboardLayout.setHorizontalGroup(
            pnlDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblDashboard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
        );
        pnlDashboardLayout.setVerticalGroup(
            pnlDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblDashboard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );

        pnlMenu.add(pnlDashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 270, 70));

        pnlTaskList.setBackground(new java.awt.Color(189, 196, 212));
        pnlTaskList.setForeground(new java.awt.Color(189, 196, 212));
        pnlTaskList.setPreferredSize(new java.awt.Dimension(270, 70));
        pnlTaskList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlTaskListMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlTaskListMousePressed(evt);
            }
        });

        lblTaskList.setBackground(new java.awt.Color(200, 209, 218));
        lblTaskList.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTaskList.setForeground(new java.awt.Color(255, 255, 255));
        lblTaskList.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTaskList.setText("My Tasks");

        javax.swing.GroupLayout pnlTaskListLayout = new javax.swing.GroupLayout(pnlTaskList);
        pnlTaskList.setLayout(pnlTaskListLayout);
        pnlTaskListLayout.setHorizontalGroup(
            pnlTaskListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTaskListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTaskList, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        pnlTaskListLayout.setVerticalGroup(
            pnlTaskListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTaskListLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblTaskList, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlMenu.add(pnlTaskList, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 270, -1));

        pnlWorkflow.setBackground(new java.awt.Color(189, 196, 212));
        pnlWorkflow.setForeground(new java.awt.Color(189, 196, 212));
        pnlWorkflow.setPreferredSize(new java.awt.Dimension(270, 70));
        pnlWorkflow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlWorkflowMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlWorkflowMousePressed(evt);
            }
        });

        lblWorkflow.setBackground(new java.awt.Color(200, 209, 218));
        lblWorkflow.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblWorkflow.setForeground(new java.awt.Color(255, 255, 255));
        lblWorkflow.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblWorkflow.setText("Workflow");

        javax.swing.GroupLayout pnlWorkflowLayout = new javax.swing.GroupLayout(pnlWorkflow);
        pnlWorkflow.setLayout(pnlWorkflowLayout);
        pnlWorkflowLayout.setHorizontalGroup(
            pnlWorkflowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlWorkflowLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lblWorkflow, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        pnlWorkflowLayout.setVerticalGroup(
            pnlWorkflowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlWorkflowLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblWorkflow, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlMenu.add(pnlWorkflow, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 270, -1));

        pnlSettings.setBackground(new java.awt.Color(189, 196, 212));
        pnlSettings.setForeground(new java.awt.Color(189, 196, 212));
        pnlSettings.setPreferredSize(new java.awt.Dimension(270, 70));
        pnlSettings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlSettingsMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlSettingsMousePressed(evt);
            }
        });

        lblSettings.setBackground(new java.awt.Color(189, 196, 212));
        lblSettings.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblSettings.setForeground(new java.awt.Color(255, 255, 255));
        lblSettings.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSettings.setText("Settings");

        javax.swing.GroupLayout pnlSettingsLayout = new javax.swing.GroupLayout(pnlSettings);
        pnlSettings.setLayout(pnlSettingsLayout);
        pnlSettingsLayout.setHorizontalGroup(
            pnlSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSettingsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );
        pnlSettingsLayout.setVerticalGroup(
            pnlSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSettings, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );

        pnlMenu.add(pnlSettings, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, -1, -1));

        lblLogo.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        lblLogo.setForeground(new java.awt.Color(28, 35, 74));
        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setText("DoneZo!");
        pnlMenu.add(lblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 200, 50));

        lblLogout.setBackground(new java.awt.Color(189, 196, 212));
        lblLogout.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblLogout.setForeground(new java.awt.Color(255, 255, 255));
        lblLogout.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogout.setText("Logout");
        lblLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLogoutMouseClicked(evt);
            }
        });
        pnlMenu.add(lblLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 610, 170, 50));

        getContentPane().add(pnlMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 274, 693));

        pnlMain.setBackground(new java.awt.Color(231, 231, 231));
        pnlMain.setPreferredSize(new java.awt.Dimension(979, 693));
        getContentPane().add(pnlMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void pnlDashboardMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDashboardMousePressed
        pnlDashboard.setBackground(ClickedColor);
        pnlTaskList.setBackground(DefaultColor);
        pnlWorkflow.setBackground(DefaultColor);
        pnlSettings.setBackground(DefaultColor);
    }//GEN-LAST:event_pnlDashboardMousePressed

    private void pnlTaskListMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTaskListMousePressed
        // TODO add your handling code here:
        pnlDashboard.setBackground(DefaultColor);
        pnlTaskList.setBackground(ClickedColor);
        pnlWorkflow.setBackground(DefaultColor);
        pnlSettings.setBackground(DefaultColor);
    }//GEN-LAST:event_pnlTaskListMousePressed

    private void pnlWorkflowMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlWorkflowMousePressed
        // TODO add your handling code here:
        pnlDashboard.setBackground(DefaultColor);
        pnlTaskList.setBackground(DefaultColor);
        pnlWorkflow.setBackground(ClickedColor);
        pnlSettings.setBackground(DefaultColor);
    }//GEN-LAST:event_pnlWorkflowMousePressed

    private void pnlSettingsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSettingsMousePressed
        // TODO add your handling code here:
        pnlDashboard.setBackground(DefaultColor);
        pnlTaskList.setBackground(DefaultColor);
        pnlWorkflow.setBackground(DefaultColor);
        pnlSettings.setBackground(ClickedColor);
    }//GEN-LAST:event_pnlSettingsMousePressed

    private void pnlDashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDashboardMouseClicked
        // TODO add your handling code here:
        Dashboard dashboard = new Dashboard(pnlDashboard, pnlTaskList, pnlWorkflow, pnlSettings, DefaultColor, ClickedColor);
        pnlMain.removeAll();
        pnlMain.add(dashboard).setVisible(true);
    }//GEN-LAST:event_pnlDashboardMouseClicked

    private void pnlTaskListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTaskListMouseClicked
        // Remove all components from pnlMain
        pnlMain.removeAll();

        // Create an instance of DoneZo
        TaskList doneZo = new TaskList();

        // Add DoneZo to pnlMain and make it visible
        pnlMain.add(doneZo);
        doneZo.setVisible(true);

        // Refresh pnlMain
        pnlMain.revalidate();
        pnlMain.repaint();
    }//GEN-LAST:event_pnlTaskListMouseClicked

    private void pnlWorkflowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlWorkflowMouseClicked
        // TODO add your handling code here:
        Workflow workflow = new Workflow();
        pnlMain.removeAll();
        pnlMain.add(workflow).setVisible(true);
    }//GEN-LAST:event_pnlWorkflowMouseClicked

    private void pnlSettingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSettingsMouseClicked
        // TODO add your handling code here:
        Settings settings = new Settings();
        pnlMain.removeAll();
        pnlMain.add(settings).setVisible(true);
    }//GEN-LAST:event_pnlSettingsMouseClicked

    private void lblLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogoutMouseClicked
            // LoginForm Login = new LoginForm();
            // Login.setVisible(true);
            // Login.pack();
            // Login.setLocationRelativeTo(null);
            // Login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // this.dispose();
        LandingPage Home = new LandingPage();
        Home.setVisible(true);
        Home.pack();
        Home.setLocationRelativeTo(null);
        Home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_lblLogoutMouseClicked

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
            
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblDashboard;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblLogout;
    private javax.swing.JLabel lblSettings;
    private javax.swing.JLabel lblTaskList;
    private javax.swing.JLabel lblWorkflow;
    private javax.swing.JPanel pnlDashboard;
    private javax.swing.JDesktopPane pnlMain;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlSettings;
    private javax.swing.JPanel pnlTaskList;
    private javax.swing.JPanel pnlWorkflow;
    // End of variables declaration//GEN-END:variables


}
