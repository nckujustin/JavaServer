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

class ImageThread extends Thread{

	public static ServerSocket ss = null;

	public void run(){

	//create a new socket to accept connection
	try{
		ss = new ServerSocket(6000);
	} catch(IOException e){
		System.out.println("ERR!");
	}

	//create a image frame for this program
	final ImageFrame frame = new ImageFrame(ss);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setVisible(true);
	//get the next jpeg img from client
	while(true){
		try{
			frame.panel.getimage();
		} catch(IOException e){
			System.out.println("ERR!");
		}
		//due to our frame need to be repaint
		frame.repaint();
	}
  }
}

/** 
    A frame with an image panel
*/
@SuppressWarnings("serial")
class ImageFrame extends JFrame{
    //use this to display the image from client
    public ImagePanel panel;

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

    public ImageFrame(ServerSocket ss){
        // get screen dimensions       
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        // center frame in screen
        setTitle("ImageTest");
        setLocation((screenWidth - DEFAULT_WIDTH) / 2, (screenHeight - DEFAULT_HEIGHT) / 2);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // add panel to frame
        this.getContentPane().setLayout(null);
        panel = new ImagePanel(ss);
        //set the size of image
        panel.setSize(640,480); //640 480
        panel.setLocation((DEFAULT_WIDTH-640), 0);
        add(panel);

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

        jb_7 = new JButton("Message7");
        //positon x, position y, size width, size height
        jb_7.setBounds(30,400,180,50);
        add(jb_7);

        input_field = new JTextField(20);
        input_field.setText("You can input message here!...");
        input_field.setBounds(250,640,580,30);
        input_field.requestFocus();
        input_field.selectAll();
        add(input_field);

        output_field = new JTextField(20);
        output_field.setText("Here is for communucation message");
        output_field.setBounds(250,500,580,130);
        add(output_field);
    }

    public static final int DEFAULT_WIDTH = 900;
    public static final int DEFAULT_HEIGHT = 700;
}

/**
    A panel that displays a tiled image
*/
@SuppressWarnings("serial")
class ImagePanel extends JPanel {     
    private ServerSocket ss;
    private Image image;
    private InputStream input_stream;
    
    static int ImageCount = 0;
    static long current_time = 0;
    static long last_time = 0;
    static double fps = 0;

    public ImagePanel(ServerSocket ss) {  
        this.ss = ss;
    }
    
    public void getimage() throws IOException{
        Socket s = this.ss.accept();
        //while(this.ss.accept)
        if(ImageCount % 30 == 0){
            current_time = System.currentTimeMillis();
            if((current_time - last_time) != 0){
                fps = 30/((double)(current_time - last_time)/1000);
            }
            System.out.println("Got image! fps : " + fps);
            last_time = current_time;
        }
        this.input_stream = s.getInputStream();
        this.image = ImageIO.read(input_stream);
        this.input_stream.close();
        ImageCount++;
    }
   
    public void paintComponent(Graphics g){  
        super.paintComponent(g);    
        if (image == null) return;
        g.drawImage(image, 0, 0, null);
    }

}