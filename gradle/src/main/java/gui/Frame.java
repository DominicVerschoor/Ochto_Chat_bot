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


public class Frame extends JFrame{

    private final JFrame frame;
    private final JTextArea area;
    private final JTextField field;
    private final ImageIcon image;
    private final ImageIcon imageIcon;
    private final JLabel label;
    private final JScrollPane scrollPane;
    private final LocalTime time;
    private final LocalDate date;
    private final Random random;
    private final JButton button;
    private final LineBorder lineBorder;



    public Frame(){
        ImageIcon image1;

        imageIcon = new ImageIcon("gradle\\src\\main\\java\\gui\\pics\\img2.png");
        random = new Random();
        frame = new JFrame();
        area = new JTextArea();
        field = new JTextField();
        button = new JButton("Add A Skill");
        image1 = new ImageIcon("gradle\\src\\main\\java\\gui\\pics\\img.png");
        Image img = image1.getImage();
        Image modImg = img.getScaledInstance(600,600, Image.SCALE_SMOOTH);
        image1 = new ImageIcon(modImg);
        image = image1;
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setSize(600, 550);
        label = new JLabel(image);
        label.setSize(600,600);
        label.add(area);
        label.add(field);
        label.add(button);
        frame.add(label);
        frame.setTitle("OCTO");
        frame.setIconImage(imageIcon.getImage());
        frame.setLocationRelativeTo(null);
        time=LocalTime.now();
        date=LocalDate.now();
        lineBorder =new LineBorder(Color.white, 5, true);

        //  FOR THE TEXT AREA
        area.setBackground(Color.LIGHT_GRAY);
        area.setBorder(lineBorder);
        area.setFont(new Font("Caslon",Font.BOLD,13));
        area.setEditable(false);

        // FOR THE TEXT INPUT FIELD
        field.setSize(350,40);
        field.setLocation(15,450);
        field.setBackground(Color.LIGHT_GRAY);
        field.setBorder(lineBorder);
        field.setFont(new Font("Caslon",Font.BOLD,13));

        // SCROLL PANE
        scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(15,35,350,400);
        scrollPane.getViewport().setBackground(Color.LIGHT_GRAY);
        scrollPane.getViewport().add(area);
        label.add(scrollPane);

        // BUTTON
        button.setSize(100,50);
        button.setLocation(420,50);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                new Frame2();
            }
        });
        label.add(button);

        main();
    }

    public void main()
    {
        // DO ACTIONS WHEN EVER USER INPUT SOMETHING
        field.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                String message=field.getText().toLowerCase();

                area.append("YOU >>   "+field.getText()+"\n");
                field.setText("");
                Socket sock=new Socket();


                if(message.contains("how are you"))
                {
                    String[] responses = new String[]{"I'm fine !,What about you?","I am good , thanks for asking !","I am great ,thanks for asking !"};
                    bot(responses[random.nextInt(3)]);
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
                    String[] responses = new String[]{"Hii","Hello","Heyy","Hello buddy"};
                    bot(responses[random.nextInt(4)]);
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
                    String[] responses = new String[]{"Welcome","My pleasure","Happy to help","That's what i'm here for"};
                    bot(responses[random.nextInt(4)]);
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
                    googleResponse(message);
                }

            }
        }
        );
    }

    private void googleResponse(String string){
        try
        {
            try
            {
                URL url=new URL("https://google.co.in");
                URLConnection connection=url.openConnection();
                connection.connect();
                bot("I currently do not know a lot about this topic...");
                bot("Here are some other sources that might be able to help you!");
                String googleURL = ("http://www.google.com/search?hl=en&q="+string.replace(" ", "+")+"&btnG=Google+Search");
                bot(googleURL);
                //java.awt.Desktop.getDesktop().browse(java.net.URI.create(googleURL));
                //Button button1 = new Button("google button?");
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
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                bot("My apologies...I don't understand ");
            }
        }
    }

     // OCTO BOT RESPONSE
    private void bot (String string){
        area.append("OCTO >>   " + string + "\n");
    }

}

