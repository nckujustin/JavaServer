import java.lang.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.awt.event.*;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

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
        System.out.println(con.getInetAddress());
        while(true){
            for (long id = 0; id < 100000000; id++) {
                
            }
            if (frame.send_flag == 1) {
                out.write(frame.send_message.getBytes());
                out.flush();
                System.out.println("this is 1");
                frame.send_flag = 0;
            }
            if (frame.back_to_center == 1) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                frame.label2.setBounds(103, 398, 50, 50);
                frame.back_to_center = 0;
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

    //for ceter
    public JLabel label2 = new JLabel();
    
    public static int send_flag = 0;
    public static String send_message = "";
    int str_count = 0;
    public static int back_to_center = 0;

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

        String ip = null;
        String address = null; 

        try{
            InetAddress addr = InetAddress.getLocalHost(); 
            ip = addr.getHostAddress().toString();
            address = addr.getHostName().toString();
        }catch(IOException e){

        }



        /*
            Show the orientation control status.
        */
        ImageIcon redDot = new ImageIcon("image/Reddot.png");
        //final JLabel label2 = new JLabel();
        label2.setBounds(103, 398, 50, 50);
        label2.setIcon(redDot);
        add(label2);

        ImageIcon background = new ImageIcon("image/orientPanel.jpg");
        JLabel label = new JLabel();
        label.setBounds(30, 320, 185, 200);
        label.setIcon(background);
        add(label);
        
        /*
            This button guide the user to input the correct ip of computer to the cell phone.
        */
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

        final JTextField cmd_input = new JTextField("", 25);
        String old_string = "";

        cmd_input.getDocument().addDocumentListener(new DocumentListener() {
              public void changedUpdate(DocumentEvent e) {

              }
              public void removeUpdate(DocumentEvent e) {
                if (str_count >= 0) {
                    str_count --;
                }
              }
              public void insertUpdate(DocumentEvent e) {
                if (back_to_center == 0) {
                    String inputString = cmd_input.getText();
                    char[] charArr = inputString.toCharArray();
                    char check_cmd = charArr[str_count];
                    switch(check_cmd){
                        // right
                        case 'e':
                            send_message = "4";
                            send_flag = 4;
                            label2.setBounds(183, 398, 50, 50);
                            back_to_center = 1;
                            break;
                        // left
                        case 't':
                            send_message = "3";
                            send_flag = 3;
                            label2.setBounds(23, 398, 50, 50); 
                            back_to_center = 1;
                            break;
                        // up
                        case 'i':
                            send_message = "1";
                            send_flag = 1;
                            label2.setBounds(103, 318, 50, 50);
                            back_to_center = 1;
                            break;
                        // down
                        case 'm':
                            send_message = "2";
                            send_flag = 2;
                            label2.setBounds(103, 478, 50, 50);
                            back_to_center = 1;
                            break;
                        default:
                            break;
                    }
                }
                str_count ++;
              }
          });
        cmd_input.setBounds(30,100,180,180);
        add(cmd_input);
            
    }

    public static final int DEFAULT_WIDTH = 250;
    public static final int DEFAULT_HEIGHT = 580;
}
