/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.donezodraft.LoginSignUpForms;

import com.mycompany.donezodraft.Main;
import com.mycompany.donezodraft.animations.MyButton;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.swing.*;

public class LoginForm extends javax.swing.JFrame {
    private final ArrayList<User> userAccounts = new ArrayList<>();

    public LoginForm() {
        initComponents();
        setBounds(450, 100, 1082, 747);
        
        loadFont("/fontStyles/Outfit-ExtraBold.ttf", 55f, new Color(240, 237, 237), lblOrganized);
        loadFont("/fontStyles/Outfit-ExtraBold.ttf", 55f, new Color(240, 237, 237), lblAgain);
        loadFont("/fontStyles/Outfit-ExtraBold.ttf", 55f, new Color(240, 237, 237), lblReady);
        loadFont("/fontStyles/Montserrat-Medium.ttf", 15f, new Color(240, 237, 237), lblMessage);
        loadFont("/fontStyles/Montserrat-ExtraBold.ttf", 24f, new Color(28, 35, 74), lblLoginAcc);
        loadFont("/fontStyles/Montserrat-Regular.ttf", 16f, new Color(83, 88, 98), lblWelcome);
        loadFont("/fontStyles/Montserrat-Regular.ttf", 20f, Color.BLACK, txtUserName);
        loadFont("/fontStyles/Montserrat-Regular.ttf", 20f, Color.BLACK, txtPassword);
        loadFont("/fontStyles/Montserrat-Regular.ttf", 12f, new Color(83, 88, 98), lblQuestion);
        loadFont("/fontStyles/Montserrat-Medium.ttf", 12f, new Color(65, 70, 81), lblUsername);
        loadFont("/fontStyles/Montserrat-Medium.ttf", 12f, new Color(65, 70, 81), lblPassword);
        loadFont("/fontStyles/Montserrat-Medium.ttf", 15f, new Color(65, 70, 81), cbRememberMe);
        loadFont("/fontStyles/Montserrat-SemiBold.ttf", 18f, new Color(240, 237, 237), btnLogin);
        loadFont("/fontStyles/Montserrat-SemiBold.ttf", 12f, new Color(28, 35, 74), lblForgotPass);
        loadFont("/fontStyles/Montserrat-SemiBold.ttf", 12f, new Color(28, 35, 74), lblSignUp);


        lblLogo.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                scaleImage(); 
            }
        });

        scaleImage();
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
    public void scaleImage() {
        if (lblLogo.getWidth() > 0 && lblLogo.getHeight() > 0) {
            // Load and scale the image
            ImageIcon icon = new ImageIcon(getClass().getResource("/IconImages/logomark2.png"));
            Image img = icon.getImage();

            // Scale the image to fit the label's size
            Image imgScale = img.getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(), Image.SCALE_SMOOTH);

            // Set the scaled image to the label
            ImageIcon scaledIcon = new ImageIcon(imgScale);
            lblLogo.setIcon(scaledIcon); 
        }
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblOrganized = new javax.swing.JLabel();
        lblAgain = new javax.swing.JLabel();
        lblReady = new javax.swing.JLabel();
        lblMessage = new javax.swing.JLabel();
        lblLoginAcc = new javax.swing.JLabel();
        lblWelcome = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        txtUserName = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        lblLogo = new javax.swing.JLabel();
        lblForgotPass = new javax.swing.JLabel();
        MyButton btnLogin = new MyButton();
        cbRememberMe = new javax.swing.JCheckBox();
        lblQuestion = new javax.swing.JLabel();
        lblSignUp = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        lblImage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LMS");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblOrganized.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        lblOrganized.setForeground(new java.awt.Color(255, 255, 255));
        lblOrganized.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblOrganized.setText("organized");
        getContentPane().add(lblOrganized, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, -1, -1));

        lblAgain.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        lblAgain.setForeground(new java.awt.Color(255, 255, 255));
        lblAgain.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblAgain.setText("again?");
        getContentPane().add(lblAgain, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 330, -1, -1));

        lblReady.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        lblReady.setForeground(new java.awt.Color(255, 255, 255));
        lblReady.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblReady.setText("Ready to get");
        getContentPane().add(lblReady, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, -1, -1));

        lblMessage.setForeground(new java.awt.Color(255, 255, 255));
        lblMessage.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblMessage.setText("Log in and let’s get back to making things happen!");
        getContentPane().add(lblMessage, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 410, -1, -1));

        lblLoginAcc.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblLoginAcc.setForeground(new java.awt.Color(1, 33, 66));
        lblLoginAcc.setText("Log in to your account");
        getContentPane().add(lblLoginAcc, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 220, -1, -1));

        lblWelcome.setForeground(new java.awt.Color(102, 102, 102));
        lblWelcome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblWelcome.setText("Welcome back! Please enter your details.");
        getContentPane().add(lblWelcome, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 250, 340, -1));

        lblUsername.setForeground(new java.awt.Color(102, 102, 102));
        lblUsername.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblUsername.setText("Username");
        getContentPane().add(lblUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 290, 140, -1));

        txtUserName.setForeground(new java.awt.Color(153, 153, 153));
        txtUserName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserNameActionPerformed(evt);
            }
        });
        getContentPane().add(txtUserName, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 312, 260, 30));

        lblPassword.setForeground(new java.awt.Color(102, 102, 102));
        lblPassword.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblPassword.setText("Password");
        getContentPane().add(lblPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 350, 110, -1));
        getContentPane().add(lblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 120, 93, 89));

        lblForgotPass.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblForgotPass.setForeground(new java.awt.Color(33, 46, 91));
        lblForgotPass.setText("Forgot Password");
        getContentPane().add(lblForgotPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(862, 410, 110, 20));

        btnLogin.setBackground(new java.awt.Color(0, 0, 102));
        btnLogin.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("Log In");
        btnLogin.setColor(new Color(28, 35, 74)); 
        btnLogin.setColorOver(new Color(53, 97, 167)); 
        btnLogin.setColorClick(new Color(28, 35, 74));  
        btnLogin.setRadius(30); 
        btnLogin.setBorderPainted(false); 
        btnLogin.setFocusPainted(false);
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        getContentPane().add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 450, 260, 30));

        cbRememberMe.setForeground(new java.awt.Color(102, 102, 102));
        cbRememberMe.setText("Remember Me");
        cbRememberMe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbRememberMeActionPerformed(evt);
            }
        });
        getContentPane().add(cbRememberMe, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 410, -1, -1));

        lblQuestion.setForeground(new java.awt.Color(102, 102, 102));
        lblQuestion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQuestion.setText("Don't have an account?");
        getContentPane().add(lblQuestion, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 490, 160, 20));

        lblSignUp.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblSignUp.setForeground(new java.awt.Color(33, 46, 91));
        lblSignUp.setText("Sign Up");
        lblSignUp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSignUpMouseClicked(evt);
            }
        });
        getContentPane().add(lblSignUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 490, -1, 20));
        getContentPane().add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 370, 260, 30));

        lblImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImages/Screenshot 2025-01-09 230404.png"))); // NOI18N
        getContentPane().add(lblImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 710));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtUserNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserNameActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        AccountsFileH.funcClearFile("src/main/resources/databases/currentUsersDatabase.txt");
        userAccounts.addAll(AccountsFileH.funcReadUsersFromFile("src/main/resources/databases/usersDatabase.txt"));
        String logInUsername = txtUserName.getText();
        String logInPassword = txtPassword.getText();
        boolean logInSuccessfully = false;
        for(User user: userAccounts){
            System.out.println(user);
            if(logInUsername.equals(user.getUsername()) && logInPassword.equals(user.getPassword())){
                AccountsFileH.funcAddUserToFile("src/main/resources/databases/currentUsersDatabase.txt", user);
                JOptionPane.showMessageDialog(null, "Log-in Successfully!", "Login", JOptionPane.INFORMATION_MESSAGE);
                Main dash = new Main();
                dash.setVisible(true);
                dash.pack();
                dash.setLocationRelativeTo(null);
                dash.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.dispose();
                logInSuccessfully = true;
                break;
            }
        }
        if (logInSuccessfully == false)
        JOptionPane.showMessageDialog(null, "Error username/password! Try again", "Login", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_btnLoginActionPerformed

    private void cbRememberMeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbRememberMeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbRememberMeActionPerformed

    private void lblSignUpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSignUpMouseClicked
        SignUp sign = new SignUp();
        sign.setVisible(true);
        sign.pack();
        sign.setLocationRelativeTo(null);
        sign.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_lblSignUpMouseClicked

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
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JCheckBox cbRememberMe;
    private javax.swing.JLabel lblAgain;
    private javax.swing.JLabel lblForgotPass;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblLoginAcc;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblMessage;
    private javax.swing.JLabel lblOrganized;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblQuestion;
    private javax.swing.JLabel lblReady;
    private javax.swing.JLabel lblSignUp;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables

    private void setExtendedState() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
