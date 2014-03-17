import java.lang.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.awt.event.*;

import javax.imageio.*;
import javax.swing.*;
import java.net.*;
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
            System.out.println("waiting...");
            for (long id = 0; id < 100000000; id++) {
                
            }
            if (frame.send_flag == 1) {
                System.out.println("1 Trying...");
                out.write(frame.send_message.getBytes());
                out.flush();
                frame.send_flag = 0;
                System.out.println("1 End...");
            }
        }
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

    //this is the textfield
    public JTextField input_field;
    public JTextField output_field;
    public JTextField label;

    public static int send_flag = 0;
    public static String send_message = "";

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
            public void mouseClicked(MouseEvent evt){
                send_message = "1";
                send_flag = 1;
                System.out.println("flag");
            }
        });

        //set the button DOWN
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
            public void mouseClicked(MouseEvent evt){
                send_message = "2";
                send_flag = 1;
                System.out.println("flag");
            }
        });

        //set the button LEFT
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
            public void mouseClicked(MouseEvent evt){
                send_message = "3";
                send_flag = 1;
                System.out.println("flag");
            }
        });

        //set the button RIGHT
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
            public void mouseClicked(MouseEvent evt){
                send_message = "4";
                send_flag = 1;
                System.out.println("flag");
            }
        });

        String ip = null;
        String address = null; 

        try{
        InetAddress addr = InetAddress.getLocalHost(); 
        ip = addr.getHostAddress().toString();
        address = addr.getHostName().toString();
        }catch(IOException e){

        }


        jb_1 = new JButton(ip);
        //positon x, position y, size width, size height
        jb_1.setBounds(30,40,180,50);
        add(jb_1);

        jb_1.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent evt){
                send_message = "1";
                send_flag = 1;
                System.out.println("flag");
            }
        });
        
    }

    public static final int DEFAULT_WIDTH = 250;
    public static final int DEFAULT_HEIGHT = 700;
}
