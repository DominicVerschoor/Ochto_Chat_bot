package gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;
import java.util.TimerTask;


public class Frame2 extends JFrame {

    private final JFrame frame;
    public final JTextArea area1;
    public final JTextArea area2;
    public final JTextArea area3;
    private final JTextField field1;
    private final JTextField field2;
    private final JTextField field3;
    private final JScrollPane scrollPane1;
    private final JScrollPane scrollPane2;
    private final JScrollPane scrollPane3;
    private final ImageIcon image;
    private final ImageIcon imageIcon;
    private final JLabel label;
    private final Random random;
    private final LineBorder lineBorder;

    public Frame2(){
        ImageIcon image1;

        imageIcon = new ImageIcon("gradle\\src\\main\\java\\gui\\pics\\img2.png");
        random = new Random();
        frame = new JFrame();
        area1 = new JTextArea();
        area2 = new JTextArea();
        area3 = new JTextArea();
        field1 = new JTextField();
        field2 = new JTextField();
        field3 = new JTextField();
        image1 = new ImageIcon("gradle\\src\\main\\java\\gui\\pics\\img.png");
        Image img = image1.getImage();
        Image modImg = img.getScaledInstance(1124,550, Image.SCALE_SMOOTH);
        image1 = new ImageIcon(modImg);
        image = image1;
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setSize(1124, 550);
        label = new JLabel(image);
        label.setSize(1124,550);
        label.add(area1);
        label.add(area2);
        label.add(area3);
        label.add(field1);
        label.add(field2);
        label.add(field3);
        label.setText("waar de fuck?");
        frame.add(label);
        frame.setTitle("Add A Skill");
        frame.setIconImage(imageIcon.getImage());
        frame.setLocationRelativeTo(null);
        lineBorder =new LineBorder(Color.white, 5, true);

        // FOR THE TEXT INPUT FIELDS
        field1.setSize(300,200);
        field1.setLocation(50,50);
        field1.setBackground(Color.LIGHT_GRAY);
        field1.setBorder(lineBorder);
        field1.setFont(new Font("Caslon",Font.BOLD,13));
        field1.setText("Enter Question here");

        field2.setSize(300,200);
        field2.setLocation(400,50);
        field2.setBackground(Color.LIGHT_GRAY);
        field2.setBorder(lineBorder);
        field2.setFont(new Font("Caslon",Font.BOLD,13));
        field2.setText("Enter Slots here");

        field3.setSize(300,200);
        field3.setLocation(750,50);
        field3.setBackground(Color.LIGHT_GRAY);
        field3.setBorder(lineBorder);
        field3.setFont(new Font("Caslon",Font.BOLD,13));
        field3.setText("Enter Actions here");

        // FOR THE OUTPUT AREAS
        area1.setBackground(Color.LIGHT_GRAY);
        area1.setBorder(lineBorder);
        area1.setFont(new Font("Caslon",Font.BOLD,13));
        area1.setEditable(false);

        area2.setBackground(Color.LIGHT_GRAY);
        area2.setBorder(lineBorder);
        area2.setFont(new Font("Caslon",Font.BOLD,13));

        area3.setBackground(Color.LIGHT_GRAY);
        area3.setBorder(lineBorder);
        area3.setFont(new Font("Caslon",Font.BOLD,13));
        area3.setEditable(false);

        // FOR THE SCROLL PANES
        scrollPane1 = new JScrollPane();
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane1.setBounds(50,300,300,200);
        scrollPane1.getViewport().setBackground(Color.LIGHT_GRAY);
        scrollPane1.getViewport().add(area1);
        label.add(scrollPane1);

        scrollPane2 = new JScrollPane();
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane2.setBounds(400,300,300,200);
        scrollPane2.getViewport().setBackground(Color.LIGHT_GRAY);
        scrollPane2.getViewport().add(area2);
        label.add(scrollPane2);

        scrollPane3 = new JScrollPane();
        scrollPane3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane3.setBounds(750,300,300,200);
        scrollPane3.getViewport().setBackground(Color.LIGHT_GRAY);
        scrollPane3.getViewport().add(area3);
        label.add(scrollPane3);

        textAreas();
    }

    public void textAreas(){
        // these are temporary ways to store the inputs, we should instead implement the slot editor here
        field1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                String input=field1.getText();
                area1.append(input + "\n");
                field1.setText(""); //should be replaced by a "clear text" button
            }
        });

        field2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                String input=field2.getText();
                area2.append(input + "\n");
                field2.setText(""); //should be replaced by a "clear text" button
            }
        });

        field3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                String input=field3.getText();
                area3.append(input + "\n");
                field3.setText(""); //should be replaced by a "clear text" button
            }
        });
    }
}
    