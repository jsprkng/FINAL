package com.mycompany.donezodraft;

import static com.mycompany.donezodraft.Main.intCompleted;
import static com.mycompany.donezodraft.Main.intTaskCounter;
import java.awt.*;
import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class ProgressBar extends JProgressBar {

    public Color getColorString() {
        return colorString;
    }

    public void setColorString(Color colorString) {
        this.colorString = colorString;
    }

    private Color colorString = new Color(200, 200, 200);

    public ProgressBar() {
        setPreferredSize(new Dimension(100, 5));
        setBackground(new Color(130,168,206));
        setForeground(new Color(1,33,66));
        setUI(new BasicProgressBarUI() {
            @Override
            protected void paintString(Graphics grphcs, int i, int i1, int i2, int i3, int i4, Insets insets) {
                grphcs.setColor(getColorString());
                super.paintString(grphcs, i, i1, i2, i3, i4, insets);
            }
        });
        setMaximum(intTaskCounter);
        setValue(intCompleted);
    }
}