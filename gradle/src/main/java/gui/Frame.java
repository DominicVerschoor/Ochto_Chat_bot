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


public class Frame extends JFrame {

    private JFrame frame;
    private JTextArea area;
    private JTextField field;
    private ImageIcon image;
    private ImageIcon imageIcon;
    private JLabel label;
    private JScrollPane sp;
    private LocalTime time;
    private LocalDate date;
    private Random random;



    public Frame(){

        imageIcon = new ImageIcon("gradle\\src\\main\\java\\gui\\pics\\img2.png");
        random = new Random();
        frame = new JFrame();
        area = new JTextArea();
        field = new JTextField();
        image = new ImageIcon("gradle\\src\\main\\java\\gui\\pics\\img.png");
        Image img = image.getImage();
        Image modImg = img.getScaledInstance(700,600, Image.SCALE_SMOOTH);
        image = new ImageIcon(modImg);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setSize(700, 550);
        label = new JLabel(image);
        label.setSize(700,600);
        label.add(area);
        label.add(field);
        frame.add(label);
        frame.setTitle("OCTO");
        frame.setIconImage(imageIcon.getImage());
        frame.setLocationRelativeTo(null);
        time=LocalTime.now();
        date=LocalDate.now();


        //sp = new JScrollPane (area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //  FOR THE TEXT AREA
        area.setSize(350,400);
        area.setLocation(15,35);
        area.setBackground(Color.LIGHT_GRAY);
        LineBorder lineBorder =new LineBorder(Color.white, 6, true);
        area.setBorder(lineBorder);
        area.setFont(new Font("Caslon",Font.BOLD,13));
        area.setEditable(false);

        // FOR THE TEXT INPUT FIELD
        field.setSize(350,40);
        field.setLocation(15,450);
        field.setBackground(Color.LIGHT_GRAY);
        LineBorder lineBorder1 =new LineBorder(Color.white, 6, true);
        field.setBorder(lineBorder1);
        field.setFont(new Font("Caslon",Font.BOLD,13));


        field.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                String message=field.getText().toLowerCase();

                area.append("YOU >>   "+field.getText()+"\n");
                field.setText("");
                Socket sock=new Socket();


                if(message.contains("how are you"))
                {
                    int num=random.nextInt(3);
                    if(num==0)
                    {
                        bot("I'm fine !,What about you ? ");
                    }
                    else if(num==1)
                    {
                        bot("I am good , thanks for asking !");
                    }
                    else
                    {
                        bot("I am great ,thanks for asking !");
                    }

                }
                else if(message.contains("you")&&(message.contains("smart")||message.contains("good")))
                {
                    bot("Thank you !");
                }
                else if(message.contains("welcome"))
                {
                    bot("You are so polite.How can i help you ?");
                }

                else if(message.contains("hi")&&message.charAt(0)=='h'||message.contains("hello")||message.contains("hey"))
                {

                    int num=random.nextInt(3);
                    if(num==0)
                    {
                        bot("Hii");
                    }
                    else if(num==1)
                    {
                        bot("Hello");
                    }
                    else if(num==2)
                    {
                        bot("Heyy");
                    }
                    else if(num==3)
                    {
                        bot("hello buddy");
                    }
                }
                else if(message.contains("by"))
                {
                    bot("Byy,See you soon ..!");
                }
                else if(message.contains("i am good")||message.contains("i am great")||message.contains("i am ")&&message.contains("fine"))
                {

                    bot("Good to hear.");
                }
                else if(message.contains("thank"))
                {
                    int num=random.nextInt(3);
                    if(num==0)
                    {
                        bot("Welcome..");
                    }
                    else if(num==1)
                    {
                        bot("My plesure");
                    }
                    else if(num==2)
                    {
                        bot("Happy to help");
                    }
                    else if(num==3)
                    {
                        bot("That's why i'm here for..");
                    }
                }
                else if(message.contains("what") && message.contains("name"))
                {
                    if(message.contains("your"))
                    {
                        bot("I'm Bot...");
                    }
                    if(message.contains("my"))
                    {
                        bot("Your name is .. maybe .. hmmm... IDK :<");
                    }
                }
                else if(message.contains("change"))
                {
                    bot("Sorry,I can't change anything...");
                }
                else if( message.contains("time"))
                {

                    String ctime=new String();
                    if(time.getHour()>12)
                    {
                        int hour=time.getHour()-12;
                        ctime= ctime+hour+":"+time.getMinute()+":"+time.getSecond()+" PM";
                    }

                    else
                    {

                        ctime=ctime+time.getHour()+":"+time.getMinute()+":"+time.getSecond()+" AM";
                    }
                    bot(ctime);


                }
                else if(message.contains("date")||message.contains("month")||message.contains("year")||message.contains("day"))
                {

                    String cdate=new String();
                    cdate=cdate + date.getDayOfWeek()+","+date.getDayOfMonth()+" "+date.getMonth()+" "+date.getYear();
                    bot(cdate);


                }

                else if(message.contains("good morning "))
                {

                    bot("Good morning,Have a nice day !");

                }
                else if(message.contains("good night"))
                {

                    bot("Good night,Have a nice dreams !");

                }
                else if(message.contains("good evening"))
                {

                    bot("Good Evening ...!");

                }
                else if(message.contains("good") && message.contains("noon"))
                {

                    bot("Good Afternoon ...!");

                }

                else if(message.contains("clear")&&(message.contains("screen")||message.contains("chat")))
                {
                    bot("wait a few second...");
                    area.setText("");
                }
                else
                {
                    try
                    {
                        try
                        {
                            URL url=new URL("https://google.co.in");
                            URLConnection connection=url.openConnection();
                            connection.connect();
                            bot("Here some results for you ...");
                            java.awt.Desktop.getDesktop().browse(java.net.URI.create("http://www.google.com/search?hl=en&q="+message.replace(" ", "+")+"&btnG=Google+Search"));

                        }
                        catch(Exception ee)
                        {
                            bot("Connect with internet connection for get better results...");
                        }

                    }
                    catch(Exception eee)
                    {
                        int num=random.nextInt(3);
                        if(num==0)
                        {

                            bot("Sorry ,I can't understand you !");
                        }
                        else if(num==1)
                        {
                            bot("Sorry,I don't understand ");
                        }
                        else if(num==2)
                        {
                            bot("My apologies...I don't understand ");
                        }
                    }
                }

            }
        });

    }


        private void bot (String string){
            area.append("OCTO >>   " + string + "\n");
        }

}

