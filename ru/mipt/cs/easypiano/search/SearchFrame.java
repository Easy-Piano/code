package ru.mipt.cs.easypiano.search;

//Dima

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;

import static javax.swing.GroupLayout.Alignment.*;

public class SearchFrame extends JFrame {
    public SearchFrame() {
        JLabel label = new JLabel("Find What:");
        final JTextField textField = new JTextField(20);
        JButton findButton = new JButton("Find");
        JButton cancelButton = new JButton("Cancel");

        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = textField.getText();
                try {
                    WebSearch.googleSearch(string);
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });

            GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                        .addComponent(label)
                        .addComponent(textField)
                        .addGroup(layout.createParallelGroup(LEADING)
                                .addComponent(findButton)
                                .addComponent(cancelButton))
        );

        layout.linkSize(SwingConstants.HORIZONTAL, findButton, cancelButton);

        layout.setVerticalGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(BASELINE)
                                .addComponent(label)
                                .addComponent(textField)
                                .addComponent(findButton))
                        .addGroup(layout.createParallelGroup(LEADING)
                                .addComponent(cancelButton))
        );

        setTitle("Find");
        pack();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
