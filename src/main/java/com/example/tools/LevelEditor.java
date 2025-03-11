package com.example.tools;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class LevelEditor {
    private JFrame frame;

    public LevelEditor() {
        frame = new JFrame("Level Editor");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel();
        JButton btnSave = new JButton("Save Level");
        btnSave.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Level saved!"));
        panel.add(btnSave);
        frame.add(panel, BorderLayout.SOUTH);
    }

    public void openEditor() {
        frame.setVisible(true);
    }
}
