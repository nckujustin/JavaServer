import java.lang.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.awt.event.*;

import javax.imageio.*;
import javax.swing.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetSocketAddress;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;

import java.io.*;
import java.util.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class ChatThread extends Thread{

	public static ServerSocket ss = null;
    public static Socket con = null;
    //public int send_flag = 0;

	public void run(){

    //create a new socket to accept connection
    try{
        ss = new ServerSocket(6001);
    } catch(IOException e){
        System.out.println("ERR!");
    }

    //create a image frame for this program
    final ChatFrame frame = new ChatFrame(ss);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

    try{
        con = ss.accept();
        OutputStream out = con.getOutputStream();
        System.out.println("chat success");
        System.out.println(con.getInetAddress());
        while(true){
            //Thread.sleep(300);
            System.out.println("waiting...");
            for (long id = 0; id < 100000000; id++) {
                
            }
            if (frame.send_flag == 1) {
                System.out.println("1 Trying...");
                out.write("5678".getBytes());
                out.flush();
                frame.send_flag = 0;
                System.out.println("1 End...");

                /*for receiving*/
                /*try{

                System.out.println("1 Trying...");

                BufferedReader in;
                in = new BufferedReader (new InputStreamReader(con.getInputStream()));
                
                System.out.println("I got :");
                boolean more = true;
                while(more) {
                    String inStr = in.readLine();
                    if (inStr == null) {
                      more = false;
                    } else {
                        System.out.println(inStr);
                    }    
                }
                //ss.close();
                con.close();
                System.out.println("end Sending...");
                frame.send_flag = 0;
                }catch(IOException e){
                    System.out.println("chat send err");
                }*/
            }
        }
        
        //out.close();
    }catch(IOException e){
        System.out.println("chat connect err");
    }

  }
}

/** 
    A frame with an image panel
*/
@SuppressWarnings("serial")
class ChatFrame extends JFrame{
    //Socket con = null;
    //use this to display the image from client
    public ChatPanel panel;

    //these are the buttons for orient
    public JButton jb_up;
    public JButton jb_down;
    public JButton jb_left;
    public JButton jb_right;

    //theses are the buttons for message
    public JButton jb_1;
    public JButton jb_2;
    public JButton jb_3;
    public JButton jb_4;
    public JButton jb_5;
    public JButton jb_6;
    public JButton jb_7;

    //this is the textfield
    public JTextField input_field;
    public JTextField output_field;

    public static int send_flag = 0;

    public ChatFrame(final ServerSocket ss){
        // get screen dimensions       
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        // center frame in screen
        setTitle("ChatWindow");
        setLocation((screenWidth / 2)-445, (screenHeight - DEFAULT_HEIGHT) / 2);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.getContentPane().setBackground( Color.GRAY );

        // add panel to frame
        this.getContentPane().setLayout(null);
        panel = new ChatPanel(ss);
        //set the size of image
        panel.setSize(640,480); //640 480
        panel.setLocation((DEFAULT_WIDTH-640), 0);
        //add(panel);

        //set the button UP
        jb_up = new JButton("UP");
        //positon x, position y, size width, size height
        jb_up.setBounds(100,510,50,50);
        add(jb_up);

        jb_up.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent evt)
            {
                jb_up.setBackground(Color.yellow);
                jb_up.setOpaque(true);
            }
            public void mouseExited(MouseEvent evt)
            {
                jb_up.setBackground(null);
                jb_up.setOpaque(true);
            }
        });

        //set the button UP
        jb_down = new JButton("Down");
        //positon x, position y, size width, size height
        jb_down.setBounds(100,610,50,50);
        add(jb_down);

        jb_down.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent evt)
            {
                jb_down.setBackground(Color.yellow);
                jb_down.setOpaque(true);
            }
            public void mouseExited(MouseEvent evt)
            {
                jb_down.setBackground(null);
                jb_down.setOpaque(true);
            }
        });

        //set the button UP
        jb_left = new JButton("LEFT");
        //positon x, position y, size width, size height
        jb_left.setBounds(50,(510+610)/2,50,50);
        add(jb_left);

        jb_left.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent evt)
            {
                jb_left.setBackground(Color.yellow);
                jb_left.setOpaque(true);
            }
            public void mouseExited(MouseEvent evt)
            {
                jb_left.setBackground(null);
                jb_left.setOpaque(true);
            }
        });

        //set the button UP
        jb_right = new JButton("RIGHT");
        //positon x, position y, size width, size height
        jb_right.setBounds(150,(510+610)/2,50,50);
        add(jb_right);

        jb_right.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent evt)
            {
                jb_right.setBackground(Color.yellow);
                jb_right.setOpaque(true);
            }
            public void mouseExited(MouseEvent evt)
            {
                jb_right.setBackground(null);
                jb_right.setOpaque(true);
            }
        });

        //set the button UP
        jb_1 = new JButton("Message1");
        //positon x, position y, size width, size height
        jb_1.setBounds(30,40,180,50);
        add(jb_1);

        jb_1.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent evt){
                System.out.println("1 started");
            }
        });

        jb_2 = new JButton("Message2");
        //positon x, position y, size width, size height
        jb_2.setBounds(30,100,180,50);
        add(jb_2);

        jb_3 = new JButton("Message3");
        //positon x, position y, size width, size height
        jb_3.setBounds(30,160,180,50);
        add(jb_3);

        jb_4 = new JButton("Message4");
        //positon x, position y, size width, size height
        jb_4.setBounds(30,220,180,50);
        add(jb_4);

        jb_5 = new JButton("Message5");
        //positon x, position y, size width, size height
        jb_5.setBounds(30,280,180,50);
        add(jb_5);

        jb_6 = new JButton("Message6");
        //positon x, position y, size width, size height
        jb_6.setBounds(30,340,180,50);
        add(jb_6);

        jb_7 = new JButton("Connect");
        //positon x, position y, size width, size height
        jb_7.setBounds(30,400,180,50);
        add(jb_7);

        jb_7.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent evt){
                send_flag = 1;
                System.out.println("flag");
                /*System.out.println("chat thread started");
                try{
                    con = ss.accept();
                    System.out.println("chat success");
                    System.out.println(con.getInetAddress());

                    try{

                        System.out.println("1 Trying...");

                        BufferedReader in;
                        in = new BufferedReader (new InputStreamReader(con.getInputStream()));
                        
                        System.out.println("I got :");
                        boolean more = true;
                        while(more) {
                            String inStr = in.readLine();
                            if (inStr == null) {
                              more = false;
                            } else {
                                System.out.println(inStr);
                            }    
                        }
                        //ss.close();
                        con.close();
                        System.out.println("end Sending...");
                    }catch(IOException e){
                        System.out.println("chat send err");
                    }
                    
                    //out.close();
                }catch(IOException e){
                    System.out.println("chat connect err");
                }*/
            }
        });
    }

    public static final int DEFAULT_WIDTH = 250;
    public static final int DEFAULT_HEIGHT = 700;
}
