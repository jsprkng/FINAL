package com.mycompany.donezodraft.InternalFrames;

import com.mycompany.donezodraft.animations.MyButton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class CalendarPanel extends JPanel {
    private JLabel lblMonth;
    private RoundedCalendar pnlCalendar;
    private JButton btnPrev, btnNext;
    private int currentMonth, currentYear, todayDay, todayMonth, todayYear;

    public CalendarPanel() {
        setLayout(new BorderLayout());
        setOpaque(false); 

        currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        todayDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        todayMonth = Calendar.getInstance().get(Calendar.MONTH);
        todayYear = Calendar.getInstance().get(Calendar.YEAR);

        lblMonth = new JLabel("", SwingConstants.CENTER);
        loadFont("/fontStyles/Outfit-ExtraBold.ttf", 16f, new Color(15, 26, 43), lblMonth);

        pnlCalendar = new RoundedCalendar(); 
        pnlCalendar.setLayout(new GridLayout(0, 7));
        pnlCalendar.setBackground(Color.WHITE); 

        btnPrev = new JButton("<");
        btnPrev.setPreferredSize(new Dimension(70, 50)); 
        btnPrev.setFocusPainted(false); 
        btnPrev.setOpaque(false); 
        btnPrev.setContentAreaFilled(false); 
        btnPrev.setBorder(null);
        btnPrev.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeMonth(-1);
            }
        });

        btnNext = new JButton(">");
        btnNext.setPreferredSize(new Dimension(70, 50)); // Adjust button size
        btnNext.setFocusPainted(false); // Remove focus ring
        btnNext.setOpaque(false); // Make button transparent
        btnNext.setContentAreaFilled(false); // Make button background transparent
        btnNext.setBorder(null);
        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeMonth(1);
            }
        });

        JPanel pnlHeader = new RoundedCalendar();
        pnlHeader.setLayout(new BorderLayout());
        pnlHeader.setBackground(Color.WHITE); 
        pnlHeader.add(btnPrev, BorderLayout.WEST);
        pnlHeader.add(lblMonth, BorderLayout.CENTER);
        pnlHeader.add(btnNext, BorderLayout.EAST);

        RoundedCalendar pnlMain = new RoundedCalendar(); 
        pnlMain.setLayout(new BorderLayout());
        pnlMain.setBackground(Color.WHITE); 
        pnlMain.add(pnlHeader, BorderLayout.NORTH);
        pnlMain.add(pnlCalendar, BorderLayout.CENTER);

        add(pnlMain, BorderLayout.CENTER);

        showCalendar(currentMonth, currentYear);
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

    public class RoundedCalendar extends JPanel {
        public RoundedCalendar() {
            setOpaque(false);  // Make the panel transparent to allow custom painting
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);  // Apply rounded corners
        }
    }

    private void showCalendar(int month, int year) {
        lblMonth.setText(getMonthName(month) + " " + year);

        pnlCalendar.removeAll();

        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String day : days) {
            JLabel lblDay = new JLabel(day, SwingConstants.CENTER);
            loadFont("/fontStyles/Outfit-SemiBold.ttf", 14f, Color.BLACK, lblDay);
            pnlCalendar.add(lblDay);
        }

        Calendar calendar = new GregorianCalendar(year, month, 1);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int startDay = calendar.get(Calendar.DAY_OF_WEEK);

        for (int i = 1; i < startDay; i++) {
            pnlCalendar.add(new JLabel(""));
        }

        for (int i = 1; i <= daysInMonth; i++) {
            JLabel lblDay = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            loadFont("/fontStyles/Outfit-Regular.ttf", 14f, Color.BLACK, lblDay);

            if (i == todayDay && month == todayMonth && year == todayYear) {
                lblDay.setBackground(new Color(255, 0, 0)); 
                lblDay.setOpaque(true); 
                lblDay.setForeground(Color.WHITE); 
            }

            pnlCalendar.add(lblDay);
        }

        pnlCalendar.revalidate();
        pnlCalendar.repaint();
    }

    private void changeMonth(int delta) {
        currentMonth += delta;
        if (currentMonth < 0) {
            currentMonth = 11;
            currentYear--;
        } else if (currentMonth > 11) {
            currentMonth = 0;
            currentYear++;
        }
        showCalendar(currentMonth, currentYear);
    }

    private String getMonthName(int month) {
        switch (month) {
            case 0: return "January";
            case 1: return "February";
            case 2: return "March";
            case 3: return "April";
            case 4: return "May";
            case 5: return "June";
            case 6: return "July";
            case 7: return "August";
            case 8: return "September";
            case 9: return "October";
            case 10: return "November";
            case 11: return "December";
            default: return "";
        }
    }
}
