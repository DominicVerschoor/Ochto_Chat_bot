package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;



public class Frame extends JFrame {

    private JFrame frame;
    private JTextArea textArea;
    private JTextField textField;
    private ImageIcon image;
    private JLabel label;







    public Frame(){
        frame = new JFrame();
        textArea = new JTextArea();
        textField = new JTextField();
        image = new ImageIcon("C:\\Users\\mobasha\\IdeaProjects\\Project_2-2\\gradle\\src\\main\\java\\gui\\pics\\img.png");
        Image img = image.getImage();
        Image modImg = img.getScaledInstance(700,600, Image.SCALE_SMOOTH);
        image = new ImageIcon(modImg);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setSize(700, 600);
        label = new JLabel(image);
        label.setSize(700,600);
        label.add(textArea);
        label.add(textField);
        frame.add(label);
        frame.setTitle("OCTO_BOT");
        frame.setLocationRelativeTo(null);

        //  FOR THE TEXT AREA
        textArea.setSize(350,400);
        textArea.setLocation(15,35);
        textArea.setBackground(Color.LIGHT_GRAY);

        // FOR THE TEXT INPUT FIELD
        textField.setSize(350,40);
        textField.setLocation(15,450);
        textField.setBackground(Color.LIGHT_GRAY);


        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String getText = textField.getText().toLowerCase(Locale.ROOT);
                textArea.append("YOU >> " +"\n" +getText+ "\n");
                textField.setText("");

                if(!(getText.isEmpty())){
                    bot("Hey there! How can i asset you?");
                    textField.setText("");
                }

            }
        });
    }


        private void bot (String string){
            textArea.append("OCTO >>" + "\n" + string + "\n");
        }

}

