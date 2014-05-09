package graphics;

import javax.swing.*;
import static javax.swing.GroupLayout.Alignment.*;

//Ivan
public class MainFrame extends JFrame{
    public MainFrame() { initComponents(); }

    private void initComponents(){
        JLabel nameLabel = new JLabel("Name of file");
        JButton exerciseButton = new JButton("Exercise");
        JButton musicBankButton = new JButton("Choose music");
        JButton pianoButton = new JButton ("Piano button");
        JButton statsButton = new JButton ("My Statistics");
        JButton tutorialButton = new JButton (" Tutorial ");



        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);


        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                     .addComponent(exerciseButton)
                     .addComponent(nameLabel))
                .addComponent(musicBankButton)
                .addComponent(pianoButton)
                .addComponent(statsButton)
                .addComponent(tutorialButton)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(exerciseButton)
                        .addComponent(nameLabel))
                .addComponent(musicBankButton)
                .addComponent(pianoButton)
                .addComponent(statsButton)
                .addComponent(tutorialButton)
        );

    setTitle("Easy Piano");
    pack();
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}



