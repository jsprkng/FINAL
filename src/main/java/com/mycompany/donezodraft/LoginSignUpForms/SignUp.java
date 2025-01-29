package com.mycompany.donezodraft.LoginSignUpForms;

import com.mycompany.donezodraft.LandingPage;
import com.mycompany.donezodraft.animations.MyButton;
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class SignUp extends javax.swing.JFrame {

    public SignUp() {
        initComponents();
        setBounds(450, 100, 1082, 747);
        
        loadFont("/fontStyles/Outfit-ExtraBold.ttf", 55f, new Color(240, 237, 237), lblLets);
        loadFont("/fontStyles/Outfit-ExtraBold.ttf", 55f, new Color(240, 237, 237), lblSmth);
        loadFont("/fontStyles/Outfit-ExtraBold.ttf", 55f, new Color(240, 237, 237), lblGreat);
        loadFont("/fontStyles/Montserrat-Medium.ttf", 15f, new Color(240, 237, 237), lblMessage);
        loadFont("/fontStyles/Montserrat-ExtraBold.ttf", 24f, new Color(28, 35, 74), lblLoginAcc);
        loadFont("/fontStyles/Montserrat-Regular.ttf", 16f, new Color(83, 88, 98), lblStart);
        loadFont("/fontStyles/Montserrat-Regular.ttf", 20f, Color.BLACK, txtPassword);
        loadFont("/fontStyles/Montserrat-Regular.ttf", 20f, Color.BLACK, txtUsername);
        loadFont("/fontStyles/Montserrat-Regular.ttf", 20f, Color.WHITE, txtName);
        loadFont("/fontStyles/Montserrat-Regular.ttf", 12f, new Color(83, 88, 98), lblQuestion);
        loadFont("/fontStyles/Montserrat-SemiBold.ttf", 12f, new Color(65, 70, 81), lblUsername);
        loadFont("/fontStyles/Montserrat-SemiBold.ttf", 12f, new Color(65, 70, 81), lblPassword);
        loadFont("/fontStyles/Montserrat-SemiBold.ttf", 12f, new Color(65, 70, 81), lblPassword1);
        loadFont("/fontStyles/Montserrat-SemiBold.ttf", 18f, new Color(240, 237, 237), btnCreate);
        loadFont("/fontStyles/Montserrat-SemiBold.ttf", 12f, new Color(28, 35, 74), lblLogin);
    
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
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblLoginAcc = new javax.swing.JLabel();
        lblStart = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        btnCreate = new javax.swing.JButton();
        lblLogin = new javax.swing.JLabel();
        lblQuestion = new javax.swing.JLabel();
        lblPassword1 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblLets = new javax.swing.JLabel();
        lblSmth = new javax.swing.JLabel();
        lblGreat = new javax.swing.JLabel();
        lblMessage = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLoginAcc.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblLoginAcc.setForeground(new java.awt.Color(1, 33, 66));
        lblLoginAcc.setText("Sign Up");
        getContentPane().add(lblLoginAcc, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, -1, -1));

        lblStart.setForeground(new java.awt.Color(102, 102, 102));
        lblStart.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblStart.setText("Start organizing with DoneZo! ");
        getContentPane().add(lblStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 220, 340, 20));

        lblUsername.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUsername.setForeground(new java.awt.Color(102, 102, 102));
        lblUsername.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblUsername.setText("Name*");
        getContentPane().add(lblUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 260, 140, -1));

        txtName.setForeground(new java.awt.Color(153, 153, 153));
        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });
        getContentPane().add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 280, 260, 30));

        lblPassword.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblPassword.setForeground(new java.awt.Color(102, 102, 102));
        lblPassword.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblPassword.setText("UserName*");
        getContentPane().add(lblPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 330, 110, -1));

        btnCreate.setBackground(new java.awt.Color(0, 0, 102));
        btnCreate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCreate.setForeground(new java.awt.Color(255, 255, 255));
        btnCreate.setText("Create Account");
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });
        getContentPane().add(btnCreate, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 480, 260, 30));

        lblLogin.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblLogin.setForeground(new java.awt.Color(33, 46, 91));
        lblLogin.setText("Login");
        lblLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLoginMouseClicked(evt);
            }
        });
        getContentPane().add(lblLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 520, -1, -1));

        lblQuestion.setForeground(new java.awt.Color(102, 102, 102));
        lblQuestion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQuestion.setText("Already have an account?");
        getContentPane().add(lblQuestion, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 520, 240, -1));

        lblPassword1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblPassword1.setForeground(new java.awt.Color(102, 102, 102));
        lblPassword1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblPassword1.setText("Password*");
        getContentPane().add(lblPassword1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 400, 110, -1));

        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });
        getContentPane().add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 350, 260, 30));

        lblLets.setBackground(new java.awt.Color(255, 255, 255));
        lblLets.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        lblLets.setForeground(new java.awt.Color(255, 255, 255));
        lblLets.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblLets.setText("Let's start");
        getContentPane().add(lblLets, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 190, 270, 80));

        lblSmth.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        lblSmth.setForeground(new java.awt.Color(255, 255, 255));
        lblSmth.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSmth.setText("something");
        getContentPane().add(lblSmth, new org.netbeans.lib.awtextra.AbsoluteConstraints(744, 250, 280, 70));

        lblGreat.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        lblGreat.setForeground(new java.awt.Color(255, 255, 255));
        lblGreat.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblGreat.setText("great!");
        getContentPane().add(lblGreat, new org.netbeans.lib.awtextra.AbsoluteConstraints(834, 310, 190, -1));

        lblMessage.setText("Make task management easy and breezy with DoneZo!");
        getContentPane().add(lblMessage, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 400, 430, 30));

        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });
        getContentPane().add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 420, 260, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImages/SignUp.jpg"))); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 710));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
        LandingPage Home = new LandingPage();
        String name = txtName.getText();
        String userName = txtUsername.getText();
        String password = txtPassword.getText();
        if(name.isBlank() || userName.isBlank() || password.isBlank()){
            JOptionPane.showMessageDialog(null, "Enter valid strings!", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ArrayList<User> existingUsers = new ArrayList<>();
        if(AccountsFileH.funcReadUsersFromFile("src/main/resources/databases/usersDatabase.txt").isEmpty()){
            AccountsFileH.funcWriteUsersToFile("src/main/resources/databases/usersDatabase.txt", new ArrayList<>(Arrays.asList(new User(name, userName, password))));
        }
        else{
            existingUsers.addAll(AccountsFileH.funcReadUsersFromFile("src/main/resources/databases/usersDatabase.txt"));
        for(User user : existingUsers){
            if(user.getUsername().equals(userName)){
                JOptionPane.showMessageDialog(null, "Username already exists!", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
            AccountsFileH.funcAddUserToFile("src/main/resources/databases/usersDatabase.txt", new User(name, userName, password));
        }


        Home.setVisible(true);
        Home.pack();
        Home.setLocationRelativeTo(null);
        Home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_btnCreateActionPerformed

    private void lblLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLoginMouseClicked
        LoginForm Login = new LoginForm();
        Login.setVisible(true);
        Login.pack();
        Login.setLocationRelativeTo(null);
        Login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_lblLoginMouseClicked

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SignUp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblGreat;
    private javax.swing.JLabel lblLets;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblLoginAcc;
    private javax.swing.JLabel lblMessage;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblPassword1;
    private javax.swing.JLabel lblQuestion;
    private javax.swing.JLabel lblSmth;
    private javax.swing.JLabel lblStart;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JTextField txtName;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
