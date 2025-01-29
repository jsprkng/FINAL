package com.mycompany.donezodraft;

import com.mycompany.donezodraft.LoginSignUpForms.SignUp;
import com.mycompany.donezodraft.LoginSignUpForms.LoginForm;
import com.mycompany.donezodraft.animations.MyButton;
import java.awt.*;
import java.io.InputStream;
import javax.swing.JFrame;

public class LandingPage extends javax.swing.JFrame {
    public LandingPage() {
        initComponents();
    setBounds(450, 100, 1082, 747);

        try {
            InputStream outfitFontStream = getClass().getResourceAsStream("/fontStyles/Outfit-ExtraBold.ttf");
            Font outfitFont = Font.createFont(Font.TRUETYPE_FONT, outfitFontStream).deriveFont(55f);
            lblTasks.setFont(outfitFont);
            lblDoneZo.setFont(outfitFont);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            InputStream outfitFontStream = getClass().getResourceAsStream("/fontStyles/Montserrat-Regular.ttf");
            Font outfitFont = Font.createFont(Font.TRUETYPE_FONT, outfitFontStream).deriveFont(14f);
            lblMessage.setFont(outfitFont);
            lblMessage2.setFont(outfitFont);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTasks = new javax.swing.JLabel();
        lblDoneZo = new javax.swing.JLabel();
        lblMessage = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        btnSignIn = new javax.swing.JButton();
        lblMessage2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(231, 231, 231));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTasks.setBackground(new java.awt.Color(0, 0, 102));
        lblTasks.setForeground(new java.awt.Color(0, 0, 102));
        lblTasks.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTasks.setText("Tasks?");
        getContentPane().add(lblTasks, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 200, 680, 90));

        lblDoneZo.setForeground(new java.awt.Color(0, 0, 102));
        lblDoneZo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDoneZo.setText("Already DoneZo.");
        getContentPane().add(lblDoneZo, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 290, 760, 80));

        lblMessage.setForeground(new java.awt.Color(30, 30, 30));
        lblMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMessage.setText("reduce stress, and unlock their full potential with a single tick.");
        getContentPane().add(lblMessage, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 380, 890, 60));

        btnLogin.setBackground(new java.awt.Color(231, 231, 231));
        btnLogin.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(0, 0, 102));
        btnLogin.setText("Log In");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        getContentPane().add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 460, 150, 30));

        btnSignIn.setBackground(new java.awt.Color(0, 0, 102));
        btnSignIn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSignIn.setForeground(new java.awt.Color(255, 255, 255));
        btnSignIn.setText("Sign In");
        btnSignIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignInActionPerformed(evt);
            }
        });
        getContentPane().add(btnSignIn, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 460, 160, 30));

        lblMessage2.setForeground(new java.awt.Color(30, 30, 30));
        lblMessage2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMessage2.setText("Make task management easy and breezy—DoneZo! motivates students to master their time,");
        getContentPane().add(lblMessage2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 360, 840, 60));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImages/LandingPage.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 710));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        LoginForm login = new LoginForm();
        login.setVisible(true);
        login.pack();
        login.setLocationRelativeTo(null);
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnSignInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignInActionPerformed
        SignUp sign = new SignUp();
        sign.setVisible(true);
        sign.pack();
        sign.setLocationRelativeTo(null);
        sign.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_btnSignInActionPerformed

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
            java.util.logging.Logger.getLogger(LandingPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LandingPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LandingPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LandingPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LandingPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnSignIn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblDoneZo;
    private javax.swing.JLabel lblMessage;
    private javax.swing.JLabel lblMessage2;
    private javax.swing.JLabel lblTasks;
    // End of variables declaration//GEN-END:variables
}
