package Main;

import java.awt.BorderLayout;

import javax.swing.*;

public class Progress extends JFrame {

    private static final long serialVersionUID = 1L;
    public JProgressBar bar;

    public Progress() {
        this.setSize(300, 80);
        this.setTitle("Démarrage");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        bar = new JProgressBar();
        bar.setMaximum(500);
        bar.setMinimum(0);
        bar.setStringPainted(true);

        this.getContentPane().add(bar, BorderLayout.CENTER);

        this.setVisible(true);
    }

}